package com.client.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {
    // TODO: 12.04.2018 Подумать над параметрами пользователя.
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name= "email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Role_id")
    private Role role;


    public User() {    }


    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
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
                ", role:" + getRole() +
                "]";
    }
}