package pl.net.lynx.demo1.web.formlogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.net.lynx.demo1.config.SecurityUserDetailsService;

import java.beans.ExceptionListener;

@Service
public class HelloService {

    @Autowired private PasswordEncoder encoder;
    @Autowired private SecurityUserDetailsService securityUserDetailsService;

    public void addUser(String username, String password) {


        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setAccountNonLocked(true);
        securityUserDetailsService.createUser(user);

        //throw new BadCredentialsException(" no user");
    }


}
