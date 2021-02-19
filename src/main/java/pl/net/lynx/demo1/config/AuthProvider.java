package pl.net.lynx.demo1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
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

@Component
public class AuthProvider implements AuthenticationProvider {
    private static final int ATTEMPTS_LIMIT = 3;

    @Autowired private SecurityUserDetailsService userDetailsService;
    @Autowired private PasswordEncoder encoder;
    @Autowired private AttemptsRepository attemptsRepository;
    @Autowired private UserRepository userRepository;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        System.out.println("username = " + username + " password = " + password);

        User user = userRepository.findUserByUsername(username);

        if(user == null){
            System.out.println(" no user");
            throw new BadCredentialsException(" no user");
        }

        if(!user.isEnabled()){
            System.out.println(" user is disable ");
            throw new BadCredentialsException(" user is disable ");
        }

        if(!user.isAccountNonExpired()){
            System.out.println(" user is expired ");
            throw new BadCredentialsException(" user is expired ");
        }

        if(!user.isAccountNonLocked()){
            System.out.println(" user is locked ");
            throw new BadCredentialsException(" user is locked ");
        }

        if(!encoder.matches(password, user.getPassword())){
            System.out.println(" bad password ");
            processFailedAttempts(user);
            throw new BadCredentialsException(" bad password ");
        }

        return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());

        }


/*
        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        User user = userRepo.findOne(username);
        if (user == null) {
            throw new BadCredentialsException("1000");
        }
        if (user.isDisabled()) {
            throw new DisabledException("1001");
        }
        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("1000");
        }
        List<Right> userRights = rightRepo.getUserRights(username);
        return new UsernamePasswordAuthenticationToken(username, password, userRights.stream().map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList()));
*/



    private void processFailedAttempts(User user){
        Attempts attempts = attemptsRepository.findAttemptsByUsername(user.getUsername());
        if(attempts != null){
            System.out.println("isPresent " + user.getUsername());
            attempts.setAttempts(attempts.getAttempts() + 1);
            attemptsRepository.save(attempts);

            if( attempts.getAttempts() +1 > ATTEMPTS_LIMIT){
                user.setAccountNonLocked(false);
                userRepository.save(user);
                throw new LockedException("To many invalid attempts.");
            }
        }else{
            System.out.println("not Present " + user.getUsername());
            attempts = new Attempts();
            attempts.setUsername(user.getUsername());
            attempts.setAttempts(1);
            attemptsRepository.save(attempts);
        }

    }


    @Override
    public boolean supports(Class<?> authentication) {
       return true;
    }


}




