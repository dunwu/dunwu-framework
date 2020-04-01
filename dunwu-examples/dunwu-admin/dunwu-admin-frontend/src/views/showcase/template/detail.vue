<template>
  <div class="app-container">
    <el-alert
      title="说明"
      type="info"
      description="模板配置"
      style="margin-bottom: 10px"
      :closable="false"
      show-icon
    />
    <el-form
      ref="templateConfig"
      v-loading="loading"
      :model="templateConfig"
      :rules="rules"
      label-width="100px"
    >
      <el-form-item
        label="模板名称"
        prop="name"
        class="template-form-item"
      >
        <el-input v-model="templateConfig.name" />
      </el-form-item>
      <el-form-item
        label="命名空间"
        prop="namespace"
        class="template-form-item"
      >
        <el-input v-model="templateConfig.namespace" />
      </el-form-item>
      <el-form-item label="标签" prop="tag" class="template-form-item">
        <el-input v-model="templateConfig.tag" />
      </el-form-item>
      <el-form-item label="模板内容" prop="content" style="width: 100%">
        <el-row :gutter="16">
          <el-col :span="10" class="components-container">
            <el-input
              v-model="templateConfig.content"
              :rows="8"
              type="textarea"
            />
          </el-col>
          <el-divider direction="vertical" />
          <el-col :span="10" class="components-container">
            <div
              style="border: 1px solid #24b0cf; min-height: 300px;"
              v-html="templateConfig.content"
            />
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('templateConfig')">
          提交
        </el-button>
        <el-button @click="resetForm('templateConfig')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style>
.template-form-item {
  max-width: 500px;
}
</style>

<script>
import { getTemplateById, createTemplate, updateTemplate } from '@/api/tool'

export default {

  data() {
    return {
      id: null,
      loading: false,
      templateConfig: {
        name: '',
        namespace: '',
        tag: '',
        content: '',
        metadata: ''
      },
      rules: {
        name: [
          {
            type: 'string',
            required: true,
            message: '请输入模板名称',
            trigger: 'blur'
          }
        ],
        namespace: [
          {
            type: 'string',
            required: true,
            message: '请输入命名空间',
            trigger: 'blur'
          }
        ],
        tag: [
          {
            type: 'string',
            required: true,
            message: '请输入标签',
            trigger: 'blur'
          }
        ],
        content: [
          {
            type: 'string',
            required: true,
            message: '请输入模板内容',
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
      this.fetchTemplateInfo(this.id)
    }
  },

  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.id) {
            updateTemplate(this.templateConfig)
              .then(response => {
                if (response.code === 0) {
                  this.$message({
                    message: '更新模板配置成功',
                    type: 'success'
                  })
                  this.$router.push({ path: '/showcase/template/list' })
                }
              })
              .catch(error => {
                this.$message.error(`添加模板配置失败`, error)
              })
          } else {
            createTemplate(this.templateConfig)
              .then(response => {
                if (response.code === 0) {
                  this.$message({
                    message: '添加模板配置成功',
                    type: 'success'
                  })
                  this.$router.push({ path: '/showcase/template/list' })
                }
              })
              .catch(error => {
                this.$message.error(`添加模板配置失败`, error)
              })
          }
        }
      })
    },

    resetForm(formName) {
      this.$refs[formName].resetFields()
    },

    async fetchTemplateInfo(id) {
      await getTemplateById({ id: id })
        .then(response => {
          if (response.code === 0) {
            this.templateConfig = response.data
          }
        })
        .catch(error => {
          console.info('error', error)
        })
    }
  }
}
</script>
