package co.zip.candidate.userapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main class for User Application.
 * @author Yogesh P
 */
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "USER-API",version = "1.0",description = "This is User Api for all the user operations"))
public class UserApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApiApplication.class, args);
	}

}

