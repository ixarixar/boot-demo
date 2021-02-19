package pl.net.lynx.demo1.web.formlogin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserByUsername(String Username);
}
