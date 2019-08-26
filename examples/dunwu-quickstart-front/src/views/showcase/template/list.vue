<template>
  <div class="app-container">
    <h3>过滤查询</h3>
    <el-form :inline="true" :model="templateQuery">
      <el-form-item label="模板名称">
        <el-input
          v-model="templateQuery.templateName"
          placeholder="输入关键字搜索"
        ></el-input>
      </el-form-item>
      <el-form-item label="命名空间">
        <el-input
          v-model="templateQuery.namespace"
          placeholder="输入关键字搜索"
        ></el-input>
      </el-form-item>
      <el-form-item label="标签">
        <el-input
          v-model="templateQuery.tag"
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
      @click="openTemplateCreatePage"
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
      <el-table-column label="模板名称">
        <template slot-scope="scope">
          <span>{{ scope.row.templateName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="命名空间">
        <template slot-scope="scope">
          <span>{{ scope.row.namespace }}</span>
        </template>
      </el-table-column>
      <el-table-column label="标签">
        <template slot-scope="scope">
          <span>{{ scope.row.tag }}</span>
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
          <el-button
            plain
            size="mini"
            type="primary"
            @click="openTemplateUpdatePage(scope.row)"
          >
            修改
          </el-button>
          <el-button
            plain
            size="mini"
            type="danger"
            @click="handleDelete(scope.row)"
          >
            删除
          </el-button>
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
import { getTemplatePage, deleteTemplate } from '@/api/tool'

export default {
  data() {
    return {
      loading: true,
      tableData: [],
      templateQuery: {},
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
    openTemplateCreatePage() {
      this.$router.push({
        path: '/showcase/template/create'
      })
    },
    openTemplateUpdatePage(row) {
      this.$router.push({
        path: `/showcase/template/update?id=${row.id}`
      })
    },
    async fetchData() {
      this.loading = true
      const params = { ...this.templateQuery, ...this.page }
      await getTemplatePage(params)
        .then(response => {
          // this.tableData = response.data
          if (response.data) {
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
    async handleDelete(row) {
      await deleteTemplate({ id: row.id })
        .then(response => {
          console.log('deleteTemplate', response)
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
    }
  }
}
</script>
