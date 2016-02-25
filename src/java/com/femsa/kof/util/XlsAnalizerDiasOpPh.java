package com.femsa.kof.util;

import com.femsa.kof.daily.dao.Rvvd445PhDAO;
import com.femsa.kof.daily.pojos.Rvvd445PhTmp;
import com.femsa.kof.managedbeans.MainBean;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;

/**
 * Clase que analiza el archivo de excel correspondiente a la carga de días
 * operativos de Filipinas
 *
 * @author TMXIDSJPINAM
 */
public class XlsAnalizerDiasOpPh {

    private List<String> omittedSheets;
    private List<String> loadedSheets;
    private List<String> errors;
    private List<Rvvd445PhTmp> cargasDiasPh = new ArrayList<Rvvd445PhTmp>();

    private static final String MSG_ERROR_TITULO = "Mensaje de error...";

    /**
     * Constructor sin parámetros que inicializa los atributos necesarios para
     * el manejo de un archivo de excel
     */
    public XlsAnalizerDiasOpPh() {
        omittedSheets = new ArrayList<String>();
        loadedSheets = new ArrayList<String>();
        errors = new ArrayList<String>();
    }

    /**
     *
     * @return
     */
    public List<Rvvd445PhTmp> getCargasDiasPh() {
        return cargasDiasPh;
    }

    /**
     *
     * @param cargasDiasPh
     */
    public void setCargasDiasPh(List<Rvvd445PhTmp> cargasDiasPh) {
        this.cargasDiasPh = cargasDiasPh;
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
    public void analizeXls(UploadedFile file, ShareCatPais catPais, ShareUsuario usuario, MainBean mainBean) {
        Workbook excelXLS = null;
        try {
            String extension = getExtension(file.getFileName());
            Iterator<Row> rowIterator = null;

            if (extension.equalsIgnoreCase("xlsx")) {
                excelXLS = new XSSFWorkbook(file.getInputstream());
            } else if (extension.equalsIgnoreCase("xls")) {
                excelXLS = new HSSFWorkbook(file.getInputstream());
            }
            int numberOfSheets = excelXLS != null ? excelXLS.getNumberOfSheets() : 0;
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = excelXLS.getSheetAt(i);
                rowIterator = sheet.iterator();
                if (i == 0) {
                    cargasDiasPh = this.analizeSheetDiasOp(rowIterator, catPais, usuario, sheet.getSheetName());
                    if (cargasDiasPh != null) {
                        loadedSheets.add(sheet.getSheetName().trim().toUpperCase());
                    } else {
                        omittedSheets.add(sheet.getSheetName().trim().toUpperCase() + ", One or more cells are incorrect");
                    }
                } else {
                    omittedSheets.add(sheet.getSheetName().trim().toUpperCase() + ", not valid.");
                }
            }
            mainBean.setPorcentajeAvance(100);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
        } finally {
            try {
                file.getInputstream().close();
                file = null;
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
            }
        }
    }

