/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21;

import com.formdev.flatlaf.FlatIntelliJLaf;
import edu.ues21.cansat21.control.PrincipalController;
import edu.ues21.cansat21.modelo.Helper;
import edu.ues21.cansat21.vista.InterfazVista;
import edu.ues21.cansat21.vista.Principal;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

/**
 * La clase que inicia la aplicación
 *
 * @author agustin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            UIManager.put("Button.arc", 0);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        IconFontSwing.register(FontAwesome.getIconFont());

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                InterfazVista vista = new Principal();
                PrincipalController ctrl = new PrincipalController(vista, null);
                vista.setControlador(ctrl);
                vista.iniciaVista();
            }
        });
    }

}
