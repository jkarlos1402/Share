package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatFabricante;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import com.femsa.kof.util.JPAUtil;

public class ShareCatFabricanteDAO {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ShareCatFabricante> getFabricantesAll() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT fab FROM ShareCatFabricante fab");
        List<ShareCatFabricante> fabricantes = query.getResultList();
        jpau.closeJPAUtil();
        return fabricantes;
    }

    public List<ShareCatFabricante> getFabricantes() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT fab FROM ShareCatFabricante fab WHERE fab.status = 1");
        List<ShareCatFabricante> fabricantes = query.getResultList();
        jpau.closeJPAUtil();
        return fabricantes;
    }

    public ShareCatFabricante getFabricante(String name) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT fab FROM ShareCatFabricante fab WHERE fab.fabricante = '" + name.toUpperCase() + "'");
        List<ShareCatFabricante> fabricantes = query.getResultList();
        ShareCatFabricante fabricante = null;
        if(fabricantes.size() > 0){
            fabricante = fabricantes.get(0);
        }
        jpau.closeJPAUtil();
        return fabricante;
    }

    public boolean saveFabricante(ShareCatFabricante fabricante) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        EntityTransaction et = em.getTransaction();
        boolean flagOk = true;
        try {
            et.begin();
            if (fabricante.getPkFabricante() != null) {
                em.merge(fabricante);
            } else if(getFabricante(fabricante.getFabricante()) == null){
                em.persist(fabricante);
            }else{
                error = "Manufacturer already exists";
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
