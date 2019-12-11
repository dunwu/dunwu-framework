package io.github.dunwu.autoconfigure.mail;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJndi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiLocatorDelegate;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Session;
import javax.naming.NamingException;

/**
 * Auto-configure a {@link MailSender} based on a {@link Session} available on JNDI.
 *
 * @author Eddú Meléndez
 * @author Stephane Nicoll
 */
@Configuration
@ConditionalOnClass(Session.class)
@ConditionalOnProperty(prefix = "spring.mail", name = "jndi-name")
@ConditionalOnJndi
class MailSenderJndiConfiguration {

    private final DunwuMailProperties properties;

    MailSenderJndiConfiguration(DunwuMailProperties properties) {
        this.properties = properties;
    }

    @Bean
    public JavaMailSenderImpl mailSender(Session session) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setDefaultEncoding(this.properties.getDefaultEncoding().name());
        sender.setSession(session);
        return sender;
    }

    @Bean
    @ConditionalOnMissingBean
    public Session session() {
        String jndiName = this.properties.getJndiName();
        try {
            return JndiLocatorDelegate.createDefaultResourceRefLocator().lookup(jndiName,
                Session.class);
        } catch (NamingException ex) {
            throw new IllegalStateException(
                String.format("Unable to find Session in JNDI location %s", jndiName),
                ex);
        }
    }

}
