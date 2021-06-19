package com.github.tomj0101.ebankv1.ordersystem.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomj0101.ebankv1.ordersystem.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductV1Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductV1.class);
        ProductV1 productV11 = new ProductV1();
        productV11.setId(1L);
        ProductV1 productV12 = new ProductV1();
        productV12.setId(productV11.getId());
        assertThat(productV11).isEqualTo(productV12);
        productV12.setId(2L);
        assertThat(productV11).isNotEqualTo(productV12);
        productV11.setId(null);
        assertThat(productV11).isNotEqualTo(productV12);
    }
}
