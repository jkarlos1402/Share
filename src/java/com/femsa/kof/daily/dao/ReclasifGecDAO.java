package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdReclasifUnGec;
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
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String paises = "";
        for (int i = 0; i < usuario.getPaises().size(); i++) {
            if(i > 0){
                paises+=",'"+(usuario.getPaises().get(i).getClaveCorta())+"'";
            } else{
                paises = "'"+(usuario.getPaises().get(i).getClaveCorta())+"'";
            }           
        }
        Query query = session.createQuery("SELECT rug FROM RvvdReclasifUnGec rug WHERE rug.pais IN (" + paises + ")");
        List<RvvdReclasifUnGec> unGecs = query.list();        
        session.close();
        return unGecs;
    }

    public boolean saveReclasifUnGec(List<RvvdReclasifUnGec> unGecs) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if (unGecs != null) {
                for (RvvdReclasifUnGec unGec : unGecs) {
                    session.update(unGec);
                }
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            error = e.getMessage();
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            flagOk = false;
        } finally {            
            session.close();
        }
        return flagOk;
    }

    public long checkReclasifUnGec(ShareUsuario usuario) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
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
        long numNotReclass = ((Number) query.getFirstResult()).longValue();        
        session.close();       
        return numNotReclass;
    }
}
