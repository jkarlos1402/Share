package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatGec;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CatGecDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdCatGec> getGecAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT gec FROM RvvdCatGec gec");
        List<RvvdCatGec> gecs = query.list();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return gecs;
    }

    public List<RvvdCatGec> getGecs() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT gec FROM RvvdCatGec gec WHERE gec.status = 1");
        List<RvvdCatGec> gecs = query.list();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return gecs;
    }

    public RvvdCatGec getGec(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatGec gec = (RvvdCatGec) session.get(RvvdCatGec.class, id);
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return gec;
    }

    public RvvdCatGec getGec(String gec) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT gec FROM RvvdCatGec gec WHERE gec.gecR = '" + gec.toUpperCase() + "' OR gec.gecEn = '" + gec.toUpperCase() + "'");
        List<RvvdCatGec> gecs = query.list();
        RvvdCatGec gecT = null;
        if (gecs.size() > 0) {
            gecT = gecs.get(0);
        }
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return gecT;
    }

    public boolean saveGec(RvvdCatGec gec) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((gec.getIdGec() == null ? getGec(gec.getGecR()) : null) == null) {
                session.saveOrUpdate(gec);
            } else {
                error = "Gec already exists";
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
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return flagOk;
    }
}
