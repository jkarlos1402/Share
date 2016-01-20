package com.femsa.kof.util;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

public class HibernateUtil {

    private SessionFactory sessionFactory;
    private boolean connectionOk;
    private String error;
    
    public HibernateUtil(){
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            connectionOk = true;
        } catch (Throwable ex) {
            // Log the exception. 
            error = "Initial SessionFactory creation failed." + ex;
            connectionOk = false;
        }
    }    

    public boolean isConnectionOk() {
        return connectionOk;
    }

    public void setConnectionOk(boolean connectionOk) {
        this.connectionOk = connectionOk;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public void closeSessionFactory(){
        sessionFactory.close();
        sessionFactory = null;
    }
}
