/**
 * 
 */
package com.bankhead.data.model.cognition.element;

import java.util.List;

import com.bankhead.data.DataModel;

import edu.stanford.nlp.pipeline.Annotation;


/**
 * @author jordancote
 *
 */
public class Element extends DataModel {
	
	private Annotation annotation;
	private String text;
	private List<String> nouns;

	public Element(String text) {
		this.annotation = new Annotation(text);
	}
	
	public Annotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}
	
}
