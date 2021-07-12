/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21.control;

import edu.ues21.cansat21.modelo.Grafico;
import edu.ues21.cansat21.modelo.Helper;
import edu.ues21.cansat21.vista.InterfazVista;
import edu.ues21.cansat21.vista.Principal;
import java.awt.BorderLayout;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import org.jfree.chart.ChartPanel;

/**
 * Clase que representa el controlado especifico que se encarga de manipular los
 * eventos que son recibidos por la interfaz gráfica denominada
 * <b>Principal.java</b>
 *
 * @author agustin
 */
public class PrincipalController extends Controlador {

    private static final long serialVersionUID = 1L;

    String claseGrafico;

    public PrincipalController(InterfazVista vista, Grafico modelo) {
        VISTA = vista;
        MODELO = modelo;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        switch (ae.getActionCommand()) {
            case "ELEGIR_CSV":
                String path = ((Principal) VISTA).seleccionarArchivo();
                if (path == null) {
                    ((Principal) VISTA).imprimeMensaje(new Exception("Debe seleccionar un archivo"));
                    ((Principal) VISTA).limpiaVista();
                    return;
                }

                Helper ayuda = new Helper();
                if (!ayuda.esArchivoValido(path)) {
                    ((Principal) VISTA).imprimeMensaje(new Exception("Ese no es un archivo valido"));
                    ((Principal) VISTA).limpiaVista();
                } 
                break;
        }

    }

    /**
     * *
     * Método perteneciente a la interfaz MouseMotionListener. Detecta cuando un
     * componente (boton en este caso) es arrastrado. Como los botones no
     * soportan en las interfaz de escritorio nativamente en cualquier SO ser
     * arrastrados y soltados lo que se hace es tomar el "handler" del boton
     * (componente) y exportarlo avisandole al SO que el elemento que se
     * arrastra, con el mouse, debe ser considerado como si estuviesemos
     * arrastrando un archivo para copiarlo en una carpeta.
     *
     * @param me El evento de arrastrar con el mouse
     */
    @Override
    public void mouseDragged(MouseEvent me) {
        var c = (JComponent) me.getSource();
        //El handler
        var handler = c.getTransferHandler();
        //Exportacion del evento
        handler.exportAsDrag(c, me, TransferHandler.COPY);
    }

    /**
     * *
     * Método perteneciente a la interfaz MouseMotionListener. Lo que hace es
     * dectectar que boton se esta "moviendo" para poder obtener el
     * ActionCommand asociado al mismo y así saber que clase de gráfico
     * consrtruir con el patrón Factory method de la clase Grafico
     *
     * @param me
     */
    @Override
    public void mouseMoved(MouseEvent me) {
        JButton btn = (JButton) me.getComponent();
        claseGrafico = null;
        if (btn != null) {
            claseGrafico = btn.getActionCommand();
        }
    }

    /**
     * *
     * Método que detecta el evento de soltar un boton sobre la pantalla
     * principal.
     *
     * @param evt El evento que origino el soltado del boton sobre la ventana
     * principal.
     */
    @Override
    public synchronized void drop(DropTargetDropEvent evt) {
        //Si no se arrastra un boton que tenga la propiedad ActionCommand
        //seteada se termina el método
        if (claseGrafico == null) {
            return;
        }
        if (((Principal) VISTA).pathArchivoSeleccionado() == null) {
            ((Principal) VISTA).imprimeMensaje(new Exception("No selecciono ningún archivo"));
            return;
        }
        DropTarget dropTarget = (java.awt.dnd.DropTarget) evt.getSource();
        JPanel panel = (JPanel) dropTarget.getComponent();

        //A partir de aquí se aplican los patrones Factory method y Command
        //para lograr construir el grafico deseado
        Helper ayuda = new Helper();
        Grafico grafico = Grafico.fabircarGrafico(claseGrafico);
        ChartPanel chartPanel = grafico.graficar(ayuda.listarValores(((Principal) VISTA).pathArchivoSeleccionado()));
        chartPanel.setMouseWheelEnabled(true);
        
        JMenuItem menuQuitar = new JMenuItem("Quitar");
        //menuQuitar.setActionCommand("QUITAR");
        menuQuitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
            }
        });
        chartPanel.getPopupMenu().add(menuQuitar);
        panel.removeAll();
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }
}
