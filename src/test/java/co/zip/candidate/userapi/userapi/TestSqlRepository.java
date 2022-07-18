package co.zip.candidate.userapi.userapi;

import co.zip.candidate.userapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestSqlRepository extends JpaRepository<User,Integer> {
}
