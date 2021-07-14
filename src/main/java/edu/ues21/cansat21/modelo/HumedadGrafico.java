/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21.modelo;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Clase que representa un Command concreto y que permite implementar la
 * funcionalidad necesaria para poder graficar en un ChartPanel (JFreeChart) el
 * gráfico para los datos de la humedad.
 *
 * @author agustin
 */
public class HumedadGrafico extends Grafico {

    /**
     * El método que debe ser implementado cada vez que se desee crear un nuevo
     * tipo gráfico.
     *
     * @param listaValores La lista con los datos necesarios para el gráfico.
     * @param panel El panel que contiene el grafico que puede existir o no.
     * @param nombreSerie El nombre de la serie a graficar.
     * @return Devuelve el ChartPanel con el gráfico deseado
     */
    @Override
    public ChartPanel graficar(List<FormatoCsv> listaValores, Component panel, String nombreSerie) {
        DefaultCategoryDataset dataset;
        ChartPanel chartPanel = null;
        if (panel != null && panel instanceof ChartPanel) {
            dataset = (DefaultCategoryDataset) ((ChartPanel) panel).getChart().getCategoryPlot().getDataset();
            chartPanel = (ChartPanel) panel;
        } else {
            dataset = new DefaultCategoryDataset();
        }
        //Es la validacion para que no se pueda mezclar series de diferentes
        //tipos de graficos
        for(Object rowKey : dataset.getRowKeys()){
            if(chartPanel != null && !rowKey.toString().contains("[H]")){
                return chartPanel;
            }
        }
        
        for (int i = 0; i < listaValores.size(); i++) {
            FormatoCsv valor = listaValores.get(i);
            dataset.addValue(valor.getPresion(), nombreSerie.toUpperCase() + "[H]", Integer.toString(i + 1));
        }
        if (chartPanel == null) {
            JFreeChart lineChart = ChartFactory.createLineChart(
                    "Humedad",
                    "Lecturas", "m snm",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false);
            LineAndShapeRenderer renderer = new LineAndShapeRenderer();
            renderer.setSeriesPaint(0, Color.RED);
            CategoryPlot plot = lineChart.getCategoryPlot();
            plot.setRenderer(renderer);
            chartPanel = new ChartPanel(lineChart);
        }
        return chartPanel;
    }

}
