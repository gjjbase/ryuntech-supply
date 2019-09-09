import Taro, { Component } from '@tarojs/taro'
import Reorder from './pages/reorder'

import './app.scss'
import 'taro-ui/dist/style/index.scss'
import "taro-ui/dist/style/components/flex.scss";
import "taro-ui/dist/style/components/nav-bar.scss";
import "taro-ui/dist/style/components/icon.scss";

// 如果需要在 h5 环境中开启 React Devtools
// 取消以下注释：
// if (process.env.NODE_ENV !== 'production' && process.env.TARO_ENV === 'h5')  {
//   require('nerv-devtools')
// }

class App extends Component {

  config = {
    pages: [
      'pages/reorder/reorder',
      'pages/startup/startup',
      'pages/auth/auth',
      'pages/register/register',
      'pages/login/login',
      'pages/user/user',
      'pages/person/person',
      'pages/qrcode/qrcode'
    ],
    window: {
      backgroundTextStyle: 'dark',
      navigationBarBackgroundColor: '#2D8CF0',
      enablePullDownRefresh: true,
      navigationBarTextStyle: "white",
    },
    tabBar: {
      color: "#333",
      selectedColor: "#2D8CF0",
      backgroundColor: "#FFFFF",
      borderStyle: "black",
      list: [
        {
          pagePath: "pages/reorder/reorder",
          text: "推荐订单",
          iconPath: "./assets/images/order/index.png",
          selectedIconPath: "./assets/images/order/index_focus.png"
        },
        {
          pagePath: "pages/user/user",
          text: "我的",
          iconPath: "./assets/images/person/person.png",
          selectedIconPath: "./assets/images/person/person_focus.png"
        }
      ]
    }
  }
  componentWillMount() {

  }
  componentDidMount () {
    console.info("componentDidMount")
  }

  componentDidShow () {console.info("componentDidShow")}

  componentDidHide () {console.info("componentDidHide")}

  componentDidCatchError () {}

  // 在 App 类中的 render() 函数没有实际作用
  // 请勿修改此函数
  render () {
    return (
      <Reorder />
    )
  }
}

Taro.render(<App />, document.getElementById('app'))
