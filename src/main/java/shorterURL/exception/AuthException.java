package shorterURL.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
