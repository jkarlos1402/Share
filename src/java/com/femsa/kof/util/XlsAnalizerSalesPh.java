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
import java.util.Date;
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
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
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
import org.xml.sax.SAXException;

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
            stringsTable = new ReadOnlySharedStringsTable(oPCPackage);

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
//                                for (int i = 0; i < attrs; i++) {
//                                    System.out.println(xmlReader.getAttributeName(i));
//                                    System.out.println(xmlReader.getAttributeValue(i));
//                                }
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

    public boolean saveSheetInfoPh(UploadedFile file, InputStream stream, long numRegistros) {
        boolean flagOk = true;
        try {
            int numSheet = 0;
            OPCPackage oPCPackage = null;
            oPCPackage = OPCPackage.open(file.getInputstream());
            stringsTable = new ReadOnlySharedStringsTable(oPCPackage);

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
//                                for (int i = 0; i < attrs; i++) {
//                                    System.out.println(xmlReader.getAttributeName(i));
//                                    System.out.println(xmlReader.getAttributeValue(i));
//                                }
                                break;
                            }
                        }
                    }
                    if (saveRows(numRegistros)) {
                        if (oPCPackage != null) {
                            oPCPackage.close();
                        }
                    } else {
                        if (oPCPackage != null) {
                            oPCPackage.close();
                        }
                        flagOk = false;
                    }
                }
            }
        } catch (InvalidFormatException ex) {
            ex.printStackTrace();
            flagOk = false;
        } catch (IOException ex) {
            ex.printStackTrace();
            flagOk = false;
        } catch (SAXException ex) {
            ex.printStackTrace();
            flagOk = false;
        } catch (OpenXML4JException ex) {
            ex.printStackTrace();
            flagOk = false;
        } catch (XMLStreamException ex) {
            ex.printStackTrace();
            flagOk = false;
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

    public boolean saveRows(long numRegistros) throws XMLStreamException {
        errors.clear();       
        boolean flagOk = true;
        RvvdInfoPhDAO infoPhDAO = new RvvdInfoPhDAO();
        Session session = infoPhDAO.getSession();
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat formatoDelTextoInverso = new SimpleDateFormat("yyyyMMdd");
        List<RvvdInfoPh> cargas = new ArrayList<RvvdInfoPh>();
        RvvdInfoPh infoTemp = null;
        String elementName = "row";
        String[] data = null;
        rowNum = 0L;
        try {
            session.beginTransaction();
            while (xmlReader.hasNext()) {
                xmlReader.next();
                if (xmlReader.isStartElement()) {
                    if (xmlReader.getLocalName().equals(elementName)) {
                        data = getDataRow();
                        /////////////
                        //primera columna FECHA (STRING O numeric)
                        infoTemp = new RvvdInfoPh();
                        try {

                            infoTemp.setFecha(data[0] != null && data[0].contains("/") ? formatoDelTexto.parse(data[0]) : new Date(Long.parseLong(data[0])));
                        } catch (ParseException ex) {
                        }

                        //segunda columna, string o nulo
                        infoTemp.setZona(data[1] != null ? data[1].trim().toUpperCase() : null);

                        //tercera columna, string            
                        infoTemp.setCategoria(data[2] != null ? data[2].trim().toUpperCase() : null);

                        //cuarta columna, string o numeric            
                        infoTemp.setUnidadDeNegocio(data[3] != null ? data[3].trim().toUpperCase() : null);

                        //quinta columna, string o nulo            
                        infoTemp.setGec(data[4] != null ? data[4].trim().toUpperCase() : null);

                        //sexta columna, string o nulo            
                        infoTemp.setCanal(data[5] != null ? data[5].trim().toUpperCase() : null);

                        //septima columna, string
                        infoTemp.setMarca(data[6] != null ? data[6].trim().toUpperCase() : null);

                        //octaba columna, string            
                        infoTemp.setEmpaque(data[7] != null ? data[7].trim().toUpperCase() : null);

                        //novena columna, string o nulo            
                        infoTemp.setRetornabilidad(data[8] != null ? data[8].trim().toUpperCase() : null);

                        //decima columna, string o nulo            
                        infoTemp.setTipoDeConsumo(data[9] != null ? data[9].trim().toUpperCase() : null);

                        //onceaba columna, numeric            
                        infoTemp.setVentaCu(data[10] != null && !data[10].equals("") ? Double.parseDouble(data[10]) : 0L);
                        //doceaba columna, numeric        
                        infoTemp.setIngresoNeto(data[11] != null && !data[10].equals("") ? Double.parseDouble(data[11]) : 0L);
                        //treceaba columna, numeric      
                        infoTemp.setCuota(data[12] != null && !data[10].equals("") ? Double.parseDouble(data[12]) : 0L);
                        //catorceaba columna, numeric         
                        infoTemp.setVentaTa(data[13] != null && !data[10].equals("") ? Double.parseDouble(data[13]) : 0L);

                        infoTemp.setIdTiempo(new BigInteger(formatoDelTextoInverso.format(infoTemp.getFecha())));
                        cargas.add(infoTemp);
                        if (rowNum % 10000 == 0 || rowNum == (numRegistros - 1)) {                            
                            for (RvvdInfoPh carga : cargas) {
                                session.save(carga);
                                if (rowNum % 100 == 0) {
                                    session.flush();
                                    session.clear();
                                }
                            }
                            cargas.clear();
                        }

                    }
                }
                rowNum++;
            }
            session.getTransaction().commit();
        } catch (Exception ex) {            
            ex.printStackTrace();

            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            flagOk = false;
        } finally {
            session.flush();
            session.clear();
            session.close();
            infoPhDAO.getHibernateUtil().closeSessionFactory();
        }
        /////////////
        return flagOk;        
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
