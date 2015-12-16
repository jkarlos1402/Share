package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatCategoriaOficial;
import com.femsa.kof.util.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class CatCategoriaOficialDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<RvvdCatCategoriaOficial> getCategoriasOficialesAll() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT co FROM RvvdCatCategoriaOficial co");
        List<RvvdCatCategoriaOficial> categoriasOficiales = (List<RvvdCatCategoriaOficial>) query.getResultList();
        jpau.closeJPAUtil();
        return categoriasOficiales;
    }

    public List<RvvdCatCategoriaOficial> getCategoriasOficiales() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT co FROM RvvdCatCategoriaOficial co WHERE co.status = 1");
        List<RvvdCatCategoriaOficial> categoriasOficiales = (List<RvvdCatCategoriaOficial>) query.getResultList();
        jpau.closeJPAUtil();
        return categoriasOficiales;
    }

    public RvvdCatCategoriaOficial getCategoriaOficial(Integer id) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        RvvdCatCategoriaOficial categOficial = em.find(RvvdCatCategoriaOficial.class, id);
        jpau.closeJPAUtil();
        return categOficial;
    }

    public RvvdCatCategoriaOficial getCategoriaOficial(String categoria) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT co FROM RvvdCatCategoriaOficial co WHERE co.categoriaOficial = '" + categoria.toUpperCase() + "'");
        List<RvvdCatCategoriaOficial> categoriasOficiales = (List<RvvdCatCategoriaOficial>) query.getResultList();
        RvvdCatCategoriaOficial categOficial = null;
        if(categoriasOficiales.size() > 0){
            categOficial = categoriasOficiales.get(0);
        }
        jpau.closeJPAUtil();
        return categOficial;
    }
    
    public boolean saveCategoriaOficial(RvvdCatCategoriaOficial catCategoriaOficial){
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean flagOk = true;
        try {
            et.begin();
            if (catCategoriaOficial.getIdCategoriaOficial() != null) {
                em.merge(catCategoriaOficial);
            } else if (getCategoriaOficial(catCategoriaOficial.getCategoriaOficial()) == null) {
                em.persist(catCategoriaOficial);
            } else {
                error = "Official category already exists";
                flagOk = false;
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
}
