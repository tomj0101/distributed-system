package com.github.tomj0101.ebankv1.ordersystem.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomj0101.ebankv1.ordersystem.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderDetailsV1Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderDetailsV1.class);
        OrderDetailsV1 orderDetailsV11 = new OrderDetailsV1();
        orderDetailsV11.setId(1L);
        OrderDetailsV1 orderDetailsV12 = new OrderDetailsV1();
        orderDetailsV12.setId(orderDetailsV11.getId());
        assertThat(orderDetailsV11).isEqualTo(orderDetailsV12);
        orderDetailsV12.setId(2L);
        assertThat(orderDetailsV11).isNotEqualTo(orderDetailsV12);
        orderDetailsV11.setId(null);
        assertThat(orderDetailsV11).isNotEqualTo(orderDetailsV12);
    }
}
