package com.erico.accessmanagement.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @InjectMocks
    MailService underTest;

    @Mock
    JavaMailSender javaMailSender;

    @Captor
    ArgumentCaptor<SimpleMailMessage> mailCaptor;

    String sender;

    String recipient;

    String subject;

    String text;

    SimpleMailMessage mailMessage;

    @BeforeEach
    void setUp() {
        sender = "oliv.ericorib@gmail.com";
        recipient = "oliv.ericorib@gmail.com";
        subject = "Teste de envio de email";
        text = "Testando se o e-mail foi enviado com sucesso. =)";

        mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        ReflectionTestUtils.setField(underTest, "sender", sender);
    }

    @Test
    void sendMail_shouldSendMailMessage() {
        Mockito.doNothing().when(javaMailSender).send(mailMessage);

        Assertions.assertDoesNotThrow(() -> underTest.sendMail(recipient, subject, text));

        Mockito.verify(javaMailSender, Mockito.times(1)).send(mailCaptor.capture());

        Assertions.assertEquals(mailMessage.getFrom(), mailCaptor.getValue().getFrom());
        Assertions.assertEquals(mailMessage.getSubject(), mailCaptor.getValue().getSubject());
        Assertions.assertEquals(mailMessage.getText(), mailCaptor.getValue().getText());
    }

    @Test
    void sendMail_shouldThrowException_whenMailSendingFails() {
        MailException expectedException = new MailSendException("Erro ao enviar e-mail");

        Mockito.doThrow(expectedException).when(javaMailSender).send(mailMessage);

        Assertions.assertThrows(MailSendException.class, () -> underTest.sendMail(recipient, subject, text));

        // TODO: Testar mensagem de MailSendException
    }
}