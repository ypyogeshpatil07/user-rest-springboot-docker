package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.dto.UserRequest;
import co.zip.candidate.userapi.dto.UserResponse;
import co.zip.candidate.userapi.entity.User;
import co.zip.candidate.userapi.exception.UserNotFoundException;
import co.zip.candidate.userapi.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for Users.
 *
 * @author Yogesh P.
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users")
    public User createUser(@Valid @RequestBody UserRequest userRequest) {
        logger.info("In createUser request" + userRequest);
        return userService.createUser(userRequest);

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{id}")
    public User getUserById(@PathVariable("id") Integer id) throws UserNotFoundException {
        logger.info("In getUserById request " + id);
        return userService.getUser(id);

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users")
    public List<User> getAllUsers() throws UserNotFoundException {
        logger.info("In getAllUsers");
        return userService.getAllUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/pagination/{offset}/{pageSize}/{sortByField}")
    public UserResponse<Page<User>> getAllUsersWithPagination(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String sortByField) throws UserNotFoundException {
        Page<User> usersWithPagination = userService.getAllUsersWithPagination(offset, pageSize, sortByField);
        return new UserResponse<>(usersWithPagination.getSize(), usersWithPagination);

    }

}
