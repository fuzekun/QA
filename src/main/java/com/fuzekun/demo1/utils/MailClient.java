package com.fuzekun.demo1.utils;


import com.fuzekun.demo1.event.EventConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Date;
import java.util.Map;

/**
 * @author: Zekun Fu
 * @date: 2022/10/10 14:44
 * @Description:
 */
@Component
public class MailClient {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);


    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromname;

    /**
     * 发送简单的文件邮件
     *
     * @param subject 主题
     * @param to      收件人
     * @param text    邮件的正文
     */
    public void sendTextMail(String subject, String to, String text) {
        this.sendTextMail(subject, fromname, to, null, null, text);
    }


    /**
     * 发送简单的文件邮件
     *
     * @param subject 主题
     * @param from    发件人
     * @param to      收件人
     * @param cc      抄送人，可以有多个抄送人
     * @param bcc     隐秘抄送人，可以有多个
     * @param text    邮件的正文
     */
    private void sendTextMail(String subject, String from, String to, String[] cc, String[] bcc, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setFrom(from);
        message.setTo(to);
        logger.info("sending Email....");
        if (cc != null) {
            message.setCc(cc);
        }
        if (bcc != null) {
            message.setBcc(bcc);
        }
        logger.info("prepared email....");
        message.setSentDate(new Date());
        message.setText(text);
        javaMailSender.send(message);
        logger.info("finished SendEmail!");
    }

    /**
     * 发送带附件的邮件
     *
     * @param subject  主题
     * @param to       收件人
     * @param text     邮件的正文
     * @param filePath 附件
     */
    public void sendAttachFileMail(String subject, String to, String text, String filePath) throws MessagingException {
        this.sendAttachFileMail(subject, fromname, to, null, null, text, filePath);
    }

    /**
     * 发送带附件的邮件
     *
     * @param subject  主题
     * @param from     发件人
     * @param to       收件人
     * @param cc       抄送人，可以有多个抄送人
     * @param bcc      隐秘抄送人，可以有多个
     * @param text     邮件的正文
     * @param filePath 附件
     */
    private void sendAttachFileMail(String subject, String from, String to, String[] cc, String[] bcc, String text, String filePath) throws MessagingException {
        //1. 构建邮件对象，注意，这里要通过 javaMailSender 来获取一个复杂邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //2. MimeMessageHelper 是一个邮件配置的辅助工具类，true 表示构建一个 multipart message 类型的邮件
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //3. 针对工具类，配置邮件发送的基本信息
        helper.setSubject(subject);
        helper.setFrom(from);
        helper.setTo(to);
        if (cc != null) {
            helper.setCc(cc);
        }
        if (bcc != null) {
            helper.setBcc(bcc);
        }
        helper.setSentDate(new Date());
        helper.setText(text);

        File file = new File(filePath);
        //4. 添加邮件附件
        helper.addAttachment(file.getName(), file);
        //5. 发送邮件
        javaMailSender.send(mimeMessage);
    }

    /**
     * 发送邮件使用Thymeleaf模板
     *
     * @param subject      主题
     * @param to           收件人
     * @param data         邮件模板需要替换的数据
     * @param templatePath 模板路径 路径在src/main/resources/templates/下
     * @throws MessagingException
     */
    public void sendThymeleafMail(String subject, String to, Map<String, Object> data, String templatePath) throws MessagingException {
        this.sendThymeleafMail(subject, fromname, to, null, null, data, templatePath);
    }


    /**
     * 发送邮件使用Thymeleaf模板
     *
     * @param subject      主题
     * @param from         发件人
     * @param to           收件人
     * @param cc           抄送人，可以有多个抄送人
     * @param bcc          隐秘抄送人，可以有多个
     * @param data         邮件模板需要替换的数据
     * @param templatePath 模板路径 路径在src/main/resources/templates/下
     * @throws MessagingException
     */
    private void sendThymeleafMail(String subject, String from, String to, String[] cc, String[] bcc, Map<String, Object> data, String templatePath) throws MessagingException {
        //1. 构建邮件对象，注意，这里要通过 javaMailSender 来获取一个复杂邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //2. MimeMessageHelper 是一个邮件配置的辅助工具类，true 表示构建一个 multipart message 类型的邮件
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //3. 针对工具类，配置邮件发送的基本信息
        helper.setSubject(subject);
        helper.setFrom(from);
        helper.setTo(to);
        if (cc != null) {
            helper.setCc(cc);
        }
        if (bcc != null) {
            helper.setBcc(bcc);
        }
        helper.setSentDate(new Date());

        Context context = new Context();
        if (data != null) {
            data.forEach((k, v) -> {
                context.setVariable(k, v);
            });
        }
        logger.info("prepared email....");
        String process = templateEngine.process(templatePath, context);
        helper.setText(process, true);
        logger.info("mail prepared finished");
        javaMailSender.send(mimeMessage);
    }
}
