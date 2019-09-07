package io.github.dunwu.quickstart.mail.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮件信息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-27
 */
@Data
@ToString
@Accessors(chain = true)
public class MailDTO implements Serializable {

	private static final long serialVersionUID = 6247967463273067024L;

	private String from;

	private String replyTo;

	private String[] to;

	private String[] cc;

	private String[] bcc;

	private Date sentDate;

	private String subject;

	private String text;

	private String[] filenames;

	private Boolean html = true;

}
