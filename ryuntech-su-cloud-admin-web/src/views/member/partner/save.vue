<template>
  <el-dialog :title="dialogTitle" :before-close="handleClose" :visible.sync="dialogVisible" width="40%">
    <el-form ref="form" :rules="rules" :model="form" status-icon label-position="right" label-width="80px">
      <el-form-item v-if="form.partnerId != null" label="合伙人编号" prop="partnerId" label-width="120px">
        <el-input v-model="form.partnerId" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="username" label-width="120px">
        <el-input v-model="form.partnerName" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="公司名称" prop="username" label-width="120px">
        <el-input v-model="form.companyName" placeholder="请输入公司名称"></el-input>
      </el-form-item>
      <el-form-item label="手机" prop="phone" label-width="120px">
        <el-input v-model="form.registerMobile" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item label="证件号码" prop="idcartNumber" label-width="120px">
        <el-input v-model="form.idcartNumber" placeholder="请输入证件号码"></el-input>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime" label-width="120px">
        <el-date-picker v-model="form.createTime" type="datetime" placeholder="选择日期时间" :disabled="true"
                        value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
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
  import {save, edit, upload} from '@/api/member/partner'
  import {parseTime} from '@/utils/index'

  export default {
    //父组件向子组件传值，通过props获取。
    //一旦父组件改变了`sonData`对应的值，子组件的`sonData`会立即改变，通过watch函数可以实时监听到值的变化
    //`props`不属于data，但是`props`中的参数可以像data中的参数一样直接使用
    props: ['sonData'],

    data() {
      return {
        dialogVisible: false,
        dialogTitle: '新增',
        imgURL: '',
        form: {
            partnerId: '',
            partnerName: '',
            companyName: '',
            registerMobile: '',
            idcartNumber: '',
            createTime: null
        },
        rules: {
          partnerId: [{required: true, trigger: 'blur', message: '请输入编号'}],
          partnerName: [{required: true, trigger: 'blur', message: '请输入姓名'}],
          companyName: [{required: true, trigger: 'blur', message: '请输入公司名'}],
          registerMobile: [{required: true, trigger: 'blur', message: '请输入注册手机'}],
          createTime: [{required: true, trigger: 'blur', message: '请选择创建时间'}]
        },
      }
    },
    watch: {
      'sonData': function (newVal, oldVal) {
        this.form = newVal
        this.imgURL = this.form.avatar
        this.dialogVisible = true
        if (newVal.partnerId != null) {
          this.dialogTitle = '修改'
        } else {
          this.dialogTitle = '新增'
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
        this.form.partnerId = null
        this.form.partnerName = null
        this.form.companyName = null
        this.form.registerMobile = null
        this.form.createTime = null
      },
      handleClose() {
        this.clearForm();
        this.dialogVisible = false
      },
      onSubmit(form) {
        this.$refs[form].validate((valid) => {
          if (valid) {
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
          } else {
            this.$message('error submit!!')
            return false;
          }
        });
      }
    }
  }
</script>



