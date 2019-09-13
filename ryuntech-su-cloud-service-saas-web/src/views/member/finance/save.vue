<template>
  <el-dialog :title="dialogTitle" :before-close="handleClose" :visible.sync="dialogVisible" width="40%">
    <el-form ref="form" :rules="rules" :model="form" status-icon label-position="right" label-width="80px">
      <el-form-item v-if="form.userId != null" label="客户编号" prop="userId" label-width="120px">
        <el-input v-model="form.userId" :disabled="true" />
      </el-form-item>
      <el-form-item label="真实姓名" prop="realname" label-width="120px">
        <el-input v-model="form.realName" maxlength="60" placeholder="真实姓名" />
      </el-form-item>
      <el-form-item label="身份证" prop="idNumber" label-width="120px">
        <el-input v-model="form.idNumber" maxlength="50" placeholder="请输入身份证" />
      </el-form-item>
      <el-form-item label="手机号" prop="mobile" label-width="120px">
        <el-input v-model="form.mobile" type="tel" maxlength="11" placeholder="请输入手机号" />
      </el-form-item>
      <el-form-item label="地址" prop="address" label-width="120px">
        <el-input v-model="form.address" maxlength="60" placeholder="请输入地址" />
      </el-form-item>
      <el-form-item label="公司名称" prop="companyName" label-width="120px">
        <el-input v-model="form.companyName" maxlength="60" placeholder="请输入公司名称" />
      </el-form-item>

      <el-form-item label="营业执照编号" prop="bussinessLicense" label-width="120px">
        <el-input v-model="form.bussinessLicense" maxlength="20" placeholder="请输入营业执照编号" />
      </el-form-item>
      <el-form-item label="职业" prop="occupation" label-width="120px">
        <el-input v-model="form.occupation" maxlength="20" placeholder="请输入职业" />
      </el-form-item>
      <el-form-item label="生日" prop="memberBirthday" label-width="120px">
        <el-date-picker
          v-model="form.memberBirthday"
          type="datetime"
          placeholder="选择日期时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          format="yyyy-MM-dd HH:mm:ss"
        />
      </el-form-item>

      <el-form-item label="创建时间" prop="createTime" label-width="120px">
        <el-date-picker
          v-model="form.createTime"
          type="datetime"
          placeholder="选择日期时间"
          :disabled="true"
          value-format="yyyy-MM-dd HH:mm:ss"
          format="yyyy-MM-dd HH:mm:ss"
        />
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose">退出 </el-button>
      <el-button type="primary" @click="onSubmit('form')">提交</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { save, edit } from '@/api/member/finance'
import { parseTime } from '@/utils/index'

export default {
  // 父组件向子组件传值，通过props获取。
  // 一旦父组件改变了`sonData`对应的值，子组件的`sonData`会立即改变，通过watch函数可以实时监听到值的变化
  // `props`不属于data，但是`props`中的参数可以像data中的参数一样直接使用
  props: ['sonData'],
  data() {
    return {
      dialogVisible: false,
      dialogTitle: '增加',
      form: {
        realName: '',
        idNumber: '',
        mobile: '',
        companName: '',
        bussinessLicense: ''
      },
      rules: {
        realName: [{ required: true, trigger: 'blur', message: '请输入真实姓名' }],
        idNumber: [{ required: true, trigger: 'blur', message: '请输入身份证' }],
        mobile: [{ required: true, trigger: 'blur', message: '请输入联系电话' }],
        companName: [{ required: true, trigger: 'blur', message: '请选择创建公司名称' }],
        bussinessLicense: [{ required: true, trigger: 'blur', message: '请选择创建营业执照编号' }]
      }
    }
  },
  watch: {
    'sonData': function(newVal, oldVal) {
      this.form = newVal
      this.dialogVisible = true
      if (newVal.userId != null) {
        this.dialogTitle = '修改'
      } else {
        this.dialogTitle = '新增'
      }
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
      this.form.realName = null
      this.form.idNumber = null
      this.form.mobile = null
      this.form.companName = null
      this.form.bussinessLicense = null
    },
    handleClose() {
      this.clearForm()
      this.dialogVisible = false
    },
    onSubmit(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          if (this.form.userId === null) {
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
          return false
        }
      })
    }
  }
}
</script>

