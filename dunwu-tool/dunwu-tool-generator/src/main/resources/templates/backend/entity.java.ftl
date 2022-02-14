package ${package.Entity};

<#if enableEasyExcel>
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
</#if>
<#if enableSwagger>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if table.enableValidate>
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import javax.validation.constraints.NotNull;
</#if>
<#if entityLombokModel>
import lombok.Data;
  <#if superEntityClass??>
import lombok.EqualsAndHashCode;
  </#if>
</#if>
<#if table.importPackages??>

  <#list table.importPackages as pkg>
import ${pkg};
  </#list>
</#if>

/**
 * ${table.comment!}实体
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Data
    <#if superEntityClass??>
@EqualsAndHashCode(callSuper = false)
    </#if>
</#if>
<#if enableEasyExcel>
@ExcelIgnoreUnannotated
</#if>
<#if table.convert>
@TableName("${table.tableName}")
</#if>
<#if enableSwagger>
@ApiModel(value = "${entity}", description = "${table.comment!}实体")
</#if>
<#if superEntityClass??>
public class ${entity} extends ${superEntityClass}<#if enableActiveRecord><${entity}></#if> {
<#elseif enableActiveRecord>
public class ${entity} extends Model<${entity}> {
<#else>
public class ${entity} implements Serializable {
</#if>

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
        <#if enableSwagger>
    @ApiModelProperty(value = "${field.comment}")
        <#else>
    /** ${field.comment} */
        </#if>
    </#if>
    <#-- EasyExcel注解 -->
    <#if enableEasyExcel>
    @ExcelProperty(value = "<#if field.labelName??>${field.labelName}<#else>${field.comment}</#if>")
    </#if>
    <#if field.keyFlag>
        <#-- 主键 -->
    @NotNull(groups = EditCheck.class)
        <#if field.keyIdentityFlag>
    @TableId(value = "${field.fieldName}", type = IdType.AUTO)
        <#elseif idType??>
    @TableId(value = "${field.fieldName}", type = IdType.${idType})
        <#elseif field.convert>
    @TableId("${field.fieldName}")
        </#if>
        <#-- 普通字段 -->
    <#elseif field.fill??>
    <#-- -----   存在字段填充设置   ----->
        <#if field.notNull>
    @NotNull(groups = { AddCheck.class, EditCheck.class })
        </#if>
        <#if field.convert>
    @TableField(value = "${field.fieldName}", fill = FieldFill.${field.fill})
        <#else>
    @TableField(fill = FieldFill.${field.fill})
        </#if>
    <#elseif field.convert>
      <#if field.notNull>
    @NotNull(groups = { AddCheck.class, EditCheck.class })
      </#if>
    @TableField("`${field.fieldName}`")
    <#else>
      <#if field.notNull>
    @NotNull(groups = { AddCheck.class, EditCheck.class })
      </#if>
    @TableField("${field.fieldName}")
    </#if>
    <#-- 乐观锁注解 -->
    <#if (versionFieldName!"") == field.fieldName>
    @Version
    </#if>
    <#-- 逻辑删除注解 -->
    <#if (logicDeleteFieldName!"") == field.fieldName>
    @TableLogic
    </#if>
    <#if (field.propertyType == "Date") || (field.propertyType == "LocalDate") || field.propertyType == "LocalDateTime">
    @JsonFormat(pattern = "${field.datePattern}", timezone = "GMT+8")
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
    public ${field.propertyType} ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
    }

    <#if entityBuilderModel>
    public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    <#else>
    public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    </#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if entityBuilderModel>
        return this;
        </#if>
    }
    </#list>
</#if>

<#if entityColumnConstant>
    <#list table.fields as field>
    public static final String ${field.fieldName?upper_case} = "${field.fieldName}";
    </#list>
</#if>
<#if enableActiveRecord>
    @Override
    protected Serializable pkVal() {
    <#if keyPropertyName??>
        return this.${keyPropertyName};
    <#else>
        return null;
    </#if>
    }

</#if>
<#if !entityLombokModel>
    @Override
    public String toString() {
        return "${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName}=" + ${field.propertyName} +
        <#else>
            ", ${field.propertyName}=" + ${field.propertyName} +
        </#if>
    </#list>
        "}";
    }
</#if>
}
