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
	
    @ElementCollection
    @CollectionTable(name="observation_nouns", joinColumns=@JoinColumn(name="observation_id"))
    @Column(name="noun")
	private List<String> nouns;
	
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
	public List<String> getNouns() {
		return nouns;
	}
	public void setNouns(List<String> nouns) {
		this.nouns = nouns;
	}

	
}
