package com.hquach.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by HQ on 7/27/2016.
 */
@Document(collection = "users")
public class User {

    public static final User ADMIN = new User("admin", "admin", null, null, null, LocalDateTime.now(),
            LocalDateTime.now(), null, null);

    public User() {
        new User(null, null, null, null, null, LocalDateTime.now(),
                LocalDateTime.now(), null, null);
    }
    public User(String userId, String password, String firstName, String lastName, String email,
                LocalDateTime createdDate, LocalDateTime updatedDate, Collection<String> roles, String token) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.roles = roles;
        this.dropbox = token;
    }

    @Id
    @NotEmpty
    @Length(min = 5, max = 30)
    private String userId;

    private String password;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String email;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private Collection<String> roles;

    private String dropbox;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.updatedDate = LocalDateTime.now();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public String getDropboxToken() {
        return dropbox;
    }

    public void setDropbox(String dropbox) {
        this.dropbox = dropbox;
        this.updatedDate = LocalDateTime.now();
    }

    public boolean isAdmin() {
        for (String role : roles) {
            if ("ROLE_ADMIN".equals(role)) {
                return true;
            }
        }
        return false;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User {" +
                " userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", roles=" + roles +
                ", dropbox=" + dropbox +
                " }";
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }
}
