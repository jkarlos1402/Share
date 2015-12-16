package com.femsa.kof.util;

import com.femsa.kof.share.pojos.ShareCatPais;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.ServletContext;

public class ScriptAnalizer {

    public static boolean executeScritsShare(List<String> errors,ShareCatPais pais) {
        JPAUtil jpau = new JPAUtil();
        EntityManager em = jpau.getEntityManager();
        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String contextPathResources = sc.getRealPath("");
        File directorioBase = new File(contextPathResources + File.separator + "WEB-INF" + File.separator + "scripts" + File.separator);
        File[] ficheros = directorioBase.listFiles();
        List<String> instructions = null;
        EntityTransaction et = em.getTransaction();
        Query queryNativo = null;
        boolean flagOk = true;
        try {
            et.begin();
            for (int i = 0; i < ficheros.length; i++) {
                File fichero = ficheros[i];
                instructions = getInstructionsSQL(fichero, errors, pais);
                if (instructions != null) {
                    for (String instruction : instructions) {
                        queryNativo = em.createNativeQuery(instruction);
                        queryNativo.executeUpdate();
                    }
                }
            }
            et.commit();
        } catch (Exception e) { 
            e.printStackTrace();
            errors.add("Error running script: " + e.getMessage());
            if (et.isActive()) {
                et.rollback();
            }
            flagOk = false;
        } finally {
            em.clear();
            em.close();
        }
        return flagOk;
    }

    private static List<String> getInstructionsSQL(File scritpSQL, List<String> errors, ShareCatPais pais) throws IOException {
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
                        cadena = cadena.replaceAll("_CLAVECORTAPAIS_", "_"+pais.getClaveCorta().trim()+"_");
                        cadena = cadena.replaceAll("'NOMBREPAIS'", "'"+pais.getNombre().trim()+"'");
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

    public ScriptAnalizer() {
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
