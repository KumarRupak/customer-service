/*;==========================================
; Title:  Service class for email
; Author: Rupak Kumar
; Date:   17 Sep 2021
;==========================================*/
package demo.wsc.beta.service.mail


import kotlin.Throws
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.*
import javax.mail.*
import javax.mail.internet.MimeMessage
import javax.mail.internet.InternetAddress

@Service
 class ServiceMailProvider : ServiceMail {
    val WebSmartCredit = "WebSmartCredit"

    companion object {
        private const val sender = "noreply.wscredit@gmail.com"
        private const val password = "rupakkum"
    }

    private lateinit var properties: Properties
    val property: Properties
        get() {
            properties = Properties()
            properties["mail.smtp.auth"] = "true"
            properties["mail.smtp.starttls.enable"] = "true"
            properties["mail.smtp.host"] = "smtp.gmail.com"
            properties["mail.smtp.port"] = "587"
            return properties
        }

    @Throws(MessagingException::class)
    override fun sendOtp(reciver: String?): Int {
        properties = property
        var oTp = "0"
        var flag: Boolean
        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(sender, password)
            }
        })
        while (oTp.length < 4) {
            oTp = String.format("%04d", Random().nextInt(10000))
        }
        val message = MessageOtp(session, sender, reciver, oTp)
        try {
            flag = true
            Transport.send(message)
        } catch (e: MessagingException) {
            flag = false
            e.message
        }
        return if (flag == true) oTp.toInt() else 0
    }

    @Throws(MessagingException::class)
    override fun sendPredctionResult(reciver: String?, predction: Int) {
        properties = property
        val properties = properties
        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(sender, password)
            }
        })
        val message = MessageSendPredction(session, sender, reciver, predction)
        try {
            Transport.send(message)
        } catch (e: Exception) {
            e.message
        }
    }

    @Throws(MessagingException::class)
    override fun sendPinGeneration(reciver: String?, cardNo: String?, transactionLimit: Long?) {
        properties = property
        val properties = properties
        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(sender, password)
            }
        })
        val message = MessageGeneratePin(session, sender, reciver, cardNo, transactionLimit)
        try {
            Transport.send(message)
        } catch (e: Exception) {
            e.message
        }
    }

    @Throws(MessagingException::class)
    override fun sendSetTransactionLimit(reciver: String?, transactionLimit: Long?) {
        properties = property
        val properties = properties
        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(sender, password)
            }
        })
        val message = MessageTransactionLimitSet(session, sender, reciver, transactionLimit)
        try {
            Transport.send(message)
        } catch (e: Exception) {
            e.message
        }
    }


    @Throws(MessagingException::class)
    override fun sendCreditAccount(reciver: String?, amount: Long?, accountNo: Long?) {
        properties = property
        val properties = properties
        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(sender, password)
            }
        })
        val message = MessageCreditAccount(session, sender, reciver, amount, accountNo)
        try {
            Transport.send(message)
        } catch (e: Exception) {
            e.message
        }
    }

    @Throws(MessagingException::class)
    override fun sendDebitAccount(reciver: String?, amount: Long?, accountNo: Long?) {
        properties = property
        val properties = properties
        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(sender, password)
            }
        })
        val message = MessageDebitAccount(session, sender, reciver, amount, accountNo)
        try {
            Transport.send(message)
        } catch (e: Exception) {
            e.message
        }
    }


    @Throws(MessagingException::class)
    override fun sendAccountDetails(reciver: String?, customerId: Int, mPIN: Int) {
        properties = property
        val properties = properties
        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(sender, password)
            }
        })
        val message = messageSendAccountDetails(session, sender, reciver, customerId, mPIN)
        try {
            Transport.send(message)
        } catch (e: Exception) {
            e.message
        }
    }

    @Throws(MessagingException::class)
    override fun sendCredit(reciver: String?, amount: Long?, cardNo: String?) {
        properties = property
        val properties = properties
        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(sender, password)
            }
        })
        val message = MessageCredit(session, sender, reciver, amount, cardNo)
        try {
            Transport.send(message)
        } catch (e: Exception) {
            e.message
        }
    }

    @Throws(MessagingException::class)
    override fun sendAlert(reciver: String?, customerId: Int) {
        properties = property
        val properties = properties
        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(sender, password)
            }
        })
        val message = MessageAlert(session, sender, reciver, customerId)
        try {
            Transport.send(message)
        } catch (e: Exception) {
            e.message
        }
    }


    private fun MessageAlert(
        session: Session,
        sender:  String,
        reciver: String?,
        customerId: Int
    ): Message? {
        val message: Message = MimeMessage(session)
        return try {
            message.setFrom(InternetAddress(sender))
            message.setRecipient(Message.RecipientType.TO, InternetAddress(reciver))
            message.subject = "Security Alert ${WebSmartCredit}"
            message.setText("Dear Customer \n\nSomeone trying to access your account with Id : $customerId"+
                    "\n\nIf you do not recognize this sign-in attempt please contact to the admin. maximum one attempt left" +
                    "\n\nThanks team WSC")
            message
        } catch (e: Exception) {
            e.message
            null
        }
    }

    private fun MessageCredit(
        session: Session,
        sender: String,
        reciver: String?,
        amount: Long?,
        cardNo: String?
    ): Message? {
        val message: Message = MimeMessage(session)
        return try {
            message.setFrom(InternetAddress(sender))
            message.setRecipient(Message.RecipientType.TO, InternetAddress(reciver))
            message.subject = "Notification ${WebSmartCredit}"
            message.setText("Dear Customer \n\nAmount of INR $amount\n\nhas been credited to your credit card $cardNo\n\nThanks team WSC")
            message
        } catch (e: Exception) {
            e.message
            null
        }
    }

    private fun messageSendAccountDetails(
        session: Session,
        sender: String,
        reciver: String?,
        customerId: Int,
        mpin: Int
    ): Message? {
        val message: Message = MimeMessage(session)
        return try {
            message.setFrom(InternetAddress(sender))
            message.setRecipient(Message.RecipientType.TO, InternetAddress(reciver))
            message.subject = "Notification ${WebSmartCredit}"
            message.setText(
                """Dear User 

Your user id for access the service is : $customerId

Your MPIN is : $mpin

Don't share the details with any one. 

Thanks team WSC"""
            )
            message
        } catch (e: Exception) {
            e.message
            null
        }
    }


    private fun MessageDebitAccount(
        session: Session,
        sender: String,
        reciver: String?,
        amount: Long?,
        accountNo: Long?
    ): Message? {
        val message: Message = MimeMessage(session)
        return try {
            message.setFrom(InternetAddress(sender))
            message.setRecipient(Message.RecipientType.TO, InternetAddress(reciver))
            message.subject = "Notification ${WebSmartCredit}"
            message.setText(
                """
    Dear Customer 
    
    Amount of INR ${amount}has been debited from your account $accountNo
    
    Thanks team WSC
    """.trimIndent()
            )
            message
        } catch (e: Exception) {
            e.message
            null
        }
    }

    private fun MessageCreditAccount(
        session: Session,
        sender: String,
        reciver: String?,
        amount: Long?,
        accountNo: Long?
    ): Message? {
        val message: Message = MimeMessage(session)
        return try {
            message.setFrom(InternetAddress(sender))
            message.setRecipient(Message.RecipientType.TO, InternetAddress(reciver))
            message.subject = "Notification ${WebSmartCredit}"
            message.setText(
                """
    Dear Customer 
    
    Amount of INR ${amount} has been credited to your account $accountNo
    
    Thanks team WSC
    """.trimIndent()
            )
            message
        } catch (e: Exception) {
            e.message
            null
        }
    }


    private fun MessageGeneratePin(
        session: Session,
        sender: String,
        reciver: String?,
        cardNo: String?,
        transactionLimit: Long?
    ): Message? {
        val message: Message = MimeMessage(session)
        return try {
            message.setFrom(InternetAddress(sender))
            message.setRecipient(Message.RecipientType.TO, InternetAddress(reciver))
            message.subject = "Notification ${WebSmartCredit}"
            message.setText("Dear customer \n\nYour pin successfully generated for card: $cardNo\n\nDaily transaction limit upto: $transactionLimit\n\nThanks team WSC")
            message
        } catch (e: Exception) {
            e.message
            null
        }
    }

    private fun MessageTransactionLimitSet(
        session: Session,
        sender: String,
        reciver: String?,
        transactionLimit: Long?
    ): Message? {
        val message: Message = MimeMessage(session)
        return try {
            message.setFrom(InternetAddress(sender))
            message.setRecipient(Message.RecipientType.TO, InternetAddress(reciver))
            message.subject = "Notification ${WebSmartCredit}"
            message.setText("Dear customer \n\n Your saily transaction limit set upto: $transactionLimit\n\nThanks team WSC")
            message
        } catch (e: Exception) {
            e.message
            null
        }
    }

    private fun MessageOtp(session: Session, sender: String, reciver: String?, oTp: String): Message? {
        val message: Message = MimeMessage(session)
        return try {
            message.setFrom(InternetAddress(sender))
            message.setRecipient(Message.RecipientType.TO, InternetAddress(reciver))
            message.subject = "Onetime OTP ${WebSmartCredit}"
            message.setText("Dear customer \n\nYour one time OTP is: $oTp\n\nThank You\n\nTeam WSC")
            message
        } catch (e: Exception) {
            e.message
            null
        }
    }

    private fun MessageSendPredction(session: Session, sender: String, reciver: String?, predction: Int): Message? {
        val message: Message = MimeMessage(session)
        return try {
            message.setFrom(InternetAddress(sender))
            message.setRecipient(Message.RecipientType.TO, InternetAddress(reciver))
            message.subject = "Notification ${WebSmartCredit}"
            if (predction == 1) message.setText(
                """
    Dear customer 
    
    Congratulation you are elegible for the credit. 
    
    Thank You
    
    Team WSC
    """.trimIndent()
            ) else message.setText(
                """Dear customer 

Thanks for your interest 

 Unfortunatly you are not elegible for the credit. 

Thank You

Team WSC"""
            )
            message
        } catch (e: Exception) {
            e.message
            null
        }
    }


}