<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryEntity">
      <el-col :span="6">
        <el-form-item label="角色编码">
          <el-input v-model="queryEntity.code" placeholder="输入关键字搜索" />
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="角色名">
          <el-input v-model="queryEntity.name" placeholder="输入关键字搜索" />
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
          重置<i class="el-icon-refresh el-icon--right" />
        </el-button>
      </el-form-item>
    </el-form>
    <el-button
      slot="trigger"
      size="small"
      type="primary"
      style="float: right;"
      @click="openCreatePage"
    >
      新建角色
    </el-button>
    <el-table
      v-loading="loading"
      :data="tableData"
      style="width: 100%;"
      fit
      stripe
      highlight-current-row
    >
      <el-table-column label="角色编码">
        <template slot-scope="scope">
          <span>{{ scope.row.code }}</span>
        </template>
      </el-table-column>
      <el-table-column label="角色名">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注">
        <template slot-scope="scope">
          <span>{{ scope.row.notes }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 'VALID'" size="medium" type="success">
            有效
          </el-tag>
          <el-tag v-if="scope.row.status === 'INVALID'" size="medium" type="info">
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
import { getRolePage, deleteRoleById } from '@/api/system/role'

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
          value: 'VALID',
          label: '有效'
        }, {
          value: 'INVALID',
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
        path: '/system/role/create'
      })
    },
    openUpdatePage(row) {
      this.$router.push({
        path: `/system/role/update?id=${row.id}`
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
      const params = { ...this.queryEntity, ...this.page }
      await getRolePage(params)
        .then(response => {
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
    handleDelete(row) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.doHandleDelete(row)
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    async doHandleDelete(row) {
      await deleteRoleById(row.id)
        .then(response => {
          console.log('deleteUser', response)
          if (response.code === 0) {
            console.log('tableData', this.tableData)
            console.log('length', this.tableData.length)
            if (this.tableData.length <= 1 && this.page.current > 1) {
              this.page.current = this.page.current - 1
            }
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