    /**
     * Método encargado de la lectura y análisis de una hoja del archivo excel
     * cargado en la interfaz gráfica correspondiente a los días operativos de
     * filipinas
     *
     * @param rowIterator lista de renglones contenidos en la hoja de excel
     * @param catPais pais que realiza el análisis
     * @param usuario usuario que realiza el análisis
     * @param sheetName nombre de la hoja de excel
     * @return Regresa una lista con los registros a ser almacenados en base de
     * datos
     */
    public List<Rvvd445PhTmp> analizeSheetDiasOp(Iterator<Row> rowIterator, ShareCatPais catPais, ShareUsuario usuario, String sheetName) {
        int numRow = 0;
        List<Rvvd445PhTmp> cargas = new ArrayList<Rvvd445PhTmp>();
        Rvvd445PhTmp diaOpTemp = null;
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoIdTiempo = new SimpleDateFormat("yyyyMMdd");
        Calendar calendario = Calendar.getInstance();
        DecimalFormat decFormat = new DecimalFormat("00");
        Rvvd445PhDAO rvvd445PhDAO = new Rvvd445PhDAO();
        List<String> paises = rvvd445PhDAO.getDescPaisGeografia();
        end:
        while (rowIterator != null && rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell cell = null;
            if (numRow > 0) {
                cell = row.getCell(0);//primera columna
                if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    if (paises.contains(cell.getStringCellValue().trim().toUpperCase())) {
                        diaOpTemp = new Rvvd445PhTmp();
                        diaOpTemp.setGvDescPais(cell.getStringCellValue().trim().toUpperCase());
                    } else {
                        cargas = null;
                        errors.add(cell.getStringCellValue().trim().toUpperCase()+" is not valid");
                        break;
                    }
                } else {
                    cargas = null;
                    errors.add("Approximately " + Character.toString((char) (65 + 0)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                    break;
                }

                cell = row.getCell(1);//segunda columna
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_STRING || cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    if (cell == null || cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        try {
                            diaOpTemp.setGdFechaAa(cell != null ? formatoDelTexto.parse(cell.getStringCellValue().trim()) : null);
                        } catch (ParseException ex) {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
                            errors.add("Approximately " + Character.toString((char) (65 + 1)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                            cargas = null;
                            break;
                        }
                    } else {
                        diaOpTemp.setGdFechaAa(cell.getDateCellValue());
                    }
                } else {
                    cargas = null;
                    errors.add("Approximately " + Character.toString((char) (65 + 1)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                    break;
                }

                cell = row.getCell(2);//tercera columna
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            try {
                                diaOpTemp.setGvDiaOpAa(Integer.parseInt(cell.getStringCellValue()));
                            } catch (Exception ex) {
                                Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
                                errors.add("Approximately " + Character.toString((char) (65 + 2)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                                cargas = null;
                                break end;
                            }
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            diaOpTemp.setGvDiaOpAa((int) cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            switch (cell.getCachedFormulaResultType()) {
                                case Cell.CELL_TYPE_STRING:
                                    try {
                                        diaOpTemp.setGvDiaOpAa(Integer.parseInt(cell.getStringCellValue()));
                                    } catch (Exception ex) {
                                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
                                        errors.add("Approximately " + Character.toString((char) (65 + 2)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                                        cargas = null;
                                        break end;
                                    }
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    diaOpTemp.setGvDiaOpAa((int) cell.getNumericCellValue());
                                    break;
                            }
                            break;
                        default:
                            cargas = null;
                            errors.add("Approximately " + Character.toString((char) (65 + 2)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                            break end;
                    }
                } else {
                    diaOpTemp.setGvDiaOpAa(0);
                }
                cell = row.getCell(3);//cuarta columna
                if (cell == null || cell.getCellType() == Cell.CELL_TYPE_STRING || cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    if (cell == null || cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        try {
                            diaOpTemp.setGdFechaAct(cell != null ? formatoDelTexto.parse(cell.getStringCellValue().trim()) : null);
                        } catch (ParseException ex) {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
                            errors.add("Approximately " + Character.toString((char) (65 + 3)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                            cargas = null;
                            break;
                        }
                    } else {
                        diaOpTemp.setGdFechaAct(cell.getDateCellValue());
                    }
                } else {
                    cargas = null;
                    errors.add("Approximately " + Character.toString((char) (65 + 3)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                    break;
                }
                cell = row.getCell(4);//quinta columna
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            try {
                                diaOpTemp.setGvDiaOpAct(Integer.parseInt(cell.getStringCellValue()));
                            } catch (Exception ex) {
                                Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
                                errors.add("Approximately " + Character.toString((char) (65 + 4)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                                cargas = null;
                                break end;
                            }
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            diaOpTemp.setGvDiaOpAct((int) cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            switch (cell.getCachedFormulaResultType()) {
                                case Cell.CELL_TYPE_STRING:
                                    try {
                                        diaOpTemp.setGvDiaOpAct(Integer.parseInt(cell.getStringCellValue()));
                                    } catch (Exception ex) {
                                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
                                        errors.add("Approximately " + Character.toString((char) (65 + 4)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                                        cargas = null;
                                        break end;
                                    }
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    diaOpTemp.setGvDiaOpAct((int) cell.getNumericCellValue());
                                    break;
                            }
                            break;
                        default:
                            cargas = null;
                            errors.add("Approximately " + Character.toString((char) (65 + 4)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                            break end;
                    }
                } else {
                    diaOpTemp.setGvDiaOpAct(0);
                }
                cell = row.getCell(5);//sexta columna
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            try {
                                diaOpTemp.setGvNMes(Integer.parseInt(cell.getStringCellValue()));
                            } catch (Exception ex) {
                                Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
                                errors.add("Approximately " + Character.toString((char) (65 + 5)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                                cargas = null;
                                break end;
                            }
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            diaOpTemp.setGvNMes((int) cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            switch (cell.getCachedFormulaResultType()) {
                                case Cell.CELL_TYPE_STRING:
                                    try {
                                        diaOpTemp.setGvNMes(Integer.parseInt(cell.getStringCellValue()));
                                    } catch (Exception ex) {
                                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
                                        errors.add("Approximately " + Character.toString((char) (65 + 5)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                                        cargas = null;
                                        break end;
                                    }
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    diaOpTemp.setGvNMes((int) cell.getNumericCellValue());
                                    break;
                            }
                            break;
                        default:
                            cargas = null;
                            errors.add("Approximately " + Character.toString((char) (65 + 5)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                            break end;
                    }
                } else {
                    cargas = null;
                    errors.add("Approximately " + Character.toString((char) (65 + 5)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                    break;
                }
                calendario.setTime(diaOpTemp.getGdFechaAct());
                diaOpTemp.setPkTiempo(new Integer(calendario.get(Calendar.YEAR) + "" + decFormat.format(diaOpTemp.getGvNMes()) + "" + decFormat.format(diaOpTemp.getGvDiaOpAct())));
                diaOpTemp.setPkTiempoAa(new Integer(formatoIdTiempo.format(diaOpTemp.getGdFechaAa())));
                diaOpTemp.setPkTiempoAct(new Integer(formatoIdTiempo.format(diaOpTemp.getGdFechaAct())));
                cargas.add(diaOpTemp);
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
