package conganhhcmus.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "IMAGE")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORIGINAL_NAME")
    private String originalname;

    @Column(name = "HASH_NAME")
    private String hashname;

    @Column(name = "TIME")
    private Date time;

    public Image() {
    }

// Getter & Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalname() {
        return originalname;
    }

    public void setOriginalname(String originalname) {
        this.originalname = originalname;
    }

    public String getHashname() {
        return hashname;
    }

    public void setHashname(String hashname) {
        this.hashname = hashname;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
