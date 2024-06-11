package com.example.achref.Controller.auth;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

@Service

public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendPasswordByEmail(String email, String password) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setTo(email);
            helper.setSubject("Your New Password");
            helper.setText("Dear User,\n\nYour new password is: " + password + "\n\nPlease change it after login for security reasons.\n\nRegards,\nYour Application Team");

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée (par exemple, en enregistrant dans un fichier journal)
        }
    }
    public void sendPasswordResetEmail(String email, String resetLink) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");

        try {
            helper.setTo(email);
            helper.setSubject("Reset Your Password");
            String html="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                    "<head>\n" +
                    "<!--[if gte mso 9]>\n" +
                    "<xml>\n" +
                    "  <o:OfficeDocumentSettings>\n" +
                    "    <o:AllowPNG/>\n" +
                    "    <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                    "  </o:OfficeDocumentSettings>\n" +
                    "</xml>\n" +
                    "<![endif]-->\n" +
                    "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                    "  <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->\n" +
                    "  <title></title>\n" +
                    "  \n" +
                    "    <style type=\"text/css\">\n" +
                    "      @media only screen and (min-width: 620px) {\n" +
                    "  .u-row {\n" +
                    "    width: 600px !important;\n" +
                    "  }\n" +
                    "  .u-row .u-col {\n" +
                    "    vertical-align: top;\n" +
                    "  }\n" +
                    "\n" +
                    "  .u-row .u-col-50 {\n" +
                    "    width: 300px !important;\n" +
                    "  }\n" +
                    "\n" +
                    "  .u-row .u-col-100 {\n" +
                    "    width: 600px !important;\n" +
                    "  }\n" +
                    "\n" +
                    "}\n" +
                    "\n" +
                    "@media (max-width: 620px) {\n" +
                    "  .u-row-container {\n" +
                    "    max-width: 100% !important;\n" +
                    "    padding-left: 0px !important;\n" +
                    "    padding-right: 0px !important;\n" +
                    "  }\n" +
                    "  .u-row .u-col {\n" +
                    "    min-width: 320px !important;\n" +
                    "    max-width: 100% !important;\n" +
                    "    display: block !important;\n" +
                    "  }\n" +
                    "  .u-row {\n" +
                    "    width: 100% !important;\n" +
                    "  }\n" +
                    "  .u-col {\n" +
                    "    width: 100% !important;\n" +
                    "  }\n" +
                    "  .u-col > div {\n" +
                    "    margin: 0 auto;\n" +
                    "  }\n" +
                    "}\n" +
                    "body {\n" +
                    "  margin: 0;\n" +
                    "  padding: 0;\n" +
                    "}\n" +
                    "\n" +
                    "table,\n" +
                    "tr,\n" +
                    "td {\n" +
                    "  vertical-align: top;\n" +
                    "  border-collapse: collapse;\n" +
                    "}\n" +
                    "\n" +
                    "p {\n" +
                    "  margin: 0;\n" +
                    "}\n" +
                    "\n" +
                    ".ie-container table,\n" +
                    ".mso-container table {\n" +
                    "  table-layout: fixed;\n" +
                    "}\n" +
                    "\n" +
                    "* {\n" +
                    "  line-height: inherit;\n" +
                    "}\n" +
                    "\n" +
                    "a[x-apple-data-detectors='true'] {\n" +
                    "  color: inherit !important;\n" +
                    "  text-decoration: none !important;\n" +
                    "}\n" +
                    "\n" +
                    "table, td { color: #000000; } #u_body a { color: #161a39; text-decoration: underline; }\n" +
                    "    </style>\n" +
                    "  \n" +
                    "  \n" +
                    "\n" +
                    "<!--[if !mso]><!--><link href=\"https://fonts.googleapis.com/css?family=Lato:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\"><link href=\"https://fonts.googleapis.com/css?family=Lato:400,700&display=swap\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\n" +
                    "\n" +
                    "</head>\n" +
                    "\n" +
                    "<body class=\"clean-body u_body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f9f9f9;color: #000000\">\n" +
                    "  <!--[if IE]><div class=\"ie-container\"><![endif]-->\n" +
                    "  <!--[if mso]><div class=\"mso-container\"><![endif]-->\n" +
                    "  <table id=\"u_body\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f9f9f9;width:100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "  <tbody>\n" +
                    "  <tr style=\"vertical-align: top\">\n" +
                    "    <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">\n" +
                    "    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #f9f9f9;\"><![endif]-->\n" +
                    "    \n" +
                    "  \n" +
                    "  \n" +
                    "<div class=\"u-row-container\" style=\"padding: 0px;background-color: #f9f9f9\">\n" +
                    "  <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #f9f9f9;\">\n" +
                    "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                    "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: #f9f9f9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #f9f9f9;\"><![endif]-->\n" +
                    "      \n" +
                    "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                    "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                    "  <div style=\"height: 100%;width: 100% !important;\">\n" +
                    "  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n" +
                    "  \n" +
                    "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                    "  <tbody>\n" +
                    "    <tr>\n" +
                    "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:15px;font-family:'Lato',sans-serif;\" align=\"left\">\n" +
                    "        \n" +
                    "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #f9f9f9;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                    "    <tbody>\n" +
                    "      <tr style=\"vertical-align: top\">\n" +
                    "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                    "          <span>&#160;</span>\n" +
                    "        </td>\n" +
                    "      </tr>\n" +
                    "    </tbody>\n" +
                    "  </table>\n" +
                    "\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "  </tbody>\n" +
                    "</table>\n" +
                    "\n" +
                    "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                    "  </div>\n" +
                    "</div>\n" +
                    "<!--[if (mso)|(IE)]></td><![endif]-->\n" +
                    "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "  </div>\n" +
                    "  \n" +
                    "\n" +
                    "\n" +
                    "  \n" +
                    "  \n" +
                    "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                    "  <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\n" +
                    "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                    "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\n" +
                    "      \n" +
                    "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                    "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                    "  <div style=\"height: 100%;width: 100% !important;\">\n" +
                    "  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n" +
                    "  \n" +
                    "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                    "  <tbody>\n" +
                    "    <tr>\n" +
                    "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:25px 10px;font-family:'Lato',sans-serif;\" align=\"left\">\n" +
                    "        \n" +
                    "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                    "  <tr>\n" +
                    "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\n" +
                    "      \n" +
                    "      <img align=\"center\" border=\"0\" src=\"https://assets.unlayer.com/projects/229805/1715272304759-logo-light.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 29%;max-width: 168.2px;\" width=\"168.2\"/>\n" +
                    "      \n" +
                    "    </td>\n" +
                    "  </tr>\n" +
                    "</table>\n" +
                    "\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "  </tbody>\n" +
                    "</table>\n" +
                    "\n" +
                    "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                    "  </div>\n" +
                    "</div>\n" +
                    "<!--[if (mso)|(IE)]></td><![endif]-->\n" +
                    "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "  </div>\n" +
                    "  \n" +
                    "\n" +
                    "\n" +
                    "  \n" +
                    "  \n" +
                    "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                    "  <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #161a39;\">\n" +
                    "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                    "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #161a39;\"><![endif]-->\n" +
                    "      \n" +
                    "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                    "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                    "  <div style=\"height: 100%;width: 100% !important;\">\n" +
                    "  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n" +
                    "  \n" +
                    "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                    "  <tbody>\n" +
                    "    <tr>\n" +
                    "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:35px 10px 10px;font-family:'Lato',sans-serif;\" align=\"left\">\n" +
                    "        \n" +
                    "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                    "  <tr>\n" +
                    "    <td style=\"padding-right: 0px;padding-left: 0px;\" align=\"center\">\n" +
                    "      \n" +
                    "      <img align=\"center\" border=\"0\" src=\"https://cdn.templates.unlayer.com/assets/1593141680866-reset.png\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 10%;max-width: 58px;\" width=\"58\"/>\n" +
                    "      \n" +
                    "    </td>\n" +
                    "  </tr>\n" +
                    "</table>\n" +
                    "\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "  </tbody>\n" +
                    "</table>\n" +
                    "\n" +
                    "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                    "  <tbody>\n" +
                    "    <tr>\n" +
                    "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 10px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\n" +
                    "        \n" +
                    "  <div style=\"font-size: 14px; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                    "    <p style=\"font-size: 14px; line-height: 140%; text-align: center;\"><span style=\"font-size: 28px; line-height: 39.2px; color: #ffffff; font-family: Lato, sans-serif;\">Please reset your password </span></p>\n" +
                    "  </div>\n" +
                    "\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "  </tbody>\n" +
                    "</table>\n" +
                    "\n" +
                    "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                    "  </div>\n" +
                    "</div>\n" +
                    "<!--[if (mso)|(IE)]></td><![endif]-->\n" +
                    "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "  </div>\n" +
                    "  \n" +
                    "\n" +
                    "\n" +
                    "  \n" +
                    "  \n" +
                    "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                    "  <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;\">\n" +
                    "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                    "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #ffffff;\"><![endif]-->\n" +
                    "      \n" +
                    "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                    "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                    "  <div style=\"height: 100%;width: 100% !important;\">\n" +
                    "  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n" +
                    "  \n" +
                    "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                    "  <tbody>\n" +
                    "    <tr>\n" +
                    "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 40px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\n" +
                    "        \n" +
                    "  <div style=\"font-size: 14px; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                    "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Hello,</span></p>\n" +
                    "<p style=\"font-size: 14px; line-height: 140%;\"> </p>\n" +
                    "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">We have sent you this email in response to your request to reset your password .</span></p>\n" +
                    "<p style=\"font-size: 14px; line-height: 140%;\"> </p>\n" +
                    "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">To reset your password, please follow the link below: </span></p>\n" +
                    "  </div>\n" +
                    "\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "  </tbody>\n" +
                    "</table>\n" +
                    "\n" +
                    "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                    "  <tbody>\n" +
                    "    <tr>\n" +
                    "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:0px 40px;font-family:'Lato',sans-serif;\" align=\"left\">\n" +
                    "        \n" +
                    "  <!--[if mso]><style>.v-button {background: transparent !important;}</style><![endif]-->\n" +
                    "<div align=\"left\">\n" +
                    "  <!--[if mso]><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"\" style=\"height:51px; v-text-anchor:middle; width:205px;\" arcsize=\"2%\"  stroke=\"f\" fillcolor=\"#18163a\"><w:anchorlock/><center style=\"color:#FFFFFF;\"><![endif]-->\n" +
                    "    <a href="+resetLink+" target=\"_blank\" class=\"v-button\" style=\"box-sizing: border-box;display: inline-block;text-decoration: none;-webkit-text-size-adjust: none;text-align: center;color: #FFFFFF; background-color: #18163a; border-radius: 1px;-webkit-border-radius: 1px; -moz-border-radius: 1px; width:auto; max-width:100%; overflow-wrap: break-word; word-break: break-word; word-wrap:break-word; mso-border-alt: none;font-size: 14px;\">\n" +
                    "      <span style=\"display:block;padding:15px 40px;line-height:120%;\"><span style=\"font-size: 18px; line-height: 21.6px;\">Reset Password</span></span>\n" +
                    "    </a>\n" +
                    "    <!--[if mso]></center></v:roundrect><![endif]-->\n" +
                    "</div>\n" +
                    "\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "  </tbody>\n" +
                    "</table>\n" +
                    "\n" +
                    "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                    "  <tbody>\n" +
                    "    <tr>\n" +
                    "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:40px 40px 30px;font-family:'Lato',sans-serif;\" align=\"left\">\n" +
                    "        \n" +
                    "  <div style=\"font-size: 14px; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                    "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"color: #888888; font-size: 14px; line-height: 19.6px;\"><em><span style=\"font-size: 16px; line-height: 22.4px;\">Please ignore this email if you did not request a password change.</span></em></span><br /><span style=\"color: #888888; font-size: 14px; line-height: 19.6px;\"><em><span style=\"font-size: 16px; line-height: 22.4px;\"> </span></em></span></p>\n" +
                    "  </div>\n" +
                    "\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "  </tbody>\n" +
                    "</table>\n" +
                    "\n" +
                    "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                    "  </div>\n" +
                    "</div>\n" +
                    "<!--[if (mso)|(IE)]></td><![endif]-->\n" +
                    "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "  </div>\n" +
                    "  \n" +
                    "\n" +
                    "\n" +
                    "  \n" +
                    "  \n" +
                    "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                    "  <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #18163a;\">\n" +
                    "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                    "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #18163a;\"><![endif]-->\n" +
                    "      \n" +
                    "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 20px 20px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                    "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\n" +
                    "  <div style=\"height: 100%;width: 100% !important;\">\n" +
                    "  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 20px 20px 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n" +
                    "  \n" +
                    "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                    "  <tbody>\n" +
                    "    <tr>\n" +
                    "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:'Lato',sans-serif;\" align=\"left\">\n" +
                    "        \n" +
                    "  <div style=\"font-size: 14px; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                    "    <p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 16px; line-height: 22.4px; color: #ecf0f1;\">Contact</span></p>\n" +
                    "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 14px; line-height: 19.6px; color: #ecf0f1;\">1912  Mcwhorter Road, FL 11223</span></p>\n" +
                    "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 14px; line-height: 19.6px; color: #ecf0f1;\">+111 222 333 | Info@company.com</span></p>\n" +
                    "  </div>\n" +
                    "\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "  </tbody>\n" +
                    "</table>\n" +
                    "\n" +
                    "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                    "  </div>\n" +
                    "</div>\n" +
                    "<!--[if (mso)|(IE)]></td><![endif]-->\n" +
                    "<!--[if (mso)|(IE)]><td align=\"center\" width=\"300\" style=\"width: 300px;padding: 0px 0px 0px 20px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                    "<div class=\"u-col u-col-50\" style=\"max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;\">\n" +
                    "  <div style=\"height: 100%;width: 100% !important;\">\n" +
                    "  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 0px 0px 0px 20px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n" +
                    "  \n" +
                    "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                    "  <tbody>\n" +
                    "    <tr>\n" +
                    "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:25px 10px 10px;font-family:'Lato',sans-serif;\" align=\"left\">\n" +
                    "        \n" +
                    "\n" +
                    "\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "  </tbody>\n" +
                    "</table>\n" +
                    "\n" +
                    "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                    "  <tbody>\n" +
                    "    <tr>\n" +
                    "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:5px 10px 10px;font-family:'Lato',sans-serif;\" align=\"left\">\n" +
                    "        \n" +
                    "  <div style=\"font-size: 14px; line-height: 140%; text-align: left; word-wrap: break-word;\">\n" +
                    "    <p style=\"line-height: 140%; font-size: 14px;\"><span style=\"font-size: 14px; line-height: 19.6px;\"><span style=\"color: #ecf0f1; font-size: 14px; line-height: 19.6px;\"><span style=\"line-height: 19.6px; font-size: 14px;\">Company &copy;&nbsp; All Rights Reserved</span></span></span></p>\n" +
                    "  </div>\n" +
                    "\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "  </tbody>\n" +
                    "</table>\n" +
                    "\n" +
                    "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                    "  </div>\n" +
                    "</div>\n" +
                    "<!--[if (mso)|(IE)]></td><![endif]-->\n" +
                    "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "  </div>\n" +
                    "  \n" +
                    "\n" +
                    "\n" +
                    "  \n" +
                    "  \n" +
                    "<div class=\"u-row-container\" style=\"padding: 0px;background-color: #f9f9f9\">\n" +
                    "  <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #1c103b;\">\n" +
                    "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                    "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: #f9f9f9;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #1c103b;\"><![endif]-->\n" +
                    "      \n" +
                    "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                    "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                    "  <div style=\"height: 100%;width: 100% !important;\">\n" +
                    "  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n" +
                    "  \n" +
                    "<table style=\"font-family:'Lato',sans-serif;\" role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
                    "  <tbody>\n" +
                    "    <tr>\n" +
                    "      <td style=\"overflow-wrap:break-word;word-break:break-word;padding:15px;font-family:'Lato',sans-serif;\" align=\"left\">\n" +
                    "        \n" +
                    "  <table height=\"0px\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #1c103b;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                    "    <tbody>\n" +
                    "      <tr style=\"vertical-align: top\">\n" +
                    "        <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">\n" +
                    "          <span>&#160;</span>\n" +
                    "        </td>\n" +
                    "      </tr>\n" +
                    "    </tbody>\n" +
                    "  </table>\n" +
                    "\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "  </tbody>\n" +
                    "</table>\n" +
                    "\n" +
                    "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                    "  </div>\n" +
                    "</div>\n" +
                    "<!--[if (mso)|(IE)]></td><![endif]-->\n" +
                    "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "  </div>\n" +
                    "  \n" +
                    "\n" +
                    "\n" +
                    "  \n" +
                    "  \n" +
                    "<div class=\"u-row-container\" style=\"padding: 0px;background-color: transparent\">\n" +
                    "  <div class=\"u-row\" style=\"margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #f9f9f9;\">\n" +
                    "    <div style=\"border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;\">\n" +
                    "      <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding: 0px;background-color: transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;\"><tr style=\"background-color: #f9f9f9;\"><![endif]-->\n" +
                    "      \n" +
                    "<!--[if (mso)|(IE)]><td align=\"center\" width=\"600\" style=\"width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\" valign=\"top\"><![endif]-->\n" +
                    "<div class=\"u-col u-col-100\" style=\"max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;\">\n" +
                    "  <div style=\"height: 100%;width: 100% !important;\">\n" +
                    "  <!--[if (!mso)&(!IE)]><!--><div style=\"box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;\"><!--<![endif]-->\n" +
                    "  \n" +
                    "  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->\n" +
                    "  </div>\n" +
                    "</div>\n" +
                    "<!--[if (mso)|(IE)]></td><![endif]-->\n" +
                    "      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "  </div>\n" +
                    "  \n" +
                    "\n" +
                    "\n" +
                    "    <!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                    "    </td>\n" +
                    "  </tr>\n" +
                    "  </tbody>\n" +
                    "  </table>\n" +
                    "  <!--[if mso]></div><![endif]-->\n" +
                    "  <!--[if IE]></div><![endif]-->\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>\n";
            helper.setText(html,true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée (par exemple, en enregistrant dans un fichier journal)
        }
    }
    public void sendVerificationCodeMail(String email,int verificationCode){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");

        try {
            helper.setTo(email);
            helper.setSubject("Verifie your account");
            helper.setText("Dear User,\n\nPlease put this code to verifie your account:\n\n"
                    + verificationCode + "\n\nRegards,\nYour Application Team");
            String html="<!DOCTYPE html>\n" +
                    "\n" +
                    "<html lang=\"en\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" +
                    "<head>\n" +
                    "<title></title>\n" +
                    "<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\n" +
                    "<meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\"/><!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]--><!--[if !mso]><!--><!--<![endif]-->\n" +
                    "<style>\n" +
                    "\t\t* {\n" +
                    "\t\t\tbox-sizing: border-box;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\tbody {\n" +
                    "\t\t\tmargin: 0;\n" +
                    "\t\t\tpadding: 0;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\ta[x-apple-data-detectors] {\n" +
                    "\t\t\tcolor: inherit !important;\n" +
                    "\t\t\ttext-decoration: inherit !important;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t#MessageViewBody a {\n" +
                    "\t\t\tcolor: inherit;\n" +
                    "\t\t\ttext-decoration: none;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\tp {\n" +
                    "\t\t\tline-height: inherit\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.desktop_hide,\n" +
                    "\t\t.desktop_hide table {\n" +
                    "\t\t\tmso-hide: all;\n" +
                    "\t\t\tdisplay: none;\n" +
                    "\t\t\tmax-height: 0px;\n" +
                    "\t\t\toverflow: hidden;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.image_block img+div {\n" +
                    "\t\t\tdisplay: none;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t.menu_block.desktop_hide .menu-links span {\n" +
                    "\t\t\tmso-hide: all;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t@media (max-width:700px) {\n" +
                    "\n" +
                    "\t\t\t.desktop_hide table.icons-inner,\n" +
                    "\t\t\t.social_block.desktop_hide .social-table {\n" +
                    "\t\t\t\tdisplay: inline-block !important;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.icons-inner {\n" +
                    "\t\t\t\ttext-align: center;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.icons-inner td {\n" +
                    "\t\t\t\tmargin: 0 auto;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.image_block div.fullWidth {\n" +
                    "\t\t\t\tmax-width: 100% !important;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.menu-checkbox[type=checkbox]~.menu-links {\n" +
                    "\t\t\t\tdisplay: none !important;\n" +
                    "\t\t\t\tpadding: 5px 0;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.menu-checkbox[type=checkbox]:checked~.menu-trigger .menu-open {\n" +
                    "\t\t\t\tdisplay: none !important;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.menu-checkbox[type=checkbox]:checked~.menu-links,\n" +
                    "\t\t\t.menu-checkbox[type=checkbox]~.menu-trigger {\n" +
                    "\t\t\t\tdisplay: block !important;\n" +
                    "\t\t\t\tmax-width: none !important;\n" +
                    "\t\t\t\tmax-height: none !important;\n" +
                    "\t\t\t\tfont-size: inherit !important;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.menu-checkbox[type=checkbox]~.menu-links>a,\n" +
                    "\t\t\t.menu-checkbox[type=checkbox]~.menu-links>span.label {\n" +
                    "\t\t\t\tdisplay: block !important;\n" +
                    "\t\t\t\ttext-align: center;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.menu-checkbox[type=checkbox]:checked~.menu-trigger .menu-close {\n" +
                    "\t\t\t\tdisplay: block !important;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.mobile_hide {\n" +
                    "\t\t\t\tdisplay: none;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.row-content {\n" +
                    "\t\t\t\twidth: 100% !important;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.stack .column {\n" +
                    "\t\t\t\twidth: 100%;\n" +
                    "\t\t\t\tdisplay: block;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.mobile_hide {\n" +
                    "\t\t\t\tmin-height: 0;\n" +
                    "\t\t\t\tmax-height: 0;\n" +
                    "\t\t\t\tmax-width: 0;\n" +
                    "\t\t\t\toverflow: hidden;\n" +
                    "\t\t\t\tfont-size: 0px;\n" +
                    "\t\t\t}\n" +
                    "\n" +
                    "\t\t\t.desktop_hide,\n" +
                    "\t\t\t.desktop_hide table {\n" +
                    "\t\t\t\tdisplay: table !important;\n" +
                    "\t\t\t\tmax-height: none !important;\n" +
                    "\t\t\t}\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t#memu-r6c1m4:checked~.menu-links {\n" +
                    "\t\t\tbackground-color: #000000 !important;\n" +
                    "\t\t}\n" +
                    "\n" +
                    "\t\t#memu-r6c1m4:checked~.menu-links a,\n" +
                    "\t\t#memu-r6c1m4:checked~.menu-links span {\n" +
                    "\t\t\tcolor: #ffffff !important;\n" +
                    "\t\t}\n" +
                    "\t</style>\n" +
                    "</head>\n" +
                    "<body style=\"background-color: #d2e7f5; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #d2e7f5;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 680px; margin: 0 auto;\" width=\"680\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:30px;line-height:30px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-2\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 680px; margin: 0 auto;\" width=\"680\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"33.333333333333336%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:0px;line-height:0px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "<td class=\"column column-2\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"33.333333333333336%\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"pad\" style=\"width:100%;padding-right:0px;padding-left:0px;\">\n" +
                    "<div align=\"center\" class=\"alignment\" style=\"line-height:10px\">\n" +
                    "<div style=\"max-width: 102px;\">\n" +
                    "    <img alt=\"Company Logo\" height=\"auto\" src=\"https://cdn.templates.unlayer.com/assets/1597218650916-xxxxc.png\" style=\"display: block; height: auto; border: 0; width: 100%;\" title=\"Company Logo\" width=\"102\"/>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "<td class=\"column column-3\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"33.333333333333336%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:0px;line-height:0px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-3\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 680px; margin: 0 auto;\" width=\"680\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:10px;line-height:10px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-4\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 680px; margin: 0 auto;\" width=\"680\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"pad\" style=\"width:100%;padding-right:0px;padding-left:0px;\">\n" +
                    "<div align=\"center\" class=\"alignment\" style=\"line-height:10px\">\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<div class=\"spacer_block block-2\" style=\"height:5px;line-height:5px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-5\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 680px; margin: 0 auto;\" width=\"680\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"16.666666666666668%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:0px;line-height:0px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "<td class=\"column column-2\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"66.66666666666667%\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"heading_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"pad\" style=\"text-align:center;width:100%;\">\n" +
                    "<h1 style=\"margin: 0; color: #03191e; direction: ltr; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; font-size: 40px; font-weight: normal; letter-spacing: normal; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0; mso-line-height-alt: 48px;\"><strong>Check</strong></h1>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"heading_block block-2\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"pad\" style=\"padding-bottom:10px;text-align:center;width:100%;\">\n" +
                    "<h1 style=\"margin: 0; color: #03191e; direction: ltr; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; font-size: 40px; font-weight: normal; letter-spacing: normal; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0; mso-line-height-alt: 300px;\"><strong>Your Code Verification</strong></h1>\n" + verificationCode+
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"paragraph_block block-3\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"pad\" style=\"padding-bottom:10px;padding-left:20px;padding-right:10px;padding-top:10px;\">\n" +
                    "<div style=\"color:#848484;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;font-size:14px;line-height:180%;text-align:center;mso-line-height-alt:25.2px;\">\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<div class=\"spacer_block block-4\" style=\"height:10px;line-height:10px;font-size:1px;\"> </div>\n" +
                    "<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"button_block block-5\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"pad\">\n" +
                    "<div align=\"center\" class=\"alignment\"><!--[if mso]>\n" +
                    "<v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"www.example.com\" style=\"height:47px;width:162px;v-text-anchor:middle;\" arcsize=\"10%\" strokeweight=\"0.75pt\" strokecolor=\"#03191E\" fillcolor=\"#03191e\">\n" +
                    "<w:anchorlock/>\n" +
                    "<v:textbox inset=\"0px,0px,0px,0px\">\n" +
                    "<center style=\"color:#ffffff; font-family:Arial, sans-serif; font-size:16px\">\n" +
                    "<![endif]--><!--[if mso]></center></v:textbox></v:roundrect><![endif]--></div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<div class=\"spacer_block block-6\" style=\"height:20px;line-height:20px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "<td class=\"column column-3\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"16.666666666666668%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:0px;line-height:0px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-6\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 680px; margin: 0 auto;\" width=\"680\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"16.666666666666668%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:0px;line-height:0px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "<td class=\"column column-2\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; background-color: #ffffff; border-bottom: 6px solid #D2E7F5; border-left: 6px solid #D2E7F5; border-right: 6px solid #D2E7F5; border-top: 6px solid #D2E7F5; padding-bottom: 5px; padding-top: 5px; vertical-align: top;\" width=\"33.333333333333336%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:20px;line-height:20px;font-size:1px;\"> </div>\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"heading_block block-2\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"pad\" style=\"text-align:center;width:100%;\">\n" +
                    "<h1 style=\"margin: 0; color: #03191e; direction: ltr; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; font-size: 26px; font-weight: normal; letter-spacing: normal; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0; mso-line-height-alt: 31.2px;\"><strong>Call</strong></h1>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"paragraph_block block-3\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"pad\" style=\"padding-bottom:10px;padding-left:20px;padding-right:10px;padding-top:10px;\">\n" +
                    "<div style=\"color:#03191e;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;font-size:14px;line-height:180%;text-align:center;mso-line-height-alt:25.2px;\">\n" +
                    "<p style=\"margin: 0; word-break: break-word;\"><span> +216 71 167 300</span></p>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<div class=\"spacer_block block-4\" style=\"height:20px;line-height:20px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "<td class=\"column column-3\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; background-color: #ffffff; border-bottom: 6px solid #D2E7F5; border-left: 6px solid #D2E7F5; border-right: 6px solid #D2E7F5; border-top: 6px solid #D2E7F5; padding-bottom: 5px; padding-top: 5px; vertical-align: top;\" width=\"33.333333333333336%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:20px;line-height:20px;font-size:1px;\"> </div>\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"heading_block block-2\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"pad\" style=\"text-align:center;width:100%;\">\n" +
                    "<h1 style=\"margin: 0; color: #03191e; direction: ltr; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; font-size: 26px; font-weight: normal; letter-spacing: normal; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0; mso-line-height-alt: 31.2px;\"><strong>Reply</strong></h1>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"paragraph_block block-3\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"pad\" style=\"padding-bottom:10px;padding-left:20px;padding-right:10px;padding-top:10px;\">\n" +
                    "<div style=\"color:#03191e;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;font-size:14px;line-height:180%;text-align:center;mso-line-height-alt:25.2px;\">\n" +
                    "<p style=\"margin: 0; word-break: break-word;\"><a href=\"mailto:Support@soprahr.com\" rel=\"noopener\" style=\"text-decoration: none; color: #03191e;\" target=\"_blank\" title=\"Support@company.com\"><span>Support@company.com</span></a></p>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<div class=\"spacer_block block-4\" style=\"height:20px;line-height:20px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "<td class=\"column column-4\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"16.666666666666668%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:0px;line-height:0px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-7\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 680px; margin: 0 auto;\" width=\"680\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"16.666666666666668%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:25px;line-height:25px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "<td class=\"column column-2\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"66.66666666666667%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:25px;line-height:25px;font-size:1px;\"> </div>\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"heading_block block-2\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"pad\" style=\"text-align:center;width:100%;\">\n" +
                    "<h1 style=\"margin: 0; color: #03191e; direction: ltr; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; font-size: 20px; font-weight: normal; letter-spacing: normal; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0; mso-line-height-alt: 24px;\"><strong>Contact us</strong></h1>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"paragraph_block block-3\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"pad\" style=\"padding-bottom:10px;padding-left:20px;padding-right:10px;padding-top:10px;\">\n" +
                    "<div style=\"color:#848484;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;font-size:14px;line-height:180%;text-align:center;mso-line-height-alt:25.2px;\">\n" +
                    "<p style=\"margin: 0; word-break: break-word;\"><span>If There is Any Problem ! Don't Forget to contact us</span></p>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"social_block block-4\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"pad\" style=\"padding-bottom:15px;padding-left:10px;padding-right:10px;padding-top:10px;text-align:center;\">\n" +
                    "<div align=\"center\" class=\"alignment\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"social-table\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; display: inline-block;\" width=\"144px\">\n" +
                    "<tr>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"menu_block block-5\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"pad\" style=\"color:#101010;font-family:inherit;font-size:14px;text-align:center;\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"alignment\" style=\"text-align:center;font-size:0px;\"><!--[if !mso]><!--><input class=\"menu-checkbox\" id=\"memu-r6c1m4\" style=\"display:none !important;max-height:0;visibility:hidden;\" type=\"checkbox\"/><!--<![endif]-->\n" +
                    "<div class=\"menu-trigger\" style=\"display:none;max-height:0px;max-width:0px;font-size:0px;overflow:hidden;\"><label class=\"menu-label\" for=\"memu-r6c1m4\" style=\"height: 36px; width: 36px; display: inline-block; cursor: pointer; mso-hide: all; user-select: none; align: center; text-align: center; color: #ffffff; text-decoration: none; background-color: #000000; border-radius: 0;\"><span class=\"menu-open\" style=\"mso-hide:all;font-size:26px;line-height:31.5px;\">☰</span><span class=\"menu-close\" style=\"display:none;mso-hide:all;font-size:26px;line-height:36px;\">✕</span></label></div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "<td class=\"column column-3\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"16.666666666666668%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:25px;line-height:25px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-8\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 680px; margin: 0 auto;\" width=\"680\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"16.666666666666668%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:0px;line-height:0px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "<td class=\"column column-2\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"66.66666666666667%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:35px;line-height:35px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "<td class=\"column column-3\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"16.666666666666668%\">\n" +
                    "<div class=\"spacer_block block-1\" style=\"height:0px;line-height:0px;font-size:1px;\"> </div>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-9\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff;\" width=\"100%\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td>\n" +
                    "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; background-color: #ffffff; width: 680px; margin: 0 auto;\" width=\"680\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td class=\"column column-1\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"icons_block block-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; text-align: center;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"pad\" style=\"vertical-align: middle; color: #1e0e4b; font-family: 'Inter', sans-serif; font-size: 15px; padding-bottom: 5px; padding-top: 5px; text-align: center;\">\n" +
                    "<table cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                    "<tr>\n" +
                    "<td class=\"alignment\" style=\"vertical-align: middle; text-align: center;\"><!--[if vml]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"display:inline-block;padding-left:0px;padding-right:0px;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"><![endif]-->\n" +
                    "<!--[if !vml]><!-->\n" +
                    "<table cellpadding=\"0\" cellspacing=\"0\" class=\"icons-inner\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; display: inline-block; margin-right: -4px; padding-left: 0px; padding-right: 0px;\"><!--<![endif]-->\n" +
                    "<tr>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table><!-- End -->\n" +
                    "</body>\n" +
                    "</html>";

            helper.setText(html,true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée (par exemple, en enregistrant dans un fichier journal)
        }
    }






    public void sendQRCodeByEmail(String email, byte[] qrCodeImage) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // Activer le mode multipart
            helper.setTo(email);
            helper.setSubject("Your Parking Reservation QR Code");

            // Créer le contenu HTML du message avec une image en ligne
            String htmlContent = "<p>Dear User,</p>" +
                    "<p>Please find attached your parking reservation QR code.</p>" +
                    "<p>Below is the logo:</p>" +
                    "<img src='cid:logo' alt='Logo'>" +
                    "<p>Regards,<br>Your Application Team</p>";

            helper.setText(htmlContent, true); // Indiquer que le contenu est au format HTML

            // Redimensionner l'image du QR code
            BufferedImage resizedImage = resizeImage(qrCodeImage, 200, 200); // Réduire la résolution à 200x200 pixels

            // Attachement du QR code
            ByteArrayResource qrCodeResource = new ByteArrayResource(imageToByteArray(resizedImage));
            helper.addAttachment("qr_code.png", qrCodeResource);

            // Chargement du logo à partir du chemin local
            FileSystemResource logoFile = new FileSystemResource("C:\\Users\\PC\\Desktop\\pfe-Work\\SopraHRProject\\front-ParkWise\\src\\assets\\img\\images\\logo\\logo-light.png");

            helper.addInline("logo", logoFile); // Ajouter le logo en tant qu'image en ligne

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée (par exemple, en enregistrant dans un fichier journal)
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode pour redimensionner l'image
    private BufferedImage resizeImage(byte[] imageData, int width, int height) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        BufferedImage originalImage = ImageIO.read(bais);

        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();

        return resizedImage;
    }

    // Méthode pour convertir une BufferedImage en tableau de bytes
    private byte[] imageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }




}
