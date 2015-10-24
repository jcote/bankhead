package com.bankhead;

import java.util.logging.Logger;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mysql.jdbc.StringUtils;

/**
 * Created By: jordancote
 * Created On: 12/20/13
 */
@Singleton
public class Constants {
	private final Logger logger;
	
    public final int SESSION_LIFETIME = 10*60; // seconds
    public final String HEADER_USERNAME = "X-bankhead-Username";
    public final String HEADER_HASHPASS = "X-bankhead-Hashpass";
    public final String WELCOME_TEMPLATE = "Updates Welcome";
    public final String SAILTHRU_KEY;
    public final String SAILTHRU_SECRET;
    
    @Inject
    public Constants(Logger logger) {
    	this.logger = logger;
    	Configuration config = null;
    	try {
			config = new PropertiesConfiguration("bankhead.properties");
		} catch (ConfigurationException e) {
			logger.severe("Could not load bankhead.properties:\n" + 
					ExceptionUtils.getMessage(e) + "\n" + 
					ExceptionUtils.getStackTrace(e));
			System.exit(1);
		}
    	this.SAILTHRU_KEY = config.getString("sailthru.key");
    	this.SAILTHRU_SECRET = config.getString("sailthru.secret");
    	if (StringUtils.isEmptyOrWhitespaceOnly(this.SAILTHRU_KEY) ||
    	    StringUtils.isEmptyOrWhitespaceOnly(this.SAILTHRU_SECRET)) {
    		logger.severe("Could not load Sailthru key/secret from bankhead.properties");
			System.exit(1);
    	}
    }
}
