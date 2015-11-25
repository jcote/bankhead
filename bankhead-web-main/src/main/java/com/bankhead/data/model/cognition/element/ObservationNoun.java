package com.bankhead.data.model.cognition.element;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bankhead.data.DataModel;
import com.bankhead.data.model.Account;
import com.bankhead.data.model.cognition.Observation;

@Entity
@Table(name="observation_noun")
public class ObservationNoun extends DataModel{
    @Id
    @GeneratedValue
    private long id;
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "observation_id", nullable = false)
    private Observation observation;
	
	@Basic
	private String text;
	
	@Basic
	private String type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Observation getObservation() {
		return observation;
	}

	public void setObservation(Observation observation) {
		this.observation = observation;
	}
	
	
}
