package com.bankhead.filter;

import com.bankhead.data.DataStore;
import com.bankhead.data.model.Account;
import com.bankhead.data.model.WebSession;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created By: jordancote
 * Created On: 12/9/13
 */
@Singleton
public class SecurityContextFilter implements Filter {
    protected ServletContext servletContext;
    private final Logger logger;
    private final DataStore dataStore;

    @Inject
    public SecurityContextFilter(Logger logger, DataStore dataStore) {
        this.logger = logger;
        this.dataStore = dataStore;
    }

    public void init(FilterConfig filterConfig) {
        servletContext = filterConfig.getServletContext();
    }

    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        if (!isAuth(req, resp)) {
            request.setAttribute("principal", null);
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return; //break filter chain, requested JSP/servlet will not be executed
        }

        //propagate to next element in the filter chain, ultimately JSP/ servlet gets executed
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    /**
     * logic to accept or reject access to the page, check if "logged in"
     * @return true when authentication is deemed valid
     */
    protected boolean isAuth(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession();

        // get authenticated account from session
        Object accountObject = httpSession.getAttribute("principal");
        // standard logic for "not logged in"
        if (accountObject == null) {
            servletContext.log("Not Authenticated");
            return false;
        }
        // should never happen
        if (!(accountObject instanceof Account)) {
            servletContext.log("Principal attribute is not an Account");
            return false;
        }
        // got valid account
        Account account = (Account) accountObject;

        // update websession
        WebSession webSession = account.getLastWebSession();
        if (webSession == null) {
            servletContext.log("Could not find websession in db");
            return false;
        }
        webSession.setLastAccessedOn(new Date());

        dataStore.save(webSession);

        // is "logged in"
        return true;
    }
}
