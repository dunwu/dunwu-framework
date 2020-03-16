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
public class City implements Comparable<City>, Serializable {

    private static final long serialVersionUID = -4489647518591298590L;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 下辖区/县
     */
    @JSONField(name = "children")
    private Set<County> counties;

    /**
     * 所属省
     */
    private Province province;

    public City() {
        counties = new TreeSet<>();
    }

    @Override
    public int compareTo(City o) {
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
        if (!(o instanceof City)) { return false; }
        City city = (City) o;
        return code.equals(city.getCode());
    }

    @Override
    public String toString() {
        return "City{" +
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

    public Set<County> getCounties() {
        return counties;
    }

    public void setCounties(Set<County> counties) {
        this.counties = counties;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

}
