package com.femsa.kof.dao;

import com.femsa.kof.pojos.ShareCatCanales;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ShareCatCanalesDAO {
    public List<ShareCatCanales> getCanales(){
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();         
        Query query = em.createQuery("SELECT canales FROM ShareCatCanales canales");
        List<ShareCatCanales> canales = query.getResultList();       
        em.close();
        return canales;
    }
}
