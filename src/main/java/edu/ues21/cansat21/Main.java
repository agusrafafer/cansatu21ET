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
import edu.ues21.cansat21.vista.Splash;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
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

        Splash splash = new Splash();
        splash.setLocationRelativeTo(null);
        splash.lblProgreso.setText("Ajustando entorno...");
        splash.setVisible(true);
        splash.getContentPane().setBackground(Color.WHITE);
        int cont = 0;
        splash.barraProgreso.setValue(cont);
        for (int i = 0; i < 5; i++) {
            cont += 20;
            splash.barraProgreso.setValue(cont);
            try {
                int nro = 0;
                Random r = new Random();
                nro = r.nextInt((500 - 450) + 1) + 450;
                Thread.sleep(nro);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            switch (i) {
                case 0:
                    splash.lblProgreso.setText("Cargando módulos...");
                    break;
                case 1:
                    splash.lblProgreso.setText("Cargando variables...");
                    break;
                case 2:
                    splash.lblProgreso.setText("Cargando configuración...");
                    break;
                case 3:
                    splash.lblProgreso.setText("Ya casí estamos listos...");
                    break;
                case 4:
                    splash.lblProgreso.setText("Creando entorno...");
                    break;
            }

        }
        splash.setVisible(false);
        splash.dispose();
        splash = null;

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                InterfazVista vista = new Principal();
                PrincipalController ctrl = new PrincipalController(vista, null);
                vista.setControlador(ctrl);
                vista.iniciaVista();
            }
        });
    }

}
