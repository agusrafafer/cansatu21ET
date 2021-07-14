/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21.modelo;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYZDataset;

/**
 *
 * @author agustin
 */
public class AcelerometroGrafico extends Grafico {

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
    public ChartPanel graficar(List<FormatoCsv> listaValores,Component panel, String nombreSerie) {
//        float ax_m_s2 = ax * (9.81/16384.0);
//        float ay_m_s2 = ay * (9.81/16384.0);
//        float az_m_s2 = az * (9.81/16384.0);
//        float gx_deg_s = gx * (250.0/32768.0);
//        float gy_deg_s = gy * (250.0/32768.0);
//        float gz_deg_s = gz * (250.0/32768.0);
        DefaultXYZDataset dataset = new DefaultXYZDataset();

        double[] vectX = new double[listaValores.size()];
        double[] vectY = new double[listaValores.size()];
        double[] vectZ = new double[listaValores.size()];
        for (int i = 0; i < listaValores.size(); i++) {
            FormatoCsv valor = listaValores.get(i);
            vectX[i] = valor.getAx();
            vectY[i] = valor.getAy();
            vectZ[i] = valor.getAz();
        }

//        double[][] serie1 = new double[][]{vectX, vectY, vectZ};
//        dataset.addSeries("Serie1", serie1);
//
//        JFreeChart lineChart = ChartFactory.createLineChart(
//                "Humedad",
//                "Lecturas", "m snm",
//                dataset,
//                PlotOrientation.VERTICAL,
//                true, true, false);
//
//        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
//        renderer.setSeriesPaint(0, Color.RED);
//        CategoryPlot plot = lineChart.getCategoryPlot();
//        plot.setRenderer(renderer);
//        return new ChartPanel(lineChart);
        return null;

    }

}
