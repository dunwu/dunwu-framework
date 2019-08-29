package io.github.dunwu.quickstart.mail.service;

import io.github.dunwu.quickstart.mail.dto.MailDTO;

import java.util.List;

/**
 * 邮件服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-28
 */
public interface MailService {

    boolean send(MailDTO mailDTO);

    void sendBatch(List<MailDTO> mailDTO, boolean html);
}
