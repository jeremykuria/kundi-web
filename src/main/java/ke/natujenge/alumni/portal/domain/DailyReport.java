package ke.natujenge.alumni.portal.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Report entity.
 */
@Schema(description = "The Report entity.")
@Entity
@Table(name = "daily_report")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DailyReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "report_date")
    private String reportDate;

    @Column(name = "succesful_trx")
    private String succesfulTrx;

    @Column(name = "failed_trx")
    private String failedTrx;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "configs")
    private String configs;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updated_on")
    private String lastUpdatedOn;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DailyReport id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportDate() {
        return this.reportDate;
    }

    public DailyReport reportDate(String reportDate) {
        this.setReportDate(reportDate);
        return this;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getSuccesfulTrx() {
        return this.succesfulTrx;
    }

    public DailyReport succesfulTrx(String succesfulTrx) {
        this.setSuccesfulTrx(succesfulTrx);
        return this;
    }

    public void setSuccesfulTrx(String succesfulTrx) {
        this.succesfulTrx = succesfulTrx;
    }

    public String getFailedTrx() {
        return this.failedTrx;
    }

    public DailyReport failedTrx(String failedTrx) {
        this.setFailedTrx(failedTrx);
        return this;
    }

    public void setFailedTrx(String failedTrx) {
        this.failedTrx = failedTrx;
    }

    public Float getAmount() {
        return this.amount;
    }

    public DailyReport amount(Float amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getConfigs() {
        return this.configs;
    }

    public DailyReport configs(String configs) {
        this.setConfigs(configs);
        return this;
    }

    public void setConfigs(String configs) {
        this.configs = configs;
    }

    public String getCreatedOn() {
        return this.createdOn;
    }

    public DailyReport createdOn(String createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public DailyReport createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedOn() {
        return this.lastUpdatedOn;
    }

    public DailyReport lastUpdatedOn(String lastUpdatedOn) {
        this.setLastUpdatedOn(lastUpdatedOn);
        return this;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public DailyReport lastUpdatedBy(String lastUpdatedBy) {
        this.setLastUpdatedBy(lastUpdatedBy);
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DailyReport)) {
            return false;
        }
        return id != null && id.equals(((DailyReport) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DailyReport{" +
            "id=" + getId() +
            ", reportDate='" + getReportDate() + "'" +
            ", succesfulTrx='" + getSuccesfulTrx() + "'" +
            ", failedTrx='" + getFailedTrx() + "'" +
            ", amount=" + getAmount() +
            ", configs='" + getConfigs() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            "}";
    }
}
