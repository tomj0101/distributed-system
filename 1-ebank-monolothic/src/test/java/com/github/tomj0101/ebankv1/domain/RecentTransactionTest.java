package com.github.tomj0101.ebankv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomj0101.ebankv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecentTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecentTransaction.class);
        RecentTransaction recentTransaction1 = new RecentTransaction();
        recentTransaction1.setId(1L);
        RecentTransaction recentTransaction2 = new RecentTransaction();
        recentTransaction2.setId(recentTransaction1.getId());
        assertThat(recentTransaction1).isEqualTo(recentTransaction2);
        recentTransaction2.setId(2L);
        assertThat(recentTransaction1).isNotEqualTo(recentTransaction2);
        recentTransaction1.setId(null);
        assertThat(recentTransaction1).isNotEqualTo(recentTransaction2);
    }
}
