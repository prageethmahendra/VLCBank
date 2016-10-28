package com.vlc.application.resources;

import com.vlc.application.resources.entity.User;

/**
 * Created by prageeth.g on 27/10/2016.
 */
public class SecurityContext {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
