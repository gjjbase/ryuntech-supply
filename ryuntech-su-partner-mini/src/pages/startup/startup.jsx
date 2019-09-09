import Taro, { Component } from '@tarojs/taro'
import { View, Text } from '@tarojs/components'
import './index.scss'

export default class Startup extends Component {

  config = {
    navigationBarTitleText: '加载中...'
  }

  componentWillMount () {

  }

  componentDidMount () {
    console.info("加载中");
    Taro.showLoading({title: '加载中'});
    Taro.getSetting()
      .then(res=>{
        console.info(res.authSetting["scope.userInfo"]);
        if(res.authSetting["scope.userInfo"]){
          Taro.reLaunch({
            url:'../reorder/reorder'
          });
          return true;
        }else {
          Taro.reLaunch({
            url:'../auth/auth'
          });
        }
      })
      .then(res=>{
        return Taro.getUserInfo();
      })
      .then(res=>{
        /* Taro.setStorage({
           key: 'userInfo',
           data: res.userInfo
         })*/
      })
      .catch(err=>{
        console.log(err)
      })
  }

  componentWillUnmount () { }

  componentDidShow () { }

  componentDidHide () { }

  render () {
    return (
      <View className='startup'>
      </View>
    )
  }
}
