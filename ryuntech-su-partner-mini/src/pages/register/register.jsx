import Taro, { Component } from '@tarojs/taro'
import { View} from '@tarojs/components'
import { AtInput, AtForm,AtButton,AtAvatar,AtCheckbox } from 'taro-ui'
import './register.scss'
import Api from '../../servers/api'

export default class Register extends Component {

  constructor () {
    super(...arguments)
    this.timer=null,
    this.state = {
      avatarUrl:'',
      partnerId:'',
      phone: '',
      codeValue: '',
      realValue: '',
      companyValue: '',
      checkedList: ['true'],
      isClose: true,
      count: 60,
      btnDisable: false,
      btnValue: '发送验证码'
    }
    this.checkboxOption = [{
      value: "list1"
    }]
    this.userInfos={

    }
  }
  config = {
    navigationBarTitleText: '注册',
    enablePullDownRefresh:false,
  }
  componentWillMount () { }

  componentDidMount () {

  }

  componentWillUnmount () { }

  componentDidShow () {
    //从缓存获取用户头像
    try {
      const value = Taro.getStorageSync('userInfo')
      if (value) {
        //判断用户数据是否存在
        this.setState({
          avatarUrl: value.avatarUrl
        });
        this.setState({
          partnerId: value.partnerId
        });
      }
    } catch (e) {
      // Do something when catch error
    }
  }

  componentDidHide () { }

  //注册
  onSubmit (event) {
    if (this.state.phone==""||this.state.phone.length!=11){
      Taro.showToast({ icon: 'none',title: "手机号错误"});
      return;
    }
    if (this.state.codeValue==""){
      Taro.showToast({ icon: 'none',title: "验证码不能为空"});
      return;
    }
    if (this.state.realValue==""){
      Taro.showToast({ icon: 'none',title: "真实姓名不能为空"});
      return;
    }
    if (this.state.companyValue==""){
      Taro.showToast({ icon: 'none',title: "公司名不能为空"});
      return;
    }
    if (this.state.checkedList[0]!="true"){
      Taro.showToast({ icon: 'none',title: "请先同意协议"});
      return;
    }
    Taro.request({
      url:Api.RegisterUpdateUser,
      method: "POST",
      data:{
        registerMobile: this.state.phone,
        codeValue: this.state.codeValue,
        partnerName: this.state.realValue,
        companyName: this.state.companyValue,
        partnerId: this.state.partnerId,
      },
      header: {
        Accept: "application/json, */*",
        "Content-Type": "application/json"
      }
    }).then(res => {
      console.info(res)
      if (res.statusCode == 200) {
        if (res.data.tcode != 200){
          Taro.showToast({ icon: 'none',title: res.data.msg});
          return;
        }
        if (res.data.data!=undefined){
          const value = Taro.getStorageSync('userInfo');
          console.info("res.data.data"+res.data.data)
          this.userInfos={
            nickName:value.nickName,
            avatarUrl:value.avatarUrl,
            city:value.city,
            country:value.country,
            gender:value.gender,
            language:value.language,
            province:value.province,
            phone:res.data.data.registerMobile,
            openId:res.data.data.openId,
            status:res.data.data.status,
            recommend:res.data.data.recommend,
            partnerId: res.data.data.partnerId,
            partnerName: res.data.data.partnerName,
            companyName: res.data.data.companyName
          }
          Taro.setStorage({key: 'userInfo', data: this.userInfos});
          Taro.reLaunch({url:'../reorder/reorder'});
        }else {
          Taro.showToast({ icon: 'none',title: "注册异常"});
        }
      }
    }).catch(function (err) {
      console.info(err);
      Taro.showToast({ icon: 'none',title: "注册异常"});
    });
  }

  handleCheckboxChange (value) {
    this.setState({
      checkedList: value
    })
  }
  // 倒计时过程事件
  timeTick() {
    //发送验证码
    console.log("-----sendCode");
    if(this.state.btnDisable){
      Taro.showToast({icon: 'none', title: "验证码已发送"}).then(r =>{});
      return;
    }
    if (this.state.phone.length<11){
      Taro.showToast({icon: 'none', title: "手机号错误"}).then(r =>{});
      return;
    }
    this.setState({btnDisable: true});
    Taro.request({
      url: Api.SendCode+this.state.phone,
      method: "GET",
      header: {
        Accept: "application/json, */*",
        "Content-Type": "application/json"
      }
    }).then(res => {
      console.info(res);
      if (res.statusCode == 200) {
        if (res.data.data.code=="OK"){
          this.timer =  setInterval(()=>{
            this.state.count--;
            this.setState({btnValue: this.state.count+"秒重新获取"});
            if(this.state.count<=0){
              this.setState({btnValue: "发送验证码"});
              this.setState({btnDisable: false,});
              clearInterval(this.timer);
            }
          }, 1000);
        }else {
          Taro.showToast({ icon: 'none',title: "发送失败"})
        }
      }else {
        Taro.showToast({ icon: 'none',title: "发送失败"})
      }
    }).catch(() => {
      Taro.showToast({ icon: 'none',title: "发送异常"});
    });
  }
  handleTabsClick (stateName, value) {
    this.setState({
      [stateName]: value
    })
  }
  render () {
    return (
      <View className='register'>
        <View className='at-row at-row__justify--around' style='height:150px' >
          <View className='at-col at-col-5' style="display: flex;justify-content: center;align-content: center;align-items: center;" >
            <AtAvatar   circle image={this.state.avatarUrl} size="large"/>
          </View>
        </View>
        <View style="padding:15px;">
          <AtForm border={false} className="meituan" onSubmit={this.onSubmit.bind(this)} >
            <AtInput
              focus={true}
              border={false}
              type='phone'
              placeholder='请输入手机号码'
              value={this.state.phone}
              onChange={this.handleTabsClick.bind(this, 'phone')}
            />
            <AtInput
              border={false}
              clear
              type='text'
              maxLength='4'
              placeholder='请输入验证码'
              value={this.state.codeValue}
              onChange={this.handleTabsClick.bind(this, 'codeValue')}>
              <AtButton type='primary'  size='small' value={this.state.count} onClick={this.timeTick.bind(this)}>
                {this.state.btnValue}
              </AtButton>
            </AtInput>
            <AtInput
              border={false}
              name='realName'
              type='text'
              placeholder='请输入真实姓名'
              maxLength='20'
              value={this.state.realValue}
              onChange={this.handleTabsClick.bind(this, 'realValue')}/>
            <AtInput
              border={false}
              name='companyName'
              type='text'
              maxLength='50'
              placeholder='请输入公司名'
              value={this.state.companyValue}
              onChange={this.handleTabsClick.bind(this, 'companyValue')}/>
            <View className='at-row' style="margin-left:15px;">
              <View className='at-col at-col-1 at-col--auto'>
                <AtCheckbox
                border={false}
                options={this.checkboxOption}
                selectedList={this.state.checkedList}
                onChange={this.handleCheckboxChange.bind(this)}
              /></View>
              <View className='at-col at-col-1 at-col--auto'
                    style="display: flex;justify-content: center;align-content: center;align-items: center;font-size: 0.8rem;">
                我已阅读并同意注册协议
              </View>
            </View>

            <View style="margin:0px 15px 0px 25px;">
              <AtButton    formType='submit' type='primary'>注册</AtButton>
            </View>
          </AtForm>
        </View>
        <View className="copyright">
            <View className="company">© 睿云科技 客服电话：400-670-5335</View>
          </View>
      </View>
    )
  }
}
