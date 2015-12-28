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
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT categ FROM ShareCatCategorias categ WHERE categ.status = 1");
        List<ShareCatCategorias> categories = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return categories;
    }

    public ShareCatCategorias getCategoria(String name) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT categ FROM ShareCatCategorias categ WHERE categ.categoria = '" + name.toUpperCase() + "'");
        List<ShareCatCategorias> categories = query.list();
        ShareCatCategorias category = null;
        if (categories.size() > 0) {
            category = categories.get(0);
        }
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return category;
    }

    public List<ShareCatCategorias> getCategoriasAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT categ FROM ShareCatCategorias categ");
        List<ShareCatCategorias> categories = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
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
            } else {
                error = "Category already exists";
                flagOk = false;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
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
