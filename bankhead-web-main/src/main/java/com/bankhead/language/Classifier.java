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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.NERClassifierCombiner;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.ling.LabeledWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.DefaultPaths;
import edu.stanford.nlp.pipeline.NERCombinerAnnotator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CollectionUtils;
import edu.stanford.nlp.util.CoreMap;

/**
 * @author jordancote
 *
 */
@Singleton
public class Classifier {
	private AbstractSequenceClassifier<CoreLabel> classifier;
	private final Logger logger;
	private StanfordCoreNLP pipeline ;
      public static final String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";
	  public static final String NER_3CLASS = DefaultPaths.DEFAULT_NER_THREECLASS_MODEL;
	  public static final String NER_7CLASS = DefaultPaths.DEFAULT_NER_MUC_MODEL;
	  public static final String NER_MISCCLASS = DefaultPaths.DEFAULT_NER_CONLL_MODEL;

	@Inject
	public Classifier(Logger logger) {
		this.logger = logger;
		
//	    String serializedClassifier = "classifiers/english.muc.7class.distsim.crf.ser.gz";

	//		this.classifier = CRFClassifier.getClassifier(serializedClassifier);
		    // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
		    Properties props = new Properties();
		    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
		    this.pipeline = new StanfordCoreNLP(props);
			

	}
	
	public Map<String,List<String>> classify(String text) {
		Map<String,List<String>> nounsByTypeMap = Collections.synchronizedMap(new HashMap<String,List<String>>());
		
		Annotation document = new Annotation(text);
		
		pipeline.annotate(document);
		
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		
		  for (CoreMap sentence : sentences) {
		      for (CoreLabel token: sentence.get(TokensAnnotation.class)) {

		          // this is the text of the token
		          String word = token.get(TextAnnotation.class);
		          // this is the POS tag of the token
		          String pos = token.get(PartOfSpeechAnnotation.class);
		          // this is the NER label of the token
		          String ne = token.get(NamedEntityTagAnnotation.class);      
		          logger.info("word: " + word + " / " + pos + " / " + ne);
		        }
			  

//		        try {
//					NERCombinerAnnotator nerAnnotator = new NERCombinerAnnotator(false, NER_3CLASS, NER_7CLASS, NER_MISCCLASS);
//				} catch (ClassNotFoundException | IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		        Properties props = new Properties();
//		        props.setProperty("ner.applyNumericClassifiers", "false");
//		        props.setProperty("ner.useSUTime", "false");
//		        props.setProperty("ner.model", NER_3CLASS);
//		        NERClassifierCombiner ner = NERClassifierCombiner.createNERClassifierCombiner("ner", props);
		        //NERCombinerAnnotator threaded4Annotator = new NERCombinerAnnotator(ner, false, 4, -1);

//		      AbstractSequenceClassifier ner = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
//		      List<List<CoreLabel>> out = ner.classify(text);
//		      for (List<CoreLabel> out2 : out) {
//		    	  for (CoreLabel label : out2) {
//		    		  logger.info("classify: " + label.word() + " / " + label.get(AnswerAnnotation.class));
//		    	  }
//		      }

//		    for (CoreLabel word : sentence) {
//		    	String type = word.get(CoreAnnotations.AnswerAnnotation.class);
//		    	logger.info(word.word() + " - " + type);
//		    	nounsByTypeMap.put(type, word.word());
//		    }
//		    

		    Tree tree = sentence.get(TreeAnnotation.class);
		    walk(tree, nounsByTypeMap);

		    // This is the coreference link graph
		    // Each chain stores a set of mentions that link to each other,
		    // along with a method for getting the most representative mention
		    // Both sentence and token offsets start at 1!
		    Map<Integer, CorefChain> graph = 
		      document.get(CorefChainAnnotation.class);
		    
		  }
		  return nounsByTypeMap;
	}
	
	private void walk(Tree tree, Map<String, List<String>> nouns) {
  	  //Check for NNP
  	  if (StringUtils.equals("NP", tree.value())) {
          // working vars
          String nnpComboText = new String();
          String nnpComboTag = null;
          boolean first = true;
		  logger.info("Evaluating NP: " + tree.toString());
  		  for (Tree leaf : tree.getLeaves()) {
  			  logger.info("Evaluating leaf: " + leaf.toString() + " " + leaf.nodeString() + leaf.pennString());
              List<Label> words = leaf.yield();
              
              logger.info("Evaluating words: " + words.toString());

              for (Label word: words) {
            	CoreLabel core = (CoreLabel) word;
      			String text = core.get(TextAnnotation.class);
      			String nerTag = core.get(NamedEntityTagAnnotation.class);
      			logger.info("Evaluating: " + text + " / " + nerTag);
      			if (!StringUtils.equals("O", nerTag)) {
      				// handle tags and NER
      				if (!first && !StringUtils.equals(nerTag, nnpComboTag)) {
      					logger.warning("NNP tags do not match within Noun Phrase: " + nnpComboTag + " vs " + nerTag);
      				} else {
          				nnpComboTag = nerTag;
      				}
      				
      				// handle text concat
      				if (!first) nnpComboText += " ";
      				nnpComboText += text;
      				
      				logger.info("Adding NNP: " + text + " with NER tag: " + nerTag);
      				
      				first = false;
      			}
            }
  		  }
          if (!StringUtils.isEmpty(nnpComboTag)) {
	            // add NNP phrase for category
	            logger.info("Adding NNP Phrase: " + nnpComboText + " with NER tag: " + nnpComboTag);
	            if (nouns.containsKey(nnpComboTag)) {
	            	nouns.get(nnpComboTag).add(nnpComboText);
	            } else {
	            	List<String> texts = new ArrayList<String>();
	            	texts.add(nnpComboText);
	            	nouns.put(nnpComboTag, texts);
	            }
          }
  	  } else {
  		  for (Tree subTree : tree.children()) {
  			  walk(subTree, nouns);
  		  }
  	  }
	}
}
