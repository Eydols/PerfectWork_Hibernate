package controllers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.context.RequestContext;

@ManagedBean
@RequestScoped
public class MailController {

    private SessionUtils sessionUtils = SessionUtils.getInstance();

    private String message;
    private String subject;

    private final String MAIL_SUPPORT_PARAM = "mail.SUPPORT";

    public MailController() {
    }

    public void sendMessage() throws AddressException {
        try {
            if (sessionUtils.isNullOrEmpty(message) || sessionUtils.isNullOrEmpty(subject)) {
                sessionUtils.failValidation("required_fields");
                return;
            }

            String mailRecipient = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(MAIL_SUPPORT_PARAM);
            
            Message msg = new MimeMessage(getSession());
            
            InternetAddress address = new InternetAddress(mailRecipient);
            
            Multipart multipart = new MimeMultipart();
            
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText(getMessage());
            
            multipart.addBodyPart(bodyPart);
            
            msg.setSubject(sessionUtils.getBundleMessage("message_form") + sessionUtils.getUserName() + ": \"" + getSubject() + "\"");
            msg.setRecipient(RecipientType.TO, address);
            msg.setContent(multipart);
            
            Transport.send(msg);
            
            hideMailForm();
            
            sessionUtils.showMessage("mail_sended");

        } catch (NamingException ex) {
            Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException me) {
            Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, me);
        }
    }
    
    private Session getSession() throws NamingException {
    InitialContext ic = new InitialContext();
    return (javax.mail.Session) ic.lookup("java:comp/env/mail/GMAIL");
    }
    
    public void showMailForm() {
    RequestContext.getCurrentInstance().execute("PF('dlgSendMail').show()");
    }
    
    public void hideMailForm() {
    RequestContext.getCurrentInstance().execute("PF('dlgSendMail').hide()");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
   
}
