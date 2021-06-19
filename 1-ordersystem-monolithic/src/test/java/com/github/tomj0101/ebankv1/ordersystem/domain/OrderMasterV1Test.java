package com.github.tomj0101.ebankv1.ordersystem.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomj0101.ebankv1.ordersystem.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderMasterV1Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderMasterV1.class);
        OrderMasterV1 orderMasterV11 = new OrderMasterV1();
        orderMasterV11.setId(1L);
        OrderMasterV1 orderMasterV12 = new OrderMasterV1();
        orderMasterV12.setId(orderMasterV11.getId());
        assertThat(orderMasterV11).isEqualTo(orderMasterV12);
        orderMasterV12.setId(2L);
        assertThat(orderMasterV11).isNotEqualTo(orderMasterV12);
        orderMasterV11.setId(null);
        assertThat(orderMasterV11).isNotEqualTo(orderMasterV12);
    }
}
