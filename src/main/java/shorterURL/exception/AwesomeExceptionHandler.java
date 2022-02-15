package shorterURL.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;

@RestControllerAdvice
public class AwesomeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthException.class)
    protected ErrorResponse handleThereIsNoSuchUserException() {
        return buildError("There is no such user or you used incorrect tokens", HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(RegistrationException.class)
    protected ErrorResponse handleRegException() {
        return buildError("Login already exist", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ErrorResponse handleThereIsNoSuchResourceException() {
        System.out.println("Ex");
        return buildError("There is no such resource", HttpStatus.NOT_FOUND);
    }

    private ErrorResponse buildError(String message, HttpStatus status) {
        return ErrorResponse.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .message(message)
                .status(status).build();
    }
}

