package com.denis.model;

import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable, UserDetails {
    // TODO: 12.04.2018 Подумать над параметрами пользователя.
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "userRoles")
    private String role;

    @Column(name = "enabled")
    @Type(type = "boolean")
    private boolean enabled;

    public User() {
    }

    public User(String username, String password, Set<GrantedAuthority> roles) {
        this.username = username;
        this.password = password;
        this.role = roles.iterator().next().getAuthority();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role));
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return this.enabled;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }


    public void setLogin(final String login) {
        this.username = login;
    }


    public void setPassword(final String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        return (7 * Objects.hashCode(getUsername())) +
                (11 * Objects.hashCode(getPassword()));
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final User other = (User) obj;
        return Objects.equals(getUsername(), other.getUsername())
                && Objects.equals(getPassword(), other.getPassword());
    }

    @Override
    public String toString() {
        return "[id " + getId() +
                " login: " + getUsername() +
                ", password: " + getPassword() +
                ", role " + getRole() +
                ", enabled: " + isEnabled() +
                "]";
    }
}
