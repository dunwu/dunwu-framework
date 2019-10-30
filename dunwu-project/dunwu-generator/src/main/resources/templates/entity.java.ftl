package ${package.Entity};

import com.fasterxml.jackson.annotation.JsonFormat;
<#if swagger2>
	import io.swagger.annotations.ApiModel;
	import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
	import lombok.Data;
    <#if superEntityClass??>
			import lombok.EqualsAndHashCode;
    </#if>
	import lombok.ToString;
    <#if superEntityClass??>
			import lombok.experimental.Accessors;
    </#if>
</#if>

<#list table.importPackages?sort as pkg>
	import ${pkg};
</#list>

/**
* ${table.comment!}数据实体
*
* @author ${author}
* @since ${date}
*/
<#if entityLombokModel>
	@Data
	@ToString
    <#if superEntityClass??>
			@Accessors(chain = true)
			@EqualsAndHashCode(callSuper = true)
    </#if>
</#if>
<#if table.convert>
	@TableName("${table.name}")
</#if>
<#if swagger2>
	@ApiModel(value = "${entity}", description = "${table.comment!}")
</#if>
<#if superEntityClass??>
	public class ${entity} extends ${superEntityClass}<#if activeRecord><${entity}></#if> {
<#elseif activeRecord>
	public class ${entity} extends Model<${entity}> {
<#else>
	public class ${entity} implements Serializable {
</#if>

private static final long serialVersionUID = 1L;
<#-- ----------	BEGIN 字段循环遍历	---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    <#if field.propertyType == "int" || field.propertyType == "Integer"
    || field.propertyType == "short" || field.propertyType == "Short"
    || field.propertyType == "long" || field.propertyType == "Long"
    || field.propertyType == "float" || field.propertyType == "Float"
    || field.propertyType == "double" || field.propertyType == "Double">
        <#assign hasDefaultValue = true />
    <#else>
        <#assign hasDefaultValue = false />
    </#if>

    <#if field.comment!?length gt 0>
        <#if swagger2>
					@ApiModelProperty(value = "${field.comment}"<#if hasDefaultValue>, example = "0"</#if>)
        <#else>
					/**
					* ${field.comment}
					*/
        </#if>
    </#if>
    <#if field.keyFlag>
    <#-- 主键 -->
        <#if field.keyIdentityFlag>
					@TableId(value = "${field.name}", type = IdType.AUTO)
        <#elseif idType??>
					@TableId(value = "${field.name}", type = IdType.${idType})
        <#elseif field.convert>
					@TableId("${field.name}")
        </#if>
    <#-- 普通字段 -->
    <#elseif field.fill??>
    <#-- -----	 存在字段填充设置	 ----->
        <#if field.convert>
					@TableField(value = "${field.name}", fill = FieldFill.${field.fill})
        <#else>
					@TableField(fill = FieldFill.${field.fill})
        </#if>
    <#elseif field.convert>
			@TableField("${field.name}")
    </#if>
<#-- 乐观锁注解 -->
    <#if (versionFieldName!"") == field.name>
			@Version
    </#if>
<#-- 逻辑删除注解 -->
    <#if (logicDeleteFieldName!"") == field.name>
			@TableLogic
    </#if>
    <#if field.propertyType == "Date">
			@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    </#if>
    <#if field.propertyType == "LocalDate">
			@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    </#if>
    <#if field.propertyType == "LocalDateTime">
			@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    </#if>
	private ${field.propertyType} ${field.propertyName};
</#list>
<#------------	END 字段循环遍历	---------->
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
			public static final String ${field.name?upper_case} = "${field.name}";

    </#list>
</#if>
<#if activeRecord>
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
