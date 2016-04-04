package com.femsa.kof.util;

import com.femsa.kof.managedbeans.MainBean;
import com.femsa.kof.share.dao.ShareTmpAllInfoCargaDAO;
import com.femsa.kof.share.pojos.ShareCatCategorias;
import com.femsa.kof.share.pojos.ShareCatPais;
import com.femsa.kof.share.pojos.ShareTmpAllInfoCarga;
import com.femsa.kof.share.pojos.ShareUsuario;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.hibernate.Query;
import org.hibernate.Session;
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

    private ReadOnlySharedStringsTable stringsTable;
    private XMLStreamReader xmlReader;
    private String sheetName;
    private long numRegistros = 0L;
    private List<Long> numRegistrosPorHoja;
    private long rowNum = 0;

    private String expReg = "[a-zA-Z]{3}\\s\\d{4}|[a-zA-Z]{3}[/][a-zA-Z]{3}\\s\\d{4}";
    private boolean bndErrorSheet = false;

    /**
     * Constructor sin parámetros que inicializa los atributos necesarios para
     * el manejo de un archivo de excel correspondiente a Share
     */
    public XlsAnalizer() {
        omittedSheets = new ArrayList<String>();
        loadedSheets = new ArrayList<String>();
        errors = new ArrayList<String>();
    }

    public List<Long> getNumRegistrosPorHoja() {
        return numRegistrosPorHoja;
    }

    public void setNumRegistrosPorHoja(List<Long> numRegistrosPorHoja) {
        this.numRegistrosPorHoja = numRegistrosPorHoja;
    }

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
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
     *
     */
    public void analizeXls(UploadedFile file, ShareCatPais catPais, ShareUsuario usuario, MainBean mainBean, List<Long> registrosPörPagina) {
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        List<ShareCatCategorias> catCategorias = (List<ShareCatCategorias>) context.getAttribute("categories_catalog");
        ShareCatCategorias categoria;
        ShareCatCategorias categoriaTmp;
        OPCPackage oPCPackage = null;
        numRegistros = 0L;
        if (numRegistrosPorHoja != null) {
            numRegistrosPorHoja.clear();
        }
        if (omittedSheets != null) {
            omittedSheets.clear();
        }
        if (loadedSheets != null) {
            loadedSheets.clear();
        }

        try {
            oPCPackage = OPCPackage.open(file.getInputstream());
            stringsTable = new ReadOnlySharedStringsTable(oPCPackage);

            XSSFReader xssfReader = new XSSFReader(oPCPackage);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();

            while (iter.hasNext()) {
                InputStream inputStream = iter.next();
                sheetName = iter.getSheetName();
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
                categoria = null;
                categoriaTmp = new ShareCatCategorias();
                categoriaTmp.setCategoria(sheetName.trim());
                if (catCategorias != null) {
                    for (ShareCatCategorias catCategoria : catCategorias) {
                        if (catCategoria.equals(categoriaTmp)) {
                            categoria = catCategoria;
                        }
                    }
                }
                //Si existe la categoria se procede a analizar la hoja
                if (categoria != null) {
                    readRows();
                    if (rowNum == 0 && bndErrorSheet) {
                        omittedSheets.add(sheetName.trim().toUpperCase() + ", One or more cells are incorrect");
                    } else if (rowNum == 0 && !bndErrorSheet) {
                        omittedSheets.add(sheetName.trim().toUpperCase() + ", empty");
                    } else {
                        loadedSheets.add(sheetName + ", " + numRegistros + " records found.");
                    }
                } else {
                    omittedSheets.add(sheetName.trim().toUpperCase() + ", not valid, category not found.");
                }
            }
            mainBean.setPorcentajeAvance(100);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
            errors.add(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
            errors.add(ex.getMessage());
        } finally {
            try {
                if (oPCPackage != null) {
                    oPCPackage.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
                errors.add(ex.getMessage());
            }
        }
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
        numRegistros = 0L;
        String elementName = "row";
        String[] data;
        rowNum = 0L;
        Pattern pat = Pattern.compile(expReg);
        Matcher mat;
        bndErrorSheet = false;
        String[] fechasTmp;
        while (xmlReader.hasNext()) {
            xmlReader.next();
            if (xmlReader.isStartElement()) {
                if (xmlReader.getLocalName().equals(elementName)) {
                    rowNum++;
                    data = getDataRow();
                    if (rowNum > 1 && data != null && !"".equalsIgnoreCase(data[0].trim())) {
                        mat = pat.matcher(data[0].trim());
                        if (!mat.matches()) {
                            errors.add("Approximately " + Character.toString((char) (65 + 0)) + "" + (rowNum) + " cell in " + sheetName + " sheet have a invalid value [" + data[0] + "], the sheet has been omitted.");
                            bndErrorSheet = true;
                        } else {
                            fechasTmp = data[0].trim().split("/");
                            numRegistros++;
                            if (fechasTmp.length > 1) {
                                numRegistros++;
                            }
                        }
                    }
                }
            }
        }
        if (bndErrorSheet) {
            rowNum = 0L;
        }
        if (numRegistrosPorHoja == null) {
            numRegistrosPorHoja = new ArrayList<Long>();
        }
        numRegistrosPorHoja.add(numRegistros);
    }

    public boolean saveExcel(InputStream stream, List<Long> numRegistros, ShareCatPais catPais, MainBean mainBean) {
        mainBean.setPorcentajeAvance(0);
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        List<ShareCatCategorias> catCategorias = (List<ShareCatCategorias>) context.getAttribute("categories_catalog");
        ShareCatCategorias categoria;
        ShareCatCategorias categoriaTmp;
        OPCPackage oPCPackage = null;
        ShareTmpAllInfoCarga infoTemp;
        ShareTmpAllInfoCarga infoTemp2 = null;
        List<ShareTmpAllInfoCarga> cargas;
        ShareTmpAllInfoCargaDAO shareTmpAllInfoCargaDAO = new ShareTmpAllInfoCargaDAO();
        Session session = shareTmpAllInfoCargaDAO.getSession();
        Query queryNativo;
        String[] fechasTmp;
        String year;
        List<String> fechas = new ArrayList<String>();
        long totalRegistros = 0;
        int finAnho;
        boolean bndBimestre;
        long cont = 0L;
        int contHoja = 0;
        float valorFlotante;
        int numReg = 0;
        int numRegIncremento = 0;
        boolean bndOk = true;
        if (numRegistros != null) {
            for (Long numRegHoja : numRegistros) {
                totalRegistros += numRegHoja;
            }
        }
        try {
            session.beginTransaction();
            queryNativo = session.createSQLQuery("TRUNCATE TABLE SHARE_TMP_ALL_INFO_CARGA");
            queryNativo.executeUpdate();
            oPCPackage = OPCPackage.open(stream);
            stringsTable = new ReadOnlySharedStringsTable(oPCPackage);
            XSSFReader xssfReader = new XSSFReader(oPCPackage);
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
            while (iter.hasNext()) {
                InputStream inputStream = iter.next();
                sheetName = iter.getSheetName();
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
                categoria = null;
                categoriaTmp = new ShareCatCategorias();
                categoriaTmp.setCategoria(sheetName.trim());
                if (catCategorias != null) {
                    for (ShareCatCategorias catCategoria : catCategorias) {
                        if (catCategoria.equals(categoriaTmp)) {
                            categoria = catCategoria;
                        }
                    }
                }
                //Si existe la categoria se procede a guardar la hoja
                if (categoria != null) {
                    ////////////////////////////////////////////////////////////
                    rowNum = 0L;
                    numReg = 0;
                    cargas = new ArrayList<ShareTmpAllInfoCarga>();
                    String elementName = "row";
                    String[] data;
                    String[] mesAnho;

                    while (xmlReader.hasNext()) {
                        xmlReader.next();
                        if (xmlReader.isStartElement()) {
                            if (xmlReader.getLocalName().equals(elementName)) {
                                rowNum++;
                                data = getDataRow();
                                /////////////
                                //primera columna FECHA (STRING O numeric)
                                if (rowNum > 1 && data != null && !"".equalsIgnoreCase(data[0].trim())) {

                                    //columna date
                                    year = data[0].trim().substring(data[0].trim().length() - 5, data[0].trim().length());
                                    fechasTmp = data[0].trim().split("/");
                                    if (fechasTmp.length > 1) {
                                        bndBimestre = true;
                                    } else {
                                        bndBimestre = false;
                                        infoTemp2 = null;
                                    }
                                    fechas.clear();
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
                                        if (mes.equalsIgnoreCase(mesesIng[11]) && bndBimestre) {
                                            finAnho = 1;
                                        } else {
                                            finAnho = 0;
                                        }
                                        String fechaObtenida = i == 0 && fechasTmp.length > 1 ? mes + " " + (Integer.parseInt(year.trim()) - finAnho) : fechasTmp[i].replaceFirst(mesReplace, mes);
                                        fechas.add(fechaObtenida.toUpperCase());
                                        cont++;
                                    }
                                    infoTemp = new ShareTmpAllInfoCarga();
                                    infoTemp.setPais(catPais.getNombre());
                                    infoTemp.setCategoria(categoria.getCategoria());
                                    infoTemp.setGrupoCategoria(categoria.getFkGrupoCategoria().getGrupoCategoria());
                                    infoTemp.setFecha(fechas.get(0).trim().toUpperCase());
                                    infoTemp.setAnio(new Short(year.trim()));
                                    mesAnho = infoTemp.getFecha().split(" ");
                                    for (int i = 0; i < mesesIng.length; i++) {
                                        if (mesesIng[i].equalsIgnoreCase(mesAnho[0])) {
                                            infoTemp.setMes((i + 1) + "");
                                        }
                                    }
                                    if (bndBimestre) {
                                        infoTemp2 = new ShareTmpAllInfoCarga();
                                        infoTemp2.setPais(catPais.getNombre());
                                        infoTemp2.setCategoria(categoria.getCategoria());
                                        infoTemp2.setGrupoCategoria(categoria.getFkGrupoCategoria().getGrupoCategoria());
                                        infoTemp2.setFecha(fechas.get(1).trim().toUpperCase());
                                        infoTemp2.setAnio(new Short(year.trim()));
                                        mesAnho = infoTemp2.getFecha().split(" ");
                                        for (int i = 0; i < mesesIng.length; i++) {
                                            if (mesesIng[i].equalsIgnoreCase(mesAnho[0])) {
                                                infoTemp2.setMes((i + 1) + "");
                                            }
                                        }
                                    }

                                    //columna Business Unit
                                    if (data.length >= 2) {
                                        infoTemp.setUnidadNegocio(data[1].trim().toUpperCase());
                                        if (bndBimestre) {
                                            infoTemp2.setUnidadNegocio(data[1].trim().toUpperCase());
                                        }
                                    }

                                    //columna channel
                                    if (data.length >= 3) {
                                        infoTemp.setCanal(data[2].trim().toUpperCase());
                                        if (bndBimestre) {
                                            infoTemp2.setCanal(data[2].trim().toUpperCase());
                                        }
                                    }

                                    //columna subchannel
                                    if (data.length >= 4) {
                                        infoTemp.setSubcanal(data[3].trim().toUpperCase());
                                        if (bndBimestre) {
                                            infoTemp2.setSubcanal(data[3].trim().toUpperCase());
                                        }
                                    }

                                    //columna Operative Unit Code
                                    if (data.length >= 5) {
                                        infoTemp.setUnidadOperativa(data[4].trim().toUpperCase());
                                        if (bndBimestre) {
                                            infoTemp2.setUnidadOperativa(data[4].trim().toUpperCase());
                                        }
                                    }

                                    //columna Region
                                    if (data.length >= 6) {
                                        infoTemp.setRegion(data[5].trim().toUpperCase());
                                        if (bndBimestre) {
                                            infoTemp2.setRegion(data[5].trim().toUpperCase());
                                        }
                                    }

                                    //columna Zone
                                    if (data.length >= 7) {
                                        infoTemp.setZona(data[6].trim().toUpperCase());
                                        if (bndBimestre) {
                                            infoTemp2.setZona(data[6].trim().toUpperCase());
                                        }
                                    }

                                    //columna Producer
                                    if (data.length >= 8) {
                                        infoTemp.setFabricante(data[7].trim().toUpperCase());
                                        if (bndBimestre) {
                                            infoTemp.setFabricante(data[7].trim().toUpperCase());
                                        }
                                    }

                                    //columna Brand
                                    if (data.length >= 9) {
                                        infoTemp.setMarca(data[8].trim().toUpperCase());
                                        if (bndBimestre) {
                                            infoTemp2.setMarca(data[8].trim().toUpperCase());
                                        }
                                    }

                                    //columna flavor
                                    if (data.length >= 10) {
                                        infoTemp.setSabor(data[9].trim().toUpperCase());
                                        if (bndBimestre) {
                                            infoTemp2.setSabor(data[9].trim().toUpperCase());
                                        }
                                    }

                                    //columna size
                                    if (data.length >= 11) {
                                        infoTemp.setTamano(data[10].trim().toUpperCase());
                                        if (bndBimestre) {
                                            infoTemp2.setTamano(data[10].trim().toUpperCase());
                                        }
                                    }

                                    //columna package
                                    if (data.length >= 12) {
                                        infoTemp.setEmpaque(data[11].trim().toUpperCase());
                                        if (bndBimestre) {
                                            infoTemp2.setEmpaque(data[11].trim().toUpperCase());
                                        }
                                    }

                                    //columna Returnability
                                    if (data.length >= 13) {
                                        infoTemp.setRetornabilidad(data[12].trim().toUpperCase());
                                        if (bndBimestre) {
                                            infoTemp2.setRetornabilidad(data[12].trim().toUpperCase());
                                        }
                                    }

                                    //columna Volume
                                    if (data.length >= 14) {
                                        valorFlotante = data[13] != null && !"".equals(data[13].trim()) ? Float.parseFloat(data[13].trim()) : 0f;
                                        if (bndBimestre) {
                                            infoTemp.setVolumenMes(valorFlotante / 2.0f);
                                            infoTemp2.setVolumenMes(valorFlotante - (valorFlotante / 2.0f));
                                        } else {
                                            infoTemp.setVolumenMes(valorFlotante);
                                        }
                                    }

                                    //columna value
                                    if (data.length >= 15) {
                                        valorFlotante = data[14] != null && !"".equals(data[14].trim()) ? Float.parseFloat(data[14].trim()) : 0f;
                                        if (bndBimestre) {
                                            infoTemp.setVentaMes(valorFlotante / 2.0f);
                                            infoTemp2.setVentaMes(valorFlotante - (valorFlotante / 2.0f));
                                        } else {
                                            infoTemp.setVentaMes(valorFlotante);
                                        }
                                    }
                                    cargas.add(infoTemp);
                                    if (bndBimestre) {
                                        cargas.add(infoTemp2);
                                    }
                                    numReg++;
                                    numRegIncremento++;
                                    if (numReg % 1000 == 0 || numReg == numRegistros.get(contHoja)) {
                                        mainBean.setPorcentajeAvance((int) ((numRegIncremento * 100) / (totalRegistros * 0.9)));
                                        for (ShareTmpAllInfoCarga carga : cargas) {
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
                        }
                        //rowNum++;
                    }
                    contHoja++;
                    ////////////////////////////////////////////////////////////
                } else {
                    omittedSheets.add(sheetName.trim().toUpperCase() + ", not valid, category not found.");
                }
            }
            session.getTransaction().commit();
            queryNativo = session.createSQLQuery("UPDATE SHARE_TMP_ALL_INFO_CARGA SET TIEMPO = (CASE WHEN (SELECT ID_TIEMPO FROM SHARE_DIM_TIEMPO WHERE GV_ANIO = SHARE_TMP_ALL_INFO_CARGA.ANIO AND GV_NMES = SHARE_TMP_ALL_INFO_CARGA.MES) IS NULL THEN 0 ELSE (SELECT ID_TIEMPO FROM SHARE_DIM_TIEMPO WHERE GV_ANIO = SHARE_TMP_ALL_INFO_CARGA.ANIO AND GV_NMES = SHARE_TMP_ALL_INFO_CARGA.MES) END)");
            queryNativo.executeUpdate();
            queryNativo = session.createSQLQuery("DELETE FROM " + catPais.getNombreTabla() + " WHERE PAIS = '" + catPais.getNombre() + "' AND FECHA IN (SELECT DISTINCT(FECHA) FROM SHARE_TMP_ALL_INFO_CARGA) AND CATEGORIA IN (SELECT DISTINCT(CATEGORIA) FROM SHARE_TMP_ALL_INFO_CARGA)");
            queryNativo.executeUpdate();
            queryNativo = session.createSQLQuery("INSERT INTO " + catPais.getNombreTabla() + " SELECT FECHA,ANIO,MES,TIEMPO,PAIS,UNIDAD_NEGOCIO,CANAL,SUBCANAL,UNIDAD_OPERATIVA,REGION,ZONA,GRUPO_CATEGORIA,CATEGORIA,FABRICANTE,MARCA,SABOR,TAMANO,EMPAQUE,RETORNABILIDAD,VOLUMEN_MES,VENTA_MES FROM SHARE_TMP_ALL_INFO_CARGA");
            queryNativo.executeUpdate();
            queryNativo = session.createSQLQuery("COMMIT");
            queryNativo.executeUpdate();
            queryNativo = session.createSQLQuery("DROP SEQUENCE SHARE_SEQ_ALL_INFO_CARGA");
            queryNativo.executeUpdate();
            queryNativo = session.createSQLQuery("CREATE SEQUENCE SHARE_SEQ_ALL_INFO_CARGA INCREMENT BY 1 START WITH 1");
            queryNativo.executeUpdate();
            mainBean.setPorcentajeAvance(100);
        } catch (IOException ex) {
            bndOk = false;
            queryNativo = session.createSQLQuery("ROLLBACK");
            queryNativo.executeUpdate();
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
            errors.add(ex.getMessage());
        } catch (Exception ex) {
            bndOk = false;
            queryNativo = session.createSQLQuery("ROLLBACK");
            queryNativo.executeUpdate();
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
            errors.add(ex.getMessage());
        } finally {
            session.flush();
            session.clear();
            session.close();
            shareTmpAllInfoCargaDAO.getHibernateUtil().closeSessionFactory();
            try {
                if (oPCPackage != null) {
                    oPCPackage.close();
                }
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
                errors.add(ex.getMessage());
            }
        }
        return bndOk;
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
}
