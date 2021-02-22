package pl.net.lynx.demo1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.net.lynx.demo1.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserByUsername(String Username);
}
