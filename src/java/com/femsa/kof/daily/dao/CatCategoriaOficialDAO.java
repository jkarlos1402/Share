package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatCategoriaOficial;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CatCategoriaOficialDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdCatCategoriaOficial> getCategoriasOficialesAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatCategoriaOficial> categoriasOficiales = null;
        try {
            Query query = session.createQuery("SELECT co FROM RvvdCatCategoriaOficial co");
            categoriasOficiales = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return categoriasOficiales;
    }

    public List<RvvdCatCategoriaOficial> getCategoriasOficiales() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatCategoriaOficial> categoriasOficiales = null;
        try {
            Query query = session.createQuery("SELECT co FROM RvvdCatCategoriaOficial co WHERE co.status = 1");
            categoriasOficiales = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return categoriasOficiales;
    }

    public RvvdCatCategoriaOficial getCategoriaOficial(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatCategoriaOficial categOficial = null;
        try {
            categOficial = (RvvdCatCategoriaOficial) session.get(RvvdCatCategoriaOficial.class, id);
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return categOficial;
    }

    public RvvdCatCategoriaOficial getCategoriaOficial(String categoria) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatCategoriaOficial categOficial = null;
        try {
            Query query = session.createQuery("SELECT co FROM RvvdCatCategoriaOficial co WHERE co.categoriaOficial = '" + categoria.toUpperCase() + "' OR co.categoriaOficialEn = '" + categoria.toUpperCase() + "'");
            List<RvvdCatCategoriaOficial> categoriasOficiales = query.list();
            if (categoriasOficiales.size() > 0) {
                categOficial = categoriasOficiales.get(0);
            }
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return categOficial;
    }

    public boolean saveCategoriaOficial(RvvdCatCategoriaOficial catCategoriaOficial) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((catCategoriaOficial.getIdCategoriaOficial() == null ? getCategoriaOficial(catCategoriaOficial.getCategoriaOficial()) : null) == null) {
                session.saveOrUpdate(catCategoriaOficial);
                error = null;
            } else {
                error = "Official category already exists";
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
