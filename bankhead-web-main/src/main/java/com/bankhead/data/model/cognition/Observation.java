/**
 * 
 */
package com.bankhead.data.model.cognition;

import java.util.List;
import java.util.Map;

import javax.persistence.*;

/**
 * @author jordancote
 *
 */
@Entity
@Table(name="observation")
public class Observation extends Cognition {
    @Id
    @GeneratedValue
    private long id;
    
    @Basic
	private String text;
    
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	
}
