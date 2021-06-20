package com.github.tomj0101.ebankv1.ordersystemv2.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomj0101.ebankv1.ordersystemv2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderMaster.class);
        OrderMaster orderMaster1 = new OrderMaster();
        orderMaster1.setId(1L);
        OrderMaster orderMaster2 = new OrderMaster();
        orderMaster2.setId(orderMaster1.getId());
        assertThat(orderMaster1).isEqualTo(orderMaster2);
        orderMaster2.setId(2L);
        assertThat(orderMaster1).isNotEqualTo(orderMaster2);
        orderMaster1.setId(null);
        assertThat(orderMaster1).isNotEqualTo(orderMaster2);
    }
}
