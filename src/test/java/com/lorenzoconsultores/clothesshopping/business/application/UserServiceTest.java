package com.lorenzoconsultores.clothesshopping.business.application;

import com.lorenzoconsultores.clothesshopping.business.domain.InvalidUserException;
import com.lorenzoconsultores.clothesshopping.business.domain.User;
import com.lorenzoconsultores.clothesshopping.business.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

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

    @Test
    public void should_throw_an_exception_when_email_is_not_valid() {
        //Given
        UserService userService = new UserService(mockUserRepository);
        //When Then
        Assertions.assertThatThrownBy(() -> userService.create("Javier", "Lorenzo Carrion", "17/03/1989", "javierlorenzocarrion.com"))
                .isInstanceOf(InvalidUserException.class)
                .hasMessage("Email must have a valid format like \"john.doe@example.org\"");
    }

    @Test
    public void should_retrieve_all_users() {
        //Given
        UserService userService = new UserService(mockUserRepository);
        User userJavier = new User("234567654", "Javi", "Lorenzo Carrion", "17/03/1989", "javierlorenzocarrion@gmail.com");
        User userMiguel = new User("234fdvdcfsdvc4", "Miguel", "Lorenzo Carrion", "17/03/1989", "javierlo@gmail.com");
        User userSergio = new User("23456fddvcfd7654", "Sergio", "Lorenzo Carrion", "17/03/1989", "jav@gmail.com");
        Mockito.when(mockUserRepository.findAll()).thenReturn(List.of(userJavier, userMiguel, userSergio));
        //When
        List<User> actual = userService.findAll();
        //Then
        Assertions.assertThat(actual).containsExactly(userJavier, userMiguel, userSergio);
    }

    @Test
    public void should_update_user() {
        //Given
        User userUpdateable = new User("4567897", "Paco", "Alvarez Lugo", "01/01/2000", "paco@gmail.com");
        UserService userService = new UserService(mockUserRepository);
        Mockito.when(mockUserRepository.findById(userUpdateable.getId())).thenReturn(Optional.of(userUpdateable));
        //When
        userService.update(userUpdateable.getId(), Optional.of("Chano"), Optional.empty(), Optional.empty(), Optional.empty());
        //Then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(mockUserRepository).save(userArgumentCaptor.capture());
        User actual = userArgumentCaptor.getValue();
        Assertions.assertThat(actual.getId()).isEqualTo(userUpdateable.getId());
        Assertions.assertThat(actual.getName()).isEqualTo("Chano");
        Assertions.assertThat(actual.getLastName()).isEqualTo(userUpdateable.getLastName());
        Assertions.assertThat(actual.getBirthDate()).isEqualTo(userUpdateable.getBirthDate());
        Assertions.assertThat(actual.getEmail()).isEqualTo(userUpdateable.getEmail());
    }

}