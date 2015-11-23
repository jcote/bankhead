package com.bankhead.data;

import java.util.List;

import org.hibernate.Session;

import com.bankhead.data.model.Account;
import com.bankhead.data.model.Bill;
import com.bankhead.data.model.BillVersion;
import com.bankhead.data.model.Contact;
import com.bankhead.data.model.cognition.Observation;

/**
 * Created By: jordancote
 * Created On: 1/3/14
 */
public interface DataStore {

    public void save(DataModel model);

    public void save(Bill bill, BillVersion billVersion);

    public List<Bill> loadCurrentBills();

    public Session createSession();

    public void closeSession(Session session);

    public void flush();

    public Account loadAccount(Session session, String username);
    
    public Contact loadContact(Session session, String email);

    public int invalidateWebSessions(Session session, long accountId);

	public List<Observation> loadObservations(String noun);

}
