package ke.natujenge.alumni.portal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "code")
    private String code;

    @Column(name = "subscriber_id")
    private String subscriberId;

    @Column(name = "receipt_id")
    private String receiptId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "amount")
    private String amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "paybill")
    private String paybill;

    @Column(name = "trx_id")
    private String trxId;

    @Column(name = "trx_end_time")
    private String trxEndTime;

    @Column(name = "trx_start_time")
    private String trxStartTime;

    @Column(name = "status")
    private String status;

    @Column(name = "status_reason")
    private String statusReason;

    @Column(name = "configs")
    private String configs;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updated_on")
    private String lastUpdatedOn;

    @Column(name = "last_updted_by")
    private String lastUpdtedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "association" }, allowSetters = true)
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Payment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return this.productId;
    }

    public Payment productId(String productId) {
        this.setProductId(productId);
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCode() {
        return this.code;
    }

    public Payment code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubscriberId() {
        return this.subscriberId;
    }

    public Payment subscriberId(String subscriberId) {
        this.setSubscriberId(subscriberId);
        return this;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getReceiptId() {
        return this.receiptId;
    }

    public Payment receiptId(String receiptId) {
        this.setReceiptId(receiptId);
        return this;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public Payment orderId(String orderId) {
        this.setOrderId(orderId);
        return this;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPhone() {
        return this.phone;
    }

    public Payment phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAmount() {
        return this.amount;
    }

    public Payment amount(String amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public Payment currency(String currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaybill() {
        return this.paybill;
    }

    public Payment paybill(String paybill) {
        this.setPaybill(paybill);
        return this;
    }

    public void setPaybill(String paybill) {
        this.paybill = paybill;
    }

    public String getTrxId() {
        return this.trxId;
    }

    public Payment trxId(String trxId) {
        this.setTrxId(trxId);
        return this;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public String getTrxEndTime() {
        return this.trxEndTime;
    }

    public Payment trxEndTime(String trxEndTime) {
        this.setTrxEndTime(trxEndTime);
        return this;
    }

    public void setTrxEndTime(String trxEndTime) {
        this.trxEndTime = trxEndTime;
    }

    public String getTrxStartTime() {
        return this.trxStartTime;
    }

    public Payment trxStartTime(String trxStartTime) {
        this.setTrxStartTime(trxStartTime);
        return this;
    }

    public void setTrxStartTime(String trxStartTime) {
        this.trxStartTime = trxStartTime;
    }

    public String getStatus() {
        return this.status;
    }

    public Payment status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusReason() {
        return this.statusReason;
    }

    public Payment statusReason(String statusReason) {
        this.setStatusReason(statusReason);
        return this;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public String getConfigs() {
        return this.configs;
    }

    public Payment configs(String configs) {
        this.setConfigs(configs);
        return this;
    }

    public void setConfigs(String configs) {
        this.configs = configs;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Payment createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedOn() {
        return this.lastUpdatedOn;
    }

    public Payment lastUpdatedOn(String lastUpdatedOn) {
        this.setLastUpdatedOn(lastUpdatedOn);
        return this;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getLastUpdtedBy() {
        return this.lastUpdtedBy;
    }

    public Payment lastUpdtedBy(String lastUpdtedBy) {
        this.setLastUpdtedBy(lastUpdtedBy);
        return this;
    }

    public void setLastUpdtedBy(String lastUpdtedBy) {
        this.lastUpdtedBy = lastUpdtedBy;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Payment project(Project project) {
        this.setProject(project);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", productId='" + getProductId() + "'" +
            ", code='" + getCode() + "'" +
            ", subscriberId='" + getSubscriberId() + "'" +
            ", receiptId='" + getReceiptId() + "'" +
            ", orderId='" + getOrderId() + "'" +
            ", phone='" + getPhone() + "'" +
            ", amount='" + getAmount() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", paybill='" + getPaybill() + "'" +
            ", trxId='" + getTrxId() + "'" +
            ", trxEndTime='" + getTrxEndTime() + "'" +
            ", trxStartTime='" + getTrxStartTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", statusReason='" + getStatusReason() + "'" +
            ", configs='" + getConfigs() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            ", lastUpdtedBy='" + getLastUpdtedBy() + "'" +
            "}";
    }
}
