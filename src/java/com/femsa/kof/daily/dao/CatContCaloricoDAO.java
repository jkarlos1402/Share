package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatContenidoCalorico;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Permite la manipulacion de el catálogo de contenidos calóricos
 *
 * @author TMXIDSJPINAM
 */
public class CatContCaloricoDAO {

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
     * Obtiene una lista con los contenidos calóricos sin importar su estatus
     *
     * @return Regresa una lista con la totalidad de contenidos calóricos
     */
    public List<RvvdCatContenidoCalorico> getContsCalAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatContenidoCalorico> contenidos = null;
        try {
            Query query = session.createQuery("SELECT cc FROM RvvdCatContenidoCalorico cc");
            contenidos = query.list();
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
        return contenidos;
    }

    /**
     * Obtiene una lista de contenidos calóricos donde el estatus es igual a 1
     *
     * @return Regresa una lista con los contenidos calóricos filtrados
     */
    public List<RvvdCatContenidoCalorico> getContsCal() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatContenidoCalorico> contenidos = null;
        try {
            Query query = session.createQuery("SELECT cc FROM RvvdCatContenidoCalorico cc WHERE cc.status = 1");
            contenidos = query.list();
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
        return contenidos;
    }

    /**
     * Obtiene un contenido calórico en específico
     *
     * @param id El identificador del contenido calórico
     * @return EL contenido calórico buscado, en caso de no existir se regresa
     * nulo
     */
    public RvvdCatContenidoCalorico getContCal(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatContenidoCalorico contenido = null;
        try {
            contenido = (RvvdCatContenidoCalorico) session.get(RvvdCatContenidoCalorico.class, id);
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
        return contenido;
    }

    /**
     * Obtiene un contenido calórico en específico
     *
     * @param contenido El nombre del contenido calórico a buscar
     * @return El contenido calórico buscado, en caso de no existir se regresa
     * nulo
     */
    public RvvdCatContenidoCalorico getContCal(String contenido) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatContenidoCalorico contenidoT = null;
        try {
            Query query = session.createQuery("SELECT cc FROM RvvdCatContenidoCalorico cc WHERE cc.contenidoCaloricoR = '" + contenido.toUpperCase() + "' OR cc.contenidoCaloricoEn = '" + contenido.toUpperCase() + "'");
            List<RvvdCatContenidoCalorico> contenidos = query.list();

            if (contenidos != null && !contenidos.isEmpty()) {
                contenidoT = contenidos.get(0);
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
        return contenidoT;
    }

    /**
     * Guarda o actualiza un contenido calórico
     *
     * @param contenido El contenido calórico a guardar o actualizar
     * @return En caso de éxito regresa verdadero, en caso contrario regresa
     * falso y el error es almacenado en el atributo error
     */
    public boolean saveContCal(RvvdCatContenidoCalorico contenido) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if (getContCal(contenido.getContenidoCaloricoR()) == null) {
                session.saveOrUpdate(contenido);
                error = null;
            } else {
                error = "Calorie already exists";
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
     * Elimina un contenido calórico
     *
     * @param contCalorico Contenido calórico a eliminar
     * @return Si la eliminación concluyó con éxito se regresa verdadero, en
     * caso contrario se regresa falso y el error es almacenado en el atributo
     * error
     */
    public boolean deleteContenidoCalorico(RvvdCatContenidoCalorico contCalorico) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query queryNativo;
        List<Object> resValicacion;
        int numOcurrencias = 0;
        boolean flagOk = true;
        RvvdCatContenidoCalorico contCaloricoActual = (RvvdCatContenidoCalorico) session.get(RvvdCatContenidoCalorico.class, contCalorico.getIdContenidoCalorico());
        try {
            session.beginTransaction();
            if (contCalorico.getIdContenidoCalorico() != null) {
                queryNativo = session.createSQLQuery("SELECT COUNT(PAIS) FROM RVVD_RECLASIF_MARCA WHERE CONTENIDO_CALORICO_R = '" + contCaloricoActual.getContenidoCaloricoR() + "'");
                resValicacion = queryNativo.list();
                for (Object object : resValicacion) {
                    numOcurrencias = Integer.parseInt(object.toString());
                }
                if (numOcurrencias == 0) {
                    session.delete(contCaloricoActual);
                    error = null;
                } else {
                    error = "Can not delete the caloric content, is already used";
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
