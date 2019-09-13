<template style="background-color: #5197EC;">

  <el-container style="background-color: #5197EC;">

    <el-header style="height: 50px;background-color: white;position: relative;">
      <el-row>
        <el-col :span="24">
          <div class="grid-content bg-purple-dark" style="padding:10px;font-size: 1.2rem;display: flex;align-items: center;justify-content: center;">
            <strong>提交需求</strong>
          </div>
        </el-col>
      </el-row>
    </el-header>

    <img style="float:left;width: 100%;padding-top: 50px;position: absolute" src="../../assets/backgroupfin.png">
    <div style="float:left;position: relative;background: url(../../assets/liucheng.png)">

      <img style="float:left;width: 40%;padding: 15px" src="../../assets/logo_2.png">
    </div>
    <el-main>
      <el-form ref="form" style="position: relative;background-color: #2562E9;margin-top:60px;padding: 30px;border-radius:5px" :rules="rules" :model="form" status-icon label-position="right" label-width="80px">
        <strong style="color:white;padding:15px;display: flex;align-items: center;justify-content: center;font-size: 30px">融资申请</strong>

        <el-input
          v-model="form.companyName"
          placeholder="请输入您的公司名"
        >
          <i slot="prefix" class="el-input__icon el-icon-search" />
        </el-input>
        <el-input
          v-model="form.mobile"
          style="margin-top: 20px"
          placeholder="请输入您的手机号"
        >
          <i slot="prefix" class="el-input__icon el-icon-phone" />
        </el-input>
        <el-row style="margin-top: 20px">
          <el-col :span="12">
            <el-input v-model="form.code" maxlength="20" placeholder="验证码">
              <i slot="prefix" class="el-input__icon el-icon-search" />
            </el-input>
          </el-col>
          <el-col :span="12"><el-button style="margin-left: 3px;color: #FFA60C" type="primary" :disabled="dis" plain @click="sendCode()">{{ this.butValue }}</el-button></el-col>
        </el-row>
        <el-input
          v-model="form.city"
          style="margin-top: 20px"
          placeholder="请输入您的所在城市"
        >
          <i slot="prefix" class="el-input__icon el-icon-search" />
        </el-input>
        <el-select v-model="form.orderPayAmount" style="margin-top: 20px" placeholder="请选择融资额度">
          <i slot="prefix" class="el-input__icon el-icon-goods" />
          <el-option label="请输入融资额度" value="0" />
          <el-option label="10W-30W" value="10W-30W" />
          <el-option label="30W-50W" value="30W-50W" />
          <el-option label="100W以上" value="100W以上" />
        </el-select>

        <el-footer style="padding:15px;display: flex;align-items: center;justify-content: center;margin-top: 20px">
          <el-button type="primary" style="background-color: #F8C415;color:white;width: 350px;height: 45px;font-size: 1.0rem;" @click="onSubmit('form')">确认申请</el-button>
        </el-footer>
      </el-form>
      <!--<div  style="background-color: #2562E9;margin-top:20px;padding: 20px;border-radius:5px" >
        <strong style="color:white;padding:15px;display: flex;align-items: center;justify-content: center;font-size: 15px">融资流程</strong>
        <img  style="width: 100%" src="../../assets/liucheng.jpg">
        &lt;!&ndash;<el-row :gutter="20">
          <el-col :span="20">
              <img  style="width: 100%" src="../../assets/liucheng.png">
          </el-col>
        </el-row>&ndash;&gt;
      </div>-->
      <img style="width: 100%;margin-top: 20px;" src="../../assets/liucheng.png">
      <img style="width: 100%;margin-top: 20px;" src="../../assets/fengxiang.png">
    </el-main>

  </el-container>
</template>

<script>
import { save, sendCode } from '@/api/member/finance'
import { Message } from 'element-ui'
import { parseTime } from '@/utils/index'
import axios from 'axios'
import adddate from '../../../static/json/map.json'

