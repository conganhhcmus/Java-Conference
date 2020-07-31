package conganhhcmus.model;

import conganhhcmus.model.entity.Conference;
import conganhhcmus.utility.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class M_Conference {

    public static List<Conference> getAllConference() {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            List<Conference> conferences = session.createQuery("from Conference p order by p.starttime desc", Conference.class).list();

            session.getTransaction().commit();
            session.close();
            return conferences;
        }
    }

    public static List<Conference> getAllConferenceJoined(Long userId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            List<Conference> conferences = session.createQuery("from Conference p where p.id in (select x.conferenceid from Participant x where x.userid = :userId and x.state = 1) order by p.starttime desc", Conference.class)
                    .setParameter("userId", userId)
                    .list();

            session.getTransaction().commit();
            session.close();
            return conferences;
        }
    }

    public static List<Conference> getAllConferenceJoinedOrderName(Long userId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();

            List<Conference> conferences = session.createQuery("from Conference p where p.id in (select x.conferenceid from Participant x where x.userid = :userId and x.state = 1) order by p.conferencename asc", Conference.class)
                    .setParameter("userId", userId)
                    .list();

            session.getTransaction().commit();
            session.close();
            return conferences;
        }
    }


    public static List<Conference> getAllConferenceOrderName() {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            List<Conference> conferences = session.createQuery("from Conference p order by p.conferencename asc ", Conference.class).list();

            session.getTransaction().commit();
            session.close();
            return conferences;
        }
    }

    public static Long numberOfConference() {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            Long numberOfConference = session.createQuery("SELECT COUNT(id) FROM Conference ", Long.class).uniqueResult();
            System.out.println("Number of conference in database: " + numberOfConference);

            session.getTransaction().commit();
            session.close();
            return numberOfConference;
        }
    }

    public static List<Conference> getAllConferenceFTSOrderByDate(String keyword) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        Transaction tx = fullTextSession.beginTransaction();
        try {
            fullTextSession.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        QueryBuilder qb = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(Conference.class).get();

        org.apache.lucene.search.Query query = qb
                .keyword().onFields("conferencename")
                .matching(keyword)
                .createQuery();

        List<Conference> results = fullTextSession.createFullTextQuery(query, Conference.class).list();

        tx.commit();
        session.close();
        return results;
    }

    public static Long addConference(Long imageid, String conferencename, String address, Date starttime, Date endtime, String description, Long membernumber) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();

            // Insert user
            Date currentDate = new Date();
            Conference conference = new Conference();
            conference.setTime(currentDate);
            conference.setAddress(address);
            conference.setConferencename(conferencename);
            conference.setDescription(description);
            conference.setStarttime(starttime);
            conference.setEndtime(endtime);
            conference.setImageid(imageid);
            conference.setMembernumber(membernumber);

            Long conferenceId = (Long) session.save(conference);
            System.out.println("Conference id = " + conferenceId);

            session.getTransaction().commit();
            session.close();
            return conferenceId;
        }
    }

    public static Conference getConferenceById(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession();) {
            // Begin a unit of work
            session.beginTransaction();

            Conference savedConference = session.find(Conference.class, id);

            session.getTransaction().commit();
            session.close();
            return savedConference;
        }
    }

    public static void updateConference(Long id, Conference new_conference) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // Begin a unit of work
            session.beginTransaction();

            Conference conference = M_Conference.getConferenceById(id);
            conference.setEndtime(new_conference.getEndtime());
            conference.setStarttime(new_conference.getStarttime());
            conference.setConferencename(new_conference.getConferencename());
            conference.setMembernumber(new_conference.getMembernumber());
            conference.setAddress(new_conference.getAddress());
            conference.setImageid(new_conference.getImageid());
            conference.setDescription(new_conference.getDescription());
            conference.setTime(new Date());

            session.update(conference);

            session.getTransaction().commit();
            session.close();
        }
    }
}