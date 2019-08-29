<template>
  <div class="app-container">
    <el-alert
      title="说明"
      type="info"
      description="目前支持三种存储介质：临时磁盘空间、数据库、FastDFS"
      style="margin-bottom: 10px"
      :closable="false"
      show-icon
    />
    <el-form
      ref="uploadForm"
      :model="uploadForm"
      :rules="checkRules"
      label-width="100px"
      style="max-width: 640px"
    >
      <el-form-item label="文件名称" prop="originName" class="upload-form-item">
        <el-input v-model="uploadForm.originName" />
      </el-form-item>
      <el-form-item label="命名空间" prop="namespace" class="upload-form-item">
        <el-input v-model="uploadForm.namespace" />
      </el-form-item>
      <el-form-item label="标签" prop="tag" class="upload-form-item">
        <el-input v-model="uploadForm.tag" />
      </el-form-item>
      <el-form-item label="存储类型" prop="storeType" class="upload-form-item">
        <el-radio-group v-model="uploadForm.storeType">
          <el-radio label="TEMP">临时磁盘空间</el-radio>
          <el-radio label="DB">数据库</el-radio>
          <el-radio label="FDFS">FastDFS</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="上传文件" prop="file" class="upload-form-item">
        <el-upload
          ref="upload"
          style="float: right; margin-bottom: 10px; margin-right: 10px;"
          highlight-current-row
          :action="uploadUrl"
          :file-list="fileList"
          :data="uploadForm"
          :auto-upload="false"
          :on-change="handleChange"
          :on-remove="handleRemove"
          :on-error="handleUploadFileError"
          :on-success="handleUploadFileSuccess"
          drag
        >
          <i class="el-icon-upload" />
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div slot="tip" class="el-upload__tip">
            只能上传jpg/png文件，且不超过500kb
          </div>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('uploadForm')">
          提交
        </el-button>
        <el-button @click="resetForm('uploadForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style>
.upload-form-item {
  max-width: 460px;
}
</style>

<script>
export default {
  data() {
    return {
      uploadUrl: process.env.VUE_APP_BASE_API + '/file/upload',
      fileList: [],
      uploadForm: {
        originName: '',
        namespace: '',
        tag: '',
        storeType: 'TEMP',
        localname: ''
      },
      checkRules: {
        originName: [
          { required: true, message: '请输入文件名', trigger: 'blur' },
          {
            min: 3,
            max: 128,
            message: '长度在 3 到 128 个字符',
            trigger: 'blur'
          }
        ],
        namespace: [
          { required: true, message: '请输入命名空间', trigger: 'blur' },
          { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' }
        ],
        tag: [
          { required: true, message: '请输入标签', trigger: 'blur' },
          { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' }
        ],
        storeType: [
          { required: true, message: '请选择存储类型', trigger: 'change' }
        ],
        localname: [
          { required: true, message: '请上传文件', trigger: 'change' }
        ]
      }
    }
  },
  methods: {
    handleChange(file, fileList) {
      console.log('handleChange', file, fileList)
      this.uploadForm.localname = file.name
      this.fileList = fileList.slice(-1)
      console.log('handleChange this.fileList', this.fileList)
    },
    handleRemove(file, fileList) {
      this.uploadForm.localname = ''
      this.fileList = []
      console.log('handleRemove', file, fileList)
    },
    handleUploadFileError(response) {
      this.$message.error(`上传文件失败`, response)
    },
    handleUploadFileSuccess(response, fileList) {
      this.fileList = []
      if (response) {
        if (response.success) {
          if (response.data && response.data.size > 0) {
            this.uploadForm.file = response.data[0]
          }
          this.$message({
            message: '上传文件成功',
            type: 'success'
          })
          this.$router.push({
            path: '/showcase/filesystem/list'
          })
        }
      }
      this.uploadForm.file = response.data
      console.log('handleUploadFileSuccess response', response)
      console.log('handleUploadFileSuccess fileList', fileList)
    },
    submitForm(formName) {
      console.log('submitForm', this.fileList)
      this.$refs[formName].validate(valid => {
        if (valid) {
          console.log('ok', this.uploadForm)
          this.$refs.upload.submit()
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>
