<template>
  <div class="app-container">
    <template v-if="localRegion">
      <el-alert type="info" :closable="false" style="margin-bottom: 10px">
        <el-avatar :size="25">IP</el-avatar>
        <span>本机 IP ：{{ this.localAddress }}</span>
        <span>所属地：{{ this.localRegion }}</span>
      </el-alert>
    </template>

    <el-card class="box-card" shadow="never" style="margin-top: 20px">
      <div slot="header" class="clearfix">
        <span>获取 IP 所在地</span>
      </div>
      <div class="text item">
        <span>IP 地址</span>
        <el-input
          v-model="query.ip"
          style="width: 200px; margin-right: 20px;"
        />
        <el-button type="primary" @click="getIpRegion()">
          查询
        </el-button>
        <br>
        <template v-if="regions.length !== 0">
          <span>所属地：</span>
          <el-tag
            v-for="tag in regions"
            :key="tag"
            :disable-transitions="false"
            style="margin-top: 20px; margin-right: 10px"
          >
            {{ tag }}
          </el-tag>
        </template>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getIpRegion, getlocalRegion } from '@/api/tool'

export default {
  name: 'IpRegion',
  data() {
    return {
      query: {
        ip: ''
      },
      localAddress: '',
      localRegion: '',
      regions: []
    }
  },
  watch: {
    uuid(val) {}
  },
  mounted() {
    this.getlocalRegion()
  },
  methods: {
    getIpRegion() {
      getIpRegion(this.query)
        .then(response => {
          if (response.success) {
            this.regions = response.data
          }
        })
        .catch(error => {
          this.$message.error(`获取 IP 所在地失败`, error)
        })
    },
    getlocalRegion() {
      getlocalRegion()
        .then(response => {
          if (response.success) {
            this.localAddress = response.data.address
            this.localRegion = response.data.regions.join(' ')
          }
        })
        .catch(error => {
          this.$message.error(`获取 IP 所在地失败`, error)
        })
    }
  }
}
</script>
