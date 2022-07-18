package co.zip.candidate.userapi.advice;

import co.zip.candidate.userapi.error.CustomError;
import co.zip.candidate.userapi.exception.DuplicateEmailIDException;
import co.zip.candidate.userapi.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Advice for Users.
 * @author Yogesh P
 */

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> handleConstraintViolationException(MethodArgumentNotValidException exception) {
        CustomError customError = new CustomError();
        for (ObjectError ex : exception.getAllErrors()){
            customError.setMessage(ex.getDefaultMessage());
        }

        customError.setStatus(HttpStatus.PRECONDITION_FAILED);
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(customError);
    }

    @ExceptionHandler(DuplicateEmailIDException.class)
    public  ResponseEntity<CustomError> handleBusinessException(DuplicateEmailIDException ex) {
        CustomError customError = new CustomError();
        customError.setMessage(ex.getMessage());
        customError.setStatus(HttpStatus.CONFLICT);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(customError);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomError> handleAccountNotFoundErrors(UserNotFoundException ex) {
        CustomError customError = new CustomError();
        customError.setMessage(ex.getMessage());
        customError.setStatus(HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomError> handleJsonErrors(HttpMessageNotReadableException ex) {
        CustomError customError = new CustomError();
        customError.setMessage(ex.getMessage());
        customError.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(customError);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CustomError> handleMethodNotSupportedErrors(HttpRequestMethodNotSupportedException ex) {
        CustomError customError = new CustomError();
        customError.setMessage(ex.getMessage());
        customError.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(customError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllServerErrors(Exception ex,
                                         HttpServletRequest request, HttpServletResponse response) {
        CustomError customError = new CustomError();
        if (ex instanceof NullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        customError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        customError.setMessage("Server is down..Please Try again after sometime..!!");
        return ResponseEntity.internalServerError().body(customError);
    }


}
