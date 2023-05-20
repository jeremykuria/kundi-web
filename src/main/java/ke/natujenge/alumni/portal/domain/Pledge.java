package ke.natujenge.alumni.portal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.*;
import ke.natujenge.alumni.portal.domain.enumeration.ReminderCycle;
import ke.natujenge.alumni.portal.domain.enumeration.Status;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Pledge entity.\n@author The JHipster team.
 */
@Schema(description = "Pledge entity.\n@author The JHipster team.")
@Entity
@Table(name = "pledge")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pledge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "target_amount")
    private Float targetAmount;

    @Column(name = "paid_amount")
    private Float paidAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "no_of_reminders")
    private Integer noOfReminders;

    @Enumerated(EnumType.STRING)
    @Column(name = "reminder_cycle")
    private ReminderCycle reminderCycle;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "association" }, allowSetters = true)
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pledge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTargetAmount() {
        return this.targetAmount;
    }

    public Pledge targetAmount(Float targetAmount) {
        this.setTargetAmount(targetAmount);
        return this;
    }

    public void setTargetAmount(Float targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Float getPaidAmount() {
        return this.paidAmount;
    }

    public Pledge paidAmount(Float paidAmount) {
        this.setPaidAmount(paidAmount);
        return this;
    }

    public void setPaidAmount(Float paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Status getStatus() {
        return this.status;
    }

    public Pledge status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getNoOfReminders() {
        return this.noOfReminders;
    }

    public Pledge noOfReminders(Integer noOfReminders) {
        this.setNoOfReminders(noOfReminders);
        return this;
    }

    public void setNoOfReminders(Integer noOfReminders) {
        this.noOfReminders = noOfReminders;
    }

    public ReminderCycle getReminderCycle() {
        return this.reminderCycle;
    }

    public Pledge reminderCycle(ReminderCycle reminderCycle) {
        this.setReminderCycle(reminderCycle);
        return this;
    }

    public void setReminderCycle(ReminderCycle reminderCycle) {
        this.reminderCycle = reminderCycle;
    }

    public String getConfigs() {
        return this.configs;
    }

    public Pledge configs(String configs) {
        this.setConfigs(configs);
        return this;
    }

    public void setConfigs(String configs) {
        this.configs = configs;
    }

    public String getCreatedOn() {
        return this.createdOn;
    }

    public Pledge createdOn(String createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Pledge createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedOn() {
        return this.lastUpdatedOn;
    }

    public Pledge lastUpdatedOn(String lastUpdatedOn) {
        this.setLastUpdatedOn(lastUpdatedOn);
        return this;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public Pledge lastUpdatedBy(String lastUpdatedBy) {
        this.setLastUpdatedBy(lastUpdatedBy);
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Pledge project(Project project) {
        this.setProject(project);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pledge)) {
            return false;
        }
        return id != null && id.equals(((Pledge) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pledge{" +
            "id=" + getId() +
            ", targetAmount=" + getTargetAmount() +
            ", paidAmount=" + getPaidAmount() +
            ", status='" + getStatus() + "'" +
            ", noOfReminders=" + getNoOfReminders() +
            ", reminderCycle='" + getReminderCycle() + "'" +
            ", configs='" + getConfigs() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            "}";
    }
}
