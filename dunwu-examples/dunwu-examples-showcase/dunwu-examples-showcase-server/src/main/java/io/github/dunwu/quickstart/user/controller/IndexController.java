package io.github.dunwu.quickstart.user.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-20
 */
@RestController
@RequestMapping("/")
@Api(tags = "index", description = "IndexController")
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "forward:/static/lib/swagger-ui/swagger.html";
    }
}
