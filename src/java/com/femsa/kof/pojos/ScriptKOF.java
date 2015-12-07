package com.femsa.kof.pojos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScriptKOF implements Serializable {

    private String name;
    private String ruta;

    public String getName() {

        return name.replaceAll(".sql", "");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

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

    public boolean saveText(String script) throws IOException {
        FileWriter fw = null;
        fw = new FileWriter(this.ruta);
        fw.write(script);
        fw.close();
        return true;
    }
}
