package com.femsa.kof.daily.managedbeans;

import com.femsa.kof.daily.pojos.RollingDaily;
import com.femsa.kof.daily.pojos.RvvdDistribucionMx;
import com.femsa.kof.share.dao.ShareTmpAllInfoCargaDAO;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.Record;
import com.femsa.kof.util.ScriptAnalizer;
import com.femsa.kof.util.XlsAnalizerDaily;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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

@ManagedBean(name = "dailyLoadBean")
@SessionScoped
public class DailyLoadBean implements Serializable {

    private List<RollingDaily> listInfoCargaRolling;
    private List<RvvdDistribucionMx> listInfoCargaDistribucion;
    private ShareCatPais countrySelected;
    private List<SelectItem> catCountriesUser;
    private List<String> omittedSheets;
    private List<String> loadedSheets;
    private List<String> errors;
    private ShareUsuario usuario;    
    private List<Record> cargas;
    
    private SimpleDateFormat formatDay = new SimpleDateFormat("dd/MM/yy");

    public DailyLoadBean() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuario = (ShareUsuario) httpSession.getAttribute("session_user");
        omittedSheets = new ArrayList<String>();
        loadedSheets = new ArrayList<String>();
        errors = new ArrayList<String>();
        cargas = new ArrayList<Record>();
    }    

    public SimpleDateFormat getFormatDay() {
        return formatDay;
    }

    public void setFormatDay(SimpleDateFormat formatDay) {
        this.formatDay = formatDay;
    }

    public List<RollingDaily> getListInfoCargaRolling() {
        return listInfoCargaRolling;
    }

    public void setListInfoCargaRolling(List<RollingDaily> listInfoCargaRolling) {
        this.listInfoCargaRolling = listInfoCargaRolling;
    }

    public List<RvvdDistribucionMx> getListInfoCargaDistribucion() {
        return listInfoCargaDistribucion;
    }

    public void setListInfoCargaDistribucion(List<RvvdDistribucionMx> listInfoCargaDistribucion) {
        this.listInfoCargaDistribucion = listInfoCargaDistribucion;
    }

    public List<Record> getCargas() {
        return cargas;
    }

    public void setCargas(List<Record> cargas) {
        this.cargas = cargas;
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

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = null;
        
        if (countrySelected != null) {
            XlsAnalizerDaily analizer = new XlsAnalizerDaily();
            analizer.analizeXls(event.getFile(), countrySelected, usuario,"rolling");
            listInfoCargaRolling = analizer.getCargasRolling();
            omittedSheets = analizer.getOmittedSheets();
            loadedSheets = analizer.getLoadedSheets();
            errors = analizer.getErrors();
            if (listInfoCargaRolling.size() > 0) {
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
//        dateExecution = new Date();
//        FacesMessage message = null;
//        if (countrySelected.getNombre().toUpperCase().equals(listInfoCarga.get(0).getPais().toUpperCase())) {
//            ShareTmpAllInfoCargaDAO cargaDAO = new ShareTmpAllInfoCargaDAO();
//            if (cargaDAO.saveInfoCarga(listInfoCarga, countrySelected, usuario)) {                
//                numEntriesSaved += listInfoCarga.size();
//                if (ScriptAnalizer.executeScritsShare(errorsScript, countrySelected)) {
//                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Records saved.");
//                    errorsScript.clear();
//                } else {
//                    String cadenaError = "";
//                    for (String error : errorsScript) {
//                        cadenaError += error + ", ";
//                    }
////                    cadenaError = cadenaError.substring(0, cadenaError.length() - 3);
//                    message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alert", "Records saved, but post-process FAILED, please contact whith the page administrator, [ERROR: " + cadenaError + "]");
//                }
//            } else {
//                String cadenaError = "";
//                for (String error : errors) {
//                    cadenaError += error + ", ";
//                }
//                //cadenaError = cadenaError.substring(0, cadenaError.length() - 3);
//                message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "An error ocurred while saving records [" + cadenaError + "]");
//            }
//            listInfoCarga.clear();
//            listInfoCarga = null;
//            omittedSheets.clear();
//            loadedSheets.clear();
//            errors.clear();
//            countrySelected = null;
//        } else {
//            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Wrong country selected");
//        }
//        FacesContext.getCurrentInstance().addMessage(null, message);
//        dateEndExecution = new Date();
    }
}
