import java.util.LinkedList;
import java.util.List;

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

    public static ${dict.formatCode} getEnumByCode(String code) {
        for (${dict.formatCode} i : ${dict.formatCode}.values()) {
            if (i.getCode().equals(code)) {
                return i;
            }
        }
        return null;
    }

    public static List<String> getEntries() {
        List<String> list = new LinkedList<>();
        for (${dict.formatCode} i : ${dict.formatCode}.values()) {
            list.add(i.toString());
        }
        return list;
    }

    public static List<String> getCodes() {
        List<String> list = new LinkedList<>();
        for (${dict.formatCode} i : ${dict.formatCode}.values()) {
            list.add(i.getCode());
        }
        return list;
    }

    public static List<String> getNames() {
        List<String> list = new LinkedList<>();
        for (${dict.formatCode} i : ${dict.formatCode}.values()) {
            list.add(i.getName());
        }
        return list;
    }

}
