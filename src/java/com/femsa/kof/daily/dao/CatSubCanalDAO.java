package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatGec;
import com.femsa.kof.daily.pojos.RvvdCatSubCanal;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Permite la administracion del catálogo de sub canales
 *
 * @author TMXIDSJPINAM
 */
public class CatSubCanalDAO {

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
     * Obtiene una lista con los sub-canales sin importar su estatus
     *
     * @return Regresa una lista con los sub-canales existentes
     */
    public List<RvvdCatSubCanal> getSubCanalesAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatSubCanal> subCanales = null;
        try {
            Query query = session.createQuery("SELECT sc FROM RvvdCatSubCanal sc");
            subCanales = query.list();
            error = null;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return subCanales;
    }

    /**
     * Obtiene una lista de sub-canales donde el estatus sea igual a 1
     *
     * @return Regresa una lista de sub-canales filtrados
     */
    public List<RvvdCatSubCanal> getSubCanales() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatSubCanal> subCanales = null;
        try {
            Query query = session.createQuery("SELECT sc FROM RvvdCatSubCanal sc WHERE sc.status = 1");
            subCanales = query.list();
            error = null;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return subCanales;
    }

    /**
     * Obtiene un sub-canal en específico
     *
     * @param id El identificador del sub-canal
     * @return El tipo de sub-canal, en caso de no existir se regresa nulo
     */
    public RvvdCatSubCanal getSubCanal(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatSubCanal subCanal = null;
        try {
            subCanal = (RvvdCatSubCanal) session.get(RvvdCatSubCanal.class, id);
            error = null;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return subCanal;
    }

    /**
     * Obtiene un sub-canal en específico
     *
     * @param subCanal El nombre del sub-canal a buscar
     * @return El sub-canal buscado, en caso de no existir se regresa nulo
     */
    public RvvdCatSubCanal getSubCanal(String subCanal) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatSubCanal subCanalT = null;
        try {
            Query query = session.createQuery("SELECT sc FROM RvvdCatSubCanal sc WHERE sc.subCanalR = '" + subCanal.toUpperCase() + "' OR sc.subCanalEn = '" + subCanal.toUpperCase() + "'");
            List<RvvdCatSubCanal> subCanales = query.list();
            if (!subCanales.isEmpty()) {
                subCanalT = subCanales.get(0);
            }
            error = null;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return subCanalT;
    }

    /**
     * Guarda o actualiza un sub-canal
     *
     * @param subCanal El sub-canal a guardar o actualizar
     * @return En caso de éxito se regresa verdadero, en caso contrario regresa
     * falso y el error se almacena en el atributo error
     */
    public boolean saveSubCanal(RvvdCatSubCanal subCanal) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((subCanal.getIdSubCanal() == null ? getSubCanal(subCanal.getSubCanalR()) : null) == null) {
                session.saveOrUpdate(subCanal);
                error = null;
            } else {
                error = "Subchannel already exists";
                flagOk = false;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
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
