package io.github.dunwu.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-15
 */
@RestController
public class HelloController {

    @GetMapping(value = { "/", "hello" })
    public String hello() {
        return "Welcome to Dunwu";
    }

    @GetMapping("ping")
    public String ping() {
        return "pong";
    }

}
