package com.femsa.kof.managedbeans;

import com.femsa.kof.pojos.ShareUsuario;
import com.femsa.kof.util.ScriptAnalizer;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "userBean")
public class UserBean implements Serializable {

    private String user;
    private String password;

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

    public String logIn() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: User or password incorrect",""));
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        ShareUsuario usuario = new ShareUsuario();
        usuario.setPkUsuario(1);
        httpSession.setAttribute("session_user", usuario);
//        ScriptAnalizer.executeScritsShare();
        return "correct";
    }
}
