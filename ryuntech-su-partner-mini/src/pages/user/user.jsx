/**
 * Created by Administrator on 2018/8/9 0009.
 */
import Taro, { Component } from '@tarojs/taro'
import { View, Text } from '@tarojs/components'
import { AtAvatar} from "taro-ui"
import myData from "../../assets/images/person/my_data.png"
import myQrcode from "../../assets/images/person/my_qrcode.png"
import partner_01 from "../../assets/images/person/partner_01.png"
import './user.scss'
import Api from '../../servers/api'
export default class User extends Component {
  config = {
    navigationBarTitleText: '我的',
    enablePullDownRefresh:false
  }
  constructor(props){
    super(props);
    this.state = {
      avatarUrl: '',
      nickName: '',
      mobile: '',
      status: '未审核',
      loginUrl: 'false',
      openId:'000',
      stateValue:"none",
      stateStyle:"display: flex;align-items: center;justify-content: center;margin-left:30px;",
      partner:{
        totalLending:"0.00",
        totalFeed:"0.00",
        totalNum:"0",
        totalOrderNum:"0",
        status:"",
      }
    };
    this.userInfo={}
  }
  componentWillMount () {
    console.info("componentWillMount")
  }


  componentWillUnmount () {console.info("componentWillUnmount") }

  componentDidShow () {
    console.info("componentDidShow");
    Taro.getSetting().then(res=>{
        if(res.authSetting["scope.userInfo"]!=undefined){
          this.getInitData();
          return true;
        }else {
          this.setState({
            "nickName":"点击登录账户"
          })
          this.setState({
            "stateStyle":"display: none;align-items: center;justify-content: center;margin-left:30px;"
          })
          this.setState({
            "loginUrl":"../auth/auth"
          })
        }
      }).catch(err=>{
        console.log("err"+err)
      })
  }
  getInitData(){
    try {
      this.userInfo = Taro.getStorageSync('userInfo');
      if (this.userInfo.openId!=null&&this.userInfo.openId!=""){
        this.setState({
          avatarUrl: this.userInfo.avatarUrl
        });
        this.setState({
          nickName: this.userInfo.nickName
        });
        this.setState({
          openId:this.userInfo.openId
        });
        Taro.request({
          url:
            Api.FindByOpenId+"?openId="+this.userInfo.openId,
          method: "GET",
          header: {
            Accept: "application/json, */*",
            "Content-Type": "application/json"
          }
        }).then(res => {
          if(res.statusCode == 200){
            console.info(res);
            this.setState({
              partner:res.data.data
            });
            const userInfos = Taro.getStorageSync('userInfo');
            this.userInfos={
              nickName:userInfos.nickName,
              avatarUrl:userInfos.avatarUrl,
              city:userInfos.city,
              country:userInfos.country,
              gender:userInfos.gender,
              language:userInfos.language,
              province:userInfos.province,
              phone:res.data.data.registerMobile,
              openId:res.data.data.openId,
              status:res.data.data.status,
              recommend:res.data.data.recommend,
              partnerId: res.data.data.partnerId,
              partnerName: res.data.data.partnerName,
              companyName: res.data.data.companyName
            }
            this.setState({
              "stateStyle":"display: flex;align-items: center;justify-content: center;margin-left:30px;"
            })
            this.setState({
              "loginUrl":"false"
            })
            if (res.data.data.status=="00"){
              this.setState({status: "未审核"});
            }else if (res.data.data.status=="01"){
              this.setState({status: "审核通过" });
            }
            Taro.setStorage({key: 'userInfo', data: this.userInfos});
          }
        });
      }
    }catch (e) {
      console.info(e);
    }
  }


  componentDidHide () {

  }


