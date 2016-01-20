package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatCategorias;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ShareCatCategoriasDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ShareCatCategorias> getCategorias() {
        List<ShareCatCategorias> categories = null;
        try {
            HibernateUtil hibernateUtil = new HibernateUtil();

            if (hibernateUtil.isConnectionOk()) {
                SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
                Session session = sessionFactory.openSession();
                Query query = session.createQuery("SELECT categ FROM ShareCatCategorias categ WHERE categ.status = 1");
                categories = query.list();
                session.flush();
                session.clear();
                session.close();
                hibernateUtil.closeSessionFactory();
                error = null;
            } else {
                error = hibernateUtil.getError();
            }
        } catch (Exception e) {
            error = e.getMessage();
        }
        return categories;
    }

    public ShareCatCategorias getCategoria(String name) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        ShareCatCategorias category = null;
        try {
            Query query = session.createQuery("SELECT categ FROM ShareCatCategorias categ WHERE categ.categoria = '" + name.toUpperCase() + "'");
            List<ShareCatCategorias> categories = query.list();
            if (categories.size() > 0) {
                category = categories.get(0);
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
        return category;
    }

    public List<ShareCatCategorias> getCategoriasAll() {
        List<ShareCatCategorias> categories = null;
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("SELECT categ FROM ShareCatCategorias categ");
            categories = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return categories;
    }

    public boolean saveCategoria(ShareCatCategorias categoria) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((categoria.getPkCategoria() == null ? getCategoria(categoria.getCategoria()) : null) == null) {
                session.saveOrUpdate(categoria);
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
