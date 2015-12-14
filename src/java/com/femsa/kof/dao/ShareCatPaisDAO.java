package com.femsa.kof.dao;

import com.femsa.kof.pojos.ShareCatPais;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class ShareCatPaisDAO {

    private String error = "";

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ShareCatPais> getCatPais() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT pais FROM ShareCatPais pais WHERE pais.idstatus = 1");
        List<ShareCatPais> countries = query.getResultList();
        em.clear();
        em.close();
        return countries;
    }

    public ShareCatPais getCatPais(String nombrePais) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT pais FROM ShareCatPais pais WHERE pais.nombre = " + nombrePais.toUpperCase() + " AND pais.idstatus = 1");
        ShareCatPais country = (ShareCatPais) query.getSingleResult();
        em.clear();
        em.close();
        return country;
    }

    public List<ShareCatPais> getCatPaisAll() {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        Query query = em.createQuery("SELECT pais FROM ShareCatPais pais");
        List<ShareCatPais> countries = query.getResultList();
        em.clear();
        em.close();
        return countries;
    }

    public boolean savePais(ShareCatPais pais) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        EntityTransaction et = em.getTransaction();
        Query query = null;
        boolean flagOk = true;
        try {
            et.begin();
            if (getCatPais(pais.getNombre()) == null) {
                if (pais.getPkPais() != null) {
                    em.merge(pais);
                } else {
                    query = em.createNativeQuery("CREATE TABLE " + pais.getNombreTabla() + " (PAIS VARCHAR2(50 BYTE),CANAL VARCHAR2(50 BYTE), "
                            + "FECHA VARCHAR2(50 BYTE), GRUPO_CATEGORIA VARCHAR2(50 BYTE), CATEGORIA VARCHAR2(50 BYTE), "
                            + "FABRICANTE VARCHAR2(100 BYTE), VOLUMEN_MES NUMBER, VENTA_MES NUMBER)");
                    query.executeUpdate();
                    em.persist(pais);
                }
            }else{
                error = "Country already exists";
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
            em.clear();
            em.close();
        }
        return flagOk;
    }
}
