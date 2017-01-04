package servlets;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;

/**
 * This servlet is send email.
 * 
 * @author Filip
 * @version 1.0
 */
@WebServlet(urlPatterns = { "/servlets/sendEmail" })
public class SendEmailServlet extends HttpServlet {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Treba napravit neki gmail account samo za ove pizdarije
	 */
	final String username = "oppgroupteflja@gmail.com";
	final String password = "Qwertzuiop1";


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String emailTo = req.getParameter("email");
		String userMessage = "Tekst koji je korisnik natipkao:\n" + req.getParameter("text") + "\n";

		System.out.println(name);

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(username, name));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));

			message.setSubject("Pristigao troškovnik od korisnika" + name);
			message.setText(userMessage);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

		resp.sendRedirect("/troskovnik-zivota/home2.html");
	}
}
