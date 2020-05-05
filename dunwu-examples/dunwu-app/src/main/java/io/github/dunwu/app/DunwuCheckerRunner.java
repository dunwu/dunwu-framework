package io.github.dunwu.app;

import io.github.dunwu.data.core.constant.DunwuConstant;
import io.github.dunwu.tool.io.AnsiSystem;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;

/**
 * 应用启动检查器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-14
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DunwuCheckerRunner implements ApplicationRunner {

    private final RestTemplate restTemplate;

    private final ConfigurableApplicationContext context;

    @Value("${server.port:8080}")
    private int port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        boolean flag = false;
        InetAddress address = InetAddress.getLocalHost();
        StringBuilder url = new StringBuilder();
        url.append(String.format("http://%s:%s", address.getHostAddress(), port));
        if (context.isActive()) {
            if (StrUtil.isNotBlank(contextPath)) {
                url.append(contextPath);
            }
        }

        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url.toString() + "/ping", String.class);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                flag = true;
                log.info("系统服务已准备完毕");
            }
        } catch (Exception e) {
            log.error("系统启动失败", e);
        } finally {
            if (!flag) {
                AnsiSystem.BOLD_YELLOW.println("\n"
                    + "╔╦╗╦ ╦╔╗╔╦ ╦╦ ╦  ╔═╗┌┬┐┌─┐┌─┐┌─┐┌┬┐\n"
                    + " ║║║ ║║║║║║║║ ║  ╚═╗ │ │ │├─┘├┤  ││\n"
                    + "═╩╝╚═╝╝╚╝╚╩╝╚═╝  ╚═╝ ┴ └─┘┴  └─┘─┴┘\n");
                AnsiSystem.BOLD_YELLOW.println("");

                // 优雅停机
                context.close();
            }
        }

        if (flag) {
            AnsiSystem.BOLD_GREEN.println("\n"
                + "╔╦╗╦ ╦╔╗╔╦ ╦╦ ╦  ╔═╗╔╦╗╔═╗╦═╗╔╦╗╔═╗╔╦╗\n"
                + " ║║║ ║║║║║║║║ ║  ╚═╗ ║ ╠═╣╠╦╝ ║ ║╣  ║║\n"
                + "═╩╝╚═╝╝╚╝╚╩╝╚═╝  ╚═╝ ╩ ╩ ╩╩╚═ ╩ ╚═╝═╩╝\n");
            AnsiSystem.BOLD_GREEN.println("系统启动成功，首页地址：" + url);
            AnsiSystem.BOLD_GREEN.println("");

            if (StrUtil.equalsIgnoreCase(profile, DunwuConstant.DEVELOP)) {
                String os = System.getProperty("os.name");
                // 默认为 windows时才自动打开页面
                if (StrUtil.containsIgnoreCase(os, DunwuConstant.SYSTEM_WINDOWS)) {
                    //使用默认浏览器打开系统登录页
                    Runtime.getRuntime().exec("cmd /c start " + url);
                }
            }
        }
    }

}
