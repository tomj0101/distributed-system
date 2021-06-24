package com.github.tomj0101.ebankv1.ordersystemv3.ordersystemfrontend.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.github.tomj0101.ebankv1.ordersystemv3.ordersystemfrontend.IntegrationTest;
import com.github.tomj0101.ebankv1.ordersystemv3.ordersystemfrontend.domain.User;
import com.github.tomj0101.ebankv1.ordersystemv3.ordersystemfrontend.repository.UserRepository;
import com.github.tomj0101.ebankv1.ordersystemv3.ordersystemfrontend.repository.search.UserSearchRepository;
import com.github.tomj0101.ebankv1.ordersystemv3.ordersystemfrontend.security.AuthoritiesConstants;
import com.github.tomj0101.ebankv1.ordersystemv3.ordersystemfrontend.service.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/**
 * Integration tests for the {@link UserResource} REST controller.
 */
@AutoConfigureWebTestClient
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
@IntegrationTest
class PublicUserResourceIT {

    private static final String DEFAULT_LOGIN = "johndoe";

    @Autowired
    private UserRepository userRepository;

    /**
     * This repository is mocked in the com.github.tomj0101.ebankv1.ordersystemv3.ordersystemfrontend.repository.search test package.
     *
     * @see com.github.tomj0101.ebankv1.ordersystemv3.ordersystemfrontend.repository.search.UserSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserSearchRepository mockUserSearchRepository;

    @Autowired
    private WebTestClient webTestClient;

    private User user;

    @BeforeEach
    public void initTest() {
        user = UserResourceIT.initTestUser(userRepository);
    }

    @Test
    void getAllPublicUsers() {
        // Initialize the database
        userRepository.save(user).block();

        // Get all the users
        UserDTO foundUser = webTestClient
            .get()
            .uri("/api/users?sort=id,DESC")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .returnResult(UserDTO.class)
            .getResponseBody()
            .blockFirst();

        assertThat(foundUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
    }

    @Test
    void getAllAuthorities() {
        webTestClient
            .get()
            .uri("/api/authorities")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .expectBody()
            .jsonPath("$")
            .isArray()
            .jsonPath("$[?(@=='" + AuthoritiesConstants.ADMIN + "')]")
            .hasJsonPath()
            .jsonPath("$[?(@=='" + AuthoritiesConstants.USER + "')]")
            .hasJsonPath();
    }
}
