package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatGec;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Permite la manipulación del catálogo de tipos de cliente
 *
 * @author TMXIDSJPINAM
 */
public class CatGecDAO {

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
     * Obtiene una lista con los tipos de cliente sin importar su estatus
     *
     * @return Regresa una lista con los tipos de cliente existentes
     */
    public List<RvvdCatGec> getGecAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatGec> gecs = null;
        try {
            Query query = session.createQuery("SELECT gec FROM RvvdCatGec gec ORDER BY gec.gecR ASC");
            gecs = query.list();
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
        return gecs;
    }

    /**
     * Obtiene una lista de tipos de cliente donde el estatus sea igual a 1
     *
     * @return Regresa una lista de tipos de cliente filtrados
     */
    public List<RvvdCatGec> getGecs() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatGec> gecs = null;
        try {
            Query query = session.createQuery("SELECT gec FROM RvvdCatGec gec WHERE gec.status = 1 ORDER BY gec.gecR ASC");
            gecs = query.list();
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
        return gecs;
    }

    /**
     * Obtiene un tipo de cliente en específico
     *
     * @param id El identificador del tipo de cliente
     * @return El tipo de cliente buscado, en caso de no existir se regresa nulo
     */
    public RvvdCatGec getGec(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatGec gec = null;
        try {
            gec = (RvvdCatGec) session.get(RvvdCatGec.class, id);
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
        return gec;
    }

    /**
     * Obtiene un tipo de cliente en específico
     *
     * @param gec El nombre del tipo de cliente a buscar
     * @return El tipo de cliente buscado, en caso de no existir se regresa nulo
     */
    public RvvdCatGec getGec(String gec) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatGec gecT = null;
        try {
            Query query = session.createQuery("SELECT gec FROM RvvdCatGec gec WHERE gec.gecR = '" + gec.toUpperCase() + "' OR gec.gecEn = '" + gec.toUpperCase() + "'");
            List<RvvdCatGec> gecs = query.list();
            if (gecs != null && !gecs.isEmpty()) {
                gecT = gecs.get(0);
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
        return gecT;
    }

    /**
     * Guarda o actualiza un tipo de cliente
     *
     * @param gec El tipo de cliente a guardar o actualizar
     * @return En caso de éxito se regresa verdadero, en caso contrario regresa
     * falso
     */
    public boolean saveGec(RvvdCatGec gec) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            RvvdCatGec gecT = gec.getIdGec() != null ? (RvvdCatGec) session.get(RvvdCatGec.class, gec.getIdGec()) : null;
            if ((gecT != null && gecT.getGecR().equalsIgnoreCase(gec.getGecR())) || getGec(gec.getGecR()) == null) {
                gecT = gecT != null ? gecT : new RvvdCatGec();
                gecT.setIdGec(gec.getIdGec());
                gecT.setGecR(gec.getGecR());
                gecT.setGecEn(gec.getGecEn());
                gecT.setIdUnidadNegocio(gec.getIdUnidadNegocio());
                gecT.setStatus(gec.getStatus());
                session.saveOrUpdate(gecT);
                error = null;
            } else {
                error = "Client type already exists";
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
     * Elimina un gec
     *
     * @param gec Gec a eliminar
     * @return Si la eliminación concluyó con éxito se regresa verdadero, en
     * caso contrario se regresa falso y el error es almacenado en el atributo
     * error
     */
    public boolean deleteGec(RvvdCatGec gec) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query queryNativo;
        List<Object> resValicacion;
        int numOcurrencias = 0;
        boolean flagOk = true;
        RvvdCatGec gecActual = (RvvdCatGec) session.get(RvvdCatGec.class, gec.getIdGec());
        try {
            session.beginTransaction();
            if (gec.getIdGec() != null) {
                queryNativo = session.createSQLQuery("SELECT COUNT(PAIS) FROM RVVD_RECLASIF_UN_GEC WHERE GEC_R = '" + gecActual.getGecR() + "'");
                resValicacion = queryNativo.list();
                for (Object object : resValicacion) {
                    numOcurrencias = Integer.parseInt(object.toString());
                }
                if (numOcurrencias == 0) {
                    session.delete(gecActual);
                    error = null;
                } else {
                    error = "Can not delete the client type, is already used";
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
