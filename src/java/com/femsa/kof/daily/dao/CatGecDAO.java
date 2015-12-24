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
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT gec FROM RvvdCatGec gec");
        List<RvvdCatGec> gecs = query.list();
        session.close();
        return gecs;
    }

    public List<RvvdCatGec> getGecs() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT gec FROM RvvdCatGec gec WHERE gec.status = 1");
        List<RvvdCatGec> gecs = query.list();
        session.close();
        return gecs;
    }

    public RvvdCatGec getGec(Integer id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatGec gec = (RvvdCatGec) session.get(RvvdCatGec.class, id);
        session.close();
        return gec;
    }

    public RvvdCatGec getGec(String gec) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT gec FROM RvvdCatGec gec WHERE gec.gecR = '" + gec.toUpperCase() + "' OR gec.gecEn = '" + gec.toUpperCase() + "'");
        List<RvvdCatGec> gecs = query.list();
        RvvdCatGec gecT = null;
        if (gecs.size() > 0) {
            gecT = gecs.get(0);
        }
        session.close();
        return gecT;
    }

    public boolean saveGec(RvvdCatGec gec) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
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
            session.close();
        }
        return flagOk;
    }
}
