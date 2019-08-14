<template>
  <el-dialog :title="dialogTitle" :before-close="handleClose" :visible.sync="dialogVisible" width="40%">
    <el-form ref="form" :rules="rules" :model="form" status-icon label-position="right" label-width="80px">
      <el-form-item v-if="form.userId != null" label="客户编号" prop="userId" label-width="120px">
        <el-input v-model="form.userId" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="真实姓名" prop="realname" label-width="120px">
        <el-input v-model="form.realname" placeholder="真实姓名"></el-input>
      </el-form-item>
      <el-form-item v-if="dialogTitle != 'Edit'" label="身份证" prop="idNumber" label-width="120px">
        <el-input v-model="form.idNumber" placeholder="请输入身份证"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile" label-width="120px">
        <el-input v-model="form.mobile" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item label="公司名称" prop="companName" label-width="120px">
        <el-input v-model="form.companName" placeholder="请输入公司名称"></el-input>
      </el-form-item>
      <el-form-item label="公司名称" prop="companName" label-width="120px">
        <el-input v-model="form.companName" placeholder="请输入公司名称"></el-input>
      </el-form-item>

      <el-form-item label="营业执照编号" prop="bussinessLicense" label-width="120px">
        <el-input v-model="form.bussinessLicense" placeholder="请输入营业执照编号"></el-input>
      </el-form-item>
      <el-form-item label="职业" prop="occupation" label-width="120px">
        <el-input v-model="form.occupation" placeholder="请输入职业"></el-input>
      </el-form-item>

      <el-form-item label="创建时间" prop="createTime" label-width="120px">
        <el-date-picker v-model="form.createTime" type="datetime" placeholder="选择日期时间" :disabled="true"
                        value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose">
        Cancel
      </el-button>
      <el-button type="primary" @click="onSubmit('form')">
        Confirm
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {save, edit, upload} from '@/api/user'
  import {parseTime} from '@/utils/index'

  export default {
    //父组件向子组件传值，通过props获取。
    //一旦父组件改变了`sonData`对应的值，子组件的`sonData`会立即改变，通过watch函数可以实时监听到值的变化
    //`props`不属于data，但是`props`中的参数可以像data中的参数一样直接使用
    props: ['sonData'],

    data() {
      return {
        dialogVisible: false,
        dialogTitle: 'Add',
        localUpload: upload,
        imgURL: '',
        form: {
          id: '',
          username: '',
          password: '',
          phone: '',
          avatar: '',
          createTime: ''
        },
        rules: {
          realname: [{required: true, trigger: 'blur', message: '真实姓名'}],
          idNumber: [{required: true, trigger: 'blur', message: '身份证'}],
          mobile: [{required: true, trigger: 'blur', message: '请输入联系电话'}],
          createTime: [{required: true, trigger: 'blur', message: '请选择创建时间'}],
          companName: [{required: true, trigger: 'blur', message: '请选择创建公司名称'}],
          bussinessLicense: [{required: true, trigger: 'blur', message: '请选择创建营业执照编号'}],
          occupation: [{required: true, trigger: 'blur', message: '请选择创建职业'}]
        },
      }
    },
    watch: {
      'sonData': function (newVal, oldVal) {
        this.form = newVal
        this.imgURL = this.form.avatar
        this.dialogVisible = true
        if (newVal.id != null) {
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
        this.form.id = null
        this.form.username = null
        this.form.password = null
        this.form.phone = null
        this.form.avatar = null
        this.imgURL = null
        this.form.createTime = parseTime(new Date(), '')
      },
      handleClose() {
        this.clearForm();
        this.dialogVisible = false
      },
      onSubmit(form) {
        this.$refs[form].validate((valid) => {
          if (valid) {
            if (this.form.id === null) {
              save(this.form).then(response => {
                if (response.code === 200) {
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
                if (response.code === 200) {
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
      },
    }
  }
</script>

<style lang="css">
  .line {
    text-align: center;
  }
</style>


