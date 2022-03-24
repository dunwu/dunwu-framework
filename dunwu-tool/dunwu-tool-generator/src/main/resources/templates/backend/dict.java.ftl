
/**
 * ${dict.note!}
 */
public enum ${dict.formatCode} {

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list dict.options as entry>
  <#if entry.note!?length gt 0>
    /** ${entry.note} */
  </#if>
    ${entry.code}("${entry.value}", "${entry.name}")<#if entry_has_next>,<#else>;</#if>
</#list>

<#------------  END 字段循环遍历  ---------->
    private final String code;
    private final String name;

    ${dict.formatCode}(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
