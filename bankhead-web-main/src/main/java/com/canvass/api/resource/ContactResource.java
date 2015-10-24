package com.bankhead.api.resource;

import com.bankhead.Constants;
import com.bankhead.api.json.ContactJson;
import com.bankhead.data.DataStore;
import com.bankhead.data.model.Contact;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sailthru.client.SailthruClient;
import com.sailthru.client.params.Send;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.Session;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;


@Singleton
@Path("contact/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    private final DataStore dataStore;
    private final Logger logger;
    private final Constants constants;

    @Inject
    public ContactResource(DataStore dataStore, Constants constants, Logger logger) {
        this.dataStore = dataStore;
        this.logger = logger;
        this.constants = constants;
    }

    @POST
    @Path("contact")
    public Response postContact(ContactJson contactJson)
    {
    	// null validation
    	if (contactJson.getEmail() == null) {
    		logger.info("Null email submitted");
    		return Response
    				.status(Status.BAD_REQUEST)
    				.entity("Field is required: email")
    				.build();
    	}
    	if (contactJson.getZipCode() == null) {
    		logger.info("Null zip code submitted");
    		return Response
    				.status(Status.BAD_REQUEST)
    				.entity("Field is required: zipCode")
    				.build();
    	}
    	
    	// localize vars 
    	String email = contactJson.getEmail();
    	String zipCodeString = contactJson.getZipCode();
    	
    	// data validation
    	if (!EmailValidator.getInstance().isValid(email)) {
    		logger.info("Bad email submitted: " + email);
    		return Response
    				.status(Status.BAD_REQUEST)
    				.entity("Malformed field: email")
    				.build();
    	}
    	if (!NumberUtils.isDigits(zipCodeString) || zipCodeString.length() != 5) {
    		logger.info("Bad zip submitted: " + zipCodeString);
    		return Response
    				.status(Status.BAD_REQUEST)
    				.entity("Malformed field: zipCode")
    				.build();
    	}
    	
    	Integer zipCode = Integer.valueOf(zipCodeString);
    	
    	// create data model
		Contact contact = new Contact();
		contact.setEmail(email);
    	contact.setZipCode(zipCode);
    	contact.setCreatedOn(new Date());
    	logger.info("posting contact: " + contact.getEmail());
    	Session session = dataStore.createSession();
    	Contact existingContact = dataStore.loadContact(session, contactJson.getEmail());
    	dataStore.closeSession(session);
    	if (existingContact != null){
	    	logger.info("Contact already exists: " + email);
	    	return Response.ok()
    				.entity("Already signed up")
    				.build();
    	}
    	dataStore.save(contact);

    	// send welcome email
    	SailthruClient sailthruClient = new SailthruClient(
    			constants.SAILTHRU_KEY, 
    			constants.SAILTHRU_SECRET);
    	Send send = new Send();
    	send.setEmail(email);
    	send.setTemplate(constants.WELCOME_TEMPLATE);
    	try {
			sailthruClient.send(send);
		} catch (IOException e) {
			logger.warning("Error sending to " + email + ":\n" 
					+ ExceptionUtils.getMessage(e));
		}
    	
    	return Response.ok().build();
    }


}
