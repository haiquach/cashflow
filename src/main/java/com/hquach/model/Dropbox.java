package com.hquach.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by HQ on 9/10/2016.
 */
public class Dropbox {

    @NotEmpty
    private String token;
    private String email;
    private String displayName;

    public Dropbox() {}

    public Dropbox(String token, String email, String displayName) {
        this.token = token;
        this.email = email;
        this.displayName = displayName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
