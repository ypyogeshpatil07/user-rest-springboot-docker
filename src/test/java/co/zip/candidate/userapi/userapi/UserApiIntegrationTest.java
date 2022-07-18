package co.zip.candidate.userapi.userapi;

import co.zip.candidate.userapi.UserApiApplication;
import co.zip.candidate.userapi.dto.UserRequest;
import co.zip.candidate.userapi.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Users Integration Test.
 * @author Yogesh P
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = UserApiApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserApiIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private TestSqlRepository testSqlRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/v1");
    }

    @Test
    @Sql(statements = "DELETE FROM USER_TBL WHERE name = 'Yogesh'",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void createUserTest() {
        UserRequest userRequest = new UserRequest("Yogesh","yogesh@gmail.com",44000.0,540.30);
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setSalary(userRequest.getSalary());
        user.setExpenses(userRequest.getExpenses());
        String finalUrl = baseUrl.concat("/users");
        User accountResponse = restTemplate.postForObject(finalUrl, userRequest, User.class);
        assertEquals("Yogesh",accountResponse.getName());
        assertEquals("yogesh@gmail.com",accountResponse.getEmail());
        assertEquals(44000.0,accountResponse.getSalary());
        assertEquals(540.30,accountResponse.getExpenses());
        assertEquals(1,testSqlRepository.findAll().size());

    }

    @Test
    @Sql(statements = "INSERT INTO USER_TBL (id,name,email,salary,expenses) VALUES (4,'Yogesh','yogesh@gmail.com',43440.0,323.3)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM USER_TBL WHERE name='Yogesh'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetAllAccounts() {
        String finalUrl = baseUrl.concat("/users");
        List users = restTemplate.getForObject(finalUrl, List.class);
        assertEquals(1, users.size());
    }



    @Test
    @Sql(statements = "INSERT INTO USER_TBL (id,name,email,salary,expenses) VALUES (1,'Yogesh','yogesh@gmail.com',43440.0,323.3)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM USER_TBL WHERE name='Yogesh'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetAccountByAccountId() {
        User user = restTemplate.getForObject(baseUrl + "/users/{userId}", User.class, 1);
        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals(1,user.getId()),
                () -> assertEquals("Yogesh", user.getName())
        );
    }
}
