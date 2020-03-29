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
        <el-form-item label="角色编码" prop="code">
          <el-input v-model="form.code" />
        </el-form-item>
        <el-form-item label="角色名" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="备注" prop="notes">
          <el-input v-model="form.notes" />
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
import { getRoleById, insertRole, updateRoleById } from '@/api/system/role'

export default {
  data() {
    return {
      loading: false,
      id: null,
      form: {
        code: '',
        name: '',
        status: 'VALID',
        notes: ''
      },
      rules: {
        code: [
          { required: true, message: '请输入角色编码', trigger: 'blur' },
          {
            min: 3,
            max: 30,
            message: '角色编码长度限定在 3 到 30 个字符',
            trigger: 'blur'
          }
        ],
        name: [
          { required: true, message: '请输入角色名', trigger: 'blur' },
          {
            min: 3,
            max: 30,
            message: '角色名长度限定在 3 到 30 个字符',
            trigger: 'blur'
          }
        ]
      }
    }
  },

  watch: {},

  created() {},

  mounted() {
    this.id = this.$route.query.id
    if (this.id) {
      this.handleSearchRoleById(this.id)
    }
  },

  methods: {
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    handleSubmitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.id) {
            updateRoleById(this.form)
              .then(response => {
                if (response.ok) {
                  this.$message({
                    message: '更新角色成功',
                    type: 'success'
                  })
                  this.$router.push({ path: '/system/role/list' })
                }
              })
              .catch(error => {
                this.$message.error('更新角色失败', error)
              })
          } else {
            insertRole(this.form)
              .then(response => {
                if (response.ok) {
                  this.$message({
                    message: '新建角色成功',
                    type: 'success'
                  })
                  this.$router.push({ path: '/system/role/list' })
                }
              })
              .catch(error => {
                this.$message.error('新建角色失败', error)
              })
          }
        }
      })
    },
    handleResetForm(formName) {
      this.$refs[formName].resetFields()
    },
    async handleSearchRoleById(id) {
      await getRoleById({ id: id })
        .then(response => {
          if (response.ok) {
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
