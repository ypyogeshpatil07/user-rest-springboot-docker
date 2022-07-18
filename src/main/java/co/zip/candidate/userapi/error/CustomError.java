package co.zip.candidate.userapi.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomError {

    private HttpStatus status;
    private String message;

    public void addConstraintErrors(Set<ConstraintViolation<?>> constraintViolations) {
    }


}
