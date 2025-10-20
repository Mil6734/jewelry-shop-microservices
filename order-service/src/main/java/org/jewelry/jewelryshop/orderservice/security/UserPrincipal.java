package org.jewelry.jewelryshop.orderservice.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

/**
 * Principal, хранящий userId и роли
 */
public class UserPrincipal {
    private final UUID id;
    private final Collection<GrantedAuthority> authorities;

    public UserPrincipal(UUID id, Collection<GrantedAuthority> authorities) {
        this.id = id;
        this.authorities = authorities;
    }

    public UUID getId() {
        return id;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
