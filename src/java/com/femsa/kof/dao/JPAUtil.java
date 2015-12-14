package com.femsa.kof.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private EntityManagerFactory emfactory;

    private EntityManager entitymanager;
    
    private String error;

    public JPAUtil() {
        try {
            emfactory = Persistence.createEntityManagerFactory("SharePortalPU");
            entitymanager = emfactory.createEntityManager();
        } catch (Exception e) {
            error = "No se puede conectar a la base de datos: " + e.getMessage();            
        }
    }

    public EntityManager getEntityManager() {
        return entitymanager;
    }
}
