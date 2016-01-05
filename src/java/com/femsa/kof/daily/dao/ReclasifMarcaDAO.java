package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdReclasifMarca;
import com.femsa.kof.daily.util.CheckCatalogs;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ReclasifMarcaDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdReclasifMarca> getReclasifMarcasAll(ShareUsuario usuario) {
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
        Query query = session.createQuery("SELECT rm FROM RvvdReclasifMarca rm WHERE rm.pais IN (" + paises + ")");
        List<RvvdReclasifMarca> marcas = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return marcas;
    }

    public boolean saveReclasifMarcas(List<RvvdReclasifMarca> marcas) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        long cont = 0L;
        try {
            session.beginTransaction();
            if (marcas != null) {
                for (RvvdReclasifMarca marca : marcas) {
                    session.update(marca);
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

    public long checkReclasifMarcas(ShareUsuario usuario) {
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
        Query query = session.createQuery("SELECT count(rm.idReclasifMarca) FROM RvvdReclasifMarca rm WHERE rm.pais IN (" + paises + ") AND (rm.contenidoCaloricoR IS NULL OR rm.contenidoCaloricoEn IS NULL OR rm.marcaR IS NULL OR rm.marcaEn IS NULL)");
        List<Object> res = query.list();
        long numNotReclass = (Long)res.get(0);  
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return numNotReclass;
    }
}
