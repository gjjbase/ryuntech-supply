import Taro, { Component } from "@tarojs/taro";
import { View,ScrollView } from "@tarojs/components";
import { AtSearchBar ,AtButton} from 'taro-ui'
import "./reorder.scss";
import app_status_1 from "../../assets/images/order/app_status_1.png";
import app_status_2 from "../../assets/images/order/app_status_2.png";
import order_02 from "../../assets/images/order/order_02.png";
import order_03 from "../../assets/images/order/order_03.png";
import order_04 from "../../assets/images/order/order_04.png";
import order_05 from "../../assets/images/order/order_05.png";
import Api from '../../servers/api'
export default class Reorder extends Component {
  config = {
    navigationBarTitleText: "推荐订单",
    window: {
      enablePullDownRefresh: true,
      backgroundTextStyle:"light"//dark：显示刷新动画
    }
  };

  constructor(props) {
    super(...arguments);
    this.state = {
      onList:[],
      orderIds:null,
      expectData:[],
      startIndex:0,
      lastIndex:0,
      //当前页
      pageCode:1,
      //总页数
      totalPageCode:0,
      //每页显示的条数
      pageSize:10,
      offset:0,
      value:'',
      searchBarValue:'',
      recommend:""
    }
    this.userInfo={
      recommend:""
    }
  }
  //获取初始化的数据
  getOrderOnList(){
    Taro.showLoading({title:"加载中"});
    Taro.request({
      url: Api.OrderList+"?pageCode="+this.state.pageCode+"&pageSize="+this.state.pageSize,
      method:"POST",
      header: {
        Accept: "application/json, */*",
        "Content-Type": "application/json"
      },
      data:{
        memberName:this.state.searchBarValue,
        referralCode:this.userInfo.recommend
      }
    }).then(res=>{

      if(res.statusCode == 200){
        Taro.hideLoading();
        if (res.data.data.records!=undefined){
          this.setState({
            onList:res.data.data.records,
            totalPageCode:res.data.data.pages
          });
        }
      }else{
        this.setState({onList:null})
      }
    }).catch(() => {
      Taro.hideLoading();
      Taro.showToast({ icon: 'none',title: "列表加载异常"});
    })
  }
  appendToList(){
    Taro.showLoading({title: '加载中'});
    let url= Api.OrderList;
    let pageCode = this.state.pageCode;
    let totalPageCode = this.state.totalPageCode;
    if(pageCode >= totalPageCode){
      Taro.showToast({title: '没有更多数据了', icon: 'success',duration: 2000});
      return false;
    }
    for(let i = 0;i<10;i++){
      if(pageCode <= totalPageCode){pageCode ++;}
    }
    Taro.request({
      url:url+"?pageCode="+pageCode+"&totalPageCode="+totalPageCode,
      method:'POST',
      header: {
        Accept: "application/json, */*",
        "Content-Type": "application/json"
      },
      data:{
        memberName:this.state.searchBarValue,
        referralCode:this.userInfo.recommend
      }
    }).then(res=>{
      if(res.statusCode == 200){
        Taro.hideLoading();
        this.setState({
          pageCode :pageCode
        });
        if (res.data.data.records!=undefined){
          this.setState({
            onList:this.state.onList.concat(res.data.records),
            totalPageCode:res.data.records.pages
          });
          Taro.showToast({title: '没有更多数据了', icon: 'success',duration: 2000});
        }
      }
    }).catch(() => {
      Taro.hideLoading();
      Taro.showToast({ icon: 'none',title: "列表加载异常"});
    })
  }

  componentDidMount(){
    this.getOrderOnList();

  }
  componentWillMount () { }

  componentWillUnmount () { }

  componentDidShow () {
    try {
      this.userInfo = Taro.getStorageSync('userInfo');
    } catch (e) {
      // Do something when catch error
      console.info(e);
    }
  }

  componentDidHide () { }

