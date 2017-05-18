package org.wx.weixiao.util;

import org.wx.weixiao.model.Lecture;
import org.wx.weixiao.model.LectureSubscriber;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * Created by darxan on 2017/4/27.
 */
public class EmailUtil {

    public static void email(final Lecture lecture) {
        if (lecture.getSubscriberList()==null||lecture.getSubscriberList().size()==0) {
            return;
        }
        final String header = "";
        final String content = lecture.getIntroduction();
        final String subject = lecture.getTitle();

        final List<InternetAddress> internetAddresses = new ArrayList<>(lecture.getSubscriberList().size());
        lecture.getSubscriberList().forEach(subscriber -> {
            try {
                InternetAddress address = new InternetAddress(subscriber.getEmail(), "", CHARSET);
                address.validate();
                internetAddresses.add(address);
            }catch (Exception e) {

            }
        });
        sendEmail(subject, content, (InternetAddress[]) internetAddresses.toArray(new InternetAddress[internetAddresses.size()]));
    }

    private static final String myEmailAccount = "18795855867@163.com";
    private static final String myEmailPassword = "qwER18795855867";
    private static final String myEmailSMTPHost = "smtp.163.com";
    private static final String EMAIL_PERSON = "教务处-学在南大";


    private static final String CHARSET = "UTF-8";


    public static void main(String[] args) {
        Lecture lecture = new Lecture();
        lecture.setTitle("讲座标题");
        lecture.setIntroduction("讲座具体介绍");
        ArrayList<LectureSubscriber> subscribers = new ArrayList<>();
        LectureSubscriber subscriber = new LectureSubscriber();
        subscriber.setEmail("381675152@qq.com");
        subscribers.add(subscriber);

        lecture.setSubscriberList(subscribers);
        email(lecture);


    }

    public static boolean sendEmail(
            String subject, String content, InternetAddress[] recipients) {

        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        properties.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        properties.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
        //     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
        //     打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
        /*
        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        */

        Session session = Session.getDefaultInstance(properties);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);


        try {
            message.setFrom(new InternetAddress(myEmailAccount, EMAIL_PERSON, CHARSET));
            message.setRecipients(MimeMessage.RecipientType.TO, recipients);
            message.setSubject(subject, CHARSET);
            message.setContent(content, "text/html;charset=UTF-8");
            message.setSentDate(new Date());
            message.saveChanges();

            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();
            transport.connect(myEmailAccount, myEmailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        }catch (Exception e ) {
            e.printStackTrace();
        }
        return true;
    }
}
