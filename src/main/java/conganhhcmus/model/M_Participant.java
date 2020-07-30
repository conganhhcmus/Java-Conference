package conganhhcmus.model;

import conganhhcmus.model.entity.Participant;
import conganhhcmus.model.entity.User;
import conganhhcmus.utility.HibernateUtils;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class M_Participant {

    public static Long numberJoinConference(Long conferenceId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            Long number = session.createQuery("SELECT COUNT(userid) FROM Participant p WHERE p.conferenceid = :conferenceId and p.state = 1", Long.class)
                    .setParameter("conferenceId", conferenceId)
                    .uniqueResult();
            System.out.println("Number of user in conference: " + number);
            session.getTransaction().commit();
            session.close();
            return number;
        }
    }

    public static Long addParticipant(Long userId, Long conferenceId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();

            // Insert user
            Date currentDate = new Date();
            Participant participant = new Participant();

            participant.setTime(currentDate);
            participant.setConferenceid(conferenceId);
            participant.setUserid(userId);
            participant.setState(0);
            Long id = (Long) session.save(participant);

            session.getTransaction().commit();
            session.close();
            return id;
        }
    }

    public static int checkUserInConference(Long userId, Long conferenceId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();
//            SELECT * FROM bugs WHERE ID = (SELECT MAX(ID) FROM bugs WHERE user = 'me')
            Participant participant = session.createQuery("FROM Participant p where p.id = (select max(x.id) from Participant x where x.userid = :userid and p.conferenceid = :conferenceid)", Participant.class)
                    .setParameter("userid", userId)
                    .setParameter("conferenceid", conferenceId)
                    .uniqueResult();

            session.getTransaction().commit();
            session.close();
            if (participant == null) return -2;
            return participant.getState();
        }
    }

    public static List<Participant> getAllRequest() {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            List<Participant> requests = session.createQuery("from Participant p where p.state = 0", Participant.class).list();

            session.getTransaction().commit();
            session.close();
            return requests;
        }
    }

    public static Participant getRequestById(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            Participant request = session.find(Participant.class, id);

            session.getTransaction().commit();
            session.close();
            return request;
        }
    }

    public static void updateParticipant(Long id, Participant newRequest) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();

            Participant request = M_Participant.getRequestById(id);
            request.setUserid(newRequest.getUserid());
            request.setConferenceid(newRequest.getConferenceid());
            request.setState(newRequest.getState());
            request.setTime(new Date());
            session.update(request);

            session.getTransaction().commit();
            session.close();
        }
    }

}