  onActionClick () {
    console.log(this.state.searchBarValue);
    this.setState({pageCode :1});
    Taro.showLoading({title:"搜索中"});
    Taro.request({
      url: Api.OrderList+"?pageCode="+this.state.pageCode+"&pageSize="+this.state.pageSize,
      method:"POST",
      data:{
        memberName:this.state.searchBarValue,
        referralCode:this.userInfo.recommend
      },
      header: {
        Accept: "application/json, */*",
        "Content-Type": "application/json"
      }
    }).then(res=>{
      if(res.statusCode == 200){
        Taro.hideLoading();
        if (res.data.data.records!=undefined){
          this.setState({
            onList:res.data.data.records,
            totalPageCode:res.data.data.pages
          });
        }
      }else{
        this.setState({onList:null})
      }
    }).catch(function (err) {
        console.log(err);
    });
  }
  handleSearchBarChange (stateName, value) {
    this.setState({
      [stateName]: value
    })
  }
  /*
  发起结算
  */
  settlement(stateName, value) {
    console.log("settlement");
    console.log("value"+value);
    console.log("stateName"+stateName);
    Taro.request({
      url: Api.Settlement+"?orderid="+stateName,
      method: "GET",
      header: {
        Accept: "application/json, */*",
        "Content-Type": "application/json"
      }
    }).then(res => {
      console.info(res);
      console.info(res.data.msg);
      if (res.statusCode == 200) {
        Taro.showToast({ icon: 'none',title: "发起结算成功"});
        this.setState({pageCode :1});
        this.getOrderOnList();
      }else {
        Taro.showToast({ icon: 'none',title: "发起结算失败"})
      }
    }).catch(() => {
      Taro.showToast({ icon: 'none',title: "发起结算异常"});
    });
  }

  onPullDownRefresh() {
    this.setState({pageCode :1});
    Taro.request({
      url: Api.OrderList+"?pageCode="+this.state.pageCode+"&pageSize="+this.state.pageSize,
      method:"POST",
      data:{
        memberName:this.state.searchBarValue,
        referralCode:this.userInfo.recommend
      },
      header: {
        Accept: "application/json, */*",
        "Content-Type": "application/json"
      }
    }).then(res=>{
      if(res.statusCode == 200){
        Taro.hideLoading();
        if (res.data.data.records!=undefined){
          this.setState({
            onList:res.data.data.records,
            totalPageCode:res.data.data.pages
          });
        }
      }else{
        this.setState({onList:null})
      }
      Taro.stopPullDownRefresh();
    }).catch(function (err) {
      console.log(err);
      Taro.stopPullDownRefresh();
    });

  }

