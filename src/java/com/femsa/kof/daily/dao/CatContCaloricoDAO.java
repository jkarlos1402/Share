package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatContenidoCalorico;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author TMXIDSJPINAM
 */
public class CatContCaloricoDAO {

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
    public List<RvvdCatContenidoCalorico> getContsCalAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatContenidoCalorico> contenidos = null;
        try {
            Query query = session.createQuery("SELECT cc FROM RvvdCatContenidoCalorico cc");
            contenidos = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return contenidos;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatContenidoCalorico> getContsCal() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatContenidoCalorico> contenidos = null;
        try {
            Query query = session.createQuery("SELECT cc FROM RvvdCatContenidoCalorico cc WHERE cc.status = 1");
            contenidos = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return contenidos;
    }

    /**
     *
     * @param id
     * @return
     */
    public RvvdCatContenidoCalorico getContCal(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatContenidoCalorico contenido = null;
        try {
            contenido = (RvvdCatContenidoCalorico) session.get(RvvdCatContenidoCalorico.class, id);
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return contenido;
    }

    /**
     *
     * @param contenido
     * @return
     */
    public RvvdCatContenidoCalorico getContCal(String contenido) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatContenidoCalorico contenidoT = null;
        try {
            Query query = session.createQuery("SELECT cc FROM RvvdCatContenidoCalorico cc WHERE cc.contenidoCaloricoR = '" + contenido.toUpperCase() + "' OR cc.contenidoCaloricoEn = '" + contenido.toUpperCase() + "'");
            List<RvvdCatContenidoCalorico> contenidos = query.list();

            if (contenidos.size() > 0) {
                contenidoT = contenidos.get(0);
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
        return contenidoT;
    }

    /**
     *
     * @param contenido
     * @return
     */
    public boolean saveContCal(RvvdCatContenidoCalorico contenido) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((contenido.getIdContenidoCalorico() == null ? getContCal(contenido.getContenidoCaloricoR()) : null) == null) {
                session.saveOrUpdate(contenido);
                error = null;
            } else {
                error = "Calorie already exists";
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
