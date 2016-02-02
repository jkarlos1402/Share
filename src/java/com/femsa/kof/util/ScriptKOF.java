package com.femsa.kof.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author TMXIDSJPINAM
 */
public class ScriptKOF implements Serializable {

    private String name;
    private String ruta;

    /**
     *
     * @return
     */
    public String getName() {

        return name.replaceAll(".sql", "");
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getRuta() {
        return ruta;
    }

    /**
     *
     * @param ruta
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    /**
     * Método que obtiene el contenido de un archivo que contiene un script sql
     * para ser colocado en la vista y pueda ser manipulado por el usuario
     *
     * @return Regresa un String con el contenido del archivo listo para ser
     * colocado en la vista
     * @throws IOException Si el archivo no existe se lanza una excepción
     */
    public String getTextScript() throws IOException {
        File scriptFile = new File(this.ruta);
        FileReader f = null;
        BufferedReader b = null;
        String cadena = null;
        String scriptText = "";
        f = new FileReader(scriptFile);
        b = new BufferedReader(f);
        while ((cadena = b.readLine()) != null) {
            scriptText += cadena.trim() + "\n";
        }

        if (b != null) {
            b.close();
        }
        if (f != null) {
            f.close();
        }
        return scriptText;
    }

    /**
     * Guarda los cambios realizados por el usuario al script sql en el
     * correspondiente archivo
     *
     * @param script Script modificado por el usuario
     * @return Si el guardado fué exitoso se regresa verdadero en caso contrario
     * se regresa falso
     * @throws IOException Si no se puede guardar el archivo se lanza una
     * excepción
     */
    public boolean saveText(String script) throws IOException {
        FileWriter fw = null;
        fw = new FileWriter(this.ruta);
        fw.write(script);
        fw.close();
        return true;
    }
}
