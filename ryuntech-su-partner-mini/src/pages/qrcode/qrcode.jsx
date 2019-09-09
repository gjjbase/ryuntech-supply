import Taro, { Component } from '@tarojs/taro'
import { View, Image} from '@tarojs/components'
import DocsHeader from '../../components/doc-header'
import Api from '../../servers/api'

import './qrcode.scss'

export default class Orcode extends Component {

  config = {
    navigationBarTitleText: '我的推荐码'
  }

  constructor(props){
    super(props);
    this.state = {
      openId:'',
      recommend:'',
      src:""
    }
  }

  componentWillMount () { }

  componentDidMount () {

   /* Taro.request({
      url:
        "http://127.0.0.1:9999/api/admin/partner/outFindByOpenId?openId="+this.state.openId,
      method: "GET",
      header: {
        Accept: "application/json, *!/!*",
        "Content-Type": "application/json"
      }
    }).then(res => {
      console.info(res.data.data);
      if(res.statusCode == 200){
        this.setState({
          recommend:res.data.data.recommend
        });
      }
    });*/
  }

  componentWillUnmount () { }

  componentDidShow () {
    const userInfo = Taro.getStorageSync('userInfo');
    this.setState({
      recommend: "推荐码:"+userInfo.recommend
    });
    this.setState({
      src:Api.ImgPath+userInfo.recommend+".png"
    })
    //设置url

  }

  componentDidHide () { }

  render () {
    return (
      <View className='qrcode'>
        <DocsHeader title={this.state.recommend} ></DocsHeader>
        <View style="display: flex;align-items: center;justify-content: center;">
          <Image style="width: 300px;height: 300px;margin:5px;"
                 src={this.state.src}
          />
        </View>
      </View>
    )
  }
}
