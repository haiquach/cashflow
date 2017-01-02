package com.hquach.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

/**
 * Created by HQ on 7/27/2016.
 */
@Document(collection = "users")
public class User {

    public User() {
        createdDate = new Date();
        updatedDate = new Date();
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

    private Date createdDate;

    private Date updatedDate;

    private Collection<String> roles;

    private String houseHoldId;

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
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getHouseHoldId() {
        return houseHoldId;
    }

    public void setHouseHoldId(String houseHoldId) {
        this.houseHoldId = houseHoldId;
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
        StringBuilder result = new StringBuilder();
        result.append("User [userId=" + userId );
        result.append(", password=" + password);
        result.append(", firstName=" + firstName);
        result.append(", lastName=" + lastName);
        result.append(", email=" + email);
        result.append(", created=" + createdDate);
        result.append(", updated=" + updatedDate);
        result.append("]");
        return result.toString();
    }

}
