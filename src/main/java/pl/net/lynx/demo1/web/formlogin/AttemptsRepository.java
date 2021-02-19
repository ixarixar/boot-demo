package pl.net.lynx.demo1.web.formlogin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptsRepository extends JpaRepository<Attempts, Integer> {
    Attempts findAttemptsByUsername(String username);
}
