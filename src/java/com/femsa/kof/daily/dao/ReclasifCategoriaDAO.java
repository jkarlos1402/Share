package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatCategoriaOficial;
import com.femsa.kof.daily.pojos.RvvdReclasifCategoria;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.JPAUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class ReclasifCategoriaDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdReclasifCategoria> getReclasifCategoriasAll(ShareUsuario usuario) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        List<String> paises = new ArrayList<String>();
        for (int i = 0; i < usuario.getPaises().size(); i++) {
            paises.add(usuario.getPaises().get(i).getClaveCorta());
        }
        Query query = em.createQuery("SELECT rc FROM RvvdReclasifCategoria rc WHERE rc.pais IN :paises");
        query.setParameter("paises", paises);
        List<RvvdReclasifCategoria> categoriasReclasificadas = (List<RvvdReclasifCategoria>) query.getResultList();
        jpau.closeJPAUtil();
        return categoriasReclasificadas;
    }

    public boolean saveReclasifCategorias(List<RvvdReclasifCategoria> reclasifCategorias) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean flagOk = true;
        try {
            et.begin();
            if (reclasifCategorias != null) {
                for (RvvdReclasifCategoria reclasifCategoria : reclasifCategorias) {
                    em.merge(reclasifCategoria);
                }
            }
            et.commit();
        } catch (Exception e) {
            error = e.getMessage();
            if (et.isActive()) {
                et.rollback();
            }
            flagOk = false;
        } finally {
            jpau.closeJPAUtil();
        }
        return flagOk;
    }

    public long checkReclasifCategorias(ShareUsuario usuario) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        List<String> paises = new ArrayList<String>();
        for (int i = 0; i < usuario.getPaises().size(); i++) {
            paises.add(usuario.getPaises().get(i).getClaveCorta());
        }
        Query query = em.createQuery("SELECT count(rc.idReclasifCategoria) FROM RvvdReclasifCategoria rc WHERE rc.pais IN :paises AND (rc.categoriaR IS NULL OR rc.categoriaEn IS NULL OR rc.categoriaOficialR IS NULL OR rc.categoriaOficialEn IS NULL)");
        query.setParameter("paises", paises);
        long numNotReclass = ((Number) query.getSingleResult()).longValue();
        jpau.closeJPAUtil();
        return numNotReclass;
    }
}
