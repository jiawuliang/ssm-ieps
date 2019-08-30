package com.ieps.util;

import javax.mail.MessagingException;

public class TestEmail {
    public static void main(String[] args) {
        try {
            MailUtil.send_mail("jiawuliang@163.com", "123456");
            System.out.println("邮件发送成功!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}