package com.github.tomj0101.ebankv1.ordersystemv2.domain;

import com.github.tomj0101.ebankv1.ordersystemv2.domain.enumeration.Condition;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Product entity.\n@author Tom\nChanges:\nCreated on 2021-06-19 14:01  - by Tom
 */
@ApiModel(description = "Product entity.\n@author Tom\nChanges:\nCreated on 2021-06-19 14:01  - by Tom")
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "product")
public class Product implements Serializable {

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

    public Product id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getProductImages() {
        return this.productImages;
    }

    public Product productImages(byte[] productImages) {
        this.productImages = productImages;
        return this;
    }

    public void setProductImages(byte[] productImages) {
        this.productImages = productImages;
    }

    public String getProductImagesContentType() {
        return this.productImagesContentType;
    }

    public Product productImagesContentType(String productImagesContentType) {
        this.productImagesContentType = productImagesContentType;
        return this;
    }

    public void setProductImagesContentType(String productImagesContentType) {
        this.productImagesContentType = productImagesContentType;
    }

    public Double getPrice() {
        return this.price;
    }

    public Product price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Condition getCondition() {
        return this.condition;
    }

    public Product condition(Condition condition) {
        this.condition = condition;
        return this;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Product active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getRegisterDate() {
        return this.registerDate;
    }

    public Product registerDate(Instant registerDate) {
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
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
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
