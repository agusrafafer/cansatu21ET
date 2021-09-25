/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21.control;

import edu.ues21.cansat21.modelo.ArchivoSeleccionado;
import edu.ues21.cansat21.modelo.Grafico;
import edu.ues21.cansat21.modelo.Helper;
import edu.ues21.cansat21.vista.InterfazVista;
import edu.ues21.cansat21.vista.Principal;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
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
    String ipPublica;
    String idCliente = "-1";
    String caractCliente = "";

    public PrincipalController(InterfazVista vista, Grafico modelo) {
        VISTA = vista;
        MODELO = modelo;
        ipPublica = Helper.obtenerIpPublica();
        idCliente = Helper.getIdCliente(ipPublica);
        caractCliente = Helper.obtenerDatosSOCliente();
        if (idCliente.equals("-1")) {
            Helper.postCliente(ipPublica);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        switch (ae.getActionCommand()) {
            case "ELEGIR_CSV_ACCION":
                File[] archivos = ((Principal) VISTA).seleccionarArchivos();
                if (archivos == null) {
                    ((Principal) VISTA).imprimeMensaje(new Exception("Debe seleccionar al menos un archivo"));
                    ((Principal) VISTA).limpiaVista();
                    return;
                }
                Helper ayuda = new Helper();
                for (File archivo : archivos) {
                    if (!ayuda.esArchivoValido(archivo.getPath())) {
                        ((Principal) VISTA).imprimeMensaje(new Exception(archivo.getName() + " no es un archivo valido"));
                    } else {
                        ((Principal) VISTA).cargarComboArchivosSel(new ArchivoSeleccionado(archivo.getName(), archivo.getPath()));
                    }
                }
                break;

            case "BORRAR_ARCH_SEL_ACCION":
                ((Principal) VISTA).quitarItemComboArchivoSel();
                break;
            case "CONFIG_ACCION":
                ((Principal) VISTA).muestraMenuOpciones();
                ((Principal) VISTA).muestraDialogoMenuOpciones("Configuracion");
                break;
            case "AYUDA_ACCION":
                ((Principal) VISTA).muestraMenuOpciones();
                ((Principal) VISTA).muestraDialogoMenuOpciones("Ayuda");
                break;
            case "LICENCIA_ACCION":
                ((Principal) VISTA).muestraMenuOpciones();
                ((Principal) VISTA).muestraDialogoMenuOpciones("Licencia");
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
        //Si no se arrastra un boton (arrastable) que tenga la propiedad ActionCommand
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
        Component chartPanel = null;
        for (Component component : panel.getComponents()) {
            if (component instanceof ChartPanel) {
                chartPanel = component;
                break;
            }
        }

        //A partir de aquí se aplican los patrones Factory method y Command
        //para lograr construir el grafico deseado
        Helper ayuda = new Helper();
        Grafico grafico = Grafico.fabircarGrafico(claseGrafico);
        String pathArchivo = ((Principal) VISTA).pathArchivoSeleccionado();
        if (pathArchivo == null) {
            ((Principal) VISTA).imprimeMensaje(new Exception("Debe seleccionar al menos un archivo"));
            return;
        }
        String nombreArchivo = pathArchivo.substring(pathArchivo.lastIndexOf(File.separator) + 1);
        chartPanel = grafico.graficar(ayuda.listarValores(pathArchivo), chartPanel, nombreArchivo);

        boolean existeMenu = false;
        for (Component component : ((ChartPanel) chartPanel).getPopupMenu().getComponents()) {
            if (component instanceof JMenuItem) {
                if (((JMenuItem) component).getText().equals("Quitar")) {
                    existeMenu = true;
                    break;
                }
            }
        }
        if (!existeMenu) {
            ((ChartPanel) chartPanel).setMouseWheelEnabled(true);
            JMenuItem menuQuitar = new JMenuItem("Quitar");
            menuQuitar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();
                }
            });
            ((ChartPanel) chartPanel).getPopupMenu().add(menuQuitar);
            panel.removeAll();
            panel.add(chartPanel, BorderLayout.CENTER);
        }
        panel.revalidate();
        panel.repaint();

        if (!idCliente.equals("-1")) {
            try {
                Properties prop = Helper.cargarArchivoConfig();
                String compartir = (String) prop.get("compartirDatos");
                if (compartir.equals("SI")) {
                    Helper.postAuditoria(claseGrafico, caractCliente, idCliente);
                }
            } catch (IOException ex) {
                ((Principal) VISTA).imprimeMensaje(ex);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() instanceof JLabel) {
            String nombreLbl = ((JLabel) me.getSource()).getAccessibleContext().getAccessibleName();
            if (nombreLbl.equals("lblAmburguesa")) {
                ((Principal) VISTA).cierraSplitPane(0);
            } else if (nombreLbl.equals("lblOpciones")) {
                ((Principal) VISTA).muestraMenuOpciones();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if (me.getSource() instanceof JLabel) {
            String nombreLbl = ((JLabel) me.getSource()).getAccessibleContext().getAccessibleName();
            if (nombreLbl.equals("lblAmburguesa") || nombreLbl.equals("lblOpciones")) {
                ((Principal) VISTA).setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                ((Principal) VISTA).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                ((Principal) VISTA).muestraMenuOpciones();
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        ((Principal) VISTA).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void windowClosing(WindowEvent we) {
        ((Principal) VISTA).cierraSistema();
    }
}
