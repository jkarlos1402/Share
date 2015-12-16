package com.femsa.kof.listener;

import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.CatalogLoader;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener()
public class SessionListenerKOF implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {  
       
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        
    }
}
