package io.github.dunwu.tool.net.bean;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2018-12-11
 */
public class County implements Comparable<County>, Serializable {

    private static final long serialVersionUID = 7960348116258943881L;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 所属省
     */
    private Province province;

    /**
     * 所属市
     */
    private City city;

    @Override
    public int compareTo(County o) {
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
        if (!(o instanceof County)) { return false; }
        County county = (County) o;
        return code.equals(county.getCode());
    }

    @Override
    public String toString() {
        return "County{" +
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

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
