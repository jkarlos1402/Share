package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdReclasifDiasOp;
import com.femsa.kof.daily.util.CheckCatalogs;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author TMXIDSJPINAM
 */
public class ReclasifDiasOpDAO {

    private String error;

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
     *
     * @param usuario
     * @return
     */
    public List<RvvdReclasifDiasOp> getReclasifDiasOpAll(ShareUsuario usuario) {
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
        List<RvvdReclasifDiasOp> diasOpReclasificados = null;
        try {
            Query query = session.createQuery("SELECT do FROM RvvdReclasifDiasOp do WHERE do.pais IN (" + paises + ") ORDER BY do.fechaR DESC");
            diasOpReclasificados = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return diasOpReclasificados;
    }

    /**
     *
     * @param reclasifDiasOp
     * @return
     */
    public boolean saveReclasifDiasOp(List<RvvdReclasifDiasOp> reclasifDiasOp) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        long cont = 0L;
        try {
            session.beginTransaction();
            if (reclasifDiasOp != null) {
                for (RvvdReclasifDiasOp reclasifDiaOp : reclasifDiasOp) {
                    session.update(reclasifDiaOp);
                    if (cont % 100 == 0) {
                        session.flush();
                        session.clear();
                    }
                    cont++;
                }
                error = null;
            }
            session.getTransaction().commit();
            CheckCatalogs.checkAllCatalogs();
        } catch (Exception e) {
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
     *
     * @param usuario
     * @return
     */
    public long checkReclasifDiasOp(ShareUsuario usuario) {
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
            Query query = session.createQuery("SELECT count(do.idReclasifDiasOp) FROM RvvdReclasifDiasOp do WHERE do.pais IN (" + paises + ") AND do.fechaR IS NULL");
            List<Object> res = query.list();
            numNotReclass = (Long) res.get(0);
            error = null;
        } catch (Exception e) {
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
