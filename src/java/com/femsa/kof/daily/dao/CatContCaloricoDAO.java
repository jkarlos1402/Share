package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatContenidoCalorico;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CatContCaloricoDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdCatContenidoCalorico> getContsCalAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT cc FROM RvvdCatContenidoCalorico cc");
        List<RvvdCatContenidoCalorico> contenidos = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return contenidos;
    }

    public List<RvvdCatContenidoCalorico> getContsCal() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT cc FROM RvvdCatContenidoCalorico cc WHERE cc.status = 1");
        List<RvvdCatContenidoCalorico> contenidos = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return contenidos;
    }

    public RvvdCatContenidoCalorico getContCal(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatContenidoCalorico contenido = (RvvdCatContenidoCalorico) session.get(RvvdCatContenidoCalorico.class, id);
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return contenido;
    }

    public RvvdCatContenidoCalorico getContCal(String contenido) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT cc FROM RvvdCatContenidoCalorico cc WHERE cc.contenidoCaloricoR = '" + contenido.toUpperCase() + "' OR cc.contenidoCaloricoEn = '" + contenido.toUpperCase() + "'");
        List<RvvdCatContenidoCalorico> contenidos = query.list();
        RvvdCatContenidoCalorico contenidoT = null;
        if (contenidos.size() > 0) {
            contenidoT = contenidos.get(0);
        }
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return contenidoT;
    }

    public boolean saveContCal(RvvdCatContenidoCalorico contenido) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((contenido.getIdContenidoCalorico() == null ? getContCal(contenido.getContenidoCaloricoR()) : null) == null) {
                session.saveOrUpdate(contenido);
            } else {
                error = "Calorie already exists";
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
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return flagOk;
    }
}
