<template>
  <div class="app-container">
    <el-col :span="12">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>菜单树</span>
        </div>
        <el-row>
          <el-col :span="12">
            <el-input
              v-model="filterText"
              placeholder="输入关键字进行过滤"
              style="padding-right: 10px"
            />
          </el-col>
          <el-col :span="12">
            <el-button
              type="primary"
              plain
              size="small"
              @click="handleSearchMenuTree"
            >
              查询
            </el-button>
            <el-button type="primary" plain size="small" @click="handleInsert">
              添加
            </el-button>
            <el-button
              v-if="deleteable"
              type="danger"
              plain
              size="small"
              @click="handleDelete"
            >
              删除
            </el-button>
          </el-col>
        </el-row>
        <el-tree
          ref="tree"
          v-loading="loading"
          :data="data"
          :props="props"
          show-checkbox
          highlight-current
          default-expand-all
          :filter-node-method="handleFilterNode"
          class="filter-tree"
          @node-click="handleNodeClick"
          @check-change="handleCheckChange"
        />
      </el-card>
    </el-col>
    <el-col v-if="showForm" :span="12" style="padding-left: 5%">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>菜单详情</span>
        </div>
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          label-width="100px"
          style="max-width: 640px"
        >
          <el-form-item label="父菜单" prop="parentId" class="form-item">
            <el-input v-model="form.parentId" />
          </el-form-item>
          <el-form-item label="菜单名称" prop="name" class="form-item">
            <el-input v-model="form.name" />
          </el-form-item>
          <el-form-item label="菜单URL" prop="url" class="form-item">
            <el-input v-model="form.url" placeholder="请输入内容" />
          </el-form-item>
          <el-form-item label="权限表达式" prop="perms" class="form-item">
            <el-input v-model="form.perms" />
          </el-form-item>
          <el-form-item label="备注" prop="notes" class="form-item">
            <el-input v-model="form.notes" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSubmitForm('form')">
              提交
            </el-button>
            <el-button @click="handleCancelForm('form')">取消</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>
  </div>
</template>

<style>
.form-item {
  max-width: 500px;
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>

<script>
import {
  checkMenu,
  getMenuById,
  insertMenu,
  treeList,
  updateMenuById,
  deleteBatchIds
} from '@/api/system/menu'

const defaultForm = {
  id: null,
  parentId: 0,
  name: '',
  url: '',
  perms: '',
  icon: '',
  type: '',
  power: '',
  notes: '',
  status: 'VALID'
}
export default {
  data() {
    return {
      loading: false,
      deleteable: false,
      showForm: false,
      props: {
        label: 'name',
        isLeaf: 'leaf',
        children: 'children'
      },
      filterText: '',
      data: [],
      checkedData: [],
      form: defaultForm,
      rules: {
        url: [
          { required: true, message: '请输入菜单URL', trigger: 'blur' },
          {
            min: 3,
            max: 100,
            message: '菜单URL长度限定在 3 到 100 个字符',
            trigger: 'blur'
          }
        ],
        name: [
          { required: true, message: '请输入菜单名称', trigger: 'blur' },
          {
            min: 2,
            max: 30,
            message: '菜单名称长度限定在 6 到 30 个字符',
            trigger: 'blur'
          }
        ]
      }
    }
  },

  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },

  created() {},

  mounted() {
    this.handleSearchMenuTree()
  },

  methods: {
    handleInit() {
      this.deleteable = false
      this.showForm = false
      this.filterText = ''
      this.data = []
      this.checkedData = []
    },
    /**
     * 确认是否删除记录
     */
    handleDelete() {
      if (this.checkedData.length === 0) {
        this.$message({ message: '请先选择一个菜单', type: 'error' })
        return
      }

      console.log('delete', this.checkedData)
      this.checkedData.forEach(item => {
        this.$refs.tree.remove(item, this.data)
      })

      this.$confirm(
        '此操作将永久删除该记录，如果该记录有子节点，也将一并删除，是否继续？',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(() => {
        console.log('this.checkedData', this.checkedData)
        this.doHandleDeleteRecord(this.checkedData, this.data)
      })
    },
    /**
     * 过滤树节点
     */
    handleFilterNode(value, data) {
      if (!value) return true
      return data.name.indexOf(value) !== -1
    },
    handleNodeClick(data) {
      this.showForm = true
      this.form = data
    },
    /**
     * 选中树形菜单节点事件
     */
    handleCheckChange(data, checked, indeterminate) {
      if (checked) {
        this.checkedData.push(data)
      } else {
        const index = this.checkedData.findIndex(d => d.id === data.id)
        this.checkedData.splice(index, 1)
      }

      this.deleteable = this.checkedData.length > 0
    },
    /**
     * 添加菜单
     */
    handleSubmitForm(formName) {
      console.log('form', this.form)
      this.$refs[formName].validate(frontValid => {
        if (frontValid) {
          checkMenu(this.form).then(response => {
            if (response.code === 0) {
              if (this.form.id !== null) {
                this.dohandleUpdateRecord()
              } else {
                this.doHandleInsertRecord()
              }
            }
          })
        }
      })
    },
    /**
     * 添加菜单
     */
    handleInsert() {
      if (this.checkedData.length > 1) {
        this.$message({ message: '只能选择一个节点作为父节点', type: 'error' })
        return
      }

      this.showForm = true
      this.form = defaultForm
      console.log('handleInsert checkedData', this.checkedData)
      if (this.checkedData.length === 1) {
        this.form.parentId = this.checkedData[0].id
      } else {
        this.form.parentId = 0
      }
    },
    /**
     * 取消表单编辑事件
     */
    handleCancelForm(formName) {
      this.$refs[formName].resetFields()
      this.showForm = false
    },
    /**
     * 向后台请求添加记录
     */
    async doHandleInsertRecord() {
      await insertMenu(this.form).then(response => {
        if (response.code === 0) {
          this.$message({ message: '添加成功', type: 'success' })
          this.showForm = false
          this.handleSearchMenuTree()
        }
      })
    },
    /**
     * 向后台请求更新记录
     */
    async dohandleUpdateRecord() {
      await updateMenuById(this.form).then(response => {
        if (response.code === 0) {
          this.$message({ message: '更新成功', type: 'success' })
          this.showForm = false
          this.handleSearchMenuTree()
        }
      })
    },
    /**
     * 向后台请求删除记录
     */
    async doHandleDeleteRecord() {
      if (this.checkedData.length === 0) {
        this.$message({ message: '没有选中要删除的记录', type: 'error' })
      }
      // 获取 ID 列表
      const idList = this.checkedData.map(v => v.id)
      await deleteBatchIds(idList).then(response => {
        if (response.code === 0) {
          this.handleSearchMenuTree()
          this.$message({ message: '删除成功', type: 'success' })
        }
      })
    },
    /**
     * 向后台请求查询指定记录详情
     */
    async handleSearchMenuById(id) {
      await getMenuById({ id: id })
        .then(response => {
          if (response.code === 0) {
            this.form = response.data
            this.form.password2 = this.form.password
            console.log('form', this.form)
          }
        })
        .catch(error => {
          console.info('error', error)
        })
    },
    /**
     * 向后台请求查询指定菜单列表树
     */
    async handleSearchMenuTree() {
      this.loading = true
      this.handleInit()
      await treeList().then(response => {
        if (response.code === 0) {
          console.log('menuTree', response.data)
          this.data = response.data
        }
      })
      this.loading = false
    }
  }
}
</script>
