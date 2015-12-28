package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatGrupoCategorias;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ShareCatGrupoCategoriasDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ShareCatGrupoCategorias> getCategoryGroups() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT gc FROM ShareCatGrupoCategorias gc WHERE gc.status = 1");
        List<ShareCatGrupoCategorias> grupos = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return grupos;
    }

    public ShareCatGrupoCategorias getCategoryGroup(String name) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT gc FROM ShareCatGrupoCategorias gc WHERE gc.grupoCategoria = '" + name.toUpperCase() + "'");
        List<ShareCatGrupoCategorias> grupos = query.list();
        ShareCatGrupoCategorias grupo = null;
        if (grupos.size() > 0) {
            grupo = grupos.get(0);
        }
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return grupo;
    }

    public List<ShareCatGrupoCategorias> getCategoryGroupsAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT gc FROM ShareCatGrupoCategorias gc");
        List<ShareCatGrupoCategorias> grupos = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return grupos;
    }

    public boolean saveGroupCategory(ShareCatGrupoCategorias grupoCategorias) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((grupoCategorias.getPkGrupoCategoria() == null ? getCategoryGroup(grupoCategorias.getGrupoCategoria()) : null) == null) {
                session.saveOrUpdate(grupoCategorias);
            } else {
                error = "Group category already exists";
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
