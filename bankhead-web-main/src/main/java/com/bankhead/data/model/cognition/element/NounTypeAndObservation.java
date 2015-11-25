package com.bankhead.data.model.cognition.element;

import com.bankhead.data.model.cognition.Observation;

public class NounTypeAndObservation {
	private String type;
	private Observation observation;
	public NounTypeAndObservation(String type, Observation observation) {
		this.type = type;
		this.observation = observation;
	}

	public NounTypeAndObservation() {}

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