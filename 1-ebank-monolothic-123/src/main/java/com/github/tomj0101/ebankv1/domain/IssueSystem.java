package com.github.tomj0101.ebankv1.domain;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * IssueSystem entity.\n@author Tom.\nChanges:\nCreated on 2021-06-11 00:36  - by Tom
 */
@ApiModel(description = "IssueSystem entity.\n@author Tom.\nChanges:\nCreated on 2021-06-11 00:36  - by Tom")
@Entity
@Table(name = "issue_system")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IssueSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "i_created")
    private Instant iCreated;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IssueSystem id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public IssueSystem title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public IssueSystem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getiCreated() {
        return this.iCreated;
    }

    public IssueSystem iCreated(Instant iCreated) {
        this.iCreated = iCreated;
        return this;
    }

    public void setiCreated(Instant iCreated) {
        this.iCreated = iCreated;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssueSystem)) {
            return false;
        }
        return id != null && id.equals(((IssueSystem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IssueSystem{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", iCreated='" + getiCreated() + "'" +
            "}";
    }
}
