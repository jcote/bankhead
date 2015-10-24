package com.bankhead.servlet;

import com.bankhead.Constants;
import com.bankhead.data.DataStore;
import com.bankhead.data.model.Account;
import com.bankhead.data.model.WebSession;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created By: jordancote
 * Created On: 12/10/13
 */
@Singleton
public class EnrollServlet extends HttpServlet {
	private final Logger logger;
    private final DataStore dataStore;
    private final Constants constants;

    @Inject
    public EnrollServlet(Logger logger, Constants constants, DataStore dataStore) {
        this.logger = logger;
        this.dataStore = dataStore;
        this.constants = constants;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = this.getServletContext();

        // get the username
        final String username = request.getHeader(constants.HEADER_USERNAME);
        servletContext.log("username:" + username);
        if (StringUtils.isEmpty(username)) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, constants.HEADER_USERNAME + " required");
            return;
        }

        // get the hashpass
        final String hashpass = request.getHeader(constants.HEADER_HASHPASS);
        servletContext.log("hashpass:" + hashpass);
        if (StringUtils.isEmpty(hashpass)) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, constants.HEADER_HASHPASS + " required");
            return;
        }
        if (hashpass.length() != 64) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, constants.HEADER_HASHPASS + " incorrect length");
            return;
        }

        Session dataSession = dataStore.createSession();
        Account account = new Account();
        account.setName(username);
        account.setHashpass(hashpass);
        account.setCreatedOn(new Date());
        //account.setEmail(email);
        dataStore.save(account);
        dataStore.closeSession(dataSession);
        
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
