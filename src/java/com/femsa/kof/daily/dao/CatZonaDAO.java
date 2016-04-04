package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatZona;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Clase que permite la manipulación del catálogo de zonas pertenecientes a
 * Daily Dashboard
 *
 * @author TMXIDSJPINAM
 */
public class CatZonaDAO {

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
     * Obtiene la lista completa de zonas
     *
     * @return Regresa una lista de zonas
     */
    public List<RvvdCatZona> getZonasAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatZona> zonas = null;
        try {
            Query query = session.createQuery("SELECT z FROM RvvdCatZona z ORDER BY z.zonaR ASC");
            zonas = query.list();
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
        return zonas;
    }

    /**
     * Obtiene el catálogo de zonas activas
     *
     * @return Regresa una lista de zonas donde el estatus es igual a 1
     */
    public List<RvvdCatZona> getZonas() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatZona> zonas = null;
        try {
            Query query = session.createQuery("SELECT z FROM RvvdCatZona z WHERE z.status = 1 ORDER BY z.zonaR ASC");
            zonas = query.list();
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
        return zonas;
    }

    /**
     * Obtiene una zona en específico
     *
     * @param id El identificador de la zona a buscar
     * @return Regresa la zona obtenida, si esxiste, en caso de no existir se
     * regresa nulo
     */
    public RvvdCatZona getZona(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatZona zona = null;
        try {
            zona = (RvvdCatZona) session.get(RvvdCatZona.class, id);
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
        return zona;
    }

    /**
     * Obtenio una zona en específico
     *
     * @param zona nombre de la zona a buscar
     * @return Regresa la zona buscada, en caso de no existir se regresa nulo
     */
    public RvvdCatZona getZona(String zona) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatZona zonaT = null;
        try {
            Query query = session.createQuery("SELECT z FROM RvvdCatZona z WHERE z.zonaR = '" + zona.toUpperCase() + "' OR z.zonaEn = '" + zona.toUpperCase() + "'");
            List<RvvdCatZona> zonas = query.list();

            if (zonas != null && !zonas.isEmpty()) {
                zonaT = zonas.get(0);
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
        return zonaT;
    }

    /**
     * Guarda o actualiza una zona
     *
     * @param zona Zona a guardar
     * @return Si el guardado concluyo con éxito se regresa verdadero, en caso
     * contrario se regresa falso y el error es almacenado en el atributo error
     */
    public boolean saveZona(RvvdCatZona zona) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            RvvdCatZona zonaT = zona.getIdZona() != null ? (RvvdCatZona) session.get(RvvdCatZona.class, zona.getIdZona()) : null;
            if ((zonaT != null && zonaT.getZonaR().equalsIgnoreCase(zona.getZonaR())) || getZona(zona.getZonaR()) == null) {
                zonaT = zonaT != null ? zonaT : new RvvdCatZona();
                zonaT.setIdZona(zona.getIdZona());
                zonaT.setZonaR(zona.getZonaR());
                zonaT.setZonaEn(zona.getZonaEn());
                zonaT.setStatus(zona.getStatus());
                session.saveOrUpdate(zonaT);
                error = null;
            } else {
                error = "Zone already exists";
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
     * Elimina una zona
     *
     * @param zona Zona a eliminar
     * @return Si la eliminación concluyó con éxito se regresa verdadero, en
     * caso contrario se regresa falso y el error es almacenado en el atributo
     * error
     */
    public boolean deleteZona(RvvdCatZona zona) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query queryNativo;
        List<Object> resValicacion;
        int numOcurrencias = 0;
        boolean flagOk = true;
        RvvdCatZona zonaActual = (RvvdCatZona) session.get(RvvdCatZona.class, zona.getIdZona());
        try {
            session.beginTransaction();
            if (zona.getIdZona() != null) {
                queryNativo = session.createSQLQuery("SELECT COUNT(PAIS) FROM RVVD_RECLASIF_ZONA WHERE ZONA_R = '" + zonaActual.getZonaR() + "'");
                resValicacion = queryNativo.list();
                for (Object object : resValicacion) {
                    numOcurrencias = Integer.parseInt(object.toString());
                }
                if (numOcurrencias == 0) {
                    session.delete(zonaActual);
                    error = null;
                } else {
                    error = "Can not delete the zone, is already used";
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
