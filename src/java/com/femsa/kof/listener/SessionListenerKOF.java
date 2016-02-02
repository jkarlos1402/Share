package com.femsa.kof.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author TMXIDSJPINAM
 */
@WebListener()
public class SessionListenerKOF implements HttpSessionListener {

    /**
     *
     * @param se
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    /**
     *
     * @param se
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext application = se.getSession().getServletContext();
        if (se.getSession().getAttribute("session_user") != null) {                                                     
        } else {                                             
        }
    }
}
