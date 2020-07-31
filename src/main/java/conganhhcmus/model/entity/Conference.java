package conganhhcmus.model.entity;


import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Indexed
@Table(name = "CONFERENCE")
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MAIN_IMAGE")
    private Long imageid;

    @Column(name = "CONFERENCE_NAME")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String conferencename;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "START_TIME")
    private Date starttime;

    @Column(name = "END_TIME")
    private Date endtime;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TIME")
    private Date time;

    @Column(name = "MEMBER_NUMBER")
    private Long membernumber;

    public Conference() {
    }

    // Getter & Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImageid() {
        return imageid;
    }

    public void setImageid(Long imageid) {
        this.imageid = imageid;
    }

    public String getConferencename() {
        return conferencename;
    }

    public void setConferencename(String conferencename) {
        this.conferencename = conferencename;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getMembernumber() {
        return membernumber;
    }

    public void setMembernumber(Long membernumber) {
        this.membernumber = membernumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        String startDate = formatter.format(starttime);
        String endDate = formatter.format(endtime);
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(starttime);

        return conferencename + " ( " + startDate + " - " + endDate + " ) " + date + " - " + membernumber + " members" + '\n'
                + description;
    }
}
