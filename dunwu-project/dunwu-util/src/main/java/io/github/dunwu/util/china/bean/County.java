package io.github.dunwu.util.china.bean;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2018-12-11
 */
public class County implements Comparable<County> {
    private String code;
    private String name;
    private City city;

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public int compareTo(County o) {
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

        County county = (County) obj;
        if (county.getCode().equals(this.getCode())) {
            return true;
        } else {
            return false;
        }
    }
}
