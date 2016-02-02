package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatUnidadNegocio;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author TMXIDSJPINAM
 */
public class CatUnidadNegocioDAO {

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
    public List<RvvdCatUnidadNegocio> getUnidadesNegAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatUnidadNegocio> unidades = null;
        try {
            Query query = session.createQuery("SELECT un FROM RvvdCatUnidadNegocio un");
            unidades = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return unidades;
    }

    /**
     *
     * @return
     */
    public List<RvvdCatUnidadNegocio> getUnidadesNeg() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatUnidadNegocio> unidades = null;
        try {
            Query query = session.createQuery("SELECT un FROM RvvdCatUnidadNegocio un WHERE un.status = 1");
            unidades = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return unidades;
    }

    /**
     *
     * @param id
     * @return
     */
    public RvvdCatUnidadNegocio getUnidadNeg(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatUnidadNegocio unidad = null;
        try {
            unidad = (RvvdCatUnidadNegocio) session.get(RvvdCatUnidadNegocio.class, id);
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return unidad;
    }

    /**
     *
     * @param unidad
     * @return
     */
    public RvvdCatUnidadNegocio getUnidadNeg(String unidad) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatUnidadNegocio unidadT = null;
        try {
            Query query = session.createQuery("SELECT un FROM RvvdCatUnidadNegocio un WHERE un.unidadNegocioR = '" + unidad.toUpperCase() + "' OR un.unidadNegocioEn = '" + unidad.toUpperCase() + "'");
            List<RvvdCatUnidadNegocio> unidades = query.list();
            if (unidades.size() > 0) {
                unidadT = unidades.get(0);
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
        return unidadT;
    }

    /**
     *
     * @param unidad
     * @return
     */
    public boolean saveUnidadNeg(RvvdCatUnidadNegocio unidad) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((unidad.getIdUnidadNegocio() == null ? getUnidadNeg(unidad.getUnidadNegocioR()) : null) == null) {
                session.saveOrUpdate(unidad);
                error = null;
            } else {
                error = "Bussiness unit already exists";
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
