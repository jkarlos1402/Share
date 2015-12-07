package com.femsa.kof.managedbeans;

import com.femsa.kof.pojos.ScriptKOF;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

@ManagedBean
@ViewScoped
public class ScriptBean implements Serializable {
    
    private List<ScriptKOF> scripts;
    private ScriptKOF scriptSelected;
    private String scriptText;
    List<String> errors = new ArrayList<String>();
    
    public ScriptBean() {
        scripts = new ArrayList<ScriptKOF>();
    }
    
    public String getScriptText() {
        if (scriptSelected != null) {
            try {
                scriptText = scriptSelected.getTextScript();
            } catch (IOException ex) {
                errors.add(ex.getMessage());
            }
        }
        return scriptText;
    }
    
    public void setScriptText(String scriptText) {
        this.scriptText = scriptText;
    }
    
    public List<ScriptKOF> getScripts() {
        scripts.clear();
        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String contextPathResources = sc.getRealPath("");
        File directorioBase = new File(contextPathResources + File.separator + "WEB-INF" + File.separator + "scripts" + File.separator);
        File[] ficheros = directorioBase.listFiles();
        ScriptKOF skof = null;
        File fichero = null;
        for (int i = 0; i < ficheros.length; i++) {
            skof = new ScriptKOF();
            fichero = ficheros[i];
            skof.setName(fichero.getName());
            skof.setRuta(fichero.getAbsolutePath());
            scripts.add(skof);
        }
        return scripts;
    }
    
    public void setScripts(List<ScriptKOF> scripts) {
        this.scripts = scripts;
    }
    
    public ScriptKOF getScriptSelected() {
        return scriptSelected;
    }
    
    public void setScriptSelected(ScriptKOF scriptSelected) {
        this.scriptSelected = scriptSelected;
    }
    
    public void saveScriptSelected() {
        FacesMessage message = null;
        if (scriptSelected != null) {
            try {
                if (scriptSelected.saveText(scriptText)) {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Script saved");
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the script");
                    errors.add("Error: There was a error while saving the script");
                }
            } catch (IOException ioe) {
                message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "There was a error while saving the script");
                errors.add("Error: " + ioe.getMessage());
            }
            FacesContext.getCurrentInstance().addMessage(null, message);
        }        
    }
    
}
