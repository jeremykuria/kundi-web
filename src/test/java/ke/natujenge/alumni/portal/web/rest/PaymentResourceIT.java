package ke.natujenge.alumni.portal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import ke.natujenge.alumni.portal.IntegrationTest;
import ke.natujenge.alumni.portal.domain.Payment;
import ke.natujenge.alumni.portal.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PaymentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentResourceIT {

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBSCRIBER_ID = "AAAAAAAAAA";
    private static final String UPDATED_SUBSCRIBER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIPT_ID = "AAAAAAAAAA";
    private static final String UPDATED_RECEIPT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_PAYBILL = "AAAAAAAAAA";
    private static final String UPDATED_PAYBILL = "BBBBBBBBBB";

    private static final String DEFAULT_TRX_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRX_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TRX_END_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TRX_END_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_TRX_START_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TRX_START_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_REASON = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIGS = "AAAAAAAAAA";
    private static final String UPDATED_CONFIGS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDATED_ON = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_ON = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_UPDTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDTED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentMockMvc;

    private Payment payment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createEntity(EntityManager em) {
        Payment payment = new Payment()
            .productId(DEFAULT_PRODUCT_ID)
            .code(DEFAULT_CODE)
            .subscriberId(DEFAULT_SUBSCRIBER_ID)
            .receiptId(DEFAULT_RECEIPT_ID)
            .orderId(DEFAULT_ORDER_ID)
            .phone(DEFAULT_PHONE)
            .amount(DEFAULT_AMOUNT)
            .currency(DEFAULT_CURRENCY)
            .paybill(DEFAULT_PAYBILL)
            .trxId(DEFAULT_TRX_ID)
            .trxEndTime(DEFAULT_TRX_END_TIME)
            .trxStartTime(DEFAULT_TRX_START_TIME)
            .status(DEFAULT_STATUS)
            .statusReason(DEFAULT_STATUS_REASON)
            .configs(DEFAULT_CONFIGS)
            .createdBy(DEFAULT_CREATED_BY)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON)
            .lastUpdtedBy(DEFAULT_LAST_UPDTED_BY);
        return payment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createUpdatedEntity(EntityManager em) {
        Payment payment = new Payment()
            .productId(UPDATED_PRODUCT_ID)
            .code(UPDATED_CODE)
            .subscriberId(UPDATED_SUBSCRIBER_ID)
            .receiptId(UPDATED_RECEIPT_ID)
            .orderId(UPDATED_ORDER_ID)
            .phone(UPDATED_PHONE)
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .paybill(UPDATED_PAYBILL)
            .trxId(UPDATED_TRX_ID)
            .trxEndTime(UPDATED_TRX_END_TIME)
            .trxStartTime(UPDATED_TRX_START_TIME)
            .status(UPDATED_STATUS)
            .statusReason(UPDATED_STATUS_REASON)
            .configs(UPDATED_CONFIGS)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdtedBy(UPDATED_LAST_UPDTED_BY);
        return payment;
    }

    @BeforeEach
    public void initTest() {
        payment = createEntity(em);
    }

    @Test
    @Transactional
    void createPayment() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();
        // Create the Payment
        restPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate + 1);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testPayment.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPayment.getSubscriberId()).isEqualTo(DEFAULT_SUBSCRIBER_ID);
        assertThat(testPayment.getReceiptId()).isEqualTo(DEFAULT_RECEIPT_ID);
        assertThat(testPayment.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testPayment.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPayment.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPayment.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testPayment.getPaybill()).isEqualTo(DEFAULT_PAYBILL);
        assertThat(testPayment.getTrxId()).isEqualTo(DEFAULT_TRX_ID);
        assertThat(testPayment.getTrxEndTime()).isEqualTo(DEFAULT_TRX_END_TIME);
        assertThat(testPayment.getTrxStartTime()).isEqualTo(DEFAULT_TRX_START_TIME);
        assertThat(testPayment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPayment.getStatusReason()).isEqualTo(DEFAULT_STATUS_REASON);
        assertThat(testPayment.getConfigs()).isEqualTo(DEFAULT_CONFIGS);
        assertThat(testPayment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPayment.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
        assertThat(testPayment.getLastUpdtedBy()).isEqualTo(DEFAULT_LAST_UPDTED_BY);
    }

    @Test
    @Transactional
    void createPaymentWithExistingId() throws Exception {
        // Create the Payment with an existing ID
        payment.setId(1L);

        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPayments() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList
        restPaymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].subscriberId").value(hasItem(DEFAULT_SUBSCRIBER_ID)))
            .andExpect(jsonPath("$.[*].receiptId").value(hasItem(DEFAULT_RECEIPT_ID)))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].paybill").value(hasItem(DEFAULT_PAYBILL)))
            .andExpect(jsonPath("$.[*].trxId").value(hasItem(DEFAULT_TRX_ID)))
            .andExpect(jsonPath("$.[*].trxEndTime").value(hasItem(DEFAULT_TRX_END_TIME)))
            .andExpect(jsonPath("$.[*].trxStartTime").value(hasItem(DEFAULT_TRX_START_TIME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].statusReason").value(hasItem(DEFAULT_STATUS_REASON)))
            .andExpect(jsonPath("$.[*].configs").value(hasItem(DEFAULT_CONFIGS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(DEFAULT_LAST_UPDATED_ON)))
            .andExpect(jsonPath("$.[*].lastUpdtedBy").value(hasItem(DEFAULT_LAST_UPDTED_BY)));
    }

    @Test
    @Transactional
    void getPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get the payment
        restPaymentMockMvc
            .perform(get(ENTITY_API_URL_ID, payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payment.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.subscriberId").value(DEFAULT_SUBSCRIBER_ID))
            .andExpect(jsonPath("$.receiptId").value(DEFAULT_RECEIPT_ID))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.paybill").value(DEFAULT_PAYBILL))
            .andExpect(jsonPath("$.trxId").value(DEFAULT_TRX_ID))
            .andExpect(jsonPath("$.trxEndTime").value(DEFAULT_TRX_END_TIME))
            .andExpect(jsonPath("$.trxStartTime").value(DEFAULT_TRX_START_TIME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.statusReason").value(DEFAULT_STATUS_REASON))
            .andExpect(jsonPath("$.configs").value(DEFAULT_CONFIGS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastUpdatedOn").value(DEFAULT_LAST_UPDATED_ON))
            .andExpect(jsonPath("$.lastUpdtedBy").value(DEFAULT_LAST_UPDTED_BY));
    }

    @Test
    @Transactional
    void getNonExistingPayment() throws Exception {
        // Get the payment
        restPaymentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment
        Payment updatedPayment = paymentRepository.findById(payment.getId()).get();
        // Disconnect from session so that the updates on updatedPayment are not directly saved in db
        em.detach(updatedPayment);
        updatedPayment
            .productId(UPDATED_PRODUCT_ID)
            .code(UPDATED_CODE)
            .subscriberId(UPDATED_SUBSCRIBER_ID)
            .receiptId(UPDATED_RECEIPT_ID)
            .orderId(UPDATED_ORDER_ID)
            .phone(UPDATED_PHONE)
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .paybill(UPDATED_PAYBILL)
            .trxId(UPDATED_TRX_ID)
            .trxEndTime(UPDATED_TRX_END_TIME)
            .trxStartTime(UPDATED_TRX_START_TIME)
            .status(UPDATED_STATUS)
            .statusReason(UPDATED_STATUS_REASON)
            .configs(UPDATED_CONFIGS)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdtedBy(UPDATED_LAST_UPDTED_BY);

        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPayment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPayment))
            )
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testPayment.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPayment.getSubscriberId()).isEqualTo(UPDATED_SUBSCRIBER_ID);
        assertThat(testPayment.getReceiptId()).isEqualTo(UPDATED_RECEIPT_ID);
        assertThat(testPayment.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testPayment.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayment.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testPayment.getPaybill()).isEqualTo(UPDATED_PAYBILL);
        assertThat(testPayment.getTrxId()).isEqualTo(UPDATED_TRX_ID);
        assertThat(testPayment.getTrxEndTime()).isEqualTo(UPDATED_TRX_END_TIME);
        assertThat(testPayment.getTrxStartTime()).isEqualTo(UPDATED_TRX_START_TIME);
        assertThat(testPayment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPayment.getStatusReason()).isEqualTo(UPDATED_STATUS_REASON);
        assertThat(testPayment.getConfigs()).isEqualTo(UPDATED_CONFIGS);
        assertThat(testPayment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPayment.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testPayment.getLastUpdtedBy()).isEqualTo(UPDATED_LAST_UPDTED_BY);
    }

    @Test
    @Transactional
    void putNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentWithPatch() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment using partial update
        Payment partialUpdatedPayment = new Payment();
        partialUpdatedPayment.setId(payment.getId());

        partialUpdatedPayment
            .code(UPDATED_CODE)
            .orderId(UPDATED_ORDER_ID)
            .amount(UPDATED_AMOUNT)
            .trxId(UPDATED_TRX_ID)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY);

        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayment))
            )
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testPayment.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPayment.getSubscriberId()).isEqualTo(DEFAULT_SUBSCRIBER_ID);
        assertThat(testPayment.getReceiptId()).isEqualTo(DEFAULT_RECEIPT_ID);
        assertThat(testPayment.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testPayment.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayment.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testPayment.getPaybill()).isEqualTo(DEFAULT_PAYBILL);
        assertThat(testPayment.getTrxId()).isEqualTo(UPDATED_TRX_ID);
        assertThat(testPayment.getTrxEndTime()).isEqualTo(DEFAULT_TRX_END_TIME);
        assertThat(testPayment.getTrxStartTime()).isEqualTo(DEFAULT_TRX_START_TIME);
        assertThat(testPayment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPayment.getStatusReason()).isEqualTo(DEFAULT_STATUS_REASON);
        assertThat(testPayment.getConfigs()).isEqualTo(DEFAULT_CONFIGS);
        assertThat(testPayment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPayment.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
        assertThat(testPayment.getLastUpdtedBy()).isEqualTo(DEFAULT_LAST_UPDTED_BY);
    }

    @Test
    @Transactional
    void fullUpdatePaymentWithPatch() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment using partial update
        Payment partialUpdatedPayment = new Payment();
        partialUpdatedPayment.setId(payment.getId());

        partialUpdatedPayment
            .productId(UPDATED_PRODUCT_ID)
            .code(UPDATED_CODE)
            .subscriberId(UPDATED_SUBSCRIBER_ID)
            .receiptId(UPDATED_RECEIPT_ID)
            .orderId(UPDATED_ORDER_ID)
            .phone(UPDATED_PHONE)
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .paybill(UPDATED_PAYBILL)
            .trxId(UPDATED_TRX_ID)
            .trxEndTime(UPDATED_TRX_END_TIME)
            .trxStartTime(UPDATED_TRX_START_TIME)
            .status(UPDATED_STATUS)
            .statusReason(UPDATED_STATUS_REASON)
            .configs(UPDATED_CONFIGS)
            .createdBy(UPDATED_CREATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .lastUpdtedBy(UPDATED_LAST_UPDTED_BY);

        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayment))
            )
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testPayment.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPayment.getSubscriberId()).isEqualTo(UPDATED_SUBSCRIBER_ID);
        assertThat(testPayment.getReceiptId()).isEqualTo(UPDATED_RECEIPT_ID);
        assertThat(testPayment.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testPayment.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayment.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testPayment.getPaybill()).isEqualTo(UPDATED_PAYBILL);
        assertThat(testPayment.getTrxId()).isEqualTo(UPDATED_TRX_ID);
        assertThat(testPayment.getTrxEndTime()).isEqualTo(UPDATED_TRX_END_TIME);
        assertThat(testPayment.getTrxStartTime()).isEqualTo(UPDATED_TRX_START_TIME);
        assertThat(testPayment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPayment.getStatusReason()).isEqualTo(UPDATED_STATUS_REASON);
        assertThat(testPayment.getConfigs()).isEqualTo(UPDATED_CONFIGS);
        assertThat(testPayment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPayment.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testPayment.getLastUpdtedBy()).isEqualTo(UPDATED_LAST_UPDTED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeDelete = paymentRepository.findAll().size();

        // Delete the payment
        restPaymentMockMvc
            .perform(delete(ENTITY_API_URL_ID, payment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
