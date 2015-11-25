/**
 * 
 */
package com.bankhead.api.json;

import java.util.List;

/**
 * @author jordancote
 * created 11/20/2015
 *
 */
public class CognitionJson {
	private String text;
	private List<CognitionJson> precedents;
	private List<CognitionJson> procedents;
	private List<String> sentences;

	public CognitionJson(String text) {
		super();
		this.text = text;
	}

	public CognitionJson() {
		super();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<CognitionJson> getPrecedents() {
		return precedents;
	}

	public void setPrecedents(List<CognitionJson> precedents) {
		this.precedents = precedents;
	}

	public List<CognitionJson> getProcedents() {
		return procedents;
	}

	public void setProcedents(List<CognitionJson> procedents) {
		this.procedents = procedents;
	}

	public List<String> getSentences() {
		return sentences;
	}

	public void setSentences(List<String> sentences) {
		this.sentences = sentences;
	}
	
	
}
