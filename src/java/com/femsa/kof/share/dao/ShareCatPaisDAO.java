package com.femsa.kof.share.dao;

import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author TMXIDSJPINAM
 */
public class ShareCatPaisDAO {

    private String error = "";
    private static final String MSG_ERROR_TITULO = "Mensaje de error...";

    /**
     *
     * @return
     */
    public String getError() {
        return error;
    }

    /**
     *
     * @param error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     *
     * @return
     */
    public List<ShareCatPais> getCatPais() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ShareCatPais> countries = null;
        try {
            Query query = session.createQuery("SELECT pais FROM ShareCatPais pais WHERE pais.idstatus = 1");
            countries = query.list();
            error = null;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return countries;
    }

    /**
     *
     * @param nombrePais
     * @return
     */
    public ShareCatPais getCatPais(String nombrePais) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ShareCatPais> countries = null;
        ShareCatPais country = null;
        try {
            Query query = session.createQuery("SELECT pais FROM ShareCatPais pais WHERE pais.nombre = '" + nombrePais.toUpperCase() + "'");
            countries = query.list();
            if (countries != null && !countries.isEmpty()) {
                country = countries.get(0);
            }
            error = null;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return country;
    }

    /**
     *
     * @return
     */
    public List<ShareCatPais> getCatPaisAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ShareCatPais> countries = null;
        try {
            Query query = session.createQuery("SELECT pais FROM ShareCatPais pais");
            countries = query.list();
            error = null;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return countries;
    }

    /**
     *
     * @param pais
     * @return
     */
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
                error = null;
            } else if (getCatPais(pais.getNombre()) == null) {
                query = session.createSQLQuery("CREATE TABLE " + pais.getNombreTabla() + " (FECHA VARCHAR2(50 BYTE), ANIO NUMBER(4,0), "
                        + "MES NUMBER(2,0), TIEMPO NUMBER(6,0), PAIS  VARCHAR2(255 BYTE), "
                        + "UNIDAD_NEGOCIO VARCHAR2(255 BYTE), CANAL VARCHAR2(255 BYTE), "
                        + "SUBCANAL VARCHAR2(255 BYTE), UNIDAD_OPERATIVA VARCHAR2(255 BYTE), "
                        + "REGION VARCHAR2(255 BYTE), ZONA VARCHAR2(255 BYTE), "
                        + "GRUPO_CATEGORIA VARCHAR2(255 BYTE), CATEGORIA VARCHAR2(255 BYTE), "
                        + "FABRICANTE VARCHAR2(255 BYTE), MARCA VARCHAR2(255 BYTE), "
                        + "SABOR VARCHAR2(255 BYTE), TAMANO VARCHAR2(255 BYTE), "
                        + "EMPAQUE VARCHAR2(255 BYTE), RETORNABILIDAD VARCHAR2(255 BYTE), "
                        + "VOLUMEN_MES NUMBER, VENTA_MES NUMBER)");
                query.executeUpdate();
                session.save(pais);
                error = null;
            } else {
                error = "Country already exists";
                flagOk = false;
            }
            session.getTransaction().commit();
        } catch (Exception e) {            
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            flagOk = false;
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getMessage();
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return flagOk;
    }
}
