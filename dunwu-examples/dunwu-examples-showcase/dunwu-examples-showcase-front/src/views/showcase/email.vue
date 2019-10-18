<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-form-item prop="to" label="收件人邮箱">
        <el-input v-model="form.to" @change="changeMailTo" />
      </el-form-item>
      <el-form-item prop="cc" label="抄送人">
        <el-input v-model="form.cc" @change="changeMailCC" />
      </el-form-item>
      <el-form-item prop="subject" label="邮件主题">
        <el-input v-model="form.subject" />
      </el-form-item>
      <el-form-item prop="text" label="邮件内容">
        <div><tinymce v-model="form.text" :height="300" /></div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('form')">确认发送</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { sendMail } from '@/api/tool'
import Tinymce from '@/components/Tinymce'

export default {
  components: { Tinymce },
  data() {
    return {
      form: {
        to: [],
        cc: [],
        subject: 'Dunwu 系统测试邮件',
        text: `<h1 style="text-align: center;">欢迎您使用 Dunwu 发送邮件</h1>
          <p style="text-align: center; font-size: 15px;"><img src="http://dunwu.test.upcdn.net/common/logo/zp.png" /></p>
          <ul>
          <li><strong>⭐ <a href="https://github.com/dunwu/dunwu" target="_blank" rel="noopener">Dunwu 项目</a></strong></li>
          <li><strong>🌟 <a href="https://github.com/dunwu" target="_blank" rel="noopener">我的 Github</a></strong></li>
          <li><strong>㊙ &nbsp;<a href="https://www.cnblogs.com/jingmoxukong/" target="_blank" rel="noopener">我的个人博客</a></strong></li>
          </ul>`
      },

      rules: {
        to: [
          {
            type: 'array',
            required: true,
            message: '请输入收件人邮箱地址',
            trigger: 'blur'
          }
        ],
        cc: [],
        subject: [
          { required: true, message: '请输入邮件主题', trigger: 'blur' }
        ],
        text: [{ required: true, message: '请输入邮件内容', trigger: 'blur' }]
      }
    }
  },
  methods: {
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          sendMail(this.form)
            .then(response => {
              if (response.success) {
                this.$message({
                  message: '发送邮件成功',
                  type: 'success'
                })
              }
            })
            .catch(error => {
              this.$message.error(`发送邮件异常`, error)
            })
        } else {
          console.error('mail form invalid!')
          return false
        }
      })
    },
    changeMailTo(value) {
      this.form.to = value.replace(/\s*/g, '').split(',')
      console.log('to', this.form.to)
    },
    changeMailCC(value) {
      this.form.cc = value.replace(/\s*/g, '').split(',')
    }
  }
}
</script>

<style scoped>
.line {
  text-align: center;
}
</style>
