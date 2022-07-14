package com.example.live.util;

import com.alibaba.fastjson.JSONObject;
import com.example.live.common.Constant;
import com.example.live.entity.Invoice;
import com.example.live.mapper.DataConfigMapper;
import com.example.live.mapper.InvoiceMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;


/**
 * 邮件发送
 *
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 9:08
 */
@Component
public class MailUtil {

    @Resource
    private JavaMailSender mailSender;
    @Autowired
    private DataConfigMapper dataConfigMapper;
    @Autowired
    private InvoiceMapper invoiceMapper;

    // 发送邮箱
    public void sendMailHandler(Integer agentUser, Integer id) {
        Invoice invoice = invoiceMapper.getInvoice(id);
        if (invoice==null) {
            return;
        }

        Thread thread = new Thread(() -> {
            String con = dataConfigMapper.getConfigStr(agentUser);
            if (StringUtils.isBlank(con)) {
                System.out.println("##邮箱地址为空, agentUser:"+agentUser);
                return;
            }

            String company = invoice.getCompany();
            String tax = invoice.getTax();
            String money = invoice.getMoney()+"";
            String email = invoice.getEmail();

            String sbd = head +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    id +
                    "</td>" +
                    "<td>" +
                    company +
                    "</td>" +
                    "<td>" +
                    tax +
                    "</td>" +
                    "<td>" +
                    money +
                    "</td>" +
                    "<td>" +
                    email +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    footer;

            String[] ss = GeneralUtil.getAgentConfig(con, 0);
            sendSimpleMail(ss[0], ss[1], null, sbd);
        });
        thread.start();
    }

    // 发送
    public void sendSimpleMail(String from, String to, String cc, String html) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            // 发件人
            mimeMessageHelper.setFrom(from);
            // 收件人
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(Constant.email_subject);
            // 抄送人
            mimeMessageHelper.setCc(cc);
            // 编辑邮件内容
            mimeMessageHelper.setText(html, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String head = "<html><body>  <head>" +
            "        <meta charset=\"UTF-8\">" +
            "        <title></title>" +
            "        <style type=\"text/css\">" +
            "            \n" +
            "            table {" +
//			"                width: 300px;" +
            "                /*居中*/" +
            "                margin: 0 auto;" +
            "                border-spacing: px;" +
            "                border-collapse: collapse;" +
            "                /*设置背景样式*/" +
            "                /*background-color: #bfa;*/" +
            "            }" +
            "            " +
            "            td," +
            "            th {" +
            "                border: 1px solid black;" +
            "            }" +
            "            /*" +
            "             * 设置隔行变色" +
            "             */" +
            "            " +
            "            tr:nth-child(even) {" +
            "                background-color: #bfa;" +
            "            }" +
            "            /*" +
            "             * 鼠标移入到tr以后，改变颜色" +
            "             */" +
            "            " +
            "            tr:hover {" +
            "                background-color: #ff0;" +
            "            }" +
            "        </style>" +
            "    </head>"
            + "<table>" +
            "            <thead>" +
            "                <tr>" +
            "                    <th>序号</th>" +
            "                    <th>公司名称</th>" +
            "                    <th>纳税识别号</th>" +
            "                    <th>发票金额</th>" +
            "                    <th>电子邮箱</th>" +
            "                </tr>" +
            "            </thead>";

    private static final String footer = " </table>\n" + "</body></html>";

}
