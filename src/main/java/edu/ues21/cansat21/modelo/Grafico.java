/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21.modelo;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.jfree.chart.ChartPanel;

/**
 * Esta clase es abstracta y no debe ser instanciada.
 * La misma conforma un patrón Command que ayuda a poder construir cada gráfico que desee verse en 
 * la interfaz principal.
 * @author agustin
 */
public abstract class Grafico {

    /**
     * Método Command que debe ser implementado en los "Commands" concretos 
     * para poder construir el gráfico que se desee.
     * @param listaValores Una lista con los valores leidos desde el archivo CSV
     * @param panel El panel que contiene el grafico que puede existir o no.
     * @param nombreSerie El nombre de la serie a graficar.
     * @return Devuelve un panel con el gráfico construido.
     */
    public abstract ChartPanel graficar(List <FormatoCsv> listaValores, Component panel, String nombreSerie);
    
    /**
     * Método que esta aplicando el patrón Factory method utilizando reflection de Java.
     * El mismo es usado en el método drop de PrincipalController para poder 
     * obtener una instancia del gráfico que se desee construir.
     * @param nombreClase El nombre de la clase del gráfico a construir
     * @return Una instancia concreta de un gráfico
     */
    public static Grafico fabircarGrafico(String nombreClase) {
        Grafico modelo = null;
        try {
            //Reflection de Java
            modelo = (Grafico) Class.forName(Grafico.class.getPackageName() + "." + nombreClase).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            System.err.println(e);
        }
        return modelo;
    }

}
