package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatCategoria;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CatCategoriaDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdCatCategoria> getCategoriasAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT c FROM RvvdCatCategoria c");
        List<RvvdCatCategoria> categorias = query.list();
        session.close();
        return categorias;
    }

    public List<RvvdCatCategoria> getCategorias() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT c FROM RvvdCatCategoria c WHERE c.status = 1");
        List<RvvdCatCategoria> categorias = query.list();
        session.close();
        return categorias;
    }

    public RvvdCatCategoria getCategoria(Integer id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatCategoria categ = (RvvdCatCategoria) session.get(RvvdCatCategoria.class, id);
        session.close();
        return categ;
    }

    public RvvdCatCategoria getCategoria(String categoria) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT c FROM RvvdCatCategoria c WHERE c.categoria = '" + categoria.toUpperCase() + "' OR c.categoriaEn = '" + categoria.toUpperCase() + "'");
        List<RvvdCatCategoria> categorias = query.list();
        RvvdCatCategoria categ = null;
        if (categorias.size() > 0) {
            categ = categorias.get(0);
        }
        session.close();
        return categ;
    }

    public boolean saveCategoria(RvvdCatCategoria catCategoria) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((catCategoria.getIdCategoria() == null ? getCategoria(catCategoria.getCategoria()) : null) == null) {
                session.saveOrUpdate(catCategoria);
            } else {
                error = "Category already exists";
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
