/*;==========================================
; Title:  Customer Services Provider
; Author: Rupak Kumar
; Date:   17 Sep 2021
;==========================================*/
package demo.wsc.beta.service.customer




import demo.wsc.beta.algorithms.CibilCalculate.CibilCalculator
import demo.wsc.beta.algorithms.ML.Knn
import demo.wsc.beta.algorithms.PasswordEncode.Encoder
import org.springframework.beans.factory.annotation.Autowired
import demo.wsc.beta.service.mail.ServiceMailProvider
import kotlin.Throws
import demo.wsc.beta.exceptions.WSCExceptionInvalidModeldata
import javax.mail.MessagingException
import demo.wsc.beta.exceptions.WSCExceptionInvalidUser
import demo.wsc.beta.exceptions.WSCExceptionInvalidDetails
import demo.wsc.beta.model.*
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.LocalDate
import demo.wsc.beta.model.transport.*
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import demo.wsc.beta.model.CibilStatus
import demo.wsc.beta.repository.*
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory

import java.util.*

@Service
@Slf4j
 class ServiceCustomerProvider : ServiceCustomer {

    private var otp = 0

    @Autowired
    private lateinit var repoCusDetails: CustomerDetailsRepository

    @Autowired
    private lateinit var repoCusAuth: AuthCustomerRepository

    @Autowired
    private lateinit var repoCredit: CreditRepository

    @Autowired
    private lateinit var serviceMail: ServiceMailProvider

    @Autowired
    private lateinit var repoCusTrans: CustomerTransactionsRepository

    @Autowired
    private lateinit var repoAdmin: WSCOwnerRepository

    @Autowired
    private lateinit var repAutoPay: PayAutoEmiRepository

    @Autowired
    private lateinit var repoCards: WSCCardsRepository

    @Autowired
    lateinit var repoCusPred: CustomerPredictionsRepository

    val log = LoggerFactory.getLogger(ServiceMailProvider::class.java)!!



    /**
     * Adding a new customer to repository
     *
     * @param 'customer details-DTO'
     * @return - Registration Status
     */
    @Throws(WSCExceptionInvalidModeldata::class, MessagingException::class)
    override fun addCustomer(accountDetails: OpenAccount): PublishCustomerStatus {

        //
          if (!repoCusDetails.findByAccountNumber(accountDetails.accountNumber!!).isPresent && !repoCusDetails.findByPanId(accountDetails.panId).isPresent) {
            //
            val customer = CustomerDetails(accountDetails)
            val authCustomer = AuthCustomer(accountDetails)
            var id = "0"
            while (id.length < 6) {
                id = String.format("%06d", Random().nextInt(999999))
            }
            customer.customerId = id.toInt()
            authCustomer.customerId=(customer.customerId)
            id = "0"
            while (id.length < 4) {
                id = String.format("%04d", Random().nextInt(10000))
            }
            authCustomer.mpin=(id.toInt())
            repoCusDetails.save(customer)
            repoCusAuth.save(authCustomer)
            serviceMail.sendAccountDetails(customer.email, authCustomer.customerId, authCustomer.mpin)
            log.info("User registered with ${accountDetails.email}")
           return PublishCustomerStatus(authCustomer.customerId, authCustomer.mpin)
        } else {
            throw WSCExceptionInvalidModeldata()
        }
    }


    /**
     * Find out the credit elegibility for the customer
     *
     * @param 'customer test data-DTO'
     * @return - defaulter or not (0/1)
     */
    @Throws(WSCExceptionInvalidUser::class, MessagingException::class)
    override fun creditPredction(testData: CustomerPredctionsTransport): Int {
        val data = CustomerPredctions(testData)
        val probability = Knn.predict(data, repoCusPred.findAll() as List<CustomerPredctions>, 3)
        val customer = repoCusDetails.findById(data.customerId)
        return if (customer.isPresent && probability > -1) {
            if (probability == 1) {
                data.defaulter = 1
                repoCusPred.save(data)
                serviceMail.sendPredctionResult(customer.get().email, 1)
            } else {
                data.defaulter = 0
                repoCusPred.save(data)
                serviceMail.sendPredctionResult(customer.get().email, 0)
            }
            customer.get().cardEligibility = 1
            customer.get().cardType = testData.cardType
            repoCusDetails.save(customer.get())
            probability
        } else {
            throw WSCExceptionInvalidUser()
        }
    }


    /**
     * Fetching of all credit card details from repository
     *
     * @param 'customer Id'
     * @return - List of Credit details
     */
    override fun getAllCards(customerId: Int): List<Credit> {
        return repoCredit.findAllCards(customerId)
    }

    /**
     * Fetching the details of any particular card based on the card number
     *
     * @param 'Card Number'
     * @return - Credit Details
     */
    @Throws(WSCExceptionInvalidDetails::class)
    override fun getCardDetails(cardNumber: String): Credit {
        return if (repoCredit.findById(cardNumber).isPresent) {
            val card=repoCredit.findById(cardNumber).get()
            card
        } else {
            throw WSCExceptionInvalidDetails()
        }
    }

    /**
     * Fetching the profile of the customer based on the customer id
     *
     * @param 'customer id'
     * @return - Customer Details
     */
    @Throws(WSCExceptionInvalidUser::class)
    override fun getProfile(customerId: Int): CustomerDetails {
        return if (repoCusDetails.findById(customerId).isPresent) {
            repoCusDetails.findById(customerId).get()
        } else {
            throw WSCExceptionInvalidUser()
        }
    }

    /**
     * Generating a new pin for the credit card based on the given otp details by the customer
     *
     * @param 'GeneratePIn- DTO'
     * @return - Boolean
     */
    @Throws(WSCExceptionInvalidDetails::class, MessagingException::class)
    override fun generatePin(data: GeneratePin): Boolean {
        var flag = false
        if (data.pin > 0 && data.otp > 0) {
            val card = repoCredit.findById(data.cardNumber)
            if (card.isPresent) {
                if (otp > 1 && otp == data.otp) {
                    card.get().cardPin=(data.pin)
                    card.get().cardFlag=(1)
                    repoCredit.save(card.get())
                    serviceMail.sendPinGeneration(
                        repoCusDetails.findById(card.get().customerId).get().email,
                        card.get().cardNumber,
                        repoCusAuth.findById(card.get().customerId).get().transactionLimit
                    )
                    otp = 0
                    flag = true
                }
            } else {
                throw WSCExceptionInvalidDetails()
            }
        }
        return flag
    }

    /**
     * Sending a otp to the customers email id
     *
     * @param 'customer Id'
     * @return - otp
     */
    @Throws(MessagingException::class)
    override fun sendOTP(customerId: Int): Int {
        val customer = repoCusDetails.findById(customerId)
        if (customer.isPresent) {
            otp = serviceMail.sendOtp(customer.get().email)
            return otp
        }
        return 0
    }

    /**
     * Block the credit card based on the card number provided by the customer
     *
     * @param 'card Number'
     * @return - Boolean
     */
    @Throws(WSCExceptionInvalidDetails::class)
    override fun blockCard(cardNumber: String): Boolean {
            val card = repoCredit.findById(cardNumber)
            return if (card.isPresent) {
                card.get().cardFlag=(2)
                repoCredit.save(card.get())
                true
            } else {
                throw WSCExceptionInvalidDetails()
            }
    }

    /**
     * Update the existing transaction limt for the customers personal account
     *
     * @param 'SetTransactionLimit-DTO'
     * @return - Boolean
     */
    @Throws(WSCExceptionInvalidDetails::class, MessagingException::class)
    override fun setTransactionLimit(limit: SetTransactionLimit): Boolean {
        if (limit.customerId > 0 && limit.transactionLimit > 0 && limit.mpin > 0) {
            if (repoCusAuth.findById(limit.customerId).get().mpin== limit.mpin) {
                val authUser = repoCusAuth.findById(limit.customerId)
                if (authUser.isPresent) {
                    authUser.get().transactionLimit=(limit.transactionLimit)
                    repoCusAuth.save(authUser.get())
                    serviceMail.sendSetTransactionLimit(
                        repoCusDetails.findById(limit.customerId).get().email,
                        limit.transactionLimit
                    )
                    return true
                }
            }
        } else {
            throw WSCExceptionInvalidDetails()
        }
        return false
    }

    /**
     * Adding amount into personal account from any availiable credit cards
     *
     * @param 'FundTransfer-DTO'
     * @return - TransactionStatus-DTO
     */
    @Transactional
    @Throws(MessagingException::class)
    override fun fundTransfer(transfer: FundTransfer): TransactionStatus {
        if (transfer.amount > 0  && transfer.cardPin > 0 && transfer.installmentPeriod > 0) {
            val transaction = TransactionStatus()
            if (repoCredit.findById(transfer.cardNumber).isPresent) {
                if (repoCredit.findById(transfer.cardNumber).get()
                        .cardFlag == 1 && repoCredit.findById(transfer.cardNumber).get().cardPin > 0 &&
                    repoCredit.findById(transfer.cardNumber).get().instalmentPeriod!!.contains(transfer.installmentPeriod)
                ) {
                    if (repoCredit.findById(transfer.cardNumber).get().cardPin == transfer.cardPin) {
                        if (repoCredit.findById(transfer.cardNumber).get().cardLimit!! >= transfer.amount) {

                            //Transaction begin
                            val cusDeatils = repoCusDetails.findById(
                                repoCredit.findById(transfer.cardNumber).get().customerId
                            ).get()
                            val card = repoCredit.findById(transfer.cardNumber).get()
                            cusDeatils.balance = cusDeatils.balance!! + transfer.amount
                            card.cardSpend=(card.cardSpend!! + transfer.amount)
                            card.cardLimit=(card.cardLimit!! - transfer.amount)
                            card.cardPendingInstalment=(card.cardPendingInstalment + transfer.installmentPeriod)
                            card.cardPaidInstalment=(card.cardPaidInstalment)
                            card.instalmentAmount=(
                                (card.cardSpend!! + transfer.amount) / (transfer.installmentPeriod + card.cardPendingInstalment) +
                                        (card.cardSpend!! + transfer.amount) / (card.cardPendingInstalment + transfer.installmentPeriod) / 100 *
                                        card.interestRate
                            )
                            val calendar = Calendar.getInstance()
                            calendar.add(Calendar.MONTH, 1) //add Month
                            card.instalamentDate=(calendar.time)
                            card.instalamentDateShowUser=(calendar.time).toString()
                            repoCusDetails.save(cusDeatils)
                            repoCredit.save(card)
                            //Transaction End

                            //Transaction
                            val transactions = CustomerTransactions()
                            transactions.transactionId=(String.format("%05d", Random().nextInt(100000)))
                            transactions.panId=(cusDeatils.panId)
                            transactions.senderAccount=("own")
                            transactions.receiverAccount=(cusDeatils.accountNumber.toString())
                            transactions.senderName=(cusDeatils.name)
                            transactions.transactionDate=(LocalDateTime.now())
                            transactions.transactionDateShowUser=(LocalDate.now().toString())
                            transactions.amount=(transfer.amount)
                            transactions.interest= 0.toString()
                            transactions.transactionDetails=("Added to account from card " + card.cardNumber)
                            repoCusTrans.save(transactions)
                            serviceMail.sendCreditAccount(cusDeatils.email, transfer.amount, cusDeatils.accountNumber)
                            transaction.transactionAmount = transfer.amount
                            transaction.setTransactionReason(1)
                            transaction.setTransactionStatus(1)
                        } else {
                            transaction.transactionAmount = transfer.amount
                            transaction.setTransactionReason(3)
                            transaction.setTransactionStatus(0)
                        }
                    } else {
                        transaction.transactionAmount = transfer.amount
                        transaction.setTransactionReason(2)
                        transaction.setTransactionStatus(0)
                    }
                } else {
                    transaction.transactionAmount = transfer.amount
                    transaction.setTransactionReason(7)
                    transaction.setTransactionStatus(0)
                }
            } else {
                transaction.transactionAmount = transfer.amount
                transaction.setTransactionReason(0)
                transaction.setTransactionStatus(0)
            }
            return transaction
        }
        return TransactionStatus()
    }

    /**
     * Paying of credit card bills
     *
     * @param 'PayEmi-DTO'
     * @return - RegistrationStatus-DTO
     */
    @Transactional
    @Throws(MessagingException::class)
    override fun payEmi(transfer: PayEmi): TransactionStatus {
        val returnInterst: Double
        if (transfer.mpin > 0 && transfer.installment > 0) {
            val transaction = TransactionStatus()
            if (repoCredit.findById(transfer.cardNumber).isPresent) {
                if (repoCusAuth.findById(repoCredit.findById(transfer.cardNumber).get().customerId)
                        .get().accountFlag < 4 && transfer.installment <= repoCredit.findById(transfer.cardNumber)
                        .get().cardPendingInstalment
                ) {
                    if (repoCusAuth.findById(repoCredit.findById(transfer.cardNumber).get().customerId).get()
                            .mpin == transfer.mpin
                    ) {
                        if (repoCusDetails.findById(repoCredit.findById(transfer.cardNumber).get().customerId)
                                .get().balance!! >=
                            repoCredit.findById(transfer.cardNumber).get().instalmentAmount * transfer.installment
                        ) {
                            if (repoCusAuth.findById(repoCredit.findById(transfer.cardNumber).get().customerId)
                                    .get().transactionLimit >= repoCredit.findById(transfer.cardNumber).get()
                                    .instalmentAmount * transfer.installment
                            ) {
                                val customer = repoCusDetails.findById(
                                    repoCredit.findById(transfer.cardNumber).get().customerId
                                ).get()
                                val card = repoCredit.findById(transfer.cardNumber).get()
                                val bank = repoAdmin.findById(card.branchId).get()
                                val amount=customer.balance
                                //Transaction begin
                                if(card.instalamentDate!!.compareTo(Calendar.getInstance().time)>0) {
                                       returnInterst= ((card.instalmentAmount) - (card.cardSpend!! / card.cardPendingInstalment))
                                       bank.returnInterest =bank.returnInterest+returnInterst
                                    customer.balance =customer.balance-
                                            (((card.cardSpend!!/card.cardPendingInstalment)*transfer.installment)+returnInterst.toLong())
                                }else
                                {
                                    customer.balance =
                                        customer.balance - (card.instalmentAmount * transfer.installment).toLong()
                                    returnInterst= ((card.instalmentAmount * transfer.installment) - ((card.cardSpend!! / card.cardPendingInstalment) * transfer.installment))
                                    bank.returnInterest =bank.returnInterest+returnInterst

                                }
                                card.cardLimit=(card.cardLimit!! + ((card.cardSpend!!/card.cardPendingInstalment)*transfer.installment))
                                card.cardSpend=(card.cardSpend!! - ((card.cardSpend!!/card.cardPendingInstalment)*transfer.installment))
                                card.cardPendingInstalment=(card.cardPendingInstalment - transfer.installment)
                                card.cardPaidInstalment=(card.cardPaidInstalment + transfer.installment)
                                val calendar = Calendar.getInstance()
                                calendar.time = card.instalamentDate
                                calendar.add(Calendar.MONTH, transfer.installment) //add Month
                                card.instalamentDate=(calendar.time)
                                card.instalamentDateShowUser=(calendar.time.toString())

                                if(card.cardPendingInstalment<1) {
                                    card.cardSpend=0
                                    card.instalmentAmount= 0.0
                                    card.cardPaidInstalment=0
                                    card.instalamentDateShowUser="NA"
                                    card.instalamentDate=Calendar.getInstance().time
                                }
                                repoCusDetails.save(customer)
                                repoCredit.save(card)
                                repoAdmin.save(bank)
                                //Transaction end

                                //Transaction
                                val transactions = CustomerTransactions()
                                transactions.transactionId=(String.format("%05d", Random().nextInt(100000)))
                                transactions.panId=(customer.panId)
                                transactions.senderAccount=(customer.accountNumber.toString())
                                transactions.receiverAccount=(bank.accountNo.toString())
                                transactions.senderName=(customer.name)
                                transactions.transactionDate=(LocalDateTime.now())
                                transactions.transactionDateShowUser=(LocalDate.now().toString())
                                transactions.amount=(amount-customer.balance)
                                transactions.interest=(returnInterst.toString())
                                transactions.transactionDetails=("Paid for EMI to " + card.cardNumber)
                                repoCusTrans.save(transactions)
                                transaction.transactionAmount = amount-customer.balance
                                transaction.setTransactionReason(1)
                                transaction.setTransactionStatus(1)
                                serviceMail.sendCredit(
                                    customer.email,
                                    (card.cardSpend!! - card.instalmentAmount * transfer.installment).toLong(),
                                    card.cardNumber
                                )

                            } else {
                                transaction.transactionAmount = (repoCredit.findById(transfer.cardNumber).get()
                                    .instalmentAmount * transfer.installment).toLong()
                                transaction.setTransactionReason(4)
                                transaction.setTransactionStatus(0)
                            }
                        } else {
                            transaction.transactionAmount = (repoCredit.findById(transfer.cardNumber).get()
                                .instalmentAmount * transfer.installment).toLong()
                            transaction.setTransactionReason(3)
                            transaction.setTransactionStatus(0)
                        }
                    } else {
                        transaction.transactionAmount = (repoCredit.findById(transfer.cardNumber).get()
                            .instalmentAmount * transfer.installment).toLong()
                        transaction.setTransactionReason(2)
                        transaction.setTransactionStatus(0)
                    }
                } else {
                    transaction.transactionAmount = (repoCredit.findById(transfer.cardNumber).get()
                        .instalmentAmount * transfer.installment).toLong()
                    transaction.setTransactionReason(0)
                    transaction.setTransactionStatus(0)
                }
            } else {
                transaction.transactionAmount = (repoCredit.findById(transfer.cardNumber).get()
                    .instalmentAmount * transfer.installment).toLong()
                transaction.setTransactionReason(0)
                transaction.setTransactionStatus(0)
            }
            return transaction
        }
        else{
            return TransactionStatus()
        }

    }

    /**
     * Fetching all transaction details based on the given panId
     *
     * @param 'Pan Id'
     * @return - List of Customer Transactions
     */
    @Throws(WSCExceptionInvalidUser::class)
    override fun transactionHistory(customerId: Int): List<CustomerTransactions> {
      val  panId=repoCusDetails.findById(customerId).get().panId
      return repoCusTrans.findAllTransactions(Encoder.encode(panId))
    }

    /**
     * Enable the auto emi option for paying credit card bills
     *
     * @param 'PayAutoEmi-DTO'
     * @return - Boolean
     */
    @Throws(WSCExceptionInvalidUser::class)
    override fun enableAutoPay(@RequestBody cardDetails: PayAutoEmi): Boolean {
        if (repoCredit.findById(cardDetails.cardNumber!!).isPresent) {
            cardDetails.serviceFlag = 1
            repAutoPay.save(cardDetails)
            return true
        } else {
            throw WSCExceptionInvalidUser()
        }
    }

    /**
     * Disable auto pay option
     *
     * @param 'Card Number'
     * @return - boolean
     */
    @Throws(WSCExceptionInvalidUser::class)
    override fun disableAutoPay(cardNumber: String): Boolean {
        if (repAutoPay.findById(cardNumber).isPresent) {
            val emi = repAutoPay.findById(cardNumber).get()
            emi.serviceFlag = 0
            repAutoPay.save(emi)
            return true
        } else {
            throw WSCExceptionInvalidUser()
        }
    }

    /**
     * Fetiching of the Credit offers provided by the bank from repository
     *
     * @param 'NA'
     * @return - List Of Cards
     */
    override fun getWSCCards(): List<WSCCards> {
        return repoCards.findAll()
    }

    /**
     * Calculate the cibil score based on the given panId by the user
     *
     * @param 'Pan Id'
     * @return - Cibil Score Result
     */
    @Throws(WSCExceptionInvalidDetails::class)
    override fun getCibilScore(panId: String): CibilStatus {
        val params = CibilCalculatorParams()
        val result = CibilScoreResult()
        return if (repoCusDetails.findCustomer(panId).isPresent && repoCusDetails.findCustomer(panId)
                .get().cardEligibility == 1
        ) {
            val credit =
                repoCredit.findAllCards(repoCusDetails.findCustomer(panId).get().customerId)[0]
            params.cardPaidInstalment = credit.cardPaidInstalment
            params.creditRecivedDate = credit.creditRecivedDate
            params.cardLimit = credit.cardLimit!!
            params.cardSpend = credit.cardSpend!!
            params.cardEligibility = 1
            params.multipleCards = repoCredit.findAllCards(
                repoCusDetails.findCustomer(panId).get().customerId
            ).size
            val map: Map<String, String> = CibilCalculator.getScore(params)
            val cus = repoCusDetails.findByPanId(panId).get()
            cus.cibilScore = (map["score"]!!.toInt())
            repoCusDetails.save(cus)
            result.parameter1 = map["1"]
            result.parameter2 = map["2"]
            result.parameter3 = map["3"]
            result.parameter4 = map["4"]
            result.score = map["score"]!!.toInt()
            result.setEligibleLoans(map["score"]!!.toInt())

            val status=CibilStatus()
            status.score=result.score
            status.scoreRatio=result.scoreRatio
            status.parameter1=result.parameter1
            status.parameter2=result.parameter2
            status.parameter3=result.parameter3
            status.parameter4=result.parameter4
            status.eligibleLoans=result.getEligibleLoans()

            status
        } else {
            throw WSCExceptionInvalidDetails()
        }
    }


    /**
     * Get all emi options for given card
     */
    @Throws(WSCExceptionInvalidDetails::class)
    override fun getCardEmi(cardNumber: String): List<Int> {
        if(repoCredit.findById(cardNumber).isPresent)
        {
            if(repoCards.findById(repoCredit.findById(cardNumber).get().cardType!!).isPresent){

               return repoCards.findById(repoCredit.findById(cardNumber).get().cardType!!).get().instalmentPeriod
            }
        }
        throw WSCExceptionInvalidDetails()
    }

}