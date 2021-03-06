package com.denis.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
/*import org.springframework.security.core.GrantedAuthority;*/


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Role  implements Serializable, GrantedAuthority {

    private long role_id;

    private List<User> user;


    private String role;

    public Role(){
    }

    public Role(String role) {
        this.role = role;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(role);
    }


    @Override
    public String getAuthority() {
        return role;
    }


    @Override
    public String toString() {
        return role;
    }
}
