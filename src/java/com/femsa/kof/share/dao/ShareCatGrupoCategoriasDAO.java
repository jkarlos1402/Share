package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatGrupoCategorias;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import com.femsa.kof.util.JPAUtil;

public class ShareCatGrupoCategoriasDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ShareCatGrupoCategorias> getCategoryGroups() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT gc FROM ShareCatGrupoCategorias gc WHERE gc.status = 1");
        List<ShareCatGrupoCategorias> grupos = (List<ShareCatGrupoCategorias>) query.getResultList();
        jpau.closeJPAUtil();
        return grupos;
    }

    public ShareCatGrupoCategorias getCategoryGroup(String name) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT gc FROM ShareCatGrupoCategorias gc WHERE gc.grupoCategoria = '" + name.toUpperCase() + "'");
        List<ShareCatGrupoCategorias> grupos = (List<ShareCatGrupoCategorias>) query.getResultList();
        ShareCatGrupoCategorias grupo = null;
        if (grupos.size() > 0) {
            grupo = grupos.get(0);
        }
        jpau.closeJPAUtil();
        return grupo;
    }

    public List<ShareCatGrupoCategorias> getCategoryGroupsAll() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT gc FROM ShareCatGrupoCategorias gc");
        List<ShareCatGrupoCategorias> grupos = (List<ShareCatGrupoCategorias>) query.getResultList();
        jpau.closeJPAUtil();
        return grupos;
    }

    public boolean saveGroupCategory(ShareCatGrupoCategorias grupoCategorias) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean flagOk = true;
        try {
            et.begin();
            if (grupoCategorias.getPkGrupoCategoria() != null) {
                em.merge(grupoCategorias);
            } else if (getCategoryGroup(grupoCategorias.getGrupoCategoria()) == null) {
                em.persist(grupoCategorias);
            } else {
                error = "Group category already exists";
                flagOk = false;
            }
            et.commit();
        } catch (Exception e) {
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
