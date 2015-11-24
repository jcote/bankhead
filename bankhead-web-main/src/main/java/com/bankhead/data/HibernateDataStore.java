package com.bankhead.data;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.bankhead.data.model.Account;
import com.bankhead.data.model.Contact;
import com.bankhead.data.model.WebSession;
import com.bankhead.data.model.cognition.Observation;
import com.bankhead.data.model.cognition.element.ObservationNoun;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class HibernateDataStore implements DataStore {
	private SessionFactory sessionFactory;
	private ServiceRegistry serviceRegistry;
    private final Logger logger;

    @Inject
    public HibernateDataStore(Logger logger) {
        this.logger = logger;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        Configuration configuration = new Configuration();
        configuration.setNamingStrategy(new ImprovedNamingStrategy());
        configuration.addAnnotatedClass(WebSession.class);
        configuration.addAnnotatedClass(Account.class);
        configuration.addAnnotatedClass(Contact.class);
        configuration.addAnnotatedClass(Observation.class);
        configuration.addAnnotatedClass(ObservationNoun.class);
	    configuration.configure();
	    serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	public void save(DataModel model) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(model);
		session.getTransaction().commit();
		session.close();
	}

    
    public List<Observation> loadObservations(String noun) {
    	Session session = sessionFactory.openSession();
        Query query = session.createQuery("select distinct observation from Observation observation, ObservationNoun observation_noun"
        		+ " where observation_noun.text = :NOUNTEXT and observation.id = observation_noun.observation"
        		).setString("NOUNTEXT", noun);
        List<Observation> observations = query.list();
        session.close();
        return observations;
    }

    public Session createSession() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    public void closeSession(Session session) {
        session.getTransaction().commit();
        session.close();
    }


    public void flush() {
        Session session = sessionFactory.openSession();
        session.flush();
        session.close();
    }


    public Account loadAccount(Session session, String username) {
        Account account = (Account) session
                .createQuery("SELECT a FROM Account AS a WHERE username = :UNAME")
                .setString("UNAME", username)
                .uniqueResult();
        return account;
    }
    
    public Contact loadContact(Session session, String email) {
    	Contact contact = (Contact) session
                .createQuery("SELECT c FROM Contact AS c WHERE email = :EMAIL")
                .setString("EMAIL", email)
                .uniqueResult();
        return contact;
    }

    public int invalidateWebSessions(Session session, long accountId) {
        String sql = "UPDATE Session SET active = :Active WHERE account_id = :AccountId";
        Query query = session.createSQLQuery(sql)
                .addEntity(WebSession.class)
                .setParameter("Active", false)
                .setParameter("AccountId", accountId);
        int closed = query.executeUpdate();
        return closed;
    }



}
