package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatCategoriaOficial;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Permite la manipulacion de las categorias oficiales
 *
 * @author TMXIDSJPINAM
 */
public class CatCategoriaOficialDAO {

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
     * Obtiene una lista con las categorias oficiales sin importar su estaus
     *
     * @return Regresa una lista con la totalidad de categorias oficiales
     */
    public List<RvvdCatCategoriaOficial> getCategoriasOficialesAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatCategoriaOficial> categoriasOficiales = null;
        try {
            Query query = session.createQuery("SELECT co FROM RvvdCatCategoriaOficial co ORDER BY co.categoriaOficial ASC");
            categoriasOficiales = query.list();
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
        return categoriasOficiales;
    }

    /**
     * Obtiene las categorias oficiales donde el estatus es 1
     *
     * @return Regresa una lista con las categorias oficiales filtradas
     */
    public List<RvvdCatCategoriaOficial> getCategoriasOficiales() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatCategoriaOficial> categoriasOficiales = null;
        try {
            Query query = session.createQuery("SELECT co FROM RvvdCatCategoriaOficial co WHERE co.status = 1 ORDER BY co.categoriaOficial ASC");
            categoriasOficiales = query.list();
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
        return categoriasOficiales;
    }

    /**
     * Obtiene una categoria oficial en específico
     *
     * @param id El identificador de la categoria oficial a buscar
     * @return La categoria oficial buscada, en caso de no existir se regresa
     * nulo
     */
    public RvvdCatCategoriaOficial getCategoriaOficial(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatCategoriaOficial categOficial = null;
        try {
            categOficial = (RvvdCatCategoriaOficial) session.get(RvvdCatCategoriaOficial.class, id);
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
        return categOficial;
    }

    /**
     * Obtiene una categoria oficial en específico
     *
     * @param categoria El nombre de la categoria oficial a buscar
     * @return La categoria oficial buscada, en caso de no existir se regresa
     * nulo
     */
    public RvvdCatCategoriaOficial getCategoriaOficial(String categoria) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatCategoriaOficial categOficial = null;
        try {
            Query query = session.createQuery("SELECT co FROM RvvdCatCategoriaOficial co WHERE co.categoriaOficial = '" + categoria.toUpperCase() + "' OR co.categoriaOficialEn = '" + categoria.toUpperCase() + "'");
            List<RvvdCatCategoriaOficial> categoriasOficiales = query.list();
            if (categoriasOficiales != null && !categoriasOficiales.isEmpty()) {
                categOficial = categoriasOficiales.get(0);
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
        return categOficial;
    }

    /**
     * Guarda o actuliza una categoria oficial
     *
     * @param catCategoriaOficial La categoria oficial a ser guardada o
     * actualizada
     * @return En caso de éxito se regresa verdadero, en caso oontrario se
     * regresa falso y el error es almacenado en el atributo error
     */
    public boolean saveCategoriaOficial(RvvdCatCategoriaOficial catCategoriaOficial) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if (getCategoriaOficial(catCategoriaOficial.getCategoriaOficial()) == null) {
                session.saveOrUpdate(catCategoriaOficial);
                error = null;
            } else {
                error = "Official category already exists";
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

    /**
     * Elimina una categoria oficial
     *
     * @param categoriaOficial Categoria oficial a eliminar
     * @return Si la eliminación concluyó con éxito se regresa verdadero, en
     * caso contrario se regresa falso y el error es almacenado en el atributo
     * error
     */
    public boolean deleteCategoriaOficial(RvvdCatCategoriaOficial categoriaOficial) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query queryNativo;
        List<Object> resValicacion;
        int numOcurrencias = 0;
        boolean flagOk = true;
        RvvdCatCategoriaOficial categoriaOficialActual = (RvvdCatCategoriaOficial) session.get(RvvdCatCategoriaOficial.class, categoriaOficial.getIdCategoriaOficial());
        try {
            session.beginTransaction();
            if (categoriaOficial.getIdCategoriaOficial() != null) {
                queryNativo = session.createSQLQuery("SELECT COUNT(PAIS) FROM RVVD_RECLASIF_CATEGORIA WHERE CATEGORIA_OFICIAL_R = '" + categoriaOficialActual.getCategoriaOficial() + "'");
                resValicacion = queryNativo.list();
                for (Object object : resValicacion) {
                    numOcurrencias = Integer.parseInt(object.toString());
                }
                if (numOcurrencias == 0) {
                    session.delete(categoriaOficialActual);
                    error = null;
                } else {
                    error = "Can not delete the official category, is already used";
                    flagOk = false;
                }
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
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
