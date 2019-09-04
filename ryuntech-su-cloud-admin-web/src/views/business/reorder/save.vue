<template>
  <el-dialog :title="dialogTitle" :before-close="handleClose" :visible.sync="dialogVisible" width="40%">
    <el-form ref="form" :rules="rules" :model="form" status-icon label-position="right" label-width="80px">
      <el-form-item v-if="form.orderid != null" label="订单编号" prop="id" label-width="120px">
        <el-input v-model="form.orderid" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="客户名称" prop="memberName" label-width="120px">
        <el-input v-model="form.memberName" placeholder="请输入客户名称"></el-input>
      </el-form-item>
      <el-form-item label="公司名称" prop="companyName" label-width="120px">
        <el-input v-model="form.companyName" placeholder="请输入公司名称" maxlength="60"></el-input>
      </el-form-item>
      <el-form-item label="申请时间" prop="orderTime" label-width="120px">
        <el-date-picker v-model="form.orderTime" type="datetime" placeholder="选择日期时间" :disabled="true"
                        value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      </el-form-item>

      <el-form-item label="城市:" prop="city" label-width="120px">
        <el-select v-model="form.city"   placeholder="请选择城市">
          <el-option label="请输入城市" value="0"></el-option>
          <el-option label="深圳" value="深圳"></el-option>
          <el-option label="广州" value="广州"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="贷款额度"  prop="orderPayAmount" label-width="120px">
        <el-select v-model="form.orderPayAmount" placeholder="请选择贷款额度" >
          <el-option label="请输入贷款额度" value="0"></el-option>
          <el-option label="10W-30W" value="10W-30W"></el-option>
          <el-option label="30W-50W" value="30W-50W"></el-option>
          <el-option label="100W以上" value="100W以上"></el-option>
        </el-select>
      </el-form-item>


      <el-form-item label="实际到账金额" prop="orderFactPayAmount" label-width="120px">
        <el-input type="number" v-model="form.orderFactPayAmount" placeholder="请输入实际到账金额"></el-input>
      </el-form-item>

      <el-form-item label="营业执照号" prop="companyName" label-width="120px">
        <el-input v-model="form.idcartNumber" placeholder="请输入营业执照号"></el-input>
      </el-form-item>
      <el-form-item label="执照注册日期" prop="bussinessRegister" label-width="120px">
        <el-date-picker v-model="form.bussinessRegister" type="datetime" placeholder="选择日期时间"
                        value-format="yyyy-MM-dd" format="yyyy-MM-dd"></el-date-picker>
      </el-form-item>
      <el-form-item label="年纳税金额" prop="payTaxes" label-width="120px">
        <el-select v-model="form.payTaxes" placeholder="请选择年纳税额">
          <el-option label="请输入年纳税额" value="0"></el-option>
          <el-option label="1W-3W" value="1"></el-option>
          <el-option label="3W-5W" value="2"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="年开发票金额" prop="annualInvoice" label-width="120px">
        <el-select v-model="form.annualInvoice" placeholder="请选择年开发票金额">
          <el-option label="请输入年开发票金额" value="0"></el-option>
          <el-option label="10W-30W" value="1"></el-option>
          <el-option label="30W-50W" value="2"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="房产信息" prop="houseType" label-width="120px"   >
        <el-radio-group v-model="form.houseType" >
          <el-radio label="按揭房" value="0"></el-radio>
          <el-radio label="全款房" value="1"></el-radio>
        </el-radio-group>
        <el-form-item label="" prop="houseAddressType" v-if="form.houseType == '1'">
          <el-radio-group v-model="form.houseAddressType">
            <el-radio label="本地房产" value="0"></el-radio>
            <el-radio label="外地房产" value="1"></el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form-item>

      <el-form-item label="车产信息" prop="carType" label-width="120px" >
        <el-radio-group v-model="form.carType"  >
          <el-radio label="按揭车" value="0"></el-radio>
          <el-radio label="全款车" value="1"></el-radio>
        </el-radio-group>
        <el-form-item label="" prop="carAddressType"  v-if="this.form.carType==1" >
          <el-radio-group v-model="form.carAddressType">
            <el-radio label="本地车" value="0"></el-radio>
            <el-radio label="外地车" value="1"></el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form-item>
      <el-form-item label="推荐码" prop="orderMemo" label-width="120px">
        <el-input maxlength="10" v-model="form.referralCode" placeholder="推荐码"></el-input>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose">
        退出
      </el-button>
      <el-button type="primary" @click="onSubmit('form')">
        提交
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {save, edit } from '@/api/business/reorder'
  import {parseTime} from '@/utils/index'

  export default {
    //父组件向子组件传值，通过props获取。
    //一旦父组件改变了`sonData`对应的值，子组件的`sonData`会立即改变，通过watch函数可以实时监听到值的变化
    //`props`不属于data，但是`props`中的参数可以像data中的参数一样直接使用
    props: ['sonData'],

    data() {
      return {
        dialogVisible: false,
        dialogTitle: '详情',
        imgURL: '',
        form: {
            orderid: '',
            memberName: '',
            companyName: '',
            orderTime: '',
            city: '',
            orderPayAmount: '',
            bussinessRegister: '',
            payTaxes: '',
            annualInvoice: '',
            houseType: '',
            carType: '',
            orderMemo: ''
        },
        rules: {
            orderid: [{required: true, trigger: 'blur', message: '请输入订单编号'}],
            memberName: [{required: true, trigger: 'blur', message: '请输入客户名称'}],
            companyName: [{required: true, trigger: 'blur', message: '请输入公司名称'}],
            createTime: [{required: true, trigger: 'blur', message: '请选择创建时间'}],
            orderPayAmount: [{required: true, trigger: 'blur', message: '请选择申请金额'}],
            idcartNumber: [{required: true, trigger: 'blur', message: '请输入执照号'}]
        },
      }
    },
    watch: {
      'sonData': function (newVal, oldVal) {
        this.form = newVal
        this.imgURL = this.form.avatar
        this.dialogVisible = true
        if (newVal.orderid != null) {
          this.dialogTitle = '修改'
        } else {
          this.dialogTitle = '详情'
        }
      },
    },
    methods: {
      _notify(message, type) {
        this.$message({
          message: message,
          type: type
        })
      },
      clearForm() {
        this.form.orderid = null
        this.form.memberName = null
        this.form.companyName = null
        this.form.orderTime = null
        this.form.city = null
        this.form.orderPayAmount = null
        this.form.bussinessRegister = null
        this.form.payTaxes = null
        this.form.annualInvoice = null
        this.form.houseType = null
        this.form.carType = null
        this.form.orderMemo = null
      },
      handleClose() {
        this.clearForm();
        this.dialogVisible = false
      },
      onSubmit(form) {
        this.$refs[form].validate((valid) => {
          if (valid) {
            if (this.form.orderid === null) {
              save(this.form).then(response => {
                if (response.tcode === 200) {
                  this._notify(response.msg, 'success')
                  this.clearForm()
                  this.$emit('sonStatus', true)
                  this.dialogVisible = false
                } else {
                  this._notify(response.msg, 'error')
                }
              })
            } else {
              edit(this.form).then(response => {
                if (response.tcode === 200) {
                  this._notify(response.msg, 'success')
                  this.clearForm()
                  this.$emit('sonStatus', true)
                  this.dialogVisible = false
                } else {
                  this._notify(response.msg, 'error')
                }
              })
            }
          } else {
            this.$message('error submit!!')
            return false;
          }
        });
      }
    }
  }
</script>



