/**
 * 
 */
package com.bankhead.api.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bankhead.api.json.BillJson;
import com.bankhead.api.json.ObservationJson;
import com.bankhead.data.DataStore;
import com.bankhead.data.model.Bill;
import com.bankhead.data.model.BillVersion;
import com.bankhead.data.model.cognition.Observation;
import com.bankhead.data.model.cognition.element.Element;
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
    	Observation observation = new Observation();
    	observation.setText(observationJson.getText());
    	Map<String,String> nounsByTypeMap = classifier.classify(observationJson.getText());
    	observation.setNouns(Lists.newArrayList(nounsByTypeMap.values()));
        dataStore.save(observation);

    	return Response.ok().build();
    }

    @GET
    @Path("observation")
    public List<ObservationJson> getObservations(String noun) {
        List<Observation> observations = dataStore.loadObservations(noun);
        List<ObservationJson> observationJsons = new ArrayList<>();
        for (Observation observation : observations) {
            ObservationJson observationJson = new ObservationJson();
            observationJson.setText(observation.getText());
            observationJsons.add(observationJson);
        }

        return observationJsons;
    }
}
