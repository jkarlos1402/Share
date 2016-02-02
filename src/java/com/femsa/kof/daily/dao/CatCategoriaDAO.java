package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatCategoria;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author TMXIDSJPINAM
 */
public class CatCategoriaDAO {

    private String error;

    /**
     *
     * @return
     */
    public String getError() {
        return error;
    }

    /**
     *
     * @param error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatCategoria> getCategoriasAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatCategoria> categorias = null;
        try {
            Query query = session.createQuery("SELECT c FROM RvvdCatCategoria c");
            categorias = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return categorias;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatCategoria> getCategorias() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatCategoria> categorias = null;
        try {
            Query query = session.createQuery("SELECT c FROM RvvdCatCategoria c WHERE c.status = 1");
            categorias = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return categorias;
    }

    /**
     *
     * @param id
     * @return
     */
    public RvvdCatCategoria getCategoria(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatCategoria categ = null;
        try {
            categ = (RvvdCatCategoria) session.get(RvvdCatCategoria.class, id);
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return categ;
    }

    /**
     *
     * @param categoria
     * @return
     */
    public RvvdCatCategoria getCategoria(String categoria) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatCategoria categ = null;
        try {
            Query query = session.createQuery("SELECT c FROM RvvdCatCategoria c WHERE c.categoria = '" + categoria.toUpperCase() + "' OR c.categoriaEn = '" + categoria.toUpperCase() + "'");
            List<RvvdCatCategoria> categorias = query.list();
            if (categorias.size() > 0) {
                categ = categorias.get(0);
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
        return categ;
    }

    /**
     *
     * @param catCategoria
     * @return
     */
    public boolean saveCategoria(RvvdCatCategoria catCategoria) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((catCategoria.getIdCategoria() == null ? getCategoria(catCategoria.getCategoria()) : null) == null) {
                session.saveOrUpdate(catCategoria);
                error = null;
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
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return flagOk;
    }
}
