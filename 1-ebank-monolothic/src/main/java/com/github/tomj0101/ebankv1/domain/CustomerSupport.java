package com.github.tomj0101.ebankv1.domain;

import com.github.tomj0101.ebankv1.domain.enumeration.SupportSeverity;
import com.github.tomj0101.ebankv1.domain.enumeration.SupportStatus;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * CustomerSupport entity.\n@author Tom.\nChanges:\nCreated on 2021-06-11 00:35  - by Tom
 */
@ApiModel(description = "CustomerSupport entity.\n@author Tom.\nChanges:\nCreated on 2021-06-11 00:35  - by Tom")
@Entity
@Table(name = "customer_support")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerSupport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "c_created", nullable = false)
    private Instant cCreated;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity")
    private SupportSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SupportStatus status;

    @OneToOne
    @JoinColumn(unique = true)
    private IssueSystem issueSystem;

    //-needle-entity-add-field - will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerSupport id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public CustomerSupport description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getcCreated() {
        return this.cCreated;
    }

    public CustomerSupport cCreated(Instant cCreated) {
        this.cCreated = cCreated;
        return this;
    }

    public void setcCreated(Instant cCreated) {
        this.cCreated = cCreated;
    }

    public SupportSeverity getSeverity() {
        return this.severity;
    }

    public CustomerSupport severity(SupportSeverity severity) {
        this.severity = severity;
        return this;
    }

    public void setSeverity(SupportSeverity severity) {
        this.severity = severity;
    }

    public SupportStatus getStatus() {
        return this.status;
    }

    public CustomerSupport status(SupportStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SupportStatus status) {
        this.status = status;
    }

    public IssueSystem getIssueSystem() {
        return this.issueSystem;
    }

    public CustomerSupport issueSystem(IssueSystem issueSystem) {
        this.setIssueSystem(issueSystem);
        return this;
    }

    public void setIssueSystem(IssueSystem issueSystem) {
        this.issueSystem = issueSystem;
    }

    // needle-entity-add-getters-setters - will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerSupport)) {
            return false;
        }
        return id != null && id.equals(((CustomerSupport) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerSupport{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", cCreated='" + getcCreated() + "'" +
            ", severity='" + getSeverity() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
