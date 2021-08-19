/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21.control;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Interfaz que "Adapta" el evento WindowListener para que la clase
 * que la implementa solo implemente los métodos que necesite.
 * @author agustin
 */
public interface AdaptadorWindowListener extends WindowListener {
    @Override
    default void windowClosing(WindowEvent we) {
        
    }

    @Override
    default void windowOpened(WindowEvent we) {
        
    }

    @Override
    default void windowClosed(WindowEvent we) {
        
    }

    @Override
    default void windowIconified(WindowEvent we) {
       
    }

    @Override
    default void windowDeiconified(WindowEvent we) {
       
    }

    @Override
    default void windowActivated(WindowEvent we) {
        
    }

    @Override
    default void windowDeactivated(WindowEvent we) {
       
    }
}
