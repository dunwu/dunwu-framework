<template>
  <el-dialog
    v-el-drag-dialog
    append-to-body
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible="crud.status.cu > 0"
    :title="crud.status.title"
    width="640px"
  >
    <el-form ref="form" :model="form"<#if table.enableValidate> :rules="rules"</#if> size="small" label-width="100px">
<#list table.formFields as field>
  <#if field.enableForm>
      <el-form-item label="<#if field.labelName??>${field.labelName}<#else>${field.comment}</#if>"<#if field.notNull> prop="${field.propertyName}"</#if>>
    <#if field.formType = 'Input'>
        <el-input v-model="form.${field.propertyName}" style="width: 90%" placeholder="请输入<#if field.labelName??>${field.labelName}<#else>${field.comment}</#if>" />
    <#elseif field.formType = 'InputNumber'>
        <el-input-number v-model="form.${field.propertyName}" style="width: 90%" placeholder="请输入<#if field.labelName??>${field.labelName}<#else>${field.comment}</#if>" />
    <#elseif field.formType = 'Textarea'>
        <el-input v-model="form.${field.propertyName}" type="textarea" :autosize="{ minRows: 2, maxRows: 4 }" style="width: 90%" placeholder="请输入<#if field.labelName??>${field.labelName}<#else>${field.comment}</#if>" />
    <#elseif field.formType = 'Radio'>
      <#if (field.dictCode)?? && (field.dictCode)!="">
        <el-radio v-model="form.${field.propertyName}" v-for="item in dict.${field.dictCode}" :key="item.id" :label="item.value"  placeholder="请选择<#if field.labelName??>${field.labelName}<#else>${field.comment}</#if>">{{ item.label }}</el-radio>
      <#else>
            未设置字典，请手动设置 Radio
      </#if>
    <#elseif field.formType = 'Select'>
      <#if (field.dictCode)?? && (field.dictCode)!="">
        <el-select v-model="form.${field.propertyName}" filterable placeholder="请选择<#if field.labelName??>${field.labelName}<#else>${field.comment}</#if>">
          <el-option
            v-for="item in dict.${field.dictCode}"
            :key="item.id"
            :label="item.label"
            :value="item.value" />
        </el-select>
      <#else>
          未设置字典，请手动设置 Select
      </#if>
    <#elseif field.formType = 'Dict'>
      <#if (field.dictCode)?? && (field.dictCode)!="">
        <el-select v-model="form.${field.propertyName}" filterable placeholder="请选择<#if field.labelName??>${field.labelName}<#else>${field.comment}</#if>">
          <el-option
            v-for="item in dict.${field.dictCode}.options"
            :key="item.code"
            :label="item.name"
            :value="item.code"
            :disabled="item.disabled"
          />
        </el-select>
      <#else>
        未设置字典，请手动设置 Select
      </#if>
    <#else>
        <el-date-picker
          v-model="form.${field.propertyName}"
          type="datetime"
          style="width: 90%"
          value-format="${field.datePattern}"
          placeholder="请选择<#if field.labelName??>${field.labelName}<#else>${field.comment}</#if>"
        />
    </#if>
      </el-form-item>
  </#if>
</#list>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="crud.cancelCU">取消</el-button>
      <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import CRUD, { crud, form } from '@crud/crud'
import ElDragDialog from '@/directive/el-drag-dialog'

/**
 * 表单实体默认值
 */
const defaultForm = {<#if table.formFields??><#list table.formFields as field> ${field.propertyName}: null,</#list></#if> }
export default {
  name: '${table.formName}',
  directives: { ElDragDialog },
  mixins: [form(defaultForm), crud()],
<#if (table.dictFields)?? && (table.dictFields?size > 0)>
  /**
   * 数据字典
   */
  dicts: [
  <#list table.dictFields as field>
    '${field.dictCode}',
  </#list>
  ],
</#if>
  data() {
    return {
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
<#list table.formFields as field>
  <#if field.enableValidate>
        ${field.propertyName}: [
          { required: true, trigger: 'blur', message: '<#if field.labelName??>${field.labelName}<#else>${field.comment}</#if>不能为空' },
    <#if field.validateType??>
          { type: '${field.validateType}', trigger: 'blur', message: '<#if field.labelName??>${field.labelName}<#else>${field.comment}</#if>必须为 ${field.validateType} 类型' }
    </#if>
        ],
  </#if>
</#list>
      }
    }
  },
<#if (table.dictFields)?? && (table.dictFields?size > 0)>
  methods: {
    // 添加与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      <#list table.dictFields as field>
      form.${field.propertyName} = `<#noparse>${form.</#noparse>${field.propertyName}<#noparse>}</#noparse>`
      </#list>
    }
  }
</#if>
}
</script>

<style scoped></style>
