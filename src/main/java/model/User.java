package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {
    // TODO: 12.04.2018 Подумать над параметрами пользователя.
    @Id
    @Column(name ="id")
    public String id;

    @Column(name ="login")
    public String login;

    @Column(name = "password")
    public String password;

    public User() {
    }
    

    public String getId() {
        return id;
    }

    public void setId(final String id) {
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
