package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {
    // TODO: 12.04.2018 Подумать над параметрами пользователя.
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "userRoles")
    private String role;

    @OneToOne (mappedBy = "user")
    private UserSession userSession;

    public User() {
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
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
        return (7 * Objects.hashCode(getLogin())) +
                (11 * Objects.hashCode(getPassword()));
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final User other = (User) obj;
        return Objects.equals(getLogin(), other.getLogin())
                && Objects.equals(getPassword(), other.getPassword());
    }

    @Override
    public String toString() {
        return "[login:" + getLogin() + ", password: " + getPassword() + "]";
    }
}
