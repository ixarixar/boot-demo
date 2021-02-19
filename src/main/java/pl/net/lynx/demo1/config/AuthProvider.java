package pl.net.lynx.demo1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.net.lynx.demo1.web.formlogin.Attempts;
import pl.net.lynx.demo1.web.formlogin.AttemptsRepository;
import pl.net.lynx.demo1.web.formlogin.User;
import pl.net.lynx.demo1.web.formlogin.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Component
public class AuthProvider implements AuthenticationProvider {
    private static final int ATTEMPTS_LIMIT = 3;

    @Autowired private SecurityUserDetailsService userDetailsService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AttemptsRepository attemptsRepository;
    @Autowired private UserRepository userRepository;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userName = authentication.getName();
        Optional<User> user = userRepository.findUserByUsername(userName);

        if(user.isPresent()){

            String pass = authentication.getCredentials() == null ? "" : authentication.getCredentials().toString();

            System.out.println("user : " + user.get().getUsername() + " pass : " + pass );


            if(passwordEncoder.matches(pass, user.get().getPassword())) {
                System.out.println("Zalogowano: " + user.get().getPassword());
                return new UsernamePasswordAuthenticationToken(
                        user.get().getUsername(),
                        pass,
                        Collections.emptyList());
            }

        }

        System.out.println("authenticate " + userName);
        /*
        Optional<Attempts> userAttempts = attemptsRepository.findAttemptsByUsername(userName);
        if(userAttempts.isPresent()){
            Attempts attempts = userAttempts.get();
            attempts.setAttempts(0);
        }
        */
        return null;

    }







/*
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!StringUtils.endsWithIgnoreCase(authentication.getPrincipal().toString(), Constants.DB_USERNAME_SUFFIX)) {
            // this user is not supported by DB authentication
            return null;
        }

        UserDetails user = combinedUserDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        String rawPw = authentication.getCredentials() == null ? null : authentication.getCredentials().toString();

        if (passwordEncoder.matches(rawPw, user.getPassword())) {
            LOGGER.warn("User successfully logged in: {}", user.getUsername());
            return new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    rawPw,
                    Collections.emptyList());
        } else {
            LOGGER.error("User failed to log in: {}", user.getUsername());
            throw new BadCredentialsException("Bad password");
        }
    }


 */



    private void processFailedAttempts(String userName, User user){
        Optional<Attempts> userAttempts = attemptsRepository.findAttemptsByUsername(userName);
        if(userAttempts.isPresent()){
            System.out.println("isPresent " + userName);
            Attempts attempts = userAttempts.get();
            attempts.setAttempts(attempts.getAttempts() + 1);
            attemptsRepository.save(attempts);

            if( attempts.getAttempts() +1 > ATTEMPTS_LIMIT){
                user.setAccountNonLocked(false);
                userRepository.save(user);
                throw new LockedException("To many invalid attempts.");
            }
        }else{
            System.out.println("not Present " + userName);
            Attempts attempts = new Attempts();
            attempts.setUsername(userName);
            attempts.setAttempts(1);
            attemptsRepository.save(attempts);
        }

    }


    @Override
    public boolean supports(Class<?> authentication) {
       return true;
    }


}




