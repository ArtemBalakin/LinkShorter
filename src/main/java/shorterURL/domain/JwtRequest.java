package shorterURL.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {
    @NotNull
    private String login;
    @NotNull
    private String password;

}
