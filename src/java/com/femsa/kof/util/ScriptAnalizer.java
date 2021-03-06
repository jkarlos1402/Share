package com.femsa.kof.util;

import com.femsa.kof.share.pojos.ShareCatPais;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author TMXIDSJPINAM
 */
public class ScriptAnalizer {

    private static final String MSG_ERROR_TITULO = "Mensaje de error...";

    /**
     *
     */
    public ScriptAnalizer() {
    }

    /**
     * Permite la ejecución de los scripts posteriores a la carga de información
     *
     * @param errors En el se almacenan los errores encontrados al ejecutar los
     * scripts
     * @param pais Pais a ejecutar
     * @return en caso de éxito regresa verdadero, en caso contrario regresa
     * falso
     */
    public static boolean executeScritsShare(List<String> errors, ShareCatPais pais) {
        HibernateUtil hibernateUtil = new HibernateUtil();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String contextPathResources = sc.getRealPath("");
        File directorioBase = new File(contextPathResources + File.separator + "WEB-INF" + File.separator + "scripts" + File.separator);
        File[] ficheros = directorioBase.listFiles();
        List<String> instructions = null;
        session.beginTransaction();
        Query queryNativo = null;
        boolean flagOk = true;
        try {
            for (int i = 0; i < ficheros.length; i++) {
                File fichero = ficheros[i];
                instructions = getInstructionsSQL(fichero, errors, pais);
                if (instructions != null) {
                    for (String instruction : instructions) {
//                        System.out.println("instruccion: "+instruction);
                        queryNativo = session.createSQLQuery(instruction);
                        queryNativo.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            flagOk = false;
            Logger.getLogger(ScriptAnalizer.class.getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
            errors.add("Error running script: " + e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
        } finally {
            session.flush();
            session.clear();
            session.close();
            hibernateUtil.closeSessionFactory();
        }
        return flagOk;
    }

    /**
     * Obtiene el número de sentencias sql contenidas en los scripts
     *
     * @param errors Almacena los errores encontrados
     * @param pais Pais a ejecutar
     * @return regresa el número de sentencias encontradas
     */
    public static int obtieneNumSentencias(List<String> errors, ShareCatPais pais) {
        int numInstrucciones = 0;
        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String contextPathResources = sc.getRealPath("");
        File directorioBase = new File(contextPathResources + File.separator + "WEB-INF" + File.separator + "scripts" + File.separator);
        File[] ficheros = directorioBase.listFiles();

        boolean flagOk = true;
        try {
            for (int i = 0; i < ficheros.length; i++) {
                File fichero = ficheros[i];
                numInstrucciones += getInstructionsSQL(fichero, errors, pais).size();
            }
        } catch (Exception e) {
            Logger.getLogger(ScriptAnalizer.class.getName()).log(Level.SEVERE, MSG_ERROR_TITULO, e);
        }
        return numInstrucciones;
    }

    /**
     * Obtiene una lista con las instrucciones sql encontradas en un archivo sql
     *
     * @param scritpSQL Archivo sql a leer
     * @param errors se almacenan los errores encontrados al leer el archivo sql
     * @param pais Pais a ejecutar
     * @return Regresa una lista con las sentencias sql encontradas
     * @throws IOException lanza excepción en caso de error de lectura/escritura
     *
     */
    public static List<String> getInstructionsSQL(File scritpSQL, List<String> errors, ShareCatPais pais) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        FileReader f = null;
        BufferedReader b = null;
        String cadena = "";
        String nombre = scritpSQL.getAbsoluteFile().getName().trim();
        int indexComentario = 0;
        List<String> statements = null;
        if (getExtension(nombre).equalsIgnoreCase("sql")) {
            try {
                f = new FileReader(scritpSQL);
                b = new BufferedReader(f);
                while ((cadena = b.readLine()) != null) {
                    cadena = cadena.trim();
                    if (!cadena.startsWith("/*", 0) && !cadena.startsWith("*", 0) && !cadena.startsWith("--", 0) && !cadena.equals("") && !cadena.equalsIgnoreCase("exit;")) {
                        if (cadena.contains("/*")) {
                            indexComentario = cadena.indexOf("/*");
                            cadena = cadena.substring(0, indexComentario);
                        }
                        cadena = cadena.replaceAll("_CLAVECORTAPAIS_", "_" + pais.getClaveCorta().trim() + "_");
                        cadena = cadena.replaceAll("'NOMBREPAIS'", "'" + pais.getNombre().trim() + "'");
                        stringBuilder.append(cadena);
                        stringBuilder.append(" ");
                        if (cadena.endsWith(";")) {
                            if (statements == null) {
                                statements = new ArrayList<String>();
                            }
                            statements.add(stringBuilder.toString().replaceAll(";", "").trim());
                            stringBuilder = new StringBuilder();
                        }
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(ScriptAnalizer.class.getName()).log(Level.SEVERE, MSG_ERROR_TITULO, ex);
                errors.add("Error trying to open the file " + nombre);
            } finally {
                if (b != null) {
                    b.close();
                }
                if (f != null) {
                    f.close();
                }

            }

        }
        return statements;
    }

    /**
     * Obtiene la extensión de un archivo
     *
     * @param filename nombre del archivo
     * @return Regresda la extensión del archivo
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
