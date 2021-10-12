package ${package.Dto};

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

/**
 * ${table.comment!} Dto 类
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
@ApiModel(value = "${table.dtoName}", description = "${table.comment!}")
</#if>
<#if superEntityClass??>
public class ${entity} extends ${superEntityClass}<#if enableActiveRecord><${entity}></#if> {
<#elseif enableActiveRecord>
public class ${entity} extends Model<${entity}> {
<#else>
public class ${table.dtoName} implements Serializable {
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
