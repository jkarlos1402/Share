package com.femsa.kof.util;

import java.util.Date;

public class Record {
    private Integer numEntriesSaved = 0;
    private Date dateExecution;
    private Date dateEndExecution;
    private String nameFile;

    public Integer getNumEntriesSaved() {
        return numEntriesSaved;
    }

    public void setNumEntriesSaved(Integer numEntriesSaved) {
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

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }
    
}
