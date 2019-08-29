package io.github.dunwu.quickstart.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-20
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "forward:/static/lib/swagger-ui/swagger.html";
    }
}
