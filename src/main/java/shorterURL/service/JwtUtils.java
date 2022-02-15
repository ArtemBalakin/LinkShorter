package shorterURL.service;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import shorterURL.domain.JwtAuthentication;
import shorterURL.entity.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setFirstName(claims.get("firstName", String.class));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

    private static Set<Role> getRoles(Claims claims) {

        final List<String> roles=new ArrayList<>(); //List.of(claims.get("roles", String.class));
        roles.add("USER");
        System.out.println(roles.get(0)+" TUTUTUT");
        return roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }

}
