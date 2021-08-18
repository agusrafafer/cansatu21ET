/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21.modelo;

import com.csvreader.CsvReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene los métodos de ayuda o soporte para otras clases
 * @author agustin
 */
public class Helper {

    /***
     * Método que determina si el archivo seleccionado tiene el formato CSV
     * requerido
     * @param pathArchivoCsv La ruta del archivo a validar
     * @return Devuelve true si es un  archivo correcto
     */
    public boolean esArchivoValido(String pathArchivoCsv) {
        BufferedReader br = null;
        InputStream inputStream = null;
        boolean valido = false;
        try {
            File file = new File(pathArchivoCsv);
            inputStream = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            int cont = 0;
            while ((line = br.readLine()) != null && cont == 0) {
                String[] cabeceras = line.split(",");
                for (String cabecera : cabeceras) {
                    if (cabecera.equals("Temperatura (*C)") || cabecera.equals("Presion (mb)")
                            || cabecera.equals("H (m s.n.m.)") || cabecera.equals("AX")
                            || cabecera.equals("AY")  || cabecera.equals("AZ")
                            || cabecera.equals("GX") || cabecera.equals("GY")
                            || cabecera.equals("GZ")) {
                        valido = true;
                        break;
                    }
                }
                cont++;
            }
            if (cont == 0) {
                valido = false;
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                br.close();
                inputStream.close();
            } catch (NullPointerException | IOException e) {
                System.err.println(e);
            }
        }
        return valido;
    }

    /**
     * Método que toma un archivo CSV y devueve una lista 
     * con objetos pertenecientes a la clase <b>FormatoCsv</b>
     * @param pathArchivoCsv La ruta del archivo CSV
     * @return Devuelve una lista de objetos del tipo FormatoCsv
     */
    public List<FormatoCsv> listarValores(String pathArchivoCsv) {
        List<FormatoCsv> listaValores = new ArrayList<>();
        CsvReader csv = null;
        try {
            csv = new CsvReader(pathArchivoCsv);
            //Salto la primer linea para que no sea leida al cargar la lista
            csv.readHeaders();
            String temp;
            String pres;
            String h;
            String ax;
            String ay;
            String az;
            String gx;
            String gy;
            String gz;
            FormatoCsv registro;
            while (csv.readRecord()) {
                //Las cabeceras del CSV son:
                //Temperatura (*C); Presion (mb); H (m s.n.m.); AX; AY; AZ; GX; GY; GZ
                temp = csv.get("Temperatura (*C)");
                pres = csv.get("Presion (mb)");
                h = csv.get("H (m s.n.m.)");
                ax = csv.get("AX");
                ay = csv.get("AY");
                az = csv.get("AZ");
                gx = csv.get("GX");
                gy = csv.get("GY");
                gz = csv.get("GZ");

                registro = new FormatoCsv(Float.parseFloat(temp), Float.parseFloat(pres), Float.parseFloat(h), Integer.parseInt(ax), Integer.parseInt(ay), Integer.parseInt(az), Integer.parseInt(gx), Integer.parseInt(gy), Integer.parseInt(gz));
                listaValores.add(registro);
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                csv.close();
            } catch (NullPointerException e) {
                System.err.println(e);
            }
        }

        return listaValores;
    }

}
