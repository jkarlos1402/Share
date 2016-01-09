package com.femsa.kof.daily.managedbeans;

import com.femsa.kof.daily.dao.RollingDAO;
import com.femsa.kof.daily.dao.Rvvd445PhDAO;
import com.femsa.kof.daily.pojos.RollingDaily;
import com.femsa.kof.daily.pojos.Rvvd445Ph;
import com.femsa.kof.daily.pojos.RvvdDistribucionMx;
import com.femsa.kof.daily.pojos.RvvdInfoPh;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareUsuario;
import com.femsa.kof.util.Record;
import com.femsa.kof.util.XlsAnalizerDaily;
import com.femsa.kof.util.XlsAnalizerDiasOpPh;
import com.femsa.kof.util.XlsAnalizerSalesPh;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "dailyLoadBean")
@SessionScoped
public class DailyLoadBean implements Serializable {

    private List<RollingDaily> listInfoCargaRolling;
    private List<Rvvd445Ph> listInfoCargaOpDaysPH;
    private List<RvvdDistribucionMx> listInfoCargaDistribucion;
    private List<RvvdInfoPh> listInfoPh;
    private ShareCatPais countrySelected;
    private List<SelectItem> catCountriesUser;
    private List<String> omittedSheets;
    private List<String> loadedSheets;
    private List<String> errors;
    private ShareUsuario usuario;
    private List<Record> cargas;
    private String nameFile;
    private long numRegistros;

    private UploadedFile uploadedFile;
    private InputStream stream;

    private SimpleDateFormat formatDay = new SimpleDateFormat("dd/MM/yy");
    private SimpleDateFormat formatDayInverse = new SimpleDateFormat("yyyMMdd");

    private long numEntriesSaved = 0;
    private Date dateExecution;
    private Date dateEndExecution;

    @ManagedProperty(value = "#{dailyUploadStatusBean}")
    DailyUploadStatusBean statusBean;

    public DailyLoadBean() {
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuario = (ShareUsuario) httpSession.getAttribute("session_user");
        omittedSheets = new ArrayList<String>();
        loadedSheets = new ArrayList<String>();
        errors = new ArrayList<String>();
        cargas = new ArrayList<Record>();
    }

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public DailyUploadStatusBean getStatusBean() {
        return statusBean;
    }

    public void setStatusBean(DailyUploadStatusBean statusBean) {
        this.statusBean = statusBean;
    }

    public List<RvvdInfoPh> getListInfoPh() {
        return listInfoPh;
    }

    public void setListInfoPh(List<RvvdInfoPh> listInfoPh) {
        this.listInfoPh = listInfoPh;
    }

    public SimpleDateFormat getFormatDayInverse() {
        return formatDayInverse;
    }

    public void setFormatDayInverse(SimpleDateFormat formatDayInverse) {
        this.formatDayInverse = formatDayInverse;
    }

    public List<Rvvd445Ph> getListInfoCargaOpDaysPH() {
        return listInfoCargaOpDaysPH;
    }

    public void setListInfoCargaOpDaysPH(List<Rvvd445Ph> listInfoCargaOpDaysPH) {
        this.listInfoCargaOpDaysPH = listInfoCargaOpDaysPH;
    }

    public long getNumEntriesSaved() {
        return numEntriesSaved;
    }

