package com.femsa.kof.listener;

import com.femsa.kof.daily.util.CheckCatalogs;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 *
 * @author TMXIDSJPINAM
 */
@WebListener()
public class AttrSessionListenerKOF implements HttpSessionAttributeListener {

    /**
     *
     * @param event
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (event.getName().equalsIgnoreCase("session_user")) {           
            CheckCatalogs.checkAllCatalogs();            
        }        
    }

    /**
     *
     * @param event
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {        
    }

    /**
     *
     * @param event
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {

    }
}
