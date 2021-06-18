package com.github.tomj0101.ebankv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomj0101.ebankv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IssueSystemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueSystem.class);
        IssueSystem issueSystem1 = new IssueSystem();
        issueSystem1.setId(1L);
        IssueSystem issueSystem2 = new IssueSystem();
        issueSystem2.setId(issueSystem1.getId());
        assertThat(issueSystem1).isEqualTo(issueSystem2);
        issueSystem2.setId(2L);
        assertThat(issueSystem1).isNotEqualTo(issueSystem2);
        issueSystem1.setId(null);
        assertThat(issueSystem1).isNotEqualTo(issueSystem2);
    }
}
