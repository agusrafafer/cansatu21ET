/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21.modelo;

/**
 * Clase que contien los atributos necesarios para poder manipular 
 * los archivos seleccionados desde el combo.
 * Es simplemente un POJO.
 * @author agustin
 */
public class ArchivoSeleccionado {
    private String nombreArchivo;
    private String pathArchivo;

    public ArchivoSeleccionado(String nombreArchivo, String pathArchivo) {
        this.nombreArchivo = nombreArchivo;
        this.pathArchivo = pathArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getPathArchivo() {
        return pathArchivo;
    }

    public void setPathArchivo(String pathArchivo) {
        this.pathArchivo = pathArchivo;
    }

    @Override
    public String toString() {
        return nombreArchivo;
    }
    
}