export default {
  // 父组件向子组件传值，通过props获取。
  // 一旦父组件改变了`sonData`对应的值，子组件的`sonData`会立即改变，通过watch函数可以实时监听到值的变化
  // `props`不属于data，但是`props`中的参数可以像data中的参数一样直接使用
  props: ['sonData'],
  data() {
    return {
      dis: false,
      mapJson: '../static/json/map.json',
      adddate: adddate,
      province: '',
      sheng: '',
      shi1: [],
      city: '',
      block: '',
      count: 60,
      butValue: '获取验证码',
      isButtonDisabled: true,
      isClose: false,
      timer: null,
      sendAuthCode: true,
      form: {
        // 推荐码
        referralCode: ''
      },
      rules: {
      }
    }
  },
  watch: {
    'sonData': function(newVal, oldVal) {

    }
  },
  created() {
    this.form.referralCode = this.$route.query.referralCode
    this.getCityData()
  },
  methods: {
    _notify(message, type) {
      this.$message({
        message: message,
        type: type
      })
    },
    clearForm() {
      this.form.companyName = null
      this.form.mobile = null
      this.form.code = null
      this.form.city = null
      this.form.sheng = null
      this.orderPayAmount = '0'
      this.sendAuthCode = true
      this.dis = false
      this.butValue = '发送验证码'
      this.count = 60
      clearInterval(this.timer)
    },
    handleClose() {
      this.clearForm()
    },
    sendCode() {
      if (this.form.mobile == undefined || this.form.mobile.length < 11) {
        this._notify('请填写正确的手机号', 'error')
        return
      }
      sendCode(this.form.mobile).then(response => {
        // 开启定时器
        this.timer = setInterval(() => {
          this.count--
          this.dis = true
          this.butValue = this.count + '秒重新获取'
          if (this.count <= 0) {
            this.sendAuthCode = true
            this.butValue = '发送验证码'
            this.count = 60
            this.dis = false
            clearInterval(this.timer)
          }
        }, 1000)
      })
    },

    onSubmit(form) {
      if (this.form.companyName == undefined) {
        this._notify('请填写公司名', 'error')
        return false
      }
      if (this.form.mobile == undefined) {
        this._notify('请填写手机号', 'error')
        return false
      }
      if (this.form.code == undefined) {
        this._notify('请填写验证码', 'error')
        return false
      }
      if (this.form.city == undefined) {
        this._notify('请填写城市', 'error')
        return false
      }
      if (this.form.orderPayAmount == undefined) {
        this._notify('请填写融资额度', 'error')
        return false
      }
      save(this.form).then(response => {
        console.info(response)
        if (response.tcode === 200) {
          this._notify(response.data, 'success')
          this.clearForm()
        } else {
          if (response.tcode === 1010) {
            this.form.code = null
          }
          if (response.tcode === 1011) {
            this.form.mobile = null
          }
          this._notify(response.msg, 'error')
        }
      }).catch(() => {
        this._notify('申请异常', 'error')
      })
    },
    getCityData() {
      var that = this
      var data = this.adddate
      that.province = []
      that.city = []
      that.block = []
      // 省市区数据分类
      for (var item in data) {
        if (item.match(/0000$/)) { // 省
          that.province.push({ id: item, value: data[item], children: [] })
        } else if (item.match(/00$/)) { // 市
          that.city.push({ id: item, value: data[item], children: [] })
        } else { // 区
          that.block.push({ id: item, value: data[item] })
        }
      }
      // 分类市级
      for (var index in that.province) {
        for (var index1 in that.city) {
          if (that.province[index].id.slice(0, 2) === that.city[index1].id.slice(0, 2)) {
            that.province[index].children.push(that.city[index1])
          }
        }
      }
    },
    choseProvince(e) {
      for (var index2 in this.province) {
        if (e === this.province[index2].id) {
          this.shi1 = this.province[index2].children
          this.form.city = this.province[index2].children[0].value
        }
      }
    }
  }
}
</script>

<style lang="css">
  .line {
    text-align: center;
  }
  .link-top {
    width: 100%;
    height: 1px;
    border-top: solid #f8faf9 8px;
  }
  .link-top2 {
    width: 100%;
    height: 1px;
    border-top: solid #f8faf9  1px;
  }
  /*input{
    border-width: 0px !important;
  }*/
  .el-form-item__label {
    text-align: left;
    font-size: 15px;
  }
  .el-input__inner {
    font-size: 15px;
  }
  .el-form-item {
     margin-bottom: 6px;
     margin-top: 6px;
  }

</style>

