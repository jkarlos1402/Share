package com.femsa.kof.util;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author TMXIDSJPINAM
 */
public class Record implements Serializable{
    private long numEntriesSaved = 0;
    private Date dateExecution;
    private Date dateEndExecution;
    private String nameFile;
    private String process;
    private String project;
    private Date fecha;

    /**
     *
     * @return
     */
    public String getProcess() {
        return process;
    }

    /**
     *
     * @param process
     */
    public void setProcess(String process) {
        this.process = process;
    }

    /**
     *
     * @return
     */
    public String getProject() {
        return project;
    }

    /**
     *
     * @param project
     */
    public void setProject(String project) {
        this.project = project;
    }

    /**
     *
     * @return
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
    public String getNameFile() {
        return nameFile;
    }

    /**
     *
     * @param nameFile
     */
    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }
    
}
