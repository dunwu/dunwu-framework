package io.github.dunwu.admin.template.config;

import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-26
 */
@Configuration
public class TemplateConfig {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Bean
    public freemarker.template.Configuration freemarkConfig() {
        /* ------------------------------------------------------------------------ */
        /* You should do this ONLY ONCE in the whole application life-cycle: */

        /* Create and adjust the configuration singleton */
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(
            freemarker.template.Configuration.VERSION_2_3_22);

        File folder = null;
        try {
            folder = ResourceUtils.getFile("classpath:templates");
            cfg.setDirectoryForTemplateLoading(folder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg;
    }

}
