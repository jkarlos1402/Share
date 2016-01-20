package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdReclasifCategoria;
import com.femsa.kof.daily.util.CheckCatalogs;
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
        List<RvvdReclasifCategoria> categoriasReclasificadas = null;
        try {
            Query query = session.createQuery("SELECT rc FROM RvvdReclasifCategoria rc WHERE rc.pais IN (" + paises + ") ORDER BY rc.categoriaR DESC,rc.categoriaEn DESC,rc.categoriaOficialR DESC,rc.categoriaOficialEn DESC");
            categoriasReclasificadas = query.list();
            error = null;
        } catch (Exception e) {
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return categoriasReclasificadas;
    }

    public boolean saveReclasifCategorias(List<RvvdReclasifCategoria> reclasifCategorias) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        long cont = 0L;
        try {
            session.beginTransaction();
            if (reclasifCategorias != null) {
                for (RvvdReclasifCategoria reclasifCategoria : reclasifCategorias) {
                    session.saveOrUpdate(reclasifCategoria);
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
        long numNotReclass = 0L;
        try {
            Query query = session.createQuery("SELECT count(rc.idReclasifCategoria) FROM RvvdReclasifCategoria rc WHERE rc.pais IN (" + paises + ") AND (rc.categoriaR IS NULL OR rc.categoriaEn IS NULL OR rc.categoriaOficialR IS NULL OR rc.categoriaOficialEn IS NULL)");
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
