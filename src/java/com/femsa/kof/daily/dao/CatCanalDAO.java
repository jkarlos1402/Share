package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatCanal;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CatCanalDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdCatCanal> getCanalesAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT c FROM RvvdCatCanal c");
        List<RvvdCatCanal> canales = query.list();
        session.close();
        return canales;
    }

    public List<RvvdCatCanal> getCanales() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT c FROM RvvdCatCanal c WHERE c.status = 1");
        List<RvvdCatCanal> canales = query.list();        
        session.close();
        return canales;
    }

    public RvvdCatCanal getCanal(Integer id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatCanal canal = (RvvdCatCanal) session.get(RvvdCatCanal.class, id);
        session.close();
        return canal;
    }

    public RvvdCatCanal getCanal(String canal) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT c FROM RvvdCatCanal c WHERE c.canalR = '" + canal.toUpperCase() + "' OR c.canalEn = '" + canal.toUpperCase() + "'");
        List<RvvdCatCanal> canales = query.list();
        RvvdCatCanal canalT = null;
        if (canales.size() > 0) {
            canalT = canales.get(0);
        }       
        session.close();
        return canalT;
    }

    public boolean saveCanal(RvvdCatCanal canal) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((canal.getIdCanal() == null ? getCanal(canal.getCanalR()) : null) == null) {
                session.saveOrUpdate(canal);
            } else {
                error = "Channel already exists";
                flagOk = false;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            error = e.getMessage();
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            flagOk = false;
        } finally {
            session.close();
        }
        return flagOk;
    }
}
