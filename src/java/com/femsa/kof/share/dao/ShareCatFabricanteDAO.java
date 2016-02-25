package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatFabricante;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author TMXIDSJPINAM
 */
public class ShareCatFabricanteDAO {

    private String error;
    private static final String MSG_ERROR_TITULO = "Mensaje de error...";

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
    public List<ShareCatFabricante> getFabricantesAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ShareCatFabricante> fabricantes = null;
        try {
            Query query = session.createQuery("SELECT fab FROM ShareCatFabricante fab");
            fabricantes = query.list();
            error = null;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return fabricantes;
    }

    /**
     *
     * @return
     */
    public List<ShareCatFabricante> getFabricantes() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ShareCatFabricante> fabricantes = null;
        try {
            Query query = session.createQuery("SELECT fab FROM ShareCatFabricante fab WHERE fab.status = 1");
            fabricantes = query.list();
            error = null;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return fabricantes;
    }

    /**
     *
     * @param name
     * @return
     */
    public ShareCatFabricante getFabricante(String name) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ShareCatFabricante> fabricantes = null;
        ShareCatFabricante fabricante = null;
        try {
            Query query = session.createQuery("SELECT fab FROM ShareCatFabricante fab WHERE fab.fabricante = '" + name.toUpperCase() + "'");
            fabricantes = query.list();
            if (fabricantes != null && !fabricantes.isEmpty()) {
                fabricante = fabricantes.get(0);
            }
            error = null;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return fabricante;
    }

    /**
     *
     * @param fabricante
     * @return
     */
    public boolean saveFabricante(ShareCatFabricante fabricante) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((fabricante.getPkFabricante() == null ? getFabricante(fabricante.getFabricante()) : null) == null) {
                session.saveOrUpdate(fabricante);
                error = null;
            } else {
                error = "Manufacturer already exists";
                flagOk = false;
            }
            session.getTransaction().commit();
        } catch (Exception e) {            
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            flagOk = false;
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return flagOk;
    }
}
