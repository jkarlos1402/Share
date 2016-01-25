package com.femsa.kof.managedbeans;

import com.femsa.kof.daily.util.CheckCatalogs;
import com.femsa.kof.share.dao.ShareUsuarioDAO;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "mainBean")
@SessionScoped
public class MainBean implements Serializable {

    private String page = "/WEB-INF/pages/welcome.xhtml";

    private String catalog = "";

    private ShareUsuario usuario;

    private List<String> notifications;
    
    private String password;
    
    private boolean firstSession;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFirstSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        firstSession = (Boolean) session.getAttribute("first_session_user");
        return firstSession;
    }

    public void setFirstSession(boolean firstSession) {        
        this.firstSession = firstSession;
    }

    public List<String> getNotifications() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        notifications = (List<String>) session.getAttribute("notifications_user");
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public ShareUsuario getUsuario() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuario = (ShareUsuario) httpSession.getAttribute("session_user");
        return usuario;
    }

    public void setUsuario(ShareUsuario usuario) {
        this.usuario = usuario;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page, String catalog, String proyecto) {
        if (!proyecto.equalsIgnoreCase("")) {
            this.page = "/WEB-INF/pages/" + proyecto + "/" + page + ".xhtml";
            this.catalog = catalog;
        } else {
            this.page = "/WEB-INF/pages/" + page + ".xhtml";
        }
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public void checkCatalogs() {
        CheckCatalogs.checkAllCatalogs();
    }

    public void saveUser() {
        FacesMessage message = null;
        ShareUsuarioDAO usuarioDAO = new ShareUsuarioDAO();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute("first_session_user", false);
        firstSession = false;
        usuario.setPassReset(false);
        if (usuarioDAO.saveUser(usuario)) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Information saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the information, " + usuarioDAO.getError());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void changeUserPassword(){
        usuario.setPassword(password);
        saveUser();
    }
}
