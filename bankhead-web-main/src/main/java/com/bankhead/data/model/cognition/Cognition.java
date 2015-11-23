/**
 * 
 */
package com.bankhead.data.model.cognition;

import java.util.List;

import com.bankhead.data.DataModel;
import com.bankhead.data.model.cognition.element.Element;

/**
 * @author jordancote
 *
 */
public abstract class Cognition extends DataModel {
	Element element;

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}


}
