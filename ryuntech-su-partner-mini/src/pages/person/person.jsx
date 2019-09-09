import Taro, { Component } from '@tarojs/taro'
import { View, Text } from '@tarojs/components'
import DocsHeader from '../../components/doc-header'
import { AtInput, AtForm,AtButton} from 'taro-ui'
import './person.scss'
import Api from '../../servers/api'

export default class Person extends Component {

  config = {
    navigationBarTitleText: '我的资料'
  }
  constructor () {
    super(...arguments)
    this.state = {
      partnerName: "",
      companyName: "",
      phone: "",
      openId: "",
      partnerId: ""
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

  componentDidShow () {
    this.userInfo = Taro.getStorageSync('userInfo')
     this.setState({
       partnerName: this.userInfo.partnerName,
       companyName: this.userInfo.companyName,
       phone: this.userInfo.phone,
       openId: this.userInfo.openId,
       partnerId: this.userInfo.partnerId
     })
  }

  componentDidHide () { }

  onSubmit (event) {
    console.info(this.userInfo);
    if (this.userInfo.partnerId==null){
      Taro.showToast({icon: 'none', title: "编号为空"})
      return;
    }
    if (this.userInfo.openId==null){
      Taro.showToast({icon: 'none', title: "openId为空"})
      return;
    }
    //开始更新用户信息
    Taro.request({
      url:Api.UpdateUser,
      method: "POST",
      data:{
        partnerId:this.state.partnerId,
        openId:this.state.openId,
        partnerName:this.state.partnerName,
        registerMobile:this.state.phone,
        companyName:this.state.companyName
      },
      header: {
        Accept: "application/json, */*",
        "Content-Type": "application/json"
      }
    }).then(res => {
      if(res.statusCode == 200){
        //更新缓存中的数据
        const values = Taro.getStorageSync('userInfo');
        this.userInfos={
          nickName:values.nickName,
          avatarUrl:values.avatarUrl,
          city:values.city,
          country:values.country,
          gender:values.gender,
          language:values.language,
          province:values.province,
          phone:res.data.data.registerMobile,
          openId:res.data.data.openId,
          status:res.data.data.status,
          recommend:res.data.data.recommend,
          partnerId: res.data.data.partnerId,
          partnerName: res.data.data.partnerName,
          companyName: res.data.data.companyName
        }

        // userInfo.detail.userInfo.operatorname = res.data.data.operatorname;
        Taro.setStorage({key: 'userInfo', data: this.userInfos});
        console.info("userInfo"+JSON.stringify(Taro.getStorageSync('userInfo')))
        Taro.showToast({ icon: 'none',title: "更新成功"});
      }
    });
  }
  handleChange (stateName, value) {
    this.setState({
      [stateName]: value
    })
    return value;
  }
  render () {
    return (
      <View className='person'>
        <DocsHeader title='个人资料' ></DocsHeader>
        <AtForm
          onSubmit={this.onSubmit.bind(this)}
        >
          <AtInput
            border={true}
          name='partnerName'
          type='text'
            maxLength="20"
          title='姓名'
          value={this.state.partnerName}
          onChange={this.handleChange.bind(this, 'partnerName')}
        ></AtInput>
          <AtInput
            name='companyName'
            type='text'
            maxLength="30"
            title='公司名'
            value={this.state.companyName}
            onChange={this.handleChange.bind(this, 'companyName')}
          ></AtInput>
          <AtInput
            name='phone'
            maxLength="11"
            type='phone'
            title='手机号'
            value={this.state.phone}
            onChange={this.handleChange.bind(this, 'phone')}
          ></AtInput>
          <View style="margin:20px;">
            <AtButton   formType='submit' type='primary' >确认更新资料</AtButton>
          </View>
        </AtForm>
      </View>
    )
  }
}
