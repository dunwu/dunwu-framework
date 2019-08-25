<template>
  <div class="app-container">
    <h3>过滤查询</h3>
    <el-form :inline="true" :model="schedulerQuery">
      <el-form-item label="作业组">
        <el-input
          v-model="schedulerQuery.jobGroup"
          placeholder="输入关键字搜索"
        ></el-input>
      </el-form-item>
      <el-form-item label="作业名">
        <el-input
          v-model="schedulerQuery.jobName"
          placeholder="输入关键字搜索"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="fetchData">
          搜索<i class="el-icon-search el-icon--right"></i>
        </el-button>
      </el-form-item>
    </el-form>
    <el-button
      slot="trigger"
      size="small"
      type="primary"
      @click="openSchedulerDetailPage"
      style="float: right; margin-bottom: 10px"
    >
      添加任务<i class="el-icon-plus el-icon--right"></i>
    </el-button>
    <el-table
      :data="tableData"
      v-loading="loading"
      style="width: 100%; margin-top: 10px; margin-bottom: 10px;"
      border
      fit
      stripe
      highlight-current-row
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="file-table-extend">
            <el-form-item label="调度器名">
              <span>{{ props.row.schedulerName }}</span>
            </el-form-item>
            <el-form-item label="触发器组">
              <span>{{ props.row.triggerGroup }}</span>
            </el-form-item>
            <el-form-item label="触发器名">
              <span>{{ props.row.triggerName }}</span>
            </el-form-item>
            <el-form-item label="作业组">
              <span>{{ props.row.jobGroup }}</span>
            </el-form-item>
            <el-form-item label="作业名">
              <span>{{ props.row.jobName }}</span>
            </el-form-item>
            <el-form-item label="JavaBean名称">
              <span>{{ props.row.beanName }}</span>
            </el-form-item>
            <el-form-item label="JavaBean类型">
              <span>{{ props.row.beanType }}</span>
            </el-form-item>
            <el-form-item label="方法名">
              <span>{{ props.row.methodName }}</span>
            </el-form-item>
            <el-form-item label="方法参数">
              <span>{{ props.row.methodParams }}</span>
            </el-form-item>
            <el-form-item label="触发器类型">
              <span>{{ props.row.triggerType }}</span>
            </el-form-item>
            <el-form-item label="调用类型">
              <span>{{ props.row.invokeType }}</span>
            </el-form-item>
            <el-form-item label="起始时间">
              <span>{{ props.row.beginTime }}</span>
            </el-form-item>
            <el-form-item label="停止时间">
              <span>{{ props.row.endTime }}</span>
            </el-form-item>
            <el-form-item label="cron表达式">
              <span>{{ props.row.cronExpression }}</span>
            </el-form-item>
            <el-form-item label="状态">
              <span>{{ props.row.status }}</span>
            </el-form-item>
            <el-form-item label="备注">
              <span>{{ props.row.note }}</span>
            </el-form-item>
            <el-form-item label="创建者">
              <span>{{ props.row.createUser }}</span>
            </el-form-item>
            <el-form-item label="更新者">
              <span>{{ props.row.updateUser }}</span>
            </el-form-item>
            <el-form-item label="创建时间">
              <span>{{ props.row.createTime }}</span>
            </el-form-item>
            <el-form-item label="更新时间">
              <span>{{ props.row.updateTime }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="作业" sortable>
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>触发器组: {{ scope.row.triggerGroup }}</p>
            <p>触发器名: {{ scope.row.triggerName }}</p>
            <p>作业组: {{ scope.row.jobGroup }}</p>
            <p>作业名: {{ scope.row.jobName }}</p>
            <div slot="reference">
              <span>{{ scope.row.jobGroup + ' / ' + scope.row.jobName }}</span>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="触发对象">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>Bean名称: {{ scope.row.beanName }}</p>
            <p>Bean类型: {{ scope.row.beanType }}</p>
            <p>方法名: {{ scope.row.methodName }}</p>
            <p>方法参数: {{ scope.row.methodParams }}</p>
            <div slot="reference" class="name-wrapper">
              {{ scope.row.methodName }}
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="状态">
        <template slot-scope="scope">
          <div slot="reference" class="name-wrapper">
            <el-tag v-if="scope.row.status === 0" size="medium" type="success">
              执行中
            </el-tag>
            <el-tag v-if="scope.row.status === 1" size="medium" type="info">
              已暂停
            </el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="触发条件">
        <template slot-scope="scope">
          <div slot="reference" class="name-wrapper">
            <el-tag
              v-if="1 === scope.row.triggerType"
              size="medium"
              type="info"
            >
              {{ scope.row.cronExpression }}
            </el-tag>
            <el-tag
              v-if="0 === scope.row.triggerType"
              size="medium"
              type="info"
            >
              {{
                '重复间隔: ' +
                  scope.row.repeatInterval +
                  ' / 重复次数: ' +
                  scope.row.repeatCount
              }}
            </el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="更新时间">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.updateTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <div style="margin-bottom: 5px">
            <el-button
              plain
              size="mini"
              type="primary"
              @click="handleEdit(scope.row)"
            >
              修改
            </el-button>
            <el-button
              plain
              size="mini"
              type="primary"
              v-if="scope.row.status === 0"
              @click="handlePause(scope.row)"
            >
              暂停
            </el-button>
            <el-button
              plain
              size="mini"
              type="primary"
              v-if="scope.row.status === 1"
              @click="handleResume(scope.row)"
            >
              恢复
            </el-button>
          </div>
          <div>
            <el-button
              plain
              size="mini"
              type="primary"
              @click="handleExecute(scope.row)"
            >
              立即执行
            </el-button>
            <el-button
              plain
              size="mini"
              type="danger"
              @click="handleDelete(scope.row)"
            >
              删除
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :page-sizes="[5, 10, 20, 40]"
      :current-page="page.current"
      :page-size="page.size"
      :total="page.total"
      background
      hide-on-single-page
      style="float: right;"
      layout="total, sizes, prev, pager, next, jumper"
    >
    </el-pagination>
  </div>
</template>

<style lang="scss" scoped>
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}

