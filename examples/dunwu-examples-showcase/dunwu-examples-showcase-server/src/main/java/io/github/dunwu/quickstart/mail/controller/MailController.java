package io.github.dunwu.quickstart.mail.controller;

import io.github.dunwu.core.BaseResult;
import io.github.dunwu.core.ResultUtils;
import io.github.dunwu.quickstart.mail.dto.MailDTO;
import io.github.dunwu.quickstart.mail.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-23
 */
@RestController
@RequestMapping("mail")
@Api(tags = "mail")
public class MailController {

	private final MailService mailService;

	public MailController(MailService mailService) {
		this.mailService = mailService;
	}

	@PostMapping("send")
	@ApiOperation(value = "发送邮件")
	public BaseResult send(@RequestBody MailDTO mailDTO) {
		mailService.send(mailDTO);
		return ResultUtils.successBaseResult();
	}

}
