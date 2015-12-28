package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatMarca;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CatMarcaDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdCatMarca> getMarcasAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT m FROM RvvdCatMarca m");
        List<RvvdCatMarca> marcas = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return marcas;
    }

    public List<RvvdCatMarca> getMarcas() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT m FROM RvvdCatMarca m WHERE m.status = 1");
        List<RvvdCatMarca> marcas = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return marcas;
    }

    public RvvdCatMarca getMarca(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatMarca marca = (RvvdCatMarca) session.get(RvvdCatMarca.class, id);
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return marca;
    }

    public RvvdCatMarca getMarca(String marca) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT m FROM RvvdCatMarca m WHERE m.marcaR = '" + marca.toUpperCase() + "' OR m.marcaEn = '" + marca.toUpperCase() + "'");
        List<RvvdCatMarca> marcas = query.list();
        RvvdCatMarca marcaT = null;
        if (marcas.size() > 0) {
            marcaT = marcas.get(0);
        }
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return marcaT;
    }

    public boolean saveMarca(RvvdCatMarca marca) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((marca.getIdMarca() == null ? getMarca(marca.getMarcaR()) : null) == null) {
                session.saveOrUpdate(marca);
            } else {
                error = "Trademark already exists";
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
