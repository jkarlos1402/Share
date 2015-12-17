package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatCanales;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.femsa.kof.util.JPAUtil;

public class ShareCatCanalesDAO {
    public List<ShareCatCanales> getCanales(){
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();         
        Query query = em.createQuery("SELECT canales FROM ShareCatCanales canales");
        List<ShareCatCanales> canales = query.getResultList();
        jpau.closeJPAUtil();
        return canales;
    }
}
