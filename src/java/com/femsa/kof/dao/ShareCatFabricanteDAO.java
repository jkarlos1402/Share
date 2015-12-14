package com.femsa.kof.dao;

import com.femsa.kof.pojos.ShareCatFabricante;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class ShareCatFabricanteDAO {

    public List<ShareCatFabricante> getFabricantesAll() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT fab FROM ShareCatFabricante fab");
        List<ShareCatFabricante> fabricantes = query.getResultList();
        em.clear();
        em.close();
        return fabricantes;
    }

    public List<ShareCatFabricante> getFabricantes() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT fab FROM ShareCatFabricante fab WHERE fab.status = 1");
        List<ShareCatFabricante> fabricantes = query.getResultList();
        em.clear();
        em.close();
        return fabricantes;
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
            } else {
                em.persist(fabricante);
            }
            et.commit();
        } catch (Exception e) {            
            if (et.isActive()) {
                et.rollback();
            }
            flagOk = false;
        } finally {
            em.clear();
            em.close();
        }
        return flagOk;
    }
}
