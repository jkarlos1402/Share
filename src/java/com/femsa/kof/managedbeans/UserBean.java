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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DualListModel;

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

    private List<ShareCatProyecto> catProyectos = new ArrayList<ShareCatProyecto>();
    private ShareCatProyecto proyectoSelected;

    private String error;

    public UserBean() {
        startBean();
    }

    private void startBean() {
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String errorDatabase = (String) context.getAttribute("error_database");
        if (errorDatabase == null || (errorDatabase.trim().equalsIgnoreCase(""))) {
            ShareCatRolDAO rolDAO = new ShareCatRolDAO();
            ShareCatPaisDAO paisDAO = new ShareCatPaisDAO();
            catRoles = rolDAO.getCatRol();
            List<ShareCatPais> sourcePais = paisDAO.getCatPais();
            List<ShareCatPais> targetPais = new ArrayList<ShareCatPais>();
            ShareCatProyectoDAO proyectoDAO = new ShareCatProyectoDAO();
            catProyectos = proyectoDAO.getCatProyectos();
            paisesAll = new DualListModel<ShareCatPais>(sourcePais, targetPais);

            ShareUsuarioDAO usuarioDAO = new ShareUsuarioDAO();
            usuariosAll = usuarioDAO.getAllUsers();
        } else {
            error = "No database connection - " + errorDatabase;
        }
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ShareCatProyecto> getCatProyectos() {
        return catProyectos;
    }

    public void setCatProyectos(List<ShareCatProyecto> catProyectos) {
        this.catProyectos = catProyectos;
    }

    public ShareCatProyecto getProyectoSelected() {
        return proyectoSelected;
    }

    public void setProyectoSelected(ShareCatProyecto proyectoSelected) {
        this.proyectoSelected = proyectoSelected;
    }

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

    public void setPaisesAll(DualListModel<ShareCatPais> paisesAll) {
        this.paisesAll = paisesAll;
    }

    public List<ShareCatRol> getCatRoles() {
        return catRoles;
    }

    public void setCatRoles(List<ShareCatRol> catRoles) {
        this.catRoles = catRoles;
    }

    public ShareCatRol getRolSelected() {
        return rolSelected;
    }

    public void setRolSelected(ShareCatRol rolSelected) {
        this.rolSelected = rolSelected;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ShareUsuario getUsuarioNuevo() {
        return usuarioNuevo;
    }

    public void setUsuarioNuevo(ShareUsuario usuarioNuevo) {
        this.usuarioNuevo = usuarioNuevo;
    }

    public ShareUsuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(ShareUsuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public List<ShareUsuario> getUsuariosAll() {
        return usuariosAll;
    }

    public void setUsuariosAll(List<ShareUsuario> usuariosAll) {
        this.usuariosAll = usuariosAll;
    }

    public String logIn() {
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
        try {
            if (error == null) {
                ShareUsuarioDAO usuarioDAO = new ShareUsuarioDAO();                
//                ShareUsuario usuario = usuarioDAO.getUsuario(user, passEnc);
                ShareUsuario usuario = usuarioDAO.getUsuario(user,true);                
                if (usuario != null) {
                    if (usuario.getPassword().equalsIgnoreCase(password)) {
                        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                        if(usuario.getLastLogin() == null){
                            httpSession.setAttribute("first_session_user", true);
                        }else{
                            httpSession.setAttribute("first_session_user", false);
                        }
                        usuario.setIntentos(0);
                        usuario.setLastLogin(new Date());
                        usuarioDAO.saveUser(usuario);
                        httpSession.setAttribute("session_user", usuario);
                        return "correct";
                    } else {
                        usuario.setIntentos(usuario.getIntentos() + 1);
                        if (usuario.getIntentos() == 3) {
                            usuario.setEstatus(false);
                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention: The user has been blocked, number of attempts exceeded, Contact the administrator", ""));
                        } else {
                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: User or password incorrect", ""));
                        }
                        usuarioDAO.saveUser(usuario);
                        return "index";
                    }
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: User or password incorrect", ""));
                    return "index";
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + error, ""));
                if (CatalogLoader.loadCatalogs("share")) {
                    servletContext.setAttribute("error_database", CatalogLoader.error);
                    CatalogLoader.loadCatalogs("daily");
                    error = null;
                    startBean();
                }
                return "index";
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + e.getMessage(), ""));
            return "index";
        }
    }

    public String logout() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
        }
        return "index";
    }

    public void saveUser() {
        FacesMessage message = null;
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
        usuarioNuevo.setPassword(usuarioNuevo.getPassword());
        usuarioNuevo.getProyectos().add(proyectoSelected);  
        if(usuarioNuevo.getIntentos() != null && usuarioNuevo.getIntentos() == 3){
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

    public void newUser() {
        usuarioNuevo = new ShareUsuario();
        usuarioSelected = null;
        for (int i = 0; i < paisesAll.getTarget().size(); i++) {
            paisesAll.getSource().add(paisesAll.getTarget().get(i));
        }
        paisesAll.getTarget().clear();
        proyectoSelected = null;
    }

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
        if (usuarioSelected.getPaises() != null && usuarioSelected.getPaises().size() > 0) {
            Object[] paisesT = usuarioSelected.getPaises().toArray();
            for (int i = 0; i < usuarioSelected.getPaises().size(); i++) {
                paisesAll.getTarget().add((ShareCatPais) paisesT[i]);
                paisesAll.getSource().remove((ShareCatPais) paisesT[i]);
            }
        }
        if (usuarioSelected.getProyectos() != null && usuarioSelected.getProyectos().size() > 0) {
            proyectoSelected = usuarioSelected.getProyectos().get(0);
        }
    }

    public void refreshUsers() {        
        ShareUsuarioDAO usuarioDAO = new ShareUsuarioDAO();
        usuariosAll = usuarioDAO.getAllUsers();
    }
}
