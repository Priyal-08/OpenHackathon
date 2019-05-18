package com.openhack.security;

import com.openhack.domain.UserAccount;
import com.openhack.domain.UserProfile;
import com.openhack.domain.UserRole; 
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class UserPrincipal implements UserDetails {
    
	private static final long serialVersionUID = 1L;

	private long id;

    private String username;
    
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;
    
    public UserPrincipal() {}

    public UserPrincipal(long id, String username,  String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(UserProfile userProfile, UserAccount userAccount, List<UserRole> userRole) {
        List<GrantedAuthority> authorities = userRole.stream().map(role ->
                new SimpleGrantedAuthority(role.getRole())
        ).collect(Collectors.toList());

        return new UserPrincipal(
        		userProfile.getId(),
        		userProfile.getEmail(),
                userAccount.getPassword(),
                authorities
        );
    }

    public long getId() {
        return id;
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
