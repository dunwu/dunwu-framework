<template>
  <div class="app-container">
    <el-card class="box-card" justify="center">
      <div slot="header">
        <span>编辑权限</span>
      </div>
      <el-form
        ref="form"
        v-loading="loading"
        :model="form"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="权限名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="权限表达式" prop="expression">
          <el-input v-model="form.expression" />
        </el-form-item>
        <el-form-item label="权限类型" prop="type">
          <el-input v-model="form.type" />
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
import { getPermissionById, insertPermission, updatePermissionById } from '@/api/system/permission'

export default {
  data() {
    return {
      loading: false,
      id: null,
      form: {
        name: '',
        expression: '',
        type: '',
        status: 'VALID',
        notes: ''
      },
      rules: {
        expression: [
          { required: true, message: '请输入权限表达式', trigger: 'blur' },
          {
            min: 3,
            max: 30,
            message: '权限表达式长度限定在 3 到 30 个字符',
            trigger: 'blur'
          }
        ],
        name: [
          { required: true, message: '请输入权限名', trigger: 'blur' },
          {
            min: 3,
            max: 30,
            message: '权限名长度限定在 3 到 30 个字符',
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
      this.handleSearchPermissionById(this.id)
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
            updatePermissionById(this.form)
              .then(response => {
                if (response.ok) {
                  this.$message({
                    message: '更新权限成功',
                    type: 'success'
                  })
                  this.$router.push({ path: '/system/permission/list' })
                }
              })
              .catch(error => {
                this.$message.error('更新权限失败', error)
              })
          } else {
            insertPermission(this.form)
              .then(response => {
                if (response.ok) {
                  this.$message({
                    message: '新建权限成功',
                    type: 'success'
                  })
                  this.$router.push({ path: '/system/permission/list' })
                }
              })
              .catch(error => {
                this.$message.error('新建权限失败', error)
              })
          }
        }
      })
    },
    handleResetForm(formName) {
      this.$refs[formName].resetFields()
    },
    async handleSearchPermissionById(id) {
      await getPermissionById({ id: id })
        .then(response => {
          if (response.ok) {
            this.form = response.data
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
