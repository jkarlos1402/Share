package com.femsa.kof.managedbeans;

import com.femsa.kof.dao.ShareCatPaisDAO;
import com.femsa.kof.dao.ShareCatRolDAO;
import com.femsa.kof.dao.ShareUsuarioDAO;
import com.femsa.kof.pojos.ShareCatPais;
import com.femsa.kof.pojos.ShareCatRol;
import com.femsa.kof.pojos.ShareUsuario;
import com.femsa.kof.util.EncrypterKOF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.DualListModel;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable {

    private String user;
    private String password;
    private String passwordConfirm;

    private ShareUsuario usuarioNuevo = new ShareUsuario();
    private ShareUsuario usuarioSelected;

    private List<ShareUsuario> usuariosAll;

    private DualListModel<ShareCatPais> paisesAll;

    private List<ShareCatRol> catRoles = new ArrayList<ShareCatRol>();
    private ShareCatRol rolSelected;

    public UserBean() {
        ShareCatRolDAO rolDAO = new ShareCatRolDAO();
        ShareCatPaisDAO paisDAO = new ShareCatPaisDAO();
        catRoles = rolDAO.getCatRol();
        List<ShareCatPais> sourcePais = paisDAO.getCatPais();
        List<ShareCatPais> targetPais = new ArrayList<ShareCatPais>();

        paisesAll = new DualListModel<ShareCatPais>(sourcePais, targetPais);
    }

    public DualListModel<ShareCatPais> getPaisesAll() {
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
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
        ShareUsuarioDAO usuarioDAO = new ShareUsuarioDAO();
        usuariosAll = usuarioDAO.getAllUsers();
        return usuariosAll;
    }

    public void setUsuariosAll(List<ShareUsuario> usuariosAll) {
        this.usuariosAll = usuariosAll;
    }

    public String logIn() {
        ShareUsuarioDAO usuarioDAO = new ShareUsuarioDAO();        
        EncrypterKOF encrypterKOF = new EncrypterKOF();
        String passEnc = encrypterKOF.encrypt(password);        
        ShareUsuario usuario = usuarioDAO.getUsuario(user, passEnc);
        if (usuario != null) {
            HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            httpSession.setAttribute("session_user", usuario);
            return "correct";
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: User or password incorrect", ""));
            return "index";
        }
    }

    public void logout() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
        }
    }
    
    public void saveUser(){
        FacesMessage message = null;
        ShareUsuarioDAO usuarioDAO = new ShareUsuarioDAO();
        if(usuarioNuevo.getPaises() == null){
            usuarioNuevo.setPaises(new ArrayList<ShareCatPais>());
        }else{
            usuarioNuevo.getPaises().clear();
        }
        for (int i = 0; i < paisesAll.getTarget().size(); i++) {
            usuarioNuevo.getPaises().add(paisesAll.getTarget().get(i));            
        }                  
        usuarioNuevo.setPassword(usuarioNuevo.getPassword());
        if (usuarioDAO.saveUser(usuarioNuevo)) {            
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "User saved");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the user");
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
        paisesAll.getTarget().clear();       
        Object[] paisesT = usuarioSelected.getPaises().toArray();
        for (int i = 0; i < usuarioSelected.getPaises().size(); i++) {
            paisesAll.getTarget().add((ShareCatPais)paisesT[i]);
            paisesAll.getSource().remove((ShareCatPais)paisesT[i]);
        }
    }
}
