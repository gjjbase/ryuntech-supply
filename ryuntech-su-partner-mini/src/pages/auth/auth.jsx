import Taro, { Component } from '@tarojs/taro'
import { View, Text } from '@tarojs/components'
import './auth.scss'
import logo from "../../assets/images/person/logo.png";
import {AtAvatar} from 'taro-ui'
import Api from '../../servers/api'


export default class Auth extends Component {

  config = {
    navigationBarTitleText: '授权'
  }
  constructor () {
    super(...arguments)
    this.state = {

    }
    this.userInfos = {
      nickName:"",
      avatarUrl:"",
      city:"",
      country:"",
      gender:"",
      language:"",
      province:"",
      phone:"",
      openId:"",
      status:"",
      recommend:"",
      partnerId:"",
      partnerName: "",
      companyName: ""
    }
  }

  componentWillMount () { }

  componentDidMount () {
  }

  componentWillUnmount () { }

  componentDidShow () { }

  componentDidHide () { }
  getUserInfo = (userInfo) => {
    if(userInfo.detail.userInfo){   //同意
      this.setState({
        basicInfo: userInfo.detail.userInfo
      });
      Taro.showLoading({title:"授权中"});
      //将用户信息存入redux
      Taro.setStorage({key:'userInfo',data:userInfo.detail.userInfo}).then(rst => {  //将用户信息存入缓存中
        Taro.login().then(response => {
            return Taro.request({
               url:Api.AuthLogin,
              method:'POST',
               data: {
                 codeValue: response.code,
                 nickname: userInfo.detail.userInfo.nickName,
                 sex:userInfo.detail.userInfo.gender,
                 avatar:userInfo.detail.userInfo.avatarUrl,
                 provinceNo:userInfo.detail.userInfo.province,
                 city:userInfo.detail.userInfo.city
               }
            }).then(res => {
                if (res.statusCode == 200) {
                  console.log("res"+res);
                  Taro.hideLoading();
                  //判断是否存在openidI
                  if (res.data.data.openId == "") {
                    Taro.showToast({title: '用户标识不存在，请重试!', icon: 'none'})
                  } else {
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
                        companyName: res.data.data.companyName,
                 /*     realName: '',
                      companyName: '',
                      phone: ''*/
                    }

                    // userInfo.detail.userInfo.operatorname = res.data.data.operatorname;
                    Taro.setStorage({key: 'userInfo', data: this.userInfos});
                    console.info("userInfo"+JSON.stringify(Taro.getStorageSync('userInfo')))
                    console.info("registerMobile"+res.data.data.registerMobile)

                    if (res.data.data.registerMobile == ""||res.data.data.registerMobile ==null) {
                      //不存在，去往注册页面
                      Taro.reLaunch({url: '../register/register'});
                    } else {
                      Taro.reLaunch({url: '../reorder/reorder'});
                    }
                  }
                }
              })
          }).catch(err => {
            console.log(err);
            Taro.showToast({title: '发生错误，请重试!',icon: 'none'})
          });
      })
    } else{ //拒绝,保持当前页面，直到同意

    }
  }

  back() {
    Taro.navigateBack();
  }
  render () {
    return (
      <View className='index'>
        <View className='at-row at-row__justify--around' style='height:150px' >
          <View className='at-col at-col-5' style="display: flex;justify-content: center;align-content: center;align-items: center;" >
            <AtAvatar   circle image={logo} size="large"/>
          </View>
        </View>
        <View className='content'>
          <View style='font-size: 1.0rem;color: #111111;font-weight: 700;margin-bottom:30px;'>睿推客申请获取以下权限</View>
          <Text>获得你的公开信息(昵称，头像等)</Text>
        </View>
        <View style="margin:0px 15px 0px 25px;">
          <Button open-type='getUserInfo' style="background-color: #2D8CF0;color: #FFFFFF;" onGetUserInfo={this.getUserInfo} > 授权登录 </Button>
        </View>
         <View style="margin:20px 15px 0px 25px;">
        <Button type="default" size="default" onClick={this.back.bind()} >拒绝</Button>
      </View>
      </View>
    )
  }
}
