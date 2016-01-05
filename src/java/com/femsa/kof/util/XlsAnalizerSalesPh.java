package com.femsa.kof.util;

import com.femsa.kof.daily.pojos.Rvvd445Ph;
import com.femsa.kof.daily.pojos.RvvdInfoPh;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;

public class XlsAnalizerSalesPh {

    private List<String> omittedSheets;
    private List<String> loadedSheets;
    private List<String> errors;
    private List<RvvdInfoPh> cargasInfoPh = new ArrayList<RvvdInfoPh>();

    public XlsAnalizerSalesPh() {
        omittedSheets = new ArrayList<String>();
        loadedSheets = new ArrayList<String>();
        errors = new ArrayList<String>();
    }

    public List<String> getOmittedSheets() {
        return omittedSheets;
    }

    public void setOmittedSheets(List<String> omittedSheets) {
        this.omittedSheets = omittedSheets;
    }

    public List<String> getLoadedSheets() {
        return loadedSheets;
    }

    public void setLoadedSheets(List<String> loadedSheets) {
        this.loadedSheets = loadedSheets;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<RvvdInfoPh> getCargasInfoPh() {
        return cargasInfoPh;
    }

    public void setCargasInfoPh(List<RvvdInfoPh> cargasInfoPh) {
        this.cargasInfoPh = cargasInfoPh;
    }

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
                if (i == 0) {
                    cargasInfoPh = this.analizeSheetInfoPh(rowIterator, catPais, usuario, sheet.getSheetName());
                    if (cargasInfoPh != null) {                       
                        loadedSheets.add(sheet.getSheetName().trim().toUpperCase());
                    } else {
                        omittedSheets.add(sheet.getSheetName().trim().toUpperCase() + ", One or more cells are incorrect");
                    }
                } else {
                    omittedSheets.add(sheet.getSheetName().trim().toUpperCase() + ", not valid.");
                }
            }
        } catch (IOException ex) {
        } finally {
            try {
                file.getInputstream().close();
                file = null;
            } catch (IOException ex) {
            }
        }
    }

    public List<RvvdInfoPh> analizeSheetInfoPh(Iterator<Row> rowIterator, ShareCatPais catPais, ShareUsuario usuario, String sheetName) {
        int numRow = 0;
        List<RvvdInfoPh> cargas = new ArrayList<RvvdInfoPh>();
        RvvdInfoPh infoTemp = null;
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoDelTextoInverso = new SimpleDateFormat("yyyyMMdd");
        String fecha = "";
        while (rowIterator != null && rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell cell = null;
            cell = row.getCell(0);//primera columna FECHA (STRING O numeric)
            if (cell != null && (cell.getCellType() == Cell.CELL_TYPE_STRING || cell.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
                infoTemp = new RvvdInfoPh();
                try {
                    infoTemp.setFecha(cell.getCellType() == Cell.CELL_TYPE_STRING ? formatoDelTexto.parse(cell.getStringCellValue().trim()) : cell.getDateCellValue());
                } catch (ParseException ex) {
                    cargas = null;
                    errors.add("Approximately " + Character.toString((char) (65 + 0)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                    break;
                }
            } else {
                cargas = null;
                errors.add("Approximately " + Character.toString((char) (65 + 0)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                break;
            }
            cell = row.getCell(1);//segunda columna, string o nulo
            if (cell == null || cell.getCellType() == Cell.CELL_TYPE_STRING) {
                infoTemp.setZona(cell != null ? cell.getStringCellValue().trim().toUpperCase() : null);
            } else {
                cargas = null;
                errors.add("Approximately " + Character.toString((char) (65 + 1)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                break;
            }
            cell = row.getCell(2);//tercera columna, string
            if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
                infoTemp.setCategoria(cell.getStringCellValue().trim().toUpperCase());
            } else {
                cargas = null;
                errors.add("Approximately " + Character.toString((char) (65 + 2)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                break;
            }
            cell = row.getCell(3);//cuarta columna, string o numeric
            if (cell != null && (cell.getCellType() == Cell.CELL_TYPE_STRING || cell.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
                infoTemp.setUnidadDeNegocio(cell.getCellType() == Cell.CELL_TYPE_STRING ? cell.getStringCellValue().trim().toUpperCase() : cell.getNumericCellValue() + "");
            } else {
                cargas = null;
                errors.add("Approximately " + Character.toString((char) (65 + 3)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                break;
            }
            cell = row.getCell(4);//quinta columna, string o nulo
            if (cell == null || cell.getCellType() == Cell.CELL_TYPE_STRING) {
                infoTemp.setGec(cell != null ? cell.getStringCellValue().trim().toUpperCase() : null);
            } else {
                cargas = null;
                errors.add("Approximately " + Character.toString((char) (65 + 4)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                break;
            }
            cell = row.getCell(5);//sexta columna, string o nulo
            if (cell == null || cell.getCellType() == Cell.CELL_TYPE_STRING) {
                infoTemp.setCanal(cell != null ? cell.getStringCellValue().trim().toUpperCase() : null);
            } else {
                cargas = null;
                errors.add("Approximately " + Character.toString((char) (65 + 5)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                break;
            }
            cell = row.getCell(6);//septima columna, string
            if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
                infoTemp.setMarca(cell.getStringCellValue().trim().toUpperCase());
            } else {
                cargas = null;
                errors.add("Approximately " + Character.toString((char) (65 + 6)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                break;
            }
            cell = row.getCell(7);//octaba columna, string
            if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
                infoTemp.setEmpaque(cell.getStringCellValue().trim().toUpperCase());
            } else {
                cargas = null;
                errors.add("Approximately " + Character.toString((char) (65 + 7)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                break;
            }
            cell = row.getCell(8);//novena columna, string o nulo
            if (cell == null || cell.getCellType() == Cell.CELL_TYPE_STRING) {
                infoTemp.setRetornabilidad(cell != null ? cell.getStringCellValue().trim().toUpperCase() : null);
            } else {
                cargas = null;
                errors.add("Approximately " + Character.toString((char) (65 + 8)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                break;
            }
            cell = row.getCell(9);//decima columna, string o nulo
            if (cell == null || cell.getCellType() == Cell.CELL_TYPE_STRING) {
                infoTemp.setTipoDeConsumo(cell != null ? cell.getStringCellValue().trim().toUpperCase() : null);
            } else {
                cargas = null;
                errors.add("Approximately " + Character.toString((char) (65 + 9)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                break;
            }
            cell = row.getCell(10);//onceaba columna, numeric
            if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                infoTemp.setVentaCu(cell.getNumericCellValue());
            } else {
                cargas = null;
                errors.add("Approximately " + Character.toString((char) (65 + 10)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                break;
            }
            cell = row.getCell(11);//doceaba columna, numeric
            if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                infoTemp.setIngresoNeto(cell.getNumericCellValue());
            } else {
                cargas = null;
                errors.add("Approximately " + Character.toString((char) (65 + 11)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                break;
            }
            cell = row.getCell(12);//treceaba columna, numeric
            if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                infoTemp.setCuota(cell.getNumericCellValue());
            } else {
                cargas = null;
                errors.add("Approximately " + Character.toString((char) (65 + 12)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                break;
            }
            cell = row.getCell(13);//catorceaba columna, numeric
            if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                infoTemp.setVentaTa(cell.getNumericCellValue());
            } else {
                cargas = null;
                errors.add("Approximately " + Character.toString((char) (65 + 13)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                break;
            }
            infoTemp.setIdTiempo(new BigInteger(formatoDelTextoInverso.format(infoTemp.getFecha())));
            cargas.add(infoTemp);
            numRow++;
        }
        return cargas;
    }

    private static String getExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

}
