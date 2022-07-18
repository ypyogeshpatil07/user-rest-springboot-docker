package co.zip.candidate.userapi.userapi;

import co.zip.candidate.userapi.dto.UserRequest;
import co.zip.candidate.userapi.entity.User;
import co.zip.candidate.userapi.exception.DuplicateEmailIDException;
import co.zip.candidate.userapi.exception.UserNotFoundException;
import co.zip.candidate.userapi.repository.IUserRepository;
import co.zip.candidate.userapi.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Service Junit Tests
 *
 * @author Yogesh P
 */

@SpringBootTest
class UserServiceUnitTests {

    @Autowired
    private UserServiceImpl userService;


    @MockBean
    private IUserRepository userRepository;


    @Test
    public void createUserTest() {
        UserRequest userRequest = new UserRequest("Yogesh", "yogesh@gmail.com", 44000.0, 540.30);
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setSalary(userRequest.getSalary());
        user.setExpenses(userRequest.getExpenses());
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByEmail("yogesh@gmail.com")).thenReturn(Optional.empty());
        assertEquals(user, userService.createUser(userRequest));
    }


    @Test
    public void getAllUsersTest() throws UserNotFoundException {
        List<User> listOfUsers = new ArrayList<>();
        listOfUsers.add(new User(3, "Yogesh", "yogesh@gmail.com", 44000.0, 540.30));
        listOfUsers.add(new User(4, "Tom", "tom@gmail.com", 43444.20, 452.21));
        when(userRepository.findAll()).
                thenReturn(listOfUsers);
        assertEquals(2, userService.getAllUsers().size());

    }

    @Test
    public void getUsersByIdTest() throws UserNotFoundException {
        int userId = 3;
        when(userRepository.findById(userId))
                .thenReturn(new User(3, "Yogesh", "yogesh@gmail.com", 44000.0, 540.30));
        assertNotNull(userService.getUser(userId).getId());
        assertEquals("Yogesh", userService.getUser(userId).getName());
    }


    @Test
    public void whenWrongUserIdExceptionTest() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUser(8);
        });
        String expectedMessage = "User not found for user Id";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenNotValidUserIdExceptionTest() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUser(8);
        });
        String expectedMessage = "User not found for user Id";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void duplicateEmailIdExceptionTest() {
        UserRequest userRequest = new UserRequest("Yogesh", "yogesh@gmail.com", 44000.0, 540.30);
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setSalary(userRequest.getSalary());
        user.setExpenses(userRequest.getExpenses());
        when(userRepository.findByEmail("yogesh@gmail.com")).thenReturn(Optional.of(new User(3, "Yogesh", "yogesh@gmail.com", 44000.0, 540.30)));
        Exception exception = assertThrows(DuplicateEmailIDException.class, () -> {
            userService.createUser(userRequest);
        });

        String expectedMessage = "Given User has duplicate email id in DB,Please check email id:";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
