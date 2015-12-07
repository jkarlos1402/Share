package com.femsa.kof.util;

import com.femsa.kof.pojos.ShareCatCategorias;
import com.femsa.kof.pojos.ShareCatPais;
import com.femsa.kof.pojos.ShareCatCanales;
import com.femsa.kof.pojos.ShareTmpAllInfoCarga;
import com.femsa.kof.pojos.ShareUsuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;

public class XlsAnalizer {

    private List<String> omittedSheets;
    private List<String> loadedSheets;
    private List<String> errors;

    private final String[] mesesEsp = {"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};
    private final String[] mesesIng = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DIC"};
    private final String[] mesesPort = {"JAN", "FEV", "MAR", "APR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};

    public XlsAnalizer() {
        omittedSheets = new ArrayList<String>();
        loadedSheets = new ArrayList<String>();
        errors = new ArrayList<String>();
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

    public List<ShareTmpAllInfoCarga> analizeXls(UploadedFile file, ShareCatPais catPais, ShareUsuario usuario) {
        List<ShareTmpAllInfoCarga> cargas = new ArrayList<ShareTmpAllInfoCarga>();
        Workbook excelXLS = null;
        try {
            String extension = getExtension(file.getFileName());
            Iterator<Row> rowIterator = null;

            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
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

                List<ShareCatCategorias> catCategorias = (List<ShareCatCategorias>) servletContext.getAttribute("categories_catalog");
                ShareCatCategorias categoria = null;
                ShareCatCategorias categoriaTmp = new ShareCatCategorias();
                categoriaTmp.setCategoria(sheet.getSheetName().trim());
                for (ShareCatCategorias catCategoria : catCategorias) {
                    if (catCategoria.equals(categoriaTmp)) {
                        categoria = catCategoria;
                    }
                }
                List<ShareTmpAllInfoCarga> objsToAdd = null;
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
        } finally {
            try {
                file.getInputstream().close();
                file = null;
            } catch (IOException ex) {
            }
        }
        return cargas;
    }

    public List<ShareTmpAllInfoCarga> analizeSheet(Iterator<Row> rowIterator, ShareCatCategorias categoria, ShareCatPais catPais, ShareUsuario usuario, String sheetName) {
        int numRow = 0;
        int numCell = 0;
        int indexFecha = 0;
        double valueCargaTmp;
        double valueCargaTmp2;
        double valueCellDef;
        boolean flagVolume = false;
        boolean flagValue = false;
        String[] fechasTmp;
        String year = "";
        List<ShareTmpAllInfoCarga> cargas = new ArrayList<ShareTmpAllInfoCarga>();
        List<String> fechas = new ArrayList<String>();
        List<Integer> indexCellBimestral = new ArrayList<Integer>();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        List<ShareCatCanales> canales = (List<ShareCatCanales>) servletContext.getAttribute("canales_catalog");
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
                                String fechaObtenida = i == 0 && fechasTmp.length > 1 ? mes + year : fechasTmp[i].replaceFirst(mesReplace, mes);
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
                                fabricante = cell.getStringCellValue().replaceAll(categoria.getCategoria(), "").replaceAll("KO ", "KOF ").replaceAll("INDUSTRY", "TOTAL").replaceAll("RTD", "").replaceAll("SPORT", "").replaceAll(categoria.getCategoriaEsp() != null && !categoria.getCategoriaEsp().trim().equals("") ? categoria.getCategoriaEsp().trim() : categoria.getCategoria(), "").trim().toUpperCase();
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

    private static String getExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

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
