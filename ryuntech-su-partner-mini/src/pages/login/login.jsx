import Taro, { Component } from '@tarojs/taro'
import { View,Form,Input,Button,Text} from '@tarojs/components'
import { AtInput, AtForm,AtButton,AtAvatar,AtCountdown ,AtCheckbox } from 'taro-ui'
import './login.scss'
import logo from "../../assets/images/person/logo.png";
import Api from '../../servers/api'

export default class Login extends Component {

  constructor () {
    super(...arguments)
    this.state = {
      phone: '',
      codeValue: '',
      isClose: false,
      count: 5,
      btnDisable: false,
      btnValue: '发送验证码',
      basicInfo: null
    }
  }
  config = {
    navigationBarTitleText: '登录',
    enablePullDownRefresh:false,
  }

  componentWillMount () { }

  componentDidMount () {
    try {
      const value = Taro.getStorageSync('userInfo')
      if (value) {
        Taro.reLaunch({
          url:'../reorder/reorder'
        });
      }
    } catch (e) {
      // Do something when catch error
    }

  }

  componentWillUnmount () { }

  componentDidShow () { }

  componentDidHide () { }

  //注册
  onSubmit (event) {
    //
    Taro.reLaunch({
      url:'../reorder/reorder'
    });
  }
  // 倒计时过程事件
  timeTick(remainingSecond) {
    //发送验证码
    console.log("-----sendCode");
    if (this.state.phone.length<11){
      Taro.showToast({icon: 'none', title: "手机号错误"}).then(r =>{});
      return;
    }
    Taro.request({
      url:
        Api.SendCode+this.state.phone,
      method: "GET",
      header: {
        Accept: "application/json, */*",
        "Content-Type": "application/json"
      },
      fail(e) {
        Taro.showToast({
          icon: 'none',
          title: "发送失败"
        })
      }
    }).then(res => {
      console.info(res);
      if (res.statusCode == 200) {
        setTimeout(() => {
          this.getRemainingSecond();
        }, 1000);
      }else {
        Taro.showToast({
          icon: 'none',
          title: "发送失败"
        })
      }
    });
  }
  handleTabsClick (stateName, value) {
    this.setState({
      [stateName]: value
    })
  }
  getRemainingSecond() {
    let remainingSecond = this.state.count;
    remainingSecond--;

    this.setState({count: remainingSecond});
    this.setState({btnValue: remainingSecond+"秒重新获取"});
    this.setState({btnDisable: true,});
    if (remainingSecond > 0 && !this.state.isClose) {
      this.timeTick(remainingSecond);
    }else {
      clearTimeout(this.timer);
      this.setState({
        btnValue: "发送验证码"
      });
      this.setState({
        btnDisable: false,
      });
    }
  }
  getUserInfo = (userInfo) => {
    console.log('userinfo',userInfo)
    if(userInfo.detail.userInfo){   //同意
      this.setState({
        basicInfo: userInfo.detail.userInfo
      }); //将用户信息存入redux
      Taro.setStorage({key:'userInfo',data:userInfo.detail.userInfo}).then(rst => {  //将用户信息存入缓存中
        // Taro.navigateBack()
        console.log('userinfo',userInfo)
      })
    } else{ //拒绝,保持当前页面，直到同意
    }
  }
  render () {
    return (
      <View className='login'>
        <View className='at-row at-row__justify--around' style='height:150px' >
          <View className='at-col at-col-5' style="display: flex;justify-content: center;align-content: center;align-items: center;" >
            <AtAvatar   circle image={logo} size="large"/>
          </View>
        </View>
        <View style="padding:15px;">
          <AtForm border={false} className="meituan" onSubmit={this.onSubmit.bind(this)}>
            <AtInput
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
              <AtButton type='primary' disabled={this.state.btnDisable} size='small' value={this.state.count} onClick={this.timeTick.bind(this)}>
                {this.state.btnValue}
              </AtButton>
            </AtInput>

            <View style="margin:0px 15px 0px 25px;">
              <AtButton    formType='submit' type='primary'>登录</AtButton>
              {/*<Button open-type='getUserInfo' style="background-color: #2D8CF0;color: #FFFFFF;" onGetUserInfo={this.getUserInfo} > 登录 </Button>*/}
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
