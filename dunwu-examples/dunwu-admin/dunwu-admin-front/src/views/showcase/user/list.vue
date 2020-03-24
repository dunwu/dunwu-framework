<template>
  <div class="app-container">
    <h3>过滤查询</h3>
    <el-form :inline="true" :model="queryEntity">
      <div>
        <el-col :span="6">
          <el-form-item label="用户名">
            <el-input v-model="queryEntity.username" placeholder="输入关键字搜索" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="邮箱">
            <el-input v-model="queryEntity.email" placeholder="输入关键字搜索" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="手机号">
            <el-input v-model="queryEntity.mobile" placeholder="输入关键字搜索" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="地址">
            <el-input v-model="queryEntity.address" placeholder="输入关键字搜索" />
          </el-form-item>
        </el-col>
      </div>
      <div>
        <el-col :span="6">
          <el-form-item label="性别">
            <el-select v-model="queryEntity.sex" placeholder="请选择">
              <el-option
                v-for="item in sexOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="状态">
            <el-select v-model="queryEntity.status" placeholder="请选择">
              <el-option
                v-for="item in statusOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-form-item>
          <el-button type="primary" size="small" @click="handleSearch">
            搜索<i class="el-icon-search el-icon--right" />
          </el-button>
          <el-button type="primary" size="small" @click="handleReset">
            重置<i class="el-icon-search el-icon--right" />
          </el-button>
        </el-form-item>
      </div>
    </el-form>
    <el-button
      slot="trigger"
      size="small"
      type="primary"
      style="float: right; margin-bottom: 10px"
      @click="openCreatePage"
    >
      添加用户
    </el-button>
    <el-table
      v-loading="loading"
      :data="tableData"
      style="width: 100%;"
      fit
      stripe
      highlight-current-row
    >
      <el-table-column label="用户名">
        <template slot-scope="scope">
          <span>{{ scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别">
        <template slot-scope="scope">
          <span v-if="scope.row.sex === 'MALE'">男</span>
          <span v-else>女</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱">
        <template slot-scope="scope">
          <span>{{ scope.row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="手机号">
        <template slot-scope="scope">
          <span>{{ scope.row.mobile }}</span>
        </template>
      </el-table-column>
      <el-table-column label="地址">
        <template slot-scope="scope">
          <span>{{ scope.row.address }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="medium" type="success">
            有效
          </el-tag>
          <el-tag v-if="scope.row.status === 1" size="medium" type="info">
            无效
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            plain
            size="mini"
            type="primary"
            @click="openUpdatePage(scope.row)"
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
      :page-sizes="[5, 10, 20, 40]"
      :current-page="page.current"
      :page-size="page.size"
      :total="page.total"
      background
      hide-on-single-page
      style="float: right; margin-top: 15px; margin-bottom: 15px;"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
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
import { getUserPage, deleteUserById } from '@/api/user'

export default {
  data() {
    return {
      loading: true,
      tableData: [],
      queryEntity: {},
      page: {
        current: 1,
        size: 10,
        total: 0,
        pages: 1
      },
      sexOptions: [
        {
          value: '',
          label: '全部'
        }, {
          value: 'MALE',
          label: '男'
        }, {
          value: 'FEMALE',
          label: '女'
        }
      ],
      statusOptions: [
        {
          value: null,
          label: '全部'
        }, {
          value: 0,
          label: '有效'
        }, {
          value: 1,
          label: '无效'
        }
      ]
    }
  },
  created() {
    this.handleSearch()
  },
  methods: {
    openCreatePage() {
      this.$router.push({
        path: '/showcase/user/create'
      })
    },
    openUpdatePage(row) {
      this.$router.push({
        path: `/showcase/user/update?id=${row.id}`
      })
    },
    handleSizeChange(val) {
      this.page.size = val
      this.handleSearch()
    },
    handleCurrentChange(val) {
      this.page.current = val
      this.handleSearch()
    },
    handleReset() {
      this.queryEntity = {}
      this.handleSearch()
    },
    async handleSearch() {
      this.loading = true
      console.log('queryEntity', this.queryEntity)
      console.log('page', this.page)
      const params = { ...this.queryEntity, ...this.page }
      await getUserPage(params)
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
    async handleDelete(row) {
      await deleteUserById(row.id)
        .then(response => {
          console.log('deleteUser', response)
          if (response.ok) {
            this.handleSearch()
            this.$message({
              message: '删除任务 ' + row.username + ' 成功',
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
