package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdReclasifEmpaque;
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
 * Permite reclasificar el catálogo de empaques
 *
 * @author TMXIDSJPINAM
 */
public class ReclasifEmpaqueDAO {

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
     * Obtiene los empaques reclasificados y sin reclasificar por parte del
     * usuario
     *
     * @param usuario El usuario correspondiente
     * @return Regresa una lista con los empaques reclasificados y sin
     * reclasificar por parte del usuario
     */
    public List<RvvdReclasifEmpaque> getReclasifEmpaquesAll(ShareUsuario usuario) {
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
        List<RvvdReclasifEmpaque> empaquesReclasificados = null;
        try {
            Query query = session.createQuery("SELECT re FROM RvvdReclasifEmpaque re WHERE re.pais IN (" + paises + ") ORDER BY re.tipoConsumoR ASC NULLS FIRST,re.tipoConsumoEn ASC NULLS FIRST, re.empaqueR ASC NULLS FIRST, re.empaqueEn ASC NULLS FIRST");
            empaquesReclasificados = query.list();
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
        return empaquesReclasificados;
    }

    /**
     * Actualiza la lista de empaques reclasificados por parte del usuario
     *
     * @param reclasifEmpaques La lista de empaques reclasificados por parte del
     * usuario
     * @return En caso de éxito se regresa verdadero, de lo contrario se regresa
     * falso y el error es almacenado en el atributo error
     */
    public boolean saveReclasifEmpaques(List<RvvdReclasifEmpaque> reclasifEmpaques) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        long cont = 0L;
        try {
            session.beginTransaction();
            for (RvvdReclasifEmpaque reclasifEmpaque : reclasifEmpaques) {
                session.update(reclasifEmpaque);
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
     * Obtiene el número de empaques sin reclasificar por parte del usuario
     *
     * @param usuario El usuario correspondiente
     * @return Regresa el número de empaques sin reclasificar por parte del
     * usuario
     */
    public long checkReclasifEmpaques(ShareUsuario usuario) {
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
            Query query = session.createQuery("SELECT count(re.idReclasifEmpaque) FROM RvvdReclasifEmpaque re WHERE re.pais IN (" + paises + ") AND (re.tipoConsumoR IS NULL OR re.tipoConsumoEn IS NULL OR re.empaqueR IS NULL OR re.empaqueEn IS NULL)");
            List<Object> res = query.list();
            numNotReclass = (Long) res.get(0);
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
        return numNotReclass;
    }
}
