package com.bankhead.data.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.bankhead.data.DataModel;

/*
 * Contact - store email addresses and other user data to be add user to the emaillist
 * 
 */
@Entity
@Table(name="contact")
public class Contact extends DataModel {

	@Id
    @GeneratedValue
    private long id;

    @NaturalId
	private String email = null;

    private Integer zipCode;
    
    @Basic
    private Date createdOn; // session create time
	
    
    /*
     * Getters and Setters
     */
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
