package com.github.tomj0101.ebankv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomj0101.ebankv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerSupportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerSupport.class);
        CustomerSupport customerSupport1 = new CustomerSupport();
        customerSupport1.setId(1L);
        CustomerSupport customerSupport2 = new CustomerSupport();
        customerSupport2.setId(customerSupport1.getId());
        assertThat(customerSupport1).isEqualTo(customerSupport2);
        customerSupport2.setId(2L);
        assertThat(customerSupport1).isNotEqualTo(customerSupport2);
        customerSupport1.setId(null);
        assertThat(customerSupport1).isNotEqualTo(customerSupport2);
    }
}
