package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatCategoria;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Permite la manipulacion de categorias correspondientes a Daily Dashboard
 *
 * @author TMXIDSJPINAM
 */
public class CatCategoriaDAO {

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
     * Obtiene una lista con la totalidad de categorias sin importar su estatus
     *
     * @return Regresa una lista con las categorias sin aplicar algun filtro
     */
    public List<RvvdCatCategoria> getCategoriasAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatCategoria> categorias = null;
        try {
            Query query = session.createQuery("SELECT c FROM RvvdCatCategoria c ORDER BY c.categoria ASC");
            categorias = query.list();
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
        return categorias;
    }

    /**
     * Obtiene el catálogo de categorias donde el estatus es 1
     *
     * @return Regresa una lista con las categorias filtradas
     */
    public List<RvvdCatCategoria> getCategorias() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatCategoria> categorias = null;
        try {
            Query query = session.createQuery("SELECT c FROM RvvdCatCategoria c WHERE c.status = 1 ORDER BY c.categoria ASC");
            categorias = query.list();
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
        return categorias;
    }

    /**
     * Obtiene una categoria en específico
     *
     * @param id El identificador de la categoria
     * @return La categoria buscada, en caso de no existir se regresa nulo
     */
    public RvvdCatCategoria getCategoria(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatCategoria categ = null;
        try {
            categ = (RvvdCatCategoria) session.get(RvvdCatCategoria.class, id);
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
        return categ;
    }

    /**
     * Obtiene una categoria en específico
     *
     * @param categoria El nombre de la categoria a buscar
     * @return La categoria buscada, en caso de no existir se regresa nulo
     */
    public RvvdCatCategoria getCategoria(String categoria) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatCategoria categ = null;
        try {
            Query query = session.createQuery("SELECT c FROM RvvdCatCategoria c WHERE c.categoria = '" + categoria.toUpperCase() + "' OR c.categoriaEn = '" + categoria.toUpperCase() + "'");
            List<RvvdCatCategoria> categorias = query.list();
            if (categorias != null && !categorias.isEmpty()) {
                categ = categorias.get(0);
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
        return categ;
    }

    /**
     * Guarda o actualiza una categoria
     *
     * @param catCategoria la categoria a ser guardada o actualizada
     * @return En caso de éxito se regresa verdadero, en caso contrario se
     * regresa falso y el error es almacenado en el atributo error
     */
    public boolean saveCategoria(RvvdCatCategoria catCategoria) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            RvvdCatCategoria catCategoriaT = catCategoria.getIdCategoria() != null ? (RvvdCatCategoria) session.get(RvvdCatCategoria.class, catCategoria.getIdCategoria()) : null;
            if ((catCategoriaT != null && catCategoriaT.getCategoria().equalsIgnoreCase(catCategoria.getCategoria())) || getCategoria(catCategoria.getCategoria()) == null) {
                catCategoriaT = catCategoriaT != null ? catCategoriaT : new RvvdCatCategoria();
                catCategoriaT.setIdCategoria(catCategoria.getIdCategoria());
                catCategoriaT.setCategoria(catCategoria.getCategoria());
                catCategoriaT.setCategoriaEn(catCategoria.getCategoriaEn());
                catCategoriaT.setIdCategoriaOficial(catCategoria.getIdCategoriaOficial());
                catCategoriaT.setStatus(catCategoria.isStatus());
                session.saveOrUpdate(catCategoriaT);
                error = null;
            } else {
                error = "Category already exists";
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
     * Elimina una categoria
     *
     * @param categoria Categoria a eliminar
     * @return Si la eliminación concluyó con éxito se regresa verdadero, en
     * caso contrario se regresa falso y el error es almacenado en el atributo
     * error
     */
    public boolean deleteCategoria(RvvdCatCategoria categoria) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query queryNativo;
        List<Object> resValicacion;
        int numOcurrencias = 0;
        boolean flagOk = true;
        RvvdCatCategoria categoriaActual = (RvvdCatCategoria) session.get(RvvdCatCategoria.class, categoria.getIdCategoria());
        try {
            session.beginTransaction();
            if (categoria.getIdCategoria() != null) {
                queryNativo = session.createSQLQuery("SELECT COUNT(PAIS) FROM RVVD_RECLASIF_CATEGORIA WHERE CATEGORIA_R = '" + categoriaActual.getCategoria()+ "'");
                resValicacion = queryNativo.list();
                for (Object object : resValicacion) {
                    numOcurrencias = Integer.parseInt(object.toString());
                }
                if (numOcurrencias == 0) {
                    session.delete(categoriaActual);
                    error = null;
                } else {
                    error = "Can not delete the category, is already used";
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
