package com.github.tomj0101.ebankv1.ordersystem.domain;

import com.github.tomj0101.ebankv1.ordersystem.domain.enumeration.Condition;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * Product entity.\n@author Tom\nChanges:\nCreated on 2021-06-19 14:01  - by Tom
 */
@ApiModel(description = "Product entity.\n@author Tom\nChanges:\nCreated on 2021-06-19 14:01  - by Tom")
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductV1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "name", length = 250, nullable = false)
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "product_images")
    private byte[] productImages;

    @Column(name = "product_images_content_type")
    private String productImagesContentType;

    @Column(name = "price")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition")
    private Condition condition;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "register_date")
    private Instant registerDate;

    // needle-entity-add-field - will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductV1 id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ProductV1 name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public ProductV1 description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getProductImages() {
        return this.productImages;
    }

    public ProductV1 productImages(byte[] productImages) {
        this.productImages = productImages;
        return this;
    }

    public void setProductImages(byte[] productImages) {
        this.productImages = productImages;
    }

    public String getProductImagesContentType() {
        return this.productImagesContentType;
    }

    public ProductV1 productImagesContentType(String productImagesContentType) {
        this.productImagesContentType = productImagesContentType;
        return this;
    }

    public void setProductImagesContentType(String productImagesContentType) {
        this.productImagesContentType = productImagesContentType;
    }

    public Double getPrice() {
        return this.price;
    }

    public ProductV1 price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Condition getCondition() {
        return this.condition;
    }

    public ProductV1 condition(Condition condition) {
        this.condition = condition;
        return this;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Boolean getActive() {
        return this.active;
    }

    public ProductV1 active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getRegisterDate() {
        return this.registerDate;
    }

    public ProductV1 registerDate(Instant registerDate) {
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
        if (!(o instanceof ProductV1)) {
            return false;
        }
        return id != null && id.equals(((ProductV1) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductV1{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", productImages='" + getProductImages() + "'" +
            ", productImagesContentType='" + getProductImagesContentType() + "'" +
            ", price=" + getPrice() +
            ", condition='" + getCondition() + "'" +
            ", active='" + getActive() + "'" +
            ", registerDate='" + getRegisterDate() + "'" +
            "}";
    }
}
