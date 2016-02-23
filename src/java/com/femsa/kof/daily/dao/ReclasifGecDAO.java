package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdReclasifUnGec;
import com.femsa.kof.daily.util.CheckCatalogs;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Permite la reclasificacion del catálogo de tipo de cliente
 *
 * @author TMXIDSJPINAM
 */
public class ReclasifGecDAO {

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
     * Obtiene los tipos de cliente reclasificados y sin reclasificar por parte
     * del usuario
     *
     * @param usuario El usuario correspondiente
     * @return Regresa una lista de tipos de clientes reclasificados y sin
     * reclasificar por parte del usuario
     */
    public List<RvvdReclasifUnGec> getReclasifUnGecAll(ShareUsuario usuario) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String paises = "";
        for (int i = 0; i < usuario.getPaises().size(); i++) {
            if (i > 0) {
                paises += ",'" + (usuario.getPaises().get(i).getClaveCorta()) + "'";
            } else {
                paises = "'" + (usuario.getPaises().get(i).getClaveCorta()) + "'";
            }
        }
        List<RvvdReclasifUnGec> unGecs = null;
        try {
            Query query = session.createQuery("SELECT rug FROM RvvdReclasifUnGec rug WHERE rug.pais IN (" + paises + ")");
            unGecs = query.list();
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
        return unGecs;
    }

    /**
     * Actualiza la lista de tipos de clientes reclasificados por parte del
     * usuario
     *
     * @param unGecs La lista de tipos de clientes reclasificados por el cliente
     * @return En caso de éxito se regresa verdadero, de lo contrario se regresa
     * falso y el error es almacenado en el atributo error
     */
    public boolean saveReclasifUnGec(List<RvvdReclasifUnGec> unGecs) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        long cont = 0L;
        try {
            session.beginTransaction();
            for (RvvdReclasifUnGec unGec : unGecs) {
                session.update(unGec);
                if (cont % 100 == 0) {
                    session.flush();
                    session.clear();
                }
                cont++;
            }
            error = null;
            session.getTransaction().commit();
            CheckCatalogs.checkAllCatalogs();
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
     * Obtiene el número de tipos de cliente sin reclasificar por el usuario
     *
     * @param usuario El usuario correspondiente
     * @return Regresa el número de tipos de cliente sin reclasificar por el
     * usuario
     */
    public long checkReclasifUnGec(ShareUsuario usuario) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String paises = "";
        for (int i = 0; i < usuario.getPaises().size(); i++) {
            if (i > 0) {
                paises += ",'" + (usuario.getPaises().get(i).getClaveCorta()) + "'";
            } else {
                paises = "'" + (usuario.getPaises().get(i).getClaveCorta()) + "'";
            }
        }
        long numNotReclass = 0L;
        try {
            Query query = session.createQuery("SELECT count(rug.idReclasifUnGec) FROM RvvdReclasifUnGec rug WHERE rug.pais IN (" + paises + ") AND (rug.gecR IS NULL OR rug.gecEn IS NULL OR rug.unidadNegocioR IS NULL OR rug.unidadNegocioEn IS NULL)");
            List<Object> res = query.list();
            numNotReclass = (Long) res.get(0);
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
        return numNotReclass;
    }
}
