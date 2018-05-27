package services

import javax.mail.internet.{InternetAddress, MimeMessage}
import javax.mail._

object MailSender {
  def send(msg: String, to: String): Unit = {
    val host = "smtp.poczta.onet.pl"
    val username = "fixme.enterprise"
    val email = "fixme.enterprise@op.pl"
    val password = "Dupa1234"

    val props = System.getProperties

    props.put("mail.smtp.host", host)
    props.put("mail.smtp.socketFactory.port", "465")
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
    //props.put("mail.smtp.user", email)
    //props.put("mail.smtp.password", password)
    props.put("mail.smtp.port", "465")
    props.put("mail.smtp.auth", "true")

    val session = Session.getInstance(props, new Authenticator() {
      override protected def getPasswordAuthentication = new PasswordAuthentication(email, password)
    })
    //val transport = session.getTransport("smtp")
    try {
      val message = new MimeMessage(session)
      message.setFrom(new InternetAddress(email))
      message.addRecipient(Message.RecipientType.TO,
        new InternetAddress(to))
      message.setSubject("Account validation")
      message.setText(msg)
      Transport.send(message)
      //transport.connect(host, username, password)
      //transport.sendMessage(message, message.getAllRecipients)
      //transport.close()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      //transport.close()
    }
  }
}