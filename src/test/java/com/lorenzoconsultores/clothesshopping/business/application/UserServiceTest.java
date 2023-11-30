package com.lorenzoconsultores.clothesshopping.business.application;

import com.lorenzoconsultores.clothesshopping.business.domain.InvalidUserException;
import com.lorenzoconsultores.clothesshopping.business.domain.User;
import com.lorenzoconsultores.clothesshopping.business.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class UserServiceTest {
    private final UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
    private final UserService userService = new UserService(mockUserRepository);

    @Nested
    class Create {
        @Test
        public void should_create_a_new_user() {
            //When
            userService.create("Javier", "Lorenzo Carrion", "17/03/1989", "javierlorenzocarrion@gmail.com");

            //Then
            ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
            Mockito.verify(mockUserRepository).save(userArgumentCaptor.capture());
            User actual = userArgumentCaptor.getValue();
            Assertions.assertThat(actual.getId()).isNotBlank();
            Assertions.assertThat(actual.getName()).isEqualTo("Javier");
            Assertions.assertThat(actual.getLastName()).isEqualTo("Lorenzo Carrion");
            Assertions.assertThat(actual.getBirthDate()).isEqualTo("17/03/1989");
            Assertions.assertThat(actual.getEmail()).isEqualTo("javierlorenzocarrion@gmail.com");
        }

        @Test
        public void should_throw_an_exception_when_format_birthDate_is_not_valid() {
            //When Then
            Assertions.assertThatThrownBy(() -> userService.create("Javier", "Lorenzo Carrion", "17-03-1989", "javierlorenzocarrion@gmail.com"))
                    .isInstanceOf(InvalidUserException.class)
                    .hasMessage("Birth date must have a valid format like \"dd/MM/yyyy\"");
        }
    }

    @Nested
    class FindUsers {
        @Test
        public void should_find_all_users() {
            userService.findAll();

            Mockito.verify(mockUserRepository).findAll();
        }
    }

}