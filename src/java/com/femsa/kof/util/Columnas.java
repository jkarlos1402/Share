package com.femsa.kof.util;

/**
 *
 * @author TMXIDSJPINAM
 */
public class Columnas {
    private String nameColumn;
    private int indexColumn;

    /**
     *
     * @param nameColumn
     * @param indexColumn
     */
    public Columnas(String nameColumn, int indexColumn) {
        this.nameColumn = nameColumn;
        this.indexColumn = indexColumn;
    }

    /**
     *
     * @return
     */
    public String getNameColumn() {
        return nameColumn;
    }

    /**
     *
     * @param nameColumn
     */
    public void setNameColumn(String nameColumn) {
        this.nameColumn = nameColumn;
    }

    /**
     *
     * @return
     */
    public int getIndexColumn() {
        return indexColumn;
    }

    /**
     *
     * @param indexColumn
     */
    public void setIndexColumn(int indexColumn) {
        this.indexColumn = indexColumn;
    }
    
}
