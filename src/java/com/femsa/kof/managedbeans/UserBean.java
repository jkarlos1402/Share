package com.femsa.kof.managedbeans;

import com.femsa.kof.share.dao.ShareCatPaisDAO;
import com.femsa.kof.share.dao.ShareCatProyectoDAO;
import com.femsa.kof.share.dao.ShareCatRolDAO;
import com.femsa.kof.share.dao.ShareUsuarioDAO;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareCatProyecto;
import com.femsa.kof.share.pojos.ShareCatRol;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.CatalogLoader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DualListModel;

/**
 *
 * @author TMXIDSJPINAM
 */
@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable {

    private String user;
    private String password;

    private ShareUsuario usuarioNuevo = new ShareUsuario();
    private ShareUsuario usuarioSelected;

    private List<ShareUsuario> usuariosAll;

    private DualListModel<ShareCatPais> paisesAll;    

    private List<ShareCatRol> catRoles = new ArrayList<ShareCatRol>();
    private ShareCatRol rolSelected;

    private DualListModel<ShareCatProyecto> catProyectos;
//    private List<ShareCatProyecto> catProyectos = new ArrayList<ShareCatProyecto>();
//    private ShareCatProyecto proyectoSelected;

    private String error;
    private static final String MSG_ERROR_TITULO = "Mensaje de error...";

    /**
     *
     */
    public UserBean() {
        startBean();
    }

    private void startBean() {
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String errorDatabase = (String) context.getAttribute("error_database");
        if (errorDatabase == null || ("".equalsIgnoreCase(errorDatabase.trim()))) {
            ShareCatRolDAO rolDAO = new ShareCatRolDAO();
            ShareCatPaisDAO paisDAO = new ShareCatPaisDAO();
            catRoles = rolDAO.getCatRol();
            List<ShareCatPais> sourcePais = paisDAO.getCatPais();
            List<ShareCatPais> targetPais = new ArrayList<ShareCatPais>();
            ShareCatProyectoDAO proyectoDAO = new ShareCatProyectoDAO();
            List<ShareCatProyecto> sourceProyecto = proyectoDAO.getCatProyectos();
            List<ShareCatProyecto> targetProyecto = new ArrayList<ShareCatProyecto>();
            catProyectos = new DualListModel(sourceProyecto, targetProyecto);
            paisesAll = new DualListModel<ShareCatPais>(sourcePais, targetPais);

            ShareUsuarioDAO usuarioDAO = new ShareUsuarioDAO();
            usuariosAll = usuarioDAO.getAllUsers();
        } else {
            error = "No database connection - " + errorDatabase;
        }
    }

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
    public DualListModel<ShareCatProyecto> getCatProyectos() {
        ShareCatProyectoDAO proyectoDAO = new ShareCatProyectoDAO();
        List<ShareCatProyecto> sourceProyecto = proyectoDAO.getCatProyectos();
        List<ShareCatProyecto> targetProyecto = catProyectos.getTarget();
        for (ShareCatProyecto proyecto : targetProyecto) {
            sourceProyecto.remove(proyecto);
        }
        catProyectos.setSource(sourceProyecto);
        return catProyectos;
    }

    /**
     *
     * @param catProyectos
     */
    public void setCatProyectos(DualListModel<ShareCatProyecto> catProyectos) {
        this.catProyectos = catProyectos;
    }

//    /**
//     *
//     * @return
//     */
//    public ShareCatProyecto getProyectoSelected() {
//        return proyectoSelected;
//    }
//
//    /**
//     *
//     * @param proyectoSelected
//     */
//    public void setProyectoSelected(ShareCatProyecto proyectoSelected) {
//        this.proyectoSelected = proyectoSelected;
//    }

    /**
     *
     * @return
     */
    public DualListModel<ShareCatPais> getPaisesAll() {
        ShareCatPaisDAO paisDAO = new ShareCatPaisDAO();
        List<ShareCatPais> sourcePais = paisDAO.getCatPais();
        List<ShareCatPais> targetPais = paisesAll.getTarget();
        for (ShareCatPais pais : targetPais) {
            sourcePais.remove(pais);
        }
        paisesAll.setSource(sourcePais);
        return paisesAll;
    }

    /**
     *
     * @param paisesAll
     */
    public void setPaisesAll(DualListModel<ShareCatPais> paisesAll) {
        this.paisesAll = paisesAll;
    }

    /**
     *
     * @return
     */
    public List<ShareCatRol> getCatRoles() {
        return catRoles;
    }

    /**
     *
     * @param catRoles
     */
    public void setCatRoles(List<ShareCatRol> catRoles) {
        this.catRoles = catRoles;
    }

    /**
     *
     * @return
     */
    public ShareCatRol getRolSelected() {
        return rolSelected;
    }

    /**
     *
     * @param rolSelected
     */
    public void setRolSelected(ShareCatRol rolSelected) {
        this.rolSelected = rolSelected;
    }

    /**
     *
     * @return
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public ShareUsuario getUsuarioNuevo() {
        return usuarioNuevo;
    }

    /**
     *
     * @param usuarioNuevo
     */
    public void setUsuarioNuevo(ShareUsuario usuarioNuevo) {
        this.usuarioNuevo = usuarioNuevo;
    }

    /**
     *
     * @return
     */
    public ShareUsuario getUsuarioSelected() {
        return usuarioSelected;
    }

    /**
     *
     * @param usuarioSelected
     */
    public void setUsuarioSelected(ShareUsuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    /**
     *
     * @return
     */
    public List<ShareUsuario> getUsuariosAll() {
        return usuariosAll;
    }

    /**
     *
     * @param usuariosAll
     */
    public void setUsuariosAll(List<ShareUsuario> usuariosAll) {
        this.usuariosAll = usuariosAll;
    }

    /**
     *
     * @return
     */
    public String logIn() {
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        if (error != null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + error, ""));
            if (CatalogLoader.loadCatalogs("share")) {
                servletContext.setAttribute("error_database", CatalogLoader.error);
                CatalogLoader.loadCatalogs("daily");
                error = null;
                startBean();
            }
            return "index";
        }
        try {
            ShareUsuarioDAO usuarioDAO = new ShareUsuarioDAO();
            ShareUsuario usuario = usuarioDAO.getUsuario(user, true);
            if (usuario != null && usuario.getPassword().equalsIgnoreCase(password)) {
                HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                if (usuario.getLastLogin() == null) {
                    httpSession.setAttribute("first_session_user", true);
                } else {
                    httpSession.setAttribute("first_session_user", false);
                }
                usuario.setIntentos(0);
                usuario.setLastLogin(new Date());
                usuarioDAO.saveUser(usuario);
                httpSession.setAttribute("session_user", usuario);
                return "correct";
            } else if (usuario != null) {
                usuario.setIntentos(usuario.getIntentos() + 1);
                if (usuario.getIntentos() == 3) {
                    usuario.setEstatus(false);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention: The user has been blocked, number of attempts exceeded, Contact the administrator", ""));
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: User or password incorrect", ""));
                }
                usuarioDAO.saveUser(usuario);
                return "index";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: User or password incorrect", ""));
                return "index";
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + e.getCause() != null ? e.getCause().getMessage() : e.getMessage(), ""));
            return "index";
        }
    }

    /**
     *
     * @return
     */
    public String logout() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
        }
        return "index";
    }

    /**
     *
     */
    public void saveUser() {
        FacesMessage message;
        ShareUsuarioDAO usuarioDAO = new ShareUsuarioDAO();
        if (usuarioNuevo.getPaises() == null) {
            usuarioNuevo.setPaises(new ArrayList<ShareCatPais>());
        } else {
            usuarioNuevo.getPaises().clear();
        }
        if (usuarioNuevo.getProyectos() == null) {
            usuarioNuevo.setProyectos(new ArrayList<ShareCatProyecto>());
        } else {
            usuarioNuevo.getProyectos().clear();
        }
        for (int i = 0; i < paisesAll.getTarget().size(); i++) {
            usuarioNuevo.getPaises().add(paisesAll.getTarget().get(i));
        }
        for (int i = 0; i < catProyectos.getTarget().size(); i++) {
            usuarioNuevo.getProyectos().add(catProyectos.getTarget().get(i));
        }
        usuarioNuevo.setPassword(usuarioNuevo.getPassword());       
        if (usuarioNuevo.getIntentos() != null && usuarioNuevo.getIntentos() == 3) {
            usuarioNuevo.setPassReset(true);
        }
        usuarioNuevo.setIntentos(0);
        if (usuarioDAO.saveUser(usuarioNuevo)) {
            refreshUsers();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "User saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the user, " + usuarioDAO.getError());
            usuarioNuevo.setPkUsuario(null);
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void newUser() {
        usuarioNuevo = new ShareUsuario();
        usuarioSelected = null;
        for (int i = 0; i < paisesAll.getTarget().size(); i++) {
            paisesAll.getSource().add(paisesAll.getTarget().get(i));
        }
        paisesAll.getTarget().clear();
        for (int i = 0; i < catProyectos.getTarget().size(); i++) {
            catProyectos.getSource().add(catProyectos.getTarget().get(i));
        }
        catProyectos.getTarget().clear();
//        proyectoSelected = null;
    }

    /**
     *
     */
    public void selectUser() {
        usuarioNuevo.setPkUsuario(usuarioSelected.getPkUsuario());
        usuarioNuevo.setEstatus(usuarioSelected.getEstatus());
        usuarioNuevo.setMail(usuarioSelected.getMail());
        usuarioNuevo.setNombre(usuarioSelected.getNombre());
        usuarioNuevo.setPassword(usuarioSelected.getPassword());
        usuarioNuevo.setRol(usuarioSelected.getRol());
        usuarioNuevo.setUsuario(usuarioSelected.getUsuario());
        usuarioNuevo.setPaises(usuarioSelected.getPaises());
        usuarioNuevo.setPais(usuarioSelected.getPais());
        usuarioNuevo.setProyectos(usuarioSelected.getProyectos());
        usuarioNuevo.setIntentos(usuarioSelected.getIntentos());
        usuarioNuevo.setLastLogin(usuarioSelected.getLastLogin());
        paisesAll.getTarget().clear();
        catProyectos.getTarget().clear();
        if (usuarioSelected.getPaises() != null && !usuarioSelected.getPaises().isEmpty()) {
            Object[] paisesT = usuarioSelected.getPaises().toArray();
            for (int i = 0; i < usuarioSelected.getPaises().size(); i++) {
                paisesAll.getTarget().add((ShareCatPais) paisesT[i]);
                paisesAll.getSource().remove((ShareCatPais) paisesT[i]);
            }
        }
        if (usuarioSelected.getProyectos() != null && !usuarioSelected.getProyectos().isEmpty()) {
//            proyectoSelected = usuarioSelected.getProyectos().get(0);
            Object[] proyectosT = usuarioSelected.getProyectos().toArray();
            for (int i = 0; i < usuarioSelected.getProyectos().size(); i++) {
                catProyectos.getTarget().add((ShareCatProyecto) proyectosT[i]);
                catProyectos.getSource().remove((ShareCatProyecto) proyectosT[i]);
            }
        }
    }

    /**
     *
     */
    public void refreshUsers() {
        ShareUsuarioDAO usuarioDAO = new ShareUsuarioDAO();
        usuariosAll = usuarioDAO.getAllUsers();
    }       
}
