package com.bankhead.data;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.bankhead.data.model.Account;
import com.bankhead.data.model.Contact;
import com.bankhead.data.model.cognition.Observation;

/**
 * Created By: jordancote
 * Created On: 1/3/14
 */
public interface DataStore {

    public void save(DataModel model);

    public Session createSession();

    public void closeSession(Session session);

    public void flush();

    public Account loadAccount(Session session, String username);
    
    public Contact loadContact(Session session, String email);

    public int invalidateWebSessions(Session session, long accountId);

	public Map<String, List<Observation>> loadObservations(String noun);

}
