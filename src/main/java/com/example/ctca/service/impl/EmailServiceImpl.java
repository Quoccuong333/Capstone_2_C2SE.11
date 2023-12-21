package com.example.ctca.service.impl;

import com.example.ctca.model.dto.EmailAccountDTO;
import com.example.ctca.model.dto.EmailTemplateDTO;
import com.example.ctca.model.dto.MailDTO;
import com.example.ctca.service.EmailService;
import com.example.ctca.utils.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String HOST_URL = "http://localhost:8080";

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String mailFrom;

    private Map<String, Object> properties =  new HashMap<>();

    @Override
    public boolean sendEmailForRegister(EmailTemplateDTO emailTemplateDTO) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Context context = new Context();

            // set properties
            if (emailTemplateDTO.getProperties() != null && !emailTemplateDTO.getProperties().isEmpty()) {
                context.setVariables(emailTemplateDTO.getProperties());
            }

            String html = templateEngine.process("mail/welcome_user.html", context);


            helper.setFrom(mailFrom);
            helper.setTo(emailTemplateDTO.getTo());
            helper.setSubject(emailTemplateDTO.getSubject());
            helper.setText(html, true);

            javaMailSender.send(message);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendEmailForResetPassword(EmailAccountDTO emailDTO) {
        return false;
    }

    @Override
    public void sendEmailForForgotPassword(EmailAccountDTO emailDTO) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            //enCoder base64
            Date date = new Date();
            MailDTO mailDTO = new MailDTO();
            mailDTO.setTime(DateUtil.convertDateToString(date, "HH:mm:ss dd-MM-yyyy"));
            mailDTO.setData(emailDTO);
            mailDTO.setMinutes(30);
            ObjectMapper objectMapper = new ObjectMapper();
            String result = objectMapper.writeValueAsString(mailDTO);
            String linkActive = HOST_URL + "/forgot-password/confirm?ref=" + encoderStringToBase64(result);

            properties.put("fullname", emailDTO.getFullName());
            properties.put("email", emailDTO.getEmail());
            properties.put("password", emailDTO.getPassword());
            properties.put("link", linkActive);
            properties.put("emailContact", "Email: ctca.contact@gmail.com");
            properties.put("phoneContact", "Hotline: 0123-456-789");

            Context context = new Context();
            context.setVariables(properties);

            String html = templateEngine.process("mail/forgot_password.html", context);

            helper.setFrom(mailFrom);
            helper.setTo(emailDTO.getEmail());
            helper.setSubject("Yêu Cầu Cấp Lại Mật Khẩu Tại www.ctca.vn");
            helper.setText(html, true);

            javaMailSender.send(message);

        } catch (Exception ex) {
        }
    }

    public String encoderStringToBase64(String text) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(text.getBytes(StandardCharsets.UTF_8) );
    }

}
