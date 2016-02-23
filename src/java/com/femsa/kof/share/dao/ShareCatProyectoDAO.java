package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatProyecto;
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
public class ShareCatProyectoDAO {

    private String error = "";
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
     * @param id
     * @return
     */
    public ShareCatProyecto getProyecto(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        ShareCatProyecto proyecto = null;
        try {
            proyecto = (ShareCatProyecto) session.get(ShareCatProyecto.class, id);
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
        return proyecto;
    }

    /**
     *
     * @return
     */
    public List<ShareCatProyecto> getCatProyectos() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ShareCatProyecto> proyectos = null;
        try {
            Query query = session.createQuery("SELECT proyecto FROM ShareCatProyecto proyecto WHERE proyecto.status = 1");
            proyectos = query.list();
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
        return proyectos;
    }

    /**
     *
     * @param nombrProyecto
     * @return
     */
    public ShareCatProyecto getProyecto(String nombrProyecto) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        ShareCatProyecto proyecto = null;
        try {
            Query query = session.createQuery("SELECT proyecto FROM ShareCatProyecto proyecto WHERE proyecto.nombreProyecto = '" + nombrProyecto.toUpperCase() + "'");
            List<ShareCatProyecto> proyectos = query.list();
            if (proyectos != null && !proyectos.isEmpty()) {
                proyecto = proyectos.get(0);
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
        return proyecto;
    }

    /**
     *
     * @return
     */
    public List<ShareCatProyecto> getCatProyectosAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ShareCatProyecto> proyectos = null;
        try {
            Query query = session.createQuery("SELECT proyecto FROM ShareCatProyecto proyecto");
            proyectos = query.list();
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
        return proyectos;
    }

    /**
     *
     * @param proyecto
     * @return
     */
    public boolean saveProyecto(ShareCatProyecto proyecto) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = null;
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if (proyecto.getIdProyecto() != null) {
                session.update(proyecto);
                error = null;
            } else if (getProyecto(proyecto.getNombreProyecto()) == null) {
                session.save(proyecto);
                error = null;
            } else {
                error = "Project already exists";
                flagOk = false;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
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
