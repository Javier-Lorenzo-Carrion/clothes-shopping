package com.lorenzoconsultores.clothesshopping;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

class UserServiceTest {
    // los campos requeridos son name, lastName, birthDate y email
    // que la fecha de nacimiento sea valida (el usuario tiene que ser mayor de edad y el formato de fecha debe ser el esperado que será el formato espaniol)
    // que el email sea valido
    // que el email sea unico
    @Test
    public void should_create_a_new_user() {
        //Given
        UserService userService = new UserService();
        //When
        User user = userService.create("Javier", "Lorenzo Carrion", "17/03/1989", "javierlorenzocarrion@gmail.com");
        //Then
        Assertions.assertThat(user.getId()).isNotBlank();
        Assertions.assertThat(user.getName()).isEqualTo("Javier");
        Assertions.assertThat(user.getLastName()).isEqualTo("Lorenzo Carrion");
        Assertions.assertThat(user.getBirthDate()).isEqualTo("17/03/1989");
        Assertions.assertThat(user.getEmail()).isEqualTo("javierlorenzocarrion@gmail.com");
    }

    @Test
    public void should_throw_an_exception_when_format_birthDate_is_not_valid() {
        //Given
        UserService userService = new UserService();
        //When Then
        Assertions.assertThatThrownBy(() -> userService.create("Javier", "Lorenzo Carrion", "17-03-1989", "javierlorenzocarrion@gmail.com"))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("Birth date must have a valid format like \"dd/MM/yyyy\"");
    }

}