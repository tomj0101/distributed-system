package com.github.tomj0101.ebankv1.ordersystemv2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Order entity. Master\n@author Tom\nChanges:\nCreated on 2021-06-19 14:01  - by Tom
 */
@ApiModel(description = "Order entity. Master\n@author Tom\nChanges:\nCreated on 2021-06-19 14:01  - by Tom")
@Entity
@Table(name = "order_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ordermaster")
public class OrderMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "total")
    private Double total;

    @NotNull
    @Size(min = 1, max = 500)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", length = 500, nullable = false)
    private String email;

    @Column(name = "register_date")
    private Instant registerDate;

    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToOne
    @JoinColumn(unique = true)
    private Status status;

    @ManyToOne
    private User user;

    // needle-entity-add-field - will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderMaster id(Long id) {
        this.id = id;
        return this;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public OrderMaster paymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getTotal() {
        return this.total;
    }

    public OrderMaster total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getEmail() {
        return this.email;
    }

    public OrderMaster email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getRegisterDate() {
        return this.registerDate;
    }

    public OrderMaster registerDate(Instant registerDate) {
        this.registerDate = registerDate;
        return this;
    }

    public void setRegisterDate(Instant registerDate) {
        this.registerDate = registerDate;
    }

    public Address getAddress() {
        return this.address;
    }

    public OrderMaster address(Address address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Status getStatus() {
        return this.status;
    }

    public OrderMaster status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return this.user;
    }

    public OrderMaster user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // needle-entity-add-getters-setters - will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderMaster)) {
            return false;
        }
        return id != null && id.equals(((OrderMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderMaster{" +
            "id=" + getId() +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", total=" + getTotal() +
            ", email='" + getEmail() + "'" +
            ", registerDate='" + getRegisterDate() + "'" +
            "}";
    }
}
