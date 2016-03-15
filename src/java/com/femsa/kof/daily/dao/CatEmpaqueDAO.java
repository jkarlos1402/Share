package com.femsa.kof.daily.dao;

import com.femsa.kof.daily.pojos.RvvdCatEmpaque;
import com.femsa.kof.util.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Permite la manipulación del catálogo de empaque
 *
 * @author TMXIDSJPINAM
 */
public class CatEmpaqueDAO {

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
     * Obtiene una lista de empaques sin importar su estatus
     *
     * @return Regresa una lista con la totalidad de empaques
     */
    public List<RvvdCatEmpaque> getEmpaquesAll() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatEmpaque> empaques = null;
        try {
            Query query = session.createQuery("SELECT e FROM RvvdCatEmpaque e ORDER BY e.empaqueR ASC");
            empaques = query.list();
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
        return empaques;
    }

    /**
     * Obtiene una lista con los empaques donde el estatus es igual a 1
     *
     * @return La lista de empaques filtrados
     */
    public List<RvvdCatEmpaque> getEmpaques() {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<RvvdCatEmpaque> empaques = null;
        try {
            Query query = session.createQuery("SELECT e FROM RvvdCatEmpaque e WHERE e.status = 1 ORDER BY e.empaqueR ASC");
            empaques = query.list();
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
        return empaques;
    }

    /**
     * Obtiene un empaque en específico
     *
     * @param id El identificador del empaque a buscar
     * @return El empaque buscado, si no existe se regresa nulo
     */
    public RvvdCatEmpaque getEmpaque(Integer id) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatEmpaque empaque = null;
        try {
            empaque = (RvvdCatEmpaque) session.get(RvvdCatEmpaque.class, id);
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
        return empaque;
    }

    /**
     * Obtiene un empaque en específico
     *
     * @param empaque Nombre del empaque a buscar
     * @return El empaque buscado, si no existe se regresa nulo
     */
    public RvvdCatEmpaque getEmpaque(String empaque) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        RvvdCatEmpaque empaqueT = null;
        try {
            Query query = session.createQuery("SELECT e FROM RvvdCatEmpaque e WHERE e.empaqueR = '" + empaque.toUpperCase() + "' OR e.empaqueEn = '" + empaque.toUpperCase() + "'");
            List<RvvdCatEmpaque> empaques = query.list();
            if (empaques != null && !empaques.isEmpty()) {
                empaqueT = empaques.get(0);
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
        return empaqueT;
    }

    /**
     * Guarda o actualiza un empaque
     *
     * @param empaque El empaque a ser guardado o actualizado
     * @return En caso de éxito se regresa verdadero, en caso contrario se
     * regresa falso
     */
    public boolean saveEmpaque(RvvdCatEmpaque empaque) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        boolean flagOk = true;
        try {
            session.beginTransaction();
            if (getEmpaque(empaque.getEmpaqueR()) == null) {
                session.saveOrUpdate(empaque);
                error = null;
            } else {
                error = "Packing already exists";
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
     * Elimina un empaque
     *
     * @param empaque Empaque a eliminar
     * @return Si la eliminación concluyó con éxito se regresa verdadero, en
     * caso contrario se regresa falso y el error es almacenado en el atributo
     * error
     */
    public boolean deleteEmpaque(RvvdCatEmpaque empaque) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query queryNativo;
        List<Object> resValicacion;
        int numOcurrencias = 0;
        boolean flagOk = true;
        RvvdCatEmpaque empaqueActual = (RvvdCatEmpaque) session.get(RvvdCatEmpaque.class, empaque.getIdEmpaque());
        try {
            session.beginTransaction();
            if (empaque.getIdEmpaque() != null) {
                queryNativo = session.createSQLQuery("SELECT COUNT(PAIS) FROM RVVD_RECLASIF_EMPAQUE WHERE EMPAQUE_R = '" + empaqueActual.getEmpaqueR() + "'");
                resValicacion = queryNativo.list();
                for (Object object : resValicacion) {
                    numOcurrencias = Integer.parseInt(object.toString());
                }
                if (numOcurrencias == 0) {
                    session.delete(empaqueActual);
                    error = null;
                } else {
                    error = "Can not delete the packing, is already used";
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
