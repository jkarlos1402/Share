package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdReclasifEmpaque;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ReclasifEmpaqueDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdReclasifEmpaque> getReclasifEmpaquesAll(ShareUsuario usuario) {
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
        Query query = session.createQuery("SELECT re FROM RvvdReclasifEmpaque re WHERE re.pais IN (" + paises + ")");
        List<RvvdReclasifEmpaque> empaquesReclasificados = query.list();        
        session.close();
        return empaquesReclasificados;
    }

    public boolean saveReclasifEmpaques(List<RvvdReclasifEmpaque> reclasifEmpaques) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if (reclasifEmpaques != null) {
                for (RvvdReclasifEmpaque reclasifEmpaque : reclasifEmpaques) {
                    session.update(reclasifEmpaque);
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

    public long checkReclasifEmpaques(ShareUsuario usuario) {
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
        Query query = session.createQuery("SELECT count(re.idReclasifEmpaque) FROM RvvdReclasifEmpaque re WHERE re.pais IN (" + paises + ") AND (re.tipoConsumoR IS NULL OR re.tipoConsumoEn IS NULL OR re.empaqueR IS NULL OR re.empaqueEn IS NULL)");        
        long numNotReclass = ((Number) query.getFirstResult()).longValue();        
        session.close();        
        return numNotReclass;
    }
}
