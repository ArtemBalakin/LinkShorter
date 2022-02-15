package shorterURL.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegistrationException extends RuntimeException {
    public RegistrationException(String message) {
        super(message);
    }
}
