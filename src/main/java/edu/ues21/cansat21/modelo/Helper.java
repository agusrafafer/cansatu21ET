/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ues21.cansat21.modelo;

import com.csvreader.CsvReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

/**
 * Clase que contiene los métodos de ayuda o soporte para otras clases
 *
 * @author agustin
 */
public class Helper {

    /**
     * *
     * Método que determina si el archivo seleccionado tiene el formato CSV
     * requerido
     *
     * @param pathArchivoCsv La ruta del archivo a validar
     * @return Devuelve true si es un archivo correcto
     */
    public boolean esArchivoValido(String pathArchivoCsv) {
        BufferedReader br = null;
        InputStream inputStream = null;
        boolean valido = false;
        try {
            File file = new File(pathArchivoCsv);
            inputStream = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            int cont = 0;
            while ((line = br.readLine()) != null && cont == 0) {
                String[] cabeceras = line.split(",");
                for (String cabecera : cabeceras) {
                    if (cabecera.equals("Temperatura (*C)") || cabecera.equals("Presion (mb)")
                            || cabecera.equals("H (m s.n.m.)") || cabecera.equals("AX")
                            || cabecera.equals("AY") || cabecera.equals("AZ")
                            || cabecera.equals("GX") || cabecera.equals("GY")
                            || cabecera.equals("GZ")) {
                        valido = true;
                        break;
                    }
                }
                cont++;
            }
            if (cont == 0) {
                valido = false;
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                br.close();
                inputStream.close();
            } catch (NullPointerException | IOException e) {
                System.err.println(e);
            }
        }
        return valido;
    }

