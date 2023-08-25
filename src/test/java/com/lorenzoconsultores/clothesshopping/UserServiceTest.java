package com.lorenzoconsultores.clothesshopping;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

class UserServiceTest {
    private final UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
    // los campos requeridos son name, lastName, birthDate y email
    // que la fecha de nacimiento sea valida (el usuario tiene que ser mayor de edad y el formato de fecha debe ser el esperado que será el formato espaniol)
    // que el email sea valido
    // que el email sea unico
    @Test
    public void should_create_a_new_user() {
        //Given
        UserService userService = new UserService(mockUserRepository);
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
        //Given
        UserService userService = new UserService(mockUserRepository);
        //When Then
        Assertions.assertThatThrownBy(() -> userService.create("Javier", "Lorenzo Carrion", "17-03-1989", "javierlorenzocarrion@gmail.com"))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("Birth date must have a valid format like \"dd/MM/yyyy\"");
    }

}