package enviando_email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class SendEmail {
    private static final String GMAIL_EMAIL = System.getenv("GMAIL_JAVA_MAIL");
	private static final String GMAIL_PASS = System.getenv("GMAIL_JAVA_PASS");
    private static final String PDF_FILE_PATH = "attachment.pdf";
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

    public boolean sendEmail(boolean htmlFormat, boolean hasAtacchment){
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

            if(hasAtacchment){
                MimeBodyPart emailbBodyPart = new MimeBodyPart();
            
                if(htmlFormat){
                    emailbBodyPart.setContent(body, "text/html; charset=utf-8");;
                }else{
                    emailbBodyPart.setText(body);
                }
            
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.setDataHandler(new DataHandler(new ByteArrayDataSource(pdfSimulatoStream(), "application/pdf")));
                attachmentPart.setFileName(PDF_FILE_PATH);

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(emailbBodyPart);
                multipart.addBodyPart(attachmentPart);

                message.setContent(multipart);

            } else{
                if(htmlFormat){
                message.setContent(body, "text/html; charset=utf-8");;
                }else{
                    message.setText(body);
                }
            }

            Transport.send(message);
            
        }catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /*
     * Esse método simula o pdf que pode ter origem em outras fontes como BD, fileSystem
     * e retorna um pdf com o texto paragrafo como exemplo
     */
    private FileInputStream pdfSimulatoStream() throws Exception{
        Document document = new Document();
        File file = new File(PDF_FILE_PATH);
        file.createNewFile();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        document.add(new Paragraph("Conteúdo do documento PDF"));
        document.close();
        return new FileInputStream(file);
    }
}
