package io.github.dunwu.quickstart.template;

import freemarker.template.TemplateException;
import io.github.dunwu.autoconfigure.mail.DunwuMailProperties;
import io.github.dunwu.quickstart.mail.dto.MailDTO;
import io.github.dunwu.quickstart.mail.service.MailService;
import io.github.dunwu.quickstart.template.service.TemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-26
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TemplateServiceTest {

    public static final String MAIL_TO = "forbreak@163.com";

    @Autowired
    private DunwuMailProperties dunwuMailProperties;

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateService templateService;

    @Test
    public void test() throws IOException, TemplateException {
        Map<String, String> params = new HashMap<>(2);
        params.put("to", MAIL_TO);
        params.put("checkCode", "123456");
        String content;
        content = templateService.mergeTemplate(TemplateService.TMPL_MAIL_CHECKCODE,
            params);

        MailDTO mailDTO = new MailDTO();
        mailDTO.setTo(new String[] { MAIL_TO });
        mailDTO.setFrom(dunwuMailProperties.getFrom());
        mailDTO.setSubject("校验码");
        mailDTO.setText(content);
        mailService.send(mailDTO);
    }

}
