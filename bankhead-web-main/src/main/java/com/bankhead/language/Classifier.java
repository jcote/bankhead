/**
 * 
 */
package com.bankhead.language;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.CollectionUtils;

/**
 * @author jordancote
 *
 */
@Singleton
public class Classifier {
	private AbstractSequenceClassifier<CoreLabel> classifier;
	private final Logger logger;
	
	@Inject
	public Classifier(Logger logger) {
		this.logger = logger;
	    String serializedClassifier = "classifiers/english.muc.7class.distsim.crf.ser.gz";

	    try {
			this.classifier = CRFClassifier.getClassifier(serializedClassifier);
			
		} catch (ClassCastException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
		}

	}
	
	public Map<String,String> classify(String text) {
		Map<String,String> nounsByTypeMap = Collections.synchronizedMap(new HashMap<String,String>());
		List<List<CoreLabel>> sentences = classifier.classify(text);
		  for (List<CoreLabel> sentence : sentences) {
		    for (CoreLabel word : sentence) {
		    	String type = word.get(CoreAnnotations.AnswerAnnotation.class);
		    	logger.info(word.word() + " - " + type);
		    	nounsByTypeMap.put(type, word.word());
		    }
		    System.out.println();
		  }
		  return nounsByTypeMap;
	}
}
