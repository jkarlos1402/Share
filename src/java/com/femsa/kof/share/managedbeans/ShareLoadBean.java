package com.femsa.kof.share.managedbeans;

import com.femsa.kof.managedbeans.MainBean;
import com.femsa.kof.share.dao.ShareLoadLogDAO;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareLoadLog;
import com.femsa.kof.share.pojos.ShareTmpAllInfoCarga;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.ScriptAnalizer;
import com.femsa.kof.util.XlsAnalizer;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author TMXIDSJPINAM
 */
@ManagedBean(name = "shareLoadBean")
@ViewScoped
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

    private long numEntriesSaved = 0L;
    private List<Long> registrosPorPagina;
    private Date dateExecution;
    private Date dateEndExecution;

    @ManagedProperty("#{mainBean}")
    private MainBean beanPrincipal;

    private UploadedFile uploadedFile;
    private InputStream stream;

    private static final String MSG_ERROR_TITULO = "Mensaje de error...";

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

    public MainBean getBeanPrincipal() {
        return beanPrincipal;
    }

    public void setBeanPrincipal(MainBean beanPrincipal) {
        this.beanPrincipal = beanPrincipal;
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
    public long getNumEntriesSaved() {
        return numEntriesSaved;
    }

    /**
     *
     * @param numEntriesSaved
     */
    public void setNumEntriesSaved(long numEntriesSaved) {
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
        numEntriesSaved = 0L;
        beanPrincipal.setPorcentajeAvance(20);
        FacesMessage message;
        uploadedFile = event.getFile();
        try {
            stream = uploadedFile.getInputstream();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            errors.add(e.getMessage());
        }
        if (countrySelected != null) {
            XlsAnalizer analizer = new XlsAnalizer();
            analizer.analizeXls(event.getFile(), countrySelected, usuario, beanPrincipal, registrosPorPagina);
            beanPrincipal.setPorcentajeAvance(50);
            omittedSheets = analizer.getOmittedSheets();
            loadedSheets = analizer.getLoadedSheets();
            errors = analizer.getErrors();
            registrosPorPagina = analizer.getNumRegistrosPorHoja();
            for (Long numReg : registrosPorPagina) {
                numEntriesSaved += numReg;
            }
            if (errors.isEmpty()) {
                nameFile = event.getFile().getFileName();
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", event.getFile().getFileName() + " is uploaded.");
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alert", event.getFile().getFileName() + " is empty or corrupt.");
            }
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alert", "Select a country");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        beanPrincipal.setPorcentajeAvance(100);
    }

    /**
     *
     */
    public void saveInfoCarga() {
        FacesMessage message;
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        Boolean flagLoadInfShare = (Boolean) context.getAttribute("flag_load_share");
        ShareLoadLogDAO logDAO = new ShareLoadLogDAO();
        XlsAnalizer analizer = new XlsAnalizer();
        if (!flagLoadInfShare) {
            flagLoadInfShare = true;
            context.setAttribute("flag_load_share", flagLoadInfShare);
            ShareLoadLog record = new ShareLoadLog();
            record.setFechaEjecucion(new Date());
            record.setNombreArchivo(nameFile);
            record.setNombreProceso("LOAD SHARE");
            record.setInicioEjecucion(new Date());
            record.setNombreProyecto("SHARE");
            record.setPais(countrySelected.getNombre());
            if (analizer.saveExcel(stream, registrosPorPagina, countrySelected, beanPrincipal)) {
                if (true/*ScriptAnalizer.executeScritsShare(errorsScript, countrySelected)*/) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Records saved.");
                } else {
                    String cadenaError = "";
                    for (String error : errorsScript) {
                        cadenaError += ", " + error;
                    }
                    cadenaError = cadenaError.replaceFirst(", ", "");
                    message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alert", "Records saved, but post-process FAILED, please contact whith the page administrator, [ERROR: " + cadenaError + "]");
                }
            } else {
                errors = analizer.getErrors();
                String cadenaError = "";
                for (String error : errors) {
                    cadenaError += error + ", ";
                }
                message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "An error ocurred while saving records [" + cadenaError + "]");
            }
            omittedSheets.clear();
            loadedSheets.clear();
            errors.clear();
            countrySelected = null;
            numEntriesSaved = 0L;
            record.setFinEjecucion(new Date());
            record.setIdUsuario(usuario);
            logDAO.saveLog(record);
            errorsScript.clear();
            flagLoadInfShare = false;
            context.setAttribute("flag_load_share", flagLoadInfShare);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Sorry", "Other country is loading, try again later");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
