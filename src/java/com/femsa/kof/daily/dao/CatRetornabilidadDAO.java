package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatCanal;
import com.femsa.kof.daily.pojos.RvvdCatRetornabilidad;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Clase que permite la manipulación del catálogo de canales pertenecientes a
 * Daily Dashboard
 *
 * @author TMXIDSJPINAM
 */
public class CatRetornabilidadDAO {

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
     * Obtiene la lista completa de retornabilidades
     *
     * @return Regresa una lista de retornabilidades
     */
    public List<RvvdCatRetornabilidad> getRetornabilidadesAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatRetornabilidad> retornabilidades = null;
        try {
            Query query = session.createQuery("SELECT r FROM RvvdCatRetornabilidad r ORDER BY r.retornabilidadR ASC");
            retornabilidades = query.list();
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
        return retornabilidades;
    }

    /**
     * Obtiene el catálogo de retornabilidades activas
     *
     * @return Regresa una lista de retornabilidades donde el estatus es igual a
     * 1
     */
    public List<RvvdCatRetornabilidad> getRetornabilidades() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatRetornabilidad> retornabilidades = null;
        try {
            Query query = session.createQuery("SELECT r FROM RvvdCatRetornabilidad r WHERE r.status = 1 ORDER BY r.retornabilidadR ASC");
            retornabilidades = query.list();
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
        return retornabilidades;
    }

    /**
     * Obtiene una retornabilidad en específico
     *
     * @param id El identificador de la retornabilidad a buscar
     * @return Regresa la retornabilidad obtenida, si esxiste, en caso de no
     * existir se regresa nulo
     */
    public RvvdCatRetornabilidad getRetornabilidad(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatRetornabilidad retornabilidad = null;
        try {
            retornabilidad = (RvvdCatRetornabilidad) session.get(RvvdCatRetornabilidad.class, id);
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
        return retornabilidad;
    }

    /**
     * Obtenio una retornabilidad en específico
     *
     * @param retornabilidad nombre de la retornabilidad a buscar
     * @return Regresa el canal buscado, en caso de no existir se regresa nulo
     */
    public RvvdCatRetornabilidad getRetornabilidad(String retornabilidad) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatRetornabilidad retornabilidadT = null;
        try {
            Query query = session.createQuery("SELECT r FROM RvvdCatRetornabilidad r WHERE r.retornabilidadR = '" + retornabilidad.toUpperCase() + "'");
            List<RvvdCatRetornabilidad> retornabilidades = query.list();

            if (retornabilidades != null && !retornabilidades.isEmpty()) {
                retornabilidadT = retornabilidades.get(0);
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
        return retornabilidadT;
    }

    /**
     * Guarda o actualiza una retornabilidad
     *
     * @param retornabilidad Retornabilidad a guardar
     * @return Si el guardado concluyo con éxito se regresa verdadero, en caso
     * contrario se regresa falso y el error es almacenado en el atributo error
     */
    public boolean saveRetornabilidad(RvvdCatRetornabilidad retornabilidad) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            RvvdCatRetornabilidad returnT = (RvvdCatRetornabilidad) session.get(RvvdCatRetornabilidad.class, retornabilidad.getIdRetornabilidad());
            if (returnT.getRetornabilidadR().equalsIgnoreCase(retornabilidad.getRetornabilidadR()) || getRetornabilidad(retornabilidad.getRetornabilidadR()) == null) {
                returnT.setIdRetornabilidad(retornabilidad.getIdRetornabilidad()); 
                returnT.setRetornabilidadR(retornabilidad.getRetornabilidadR());
                returnT.setStatus(retornabilidad.getStatus());
                session.saveOrUpdate(returnT);
                error = null;
            } else {
                error = "Returnability already exists";
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

    /**
     * Elimina una retornabilidad
     *
     * @param retornabilidad Retornabilidad a eliminar
     * @return Si la eliminación concluyó con éxito se regresa verdadero, en
     * caso contrario se regresa falso y el error es almacenado en el atributo
     * error
     */
    public boolean deleteRetornabilidad(RvvdCatRetornabilidad retornabilidad) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query queryNativo;
        List<Object> resValicacion;
        int numOcurrencias = 0;
        boolean flagOk = true;
        RvvdCatRetornabilidad retornabilidadActual = (RvvdCatRetornabilidad) session.get(RvvdCatRetornabilidad.class, retornabilidad.getIdRetornabilidad());
        try {
            session.beginTransaction();
            if (retornabilidad.getIdRetornabilidad() != null) {
                queryNativo = session.createSQLQuery("SELECT COUNT(PAIS) FROM RVVD_RECLASIF_EMPAQUE WHERE RETORNABILIDAD_R = '" + retornabilidadActual.getRetornabilidadR() + "'");
                resValicacion = queryNativo.list();
                for (Object object : resValicacion) {
                    numOcurrencias = Integer.parseInt(object.toString());
                }
                if (numOcurrencias == 0) {
                    session.delete(retornabilidadActual);
                    error = null;
                } else {
                    error = "Can not delete the returnability, is already used";
                    flagOk = false;
                }
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
