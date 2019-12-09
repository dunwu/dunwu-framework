<template>
  <div class="app-container">
    <h3>过滤查询</h3>
    <el-form :inline="true" :model="fileQuery" class="demo-form-inline">
      <el-form-item label="文件名">
        <el-input
          v-model="fileQuery.originName"
          placeholder="输入关键字搜索"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="fetchData">
          搜索<i class="el-icon-search el-icon--right" />
        </el-button>
      </el-form-item>
    </el-form>
    <h3>上传文件列表</h3>
    <el-button
      slot="trigger"
      size="small"
      type="primary"
      style="float: right; margin-bottom: 10px"
      @click="openUploadPage"
    >
      添加文件<i class="el-icon-upload el-icon--right" />
    </el-button>
    <el-table
      v-loading="loading"
      :data="tableData"
      :row-class-name="tableRowClassName"
      style="width: 100%; margin-top: 10px; margin-bottom: 10px;"
      border
      fit
      stripe
      highlight-current-row
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="file-table-extend">
            <el-form-item label="命名空间">
              <span>{{ props.row.namespace }}</span>
            </el-form-item>
            <el-form-item label="标签">
              <span>{{ props.row.tag }}</span>
            </el-form-item>
            <el-form-item label="原文件名">
              <span>{{ props.row.originName }}</span>
            </el-form-item>
            <el-form-item label="实际文件名">
              <span>{{ props.row.fileName }}</span>
            </el-form-item>
            <el-form-item label="文件大小(字节)">
              <span>{{ props.row.size }}</span>
            </el-form-item>
            <el-form-item label="文件扩展名">
              <span>{{ props.row.extension }}</span>
            </el-form-item>
            <el-form-item label="文件类型">
              <span>{{ props.row.contentType }}</span>
            </el-form-item>
            <el-form-item label="存储类型">
              <span>{{ props.row.storeType }}</span>
            </el-form-item>
            <el-form-item label="存储路径">
              <span>{{ props.row.storeUrl }}</span>
            </el-form-item>
            <el-form-item label="访问路径">
              <span>{{ props.row.accessUrl }}</span>
            </el-form-item>
            <el-form-item label="上传时间">
              <span>{{ props.row.uploadTime }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="文件名" sortable>
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>命名空间: {{ scope.row.namespace }}</p>
            <p>标签: {{ scope.row.tag }}</p>
            <p>文件名: {{ scope.row.originName }}</p>
            <div slot="reference">
              <span>{{ scope.row.originName }}</span>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column label="文件类型">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>文件类型: {{ scope.row.contentType }}</p>
            <p>文件扩展名: {{ scope.row.extension }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium" type="info">{{
                scope.row.contentType
              }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="storeType" label="存储服务类型" />
      <el-table-column prop="accessUrl" label="访问路径" />
      <el-table-column label="上传时间">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span style="margin-left: 10px">{{ scope.row.uploadTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-popover
            placement="top"
            trigger="click"
            style="margin-right: 10px"
          >
            <el-card :body-style="{ padding: '0px' }">
              <img
                :src="getImageUrl(scope.row)"
                style="max-width: 320px"
                class="image"
              >
            </el-card>
            <el-button slot="reference" plain size="mini" type="primary">
              查看<i class="el-icon-view el-icon--right" />
            </el-button>
          </el-popover>
          <el-button plain size="mini" type="primary">
            <a
              class="download"
              :href="getDownloadUrl(scope.row)"
              target="_blank"
              download=""
              title="下载"
            >
              下载<i class="el-icon-download el-icon--right" />
            </a>
          </el-button>
          <el-button
            plain
            size="mini"
            type="danger"
            @click="handleDeleteFile(scope.row)"
          >
            删除<i class="el-icon-delete el-icon--right" />
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
      style="float: right;"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<style>
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
import { getFilePage, deleteFile } from '@/api/file'

export default {
  data() {
    return {
      loading: true,
      uploadUrl: process.env.VUE_APP_BASE_API + '/file/upload',
      fileList: [],
      tableData: [],
      fileQuery: {
        fileName: null
      },
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
    async handleDeleteFile(row) {
      await deleteFile({ id: row.id })
        .then(response => {
          console.log('handleDeleteFile', response)
          if (response.data) {
            this.fetchData()
            this.$message({
              message: '删除文件 ' + row.originName + ' 成功',
              type: 'success'
            })
          }
        })
        .catch(error => {
          console.info('error', error)
        })
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return 'warning-row'
      } else if (rowIndex === 3) {
        return 'success-row'
      }
      return ''
    },
    async fetchData() {
      this.loading = true
      const params = { ...this.fileQuery, ...this.page }
      await getFilePage(params)
        .then(response => {
          // this.tableData = response.data
          if (response.data) {
            this.tableData = response.data.list
            // this.page = response.data.page
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
    openUploadPage() {
      this.$router.push({
        path: '/showcase/filesystem/upload'
      })
    },
    getDownloadUrl(row) {
      return process.env.VUE_APP_BASE_API + '/file/download/' + row.accessUrl
    },
    getImageUrl(row) {
      return process.env.VUE_APP_BASE_API + '/file/image/' + row.accessUrl
    }
  }
}
</script>
