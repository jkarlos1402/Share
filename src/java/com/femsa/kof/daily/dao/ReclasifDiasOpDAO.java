package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdReclasifDiasOp;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ReclasifDiasOpDAO {
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdReclasifDiasOp> getReclasifDiasOpAll(ShareUsuario usuario) {
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
        Query query = session.createQuery("SELECT do FROM RvvdReclasifDiasOp do WHERE do.pais IN (" + paises + ")");
        List<RvvdReclasifDiasOp> diasOpReclasificados = query.list();        
        session.close();
        return diasOpReclasificados;
    }

    public boolean saveReclasifDiasOp(List<RvvdReclasifDiasOp> reclasifDiasOp) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if (reclasifDiasOp != null) {
                for (RvvdReclasifDiasOp reclasifDiaOp : reclasifDiasOp) {
                    session.update(reclasifDiaOp);
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

    public long checkReclasifDiasOp(ShareUsuario usuario) {
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
        Query query = session.createQuery("SELECT count(do.idReclasifDiasOp) FROM RvvdReclasifDiasOp do WHERE do.pais IN (" + paises + ") AND do.fechaR IS NULL");        
        long numNotReclass = ((Number) query.getFirstResult()).longValue();        
        session.close();        
        return numNotReclass;
    }
}
