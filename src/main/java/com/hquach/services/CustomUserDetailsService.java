package com.hquach.services;

import com.hquach.model.User;
import com.hquach.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Login custom user details service
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String userId)
            throws UsernameNotFoundException {
        User user = getUser(userId);
        if (user == null) {
            LOG.info("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(),
                true, true, true, true, getGrantedAuthorities(user));
    }


    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (String userProfile : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(userProfile));
        }
        return authorities;
    }

    private User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }

}
