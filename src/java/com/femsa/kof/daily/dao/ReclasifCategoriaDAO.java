package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdReclasifCategoria;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ReclasifCategoriaDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdReclasifCategoria> getReclasifCategoriasAll(ShareUsuario usuario) {
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
        Query query = session.createQuery("SELECT rc FROM RvvdReclasifCategoria rc WHERE rc.pais IN (" + paises + ")");
        List<RvvdReclasifCategoria> categoriasReclasificadas = query.list();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return categoriasReclasificadas;
    }

    public boolean saveReclasifCategorias(List<RvvdReclasifCategoria> reclasifCategorias) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if (reclasifCategorias != null) {
                for (RvvdReclasifCategoria reclasifCategoria : reclasifCategorias) {
                    session.saveOrUpdate(reclasifCategoria);
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
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return flagOk;
    }

    public long checkReclasifCategorias(ShareUsuario usuario) {
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
        Query query = session.createQuery("SELECT count(rc.idReclasifCategoria) FROM RvvdReclasifCategoria rc WHERE rc.pais IN (" + paises + ") AND (rc.categoriaR IS NULL OR rc.categoriaEn IS NULL OR rc.categoriaOficialR IS NULL OR rc.categoriaOficialEn IS NULL)");
        long numNotReclass = ((Number) query.getFirstResult()).longValue();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return numNotReclass;
    }
}
