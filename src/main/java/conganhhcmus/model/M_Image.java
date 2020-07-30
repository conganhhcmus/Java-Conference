package conganhhcmus.model;

import conganhhcmus.model.entity.Conference;
import conganhhcmus.model.entity.Image;
import conganhhcmus.model.entity.User;
import conganhhcmus.utility.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

public class M_Image {

    public static Image getImageById(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            Image savedImg = session.find(Image.class, id);
            System.out.println("savedImg: " + savedImg);

            session.getTransaction().commit();
            session.close();
            return savedImg;
        }
    }

    public static Long addImg(String originalname, String hashname) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();

            // Insert user
            Date currentDate = new Date();
            Image img = new Image();
            img.setOriginalname(originalname);
            img.setTime(new Date());
            img.setHashname(hashname);
            Long id = (Long) session.save(img);
            System.out.println("Img id = " + id);

            session.getTransaction().commit();
            session.close();
            return id;
        }
    }

    public static Long countImg() {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            Long numberOfImg = session.createQuery("SELECT COUNT(id) FROM Image", Long.class).uniqueResult();
            System.out.println("Number of img in database: " + numberOfImg);

            session.getTransaction().commit();
            session.close();
            return numberOfImg;
        }
    }
}