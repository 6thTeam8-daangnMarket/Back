package com.sparta.clone_backend.security;

<<<<<<< HEAD
=======

>>>>>>> origin/write&detail
import com.sparta.clone_backend.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

<<<<<<< HEAD

import java.util.Collection;


=======
import java.util.ArrayList;
import java.util.Collection;

>>>>>>> origin/write&detail
public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
<<<<<<< HEAD
        return user.getUsername();
    }
    public String getNickname(){
        return user.getNickname();
    }
=======
        return user.getUsername(); }

    public String getNickname() { return user.getNickname(); }
>>>>>>> origin/write&detail

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
<<<<<<< HEAD

        return null;
    }
=======
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        return authorities;
    }

>>>>>>> origin/write&detail
}