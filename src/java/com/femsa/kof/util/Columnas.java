package com.femsa.kof.util;

public class Columnas {
    private String nameColumn;
    private int indexColumn;

    public Columnas(String nameColumn, int indexColumn) {
        this.nameColumn = nameColumn;
        this.indexColumn = indexColumn;
    }

    public String getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    public int getIndexColumn() {
        return indexColumn;
    }

    public void setIndexColumn(int indexColumn) {
        this.indexColumn = indexColumn;
    }
    
}
