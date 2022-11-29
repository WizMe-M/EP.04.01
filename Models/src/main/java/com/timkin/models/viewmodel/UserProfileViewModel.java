package com.timkin.models.viewmodel;

import com.timkin.models.entity.Profile;
import com.timkin.models.entity.User;

public class UserProfileViewModel {

    private final User user;
    private final Profile profile;

    public UserProfileViewModel(User user, Profile profile) {
        this.user = user;
        this.profile = profile;
    }

    public User getUser() {
        return user;
    }

    public Profile getProfile() {
        return profile;
    }

    public boolean hasProfile() {
        return profile == null;
    }
}
