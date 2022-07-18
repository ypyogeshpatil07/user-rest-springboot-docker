package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.dto.UserRequest;
import co.zip.candidate.userapi.entity.User;
import co.zip.candidate.userapi.exception.DuplicateEmailIDException;
import co.zip.candidate.userapi.exception.UserNotFoundException;
import co.zip.candidate.userapi.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Users.
 * @author Yogesh P.
 */
@Service
public class UserServiceImpl implements IUserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserRepository userRepository;

    @Override
    public User createUser(UserRequest userRequest) throws DuplicateEmailIDException {
        checkIfAccountAlreadyExist(userRequest);
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setSalary(userRequest.getSalary());
        user.setExpenses(userRequest.getExpenses());
        logger.info("User is getting saved in DB" + user);
        return userRepository.save(user);

    }


    private void checkIfAccountAlreadyExist(UserRequest userRequest) {
        Optional<User> accountExist = userRepository.findByEmail(userRequest.getEmail());
        if (accountExist.isPresent()) {
            logger.info("Given User has duplicate email id in DB,Please check email id:" + userRequest.getEmail());
            throw new DuplicateEmailIDException("Given User has duplicate email id in DB,Please check email id:" + userRequest.getEmail());
        }
    }

    @Override
    @Cacheable(value="users-cache", key = "#id")
    public User getUser(Integer id) throws UserNotFoundException {
        User user = null;
        logger.info("loading from db");
        user = userRepository.findById(id);
        if (null != user) {
            logger.info("Requested User retrieved from DB by User Id :->" + user);
            return user;
        } else {
            throw new UserNotFoundException("User not found for user Id " + id);
        }

    }

    @Override
    public List<User> getAllUsers() throws UserNotFoundException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found in DB");
        }
        logger.info("Users List successfully returned ->" + users);
        return users;

    }
    @Override
    public Page<User> getAllUsersWithPagination(int offset, int pageSize,String sortByField) throws UserNotFoundException {
        Page<User> users = userRepository.findAll(PageRequest.of(offset,pageSize).withSort(Sort.by(sortByField)));
        if (users.isEmpty()){
            throw new UserNotFoundException("No users found in DB");
        }
        logger.info( users.getTotalElements() + " User(s) successfully returned");
        return users;

    }


}
