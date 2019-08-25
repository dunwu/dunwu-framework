<template>
  <div class="app-container">
    <el-alert
      title="说明"
      type="info"
      description="目前支持两种触发模式：Simple、Cron"
      style="margin-bottom: 10px"
      :closable="false"
      show-icon
    >
    </el-alert>
    <el-form
      v-loading="loading"
      :model="schedulerForm"
      :rules="checkRules"
      ref="schedulerForm"
      label-width="100px"
      style="max-width: 640px"
    >
      <el-form-item label="任务组" prop="jobGroup" class="upload-form-item">
        <el-input v-model="schedulerForm.jobGroup"></el-input>
      </el-form-item>
      <el-form-item label="任务名" prop="jobName" class="upload-form-item">
        <el-input v-model="schedulerForm.jobName"></el-input>
      </el-form-item>
      <el-form-item
        label="触发类型"
        prop="triggerType"
        class="upload-form-item"
      >
        <el-radio-group v-model="schedulerForm.triggerType">
          <el-radio :label="0">SIMPLE</el-radio>
          <el-radio :label="1">CRON</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item
        v-if="0 === schedulerForm.triggerType.valueOf()"
        label="时间范围"
        prop="timeRange"
        class="upload-form-item"
      >
        <el-date-picker
          v-model="timeRange"
          type="datetimerange"
          :picker-options="pickerOptions"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          align="right"
          @change="dateChange"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item
        v-if="0 === schedulerForm.triggerType.valueOf()"
        label="重复触发间隔（单位：秒）"
        prop="repeatInterval"
        class="upload-form-item"
      >
        <el-input v-model="schedulerForm.repeatInterval"></el-input>
      </el-form-item>
      <el-form-item
        v-if="0 === schedulerForm.triggerType.valueOf()"
        label="重复触发次数"
        prop="repeatCount"
        class="upload-form-item"
      >
        <el-input v-model="schedulerForm.repeatCount"></el-input>
      </el-form-item>
      <el-form-item
        v-if="1 === schedulerForm.triggerType.valueOf()"
        label="CRON 表达式"
        prop="cronExpression"
        class="upload-form-item"
      >
        <el-input v-model="schedulerForm.cronExpression"></el-input>
      </el-form-item>
      <el-form-item label="调度对象类" prop="beanType" class="upload-form-item">
        <el-select
          v-model="schedulerForm.beanType"
          placeholder="请选择"
          style="width: 100%"
          @change="selectBean"
        >
          <el-option
            v-for="item in beans"
            :key="item.beanType"
            :value="item.beanType"
            :label="item.beanType"
            :disabled="item.disabled"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        label="调度参数"
        prop="methodParams"
        class="upload-form-item"
      >
        <el-input
          type="textarea"
          :rows="2"
          placeholder="请输入内容"
          v-model="schedulerForm.methodParams"
        >
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('schedulerForm')">
          提交
        </el-button>
        <el-button @click="resetForm('schedulerForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style>
.upload-form-item {
  max-width: 500px;
}
</style>

<script>
import { getById, getJobBeans, createJob, updateJob } from '@/api/scheduler'
import { formatTime } from '@/utils/index'

export default {
  watch: {},

  data() {
    return {
      loading: false,
      id: null,
      beans: [],
      pickerOptions: {
        shortcuts: [
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
              picker.$emit('pick', [start, end])
            }
          }
        ]
      },
      timeRange: [],
      schedulerForm: {
        jobGroup: '',
        jobName: '',
        triggerType: 0,
        invokeType: 0,
        repeatInterval: '',
        repeatCount: '',
        cronExpression: '',
        beanName: '',
        beanType: '',
        methodName: '',
        methodParams: '',
        beginTime: '',
        endTime: '',
        note: ''
      },
      checkRules: {
        jobName: [
          { required: true, message: '请输入任务名', trigger: 'blur' },
          {
            min: 3,
            max: 128,
            message: '长度在 3 到 128 个字符',
            trigger: 'blur'
          }
        ]
      }
    }
  },

  created() {},

  mounted() {
    this.fetchBeans()
    this.id = this.$route.query.id
    if (this.id) {
      this.fetchSchedulerInfo(this.id)
    }
  },

  methods: {
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    dateChange(picker) {
      if (picker.length === 2) {
        this.timeRange = []
        this.schedulerForm.beginTime = formatTime(
          picker[0],
          'YYYY-MM-DD hh:mm:ss'
        )
        this.schedulerForm.endTime = formatTime(
          picker[1],
          'YYYY-MM-DD hh:mm:ss'
        )
        this.timeRange.push(this.schedulerForm.beginTime)
        this.timeRange.push(this.schedulerForm.endTime)
      }
    },
    selectBean(value) {
      this.beans.forEach(item => {
        if (item.beanType === value) {
          this.schedulerForm.beanName = item.beanName
        }
      })
      this.schedulerForm.beanType = value
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.id) {
            updateJob(this.schedulerForm)
              .then(response => {
                if (response.success) {
                  this.$message({
                    message: '更新调度任务成功',
                    type: 'success'
                  })
                  this.$router.push({ path: '/showcase/scheduler/list' })
                }
              })
              .catch(error => {
                this.$message.error(`创建调度任务失败`, error)
              })
          } else {
            createJob(this.schedulerForm)
              .then(response => {
                if (response.success) {
                  this.$message({
                    message: '创建调度任务成功',
                    type: 'success'
                  })
                  this.$router.push({ path: '/showcase/scheduler/list' })
                }
              })
              .catch(error => {
                this.$message.error(`创建调度任务失败`, error)
              })
          }
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    async fetchBeans() {
      await getJobBeans()
        .then(response => {
          if (response.data) {
            this.beans = response.data
          }
        })
        .catch(error => {
          console.info('error', error)
        })
    },
    async fetchSchedulerInfo(id) {
      await getById({ id: id })
        .then(response => {
          if (response.success) {
            this.schedulerForm = response.data
            this.timeRange = []
            this.timeRange.push(this.schedulerForm.beginTime)
            this.timeRange.push(this.schedulerForm.endTime)
          }
        })
        .catch(error => {
          console.info('error', error)
        })
    }
  }
}
</script>
