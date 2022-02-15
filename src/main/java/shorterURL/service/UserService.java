package shorterURL.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import shorterURL.entity.Role;
import shorterURL.entity.User;
import shorterURL.exception.RegistrationException;
import shorterURL.repositories.UserRepository;

@Service

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByLogin(@NonNull String login) {

        return userRepository.findByLogin(login);
    }

    public User regUser(String login, String password) {
        User user = User.builder().login(login).password(password).roles(Role.USER).build();
        if(userRepository.findByLogin(login)!=null) throw new RegistrationException();
        return userRepository.save(user);
    }

}