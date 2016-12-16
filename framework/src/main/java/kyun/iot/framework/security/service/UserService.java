package kyun.iot.framework.security.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kyun.iot.framework.security.service.domain.Role;
import kyun.iot.framework.security.service.domain.User;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {

        logger.info("username : " + username);

        String password = "$2a$10$KubYkiMX8KauRi5UuhucjOH2YCFPxXe0CbW9jsFSCUUL5sigh//Nq";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        Role role = new Role();
        role.setName("ROLE_USER");

        List<Role> roles = new ArrayList<Role>();
        roles.add(role);
        user.setAuthorities(roles);

        /*if (user == null) {
            throw new UsernameNotFoundException("접속자 정보를 찾을 수 없습니다.");
        }*/

        return user;
    }
}