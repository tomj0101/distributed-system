package com.github.tomj0101.ebankv1.domain;

import com.github.tomj0101.ebankv1.domain.enumeration.TransactionStatus;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * RecentTransaction entity.\n@author Tom.\nChanges:\nCreated on 2021-06-11 00:33  - by Tom
 */
@ApiModel(description = "RecentTransaction entity.\n@author Tom.\nChanges:\nCreated on 2021-06-11 00:33  - by Tom")
@Entity
@Table(name = "recent_transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RecentTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "description", length = 50, nullable = false)
    private String description;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Long amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @NotNull
    @Column(name = "t_created", nullable = false)
    private Instant tCreated;

    //-needle-entity-add-field - will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecentTransaction id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public RecentTransaction description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAmount() {
        return this.amount;
    }

    public RecentTransaction amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public TransactionStatus getStatus() {
        return this.status;
    }

    public RecentTransaction status(TransactionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Instant gettCreated() {
        return this.tCreated;
    }

    public RecentTransaction tCreated(Instant tCreated) {
        this.tCreated = tCreated;
        return this;
    }

    public void settCreated(Instant tCreated) {
        this.tCreated = tCreated;
    }

    //-needle-entity-add-getters-setters - will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecentTransaction)) {
            return false;
        }
        return id != null && id.equals(((RecentTransaction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecentTransaction{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", amount=" + getAmount() +
            ", status='" + getStatus() + "'" +
            ", tCreated='" + gettCreated() + "'" +
            "}";
    }
}
