package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdInfoPh;
import com.femsa.kof.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RvvdInfoPhDAO {

    private List<String> errors = new ArrayList<String>();

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public boolean saveInfoPh(List<RvvdInfoPh> infoPh) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = null;
        List<Object> resultado = null;
        boolean flagOk = true;
        long cont = 0L;
        try {
            session.beginTransaction();
            for (RvvdInfoPh carga : infoPh) {
                session.save(carga);
                if (cont % 100 == 0) {
                    session.flush();
                    session.clear();
                }
                cont++;
            }            
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("Error saving records: " + e.getMessage());
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            flagOk = false;
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return flagOk;
    }
}
