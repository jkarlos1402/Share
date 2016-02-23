package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdReclasifCanal;
import com.femsa.kof.daily.pojos.RvvdReclasifZona;
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
 * Permite la reclasificación del catálogo zona
 *
 * @author TMXIDSJPINAM
 */
public class ReclasifZonaDAO {

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
     * Obtiene la lista completa de Zonas reclasificadas y sin reclasificar de
     * acuerdo al usuario
     *
     * @param usuario El usuario que realiza la búsqueda
     * @return Una lista con las zonas reclasificadas y sin reclasificar
     * pertenecientes al usuario
     */
    public List<RvvdReclasifZona> getReclasifZonasAll(ShareUsuario usuario) {
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
        List<RvvdReclasifZona> zonasReclasificadas = null;
        try {
            Query query = session.createQuery("SELECT rz FROM RvvdReclasifZona rz WHERE rz.pais IN (" + paises + ") ORDER BY rz.zonaR DESC,rz.zonaEn DESC");
            zonasReclasificadas = query.list();
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
        return zonasReclasificadas;
    }

    /**
     * Actualiza la lista de zonas reclasificadas con los cambios realizados
     * por el usuario
     *
     * @param reclasifZonas La lista actualizada de zonas reclasificadas
     * @return En caso de éxito se regresa verdadero, en caso contrario se
     * regresa falso, y el error es almacenado en el atributo error
     */
    public boolean saveReclasifZonas(List<RvvdReclasifZona> reclasifZonas) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        long cont = 0L;
        try {
            session.beginTransaction();
            for (RvvdReclasifZona reclasifZona : reclasifZonas) {
                session.saveOrUpdate(reclasifZona);
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
     * Obtiene el número de zonas sin reclasificar por el usuario
     *
     * @param usuario El usuario correspondiente
     * @return Regresa el número de canales sin reclasificar por parte del
     * usuario
     */
    public long checkReclasifZonas(ShareUsuario usuario) {
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
            Query query = session.createQuery("SELECT count(rz.idReclasifZona) FROM RvvdReclasifZona rz WHERE rz.pais IN (" + paises + ") AND (rz.zonaR IS NULL OR rz.zonaEn IS NULL)");
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
