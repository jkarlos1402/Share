package com.femsa.kof.util;

import com.femsa.kof.daily.pojos.RollingDaily;
import com.femsa.kof.daily.pojos.RvvdCatCategoriaOficial;
import com.femsa.kof.daily.pojos.RvvdDistribucionMxTmp;
import com.femsa.kof.daily.pojos.RvvdReclasifDiasOpTmp;
import com.femsa.kof.daily.pojos.RvvdStRollingTmp;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author TMXIDSJPINAM
 */
public class XlsAnalizerDaily {

    private List<String> omittedSheets;
    private List<String> loadedSheets;
    private List<String> errors;
    private List<RollingDaily> cargasRolling = new ArrayList<RollingDaily>();
    private List<RvvdDistribucionMxTmp> cargasDistribucion = new ArrayList<RvvdDistribucionMxTmp>();

    private static final String MSG_ERROR_TITULO = "Mensaje de error...";

    /**
     *
     */
    public XlsAnalizerDaily() {
        omittedSheets = new ArrayList<String>();
        loadedSheets = new ArrayList<String>();
        errors = new ArrayList<String>();
    }

    /**
     *
     * @return
     */
    public List<RollingDaily> getCargasRolling() {
        return cargasRolling;
    }

    /**
     *
     * @param cargasRolling
     */
    public void setCargasRolling(List<RollingDaily> cargasRolling) {
        this.cargasRolling = cargasRolling;
    }

    /**
     *
     * @return
     */
    public List<RvvdDistribucionMxTmp> getCargasDistribucion() {
        return cargasDistribucion;
    }

