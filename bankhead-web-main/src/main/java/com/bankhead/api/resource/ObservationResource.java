/**
 * 
 */
package com.bankhead.api.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.Session;

import com.bankhead.api.json.NounTypeJson;
import com.bankhead.api.json.ObservationJson;
import com.bankhead.api.json.ObservationNounJson;
import com.bankhead.data.DataStore;
import com.bankhead.data.model.cognition.Observation;
import com.bankhead.data.model.cognition.element.Element;
import com.bankhead.data.model.cognition.element.ObservationNoun;
import com.bankhead.language.Classifier;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.stanford.nlp.util.CollectionUtils;

/**
 * @author jordancote
 *
 */
@Singleton
@Path("cognition/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ObservationResource {
    private final DataStore dataStore;
    private final Logger logger;
    private final Classifier classifier;
    
    @Inject
    public ObservationResource(DataStore dataStore, Logger logger, Classifier classifier) {
        this.dataStore = dataStore;
        this.logger = logger;
        this.classifier = classifier;
    }

    @POST
    @Path("observation")
    public Response postObservation(ObservationJson observationJson) {
    	// observation table
    	Observation observation = new Observation();
    	observation.setText(observationJson.getText());
    	dataStore.save(observation);
    	
    	// observation nouns table
    	Session session = dataStore.createSession();
    	Map<String,List<String>> nounsByTypeMap = classifier.classify(observationJson.getText());
    	for (String k : nounsByTypeMap.keySet()) {
    		for (String v : nounsByTypeMap.get(k)) {
        		ObservationNoun observationNoun = new ObservationNoun();
        		observationNoun.setObservation(observation);
	    		observationNoun.setType(k);
	    		observationNoun.setText(v);
	    		session.saveOrUpdate(observationNoun);
    		}
    	}
    	dataStore.closeSession(session);

    	return Response.ok().build();
    }

    @GET
    @Path("observation/by/noun/{noun}")
    public Map<String, List<ObservationJson>> getObservations(@PathParam("noun") String noun) {
    	Pattern p = Pattern.compile("[A-Za-z0-9-_ ]+");
    	Matcher m = p.matcher(noun);
    	if (!m.matches()) {
    		throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
    	}
    	
    	// to build
    	Map<String, List<ObservationJson>> out = new HashMap<>();
    	
    	// (type -> observation) from db
        Map<String, List<Observation>> observationsByNounType = dataStore.loadObservations(noun);
        
        for (String type : observationsByNounType.keySet()) {
        	for (Observation observation : observationsByNounType.get(type)) {
        		ObservationJson observationJson = new ObservationJson(observation.getText());
        		if (out.containsKey(type)) {
        			out.get(type).add(observationJson);
        		} else {
        			List<ObservationJson> observationJsons = new ArrayList<>();
        			observationJsons.add(observationJson);
        			out.put(type, observationJsons);
        		}
        		logger.info("added type " + type + " observation: " + observation.getText());
        	}
        }

        return out;
    }
}
