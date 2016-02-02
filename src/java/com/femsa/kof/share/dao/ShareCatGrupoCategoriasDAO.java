package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatGrupoCategorias;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author TMXIDSJPINAM
 */
public class ShareCatGrupoCategoriasDAO {

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
    public List<ShareCatGrupoCategorias> getCategoryGroups() {
        List<ShareCatGrupoCategorias> grupos = null;
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("SELECT gc FROM ShareCatGrupoCategorias gc WHERE gc.status = 1");
            grupos = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return grupos;
    }

    /**
     *
     * @param name
     * @return
     */
    public ShareCatGrupoCategorias getCategoryGroup(String name) {
        ShareCatGrupoCategorias grupo = null;
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("SELECT gc FROM ShareCatGrupoCategorias gc WHERE gc.grupoCategoria = '" + name.toUpperCase() + "'");
            List<ShareCatGrupoCategorias> grupos = query.list();
            if (grupos.size() > 0) {
                grupo = grupos.get(0);
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
        return grupo;
    }

    /**
     *
     * @return
     */
    public List<ShareCatGrupoCategorias> getCategoryGroupsAll() {
        List<ShareCatGrupoCategorias> grupos = null;
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("SELECT gc FROM ShareCatGrupoCategorias gc");
            grupos = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getClass().getName() + " - " + e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return grupos;
    }

    /**
     *
     * @param grupoCategorias
     * @return
     */
    public boolean saveGroupCategory(ShareCatGrupoCategorias grupoCategorias) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((grupoCategorias.getPkGrupoCategoria() == null ? getCategoryGroup(grupoCategorias.getGrupoCategoria()) : null) == null) {
                session.saveOrUpdate(grupoCategorias);
                error = null;
            } else {
                error = "Group category already exists";
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