  render() {
    return (
      <View className="reorder" >
        <View>
          <AtSearchBar
            actionName='搜一下'
            value={this.state.searchBarValue}
            onChange={this.handleSearchBarChange.bind(this, 'searchBarValue')}
            onActionClick={this.onActionClick.bind(this)}
          />
          <ScrollView lowerThreshold='10' scrollTop='0' upperThreshold='10' style='height:300px'
                      scroll-y lowerThreshold='30' style='height:100vh' onScrolltolower={this.appendToList.bind(this)}
                      scrollWithAnimation>
            <View className="tabItemContent" >
              {this.state.onList.map((item,index)=>{
                return (
                  <View>
                    <View className='at-col at-col-10 at-col--auto' >
                      <View  style="height: 10px;  background-color: #f5f5f5;" />
                    </View>
                    <View className='at-row at-row--wrap' style="padding: 15px;" key={index}>
                      <View className='at-col at-col-5 at-col--auto'>
                        <Text style='font-size: 1.0rem;color: #111111;font-weight: 700;' >{item.companyName}</Text>
                      </View>
                      <View className='at-col at-col-5 at-row__align--center' >
                        <Text style='font-size: 0.8rem;color: #999999;padding-left: 10px;'>申请时间：{item.orderTime}</Text>
                      </View>
                      <View className='at-col at-col-10 at-col--auto' >
                        <View className='at-col at-col-10 at-col--auto' >
                          <View  style="height: 1px;width:100%; background-color: #f5f5f5;margin-top: 5px;" />
                        </View>
                      </View>
                      {/*<AtDivider content='没有更多了' fontColor='#ed3f14' lineColor='#ed3f14' />*/}
                      <View className='at-col at-col-4 at-col--auto' style="margin-right: 30px;">
                        <View><Text style='font-size: 0.8rem;color: #666666;'>申请额度</Text></View>
                        <View><Text style='font-size: 0.9rem;color: #2D8CF0;padding: 5px;'>{item.orderPayAmount}</Text></View>
                        <View hidden={item.orderStatus === '04'&&item.paymentStatus==='00'?false:true}>
                          <AtButton type='primary' size='small' onClick={this.settlement.bind(this,item.orderid)}>可结算</AtButton>
                        </View>
                      </View>
                      <View  className='at-col at-col-2 at-col--auto' >
                        <View><Text style='font-size: 0.8rem;color: #999999;'>融资进度</Text></View>
                        <View style="display: flex;align-items: center;justify-content: center;">
                          <Image hidden={item.orderStatus === '01'?false:true} style="width: 12px;height: 12px;" className="icon" src={app_status_1}></Image>
                          <Image hidden={item.orderStatus === '02'?false:true} style="width: 12px;height: 12px;" className="icon" src={order_02}></Image>
                          <Image hidden={item.orderStatus === '03'?false:true} style="width: 12px;height: 12px;" className="icon" src={order_03}></Image>
                          <Image hidden={item.orderStatus === '04'?false:true} style="width: 12px;height: 12px;" className="icon" src={order_04}></Image>
                          <Image hidden={item.orderStatus === '05'?false:true} style="width: 12px;height: 12px;" className="icon" src={order_05}></Image>
                          <Text hidden={item.orderStatus === '01'?false:true} style='font-size: 0.8rem;color: #666666;padding: 5px;'>待处理</Text>
                          <Text hidden={item.orderStatus === '02'?false:true} style='font-size: 0.8rem;color: #666666;padding: 5px;'>不符合</Text>
                          <Text hidden={item.orderStatus === '03'?false:true} style='font-size: 0.8rem;color: #666666;padding: 5px;'>审核中</Text>
                          <Text hidden={item.orderStatus === '04'?false:true} style='font-size: 0.8rem;color: #666666;padding: 5px;'>已放款</Text>
                          <Text hidden={item.orderStatus === '05'?false:true} style='font-size: 0.8rem;color: #666666;padding: 5px;'>已拒款</Text>
                        </View>
                      </View>
                      <View className='at-col at-col-2 at-col--auto' >
                        <View ><Text style='font-size: 0.8rem;color: #999999;'>提成金额</Text></View>
                        <View style="display: flex;align-items: center;justify-content: center;">
                          <Image style="width: 12px;height: 12px;" className="icon" src={app_status_2}></Image>
                          <Text hidden={item.paymentStatus === '00'?false:true}  style='font-size: 0.8rem;color: #666666;padding: 5px;'>待更新</Text>
                          <Text hidden={item.paymentStatus === '01'?false:true}  style='font-size: 0.8rem;color: #666666;padding: 5px;'>
                            {item.paymentFee}RMB
                          </Text>
                          <Text hidden={item.paymentStatus === '02'?false:true}  style='font-size: 0.8rem;color: #666666;padding: 5px;'>结算中</Text>
                          <Text hidden={item.paymentStatus === '03'?false:true}  style='font-size: 0.8rem;color: #666666;padding: 5px;'>可结算</Text>
                        </View>
                      </View>
                      <View className='at-col at-col-2 at-col--auto'>
                        <View><Text style='font-size: 0.8rem;color: #999999;'>实际放款</Text></View>
                        <View style="display: flex;align-items: center;justify-content: center;">
                          <Text style='font-size: 0.8rem;color: #666666;padding: 5px;' hidden={item.orderFactPayAmount != 0?false:true}>{item.orderFactPayAmount}W</Text>
                          <Text style='font-size: 0.8rem;color: #666666;padding: 5px;' hidden={item.orderFactPayAmount === 0?false:true}>无</Text>
                        </View>
                      </View>
                    </View>
                  </View>
                )
              })}
            </View>
          </ScrollView>
        </View>
      </View>
    );
  }
}
