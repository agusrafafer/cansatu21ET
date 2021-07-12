/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21.vista;

import edu.ues21.cansat21.control.Controlador;




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
    
    default String seleccionarArchivo(){
        return null;
    }
    
    default String pathArchivoSeleccionado(){
        return null;
    }
}
