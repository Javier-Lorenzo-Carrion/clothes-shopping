package com.lorenzoconsultores.clothesshopping.infrastructure.rest;

import com.lorenzoconsultores.clothesshopping.infrastructure.persistence.entities.UserEntity;
import com.lorenzoconsultores.clothesshopping.infrastructure.persistence.jpa.UserJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserJPARepository userJPARepository;

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
    }
}