    /**
     *
     * @param cargasDistribucion
     */
    public void setCargasDistribucion(List<RvvdDistribucionMxTmp> cargasDistribucion) {
        this.cargasDistribucion = cargasDistribucion;
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
     */
    public void analizeXls(UploadedFile file, ShareCatPais catPais, ShareUsuario usuario) {
        Workbook excelXLS = null;
        try {
            String extension = getExtension(file.getFileName());
            Iterator<Row> rowIterator = null;

            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Iterator<Sheet> sheets = null;
            if (extension.equalsIgnoreCase("xlsx")) {
                excelXLS = new XSSFWorkbook(file.getInputstream());
            } else if (extension.equalsIgnoreCase("xls")) {
                excelXLS = new HSSFWorkbook(file.getInputstream());
            }
            int numberOfSheets = excelXLS.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = excelXLS.getSheetAt(i);
                rowIterator = sheet.iterator();
                if (sheet.getSheetName().trim().equalsIgnoreCase("ROLLING")) {
                    cargasRolling = this.analizeSheetRolling(rowIterator, catPais, usuario, sheet.getSheetName());
                    if (cargasRolling != null) {
                        loadedSheets.add(sheet.getSheetName().trim().toUpperCase());
                    } else {
                        omittedSheets.add(sheet.getSheetName().trim().toUpperCase() + ", One or more cells are incorrect");
                    }
                } else if (sheet.getSheetName().trim().equalsIgnoreCase("DISTRIBUCION_MX")) {
                    cargasDistribucion = this.analizeSheetDistribucionMx(rowIterator, catPais, usuario, sheet.getSheetName());
                    if (cargasDistribucion != null) {
                        loadedSheets.add(sheet.getSheetName().trim().toUpperCase());
                    } else {
                        omittedSheets.add(sheet.getSheetName().trim().toUpperCase() + ", One or more cells are incorrect");
                    }
                } else {
                    omittedSheets.add(sheet.getSheetName().trim().toUpperCase() + ", not valid.");
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
    }

    /**
     * Método encargado de la lectura y análisis de una hoja del archivo excel
     * cargado en la interfaz gráfica correspondiente a Rolling
     *
     *
     * @param rowIterator lista de renglones contenidos en la hoja de excel
     * @param catPais pais que realiza el análisis
     * @param usuario usuario que realiza el análisis
     * @param sheetName nombre de la hoja de excel
     * @return Regresa una lista con los registros a ser almacenados en base de
     * datos
     */
    public List<RollingDaily> analizeSheetRolling(Iterator<Row> rowIterator, ShareCatPais catPais, ShareUsuario usuario, String sheetName) {
        int numRow = 0;
        int numCell = 0;
        List<Columnas> cabeceras = new ArrayList<Columnas>();
        List<RollingDaily> cargas = new ArrayList<RollingDaily>();
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        List<RvvdCatCategoriaOficial> categoriasOficiales = (List<RvvdCatCategoriaOficial>) context.getAttribute("categoria_oficial_catalog");
        List<String> nombreCatOficiales = new ArrayList<String>();
        if (categoriasOficiales != null) {
            for (RvvdCatCategoriaOficial categoria : categoriasOficiales) {
                nombreCatOficiales.add(categoria.getCategoriaOficialEn());
            }
        }
        RvvdCatCategoriaOficial categoriaOficialTemp = null;
        Calendar fechaTemp = Calendar.getInstance();
        String descPais = "";
        String zona = "";
        end:
        while (rowIterator != null && rowIterator.hasNext()) {
            numCell = 0;
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            RvvdReclasifDiasOpTmp diaOp = null;
            RollingDaily rolling = null;
            RvvdStRollingTmp stRolling = null;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        if (!cell.getStringCellValue().trim().equals("") && numRow == 0) {
                            cabeceras.add(new Columnas(cell.getStringCellValue().trim(), numCell));
                        } else if (!cell.getStringCellValue().trim().equals("") && numRow > 0) {
                            if (cabeceras.get(numCell).getNameColumn().equalsIgnoreCase("country") && !cell.getStringCellValue().trim().equals("")) {
                                diaOp = new RvvdReclasifDiasOpTmp();
                                rolling = new RollingDaily();
                                rolling.setDiasOperativos(diaOp);
                                descPais = catPais.getNombre();
                                zona = cell.getStringCellValue().trim().toUpperCase();
                                diaOp.setPais(catPais.getClaveCorta());
                                cargas.add(rolling);
                            }
                        }
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        switch (cell.getCachedFormulaResultType()) {
                            case Cell.CELL_TYPE_STRING:
                                if (!cell.getStringCellValue().trim().equals("") && numRow == 0) {
                                    cabeceras.add(new Columnas(cell.getStringCellValue().trim(), numCell));
                                } else if (!cell.getStringCellValue().trim().equals("") && numRow > 0) {
                                    if (cabeceras.get(numCell).getNameColumn().equalsIgnoreCase("country") && !cell.getStringCellValue().trim().equals("")) {
                                        diaOp = new RvvdReclasifDiasOpTmp();
                                        descPais = catPais.getNombre();
                                        zona = cell.getStringCellValue().trim().toUpperCase();
                                        diaOp.setPais(catPais.getClaveCorta());
                                        cargas.add(rolling);
                                    }
                                }
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if (diaOp != null && cabeceras.get(numCell).getNameColumn().equalsIgnoreCase("Date current year") && numRow > 0 && numCell > 0) {
                                    diaOp.setFecha(cell.getDateCellValue());
                                } else if (diaOp != null && cabeceras.get(numCell).getNameColumn().equalsIgnoreCase("Date PY") && numRow > 0 && numCell > 0) {
                                    diaOp.setFechaR(cell.getDateCellValue());
                                } else if (diaOp != null && nombreCatOficiales.indexOf(cabeceras.get(numCell).getNameColumn().toUpperCase()) != -1 && numRow > 0 && numCell > 0) {
                                    stRolling = new RvvdStRollingTmp();
                                    fechaTemp.setTime(diaOp.getFecha());
                                    stRolling.setAnio(fechaTemp.get(Calendar.YEAR));
                                    stRolling.setMes(fechaTemp.get(Calendar.MONTH) + 1);
                                    stRolling.setDia(fechaTemp.get(Calendar.DAY_OF_MONTH));
                                    stRolling.setFecha(diaOp.getFecha());
                                    categoriaOficialTemp = categoriasOficiales.get(nombreCatOficiales.indexOf(cabeceras.get(numCell).getNameColumn().toUpperCase()));
                                    stRolling.setCategoriaOficialR(categoriaOficialTemp.getCategoriaOficial());
                                    stRolling.setCategoriaOficialEn(categoriaOficialTemp.getCategoriaOficialEn());
                                    stRolling.setPais(catPais.getClaveCorta());
                                    stRolling.setDescPais(descPais);
                                    if (catPais.getClaveCorta().trim().equalsIgnoreCase("CAM")) {
                                        stRolling.setZona(zona);
                                    } else {
                                        stRolling.setZona("ROLLING");
                                    }
                                    stRolling.setGec("ROLLING");
                                    stRolling.setCategoria("ROLLING");
                                    stRolling.setRollingCu(cell.getNumericCellValue());
                                    rolling.addRolling(stRolling);
                                } else if (diaOp != null && cabeceras.get(numCell).getNameColumn().equalsIgnoreCase("Others") && numRow > 0 && numCell > 0) {
                                    stRolling = new RvvdStRollingTmp();
                                    fechaTemp.setTime(diaOp.getFecha());
                                    stRolling.setAnio(fechaTemp.get(Calendar.YEAR));
                                    stRolling.setMes(fechaTemp.get(Calendar.MONTH) + 1);
                                    stRolling.setDia(fechaTemp.get(Calendar.DAY_OF_MONTH));
                                    stRolling.setCategoriaOficialR("OTROS");
                                    stRolling.setCategoriaOficialEn("OTHERS");
                                    stRolling.setPais(catPais.getClaveCorta());
                                    stRolling.setDescPais(descPais);
                                    if (catPais.getClaveCorta().trim().equalsIgnoreCase("CAM")) {
                                        stRolling.setZona(zona);
                                    } else {
                                        stRolling.setZona("ROLLING");
                                    }
                                    stRolling.setGec("ROLLING");
                                    stRolling.setCategoria("ROLLING");
                                    stRolling.setRollingCu(cell.getNumericCellValue());
                                    rolling.addRolling(stRolling);
                                }
                                break;
                        }
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if (diaOp != null && cabeceras.get(numCell).getNameColumn().equalsIgnoreCase("Date current year") && numRow > 0 && numCell > 0) {
                            diaOp.setFecha(cell.getDateCellValue());
                        } else if (diaOp != null && cabeceras.get(numCell).getNameColumn().equalsIgnoreCase("Date PY") && numRow > 0 && numCell > 0) {
                            diaOp.setFechaR(cell.getDateCellValue());
                        } else if (diaOp != null && nombreCatOficiales.indexOf(cabeceras.get(numCell).getNameColumn().toUpperCase()) != -1 && numRow > 0 && numCell > 0) {
                            stRolling = new RvvdStRollingTmp();
                            fechaTemp.setTime(diaOp.getFecha());
                            stRolling.setAnio(fechaTemp.get(Calendar.YEAR));
                            stRolling.setMes(fechaTemp.get(Calendar.MONTH) + 1);
                            stRolling.setDia(fechaTemp.get(Calendar.DAY_OF_MONTH));
                            stRolling.setFecha(diaOp.getFecha());
                            categoriaOficialTemp = categoriasOficiales.get(nombreCatOficiales.indexOf(cabeceras.get(numCell).getNameColumn().toUpperCase()));
                            stRolling.setCategoriaOficialR(categoriaOficialTemp.getCategoriaOficial());
                            stRolling.setCategoriaOficialEn(categoriaOficialTemp.getCategoriaOficialEn());
                            stRolling.setPais(catPais.getClaveCorta());
                            stRolling.setDescPais(descPais);
                            if (catPais.getClaveCorta().trim().equalsIgnoreCase("CAM")) {
                                stRolling.setZona(zona);
                            } else {
                                stRolling.setZona("ROLLING");
                            }
                            stRolling.setGec("ROLLING");
                            stRolling.setCategoria("ROLLING");
                            stRolling.setRollingCu(cell.getNumericCellValue());
                            rolling.addRolling(stRolling);
                        } else if (diaOp != null && cabeceras.get(numCell).getNameColumn().equalsIgnoreCase("Others") && numRow > 0 && numCell > 0) {
                            stRolling = new RvvdStRollingTmp();
                            fechaTemp.setTime(diaOp.getFecha());
                            stRolling.setAnio(fechaTemp.get(Calendar.YEAR));
                            stRolling.setMes(fechaTemp.get(Calendar.MONTH) + 1);
                            stRolling.setDia(fechaTemp.get(Calendar.DAY_OF_MONTH));
                            stRolling.setFecha(diaOp.getFecha());
                            stRolling.setCategoriaOficialR("OTROS");
                            stRolling.setCategoriaOficialEn("OTHERS");
                            stRolling.setPais(catPais.getClaveCorta());
                            stRolling.setDescPais(descPais);
                            if (catPais.getClaveCorta().trim().equalsIgnoreCase("CAM")) {
                                stRolling.setZona(zona);
                            } else {
                                stRolling.setZona("ROLLING");
                            }
                            stRolling.setGec("ROLLING");
                            stRolling.setCategoria("ROLLING");
                            stRolling.setRollingCu(cell.getNumericCellValue());
                            rolling.addRolling(stRolling);
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
     * Método encargado de la lectura y análisis de una hoja del archivo excel
     * cargado en la interfaz gráfica correspondiente a la distribución de
     * México
     *
     * @param rowIterator lista de renglones contenidos en la hoja de excel
     * @param catPais pais que realiza el análisis
     * @param usuario usuario que realiza el análisis
     * @param sheetName nombre de la hoja de excel
     * @return Regresa una lista con los registros a ser almacenados en base de
     * datos
     */
    public List<RvvdDistribucionMxTmp> analizeSheetDistribucionMx(Iterator<Row> rowIterator, ShareCatPais catPais, ShareUsuario usuario, String sheetName) {
        int numRow = 0;
        List<RvvdDistribucionMxTmp> cargas = new ArrayList<RvvdDistribucionMxTmp>();
        RvvdDistribucionMxTmp distribucionTemp = null;
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyyMMdd");
        String fecha = "";
        double porcentaje = 0.00;
        while (rowIterator != null && rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.iterator();
            Cell cell = null;
            if (numRow > 0) {
                distribucionTemp = new RvvdDistribucionMxTmp();
                distribucionTemp.setPais("MEX");
                cell = row.getCell(3);
                if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    fecha = (long) cell.getNumericCellValue() + "";
                    try {
                        distribucionTemp.setFechaOrigen(formatoDelTexto.parse(fecha));
                    } catch (ParseException ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
                        cargas = null;
                        errors.add("Approximately " + Character.toString((char) (65 + cell.getColumnIndex())) + "" + (cell.getRowIndex() + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                        break;
                    }
                } else {
                    cargas = null;
                    errors.add("Approximately " + Character.toString((char) (65 + 3)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [], the sheet has been omitted.");
                    break;
                }
                cell = row.getCell(4);
                if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    fecha = (long) cell.getNumericCellValue() + "";
                    try {
                        distribucionTemp.setFechaDestino(formatoDelTexto.parse(fecha));
                    } catch (ParseException ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
                        cargas = null;
                        errors.add("Approximately " + Character.toString((char) (65 + cell.getColumnIndex())) + "" + (cell.getRowIndex() + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                        break;
                    }
                } else {
                    cargas = null;
                    errors.add("Approximately " + Character.toString((char) (65 + 4)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                    break;
                }
                cell = row.getCell(5);
                if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    porcentaje = (double) cell.getNumericCellValue();
                    distribucionTemp.setPorcentaje(porcentaje);
                } else {
                    cargas = null;
                    errors.add("Approximately " + Character.toString((char) (65 + 5)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                    break;
                }
                cargas.add(distribucionTemp);
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
}
