/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21.modelo;

/**
 * Clase que contien los atributos necesarios para poder manipular 
 * el formato CSV desde cualquier orta clase.
 * Es simplemente un POJO.
 * @author agustin
 */
public class FormatoCsv {    
    private float temperatura;
    private float presion;
    private float humedad;
    private int ax;
    private int ay;
    private int az;
    private int gx;
    private int gy;
    private int gz;

    public FormatoCsv(float temperatura, float presion, float humedad, int ax, int ay, int az, int gx, int gy, int gz) {
        this.temperatura = temperatura;
        this.presion = presion;
        this.humedad = humedad;
        this.ax = ax;
        this.ay = ay;
        this.az = az;
        this.gx = gx;
        this.gy = gy;
        this.gz = gz;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public float getPresion() {
        return presion;
    }

    public void setPresion(float presion) {
        this.presion = presion;
    }

    public float getHumedad() {
        return humedad;
    }

    public void setHumedad(float humedad) {
        this.humedad = humedad;
    }

    public int getAx() {
        return ax;
    }

    public void setAx(int ax) {
        this.ax = ax;
    }

    public int getAy() {
        return ay;
    }

    public void setAy(int ay) {
        this.ay = ay;
    }

    public int getAz() {
        return az;
    }

    public void setAz(int az) {
        this.az = az;
    }

    public int getGx() {
        return gx;
    }

    public void setGx(int gx) {
        this.gx = gx;
    }

    public int getGy() {
        return gy;
    }

    public void setGy(int gy) {
        this.gy = gy;
    }

    public int getGz() {
        return gz;
    }

    public void setGz(int gz) {
        this.gz = gz;
    }
}
