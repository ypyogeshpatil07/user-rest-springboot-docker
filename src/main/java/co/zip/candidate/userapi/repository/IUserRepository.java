package co.zip.candidate.userapi.repository;

import co.zip.candidate.userapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Users.
 * @author Yogesh P
 */
@Repository
public interface IUserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String mailId);


    User findById(Integer id);
}
