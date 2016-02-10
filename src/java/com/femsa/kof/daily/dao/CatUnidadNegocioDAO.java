package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatUnidadNegocio;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Permite la manipulación del catálogo de Unidad de negocio
 *
 * @author TMXIDSJPINAM
 */
public class CatUnidadNegocioDAO {

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
     * Obtiene las unidades de negocio sin importar el estatus
     *
     * @return Una lista con las unidades de negocio sin filtrar
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
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
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
     * Obtiene las unidades de negocio donde el estaus es igual a 1
     *
     * @return Regresa una lista con las unidades de negocio filtradas
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
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
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
     * Obtiene una unidad de negocio en específico
     *
     * @param id El identificador de la unidad de negocio a buscar
     * @return La unidad de negocio buscada, en caso de no existir se regresa
     * nulo
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
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
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
     * Obtiene una unidad de negocio en específico
     *
     * @param unidad El nombre de la unidad de negocio a buscar
     * @return La unidad de negocio buscada, en caso de no existir se regresa
     * nulo
     */
    public RvvdCatUnidadNegocio getUnidadNeg(String unidad) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatUnidadNegocio unidadT = null;
        try {
            Query query = session.createQuery("SELECT un FROM RvvdCatUnidadNegocio un WHERE un.unidadNegocioR = '" + unidad.toUpperCase() + "' OR un.unidadNegocioEn = '" + unidad.toUpperCase() + "'");
            List<RvvdCatUnidadNegocio> unidades = query.list();
            if (!unidades.isEmpty()) {
                unidadT = unidades.get(0);
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
        return unidadT;
    }

    /**
     * Guarda o actualiza una unidad de negocio
     *
     * @param unidad La unidad de negocio a ser guardada o actualizada
     * @return En caso de éxito regresa verdadero, de lo contrario se regresa
     * falso y el error es almacenado en el atributo error
     *
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
