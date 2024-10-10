package com.iris.blog.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.common.constant.SystemParamCodeConstant;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.domain.config.EmailConfig;
import com.iris.blog.service.EmailService;
import com.iris.blog.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private ThreadPoolTaskExecutor executor;

    private final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();


    @PostConstruct
    public void init() {
        String paramValue = sysConfigService.getValueByCode(SystemParamCodeConstant.SYS_EMAIL_CONFIG_KEY);
        EmailConfig systemConfig = JSON.parseObject(paramValue, EmailConfig.class);
        javaMailSender.setHost(systemConfig.getEmailHost());
        javaMailSender.setUsername(systemConfig.getEmailUsername());
        javaMailSender.setPassword(systemConfig.getEmailPassword());
        javaMailSender.setPort(systemConfig.getEmailPort());
        javaMailSender.setDefaultEncoding("UTF-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.debug", "true");
        javaMailSender.setJavaMailProperties(p);
    }


    /**
     * 通知我
     */
    @Override
    public void emailNoticeMe(String content) {
        // 发送邮件
        this.send("1444073716@qq.com", content);
    }


    /**
     * 友链通过发送通知
     *
     * @param email 邮箱账号
     */
    @Override
    public void friendPassSendEmail(String email) {
        String content = "<html>\n" +
                "<body>\n" +
                "    <p>您在" + "<a href='https://www.lstar.icu'>鸢尾博客</a>" + "站点申请友链加入,<span style=\"color: #3fe847\"><strong>审核通过</strong></span>啦!!</span>\n" +
                "<p style='padding: 20px;'>非常感谢您的加入，本站将会继续努力分享高质量的文章，欢迎相互交流互访。</p>" +
                "<p>您可前往<a href='https://www.lstar.icu/links'>本站友链模块</a>查阅您的站点。</p>\n" +
                "<p>祝您生活愉快🤞🎉~~。</p></body>\n" +
                "</html>";
        this.send(email, content);
    }

    /**
     * 友链未通过发送通知
     *
     * @param email  邮箱账号
     * @param reason 原因
     */
    @Override
    public void friendFailedSendEmail(String email, String reason) {
        String content = "<html>\n" +
                "<body>\n" +
                "    <p>您在" + "<a href='https://www.lstar.icu'>鸢尾博客</a>" + "站点申请的友链加入,<span style=\"color: #E60F0F\"><strong>审核未通过</strong></span>!</span>\n" +
                "<p>具体原因为:<strong>" + reason + "</strong></p>\n" +
                "<p>请按规定重新提交,给您带来不便,深感抱歉。</p>\n" +
                "<p>祝您生活愉快🤞🎉~~。</p></body>\n" +
                "</html>";
        this.send(email, content);
    }

    /**
     * 发送邮箱验证码
     */
    public void sendCode(String email) {
        int code = RandomUtil.randomInt(100000,999999);
        String content = "<html>\n" +
                "\t<body><div id=\"contentDiv\" onmouseover=\"getTop().stopPropagation(event);\" onclick=\"getTop().preSwapLink(event, 'html', 'ZC0004_vDfNJayMtMUuKGIAzzsWvc8');\" style=\"position:relative;font-size:14px;height:auto;padding:15px 15px 10px 15px;z-index:1;zoom:1;line-height:1.7;\" class=\"body\">\n" +
                "  <div id=\"qm_con_body\">\n" +
                "    <div id=\"mailContentContainer\" class=\"qmbox qm_con_body_content qqmail_webmail_only\" style=\"opacity: 1;\">\n" +
                "      <style type=\"text/css\">\n" +
                "        .qmbox h1,.qmbox \t\t\th2,.qmbox \t\t\th3 {\t\t\t\tcolor: #00785a;\t\t\t}\t\t\t.qmbox p {\t\t\t\tpadding: 0;\t\t\t\tmargin: 0;\t\t\t\tcolor: #333;\t\t\t\tfont-size: 16px;\t\t\t}\t\t\t.qmbox hr {\t\t\t\tbackground-color: #d9d9d9;\t\t\t\tborder: none;\t\t\t\theight: 1px;\t\t\t}\t\t\t.qmbox .eo-link {\t\t\t\tcolor: #0576b9;\t\t\t\ttext-decoration: none;\t\t\t\tcursor: pointer;\t\t\t}\t\t\t.qmbox .eo-link:hover {\t\t\t\tcolor: #3498db;\t\t\t}\t\t\t.qmbox .eo-link:hover {\t\t\t\ttext-decoration: underline;\t\t\t}\t\t\t.qmbox .eo-p-link {\t\t\t\tdisplay: block;\t\t\t\tmargin-top: 20px;\t\t\t\tcolor: #009cff;\t\t\t\ttext-decoration: underline;\t\t\t}\t\t\t.qmbox .p-intro {\t\t\t\tpadding: 30px;\t\t\t}\t\t\t.qmbox .p-code {\t\t\t\tpadding: 0 30px 0 30px;\t\t\t}\t\t\t.qmbox .p-news {\t\t\t\tpadding: 0px 30px 30px 30px;\t\t\t}\n" +
                "      </style>\n" +
                "      <div style=\"max-width:800px;padding-bottom:10px;margin:20px auto 0 auto;\">\n" +
                "        <table cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #fff;border-collapse: collapse; border:1px solid #e5e5e5;box-shadow: 0 10px 15px rgba(0, 0, 0, 0.05);text-align: left;width: 100%;font-size: 14px;border-spacing: 0;\">\n" +
                "          <tbody>\n" +
                "            <tr style=\"background-color: #f8f8f8;\">\n" +
                "              <td>\n" +
                "                <img style=\"padding: 15px 0 15px 30px;width:50px\" src=\"https://ik.imagekit.io/irisblog/Iris_blog_128.png?updatedAt=1727676867801\">" +
                "                <span>鸢尾博客. </span>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td class=\"p-intro\">\n" +
                "                <p style=\"line-height:1.75em;\">"+email+",您好!感谢您使用 鸢尾博客. </p>\n" +
                "                <h1 style=\"font-size: 26px; font-weight: bold;\">验证您的邮箱地址</h1>\n" +
                "                <p style=\"line-height:1.75em;\">以下是您的邮箱验证码:</p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td class=\"p-code\">\n" +
                "                <p style=\"color: #253858;text-align:center;line-height:1.75em;background-color: #f2f2f2;min-width: 200px;margin: 0 auto;font-size: 28px;border-radius: 5px;border: 1px solid #d9d9d9;font-weight: bold;\">"+code+"</p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td class=\"p-intro\">\n" +
                "                <p style=\"line-height:1.75em;\">如果你没有请求此代码，可放心忽略这封电子邮件。别人可能错误地键入了你的电子邮件地址。 </p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "              <td class=\"p-intro\">\n" +
                "                <hr>\n" +
                "                <p style=\"text-align: center;line-height:1.75em;\">Iris - <a href='https://www.lstar.icu' style='text-decoration: none;'>鸢尾博客</a></p>\n" +
                "              </td>\n" +
                "            </tr>\n" +
                "          </tbody>\n" +
                "        </table>\n" +
                "      </div>\n" +
                "      <style type=\"text/css\">\n" +
                "        .qmbox style, .qmbox script, .qmbox head, .qmbox link, .qmbox meta {display: none !important;}\n" +
                "      </style>\n" +
                "    </div>\n" +
                "  </div><!-- -->\n" +
                "  <style>\n" +
                "    #mailContentContainer .txt {height:auto;}\n" +
                "  </style>\n" +
                "</div></body>\n" +
                "</html>\n";
        this.send(email, content);
        log.info("邮箱验证码发送成功,邮箱:{},验证码:{}", email, code);
        redisUtil.saveRedisValue(RedisKeyConstant.USER_LOGIN_CODE + email, String.valueOf(code), 5L, TimeUnit.MINUTES);
    }

    @Override
    public void emailNotice(String email, String content) {
        String contentTemplate = "<html>\n" +
                "<body>\n" +
                "    <p>您在" + "<a href='https://www.lstar.icu'>鸢尾博客</a>" + "站点提交的问题反馈,<span style=\"color: #3fe847\"><strong>已经解决</strong></span>啦!!</span>\n" +
                "<p>非常感谢您的反馈，本站将会继续努力分享高质量的文章，欢迎相互交流互访。</p>\n" +
                "<p>您可前往<a href='https://www.lstar.icu/freedback'>本站</a>查阅您的反馈。</p>\n" +
                "<p>祝您生活愉快🤞🎉~~。</p></body>\n" +
                "</html>";
        this.send(email, contentTemplate);
    }

    private void send(String email, String template) {
        executor.execute(() -> {
            try {
                //创建一个MINE消息
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mineHelper = new MimeMessageHelper(mimeMessage, true);
                // 设置邮件主题
                mineHelper.setSubject("鸢尾博客");
                // 设置邮件发送者
                mineHelper.setFrom(Objects.requireNonNull(javaMailSender.getUsername()));
                // 设置邮件接收者，可以有多个接收者，中间用逗号隔开
                mineHelper.setTo(email);
                // 设置邮件发送日期
                mineHelper.setSentDate(new Date());
                // 设置邮件的正文
                mineHelper.setText(template, true);
                // 发送邮件
                javaMailSender.send(mimeMessage);
            } catch (Exception e) {
                log.error("邮箱验证码发送异常邮箱:{}", email, e);
            }
        });
    }
}