  componentDidMount () {

  }
  navigateToDetail(url){
    if (this.state.partner.status=="00"){
      Taro.showToast({ icon: 'none',title: "请等待管理员审核"});
    }else if (this.state.partner.status=="01"){
      Taro.navigateTo({ url: url })
    }
  }
  navigateToAuth(url){
    if (url!="false"){
      Taro.navigateTo({ url: url })
    }
  }
  render () {
    return (
      <View className="userCenter">
        <View className="userInfo">
          <View className='at-row' >
            <View className='t-col at-col-1 at-col--auto' style="padding-left:30px">
              <AtAvatar circle image={this.state.avatarUrl}  size="large"></AtAvatar>
            </View>
            <View className='t-col at-col-1 at-col--auto' style="display: flex;align-items: center;justify-content: center;" onClick={this.navigateToAuth.bind(this,this.state.loginUrl)}>
              <Text  style='font-size: 1.2rem;color: #FFFFFF;font-weight: 700;padding-left:10px' >{this.state.nickName}</Text>
            </View>
            <View   className='t-col at-col-1 at-col--auto' style={this.state.stateStyle}>
                <Image style="width: 15px;height: 15px;margin:5px;"   src={partner_01}></Image>
                <Text  className="desc" style='font-size: 0.9rem;color: #FFFFFF;font-weight: 700;'>{this.state.status}</Text>
            </View>
          </View>
        </View>
        <View className="order">
          <View className='at-row at-row__justify--center'>
            <View  className='at-col' >
              <View style="display: flex;align-items: center;justify-content: center;padding-top:30px"><Text
                style='font-size: 1.0rem;color: #111111;font-weight: 700;'>{this.state.partner.totalFeed}</Text></View>
              <View style="display: flex;align-items: center;justify-content: center;">
                <Text style='font-size: 0.8rem;color: #666666;'>累计创收</Text>
              </View>
            </View>
            <View  style="width: 1px;margin-top:30px ; background-color: #f5f5f5;" />
            <View  className='at-col'>
              <View style="display: flex;align-items: center;justify-content: center;padding-top:30px"><Text
                style='font-size: 1.0rem;color: #111111;font-weight: 700;'>
                {this.state.partner.totalLending}
              </Text></View>
              <View style="display: flex;align-items: center;justify-content: center;">
                <Text style='font-size: 0.8rem;color: #666666;'>待结算金额</Text>
              </View>
            </View>
            <View  style="width: 1px;margin-top:30px ; background-color: #f5f5f5;" />
            <View  className='at-col'>
              <View style="display: flex;align-items: center;justify-content: center;padding-top:30px"><Text
                style='font-size: 1.0rem;color: #2D8CF0;font-weight: 700;'>{this.state.partner.totalOrderNum}</Text></View>
              <View style="display: flex;align-items: center;justify-content: center;">
                <Text style='font-size: 0.8rem;color: #666666;'>推荐单量</Text>
              </View>
            </View>
            <View  style="width: 1px;margin-top:30px ; background-color: #f5f5f5;" />
            <View  className='at-col'>
              <View style="display: flex;align-items: center;justify-content: center;padding-top:30px"><Text
                style='font-size: 1.0rem;color: #2D8CF0;font-weight: 700;'>{this.state.partner.totalNum}</Text></View>
              <View style="display: flex;align-items: center;justify-content: center;">
                <Text style='font-size: 0.8rem;color: #666666;'>待结算单</Text>
              </View>
            </View>
          </View>
          <View className="discount">
            <View className="cardpon" onClick={this.navigateToDetail.bind(this,"../person/person")}>
              <Image style="width: 15px;height: 15px;margin:5px;"   src={myData}></Image>
              <Text className="desc" style='font-size: 0.8rem;color: #111111;font-weight: 700;'>我的资料</Text>
              <View className="arrow" >></View>
            </View>
           {/* <View className="cardpon" onClick={this.navigateToDetail.bind(this,"../reorder/reorder")}>
              <Image style="width: 15px;height: 15px;margin:5px;"   src={myCommended}></Image>
              <Text className="desc" style='font-size: 0.8rem;color: #111111;font-weight: 700;'>我的银行卡</Text>
              <View className="arrow">></View>
            </View>*/}
            <View className="cardpon" onClick={this.navigateToDetail.bind(this,"../qrcode/qrcode")}>
              <Image style="width: 15px;height: 15px;margin:5px;"   src={myQrcode}></Image>
              <Text className="desc" style='font-size: 0.8rem;color: #111111;font-weight: 700;'>我的推荐码</Text>
              <View className="arrow">></View>
            </View>
          </View>
        </View>
      </View>
    )
  }
}
