/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.femsa.kof.listener;

import com.femsa.kof.daily.util.CheckCatalogs;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.CatalogLoader;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Web application lifecycle listener.
 *
 * @author TMXIDSJPINAM
 */
@WebListener()
public class AttrSessionListenerKOF implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (event.getName().equalsIgnoreCase("session_user")) {
            CatalogLoader.loadCatalogs("share");
            CatalogLoader.loadCatalogs("daily"); 
            List<String> notifications = new ArrayList<String>();
            long numNotReclassCateg = CheckCatalogs.checkCategoryDaily((ShareUsuario)event.getValue());
            if(numNotReclassCateg > 0){
                notifications.add("You have "+numNotReclassCateg+" categories without reclassifying.");
            }
            event.getSession().setAttribute("notifications_user", notifications);
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {

    }
}
