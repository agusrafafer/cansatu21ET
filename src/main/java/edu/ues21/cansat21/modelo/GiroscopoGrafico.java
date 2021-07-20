/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21.modelo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author agustin
 */
public class GiroscopoGrafico extends Grafico {

    /*
     Primero tenemos que saber los rangos con los que está configurado nuestro MPU6050, 
     dichos rangos pueden ser 2g/4g/8g/16g para el acelerómetro y 250/500/1000/2000(°/s) para el giroscopio.
     Para este ejemplo trabajaremos con los rangos por defecto (2g y 250°/s):

        Variable          | valor mínimo | valor central | valor máximo |
        -----------------------------------------------------------------
        Lectura MPU6050   | -32768       | 0             | +32767       |
        -----------------------------------------------------------------
        Aceleración       | -2g          | 0g            | +2g          |
        -----------------------------------------------------------------
        Velocidad angular | -250°/s      | 0°/s          | +250°/s      |
        -----------------------------------------------------------------
     Más info: https://naylampmechatronics.com/blog/45_tutorial-mpu6050-acelerometro-y-giroscopio.html
               https://www.luisllamas.es/arduino-orientacion-imu-mpu-6050/
               https://www.luisllamas.es/como-usar-un-acelerometro-arduino/
               https://www.luisllamas.es/medir-la-inclinacion-imu-arduino-filtro-complementario/
     */
    /**
     * El método que debe ser implementado cada vez que se desee crear un nuevo
     * tipo gráfico.
     *
     * @param listaValores La lista con los datos necesarios para el gráfico.
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
        //tipos de graficos. La letra que se encuentra entre [] indica el tipo
        //de grafico
        for (Object rowKey : dataset.getRowKeys()) {
            if (chartPanel != null && !rowKey.toString().contains("[G]")) {
                return chartPanel;
            }
        }

        //Variables necesarias para calcular 
        //el la rotacion usando el diferencial
        //del tiempo
        long tiempoPrev = 0, dt = 0;
        double giroscAngX, giroscAngY;
        double giroscAngXPrev = 0, giroscAngYPrev = 0;
        for (int i = 0; i < listaValores.size(); i++) {
            dt = (i * 1000) - tiempoPrev;
            FormatoCsv valor = listaValores.get(i);
            giroscAngX = (((double)valor.getGx()) / 131) * dt / 1000.0 + giroscAngXPrev;
            giroscAngY = (((double)valor.getGy()) / 131) * dt / 1000.0 + giroscAngYPrev;
            //Debo usar la clase Double y no el tipo nativo porque
            //el metodo addValue del dataset requiere un objeto
            //comparable
            Double giroscAngYComparable = giroscAngY;
            DecimalFormat decimalFormat =  new DecimalFormat("###.##");
            dataset.addValue(giroscAngX, nombreSerie.toUpperCase() + "[G]", decimalFormat.format(giroscAngYComparable));
            tiempoPrev = i * 1000; //milisegundos
            giroscAngXPrev = giroscAngX;
            giroscAngYPrev = giroscAngY;
        }
        if (chartPanel == null) {
            JFreeChart lineChart = ChartFactory.createLineChart(
                    "Ángulo rotación (Giro)",
                    "º Y", "º X",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false);
            LineAndShapeRenderer renderer = new LineAndShapeRenderer();
            renderer.setSeriesPaint(0, Color.ORANGE);
            CategoryPlot plot = lineChart.getCategoryPlot();
            plot.setRenderer(renderer);
            Font font3 = new Font("Dialog", Font.PLAIN, 8);
            plot.getDomainAxis().setTickLabelFont(font3);
            plot.getRangeAxis().setTickLabelFont(font3);
            chartPanel = new ChartPanel(lineChart);
        }
        return chartPanel;
    }

}
