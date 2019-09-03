<template>
  <el-container style="background-color: #FFFFFF">
    <el-header style="height: 45px">
      <el-row>
        <el-col :span="24" >
          <div class="grid-content bg-purple-dark" style="padding:10px;font-size: 1.2rem;display: flex;align-items: center;justify-content: center;">
            <strong>完善信息</strong>
          </div>
        </el-col>
      </el-row>
    </el-header>
    <div class="link-top2"></div>
    <div class="link-top"></div>
    <el-main>
      <el-form ref="form" :rules="rules" :model="form" status-icon label-position="right" label-width="80px">

        <el-form-item label="公司名:" prop="companyName" label-width="120px">
          <el-input style="border-width: 0px !important;" maxlength="20" v-model="form.companyName" placeholder="姓名"></el-input>
        </el-form-item>
        <div class="link-top2"></div>
        <el-form-item label="手机号:" prop="mobile" label-width="120px">
          <el-input style="border-width: 0px !important;" maxlength="20" v-model="form.mobile" placeholder="手机号"/>
        </el-form-item>
        <div class="link-top2"></div>
        <el-form-item style="border-width: 0px !important;" label="验证码:" prop="code" label-width="120px">
          <el-row>
            <el-col :span="12"><el-input maxlength="20"  v-model="form.code" placeholder="验证码"></el-input> </el-col>
            <el-col :span="12"><el-button style="margin-left: 3px;" type="primary"  :disabled = "dis" @click="sendCode()" plain >{{this.butValue}}</el-button></el-col>
          </el-row>
        </el-form-item>
        <div class="link-top2"></div>
        <el-form-item label="城市:" prop="city" label-width="120px">
          <el-select
            v-model="sheng"
            @change="choseProvince"
            placeholder="省级地区">
            <el-option
              v-for="item in province"
              :key="item.id"
              :label="item.value"
              :value="item.id">
            </el-option>
          </el-select>
          <el-select
            v-model="form.city"
            placeholder="市级地区">
            <el-option
              v-for="item in shi1"
              :key="item.id"
              :label="item.value"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <div class="link-top2"></div>
        <el-form-item  label="贷款额度:"  prop="orderPayAmount" label-width="120px">
          <el-select v-model="form.orderPayAmount" placeholder="请选择贷款额度" >
            <el-option label="请输入贷款额度" value="0"></el-option>
            <el-option label="10W-30W" value="10W-30W"></el-option>
            <el-option label="30W-50W" value="30W-50W"></el-option>
          </el-select>
        </el-form-item>
        <div class="link-top2"></div>
      </el-form>
    </el-main>
    <el-footer style="padding:15px;display: flex;align-items: center;justify-content: center;">
      <el-button type="primary"  style="width: 350px;height: 45px;font-size: 1.0rem;" @click="onSubmit('form')"> 提交</el-button>
    </el-footer>
  </el-container>
</template>

<script>
    import {save,sendCode} from '@/api/member/finance'
    import { Message } from 'element-ui'
    import {parseTime} from '@/utils/index'
    import axios from 'axios'
    import adddate from "../../../static/json/map.json";

    export default {
        //父组件向子组件传值，通过props获取。
        //一旦父组件改变了`sonData`对应的值，子组件的`sonData`会立即改变，通过watch函数可以实时监听到值的变化
        //`props`不属于data，但是`props`中的参数可以像data中的参数一样直接使用
        props: ['sonData'],
        data() {
            return {
                dis: false,
                mapJson:'../static/json/map.json',
                adddate:adddate,
                province:'',
                sheng: '',
                shi1: [],
                city:'',
                block:'',
                count: 60,
                butValue: "获取验证码",
                isButtonDisabled: true,
                isClose:false,
                timer:null,
                sendAuthCode:true,
                form: {
                    //推荐码
                    referralCode:""
                },
                rules: {
                }
            }
        },
        watch: {
            'sonData': function (newVal, oldVal) {

            }
        },
        methods: {
            _notify(message, type) {
                this.$message({
                    message: message,
                    type: type
                })
            },
            clearForm() {
                this.form.companyName = null;
                this.form.mobile = null;
                this.form.code = null;
                this.form.city = null;
                this.form.sheng = null;
                this.orderPayAmount = null;
                this.sendAuthCode = true;
                this.dis=false;
                this.butValue="发送验证码";
                this.count=60;
                clearInterval(this.timer);
            },
            handleClose() {
                this.clearForm();
            },
            sendCode(){
                if (this.form.mobile ==undefined||this.form.mobile.length<11){
                    this._notify("请填写正确的手机号", 'error')
                    return;
                }
                sendCode(this.form.mobile).then(response => {
                    //开启定时器
                    this.timer =  setInterval(()=>{
                        this.count--;
                        this.dis=true;
                        this.butValue=this.count+"秒重新获取";
                        if(this.count<=0){
                            this.sendAuthCode = true;
                            this.butValue="发送验证码";
                            this.count=60;
                            this.dis=false;
                            clearInterval(this.timer);
                        }
                    }, 1000);
                })

            },

            onSubmit(form) {
                if(this.form.companyName==undefined){
                    this._notify("请填写公司名", 'error')
                    return false;
                }
                if(this.form.mobile==undefined){
                    this._notify("请填写手机号", 'error')
                    return false;
                }
                if(this.form.code==undefined){
                    this._notify("请填写手机号", 'error')
                    return false;
                }
                if(this.form.city==undefined){
                    this._notify("请填写手机号", 'error')
                    return false;
                }
                if(this.form.orderPayAmount==undefined){
                    this._notify("请填写手机号", 'error')
                    return false;
                }
                save(this.form).then(response => {
                    console.info(response)
                    if (response.tcode === 200) {
                        this._notify(response.data, 'success');
                        this.clearForm();
                    } else {
                        if (response.tcode===1010){
                            this.form.code = null;
                        }
                        if (response.tcode===1011){
                            this.form.mobile = null;
                        }
                        this._notify(response.msg, 'error');
                    }
                }).catch(() => {
                    this._notify('申请异常', 'error')
                });


            },
            getCityData(){
                var that = this
                var data = this.adddate;
                that.province = []
                that.city = []
                that.block = []
                // 省市区数据分类
                for (var item in data) {
                    if (item.match(/0000$/)) {//省
                        that.province.push({id: item, value: data[item], children: []})
                    } else if (item.match(/00$/)) {//市
                        that.city.push({id: item, value: data[item], children: []})
                    } else {//区
                        that.block.push({id: item, value: data[item]})
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
            choseProvince(e){
                for (var index2 in this.province) {
                    if (e === this.province[index2].id) {
                        this.shi1 = this.province[index2].children
                        this.form.city = this.province[index2].children[0].value
                    }
                }
            }
        },
        created(){
            this.form.referralCode = this.$route.query.referralCode;
            this.getCityData();
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
  input{
    border-width: 0px !important;
  }
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


