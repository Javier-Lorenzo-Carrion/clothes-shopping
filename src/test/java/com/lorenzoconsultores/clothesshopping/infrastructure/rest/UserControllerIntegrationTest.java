package com.lorenzoconsultores.clothesshopping.infrastructure.rest;

import com.lorenzoconsultores.clothesshopping.common.IntegrationTest;
import com.lorenzoconsultores.clothesshopping.infrastructure.persistence.entities.UserEntity;
import com.lorenzoconsultores.clothesshopping.infrastructure.persistence.jpa.UserJPARepository;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;

@IntegrationTest
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserJPARepository userJPARepository;

    @AfterEach
    public void tearDown() {
        userJPARepository.deleteAllInBatch();
    }

    @Nested
    @DisplayName("POST /users")
    class PostCreateUser {
        @Test
        public void should_create_a_user() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content("""
                    {"name": "Pepe", "lastName": "Rojas", "birthDate": "15/03/1989", "email": "peperojas@gmail.com"}
                    """)).andExpect(MockMvcResultMatchers.status().isCreated());

            Optional<UserEntity> userEntity = userJPARepository.findByEmail("peperojas@gmail.com");

            Assertions.assertThat(userEntity.isPresent()).isTrue();
            Assertions.assertThat(userEntity.get().getName()).isEqualTo("Pepe");
            Assertions.assertThat(userEntity.get().getEmail()).isEqualTo("peperojas@gmail.com");

        }

        @Test
        public void should_inform_email_is_already_used() throws Exception {
            UserEntity userWithEmailDuplicated = new UserEntity(UUID.randomUUID(), "Pepe", "Rojas", "15/03/1989", "peperojas@gmail.com");
            userJPARepository.saveAndFlush(userWithEmailDuplicated);
            mockMvc.perform(MockMvcRequestBuilders.post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {"name": "Pepe", "lastName": "Rojas", "birthDate": "15/03/1989", "email": "peperojas@gmail.com"}
                                    """))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Invalid email, peperojas@gmail.com is already in use")));

        }
    }
}
