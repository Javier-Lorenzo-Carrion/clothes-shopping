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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
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
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Email, peperojas@gmail.com, is already in use")));

        }

        @Test
        public void should_inform_email_is_not_valid() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {"name": "Pepe", "lastName": "Rojas", "birthDate": "15/03/1989", "email": "peperojas.com"}
                                    """))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Email must have a valid format like \"john.doe@example.org\"")));
        }
    }

    @Nested
    @DisplayName("GET /users")
    class GetAllUsers {
        @Test
        public void should_retrieve_all_the_users() throws Exception {
            UserEntity userPepe = new UserEntity(UUID.randomUUID(), "Pepe", "Rojas", "15/03/1989", "peperojas@gmail.com");
            UserEntity userMudo = new UserEntity(UUID.randomUUID(), "Mudo", "Lorenzo", "15/06/1989", "mudaso@gmail.com");
            UserEntity userMiguel = new UserEntity(UUID.randomUUID(), "Miguel", "Cabrera", "15/09/1989", "sepelio@gmail.com");
            userJPARepository.saveAllAndFlush(List.of(userPepe, userMudo, userMiguel));
            mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("peperojas@gmail.com")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", Matchers.is("mudaso@gmail.com")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[2].email", Matchers.is("sepelio@gmail.com")))
                    .andDo(MockMvcResultHandlers.print());
        }
    }

    @Nested
    @DisplayName("PATCH /users/{id}")
    class UpdateUser {
        @Test
        public void should_update_existing_user() throws Exception {
            //Given
            UUID id = UUID.randomUUID();
            UserEntity userToUpdate = new UserEntity(id, "Lolo", "Cabrera", "15/09/1989", "sepelio@gmail.com");
            userJPARepository.saveAndFlush(userToUpdate);
            //When
            mockMvc.perform(MockMvcRequestBuilders.patch("/users/" + id).contentType(MediaType.APPLICATION_JSON).content("""
                    {"name": "Pepe"}
                    """)).andExpect(MockMvcResultMatchers.status().isNoContent());
            //Then
            Optional<UserEntity> updatedUser = userJPARepository.findById(id);
            Assertions.assertThat(updatedUser.isPresent()).isTrue();
            Assertions.assertThat(updatedUser.get().getName()).isEqualTo("Pepe");
            Assertions.assertThat(updatedUser.get().getLastName()).isEqualTo("Cabrera");
            Assertions.assertThat(updatedUser.get().getBirthDate()).isEqualTo("15/09/1989");
            Assertions.assertThat(updatedUser.get().getEmail()).isEqualTo("sepelio@gmail.com");
        }

        @Test
        public void should_fail_when_update_non_existing_user() throws Exception {
            //Given
            UUID id = UUID.randomUUID();
            //When //Then
            mockMvc.perform(MockMvcRequestBuilders.patch("/users/" + id).contentType(MediaType.APPLICATION_JSON).content("""
                            {"name": "Carolina"}
                            """))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("User with id '" + id + "' not found.")));
        }
    }
}
