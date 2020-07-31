package conganhhcmus.model.entity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "AVATAR")
    private Long avatar;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FULL_NAME")
    private String fullname;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PERMISSION")
    private int permission;

    @Column(name = "TIME")
    private Date time;


    public User() {
    }

    // getter & setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public Date getJoin_date() {
        return time;
    }

    public void setJoin_date(Date join_date) {
        this.time = join_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAvatar() {
        return avatar;
    }

    public void setAvatar(Long avatar) {
        this.avatar = avatar;
    }

    public String toString() {
        String result = String.format("- Username: " + username + "\n" +
                "- Password: " + password);
        return result;
    }
}
