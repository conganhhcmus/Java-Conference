package conganhhcmus.utility;

import conganhhcmus.model.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure();
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Conference.class);
                configuration.addAnnotatedClass(Image.class);
                configuration.addAnnotatedClass(Participant.class);
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}