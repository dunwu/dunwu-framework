package io.github.dunwu.util.net.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2018-12-11
 */
public class Province implements Comparable<Province>, Serializable {

    private static final long serialVersionUID = 4767006458888002150L;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 下辖城市
     */
    @JSONField(name = "children")
    private Set<City> cities;

    public Province() {
        cities = new TreeSet<>();
    }

    @Override
    public int compareTo(Province o) {
        return this.getCode().compareToIgnoreCase(o.getCode());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof Province)) { return false; }
        Province province = (Province) o;
        return code.equals(province.getCode());
    }

    @Override
    public String toString() {
        return "Province{" +
            "code='" + code + '\'' +
            ", name='" + name + '\'' +
            '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

}
