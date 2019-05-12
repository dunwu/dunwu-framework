package io.github.dunwu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dunwu.web")
public class DunwuWebProperties {

    private Boolean request;

    public DunwuWebProperties() {
        this.request = true;
    }

    public Boolean getRequest() {
        return request;
    }

    public void setRequest(Boolean request) {
        this.request = request;
    }
}
