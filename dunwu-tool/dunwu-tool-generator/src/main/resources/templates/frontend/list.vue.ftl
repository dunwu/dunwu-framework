<#--noinspection ALL-->
<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
<#if table.enableQuery>
      <div v-if="crud.props.searchToggle">
        <el-row>
  <#list table.queryFields as field>
          <el-col :span="6">
    <#if field.queryType = 'BETWEEN'>
      <#if (field.propertyType == "Date") || (field.propertyType == "LocalDate") || field.propertyType == "LocalDateTime">
            <date-range-picker
              v-model="query.${field.propertyName}Range"
              class="date-item"
              style="width: 90%"
            />
      <#else>
            <el-input
              v-model="query.${field.propertyName}Range"
              clearable
              placeholder="请输入<#if field.labelName??>${field.labelName}<#else>${field.propertyName}</#if>"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
      </#if>
    <#else>
          <#if (field.dictCode)?? && (field.dictCode)!="">
            <el-select v-model="query.${field.propertyName}" style="width: 90%" placeholder="请选择<#if field.labelName??>${field.labelName}<#else>${field.comment}</#if>" filterable>
              <el-option
                v-for="item in dict.${field.dictCode}.options"
                :key="item.code"
                :label="item.name"
                :value="item.value"
                :disabled="item.disabled"
              />
            </el-select>
          <#else>
            <el-input
              v-model="query.${field.propertyName}"
              clearable
              placeholder="请输入<#if field.labelName??>${field.labelName}<#else>${field.propertyName}</#if>"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </#if>
    </#if>
          </el-col>
  </#list>
  <#assign queryExtFieldsSize = table.queryExtFields?size />
  <#if table.queryExtFields??>
    <#list table.queryExtFields as field>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
    <#if field.queryType = 'BETWEEN'>
      <#if (field.propertyType == "Date") || (field.propertyType == "LocalDate") || field.propertyType == "LocalDateTime">
              <date-range-picker
                v-model="query.${field.propertyName}Range"
                class="date-item"
                style="width: 90%"
              />
      <#else>
              <el-input
                v-model="query.${field.propertyName}Range"
                clearable
                placeholder="请输入<#if field.labelName??>${field.labelName}<#else>${field.propertyName}</#if>"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
      </#if>
    <#else>
      <#if (field.dictCode)?? && (field.dictCode)!="">
              <el-select v-model="query.${field.propertyName}" style="width: 90%" placeholder="请选择<#if field.labelName??>${field.labelName}<#else>${field.comment}</#if>" filterable>
                <el-option
                  v-for="item in dict.${field.dictCode}.options"
                  :key="item.code"
                  :label="item.name"
                  :value="item.value"
                  :disabled="item.disabled"
                />
              </el-select>
      <#else>
              <el-input
                v-model="query.${field.propertyName}"
                clearable
                placeholder="请输入<#if field.labelName??>${field.labelName}<#else>${field.propertyName}</#if>"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
      </#if>
    </#if>
            </el-col>
          </template>
    </#list>
  </#if>
          <el-col :span="6">
            <TableQueryOperation :crud="crud" />
  <#if table.queryExtFields?size gt 0>
            <el-button v-if="crud.showExtendSearch" type="text" @click="crud.toggleExtendSearch">
              折叠
              <i class="el-icon-arrow-up el-icon--right" />
            </el-button>
            <el-button v-else type="text" @click="crud.toggleExtendSearch">
              展开
              <i class="el-icon-arrow-down el-icon--right" />
            </el-button>
  </#if>
          </el-col>
        </el-row>
      </div>
  <#if table.enablePermission>
      <TableOperation :permission="permission" />
  <#else>
      <TableOperation />
  </#if>
</#if>
    </div>

    <!--表格渲染-->
<#if table.enableList>
    <el-table
      ref="table"
      v-loading="crud.loading"
      :data="crud.data"
      <#if table.enableSort>@sort-change="crud.changeTableSort"</#if>
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column type="selection" width="55" />
  <#list table.fields as field>
    <#if field.enableList>
      <#if (field.listType)?? && (field.listType == "Image")>
      <el-table-column prop="${field.propertyName}" label="<#if field.labelName??>${field.labelName}<#else>${field.propertyName}</#if>" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <el-image style="width: 50px; height: 50px" :src="scope.row.${field.propertyName}" fit="fill"></el-image>
        </template>
      </el-table-column>
      <#else>
        <#if (field.dictCode)?? && (field.dictCode)!="">
      <el-table-column prop="${field.propertyName}" label="<#if field.labelName??>${field.labelName}<#else>${field.propertyName}</#if>"<#if field.enableSort> :sortable="'custom'"</#if> :show-overflow-tooltip="true">
        <template slot-scope="scope">
            {{ dict.label.${field.dictCode}[scope.row.${field.propertyName}] }}
        </template>
      </el-table-column>
        <#else>
      <el-table-column prop="${field.propertyName}" label="<#if field.labelName??>${field.labelName}<#else>${field.propertyName}</#if>"<#if field.enableSort> :sortable="'custom'"</#if> :show-overflow-tooltip="true" />
        </#if>
      </#if>
    </#if>
  </#list>
  <#if table.enablePermission>
      <el-table-column v-if="checkPer(['admin','<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:edit','<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:del'])" label="操作" width="150px">
        <template slot-scope="scope">
          <TableColumnOperation :data="scope.row" :permission="permission" />
        </template>
      </el-table-column>
  <#else>
      <el-table-column label="操作" width="150px">
        <template slot-scope="scope">
          <TableColumnOperation :data="scope.row" />
        </template>
      </el-table-column>
  </#if>
    </el-table>
</#if>

    <!--分页组件-->
    <Pagination />

    <#if table.enableForm>
    <!--表单组件-->
    <${table.formName} />
    </#if>
  </div>
</template>

<script>
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import TableQueryOperation from '@crud/TableQueryOperation'
import Pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker'
import ${table.apiName} from './${table.apiName}'
import ${table.formName} from './${table.formName}'

export default {
  name: '${table.listName}',
  components: { Pagination, TableOperation, TableQueryOperation, TableColumnOperation, DateRangePicker, ${table.formName} },
  mixins: [presenter(), header(), crud()],
  cruds() {
    return CRUD({
      title: '${table.comment!}',
      url: '<#if package.ModuleName??>${package.ModuleName}/</#if><#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.apiBaseUrl}</#if>',
      <#if table.enableSort>sort: [<#list table.fields as field><#if field.enableSort>'${field.propertyName},${field.sortType}',</#if></#list>],</#if>
      crudMethod: { ...${table.apiName} }
    })
  },
<#if (table.dictFields)?? && (table.dictFields?size > 0)>
  /**
   * 数据字典
   */
  dicts: [
    <#list table.dictFields as field>
    '${field.dictCode}'<#if field_has_next>,</#if>
    </#list>
  ],
</#if>
  data() {
    return {
      <#if table.enablePermission>permission: {
        add: ['admin', '<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:add'],
        edit: ['admin', '<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:edit'],
        del: ['admin', '<#if package.ModuleName??>${package.ModuleName}</#if>:${table.apiBaseUrl}:del']
      },</#if>
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped></style>
