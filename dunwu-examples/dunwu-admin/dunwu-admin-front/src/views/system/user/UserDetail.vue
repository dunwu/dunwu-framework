<template>
  <div class="app-container">
    <el-card class="box-card" justify="center">
      <div slot="header">
        <span>编辑角色</span>
      </div>
      <el-form
        ref="form"
        v-loading="loading"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="form.password"
            type="password"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label="确认密码" prop="password2">
          <el-input
            v-model="form.password2"
            type="password"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio v-model="form.sex" label="MALE">男</el-radio>
          <el-radio v-model="form.sex" label="FEMALE">女</el-radio>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="form.mobile" />
        </el-form-item>
      </el-form>
      <div class="button-container">
        <el-button type="primary" @click="handleSubmitForm('form')">
          提交
        </el-button>
        <el-button @click="handleResetForm('form')">重置</el-button>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
.box-card {
  max-width: 640px;
  min-width: 500px;
  margin: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.button-container {
  display: flex;
  justify-content: center;
}
</style>

<script>
import { getUserById, insertUser, updateUserById } from '@/api/system/user'

export default {
  data() {
    const validatePassword2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      loading: false,
      id: null,
      form: {
        username: '',
        password: '',
        sex: 'MALE',
        email: '',
        mobile: '',
        status: 'VALID'
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          {
            min: 3,
            max: 30,
            message: '用户名长度限定在 3 到 30 个字符',
            trigger: 'blur'
          }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {
            min: 6,
            max: 30,
            message: '密码长度限定在 6 到 30 个字符',
            trigger: 'blur'
          }
        ],
        password2: [{ validator: validatePassword2, trigger: 'blur' }]
      }
    }
  },

  watch: {},

  created() {},

  mounted() {
    this.id = this.$route.query.id
    if (this.id) {
      this.handleSearchUserById(this.id)
    }
  },

  methods: {
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    handleSubmitForm(formName) {
      console.log('form', this.form)
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.id) {
            updateUserById(this.form)
              .then(response => {
                if (response.code === 0) {
                  this.$message({
                    message: '更新用户成功',
                    type: 'success'
                  })
                  this.$router.push({ path: '/system/user/list' })
                }
              })
              .catch(error => {
                this.$message.error('更新用户失败', error)
              })
          } else {
            insertUser(this.form)
              .then(response => {
                if (response.code === 0) {
                  this.$message({
                    message: '创建用户成功',
                    type: 'success'
                  })
                  this.$router.push({ path: '/system/user/list' })
                }
              })
              .catch(error => {
                this.$message.error('创建用户失败', error)
              })
          }
        }
      })
    },
    handleResetForm(formName) {
      this.$refs[formName].resetFields()
    },
    async handleSearchUserById(id) {
      await getUserById({ id: id })
        .then(response => {
          if (response.code === 0) {
            this.form = response.data
            this.form.password2 = this.form.password
            console.log('form', this.form)
          }
        })
        .catch(error => {
          console.info('error', error)
        })
    }
  }
}
</script>
