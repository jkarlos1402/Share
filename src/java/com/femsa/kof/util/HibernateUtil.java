package com.femsa.kof.util;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Clase que permite la conexión con la base de datos
 *
 */
public class HibernateUtil {

    private SessionFactory sessionFactory;
    private boolean connectionOk;
    private String error;

    /**
     *
     */
    public HibernateUtil() {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            connectionOk = true;
        } catch (Exception ex) {
            // Log the exception. 
            error = "Initial SessionFactory creation failed." + ex;
            connectionOk = false;
        }
    }

    /**
     *
     * @return
     */
    public boolean isConnectionOk() {
        return connectionOk;
    }

    /**
     *
     * @param connectionOk
     */
    public void setConnectionOk(boolean connectionOk) {
        this.connectionOk = connectionOk;
    }

    /**
     *
     * @return
     */
    public String getError() {
        return error;
    }

    /**
     *
     * @param error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     *
     * @return
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Cierra el pool de conexiones
     */
    public void closeSessionFactory() {
        sessionFactory.close();
        sessionFactory = null;
    }
}
