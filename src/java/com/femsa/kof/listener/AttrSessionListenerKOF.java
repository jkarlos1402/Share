package com.femsa.kof.listener;

import com.femsa.kof.daily.util.CheckCatalogs;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener()
public class AttrSessionListenerKOF implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (event.getName().equalsIgnoreCase("session_user")) {           
            CheckCatalogs.checkAllCatalogs();            
        }        
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {        
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {

    }
}
