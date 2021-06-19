package com.github.tomj0101.ebankv1.ordersystem.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomj0101.ebankv1.ordersystem.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AddressV1Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressV1.class);
        AddressV1 addressV11 = new AddressV1();
        addressV11.setId(1L);
        AddressV1 addressV12 = new AddressV1();
        addressV12.setId(addressV11.getId());
        assertThat(addressV11).isEqualTo(addressV12);
        addressV12.setId(2L);
        assertThat(addressV11).isNotEqualTo(addressV12);
        addressV11.setId(null);
        assertThat(addressV11).isNotEqualTo(addressV12);
    }
}
