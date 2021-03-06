package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatTipoConsumo;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Permite la manipulación del catálogo de tipo de consumo
 *
 * @author TMXIDSJPINAM
 */
public class CatTipoConsumoDAO {

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
     * Obtiene los tipos de consumo sin importar su estatus
     *
     * @return Regresa una lista con los tipos de consumo sin filtrar
     */
    public List<RvvdCatTipoConsumo> getTiposConsumoAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatTipoConsumo> tiposConsumo = null;
        try {
            Query query = session.createQuery("SELECT tc FROM RvvdCatTipoConsumo tc ORDER BY tc.tipoConsumoR ASC");
            tiposConsumo = query.list();
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
        return tiposConsumo;
    }

    /**
     * Obtiene los tipos de consumo donde el estaus es igual a 1
     *
     * @return Regresa una lista con los tipos de consumo filtrados
     */
    public List<RvvdCatTipoConsumo> getTiposConsumo() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatTipoConsumo> tiposConsumo = null;
        try {
            Query query = session.createQuery("SELECT tc FROM RvvdCatTipoConsumo tc WHERE tc.status = 1 ORDER BY tc.tipoConsumoR ASC");
            tiposConsumo = query.list();
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
        return tiposConsumo;
    }

    /**
     * Obtiene un tipo de consumo en específico
     *
     * @param id El identificador del tipo de consumo a buscar
     * @return El tipo de consumo buscado, en caso de no existir se regresa nulo
     */
    public RvvdCatTipoConsumo getTipoConsumo(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatTipoConsumo tipoConsumo = null;
        try {
            tipoConsumo = (RvvdCatTipoConsumo) session.get(RvvdCatTipoConsumo.class, id);
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
        return tipoConsumo;
    }

    /**
     * Obtiene un tipo de consumo en específico
     *
     * @param tipoConsumo El nombre del tipo de consumo a buscar
     * @return El tipo de consumo buscado, en caso de no existir se regresa nulo
     */
    public RvvdCatTipoConsumo getTipoConsumo(String tipoConsumo) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatTipoConsumo tipoConsumoT = null;
        try {
            Query query = session.createQuery("SELECT tc FROM RvvdCatTipoConsumo tc WHERE tc.tipoConsumoR = '" + tipoConsumo.toUpperCase() + "' OR tc.tipoConsumoEn = '" + tipoConsumo.toUpperCase() + "'");
            List<RvvdCatTipoConsumo> tiposConsumo = query.list();
            if (tiposConsumo != null && !tiposConsumo.isEmpty()) {
                tipoConsumoT = tiposConsumo.get(0);
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
        return tipoConsumoT;
    }

    /**
     * Guarda o actualiza un tipo de consumo
     *
     * @param tipoConsumo El tipo de consumo a guardar o actualizar
     * @return En caso de éxito se regresa verdadero, de lo contrario se regresa
     * falso y el error es almacenado en el atributo error
     */
    public boolean saveTipoConsumo(RvvdCatTipoConsumo tipoConsumo) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            RvvdCatTipoConsumo tipoConsumoT = tipoConsumo.getIdTipoConsumo() != null ? (RvvdCatTipoConsumo) session.get(RvvdCatTipoConsumo.class, tipoConsumo.getIdTipoConsumo()) : null;
            if ((tipoConsumoT != null && tipoConsumoT.getTipoConsumoR().equalsIgnoreCase(tipoConsumo.getTipoConsumoR())) || getTipoConsumo(tipoConsumo.getTipoConsumoR()) == null) {
                tipoConsumoT = tipoConsumoT != null ? tipoConsumoT : new RvvdCatTipoConsumo();
                tipoConsumoT.setIdTipoConsumo(tipoConsumo.getIdTipoConsumo());
                tipoConsumoT.setTipoConsumoR(tipoConsumo.getTipoConsumoR());
                tipoConsumoT.setTipoConsumoEn(tipoConsumo.getTipoConsumoEn());
                tipoConsumoT.setStatus(tipoConsumo.getStatus());
                session.saveOrUpdate(tipoConsumoT);
                error = null;
            } else {
                error = "Type of consumption already exists";
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
     * Elimina un tipo de consumo
     *
     * @param tipoConsumo Tipo de consumo a eliminar
     * @return Si la eliminación concluyó con éxito se regresa verdadero, en
     * caso contrario se regresa falso y el error es almacenado en el atributo
     * error
     */
    public boolean deleteTipoConsumo(RvvdCatTipoConsumo tipoConsumo) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query queryNativo;
        List<Object> resValicacion;
        int numOcurrencias = 0;
        boolean flagOk = true;
        RvvdCatTipoConsumo tipoConsumoActual = (RvvdCatTipoConsumo) session.get(RvvdCatTipoConsumo.class, tipoConsumo.getIdTipoConsumo());
        try {
            session.beginTransaction();
            if (tipoConsumo.getIdTipoConsumo() != null) {
                queryNativo = session.createSQLQuery("SELECT COUNT(PAIS) FROM RVVD_RECLASIF_EMPAQUE WHERE TIPO_CONSUMO_R = '" + tipoConsumoActual.getTipoConsumoR() + "'");
                resValicacion = queryNativo.list();
                for (Object object : resValicacion) {
                    numOcurrencias = Integer.parseInt(object.toString());
                }
                if (numOcurrencias == 0) {
                    session.delete(tipoConsumoActual);
                    error = null;
                } else {
                    error = "Can not delete the type of consumption, is already used";
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
