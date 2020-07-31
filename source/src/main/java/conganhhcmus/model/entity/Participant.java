package conganhhcmus.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PARTICIPANT")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CONFERENCE_ID")
    private Long conferenceid;

    @Column(name = "USER_ID")
    private Long userid;

    @Column(name = "STATE")
    private int state;

    @Column(name = "TIME")
    private Date time;

    public Participant() {
    }

    // Getter & Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConferenceid() {
        return conferenceid;
    }

    public void setConferenceid(Long conferenceid) {
        this.conferenceid = conferenceid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
