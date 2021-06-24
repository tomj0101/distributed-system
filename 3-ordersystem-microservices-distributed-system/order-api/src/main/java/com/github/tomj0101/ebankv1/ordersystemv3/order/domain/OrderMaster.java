package com.github.tomj0101.ebankv1.ordersystemv3.order.domain;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Order entity. Master\n@author Tom\nChanges:\nCreated on 2021-06-19 14:01  - by Tom
 */
@ApiModel(description = "Order entity. Master\n@author Tom\nChanges:\nCreated on 2021-06-19 14:01  - by Tom")
@Document(collection = "order_master")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ordermaster")
public class OrderMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Field("payment_method")
    private String paymentMethod;

    @Field("total")
    private Double total;

    @NotNull
    @Size(min = 1, max = 500)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Field("email")
    private String email;

    @Field("register_date")
    private Instant registerDate;

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
