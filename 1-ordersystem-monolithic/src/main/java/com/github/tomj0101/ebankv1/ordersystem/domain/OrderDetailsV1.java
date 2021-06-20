package com.github.tomj0101.ebankv1.ordersystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Order entity. Detail\n@author Tom\nChanges:\nCreated on 2021-06-19 14:01  - by Tom
 * This module is not in use
 */
@ApiModel(description = "Order entity. Detail\n@author Tom\nChanges:\nCreated on 2021-06-19 14:01  - by Tom")
@Entity
@Table(name = "order_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderDetailsV1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private Long userId;

    @Column(name = "register_date")
    private Instant registerDate;

    @JsonIgnoreProperties(value = { "address", "status", "user" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private OrderMasterV1 orderMaster;

    // needle-entity-add-field - will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderDetailsV1 id(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return this.userId;
    }

    public OrderDetailsV1 userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instant getRegisterDate() {
        return this.registerDate;
    }

    public OrderDetailsV1 registerDate(Instant registerDate) {
        this.registerDate = registerDate;
        return this;
    }

    public void setRegisterDate(Instant registerDate) {
        this.registerDate = registerDate;
    }

    public OrderMasterV1 getOrderMaster() {
        return this.orderMaster;
    }

    public OrderDetailsV1 orderMaster(OrderMasterV1 orderMaster) {
        this.setOrderMaster(orderMaster);
        return this;
    }

    public void setOrderMaster(OrderMasterV1 orderMaster) {
        this.orderMaster = orderMaster;
    }

    // needle-entity-add-getters-setters - will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDetailsV1)) {
            return false;
        }
        return id != null && id.equals(((OrderDetailsV1) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDetailsV1{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", registerDate='" + getRegisterDate() + "'" +
            "}";
    }
}
