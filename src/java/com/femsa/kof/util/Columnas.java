package com.femsa.kof.util;

/**
 * clase que representa una columna de un archivo de excel, en ella se almacena
 * el nombre de la commna así como el ídice dentro de la hoja para su valicaión
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

    @Override
    public String toString() {
        return "Columnas{" + "nameColumn=" + nameColumn + ", indexColumn=" + indexColumn + '}';
    }

}
