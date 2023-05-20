package ke.natujenge.alumni.portal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import ke.natujenge.alumni.portal.domain.enumeration.Role;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AssocMember.
 */
@Entity
@Table(name = "assoc_member")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssocMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "year")
    private String year;

    @Column(name = "reg_no")
    private String regNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "configs")
    private String configs;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "last_updated_on")
    private String lastUpdatedOn;

    @ManyToOne
    @JsonIgnoreProperties(value = { "project" }, allowSetters = true)
    private Pledge pledge;

    @ManyToMany
    @JoinTable(
        name = "rel_assoc_member__association",
        joinColumns = @JoinColumn(name = "assoc_member_id"),
        inverseJoinColumns = @JoinColumn(name = "association_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "members" }, allowSetters = true)
    private Set<Association> associations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AssocMember id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public AssocMember name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public AssocMember email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public AssocMember phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getYear() {
        return this.year;
    }

    public AssocMember year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRegNo() {
        return this.regNo;
    }

    public AssocMember regNo(String regNo) {
        this.setRegNo(regNo);
        return this;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public Role getRole() {
        return this.role;
    }

    public AssocMember role(Role role) {
        this.setRole(role);
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getConfigs() {
        return this.configs;
    }

    public AssocMember configs(String configs) {
        this.setConfigs(configs);
        return this;
    }

    public void setConfigs(String configs) {
        this.configs = configs;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public AssocMember createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return this.createdOn;
    }

    public AssocMember createdOn(String createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public AssocMember lastUpdatedBy(String lastUpdatedBy) {
        this.setLastUpdatedBy(lastUpdatedBy);
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedOn() {
        return this.lastUpdatedOn;
    }

    public AssocMember lastUpdatedOn(String lastUpdatedOn) {
        this.setLastUpdatedOn(lastUpdatedOn);
        return this;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Pledge getPledge() {
        return this.pledge;
    }

    public void setPledge(Pledge pledge) {
        this.pledge = pledge;
    }

    public AssocMember pledge(Pledge pledge) {
        this.setPledge(pledge);
        return this;
    }

    public Set<Association> getAssociations() {
        return this.associations;
    }

    public void setAssociations(Set<Association> associations) {
        this.associations = associations;
    }

    public AssocMember associations(Set<Association> associations) {
        this.setAssociations(associations);
        return this;
    }

    public AssocMember addAssociation(Association association) {
        this.associations.add(association);
        association.getMembers().add(this);
        return this;
    }

    public AssocMember removeAssociation(Association association) {
        this.associations.remove(association);
        association.getMembers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssocMember)) {
            return false;
        }
        return id != null && id.equals(((AssocMember) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssocMember{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", year='" + getYear() + "'" +
            ", regNo='" + getRegNo() + "'" +
            ", role='" + getRole() + "'" +
            ", configs='" + getConfigs() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            "}";
    }
}
