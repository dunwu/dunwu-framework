package io.github.dunwu.util.china.bean;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2018-12-11
 */
public class City implements Comparable<City> {
    private String code;
    private String name;
    private Province province;
    private Set<County> counties;

    public City() {
        counties = new TreeSet<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Set<County> getCounties() {
        return counties;
    }

    public void setCounties(Set<County> counties) {
        this.counties = counties;
    }

    @Override
    public int compareTo(City o) {
        return this.getCode().compareToIgnoreCase(o.getCode());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        City city = (City) obj;
        if (city.getCode().equals(this.getCode())) {
            return true;
        } else {
            return false;
        }
    }
}
