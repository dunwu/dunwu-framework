<template>
  <div class="app-container">
    <el-tabs v-model="activeName">
      <el-tab-pane label="UUID" name="uuid">
        <el-card class="box-card" shadow="never">
          <div slot="header" class="clearfix">
            <span>单个 ID</span>
          </div>
          <div class="text item">
            <span>{{ uuid }}</span>
            <el-button type="primary" @click="generateUuid()">
              刷新
            </el-button>
          </div>
        </el-card>

        <el-card class="box-card" shadow="never" style="margin-top: 20px">
          <div slot="header" class="clearfix">
            <span>批量生成 ID</span>
          </div>
          <div class="text item">
            <el-alert type="info" :closable="false" style="margin-bottom: 10px">
              最多允许一次生成 100 个 ID
            </el-alert>
            <span>ID 数量</span>
            <el-input
              v-model="uuidQuery.num"
              style="width: 100px; margin-right: 20px;"
            />
            <el-checkbox
              v-model="uuidQuery.withSeparator"
            >是否带 "-" 符号
            </el-checkbox>
            <el-button type="primary" @click="generateUuidList()">
              生成
            </el-button>
            <el-button type="primary" @click="copyUuidList(uuidList, $event)">
              复制
            </el-button>
            <ul id="uuidList">
              <li v-for="value in uuidList" :key="value">
                {{ value }}
              </li>
            </ul>
          </div>
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="SnowFlake ID" name="snowflakeId">
        <el-alert
          type="info"
          :closable="false"
          show-icon
          style="margin-bottom: 10px"
        >
          根据雪花算法生成分布式 ID，参考文章：
          <el-link
            type="primary"
            href="https://github.com/dunwu/javaweb/blob/master/docs/theory/distributed-id-theory.md"
            target="_blank"
          >
            《分布式 ID 基本原理》
          </el-link>
        </el-alert>
        <el-card class="box-card" shadow="never">
          <div slot="header" class="clearfix">
            <span>单个 ID</span>
          </div>
          <div class="text item">
            <span>{{ snowflakeId }}</span>
            <el-button type="primary" @click="generateSnowflakeId()">
              刷新
            </el-button>
          </div>
        </el-card>

        <el-card class="box-card" shadow="never" style="margin-top: 20px">
          <div slot="header" class="clearfix">
            <span>批量生成 ID</span>
          </div>
          <div class="text item">
            <el-alert type="info" :closable="false" style="margin-bottom: 10px">
              最多允许一次生成 100 个 ID
            </el-alert>
            <span>ID 数量</span>
            <el-input
              v-model="snowflakeQuery.num"
              style="width: 100px; margin-right: 20px;"
            />
            <span>数据中心ID</span>
            <el-input
              v-model="snowflakeQuery.dataCenterId"
              style="width: 100px; margin-right: 20px;"
            />
            <span>机器ID</span>
            <el-input
              v-model="snowflakeQuery.machineId"
              style="width: 100px; margin-right: 20px;"
            />
            <el-button type="primary" @click="generateSnowflakeIdList()">
              生成
            </el-button>
            <el-button
              type="primary"
              @click="copySnowflakeIdList(snowflakeIdList, $event)"
            >
              复制
            </el-button>
            <ul id="snowflakeIdList">
              <li v-for="value in snowflakeIdList" :key="value">
                {{ value }}
              </li>
            </ul>
          </div>
        </el-card>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import clip from '@/utils/clipboard'
import { generateUuid, generateSnowFlakeId } from '@/api/tool'

export default {
  name: 'GenerateId',
  data() {
    return {
      uuid: '',
      uuidQuery: {
        num: 1,
        withSeparator: true
      },
      uuidList: [],
      snowflakeId: '',
      snowflakeQuery: {
        num: 1,
        dataCenterId: 1,
        machineId: 1
      },
      snowflakeIdList: [],
      activeName: 'uuid'
    }
  },
  watch: {
    uuid(val) {},
    snowflakeId(val) {}
  },
  mounted() {
    this.generateUuid()
    this.generateSnowflakeId()
  },
  methods: {
    generateUuid() {
      generateUuid({ num: 1, withSeparator: true })
        .then(response => {
          if (response.data && Array.isArray(response.data)) {
            this.uuid = response.data[0]
          }
        })
        .catch(error => {
          this.$message.error(`生成ID请求失败`, error)
        })
    },
    generateUuidList() {
      generateUuid(this.uuidQuery)
        .then(response => {
          this.uuidList = response.data
        })
        .catch(error => {
          this.$message.error(`生成ID请求失败`, error)
        })
    },
    copyUuidList(list, event) {
      // 将生成的 ID 数组拼接为字符串
      const text = list.join('\n')
      clip(text, event)
    },
    generateSnowflakeId() {
      generateSnowFlakeId({
        num: 1,
        dataCenterId: 1,
        machineId: 1
      })
        .then(response => {
          if (response.data && Array.isArray(response.data)) {
            this.snowflakeId = response.data[0]
          }
        })
        .catch(error => {
          this.$message.error(`生成ID请求失败`, error)
        })
    },
    generateSnowflakeIdList() {
      generateSnowFlakeId(this.snowflakeQuery)
        .then(response => {
          console.log('generateSnowflakeIdList', response.data)
          this.snowflakeIdList = response.data
        })
        .catch(error => {
          this.$message.error(`生成ID请求失败`, error)
        })
    },
    copySnowflakeIdList(list, event) {
      // 将生成的 ID 数组拼接为字符串
      const text = list.join('\n')
      clip(text, event)
    },
    clipboardSuccess() {
      this.$message({
        message: '复制成功',
        type: 'success',
        duration: 1500
      })
    }
  }
}
</script>
