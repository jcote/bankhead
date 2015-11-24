package com.bankhead.data.model;

import com.bankhead.data.DataModel;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="account")
public class Account extends DataModel implements java.security.Principal {

    public enum Role {
        Viewer, Voter, Admin, SuperAdmin
    };
    
	@Id
	@GeneratedValue
	private long id;

	@NaturalId
	private String username;

    @OneToOne
    @JoinColumn(name = "last_web_session_id")
    private WebSession lastWebSession;

    @Temporal(TemporalType.TIME)
	private Date updatedOn;
	
	@Basic
	private String email;
	
	@Temporal(TemporalType.TIME)
	private Date createdOn;
		
	@Basic
	private String hashpass;
	
	@Basic
    private Role role;
    
	public Account() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHashpass() {
		return hashpass;
	}

	public void setHashpass(String hashpass) {
		this.hashpass = hashpass;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	// Principal methods

	public String getName() {
		return username;
	}

	public void setName(String name) {
		this.username = name;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public WebSession getLastWebSession() {
        return lastWebSession;
    }

    public void setLastWebSession(WebSession lastWebSession) {
        this.lastWebSession = lastWebSession;
    }
}
