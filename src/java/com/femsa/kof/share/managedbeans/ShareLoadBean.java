package com.femsa.kof.share.managedbeans;

import com.femsa.kof.share.dao.ShareTmpAllInfoCargaDAO;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareTmpAllInfoCarga;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.Record;
import com.femsa.kof.util.ScriptAnalizer;
import com.femsa.kof.util.XlsAnalizer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author TMXIDSJPINAM
 */
@ManagedBean(name = "shareLoadBean")
@SessionScoped
public class ShareLoadBean implements Serializable {

    private List<ShareTmpAllInfoCarga> listInfoCarga;
    private ShareCatPais countrySelected;
    private List<SelectItem> catCountriesUser;
    private List<String> omittedSheets;
    private List<String> loadedSheets;
    private List<String> errors;
    private List<String> errorsScript;
    private ShareUsuario usuario;
    private String nameFile;

    private Integer numEntriesSaved = 0;
    private Date dateExecution;
    private Date dateEndExecution;

    @ManagedProperty(value = "#{shareUploadStatusBean}")
    ShareUploadStatusBean statusBean;

    /**
     *
     */
    public ShareLoadBean() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuario = (ShareUsuario) httpSession.getAttribute("session_user");
        omittedSheets = new ArrayList<String>();
        loadedSheets = new ArrayList<String>();
        errors = new ArrayList<String>();
        errorsScript = new ArrayList<String>();
    }

    /**
     *
     * @return
     */
    public List<String> getErrorsScript() {
        return errorsScript;
    }

    /**
     *
     * @param errorsScript
     */
    public void setErrorsScript(List<String> errorsScript) {
        this.errorsScript = errorsScript;
    }

    /**
     *
     * @return
     */
    public ShareUploadStatusBean getStatusBean() {
        return statusBean;
    }

    /**
     *
     * @param statusBean
     */
    public void setStatusBean(ShareUploadStatusBean statusBean) {
        this.statusBean = statusBean;
    }

    /**
     *
     * @return
     */
    public Date getDateExecution() {
        return dateExecution;
    }

    /**
     *
     * @param dateExecution
     */
    public void setDateExecution(Date dateExecution) {
        this.dateExecution = dateExecution;
    }

    /**
     *
     * @return
     */
    public Date getDateEndExecution() {
        return dateEndExecution;
    }

    /**
     *
     * @param dateEndExecution
     */
    public void setDateEndExecution(Date dateEndExecution) {
        this.dateEndExecution = dateEndExecution;
    }

    /**
     *
     * @return
     */
    public Integer getNumEntriesSaved() {
        return numEntriesSaved;
    }

    /**
     *
     * @param numEntriesSaved
     */
    public void setNumEntriesSaved(Integer numEntriesSaved) {
        this.numEntriesSaved = numEntriesSaved;
    }

    /**
     *
     * @return
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     *
     * @param errors
     */
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    /**
     *
     * @return
     */
    public ShareUsuario getUsuario() {
        return usuario;
    }

    /**
     *
     * @return
     */
    public List<String> getLoadedSheets() {
        return loadedSheets;
    }

    /**
     *
     * @param loadedSheets
     */
    public void setLoadedSheets(List<String> loadedSheets) {
        this.loadedSheets = loadedSheets;
    }

    /**
     *
     * @return
     */
    public List<String> getOmittedSheets() {
        return omittedSheets;
    }

    /**
     *
     * @param omittedSheets
     */
    public void setOmittedSheets(List<String> omittedSheets) {
        this.omittedSheets = omittedSheets;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getCatCountriesUser() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuario = (ShareUsuario) httpSession.getAttribute("session_user");
        List<ShareCatPais> catCountriesUserT = usuario.getPaises();
        catCountriesUser = new ArrayList<SelectItem>();
        if (catCountriesUserT != null) {
            for (ShareCatPais shareCatPais : catCountriesUserT) {
                if (shareCatPais.getIdstatus()) {
                    catCountriesUser.add(new SelectItem(shareCatPais, shareCatPais.getNombre()));
                }
            }
        }
        return catCountriesUser;
    }

    /**
     *
     * @param catCountriesUser
     */
    public void setCatCountriesUser(List<SelectItem> catCountriesUser) {
        this.catCountriesUser = catCountriesUser;
    }

    /**
     *
     * @return
     */
    public ShareCatPais getCountrySelected() {
        return countrySelected;
    }

    /**
     *
     * @param countrySelected
     */
    public void setCountrySelected(ShareCatPais countrySelected) {
        this.countrySelected = countrySelected;
    }

    /**
     *
     * @return
     */
    public List<ShareTmpAllInfoCarga> getListInfoCarga() {
        return listInfoCarga;
    }

    /**
     *
     * @param listInfoCarga
     */
    public void setListInfoCarga(List<ShareTmpAllInfoCarga> listInfoCarga) {
        this.listInfoCarga = listInfoCarga;
    }

    /**
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = null;
        if (countrySelected != null) {
            XlsAnalizer analizer = new XlsAnalizer();
            listInfoCarga = analizer.analizeXls(event.getFile(), countrySelected, usuario);
            omittedSheets = analizer.getOmittedSheets();
            loadedSheets = analizer.getLoadedSheets();
            errors = analizer.getErrors();
            if (!listInfoCarga.isEmpty()) {
                nameFile = event.getFile().getFileName();
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", event.getFile().getFileName() + " is uploaded.");
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alert", event.getFile().getFileName() + " is empity or corrupt.");
            }
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alert", "Select a country");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     *
     */
    public void saveInfoCarga() {
        FacesMessage message = null;
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        Boolean flagLoadInfShare = (Boolean) context.getAttribute("flag_load_share");
        if (!flagLoadInfShare) {
            flagLoadInfShare = true;
            context.setAttribute("flag_load_share", flagLoadInfShare);
            Record record = new Record();
            record.setFecha(new Date());
            record.setNameFile(nameFile);
            record.setProcess("LOAD SHARE");
            record.setDateExecution(new Date());
            record.setProject("SHARE");
            if (countrySelected.getNombre().toUpperCase().equals(listInfoCarga.get(0).getPais().toUpperCase())) {
                ShareTmpAllInfoCargaDAO cargaDAO = new ShareTmpAllInfoCargaDAO();
                if (cargaDAO.saveInfoCarga(listInfoCarga, countrySelected, usuario)) {
                    record.setNumEntriesSaved(listInfoCarga.size());
                    if (ScriptAnalizer.executeScritsShare(errorsScript, countrySelected)) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Records saved.");
                    } else {
                        String cadenaError = "";
                        for (String error : errorsScript) {
                            cadenaError += error + ", ";
                        }
                        message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alert", "Records saved, but post-process FAILED, please contact whith the page administrator, [ERROR: " + cadenaError + "]");
                    }
                } else {
                    errors = cargaDAO.getErrors();
                    String cadenaError = "";
                    for (String error : errors) {
                        cadenaError += error + ", ";
                    }
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
            record.setDateEndExecution(new Date());
            if (statusBean == null) {
                statusBean = new ShareUploadStatusBean();
            }
            statusBean.getCargasSession().add(record);
            errorsScript.clear();
            flagLoadInfShare = false;
            context.setAttribute("flag_load_share", flagLoadInfShare);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Sorry", "Other country is loading, try again later");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
