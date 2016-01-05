package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdReclasifUnGec;
import com.femsa.kof.daily.util.CheckCatalogs;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ReclasifGecDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

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
        Query query = session.createQuery("SELECT rug FROM RvvdReclasifUnGec rug WHERE rug.pais IN (" + paises + ")");
        List<RvvdReclasifUnGec> unGecs = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return unGecs;
    }

    public boolean saveReclasifUnGec(List<RvvdReclasifUnGec> unGecs) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        long cont = 0L;
        try {
            session.beginTransaction();
            if (unGecs != null) {
                for (RvvdReclasifUnGec unGec : unGecs) {
                    session.update(unGec);
                    if (cont % 100 == 0) {
                        session.flush();
                        session.clear();
                    }
                    cont++;
                }
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
        Query query = session.createQuery("SELECT count(rug.idReclasifUnGec) FROM RvvdReclasifUnGec rug WHERE rug.pais IN (" + paises + ") AND (rug.gecR IS NULL OR rug.gecEn IS NULL OR rug.unidadNegocioR IS NULL OR rug.unidadNegocioEn IS NULL)");
        List<Object> res = query.list();
        long numNotReclass = (Long)res.get(0);  
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return numNotReclass;
    }
}
