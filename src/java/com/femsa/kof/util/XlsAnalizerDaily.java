package com.femsa.kof.util;

import com.femsa.kof.daily.pojos.RollingDaily;
import com.femsa.kof.daily.pojos.RvvdCatCategoriaOficial;
import com.femsa.kof.daily.pojos.RvvdDistribucionMx;
import com.femsa.kof.daily.pojos.RvvdReclasifDiasOp;
import com.femsa.kof.share.pojos.ShareCatCategorias;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareCatCanales;
import com.femsa.kof.share.pojos.ShareTmpAllInfoCarga;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class XlsAnalizerDaily {

    private List<String> omittedSheets;
    private List<String> loadedSheets;
    private List<String> errors;
    private List<RollingDaily> cargasRolling = new ArrayList<RollingDaily>();
    private List<RvvdDistribucionMx> cargasDistribucion = new ArrayList<RvvdDistribucionMx>();

    private final String[] mesesEsp = {"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};
    private final String[] mesesIng = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    private final String[] mesesPort = {"JAN", "FEV", "MAR", "APR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};

    public XlsAnalizerDaily() {
        omittedSheets = new ArrayList<String>();
        loadedSheets = new ArrayList<String>();
        errors = new ArrayList<String>();
    }

    public List<RollingDaily> getCargasRolling() {
        return cargasRolling;
    }

    public void setCargasRolling(List<RollingDaily> cargasRolling) {
        this.cargasRolling = cargasRolling;
    }

    public List<RvvdDistribucionMx> getCargasDistribucion() {
        return cargasDistribucion;
    }

    public void setCargasDistribucion(List<RvvdDistribucionMx> cargasDistribucion) {
        this.cargasDistribucion = cargasDistribucion;
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

    public void analizeXls(UploadedFile file, ShareCatPais catPais, ShareUsuario usuario, String tipo) {
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
                if (tipo.trim().equalsIgnoreCase("rolling") && sheet.getSheetName().trim().equalsIgnoreCase("ROLLING")) {
                    cargasRolling = this.analizeSheetRolling(rowIterator, catPais, usuario, sheet.getSheetName());
                    if (cargasRolling != null) {
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

    public List<RollingDaily> analizeSheetRolling(Iterator<Row> rowIterator, ShareCatPais catPais, ShareUsuario usuario, String sheetName) {
        int numRow = 0;
        int numCell = 0;
        List<Columnas> cabeceras = new ArrayList<Columnas>();
        List<RollingDaily> cargas = new ArrayList<RollingDaily>();
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        List<RvvdCatCategoriaOficial> categoriasOficiales = (List<RvvdCatCategoriaOficial>) context.getAttribute("categoria_oficial_catalog");       
        end:
        while (rowIterator != null && rowIterator.hasNext()) {
            numCell = 0;
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            RvvdReclasifDiasOp diaOp = null;
            RollingDaily rolling = null;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:                        
                        if (!cell.getStringCellValue().trim().equals("") && numRow == 0) {
                            cabeceras.add(new Columnas(cell.getStringCellValue().trim(), numCell));
                        } else if (!cell.getStringCellValue().trim().equals("") && numRow > 0) {
                            if (cabeceras.get(numCell).getNameColumn().equalsIgnoreCase("country") && !cell.getStringCellValue().trim().equals("")) {
                                diaOp = new RvvdReclasifDiasOp();
                                rolling = new RollingDaily();
                                rolling.setDiasOperativos(diaOp);
                                diaOp.setPais(cell.getStringCellValue().trim().toUpperCase());
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
                                        diaOp = new RvvdReclasifDiasOp();
                                        diaOp.setPais(cell.getStringCellValue().trim().toUpperCase());
                                        cargas.add(rolling);
                                    }
                                }
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if (diaOp != null && cabeceras.get(numCell).getNameColumn().equalsIgnoreCase("Date current year") && numRow > 0 && numCell > 0) {
                                    diaOp.setFecha(cell.getDateCellValue());
                                } else if (diaOp != null && cabeceras.get(numCell).getNameColumn().equalsIgnoreCase("Date PY") && numRow > 0 && numCell > 0) {
                                    diaOp.setFechaR(cell.getDateCellValue());
                                }
                                break;
                        }
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if (diaOp != null && cabeceras.get(numCell).getNameColumn().equalsIgnoreCase("Date current year") && numRow > 0 && numCell > 0) {
                            diaOp.setFecha(cell.getDateCellValue());
                        } else if (diaOp != null && cabeceras.get(numCell).getNameColumn().equalsIgnoreCase("Date PY") && numRow > 0 && numCell > 0) {
                            diaOp.setFechaR(cell.getDateCellValue());
                        }
                        break;
                }
                numCell++;
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
