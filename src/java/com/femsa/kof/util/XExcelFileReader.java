package com.femsa.kof.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

public class XExcelFileReader {

    private long rowNum = 0;
    private OPCPackage opcPkg;
    private ReadOnlySharedStringsTable stringsTable;
    private XMLStreamReader xmlReader;
    private List<String> errors = new ArrayList<String>();
    private String sheetName;

    public XExcelFileReader(InputStream in) throws Exception {
        opcPkg = OPCPackage.open(in);
        this.stringsTable = new ReadOnlySharedStringsTable(opcPkg);

        XSSFReader xssfReader = new XSSFReader(opcPkg);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        InputStream inputStream = iter.next();
        xmlReader = factory.createXMLStreamReader(inputStream);
        sheetName = iter.getSheetName();
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
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public long rowNum() {
        return rowNum;
    }

    public void readRows() throws XMLStreamException {
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
                        errors.add("Approximately " + (rowNum + 1) + " row in " + sheetName + " sheet have a invalid number of columns, the sheet has been omitted.");
                        rowNum = 0L;
                        break;
                    } else {
                        for (int i = 10; i < 14; i++) {
                            if (data[i] == null) {
                                errors.add("Approximately " + Character.toString((char) (65 + i)) + "" + (rowNum + 1) + " cell in " + sheetName + " sheet have a invalid value [" + data[i] + "], the sheet has been omitted.");
                                rowNum = 0L;
                                break;
                            } else {
                                try {
                                    Double.parseDouble(data[i]);
                                } catch (NumberFormatException e) {
                                    errors.add("Approximately " + Character.toString((char) (65 + i)) + "" + (rowNum + 1) + " cell in " + sheetName + " sheet have a invalid value [" + data[i] + "], the sheet has been omitted.");
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

    @Override
    protected void finalize() throws Throwable {
        if (opcPkg != null) {
            opcPkg.close();
        }
        super.finalize();
    }

}
