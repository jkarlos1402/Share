package com.femsa.kof.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private EntityManagerFactory emfactory;

    private EntityManager entitymanager;

    public JPAUtil(){       
        emfactory = Persistence.createEntityManagerFactory("SharePortalPU");
        entitymanager = emfactory.createEntityManager();        
    }

    public EntityManager getEntityManager() {
        return entitymanager;
    }
}
