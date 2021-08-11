/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21.control;

import edu.ues21.cansat21.modelo.Grafico;
import edu.ues21.cansat21.vista.InterfazVista;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;

/**
 * Los demas controladores deben extenderse de esta clase para recibir la vista
 * y el modelo a manipular. En este caso el controlador debera implementar los
 * métodos de las interfaz ActionListener y MouseMotionListener.
 *
 * @author agustin
 */
public abstract class Controlador extends DropTarget implements ActionListener, MouseMotionListener, AdapterMouseListener {

    private static final long serialVersionUID = 1L;
    
    InterfazVista VISTA = null;
    Grafico MODELO = null;
}
