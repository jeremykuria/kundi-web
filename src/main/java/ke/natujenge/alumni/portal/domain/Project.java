package ke.natujenge.alumni.portal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * not an ignored comment
 */
@Schema(description = "not an ignored comment")
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "target_amount")
    private String targetAmount;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "no_of_pledge_reminder")
    private String noOfPledgeReminder;

    @Column(name = "contributed_amount")
    private String contributedAmount;

    @Column(name = "paybill")
    private String paybill;

    @Column(name = "configs")
    private String configs;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "last_updated_on")
    private String lastUpdatedOn;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "members" }, allowSetters = true)
    private Association association;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Project id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Project name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetAmount() {
        return this.targetAmount;
    }

    public Project targetAmount(String targetAmount) {
        this.setTargetAmount(targetAmount);
        return this;
    }

    public void setTargetAmount(String targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public Project startDate(String startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public Project endDate(String endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNoOfPledgeReminder() {
        return this.noOfPledgeReminder;
    }

    public Project noOfPledgeReminder(String noOfPledgeReminder) {
        this.setNoOfPledgeReminder(noOfPledgeReminder);
        return this;
    }

    public void setNoOfPledgeReminder(String noOfPledgeReminder) {
        this.noOfPledgeReminder = noOfPledgeReminder;
    }

    public String getContributedAmount() {
        return this.contributedAmount;
    }

    public Project contributedAmount(String contributedAmount) {
        this.setContributedAmount(contributedAmount);
        return this;
    }

    public void setContributedAmount(String contributedAmount) {
        this.contributedAmount = contributedAmount;
    }

    public String getPaybill() {
        return this.paybill;
    }

    public Project paybill(String paybill) {
        this.setPaybill(paybill);
        return this;
    }

    public void setPaybill(String paybill) {
        this.paybill = paybill;
    }

    public String getConfigs() {
        return this.configs;
    }

    public Project configs(String configs) {
        this.setConfigs(configs);
        return this;
    }

    public void setConfigs(String configs) {
        this.configs = configs;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Project createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return this.createdOn;
    }

    public Project createdOn(String createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastUpdatedOn() {
        return this.lastUpdatedOn;
    }

    public Project lastUpdatedOn(String lastUpdatedOn) {
        this.setLastUpdatedOn(lastUpdatedOn);
        return this;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public Project lastUpdatedBy(String lastUpdatedBy) {
        this.setLastUpdatedBy(lastUpdatedBy);
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Association getAssociation() {
        return this.association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

    public Project association(Association association) {
        this.setAssociation(association);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", targetAmount='" + getTargetAmount() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", noOfPledgeReminder='" + getNoOfPledgeReminder() + "'" +
            ", contributedAmount='" + getContributedAmount() + "'" +
            ", paybill='" + getPaybill() + "'" +
            ", configs='" + getConfigs() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            "}";
    }
}
