package conganhhcmus.model;
import javax.persistence.*;
import java.util.Date;


@Entity
public class User {
    @Id @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FULLNAME")
    private  String fullname;

    @Column(name = "PERMISSION")
    private int permission;

    @Column(name = "TIME")
    private  Date time;


    public User(){ }

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
    public String getFullName() {
        return fullname;
    }
    public void setFullName(String fullname) {
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
    public String toString(){
        String result = String.format("- Username: " + username + "\n" +
                "- Password: " + password);
        return result;
    }
}