    /**
     * Método que toma un archivo CSV y devueve una lista con objetos
     * pertenecientes a la clase <b>FormatoCsv</b>
     *
     * @param pathArchivoCsv La ruta del archivo CSV
     * @return Devuelve una lista de objetos del tipo FormatoCsv
     */
    public List<FormatoCsv> listarValores(String pathArchivoCsv) {
        List<FormatoCsv> listaValores = new ArrayList<>();
        CsvReader csv = null;
        try {
            csv = new CsvReader(pathArchivoCsv);
            //Salto la primer linea para que no sea leida al cargar la lista
            csv.readHeaders();
            String temp;
            String pres;
            String h;
            String ax;
            String ay;
            String az;
            String gx;
            String gy;
            String gz;
            FormatoCsv registro;
            while (csv.readRecord()) {
                //Las cabeceras del CSV son:
                //Temperatura (*C); Presion (mb); H (m s.n.m.); AX; AY; AZ; GX; GY; GZ
                temp = csv.get("Temperatura (*C)");
                pres = csv.get("Presion (mb)");
                h = csv.get("H (m s.n.m.)");
                ax = csv.get("AX");
                ay = csv.get("AY");
                az = csv.get("AZ");
                gx = csv.get("GX");
                gy = csv.get("GY");
                gz = csv.get("GZ");

                registro = new FormatoCsv(Float.parseFloat(temp), Float.parseFloat(pres), Float.parseFloat(h), Integer.parseInt(ax), Integer.parseInt(ay), Integer.parseInt(az), Integer.parseInt(gx), Integer.parseInt(gy), Integer.parseInt(gz));
                listaValores.add(registro);
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            try {
                csv.close();
            } catch (NullPointerException e) {
                System.err.println(e);
            }
        }

        return listaValores;
    }

    public static String cargarLicencia() throws IOException {
        return new String(Helper.class.getResourceAsStream("/licencia").readAllBytes());
    }

    public static Properties cargarArchivoConfig() throws IOException {
        File f = new File(Helper.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        //Para obtener el path donde esta corriendo la aplicacion ya sea un jar o no
        String path = f.getParent();
        String sep = FileSystems.getDefault().getSeparator();
        Properties properties = new Properties();
        properties.load(new FileReader(new File(path + sep + "resources" + sep + "config.properties")));
        return properties;
    }

    public static void guardarArchivoConfig(Properties properties) throws IOException, URISyntaxException {
        File f = new File(Helper.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        //Para obtener el path donde esta corriendo la aplicacion ya sea un jar o no
        String path = f.getParent();
        String sep = FileSystems.getDefault().getSeparator();
        properties.store(new BufferedWriter(new FileWriter(new File(path + sep + "resources" + sep + "config.properties"))), "Propiedades CansatET");
    }

    public static void postAuditoria(String accion, String caracCliente, String cliente) {
        new Thread(() -> {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "CansatUes21");

            String auditoria = "{\n"
                    + "  \"accion\": \"" + accion.toUpperCase() + "\",\n"
                    + "  \"caracteristica\": \"" + caracCliente + "\",\n"
                    + "  \"cliente\": \"" + cliente + "\"\n"
                    + "}";

            try {
                Properties prop = cargarArchivoConfig();
                String urlServidor = (String) prop.get("urlServidor");
                HttpPost postRequest = new HttpPost(urlServidor + "/auditoria");
                postRequest.addHeader("content-type", "application/json");
                StringEntity auditoriaEntity = new StringEntity(auditoria);
                postRequest.setEntity(auditoriaEntity);
                HttpResponse response = httpClient.execute(postRequest);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Fallo con codigo HTTP de error: " + statusCode);
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                httpClient.getConnectionManager().shutdown();
            }
        }).start();
    }

    public static void postCliente(String ip) {
        //new Thread(() -> {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "CansatUes21");

        String cliente = "{\n"
                + "  \"codIdentificacion\": \"" + ip + "\" \n"
                + "}";

        try {
            Properties prop = cargarArchivoConfig();
            String urlServidor = (String) prop.get("urlServidor");
            HttpPost postRequest = new HttpPost(urlServidor + "/cliente");
            postRequest.addHeader("content-type", "application/json");
            StringEntity clienteEntity = new StringEntity(cliente);
            postRequest.setEntity(clienteEntity);
            HttpResponse response = httpClient.execute(postRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Fallo con codigo HTTP de error: " + statusCode);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        //}).start();
    }

    public static String getIdCliente(String ip) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "CansatUes21");

        StringBuilder sb = new StringBuilder("-1");
        try {
            Properties prop = cargarArchivoConfig();
            String urlServidor = (String) prop.get("urlServidor");
            HttpGet getRequest = new HttpGet(urlServidor + "/cliente/" + ip);
            getRequest.addHeader("content-type", "application/json");
            HttpResponse response = httpClient.execute(getRequest);
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            String line = "";
            sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Fallo con codigo HTTP de error: " + statusCode);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return sb.toString();
    }

    public static String obtenerIpPublica() {
        // buscamos la IP publica para usar como identificador de la 
        // computadora
        String ipPublica = "";
        try {
            Properties prop = cargarArchivoConfig();
            String urlServidorIp = (String) prop.get("ipPublica");
            URL url = new URL(urlServidorIp);
            BufferedReader sc = new BufferedReader(new InputStreamReader(url.openStream()));
            ipPublica = sc.readLine().trim();
        } catch (IOException e) {
            ipPublica = "0.0.0.0";
        }
        return ipPublica;
    }

    public static String obtenerDatosSOCliente() {
        StringBuilder sb = new StringBuilder();
        sb.append("SO: ");
        sb.append(System.getProperty("os.name"));
        sb.append("; ver: ");
        sb.append(System.getProperty("os.version"));
        sb.append("; arq: ");
        sb.append(System.getProperty("os.arch"));
        sb.append("; Java: ");
        sb.append(System.getProperty("java.version"));
        sb.append("; Java vendor: ");
        sb.append(System.getProperty("java.vendor"));
        sb.append("; Java vendor url: ");
        sb.append(System.getProperty("java.vendor.url"));

        return sb.toString();
    }

}
