package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ShareCatPaisDAO {

    private String error = "";

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ShareCatPais> getCatPais() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT pais FROM ShareCatPais pais WHERE pais.idstatus = 1");
        List<ShareCatPais> countries = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return countries;
    }

    public ShareCatPais getCatPais(String nombrePais) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT pais FROM ShareCatPais pais WHERE pais.nombre = '" + nombrePais.toUpperCase() + "'");
        List<ShareCatPais> countries = query.list();
        ShareCatPais country = null;
        if (countries.size() > 0) {
            country = countries.get(0);
        }
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return country;
    }

    public List<ShareCatPais> getCatPaisAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT pais FROM ShareCatPais pais");
        List<ShareCatPais> countries = query.list();
        session.flush();
        session.clear();
        session.close();
        hibernateUtil.closeSessionFactory();
        return countries;
    }

    public boolean savePais(ShareCatPais pais) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = null;
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if (pais.getPkPais() != null) {
                session.update(pais);
            } else if (getCatPais(pais.getNombre()) == null) {
                query = session.createSQLQuery("CREATE TABLE " + pais.getNombreTabla() + " (PAIS VARCHAR2(50 BYTE),CANAL VARCHAR2(50 BYTE), "
                        + "FECHA VARCHAR2(50 BYTE), GRUPO_CATEGORIA VARCHAR2(50 BYTE), CATEGORIA VARCHAR2(50 BYTE), "
                        + "FABRICANTE VARCHAR2(100 BYTE), VOLUMEN_MES NUMBER, VENTA_MES NUMBER)");
                query.executeUpdate();
                session.save(pais);
            } else {
                error = "Country already exists";
                flagOk = false;
            }
            session.getTransaction().commit();
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
}
