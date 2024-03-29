package ${package.Query};

import io.github.dunwu.tool.data.annotation.QueryField;
<#if enableSwagger>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
  <#if superEntityClass??>
import lombok.EqualsAndHashCode;
  </#if>
import lombok.experimental.Accessors;
</#if>
<#if table.importPackages??>

  <#list table.importPackages as pkg>
import ${pkg};
  </#list>
</#if>
import java.util.List;

/**
 * ${table.comment!} Query 实体
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Data
@Accessors(chain = true)
    <#if superEntityClass??>
@EqualsAndHashCode(callSuper = false)
    </#if>
</#if>
<#if enableSwagger>
@ApiModel(value = "${table.queryName}", description = "${table.comment!} Query 实体")
</#if>
<#if superEntityClass??>
public class ${entity} extends ${superEntityClass}<#if enableActiveRecord><${entity}></#if> {
<#elseif enableActiveRecord>
public class ${entity} extends Model<${entity}> {
<#else>
public class ${table.queryName} implements Serializable {
</#if>
<#if entitySerialVersionUID>

    private static final long serialVersionUID = 1L;
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.queryFields as field>

  <#if field.comment!?length gt 0>
    <#if enableSwagger>
    @ApiModelProperty(value = "${field.comment}")
    <#else>
    /** ${field.comment} */
    </#if>
  </#if>
  <#if (field.propertyType == "Date") || (field.propertyType == "LocalDate") || field.propertyType == "LocalDateTime">
    <#if field.datePattern??>
    @JsonFormat(pattern = "${field.datePattern}", timezone = "GMT+8")
    </#if>
  </#if>
  <#if field.queryType == "LIKE">
    @QueryField(value = "`${field.fieldName}`", type = QueryField.QueryType.LIKE)
    private ${field.propertyType} ${field.propertyName};
  <#elseif field.queryType == "BETWEEN">
    <#if (field.propertyType == "Date") || (field.propertyType == "LocalDate") || field.propertyType == "LocalDateTime">
      <#if field.datePattern??>
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "${field.datePattern}", timezone = "GMT+8")
      </#if>
    </#if>
    @QueryField(value = "${field.fieldName}", type = QueryField.QueryType.BETWEEN)
    private List<${field.propertyType}> ${field.propertyName}Range;
  <#else>
    @QueryField(value = "`${field.fieldName}`", type = QueryField.QueryType.EQUALS)
    private ${field.propertyType} ${field.propertyName};
  </#if>
</#list>
<#list table.queryExtFields as field>

  <#if field.comment!?length gt 0>
    <#if enableSwagger>
    @ApiModelProperty(value = "${field.comment}")
    <#else>
    /** ${field.comment} */
    </#if>
  </#if>
  <#if (field.propertyType == "Date") || (field.propertyType == "LocalDate") || field.propertyType == "LocalDateTime">
    <#if field.datePattern??>
    @JsonFormat(pattern = "${field.datePattern}", timezone = "GMT+8")
    </#if>
  </#if>
  <#if field.queryType == "LIKE">
    @QueryField(value = "`${field.fieldName}`", type = QueryField.QueryType.LIKE)
    private ${field.propertyType} ${field.propertyName};
  <#elseif field.queryType == "BETWEEN">

      <#if (field.propertyType == "Date") || (field.propertyType == "LocalDate") || field.propertyType == "LocalDateTime">
          <#if field.datePattern??>
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "${field.datePattern}", timezone = "GMT+8")
          </#if>
      </#if>
    @QueryField(value = "${field.fieldName}", type = QueryField.QueryType.BETWEEN)
    private List<${field.propertyType}> ${field.propertyName}Range;
  <#else>
    @QueryField(value = "`${field.fieldName}`", type = QueryField.QueryType.EQUALS)
    private ${field.propertyType} ${field.propertyName};
  </#if>
</#list>
<#------------  END 字段循环遍历  ---------->
<#if !entityLombokModel>

  <#list table.queryFields as field>
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
  <#list table.queryExtFields as field>
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

<#if !entityLombokModel>
    @Override
    public String toString() {
        return "${entity}{" +
    <#list table.queryFields as field>
        <#if field_index==0>
            "${field.propertyName}=" + ${field.propertyName} +
        <#else>
            ", ${field.propertyName}=" + ${field.propertyName} +
        </#if>
    </#list>
    <#list table.queryExtFields as field>
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
