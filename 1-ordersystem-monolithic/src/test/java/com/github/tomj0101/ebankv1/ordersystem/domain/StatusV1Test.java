package com.github.tomj0101.ebankv1.ordersystem.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomj0101.ebankv1.ordersystem.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatusV1Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusV1.class);
        StatusV1 statusV11 = new StatusV1();
        statusV11.setId(1L);
        StatusV1 statusV12 = new StatusV1();
        statusV12.setId(statusV11.getId());
        assertThat(statusV11).isEqualTo(statusV12);
        statusV12.setId(2L);
        assertThat(statusV11).isNotEqualTo(statusV12);
        statusV11.setId(null);
        assertThat(statusV11).isNotEqualTo(statusV12);
    }
}
