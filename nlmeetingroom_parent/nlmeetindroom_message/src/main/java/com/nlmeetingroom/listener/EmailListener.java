package com.nlmeetingroom.listener;

import io.github.isliqian.NiceEmail;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Map;

@Component
@RabbitListener(queues = "email")
public class EmailListener {
    @RabbitHandler
    public void sendEmail(Map<String,String> emailInfo) throws MessagingException {
        String toEmail = emailInfo.get("toEmail");
        String message = emailInfo.get("message");
        System.out.println(toEmail+" "+message);
        NiceEmail.config(NiceEmail.SMTP_QQ(), "2528716350@qq.com", "plxqecunlfbgeaah");
        NiceEmail.subject("审核结果")//主题
                .from("会议室管理系统管理员")//发件人昵称
                .to(toEmail)//收件人
                .text(message)//内容
                .send();//发送
    }
}