    public void setNumEntriesSaved(long numEntriesSaved) {
        this.numEntriesSaved = numEntriesSaved;
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
            analizer.analizeXls(event.getFile(), countrySelected, usuario);
            listInfoCargaRolling = analizer.getCargasRolling();
            listInfoCargaDistribucion = analizer.getCargasDistribucion();
            omittedSheets = analizer.getOmittedSheets();
            loadedSheets = analizer.getLoadedSheets();
            errors = analizer.getErrors();
            if (listInfoCargaRolling.size() > 0) {
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

    public void handleFileUploadDaysPH(FileUploadEvent event) {
        FacesMessage message = null;
        XlsAnalizerDiasOpPh analizer = new XlsAnalizerDiasOpPh();
        analizer.analizeXls(event.getFile(), countrySelected, usuario);
        listInfoCargaOpDaysPH = analizer.getCargasDiasPh();
        omittedSheets = analizer.getOmittedSheets();
        loadedSheets = analizer.getLoadedSheets();
        errors = analizer.getErrors();
        if (listInfoCargaOpDaysPH != null && listInfoCargaOpDaysPH.size() > 0) {
            nameFile = event.getFile().getFileName();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", event.getFile().getFileName() + " is uploaded.");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alert", event.getFile().getFileName() + " is empity or corrupt.");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void handleFileUploadSalesPH(FileUploadEvent event) {
        FacesMessage message = null;
        XlsAnalizerSalesPh analizer = new XlsAnalizerSalesPh();
        analizer.analizeXls(event.getFile(), countrySelected, usuario);
        uploadedFile = event.getFile();
        try {
            stream = uploadedFile.getInputstream();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        omittedSheets = analizer.getOmittedSheets();
        loadedSheets = analizer.getLoadedSheets();
        errors = analizer.getErrors();
        numRegistros = analizer.getNumRegistros();
        if (numRegistros > 0) {
            nameFile = event.getFile().getFileName();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", event.getFile().getFileName() + " is uploaded.");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alert", event.getFile().getFileName() + " is empity or corrupt.");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void saveInfoCarga() {
        FacesMessage message = null;
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        Boolean flagLoadRollingDaily = (Boolean) context.getAttribute("flag_load_rolling_daily");
        if (!flagLoadRollingDaily) {
            flagLoadRollingDaily = true;
            context.setAttribute("flag_load_rolling_daily", flagLoadRollingDaily);
            Record record = new Record();
            record.setFecha(new Date());
            record.setNameFile(nameFile);
            record.setProcess("LOAD ROLLING");
            record.setDateExecution(new Date());
            record.setProject("DAILY DASHBOARD");
            if (countrySelected.getClaveCorta().toUpperCase().equals(listInfoCargaRolling.get(0).getDiasOperativos().getPais().toUpperCase())) {
                RollingDAO rollingDAO = new RollingDAO();
                if (rollingDAO.saveDaily(listInfoCargaRolling, listInfoCargaDistribucion)) {
                    record.setNumEntriesSaved(listInfoCargaRolling.size() + (listInfoCargaRolling.size() * 4));
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Records saved.");
                } else {
                    String cadenaError = "";
                    for (String error : errors) {
                        cadenaError += error + ", ";
                    }
                    message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "An error ocurred while saving records [" + cadenaError + "]");
                }
                listInfoCargaRolling.clear();
                listInfoCargaRolling = null;
                listInfoCargaDistribucion.clear();
                listInfoCargaDistribucion = null;
                omittedSheets.clear();
                loadedSheets.clear();
                errors.clear();
                countrySelected = null;
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Wrong country selected");
            }
            record.setDateEndExecution(new Date());
            if (statusBean == null) {
                statusBean = new DailyUploadStatusBean();
            }
            statusBean.getCargasSession().add(record);
            flagLoadRollingDaily = false;
            context.setAttribute("flag_load_rolling_daily", flagLoadRollingDaily);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Sorry", "Other country is loading, try again later");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void saveInfoDiasOpPh() {
        FacesMessage message = null;
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        Boolean flagLoadOpDaysDaily = (Boolean) context.getAttribute("flag_load_opdays_daily");
        if (!flagLoadOpDaysDaily) {
            flagLoadOpDaysDaily = true;
            context.setAttribute("flag_load_opdays_daily", flagLoadOpDaysDaily);
            Record record = new Record();
            record.setFecha(new Date());
            record.setNameFile(nameFile);
            record.setProcess("LOAD OPERATIVE DAYS PHILIPPINES");
            record.setDateExecution(new Date());
            record.setProject("DAILY DASHBOARD");
            Rvvd445PhDAO rvvd445PhDAO = new Rvvd445PhDAO();
            if (rvvd445PhDAO.save445Ph(listInfoCargaOpDaysPH)) {
                record.setNumEntriesSaved(listInfoCargaOpDaysPH.size());
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Records saved.");
            } else {
                String cadenaError = "";
                for (String error : errors) {
                    cadenaError += error + ", ";
                }
                message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "An error ocurred while saving records [" + cadenaError + "]");
            }
            listInfoCargaOpDaysPH.clear();
            listInfoCargaOpDaysPH = null;
            omittedSheets.clear();
            loadedSheets.clear();
            errors.clear();
            countrySelected = null;
            record.setDateEndExecution(new Date());
            if (statusBean == null) {
                statusBean = new DailyUploadStatusBean();
            }
            statusBean.getCargasSession().add(record);
            flagLoadOpDaysDaily = false;
            context.setAttribute("flag_load_opdays_daily", flagLoadOpDaysDaily);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Sorry", "Other country is loading, try again later");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void saveInfoPh() {
        FacesMessage message = null;
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        Boolean flagLoadSalesDayly = (Boolean) context.getAttribute("flag_load_sales_daily");
        if (!flagLoadSalesDayly) {
            flagLoadSalesDayly = true;
            context.setAttribute("flag_load_sales_daily", flagLoadSalesDayly);
            Record record = new Record();
            record.setFecha(new Date());
            record.setNameFile(nameFile);
            record.setProcess("LOAD SALES PHILIPPINES");
            record.setDateExecution(new Date());
            record.setProject("DAILY DASHBOARD");
            XlsAnalizerSalesPh salesPh = new XlsAnalizerSalesPh();
            if (salesPh.saveSheetInfoPh(uploadedFile, stream, numRegistros)) {
                record.setNumEntriesSaved(numRegistros);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Records saved.");
            } else {
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
            numRegistros = 0L;            
            record.setDateEndExecution(new Date());
            if (statusBean == null) {
                statusBean = new DailyUploadStatusBean();
            }
            statusBean.getCargasSession().add(record);
            flagLoadSalesDayly = false;
            context.setAttribute("flag_load_sales_daily", flagLoadSalesDayly);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Sorry", "Other country is loading, try again later");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
