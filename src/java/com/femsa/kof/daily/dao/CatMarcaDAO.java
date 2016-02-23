package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatMarca;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Permite manipular el catálogo de marca
 *
 * @author TMXIDSJPINAM
 */
public class CatMarcaDAO {

    private String error;
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
     * Obtiene la lista de marcas sin importar el estatus
     *
     * @return La lista de marcas sin filtrar
     */
    public List<RvvdCatMarca> getMarcasAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatMarca> marcas = null;
        try {
            Query query = session.createQuery("SELECT m FROM RvvdCatMarca m");
            marcas = query.list();
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
        return marcas;
    }

    /**
     * Obtiene las marcas donde el estatus es igual a 1
     *
     * @return Regresa una lista con las marcas filtradas
     */
    public List<RvvdCatMarca> getMarcas() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatMarca> marcas = null;
        try {
            Query query = session.createQuery("SELECT m FROM RvvdCatMarca m WHERE m.status = 1");
            marcas = query.list();
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
        return marcas;
    }

    /**
     * Obtiene una marca en específico
     *
     * @param id EL identificador de la marca a buscar
     * @return La marca buscada, en caso de no existir se regresa nulo
     */
    public RvvdCatMarca getMarca(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatMarca marca = null;
        try {
            marca = (RvvdCatMarca) session.get(RvvdCatMarca.class, id);
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
        return marca;
    }

    /**
     * Obtiene una marca en específico
     *
     * @param marca El nombre de la marca a buscar
     * @return La marca buscada, en caso de no existir se regresa nulo
     */
    public RvvdCatMarca getMarca(String marca) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatMarca marcaT = null;
        try {
            Query query = session.createQuery("SELECT m FROM RvvdCatMarca m WHERE m.marcaR = '" + marca.toUpperCase() + "' OR m.marcaEn = '" + marca.toUpperCase() + "'");
            List<RvvdCatMarca> marcas = query.list();
            if (marcas != null && !marcas.isEmpty()) {
                marcaT = marcas.get(0);
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
        return marcaT;
    }

    /**
     * Guarda o actuliza una marca
     *
     * @param marca La marca a guardar o actualizar
     * @return En caso de éxito se regresa verdadero, en caso contrario se
     * regresa falso y se almacena el error en el atributo error
     */
    public boolean saveMarca(RvvdCatMarca marca) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if ((marca.getIdMarca() == null ? getMarca(marca.getMarcaR()) : null) == null) {
                session.saveOrUpdate(marca);
                error = null;
            } else {
                error = "Brand already exists";
                flagOk = false;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            error = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
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
