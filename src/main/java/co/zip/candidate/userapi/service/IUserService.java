package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.dto.UserRequest;
import co.zip.candidate.userapi.entity.User;
import co.zip.candidate.userapi.exception.UserNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Interface all operations in Users.
 */
public interface IUserService {

    User createUser(UserRequest userRequest);

    User getUser(Integer id) throws UserNotFoundException;

    List<User> getAllUsers() throws UserNotFoundException;

     Page<User> getAllUsersWithPagination(int offset, int pageSize, String sortByField) throws UserNotFoundException;


}
