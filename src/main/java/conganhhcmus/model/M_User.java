package conganhhcmus.model;

import conganhhcmus.model.entity.User;
import conganhhcmus.utility.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

public class M_User {
    public static Long addUser(String username, String password, String fullname, String email, int permission) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();

            // Insert user
            Date currentDate = new Date();
            User user = new User();
            user.setFullname(fullname);
            user.setUsername(username);
            user.setPassword(password);
            user.setPermission(permission);
            user.setJoin_date(currentDate);
            user.setEmail(email);
            Long userId = (Long) session.save(user);
            System.out.println("User id = " + userId);

            session.getTransaction().commit();
            session.close();
            return userId;
        }
    }

    public static User getUserById(Long userId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            User savedUser = session.find(User.class, userId);
            System.out.println("savedUser: " + savedUser);

            session.getTransaction().commit();
            session.close();
            return savedUser;
        }
    }

    public static List<User> getAllUser() {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            List<User> users = session.createQuery("from User u where u.permission < 1", User.class).list();
            users.forEach(System.out::println);

            session.getTransaction().commit();
            session.close();
            return users;
        }
    }

    public static Long numberOfUser() {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            Long numberOfUser = session.createQuery("SELECT COUNT(id) FROM User", Long.class).uniqueResult();
            System.out.println("Number of user in database: " + numberOfUser);

            session.getTransaction().commit();
            session.close();
            return numberOfUser;
        }
    }

    public static void updateUser(Long userId, User newUser) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();

            User user = M_User.getUserById(userId);
            user.setUsername(newUser.getUsername());
            user.setPassword(newUser.getPassword());
            user.setPermission(newUser.getPermission());
            user.setJoin_date(newUser.getJoin_date());
            user.setEmail(newUser.getEmail());
            user.setAvatar(newUser.getAvatar());
            user.setJoin_date(new Date());
            session.update(user);

            session.getTransaction().commit();
            session.close();
        }
    }

    public static void deleteUser(Long userId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();

            User user = M_User.getUserById(userId);
            session.delete(user);

            session.getTransaction().commit();
            session.close();
        }
    }

    public static boolean isUsernameAlreadyInUse(String username) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();

            User user;

            try {
                user = session.createQuery("from User u where u.username = :username", User.class)
                        .setParameter("username", username)
                        .getSingleResult();
            } catch (NoResultException e) {
                user = null;
            }

            session.getTransaction().commit();
            session.close();
            if (user == null) return false;
            else return true;
        }
    }

    public static User getUserByUsername(String username) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();

            User user;

            try {
                user = session.createQuery("from User u where u.username = :username", User.class)
                        .setParameter("username", username)
                        .getSingleResult();
            } catch (NoResultException e) {
                user = null;
            }

            session.getTransaction().commit();
            session.close();
            return user;
        }
    }


    public static Long numberOfConference(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            Long number = session.createQuery("SELECT COUNT(p.conferenceid) FROM Participant p where p.userid = :userid and p.state = 1 ", Long.class)
                    .setParameter("userid", id)
                    .uniqueResult();

            session.getTransaction().commit();
            session.close();
            return number;
        }
    }
}
