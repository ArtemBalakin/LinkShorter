package shorterURL.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shorterURL.domain.JwtResponse;
import shorterURL.domain.RefreshJwtRequest;
import shorterURL.entity.User;
import shorterURL.service.AuthService;
import shorterURL.service.UserService;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestParam("login") String login,
                                             @RequestParam("password") String password) {

        final JwtResponse token = authService.login(login, password);
        return ResponseEntity.ok(token);
    }

    @PostMapping("registry")
    public ResponseEntity registry(@RequestParam("login") String login,
                                   @RequestParam("password") String password) {
        final User user = userService.regUser(login, password);
        return ResponseEntity.ok(user);
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestParam("refreshToken") String oldToken) {
        final JwtResponse token = authService.getAccessToken(oldToken);
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestParam("refreshToken") String oldToken) {
        final JwtResponse token = authService.refresh(oldToken);
        return ResponseEntity.ok(token);
    }

}
