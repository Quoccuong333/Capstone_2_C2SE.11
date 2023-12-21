package com.example.ctca.service;

import com.example.ctca.model.dto.EmailAccountDTO;
import com.example.ctca.model.dto.EmailTemplateDTO;

public interface EmailService {

    boolean sendEmailForRegister(EmailTemplateDTO emailTemplateDTO);

    boolean sendEmailForResetPassword(EmailAccountDTO emailDTO);

    void sendEmailForForgotPassword(EmailAccountDTO emailDTO);
}
