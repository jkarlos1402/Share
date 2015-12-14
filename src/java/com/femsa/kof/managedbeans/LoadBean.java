package com.femsa.kof.managedbeans;

import com.femsa.kof.dao.ShareTmpAllInfoCargaDAO;
import com.femsa.kof.pojos.ShareCatPais;
import com.femsa.kof.pojos.ShareTmpAllInfoCarga;
import com.femsa.kof.pojos.ShareUsuario;
import com.femsa.kof.util.ScriptAnalizer;
import com.femsa.kof.util.XlsAnalizer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;

@ManagedBean(name = "loadBean")
@SessionScoped
public class LoadBean implements Serializable {

    private List<ShareTmpAllInfoCarga> listInfoCarga;
    private ShareCatPais countrySelected;
    private List<SelectItem> catCountriesUser;
    private List<String> omittedSheets;
    private List<String> loadedSheets;
    private List<String> errors;
    private List<String> errorsScript;
    private ShareUsuario usuario;

    private Integer numEntriesSaved = 0;
    private Date dateExecution;
    private Date dateEndExecution;

    public LoadBean() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuario = (ShareUsuario) httpSession.getAttribute("session_user");
        omittedSheets = new ArrayList<String>();
        loadedSheets = new ArrayList<String>();
        errors = new ArrayList<String>();
        errorsScript = new ArrayList<String>();
    }

    public Date getDateExecution() {
        return dateExecution;
    }

    public void setDateExecution(Date dateExecution) {
        this.dateExecution = dateExecution;
    }

    public Date getDateEndExecution() {
        return dateEndExecution;
    }

    public void setDateEndExecution(Date dateEndExecution) {
        this.dateEndExecution = dateEndExecution;
    }

    public Integer getNumEntriesSaved() {
        return numEntriesSaved;
    }

    public void setNumEntriesSaved(Integer numEntriesSaved) {
        this.numEntriesSaved = numEntriesSaved;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public ShareUsuario getUsuario() {
        return usuario;
    }

    public List<String> getLoadedSheets() {
        return loadedSheets;
    }

    public void setLoadedSheets(List<String> loadedSheets) {
        this.loadedSheets = loadedSheets;
    }

    public List<String> getOmittedSheets() {
        return omittedSheets;
    }

    public void setOmittedSheets(List<String> omittedSheets) {
        this.omittedSheets = omittedSheets;
    }

    public List<SelectItem> getCatCountriesUser() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuario = (ShareUsuario) httpSession.getAttribute("session_user");
        List<ShareCatPais> catCountriesUserT = usuario.getPaises();
        catCountriesUser = new ArrayList<SelectItem>();
        if (catCountriesUserT != null) {
            for (ShareCatPais shareCatPais : catCountriesUserT) {
                catCountriesUser.add(new SelectItem(shareCatPais, shareCatPais.getNombre()));
            }
        }
        return catCountriesUser;
    }

    public void setCatCountriesUser(List<SelectItem> catCountriesUser) {
        this.catCountriesUser = catCountriesUser;
    }

    public ShareCatPais getCountrySelected() {
        return countrySelected;
    }

    public void setCountrySelected(ShareCatPais countrySelected) {
        this.countrySelected = countrySelected;
    }

    public List<ShareTmpAllInfoCarga> getListInfoCarga() {
        return listInfoCarga;
    }

    public void setListInfoCarga(List<ShareTmpAllInfoCarga> listInfoCarga) {
        this.listInfoCarga = listInfoCarga;
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = null;

        if (usuario == null) {
            usuario = new ShareUsuario();
            usuario.setPkUsuario(1);
        }
        if (countrySelected != null) {
            XlsAnalizer analizer = new XlsAnalizer();
            listInfoCarga = analizer.analizeXls(event.getFile(), countrySelected, usuario);
            omittedSheets = analizer.getOmittedSheets();
            loadedSheets = analizer.getLoadedSheets();
            errors = analizer.getErrors();
            if (listInfoCarga.size() > 0) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", event.getFile().getFileName() + " is uploaded.");
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alert", event.getFile().getFileName() + " is empity or corrupt.");
            }
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alert", "Select a country");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void saveInfoCarga() {
        dateExecution = new Date();
        FacesMessage message = null;
        if (countrySelected.getNombre().toUpperCase().equals(listInfoCarga.get(0).getPais().toUpperCase())) {
            ShareTmpAllInfoCargaDAO cargaDAO = new ShareTmpAllInfoCargaDAO();
            if (cargaDAO.saveInfoCarga(listInfoCarga, countrySelected, usuario)) {                
                numEntriesSaved += listInfoCarga.size();
                if (ScriptAnalizer.executeScritsShare(errorsScript, countrySelected)) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Records saved.");
                    errorsScript.clear();
                } else {
                    String cadenaError = "";
                    for (String error : errorsScript) {
                        cadenaError += error + ", ";
                    }
//                    cadenaError = cadenaError.substring(0, cadenaError.length() - 3);
                    message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alert", "Records saved, but post-process FAILED, please contact whith the page administrator, [ERROR: " + cadenaError + "]");
                }
            } else {
                String cadenaError = "";
                for (String error : errors) {
                    cadenaError += error + ", ";
                }
                //cadenaError = cadenaError.substring(0, cadenaError.length() - 3);
                message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "An error ocurred while saving records [" + cadenaError + "]");
            }
            listInfoCarga.clear();
            listInfoCarga = null;
            omittedSheets.clear();
            loadedSheets.clear();
            errors.clear();
            countrySelected = null;
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Wrong country selected");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        dateEndExecution = new Date();
    }
}
