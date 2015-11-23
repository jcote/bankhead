package com.bankhead.data.model;

import com.bankhead.data.DataModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="session")
public class WebSession extends DataModel implements Serializable {
	private static final long serialVersionUID = -3555341801885586617L;
	
	@Id
	@GeneratedValue
	private long id;   // id
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
    private Account account;      // user

	@Basic
    private Date createdOn;    // session create time
	
	@Basic
    private boolean active;     // session active?
	
	@Basic
    private boolean secure;     // session secure?

	@Basic
    private Date lastAccessedOn;  // session last use time

    @Basic
    private String sessionId;

    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) { this.account = account; }
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isSecure() {
		return secure;
	}
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createTime) {
		this.createdOn = createTime;
	}
	public Date getLastAccessedOn() {
		return lastAccessedOn;
	}
	public void setLastAccessedOn(Date lastAccessedTime) {
		this.lastAccessedOn = lastAccessedTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static WebSession createNew(Account account) {
		WebSession session = new WebSession();
		session.setAccount(account);
		session.setCreatedOn(Calendar.getInstance().getTime());
		session.setLastAccessedOn(Calendar.getInstance().getTime());
		session.setActive(true);
		session.setSecure(true);
		return session;
	}

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
