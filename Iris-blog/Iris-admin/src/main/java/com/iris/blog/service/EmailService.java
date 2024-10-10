package com.iris.blog.service;
public interface EmailService {

    /**
     * 友链通过通知
     * @param email 邮箱账号
     */
    void friendPassSendEmail(String email);

    /**
     * 友链未通过通知
     * @param email 邮箱账号
     * @param reason 未通过原因
     */
    void friendFailedSendEmail(String email,String reason);

    /**
     * 邮箱通知我
     * @param content 内容
     */
    void emailNoticeMe(String content);

    /**
     * 发送邮箱验证码
     * @param email
     */
    void sendCode(String email);

    /**
     * 发送邮件通知
     * @param email
     * @param content
     */
    void emailNotice(String email, String content);
}