.file-table-extend {
  font-size: 0;
}

.file-table-extend label {
  width: 150px;
  color: #99a9bf;
}

.file-table-extend .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>

<script>
import {
  getSchedulerPage,
  deleteJob,
  pauseJob,
  resumeJob,
  executeJob
} from '@/api/scheduler'

export default {
  data() {
    return {
      loading: true,
      tableData: [],
      schedulerQuery: {},
      page: {
        current: 1,
        size: 10,
        total: 0,
        pages: 1
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    handleSizeChange(val) {
      this.page.size = val
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.page.current = val
      this.fetchData()
    },
    openSchedulerDetailPage() {
      this.$router.push({
        path: '/showcase/scheduler/detail'
      })
    },
    async fetchData() {
      this.loading = true
      console.log('schedulerQuery', this.schedulerQuery)
      console.log('page', this.page)
      const params = { ...this.schedulerQuery, ...this.page }
      await getSchedulerPage(params)
        .then(response => {
          // this.tableData = response.data
          if (response.data) {
            console.log('fetch', response.data)
            this.tableData = response.data.list
            this.page.current = response.data.current
            this.page.size = response.data.size
            this.page.total = response.data.total
            this.page.pages = response.data.pages
          }
          this.loading = false
        })
        .catch(error => {
          console.info('error', error)
        })
    },
    async handleEdit(row) {
      this.$router.push({
        path: `/showcase/scheduler/edit?id=${row.id}`
      })
    },
    async handleDelete(row) {
      await deleteJob({ id: row.id })
        .then(response => {
          console.log('deleteJob', response)
          if (response.success) {
            this.fetchData()
            this.$message({
              message: '删除任务 ' + row.jobName + ' 成功',
              type: 'success'
            })
          }
        })
        .catch(error => {
          console.info('error', error)
        })
    },
    async handlePause(row) {
      await pauseJob({ id: row.id })
        .then(response => {
          if (response.success) {
            row.status = 1
            this.$message({
              message: '暂停任务 ' + row.jobName + ' 成功',
              type: 'success'
            })
          } else {
            this.$message.error(response.message)
          }
        })
        .catch(error => {
          console.info('error', error)
        })
    },
    async handleResume(row) {
      await resumeJob({ id: row.id })
        .then(response => {
          if (response.success) {
            row.status = 0
            this.$message({
              message: '恢复任务 ' + row.jobName + ' 成功',
              type: 'success'
            })
          } else {
            this.$message.error(response.message)
          }
        })
        .catch(error => {
          console.info('error', error)
        })
    },
    async handleExecute(row) {
      await executeJob({ ...row })
        .then(response => {
          if (response.success) {
            this.$message({
              message: '执行任务 ' + row.jobName + ' 成功',
              type: 'success'
            })
          } else {
            this.$message.error(response.message)
          }
        })
        .catch(error => {
          console.info('error', error)
        })
    }
  }
}
</script>
