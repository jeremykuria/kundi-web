package ke.natujenge.alumni.portal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import ke.natujenge.alumni.portal.domain.enumeration.AssociationType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Association.
 */
@Entity
@Table(name = "association")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Association implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "brief")
    private String brief;

    @Column(name = "description")
    private String description;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "content_status")
    private String contentStatus;

    @Column(name = "paybill")
    private String paybill;

    @Enumerated(EnumType.STRING)
    @Column(name = "association_type")
    private AssociationType associationType;

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

    @ManyToMany(mappedBy = "associations")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pledge", "associations" }, allowSetters = true)
    private Set<AssocMember> members = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Association id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Association name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return this.brief;
    }

    public Association brief(String brief) {
        this.setBrief(brief);
        return this;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDescription() {
        return this.description;
    }

    public Association description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Association phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContentStatus() {
        return this.contentStatus;
    }

    public Association contentStatus(String contentStatus) {
        this.setContentStatus(contentStatus);
        return this;
    }

    public void setContentStatus(String contentStatus) {
        this.contentStatus = contentStatus;
    }

    public String getPaybill() {
        return this.paybill;
    }

    public Association paybill(String paybill) {
        this.setPaybill(paybill);
        return this;
    }

    public void setPaybill(String paybill) {
        this.paybill = paybill;
    }

    public AssociationType getAssociationType() {
        return this.associationType;
    }

    public Association associationType(AssociationType associationType) {
        this.setAssociationType(associationType);
        return this;
    }

    public void setAssociationType(AssociationType associationType) {
        this.associationType = associationType;
    }

    public String getConfigs() {
        return this.configs;
    }

    public Association configs(String configs) {
        this.setConfigs(configs);
        return this;
    }

    public void setConfigs(String configs) {
        this.configs = configs;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Association createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return this.createdOn;
    }

    public Association createdOn(String createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastUpdatedOn() {
        return this.lastUpdatedOn;
    }

    public Association lastUpdatedOn(String lastUpdatedOn) {
        this.setLastUpdatedOn(lastUpdatedOn);
        return this;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public Association lastUpdatedBy(String lastUpdatedBy) {
        this.setLastUpdatedBy(lastUpdatedBy);
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Set<AssocMember> getMembers() {
        return this.members;
    }

    public void setMembers(Set<AssocMember> assocMembers) {
        if (this.members != null) {
            this.members.forEach(i -> i.removeAssociation(this));
        }
        if (assocMembers != null) {
            assocMembers.forEach(i -> i.addAssociation(this));
        }
        this.members = assocMembers;
    }

    public Association members(Set<AssocMember> assocMembers) {
        this.setMembers(assocMembers);
        return this;
    }

    public Association addMember(AssocMember assocMember) {
        this.members.add(assocMember);
        assocMember.getAssociations().add(this);
        return this;
    }

    public Association removeMember(AssocMember assocMember) {
        this.members.remove(assocMember);
        assocMember.getAssociations().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Association)) {
            return false;
        }
        return id != null && id.equals(((Association) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Association{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", brief='" + getBrief() + "'" +
            ", description='" + getDescription() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", contentStatus='" + getContentStatus() + "'" +
            ", paybill='" + getPaybill() + "'" +
            ", associationType='" + getAssociationType() + "'" +
            ", configs='" + getConfigs() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            "}";
    }
}
