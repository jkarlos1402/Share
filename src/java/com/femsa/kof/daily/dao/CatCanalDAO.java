package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatCanal;
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
public class CatCanalDAO {

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
     * Obtiene la lista completa de canales
     *
     * @return Regresa una lista de canales
     */
    public List<RvvdCatCanal> getCanalesAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatCanal> canales = null;
        try {
            Query query = session.createQuery("SELECT c FROM RvvdCatCanal c ORDER BY c.canalR ASC");
            canales = query.list();
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
        return canales;
    }

    /**
     * Obtiene el catálogo de canales activos
     *
     * @return Regresa una lista de canales donde el estatus es igual a 1
     */
    public List<RvvdCatCanal> getCanales() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatCanal> canales = null;
        try {
            Query query = session.createQuery("SELECT c FROM RvvdCatCanal c WHERE c.status = 1 ORDER BY c.canalR ASC");
            canales = query.list();
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
        return canales;
    }

    /**
     * Obtiene un canal en específico
     *
     * @param id El identificador del canal a buscar
     * @return Regresa el canal obtenido, si esxiste, en caso de no existir se
     * regresa nulo
     */
    public RvvdCatCanal getCanal(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatCanal canal = null;
        try {
            canal = (RvvdCatCanal) session.get(RvvdCatCanal.class, id);
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
        return canal;
    }

    /**
     * Obtenio un canal en específico
     *
     * @param canal nombre del canal a buscar
     * @return Regresa el canal buscado, en caso de no existir se regresa nulo
     */
    public RvvdCatCanal getCanal(String canal) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatCanal canalT = null;
        try {
            Query query = session.createQuery("SELECT c FROM RvvdCatCanal c WHERE c.canalR = '" + canal.toUpperCase() + "' OR c.canalEn = '" + canal.toUpperCase() + "'");
            List<RvvdCatCanal> canales = query.list();

            if (canales != null && !canales.isEmpty()) {
                canalT = canales.get(0);
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
        return canalT;
    }

    /**
     * Guarda o actualiza un canal
     *
     * @param canal Canal a guardar
     * @return Si el guardado concluyo con éxito se regresa verdadero, en caso
     * contrario se regresa falso y el error es almacenado en el atributo error
     */
    public boolean saveCanal(RvvdCatCanal canal) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            RvvdCatCanal canalT = canal.getIdCanal() != null ? (RvvdCatCanal) session.get(RvvdCatCanal.class, canal.getIdCanal()) : null;
            if ((canalT != null && canalT.getCanalR().equalsIgnoreCase(canal.getCanalR())) || getCanal(canal.getCanalR()) == null) {
                canalT = canalT != null ? canalT : new RvvdCatCanal();
                canalT.setIdCanal(canal.getIdCanal());
                canalT.setCanalR(canal.getCanalR());
                canalT.setCanalEn(canal.getCanalEn());
                canalT.setStatus(canal.getStatus());
                canalT.setSubCanalesList(canal.getSubCanalesList());
                session.saveOrUpdate(canalT);
                error = null;
            } else {
                error = "Channel already exists";
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

    /**
     * Elimina un canal
     *
     * @param canal Canal a eliminar
     * @return Si la eliminación concluyó con éxito se regresa verdadero, en
     * caso contrario se regresa falso y el error es almacenado en el atributo
     * error
     */
    public boolean deleteCanal(RvvdCatCanal canal) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query queryNativo;
        List<Object> resValicacion;
        int numOcurrencias = 0;
        boolean flagOk = true;
        RvvdCatCanal canalActual = (RvvdCatCanal) session.get(RvvdCatCanal.class, canal.getIdCanal());
        try {
            session.beginTransaction();
            if (canal.getIdCanal() != null) {
                queryNativo = session.createSQLQuery("SELECT COUNT(PAIS) FROM RVVD_RECLASIF_CANAL WHERE CANAL_R = '" + canalActual.getCanalR() + "'");
                resValicacion = queryNativo.list();
                for (Object object : resValicacion) {
                    numOcurrencias = Integer.parseInt(object.toString());
                }
                if (numOcurrencias == 0) {
                    session.delete(canalActual);
                    error = null;
                } else {
                    error = "Can not delete the channel, is already used";
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
