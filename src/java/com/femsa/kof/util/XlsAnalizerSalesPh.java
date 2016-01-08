package com.femsa.kof.util;

import com.femsa.kof.daily.dao.RvvdInfoPhDAO;
import com.femsa.kof.daily.pojos.RvvdInfoPh;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFChartSheet;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.primefaces.model.UploadedFile;
import org.xml.sax.InputSource;

public class XlsAnalizerSalesPh {

    private List<String> omittedSheets;
    private List<String> loadedSheets;
    private List<String> errors;
    private List<RvvdInfoPh> cargasInfoPh = new ArrayList<RvvdInfoPh>();
    private long numRegistros = 0L;
    private long rowNum = 0;
    private ReadOnlySharedStringsTable stringsTable;
    private XMLStreamReader xmlReader;
    private String sheetName;

    public XlsAnalizerSalesPh() {
        omittedSheets = new ArrayList<String>();
        loadedSheets = new ArrayList<String>();
        errors = new ArrayList<String>();
    }

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
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
        int numSheet = 0;
        try {
            String extension = getExtension(file.getFileName());
            OPCPackage oPCPackage = null;
            oPCPackage = OPCPackage.open(file.getInputstream());
            this.stringsTable = new ReadOnlySharedStringsTable(oPCPackage);

            XSSFReader xssfReader = new XSSFReader(oPCPackage);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            while (iter.hasNext()) {
                InputStream inputStream = iter.next();
                sheetName = iter.getSheetName();
                if (numSheet == 0) {
                    xmlReader = factory.createXMLStreamReader(inputStream);
                    while (xmlReader.hasNext()) {
                        xmlReader.next();
                        if (xmlReader.isStartElement()) {
                            if (xmlReader.getLocalName().equals("sheetData")) {
                                int attrs = xmlReader.getAttributeCount();
                                for (int i = 0; i < attrs; i++) {
                                    System.out.println(xmlReader.getAttributeName(i));
                                    System.out.println(xmlReader.getAttributeValue(i));
                                }
                                break;
                            }
                        }
                    }
                    readRows();
                    if (rowNum == 0) {
                        omittedSheets.clear();
                        omittedSheets.add(sheetName.trim().toUpperCase() + ", One or more cells are incorrect");
                    } else {
                        numRegistros = rowNum;
                        loadedSheets.clear();
                        loadedSheets.add(sheetName);
                    }
                } else {
                    omittedSheets.clear();
                    omittedSheets.add(sheetName.trim().toUpperCase() + ", not valid.");
                }
            }           

            if (oPCPackage != null) {
                oPCPackage.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public long analizeSheetInfoPh(Iterator<Row> rowIterator, ShareCatPais catPais, ShareUsuario usuario, String sheetName) {
        long numRow = 0L;
        Row row = null;
        Cell cell = null;
        while (rowIterator != null && rowIterator.hasNext()) {
            System.out.println(numRow);
            row = rowIterator.next();
            cell = row.getCell(0);//primera columna FECHA (STRING O numeric)
            if (cell == null || (cell.getCellType() != Cell.CELL_TYPE_STRING && cell.getCellType() != Cell.CELL_TYPE_NUMERIC)) {
                errors.add("Approximately " + Character.toString((char) (65 + 0)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                numRow = 0L;
                break;
            }
            cell = row.getCell(1);//segunda columna, string o nulo
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_STRING) {
                errors.add("Approximately " + Character.toString((char) (65 + 1)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                numRow = 0L;
                break;
            }
            cell = row.getCell(2);//tercera columna, string
            if (cell == null || cell.getCellType() != Cell.CELL_TYPE_STRING) {
                errors.add("Approximately " + Character.toString((char) (65 + 2)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                numRow = 0L;
                break;
            }
            cell = row.getCell(3);//cuarta columna, string o numeric
            if (cell == null || (cell.getCellType() != Cell.CELL_TYPE_STRING && cell.getCellType() != Cell.CELL_TYPE_NUMERIC)) {
                errors.add("Approximately " + Character.toString((char) (65 + 3)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                numRow = 0L;
                break;
            }
            cell = row.getCell(4);//quinta columna, string o nulo
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_STRING) {
                errors.add("Approximately " + Character.toString((char) (65 + 4)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                numRow = 0L;
                break;
            }
            cell = row.getCell(5);//sexta columna, string o nulo
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_STRING) {
                errors.add("Approximately " + Character.toString((char) (65 + 5)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                numRow = 0L;
                break;
            }
            cell = row.getCell(6);//septima columna, string
            if (cell == null || cell.getCellType() != Cell.CELL_TYPE_STRING) {
                errors.add("Approximately " + Character.toString((char) (65 + 6)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                numRow = 0L;
                break;
            }
            cell = row.getCell(7);//octaba columna, string
            if (cell == null || cell.getCellType() != Cell.CELL_TYPE_STRING) {
                errors.add("Approximately " + Character.toString((char) (65 + 7)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                numRow = 0L;
                break;
            }
            cell = row.getCell(8);//novena columna, string o nulo
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_STRING) {
                errors.add("Approximately " + Character.toString((char) (65 + 8)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                numRow = 0L;
                break;
            }
            cell = row.getCell(9);//decima columna, string o nulo
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_STRING) {
                errors.add("Approximately " + Character.toString((char) (65 + 9)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                numRow = 0L;
                break;
            }
            cell = row.getCell(10);//onceaba columna, numeric
            if (cell == null || cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {
                errors.add("Approximately " + Character.toString((char) (65 + 10)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                numRow = 0L;
                break;
            }
            cell = row.getCell(11);//doceaba columna, numeric
            if (cell == null || cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {
                errors.add("Approximately " + Character.toString((char) (65 + 11)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                numRow = 0L;
                break;
            }
            cell = row.getCell(12);//treceaba columna, numeric
            if (cell == null || cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {
                errors.add("Approximately " + Character.toString((char) (65 + 12)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                numRow = 0L;
                break;
            }
            cell = row.getCell(13);//catorceaba columna, numeric
            if (cell == null || cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {
                errors.add("Approximately " + Character.toString((char) (65 + 13)) + "" + (numRow + 1) + " cell in " + sheetName + " sheet have a invalid value [" + cell + "], the sheet has been omitted.");
                numRow = 0L;
                break;
            }
            numRow++;
        }
        return numRow;
    }

    public boolean saveSheetInfoPh(UploadedFile file, InputStream stream, long numRegistros) {
        boolean flagOk = true;
        Workbook excelXLS = null;
        RvvdInfoPhDAO infoPhDAO = new RvvdInfoPhDAO();
        Session session = infoPhDAO.getSession();
        try {
            String extension = getExtension(file.getFileName());
            Iterator<Row> rowIterator = null;
            if (extension.equalsIgnoreCase("xlsx")) {
                excelXLS = new XSSFWorkbook(stream);
            } else if (extension.equalsIgnoreCase("xls")) {
                excelXLS = new HSSFWorkbook(stream);
            }
            int numberOfSheets = excelXLS.getNumberOfSheets();
            Sheet sheet = excelXLS.getSheetAt(0);
            rowIterator = sheet.iterator();
            int numRow = 0;
            List<RvvdInfoPh> cargas = new ArrayList<RvvdInfoPh>();
            RvvdInfoPh infoTemp = null;
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoDelTextoInverso = new SimpleDateFormat("yyyyMMdd");
            String fecha = "";
            session.beginTransaction();
            Row row = null;
            Cell cell = null;
            while (rowIterator != null && rowIterator.hasNext()) {
                row = rowIterator.next();

                cell = row.getCell(0);//primera columna FECHA (STRING O numeric)
                infoTemp = new RvvdInfoPh();
                try {
                    infoTemp.setFecha(cell.getCellType() == Cell.CELL_TYPE_STRING ? formatoDelTexto.parse(cell.getStringCellValue().trim()) : cell.getDateCellValue());
                } catch (ParseException ex) {
                }

                cell = row.getCell(1);//segunda columna, string o nulo
                infoTemp.setZona(cell != null ? cell.getStringCellValue().trim().toUpperCase() : null);

                cell = row.getCell(2);//tercera columna, string            
                infoTemp.setCategoria(cell.getStringCellValue().trim().toUpperCase());

                cell = row.getCell(3);//cuarta columna, string o numeric            
                infoTemp.setUnidadDeNegocio(cell.getCellType() == Cell.CELL_TYPE_STRING ? cell.getStringCellValue().trim().toUpperCase() : cell.getNumericCellValue() + "");

                cell = row.getCell(4);//quinta columna, string o nulo            
                infoTemp.setGec(cell != null ? cell.getStringCellValue().trim().toUpperCase() : null);

                cell = row.getCell(5);//sexta columna, string o nulo            
                infoTemp.setCanal(cell != null ? cell.getStringCellValue().trim().toUpperCase() : null);

                cell = row.getCell(6);//septima columna, string
                infoTemp.setMarca(cell.getStringCellValue().trim().toUpperCase());

                cell = row.getCell(7);//octaba columna, string            
                infoTemp.setEmpaque(cell.getStringCellValue().trim().toUpperCase());

                cell = row.getCell(8);//novena columna, string o nulo            
                infoTemp.setRetornabilidad(cell != null ? cell.getStringCellValue().trim().toUpperCase() : null);

                cell = row.getCell(9);//decima columna, string o nulo            
                infoTemp.setTipoDeConsumo(cell != null ? cell.getStringCellValue().trim().toUpperCase() : null);

                cell = row.getCell(10);//onceaba columna, numeric            
                infoTemp.setVentaCu(cell.getNumericCellValue());

                cell = row.getCell(11);//doceaba columna, numeric            
                infoTemp.setIngresoNeto(cell.getNumericCellValue());

                cell = row.getCell(12);//treceaba columna, numeric            
                infoTemp.setCuota(cell.getNumericCellValue());

                cell = row.getCell(13);//catorceaba columna, numeric            
                infoTemp.setVentaTa(cell.getNumericCellValue());

                infoTemp.setIdTiempo(new BigInteger(formatoDelTextoInverso.format(infoTemp.getFecha())));
                cargas.add(infoTemp);
                if (numRow % 10000 == 0 || numRow == (numRegistros - 1)) {
                    for (RvvdInfoPh carga : cargas) {
                        session.save(carga);
                        if (numRow % 100 == 0) {
                            session.flush();
                            session.clear();
                        }
                    }
                    cargas.clear();
                }
                numRow++;
            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            flagOk = false;
        } finally {
            try {
                file.getInputstream().close();
                file = null;
            } catch (IOException ex) {
            }
            session.flush();
            session.clear();
            session.close();
            infoPhDAO.getHibernateUtil().closeSessionFactory();
        }
        return flagOk;
    }

    private static String getExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

    public void readRows() throws XMLStreamException {
        errors.clear();
        String elementName = "row";
        String[] data;
        rowNum = 0L;
        while (xmlReader.hasNext()) {
            xmlReader.next();
            if (xmlReader.isStartElement()) {
                if (xmlReader.getLocalName().equals(elementName)) {
                    rowNum++;
                    data = getDataRow();
                    /////////////
                    if (data.length < 14) {
                        errors.add("Approximately " + (rowNum) + " row in " + sheetName + " sheet have a invalid number of columns, the sheet has been omitted.");
                        rowNum = 0L;
                        break;
                    } else {
                        for (int i = 10; i < 14; i++) {
                            if (data[i] == null) {
                                data[i] = "0";
                            } else {
                                try {
                                    Double.parseDouble(data[i].trim().equalsIgnoreCase("") ? "0" : data[i]);
                                } catch (NumberFormatException e) {
                                    errors.add("Approximately " + Character.toString((char) (65 + i)) + "" + (rowNum) + " cell in " + sheetName + " sheet have a invalid value [" + data[i] + "], the sheet has been omitted.");
                                    rowNum = 0L;
                                    break;
                                }
                            }
                        }
                    }

                    /////////////                    
                }
            }
        }
    }

    private String[] getDataRow() throws XMLStreamException {
        List<String> rowValues = new ArrayList<String>();
        while (xmlReader.hasNext()) {
            xmlReader.next();
            if (xmlReader.isStartElement()) {
                if (xmlReader.getLocalName().equals("c")) {
                    CellReference cellReference = new CellReference(xmlReader.getAttributeValue(null, "r"));
                    // Fill in the possible blank cells!
                    while (rowValues.size() < cellReference.getCol()) {
                        rowValues.add("");
                    }
                    String cellType = xmlReader.getAttributeValue(null, "t");
                    rowValues.add(getCellValue(cellType));
                }
            } else if (xmlReader.isEndElement() && xmlReader.getLocalName().equals("row")) {
                break;
            }
        }
        return rowValues.toArray(new String[rowValues.size()]);
    }

    private String getCellValue(String cellType) throws XMLStreamException {
        String value = ""; // by default
        while (xmlReader.hasNext()) {
            xmlReader.next();
            if (xmlReader.isStartElement()) {
                if (xmlReader.getLocalName().equals("v")) {
                    if (cellType != null && cellType.equals("s")) {
                        int idx = Integer.parseInt(xmlReader.getElementText());
                        return new XSSFRichTextString(stringsTable.getEntryAt(idx)).toString();
                    } else {
                        return xmlReader.getElementText();
                    }
                }
            } else if (xmlReader.isEndElement() && xmlReader.getLocalName().equals("c")) {
                break;
            }
        }
        return value;
    }
}
