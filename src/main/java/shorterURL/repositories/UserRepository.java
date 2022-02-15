package shorterURL.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shorterURL.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByLogin(String login);
}
