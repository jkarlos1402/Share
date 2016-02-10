package com.femsa.kof.util;

import com.femsa.kof.share.pojos.ShareCatCategorias;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareCatCanales;
import com.femsa.kof.share.pojos.ShareTmpAllInfoCarga;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;

/**
 * Clase que analiza el archivo de excel correspondiente a la carga de
 * informacion de Share
 */
public class XlsAnalizer {

    private List<String> omittedSheets;
    private List<String> loadedSheets;
    private List<String> errors;

    private final String[] mesesEsp = {"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};
    private final String[] mesesIng = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    private final String[] mesesPort = {"JAN", "FEV", "MAR", "APR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};

    private static final String MSG_ERROR_TITULO = "Mensaje de error...";
    /**
     * Constructor sin parámetros que inicializa los atributos necesarios para
     * el manejo de un archivo de excel correspondiente a Share
     */
    public XlsAnalizer() {
        omittedSheets = new ArrayList<String>();
        loadedSheets = new ArrayList<String>();
        errors = new ArrayList<String>();
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
     * Se encarga de la llamada a los métodos correspondientes para el análisis
     * de todas las hojas de excel
     *
     * @param file archivo cargado de la interfaz gráfica
     * @param catPais pais que realiza el análisis
     * @param usuario usuario que realiza el análisis
     * @return Regresa una lista con los registros a ser almacenados en base de
     * datos
     */
    public List<ShareTmpAllInfoCarga> analizeXls(UploadedFile file, ShareCatPais catPais, ShareUsuario usuario) {
        List<ShareTmpAllInfoCarga> cargas = new ArrayList<ShareTmpAllInfoCarga>();
        Workbook excelXLS;
        try {
            String extension = getExtension(file.getFileName());
            Iterator<Row> rowIterator;

            ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();           
            if (extension.equalsIgnoreCase("xlsx")) {
                excelXLS = new XSSFWorkbook(file.getInputstream());
            } else {
                excelXLS = new HSSFWorkbook(file.getInputstream());
            }
            int numberOfSheets = excelXLS.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = excelXLS.getSheetAt(i);
                rowIterator = sheet.iterator();

                List<ShareCatCategorias> catCategorias = (List<ShareCatCategorias>) context.getAttribute("categories_catalog");
                ShareCatCategorias categoria = null;
                ShareCatCategorias categoriaTmp = new ShareCatCategorias();
                categoriaTmp.setCategoria(sheet.getSheetName().trim());
                if (catCategorias != null) {
                    for (ShareCatCategorias catCategoria : catCategorias) {
                        if (catCategoria.equals(categoriaTmp)) {
                            categoria = catCategoria;
                        }
                    }
                }
                List<ShareTmpAllInfoCarga> objsToAdd;
                if (categoria != null) {
                    objsToAdd = this.analizeSheet(rowIterator, categoria, catPais, usuario, sheet.getSheetName());
                    if (objsToAdd != null) {
                        for (ShareTmpAllInfoCarga obj : objsToAdd) {
                            cargas.add(obj);
                        }
                        loadedSheets.add(sheet.getSheetName().trim().toUpperCase());
                    } else {
                        omittedSheets.add(sheet.getSheetName().trim().toUpperCase() + ", One or more cells are incorrect");
                    }
                } else {
                    omittedSheets.add(sheet.getSheetName().trim().toUpperCase() + ", Category not found");
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
        } finally {
            try {
                file.getInputstream().close();               
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
            }
        }
        return cargas;
    }

    /**
     * Analiza la estructura de una hoja de excel verificando que sea la
     * adecuada para que los registros sean almacenados en base de datos
     *
     * @param rowIterator Renglones de la hoja de excel
     * @param categoria Categoria analizada
     * @param catPais Pais que realiza el análisis
     * @param usuario Usuario que realiza el análisis.
     * @param sheetName Nombre de la hoja analizada
     * @return Regresa una lista con los registros analizados y listos para ser
     * almacenados en base de datos, si existe un error se regresa nulo y el
     * error es almacenado en el atributo errors
     */
    public List<ShareTmpAllInfoCarga> analizeSheet(Iterator<Row> rowIterator, ShareCatCategorias categoria, ShareCatPais catPais, ShareUsuario usuario, String sheetName) {
        int numRow = 0;
        int numCell;
        int indexFecha;
        double valueCargaTmp;
        double valueCargaTmp2;
        double valueCellDef;
        boolean flagVolume = false;
        boolean flagValue = false;
        String[] fechasTmp;
        String year;
        List<ShareTmpAllInfoCarga> cargas = new ArrayList<ShareTmpAllInfoCarga>();
        List<String> fechas = new ArrayList<String>();
        List<Integer> indexCellBimestral = new ArrayList<Integer>();
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        List<ShareCatCanales> canales = (List<ShareCatCanales>) context.getAttribute("canales_catalog");
        String canal = "";
        String fabricante = "";
        end:
        while (rowIterator != null && rowIterator.hasNext()) {
            numCell = 0;
            indexFecha = 0;
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                int finAnho;
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        if (!cell.getStringCellValue().trim().equals("") && numRow == 0 || (numRow == 1 && numCell > 2)) {
                            year = cell.getStringCellValue().trim().substring(cell.getStringCellValue().trim().length() - 5, cell.getStringCellValue().trim().length());
                            fechasTmp = cell.getStringCellValue().trim().split("/");
                            for (int i = 0; i < fechasTmp.length; i++) {
                                String mes = fechasTmp[i].trim().substring(0, 3);
                                String mesReplace = fechasTmp[i].trim().substring(0, 3);
                                for (int j = 0; j < mesesEsp.length; j++) {
                                    String mesEsp = mesesEsp[j];
                                    if (mesEsp.equalsIgnoreCase(mes)) {
                                        mes = mesesIng[j];
                                    }
                                }
                                for (int j = 0; j < mesesPort.length; j++) {
                                    String mesPort = mesesPort[j];
                                    if (mesPort.equalsIgnoreCase(mes)) {
                                        mes = mesesIng[j];
                                    }
                                }
                                if (mes.equalsIgnoreCase(mesesIng[11])) {
                                    finAnho = 1;
                                } else {
                                    finAnho = 0;
                                }
                                String fechaObtenida = i == 0 && fechasTmp.length > 1 ? mes + " " + (Integer.parseInt(year.trim()) - finAnho) : fechasTmp[i].replaceFirst(mesReplace, mes);
                                fechas.add(fechaObtenida.toUpperCase());
                            }
                            if (fechasTmp.length > 1) {
                                indexCellBimestral.add(numCell);
                            }
                        } else if (!cell.getStringCellValue().trim().equals("") && numRow > 0 && numCell == 0) {
                            if (cell.getStringCellValue().trim().equalsIgnoreCase("volume")) {
                                flagVolume = true;
                                flagValue = false;
                            } else if (cell.getStringCellValue().trim().equalsIgnoreCase("value")) {
                                flagVolume = false;
                                flagValue = true;
                            }
                        } else if (numRow > 0 && numCell == 1) {
                            if (!cell.getStringCellValue().trim().equals("")) {
                                canal = cell.getStringCellValue().trim().toUpperCase();
                                ShareCatCanales canalTemp = new ShareCatCanales();
                                canalTemp.setGvCanal(canal);
                                if (!canales.contains(canalTemp)) {
                                    cargas = null;
                                    errors.add(canal + " channel in " + sheetName + " sheet not found");
                                    break end;
                                }
                            }
                        } else if (numRow > 0 && numCell == 2) {
                            if (!cell.getStringCellValue().trim().equals("")) {
                                fabricante = cell.getStringCellValue().toUpperCase().replaceAll("INDUSTRY", "TOTAL")
                                        .replaceAll(categoria.getCategoria(), "")
                                        .replaceAll("KO ", "KOF ")
                                        .replaceAll(" KO", " KOF")
                                        .replaceAll("RTD", "")
                                        .replaceAll("SPORTS", "")
                                        .replaceAll("SPORT", "")
                                        .replaceAll(categoria.getCategoriaEsp() != null && !categoria.getCategoriaEsp().trim().equals("") ? categoria.getCategoriaEsp().trim() : categoria.getCategoria(), "")
                                        .replaceAll(categoria.getCategoria().substring(0, categoria.getCategoria().length() - 1), "")
                                        .replaceAll(catPais.getNombre().toUpperCase(), "")
                                        .trim().toUpperCase();
                                fabricante = fabricante.replaceAll(!fabricante.trim().endsWith("TOTAL") && fabricante.contains("TOTAL") ? "TOTAL" : "OTHERTHING", "");
                            }
                        } else if (numRow > 0 && numCell > 2) {
                            if (cell.getStringCellValue().trim().equalsIgnoreCase("NA")) {
                                if (indexCellBimestral.contains(numCell)) {
                                    for (int i = 0; i < 2; i++) {
                                        addRecordCarga(canal, categoria, fabricante, fechas, indexFecha, catPais, 0, cargas, flagValue, flagVolume, usuario);
                                        indexFecha++;
                                    }
                                } else {
                                    addRecordCarga(canal, categoria, fabricante, fechas, indexFecha, catPais, 0, cargas, flagValue, flagVolume, usuario);
                                    indexFecha++;
                                }
                            } else {
                                cargas = null;
                                errors.add("Approximately " + Character.toString((char) (65 + cell.getColumnIndex())) + "" + (cell.getRowIndex() + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell.getStringCellValue() + "], the sheet has been omitted.");
                                break end;
                            }
                        }
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        switch (cell.getCachedFormulaResultType()) {
                            case Cell.CELL_TYPE_NUMERIC:
                                if (numCell == 0 || numCell == 1 || numCell == 2) {
                                    cargas = null;
                                    break end;
                                } else if (indexCellBimestral.contains(numCell)) {
                                    valueCargaTmp = cell.getNumericCellValue() / 2;
                                    valueCargaTmp2 = cell.getNumericCellValue() - valueCargaTmp;
                                    for (int i = 0; i < 2; i++) {
                                        valueCellDef = i == 0 ? valueCargaTmp : valueCargaTmp2;
                                        addRecordCarga(canal, categoria, fabricante, fechas, indexFecha, catPais, valueCellDef, cargas, flagValue, flagVolume, usuario);
                                        indexFecha++;
                                    }
                                } else {
                                    addRecordCarga(canal, categoria, fabricante, fechas, indexFecha, catPais, cell.getNumericCellValue(), cargas, flagValue, flagVolume, usuario);
                                    indexFecha++;
                                }
                                break;
                            case Cell.CELL_TYPE_STRING:
                                if (cell.getStringCellValue().trim().equalsIgnoreCase("NA")) {
                                    if (indexCellBimestral.contains(numCell)) {
                                        for (int i = 0; i < 2; i++) {
                                            addRecordCarga(canal, categoria, fabricante, fechas, indexFecha, catPais, 0, cargas, flagValue, flagVolume, usuario);
                                            indexFecha++;
                                        }
                                    } else {
                                        addRecordCarga(canal, categoria, fabricante, fechas, indexFecha, catPais, 0, cargas, flagValue, flagVolume, usuario);
                                        indexFecha++;
                                    }
                                } else {
                                    cargas = null;
                                    errors.add("Approximately " + Character.toString((char) (65 + cell.getColumnIndex())) + "" + (cell.getRowIndex() + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell.getStringCellValue() + "], the sheet has been omitted.");
                                    break end;
                                }
                                break;
                        }
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if (numCell == 0 || numCell == 1 || numCell == 2) {
                            cargas = null;
                            break end;
                        } else if (indexCellBimestral.contains(numCell)) {
                            valueCargaTmp = cell.getNumericCellValue() / 2;
                            valueCargaTmp2 = cell.getNumericCellValue() - valueCargaTmp;
                            for (int i = 0; i < 2; i++) {
                                valueCellDef = i == 0 ? valueCargaTmp : valueCargaTmp2;
                                addRecordCarga(canal, categoria, fabricante, fechas, indexFecha, catPais, valueCellDef, cargas, flagValue, flagVolume, usuario);
                                indexFecha++;
                            }
                        } else {
                            addRecordCarga(canal, categoria, fabricante, fechas, indexFecha, catPais, cell.getNumericCellValue(), cargas, flagValue, flagVolume, usuario);
                            indexFecha++;
                        }
                        break;
                }
                numCell++;
            }
            numRow++;
        }

        return cargas;
    }

    /**
     * Método estático encargado de obtener la extensión del archivo cargado
     *
     * @param filename nombre del archivo
     * @return Regresa la extensión del archivo
     */
    private static String getExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }
    
    /**
     * 
     * @param canal
     * @param categoria
     * @param fabricante
     * @param fechas
     * @param indexFecha
     * @param catPais
     * @param cellValue
     * @param cargas
     * @param flagValue
     * @param flagVolume
     * @param usuario 
     */
    private static void addRecordCarga(String canal, ShareCatCategorias categoria, String fabricante, List<String> fechas, int indexFecha, ShareCatPais catPais, double cellValue, List<ShareTmpAllInfoCarga> cargas, boolean flagValue, boolean flagVolume, ShareUsuario usuario) {
        ShareTmpAllInfoCarga carga = new ShareTmpAllInfoCarga();
        carga.setCanal(canal);
        carga.setCategoria(categoria.getCategoria());
        carga.setFabricante(fabricante);
        carga.setFecha(fechas.get(indexFecha));
        carga.setGrupoCategoria(categoria.getFkGrupoCategoria().getGrupoCategoria());
        carga.setPais(catPais.getNombre());
        carga.setFkUsuario(usuario.getPkUsuario());
        if (flagValue) {
            carga.setVentaMes(cellValue);
        } else if (flagVolume) {
            carga.setVolumenMes(cellValue);
        }
        cargas.add(carga);
    }
}
