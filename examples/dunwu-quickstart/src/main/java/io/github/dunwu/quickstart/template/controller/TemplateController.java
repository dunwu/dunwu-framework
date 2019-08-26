package io.github.dunwu.quickstart.template.controller;

import freemarker.template.TemplateException;
import io.github.dunwu.core.BaseResult;
import io.github.dunwu.core.ResultUtil;
import io.github.dunwu.quickstart.mail.dto.MailDTO;
import io.github.dunwu.quickstart.mail.service.MailService;
import io.github.dunwu.quickstart.template.service.TemplateService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-26
 */
@RestController
@RequestMapping("/template")
@Api(tags = "template", description = "模板服务")
public class TemplateController {

    private final MailService mailService;
    private final TemplateService templateService;

    public TemplateController(MailService mailService, TemplateService templateService) {
        this.mailService = mailService;
        this.templateService = templateService;
    }

    @GetMapping("/test")
    public BaseResult test() {

        Map<String, String> params = new HashMap<>(2);
        params.put("to", "forbreak@163.com");
        params.put("checkCode", "123456");
        String content = "";
        try {
            content = templateService.mergeTemplate(TemplateService.TMPL_MAIL_CHECKCODE, params);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return ResultUtil.failBaseResult();
        }

        MailDTO mailDTO = new MailDTO();
        mailDTO.setTo(new String[] {"forbreak@163.com"});
        mailDTO.setFrom("forbreak@163.com");
        mailDTO.setSubject("校验码");
        mailDTO.setText(content);
        mailService.send(mailDTO);
        return ResultUtil.successBaseResult();
    }
}
