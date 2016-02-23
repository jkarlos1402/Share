package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdReclasifCategoria;
import com.femsa.kof.daily.util.CheckCatalogs;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Permite la reclasificacion del catálogo de categorias
 *
 * @author TMXIDSJPINAM
 */
public class ReclasifCategoriaDAO {

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
     * Obtiene las categorias reclasificadas y sin reclasificar pertenecientes
     * al usuario
     *
     * @param usuario Usuario correspondiente
     * @return Regresa una lista con las categorias reclasificadas y sin
     * reclasificar
     */
    public List<RvvdReclasifCategoria> getReclasifCategoriasAll(ShareUsuario usuario) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String paises = "";
        for (int i = 0; i < usuario.getPaises().size(); i++) {
            if (i > 0) {
                paises += ",'" + (usuario.getPaises().get(i).getClaveCorta()) + "'";
            } else {
                paises = "'" + (usuario.getPaises().get(i).getClaveCorta()) + "'";
            }
        }
        List<RvvdReclasifCategoria> categoriasReclasificadas = null;
        try {
            Query query = session.createQuery("SELECT rc FROM RvvdReclasifCategoria rc WHERE rc.pais IN (" + paises + ") ORDER BY rc.categoriaR DESC,rc.categoriaEn DESC,rc.categoriaOficialR DESC,rc.categoriaOficialEn DESC");
            categoriasReclasificadas = query.list();
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
        return categoriasReclasificadas;
    }

    /**
     * Actualiza la lista de categorias reclasificadas por el usuario
     *
     * @param reclasifCategorias Lista de categorias actualizadas por el usuario
     * @return En caso de éxito regresa verdadero, de lo contrario regresa falso
     * y el error es almacenado en el atributo error.
     */
    public boolean saveReclasifCategorias(List<RvvdReclasifCategoria> reclasifCategorias) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        long cont = 0L;
        try {
            session.beginTransaction();
            for (RvvdReclasifCategoria reclasifCategoria : reclasifCategorias) {
                session.saveOrUpdate(reclasifCategoria);
                if (cont % 100 == 0) {
                    session.flush();
                    session.clear();
                }
                cont++;
            }
            error = null;
            session.getTransaction().commit();
            CheckCatalogs.checkAllCatalogs();
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
     * Obtiene el número de categorias sin reclasificar por el usuario
     *
     * @param usuario El usuario correspondiente
     * @return Regresa el número de categorias sin reclasificar por el usuario
     */
    public long checkReclasifCategorias(ShareUsuario usuario) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String paises = "";
        for (int i = 0; i < usuario.getPaises().size(); i++) {
            if (i > 0) {
                paises += ",'" + (usuario.getPaises().get(i).getClaveCorta()) + "'";
            } else {
                paises = "'" + (usuario.getPaises().get(i).getClaveCorta()) + "'";
            }
        }
        long numNotReclass = 0L;
        try {
            Query query = session.createQuery("SELECT count(rc.idReclasifCategoria) FROM RvvdReclasifCategoria rc WHERE rc.pais IN (" + paises + ") AND (rc.categoriaR IS NULL OR rc.categoriaEn IS NULL OR rc.categoriaOficialR IS NULL OR rc.categoriaOficialEn IS NULL)");
            List<Object> res = query.list();
            numNotReclass = (Long) res.get(0);
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
        return numNotReclass;
    }
}
