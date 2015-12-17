package com.femsa.kof.managedbeans;

import com.femsa.kof.share.pojos.ShareUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

    List<String> notifications;

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

}
