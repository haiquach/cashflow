package com.hquach.repository;

import com.hquach.model.Dropbox;
import com.hquach.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

/**
 * User Repository
 * @author Hai Quach
 */
@Repository
public class UserRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUserId(String userId) {
        Query query = Query.query(Criteria.where("_id").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }

    public Collection<User> findAllUsers() {
        return mongoTemplate.findAll(User.class);
    }

    public void save(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        mongoTemplate.save(user);
    }

    public void deleteUser(String userId) {
        mongoTemplate.remove(Query.query(Criteria.where("_id").is(userId)), User.class);
    }

    public boolean isUserExisted(String userId) {
        return mongoTemplate.exists(Query.query(Criteria.where("_id").is(userId)), User.class);
    }

    public String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        if (authentication.getName().equals("admin")) {
            return User.ADMIN;
        }
        return findByUserId(authentication.getName());
    }

    public void updateUser(String userId, String firstName, String lastName, String email, Collection<String> roles) {
        Query query = Query.query(Criteria.where("userId").in(userId));
        Update update = new Update();
        update.set("firstName", firstName);
        update.set("lastName", lastName);
        update.set("email", email);
        if (roles != null) {
            update.set("roles", roles);
        }
        mongoTemplate.updateMulti(query, update, User.class);
    }

    public void resetPassword(String userId, String password) {
        User user = findByUserId(userId);
        user.setPassword(passwordEncoder.encode(password));
        mongoTemplate.save(user);
    }

    public void saveDropbox(String dropbox) {
        User user = getLoggedUser();
        user.setDropbox(dropbox);
        mongoTemplate.save(user);
    }
}
