package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatEmpaque;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CatEmpaqueDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdCatEmpaque> getEmpaquesAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT e FROM RvvdCatEmpaque e");
        List<RvvdCatEmpaque> empaques = query.list();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return empaques;
    }

    public List<RvvdCatEmpaque> getEmpaques() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT e FROM RvvdCatEmpaque e WHERE e.status = 1");
        List<RvvdCatEmpaque> empaques = query.list();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return empaques;
    }

    public RvvdCatEmpaque getEmpaque(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatEmpaque empaque = (RvvdCatEmpaque) session.get(RvvdCatEmpaque.class, id);
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return empaque;
    }

    public RvvdCatEmpaque getEmpaque(String empaque) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT e FROM RvvdCatEmpaque e WHERE e.empaqueR = '" + empaque.toUpperCase() + "' OR e.empaqueEn = '" + empaque.toUpperCase() + "'");
        List<RvvdCatEmpaque> empaques = query.list();
        RvvdCatEmpaque empaqueT = null;
        if (empaques.size() > 0) {
            empaqueT = empaques.get(0);
        }
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return empaqueT;
    }

    public boolean saveEmpaque(RvvdCatEmpaque empaque) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((empaque.getIdEmpaque() == null ? getEmpaque(empaque.getEmpaqueR()) : null) == null) {
                session.saveOrUpdate(empaque);
            } else {
                error = "Packing already exists";
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
