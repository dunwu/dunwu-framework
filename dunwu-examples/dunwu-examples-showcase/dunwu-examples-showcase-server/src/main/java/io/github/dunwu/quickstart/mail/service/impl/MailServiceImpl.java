package io.github.dunwu.quickstart.mail.service.impl;

import io.github.dunwu.autoconfigure.mail.DunwuMailProperties;
import io.github.dunwu.quickstart.mail.dto.MailDTO;
import io.github.dunwu.quickstart.mail.service.MailService;
import io.github.dunwu.util.collection.CollectionUtils;
import io.github.dunwu.util.parser.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2019-01-09
 */
@Service
public class MailServiceImpl implements MailService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final JavaMailSender javaMailSender;

	private final ExecutorService mailExecutor;

	private final DunwuMailProperties mailProperties;

	public MailServiceImpl(JavaMailSender javaMailSender,
		DunwuMailProperties mailProperties,
		@Qualifier("mailExecutor") ExecutorService mailExecutor) {
		this.javaMailSender = javaMailSender;
		this.mailExecutor = mailExecutor;
		this.mailProperties = mailProperties;
	}

	@Override
	public boolean send(MailDTO mailDTO) {
		Future<?> future;
		if (mailDTO.getHtml()) {
			future = mailExecutor.submit(() -> sendMimeMessage(mailDTO));
		} else {
			future = mailExecutor.submit(() -> sendSimpleMessage(mailDTO));
		}

		return future.isDone();
	}

	@Override
	public void sendBatch(List<MailDTO> mailDTOS, boolean html) {
		if (html) {
			mailExecutor.submit(() -> sendMimeMessage(mailDTOS));
		} else {
			mailExecutor.submit(() -> sendSimpleMessage(mailDTOS));
		}
	}

	private void sendMimeMessage(List<MailDTO> mailDTOS) {
		List<MimeMessage> messages = new ArrayList<>();
		for (MailDTO mailDTO : mailDTOS) {
			MimeMessage mimeMessage = null;
			try {
				mimeMessage = fillMimeMessage(mailDTO);
			} catch (MessagingException e) {
				log.error("批量发送 MIME 邮件失败", e);
			}
			messages.add(mimeMessage);
		}

		javaMailSender.send(messages.toArray(new MimeMessage[] {}));
		if (log.isDebugEnabled()) {
			log.debug("批量发送 MIME 邮件成功");
		}
	}

	private void sendSimpleMessage(List<MailDTO> mailDTOS) {
		if (CollectionUtils.isEmpty(mailDTOS)) {
			return;
		}

		List<SimpleMailMessage> simpleMailMessages = BeanUtils.mapList(mailDTOS,
			SimpleMailMessage.class);
		for (SimpleMailMessage simpleMailMessage : simpleMailMessages) {
			if (StringUtils.isBlank(simpleMailMessage.getFrom())) {
				simpleMailMessage.setFrom(mailProperties.getFrom());
			}
		}

		try {
			javaMailSender.send(simpleMailMessages.toArray(new SimpleMailMessage[] {}));
			if (log.isDebugEnabled()) {
				log.debug("批量发送 SIMPLE 邮件成功");
			}
		} catch (MailException e) {
			log.error("批量发送 SIMPLE 邮件失败", e);
		}
	}

	private void sendMimeMessage(MailDTO mailDTO) {
		try {
			MimeMessage mimeMessage = fillMimeMessage(mailDTO);
			javaMailSender.send(mimeMessage);
			if (log.isDebugEnabled()) {
				log.debug("发送 MIME 邮件成功");
			}
		} catch (MessagingException | MailException e) {
			log.error("发送 MIME 邮件失败", e);
		}
	}

	private void sendSimpleMessage(MailDTO mailDTO) {
		SimpleMailMessage simpleMailMessage = BeanUtils.map(mailDTO,
			SimpleMailMessage.class);
		if (StringUtils.isBlank(mailDTO.getFrom())) {
			simpleMailMessage.setFrom(mailProperties.getFrom());
		}

		try {
			javaMailSender.send(simpleMailMessage);
			if (log.isDebugEnabled()) {
				log.debug("发送 SIMPLE 邮件成功");
			}
		} catch (MailException e) {
			log.error("发送 SIMPLE 邮件失败", e);
		}
	}

	private MimeMessage fillMimeMessage(MailDTO mailDTO) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true,
			mailProperties.getDefaultEncoding().toString());

		if (StringUtils.isBlank(mailDTO.getFrom())) {
			messageHelper.setFrom(mailProperties.getFrom());
		} else {
			messageHelper.setFrom(mailDTO.getFrom());
		}
		messageHelper.setTo(mailDTO.getTo());
		messageHelper.setSubject(mailDTO.getSubject());
		messageHelper.setText(mailDTO.getText(), true);

		// 添加邮件附件
		if (mailDTO.getFilenames() != null && mailDTO.getFilenames().length > 0) {
			for (String filename : mailDTO.getFilenames()) {
				messageHelper.addAttachment(filename, new File(filename));
			}
		}

		return mimeMessage;
	}

}
