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
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT co FROM RvvdCatCategoriaOficial co");
        List<RvvdCatCategoriaOficial> categoriasOficiales = query.list();
        session.close();
        return categoriasOficiales;
    }

    public List<RvvdCatCategoriaOficial> getCategoriasOficiales() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT co FROM RvvdCatCategoriaOficial co WHERE co.status = 1");
        List<RvvdCatCategoriaOficial> categoriasOficiales = query.list();
        session.close();
        return categoriasOficiales;
    }

    public RvvdCatCategoriaOficial getCategoriaOficial(Integer id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatCategoriaOficial categOficial = (RvvdCatCategoriaOficial) session.get(RvvdCatCategoriaOficial.class, id);
        session.close();
        return categOficial;
    }

    public RvvdCatCategoriaOficial getCategoriaOficial(String categoria) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT co FROM RvvdCatCategoriaOficial co WHERE co.categoriaOficial = '" + categoria.toUpperCase() + "' OR co.categoriaOficialEn = '" + categoria.toUpperCase() + "'");
        List<RvvdCatCategoriaOficial> categoriasOficiales = query.list();
        RvvdCatCategoriaOficial categOficial = null;
        if (categoriasOficiales.size() > 0) {
            categOficial = categoriasOficiales.get(0);
        }
        session.close();
        return categOficial;
    }

    public boolean saveCategoriaOficial(RvvdCatCategoriaOficial catCategoriaOficial) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((catCategoriaOficial.getIdCategoriaOficial() == null ? getCategoriaOficial(catCategoriaOficial.getCategoriaOficial()) : null) == null) {
                session.saveOrUpdate(catCategoriaOficial);
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
            session.close();
        }
        return flagOk;
    }
}
