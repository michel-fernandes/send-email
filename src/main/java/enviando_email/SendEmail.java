package enviando_email;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendEmail {
    private static final String GMAIL_EMAIL = System.getenv("GMAIL_JAVA_MAIL");
	private static final String GMAIL_PASS = System.getenv("GMAIL_JAVA_PASS");
    private String adressesTo = "";
    private String sendBy = "";
    private String subject = "";
    private String body = "";
     
    public SendEmail(String adressesTo, String sendBy, String subject, String body) {
        this.adressesTo = adressesTo;
        this.sendBy = sendBy;
        this.subject = subject;
        this.body = body;
    }

    public boolean sendEmail(boolean htmlFormat){
        try {
    	
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.trust", "*");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls", "true");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.socketFactory.port", "465");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(GMAIL_EMAIL, GMAIL_PASS);
                }
            });
            
            Address [] toUser = InternetAddress.parse(adressesTo);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(GMAIL_EMAIL, sendBy));
            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(subject);

            if(htmlFormat){
                message.setContent(body, "text/html; charset=utf-8");;
            }else{
                message.setText(body);
            }
    
            Transport.send(message);
            
        }catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


}
