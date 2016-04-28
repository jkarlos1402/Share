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
     * Cuando un usuario inicia sesion se checan los catalogos para indicar si
     * tiene acciones pendites a realizar, como la reclasificación de catálogos
     *
     * @param event
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if ("session_user".equalsIgnoreCase(event.getName())) {
            CheckCatalogs.checkAllCatalogs();
        }
    }

    /**
     *
     * @param event
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        //comentario
    }

    /**
     *
     * @param event
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        //comentario
    }
}
