/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21.vista;

import edu.ues21.cansat21.control.Controlador;
import edu.ues21.cansat21.modelo.ArchivoSeleccionado;
import java.io.File;




/**
 *
 * @author agustin
 */
public interface InterfazVista  {

    default void setControlador(Controlador c){
        
    }

    default void iniciaVista(){
        
    }

    default void imprimeMensaje(Exception... e){
        
    }
    
    default void limpiaVista(){
        
    }
    
    default File [] seleccionarArchivos(){
        return null;
    }
    
    default void cargarComboArchivosSel(ArchivoSeleccionado archivosSel) {
        
    }
    
    default void quitarItemComboArchivoSel() {
        
    }
    
    default String pathArchivoSeleccionado(){
        return null;
    }
    
    default void cierraSplitPane(int posicion) {
        
    }
}
