package com.github.tomj0101.ebankv1.ordersystemv2.domain;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Status entity\n@author Tom\nChanges:\nCreated on 2021-06-19 14:01  - by Tom
 */
@ApiModel(description = "Status entity\n@author Tom\nChanges:\nCreated on 2021-06-19 14:01  - by Tom")
@Entity
@Table(name = "status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "status")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "name", length = 500, nullable = false)
    private String name;

    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @Column(name = "register_date")
    private Instant registerDate;

    // needle-entity-add-field - will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Status name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Status description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getRegisterDate() {
        return this.registerDate;
    }

    public Status registerDate(Instant registerDate) {
        this.registerDate = registerDate;
        return this;
    }

    public void setRegisterDate(Instant registerDate) {
        this.registerDate = registerDate;
    }

    // needle-entity-add-getters-setters - will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Status)) {
            return false;
        }
        return id != null && id.equals(((Status) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Status{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", registerDate='" + getRegisterDate() + "'" +
            "}";
    }
}
