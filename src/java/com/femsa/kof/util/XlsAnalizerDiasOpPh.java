package com.femsa.kof.util;

import com.femsa.kof.daily.pojos.Rvvd445Ph;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;

public class XlsAnalizerDiasOpPh {

    private List<String> omittedSheets;
    private List<String> loadedSheets;
    private List<String> errors;
    private List<Rvvd445Ph> cargasDiasPh = new ArrayList<Rvvd445Ph>();

    public XlsAnalizerDiasOpPh() {
        omittedSheets = new ArrayList<String>();
        loadedSheets = new ArrayList<String>();
        errors = new ArrayList<String>();
    }

    public List<Rvvd445Ph> getCargasDiasPh() {
        return cargasDiasPh;
    }

    public void setCargasDiasPh(List<Rvvd445Ph> cargasDiasPh) {
        this.cargasDiasPh = cargasDiasPh;
    }

    public List<String> getLoadedSheets() {
        return loadedSheets;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setLoadedSheets(List<String> loadedSheets) {
        this.loadedSheets = loadedSheets;
    }

    public List<String> getOmittedSheets() {
        return omittedSheets;
    }

    public void setOmittedSheets(List<String> omittedSheets) {
        this.omittedSheets = omittedSheets;
    }

    public void analizeXls(UploadedFile file, ShareCatPais catPais, ShareUsuario usuario) {
        Workbook excelXLS = null;
        try {
            String extension = getExtension(file.getFileName());
            Iterator<Row> rowIterator = null;
            
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
        } catch (IOException ex) {
        } finally {
            try {
                file.getInputstream().close();
                file = null;
            } catch (IOException ex) {
            }
        }
    }

    public List<Rvvd445Ph> analizeSheetDiasOp(Iterator<Row> rowIterator, ShareCatPais catPais, ShareUsuario usuario, String sheetName) {
        int numRow = 0;
        List<Rvvd445Ph> cargas = new ArrayList<Rvvd445Ph>();
        Rvvd445Ph diaOpTemp = null;
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");        
        while (rowIterator != null && rowIterator.hasNext()) {
            Row row = rowIterator.next();            
            Cell cell = null;
            if (numRow > 0) {
                cell = row.getCell(0);//primera columna
                if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    diaOpTemp = new Rvvd445Ph();
                    diaOpTemp.setPais(cell.getStringCellValue().trim().toUpperCase());
                } else {
                    cargas = null;
                    errors.add("Approximately " + Character.toString((char) (65 + 0)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                    break;
                }

                cell = row.getCell(1);//segunda columna
                if (cell != null && (cell.getCellType() == Cell.CELL_TYPE_STRING || cell.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
                    if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        try {
                            diaOpTemp.setFecha(formatoDelTexto.parse(cell.getStringCellValue().trim()));
                        } catch (ParseException ex) {
                            errors.add("Approximately " + Character.toString((char) (65 + 1)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                            cargas = null;
                            break;
                        }
                    } else {
                        diaOpTemp.setFecha(cell.getDateCellValue());
                    }
                } else {
                    cargas = null;
                    errors.add("Approximately " + Character.toString((char) (65 + 1)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                    break;
                }

                cell = row.getCell(2);//tercera columna
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        diaOpTemp.setFechaReasignacion(new BigInteger(cell.getStringCellValue().trim()));
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        diaOpTemp.setFechaReasignacion(new BigInteger((long)cell.getNumericCellValue()+""));
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        switch (cell.getCachedFormulaResultType()) {
                            case Cell.CELL_TYPE_STRING:
                                diaOpTemp.setFechaReasignacion(new BigInteger(cell.getStringCellValue().trim()));
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                diaOpTemp.setFechaReasignacion(new BigInteger((long)cell.getNumericCellValue()+""));
                                break;
                        }
                        break;
                    default:                        
                        break;
                }
                cell = row.getCell(3);//cuarta columna
                if (cell != null && (cell.getCellType() == Cell.CELL_TYPE_STRING || cell.getCellType() == Cell.CELL_TYPE_NUMERIC)) {
                    if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        try {
                            diaOpTemp.setFechaAa(formatoDelTexto.parse(cell.getStringCellValue().trim()));
                        } catch (ParseException ex) {
                            errors.add("Approximately " + Character.toString((char) (65 + 3)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                            cargas = null;
                            break;
                        }
                    } else {
                        diaOpTemp.setFechaAa(cell.getDateCellValue());
                    }
                } else {
                    cargas = null;
                    errors.add("Approximately " + Character.toString((char) (65 + 3)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                    break;
                }
                cargas.add(diaOpTemp);
            }            
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
