package pl.net.lynx.demo1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.net.lynx.demo1.model.User;
import pl.net.lynx.demo1.repository.UserRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("SecurityUserDetailsService -> loadUserByUsername -> " + username);
        User user = userRepository.findUserByUsername(username);
        if(user == null){
            new UsernameNotFoundException("User not present");
        }
        return user;
    }

    public void createUser(UserDetails userDetails){
        userRepository.save((User) userDetails);
    }

}
