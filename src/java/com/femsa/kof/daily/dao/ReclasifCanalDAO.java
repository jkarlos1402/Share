package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdReclasifCanal;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ReclasifCanalDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdReclasifCanal> getReclasifCanalesAll(ShareUsuario usuario) {
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
        Query query = session.createQuery("SELECT rc FROM RvvdReclasifCanal rc WHERE rc.pais IN (" + paises + ")");
        List<RvvdReclasifCanal> canalesReclasificados = query.list();        
        session.close();
        return canalesReclasificados;
    }

    public boolean saveReclasifCanales(List<RvvdReclasifCanal> reclasifCanales) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if (reclasifCanales != null) {
                for (RvvdReclasifCanal reclasifCanal : reclasifCanales) {
                    session.saveOrUpdate(reclasifCanal);
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

    public long checkReclasifCanales(ShareUsuario usuario) {
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
        Query query = session.createQuery("SELECT count(rc.idReclasifCanal) FROM RvvdReclasifCanal rc WHERE rc.pais IN ("+paises+") AND (rc.canalR IS NULL OR rc.canalEn IS NULL)");       
        long numNotReclass = ((Number) query.getFirstResult()).longValue();       
        session.close();
        return numNotReclass;
    }
}
