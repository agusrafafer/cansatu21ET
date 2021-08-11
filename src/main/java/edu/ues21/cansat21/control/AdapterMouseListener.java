/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21.control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Esta interfaz esta funcionando como un Adaptador de MouseListener
 * para que cada método tenga una implementación por defecto de manera 
 * de no <b>ensuciar</b> la clase PrincipalController con métodos vacios. 
 * @author agustin
 */
public interface AdapterMouseListener extends MouseListener{
    @Override
    default void mouseClicked(MouseEvent me) {

    }

    @Override
    default void mousePressed(MouseEvent me) {
        
    }

    @Override
    default void mouseReleased(MouseEvent me) {
        
    }

    @Override
    default void mouseEntered(MouseEvent me) {
        
    }

    @Override
    default void mouseExited(MouseEvent me) {
        
    }
}
