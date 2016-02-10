package com.femsa.kof.util;

import com.femsa.kof.daily.dao.RvvdInfoPhDAO;
import com.femsa.kof.daily.pojos.RvvdInfoPh;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.hibernate.Session;
import org.primefaces.model.UploadedFile;
import org.xml.sax.SAXException;

/**
 * Clase que analiza el archivo de excel correspondiente a la carga de ventas de
 * Filipinas
 *
 * @author TMXIDSJPINAM
 */
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

    private static final String MSG_ERROR_TITULO = "Mensaje de error...";

    /**
     * Constructor sin parámetros de la clase la cual inizializa las listas de
     * hojas cargadas y omitidas del archivo excel, además se inicializa la
     * lista de errores.
     */
    public XlsAnalizerSalesPh() {
        omittedSheets = new ArrayList<String>();
        loadedSheets = new ArrayList<String>();
        errors = new ArrayList<String>();
    }

    /**
     * Método accesor del atributo numRegistros, el cual almacena el número de
     * registros que serán almacenados.
     *
     * @return Regresa el numero de registros a guardar en la base de datos
     */
    public long getNumRegistros() {
        return numRegistros;
    }

    /**
     * Método accesor del atributo numRegistros, el cual almacena el número de
     * registros que serán almacenados.
     *
     * @param numRegistros Número de registros a ser almacenados en base de
     * datos.
     */
    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
    }

    /**
     * Método accesor del atributo omittedSheets, el cual almacena el nombre de
     * las hojas omitidas al realizar la carga del archivo excel
     *
     * @return Regresa una lista con los nombres de las hojas omitidas
     */
    public List<String> getOmittedSheets() {
        return omittedSheets;
    }

    /**
     * Método accesor para el atributo omittedSheets el cuál se utiliza para
     * almacenar las hojas omitidas al cargar el archivo de excel
     *
     * @param omittedSheets lista de hojas omitidas al cargar el archivo de
     * excel
     */
    public void setOmittedSheets(List<String> omittedSheets) {
        this.omittedSheets = omittedSheets;
    }

    /**
     * Método accesor para el atributo loadedSheets en el cual se almacenan los
     * nombres de las hojas cargadas en el procesamiento del archivo excel
     *
     * @return Regresa una lista con los nombres de las hojas cargadas del
     * archivo excel
     */
    public List<String> getLoadedSheets() {
        return loadedSheets;
    }

    /**
     * Método accesor para el atributo loadedSheets en el cual se almacena una
     * lista de los nombres de las hojas de excel que fueron cargadas
     *
     * @param loadedSheets Lista de nombres de las hojas de excel cargadas
     */
    public void setLoadedSheets(List<String> loadedSheets) {
        this.loadedSheets = loadedSheets;
    }

    /**
     * Método accesor para el atributo errors en el cual se almacena una lista
     * de errores
     *
     * @return Lista de errores obtenidos en el análisis del archivo excel
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Método accesor para el atributo errors en el cual se almacena una lista
     * con los errores obtenidos durante el análisis del archivo excel
     *
     * @param errors Lista con los errores obtenidos durante el análisis del
     * archivo excel
     */
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    /**
     * Método accesor para el atributo cargasInfoPh donde se almacena una lista
     * con los registros obtenidos del proceso de análisis del archivo excel y
     * que serán guardados en base de datos
     *
     * @return Regresa un lista con los registros a guardar en base de datos.
     */
    public List<RvvdInfoPh> getCargasInfoPh() {
        return cargasInfoPh;
    }

    /**
     * Método accesor para el atributo cargasInfoPh donde se almacenan los
     * registros obtenidos en el proceso de análisis del archivo excel y que
     * serán almacenados en base de datos.
     *
     * @param cargasInfoPh Lista de registros a ser almacenados en base de datos
     */
    public void setCargasInfoPh(List<RvvdInfoPh> cargasInfoPh) {
        this.cargasInfoPh = cargasInfoPh;
    }

    /**
     * Método encargado de la lectura y análisis del archivo excel cargado en la
     * interfaz gráfica
     *
     * @param file archivo cargado mediante la interfaz gráfica, se obtiene de
     * primefaces
     * @param catPais pais seleccionado para realizar la carga
     * @param usuario Usuario quien realizará el análisis del archivo excel
     */
    public void analizeXls(UploadedFile file, ShareCatPais catPais, ShareUsuario usuario) {
        int numSheet = 0;
        try {
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
                        errors.clear();
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
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
            errors.add(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
            errors.add(ex.getMessage());
        }
    }

    /**
     * Método encargado de guardar el contenido de una hoja excel cargado en la
     * interfaz gráfica en la base de datos, realizando la llamada
     * correspondiente a los métodos que tienen acceso directo con la base de
     * datos
     *
     * @param file Archivo cargado desde la interfaz gráfica
     * @param stream flujo de bits correspondiente al archivo cargado desde la
     * interfaz gráfica
     * @param numRegistros número de registros a guardar
     * @return regresa verdadero si el guardado a base de datos fué exitoso, de
     * lo contrario retorna false
     */
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
                                break;
                            }
                        }
                    }
                    if (saveRows(numRegistros)) {
                        if (oPCPackage != null) {
                            oPCPackage.close();
                        }
                        errors.clear();
                    } else {
                        if (oPCPackage != null) {
                            oPCPackage.close();
                        }
                        flagOk = false;
                    }
                }
            }
        } catch (InvalidFormatException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
            errors.add(ex.getMessage());
            flagOk = false;
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
            errors.add(ex.getMessage());
            flagOk = false;
        } catch (SAXException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
            errors.add(ex.getMessage());
            flagOk = false;
        } catch (OpenXML4JException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
            errors.add(ex.getMessage());
            flagOk = false;
        } catch (XMLStreamException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
            errors.add(ex.getMessage());
            flagOk = false;
        }
        return flagOk;
    }

    /**
     * Método encargado de recorrer todos los registros de una hoja de excel y
     * verificar que la estructura de cada registro sea la adecuada para poder
     * ser almacenada en base de datos, si algún registro no cumple con la
     * estructura solicitada se almacena el error encontrado en el atributo
     * errors y el numero de registros encontrado se convierte en 0 y es
     * almacenado en el atributo rowNum
     *
     * @throws XMLStreamException Si la esctructura del archivo cargado no es el
     * adecuado se lanza excepción
     */
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
                                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
                                    errors.add("Approximately " + Character.toString((char) (65 + i)) + "" + (rowNum) + " cell in " + sheetName + " sheet have a invalid value [" + data[i] + "], the sheet has been omitted.");
                                    rowNum = 0L;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Método encargado de guardar a base de datos los registros contenidos en
     * la hoja de excel para ello realiza cargas de 10000 elementos contenidos
     * en una lista, cada registo corresponde a un objeto de la clase RvvdInfoPh
     *
     * @param numRegistros número de registros a ser almacenados en la base de
     * datos
     * @return regresa verdadero si el guardado a la base de datos fué exitoso,
     * de lo contrario regresa falso
     * @throws XMLStreamException Si la estructura del archivo cargado es
     * incorrecta se lanza excepción.
     */
    public boolean saveRows(long numRegistros) throws XMLStreamException {
        errors.clear();
        boolean flagOk = true;
        RvvdInfoPhDAO infoPhDAO = new RvvdInfoPhDAO();
        Session session = infoPhDAO.getSession();
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat formatoDelTextoGeneral = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoDelTextoInverso = new SimpleDateFormat("yyyyMMdd");
        List<RvvdInfoPh> cargas = new ArrayList<RvvdInfoPh>();
        Date fechaTemp;
        Calendar calendarTemp = Calendar.getInstance();
        RvvdInfoPh infoTemp = null;
        String elementName = "row";
        String[] data = null;
        rowNum = 0L;
        long cont = 0L;
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
                            fechaTemp = formatoDelTextoGeneral.parse("01/01/1900");
                            calendarTemp.setTime(fechaTemp);
                            if (data[0] != null && !data[0].contains("/")) {
                                calendarTemp.add(Calendar.DAY_OF_YEAR, Integer.parseInt(data[0]) - 2);
                                fechaTemp = formatoDelTextoGeneral.parse(formatoDelTexto.format(calendarTemp.getTime()));
                            }
                            infoTemp.setFecha(data[0] != null && data[0].contains("/") ? formatoDelTexto.parse(data[0]) : fechaTemp);
                        } catch (ParseException ex) {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
                            errors.add(ex.getMessage());
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
                        infoTemp.setIngresoNeto(data[11] != null && !data[11].equals("") ? Double.parseDouble(data[11]) : 0L);
                        //treceaba columna, numeric      
                        infoTemp.setCuota(data[12] != null && !data[12].equals("") ? Double.parseDouble(data[12]) : 0L);
                        //catorceaba columna, numeric         
                        infoTemp.setVentaTa(data[13] != null && !data[13].equals("") ? Double.parseDouble(data[13]) : 0L);

                        infoTemp.setIdTiempo(new BigInteger(formatoDelTextoInverso.format(infoTemp.getFecha())));
                        cargas.add(infoTemp);
                        if (rowNum % 10000 == 0 || rowNum == (numRegistros - 1)) {
                            System.out.println("entro a guardar en: " + rowNum);
                            for (RvvdInfoPh carga : cargas) {
                                session.save(carga);
                                if (cont % 100 == 0) {
                                    session.flush();
                                    session.clear();
                                }
                                cont++;
                            }
                            cargas.clear();
                        }

                    }
                }
                rowNum++;
            }
            session.getTransaction().commit();
            errors.clear();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
            errors.add(ex.getMessage());
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
        return flagOk;
    }

    /**
     * Método encargado de obtener los datos de las columnas de un registro de
     * excel
     *
     * @return Regresa un Array de Strings con los valores de un registro de
     * excel
     * @throws XMLStreamException si la estructura del archivo no es la adecuada
     * se lanza excepción.
     */
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

    /**
     * Método encargado de obtener el valor de una celda de excel
     *
     * @param cellType tipo de celda a ser leida
     * @return Regresa el valor de la celda de excel en un String
     * @throws XMLStreamException Si el formato del archivo es incorrecto se
     * lanza una excepción.
     */
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

    @Override
    public String toString() {
        return "XlsAnalizerSalesPh{" + "omittedSheets=" + omittedSheets + ", errors=" + errors + '}';
    }

}
