    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package promptZal;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jesua
 */
public class reporteHTML {
    
    //*******************INICIO HTML*************************
    private void escribirInicioHTML(FileWriter archivo, String titulo) throws IOException{
    
        archivo.write("<!DOCTYPE html>\n");
        archivo.write("<html>\n");
        archivo.write("<head>\n");
        archivo.write("<meta charset=\"UTF-8\">\n");
        archivo.write("<title>"+titulo+"</title>\n");
    
        archivo.write("<style>\n");
        archivo.write("body { font-family: Arial, sans-serif; margin: 0; background: #F5F1E8; color: #1F1F1F; }\n");
        archivo.write(".encabezado { background: linear-gradient(90deg, #007A5E, #3FAF8F); color: white; padding: 25px; text-align: center; }\n");
        archivo.write(".encabezado h1 { margin: 0; font-size: 34px; }\n");
        archivo.write(".encabezado p { margin: 8px 0 0 0; font-size: 15px; }\n");
        archivo.write(".contenedor { width: 90%; margin: 30px auto; }\n");
        archivo.write(".card { background: white; border-radius: 14px; padding: 20px; box-shadow: 0 4px 14px rgba(0,0,0,0.12); margin-bottom: 25px; }\n");
        archivo.write("table { width: 100%; border-collapse: collapse; margin-top: 15px; }\n");
        archivo.write("th { background: #007A5E; color: white; padding: 12px; font-size: 14px; }\n");
        archivo.write("td { border-bottom: 1px solid #DDD; padding: 10px; text-align: center; font-size: 14px; }\n");
        archivo.write("tr:nth-child(even) { background: #F8F8F8; }\n");
        archivo.write(".mensaje { font-size: 22px; font-weight: bold; color: #007A5E; text-align: center; padding: 30px; }\n");
        archivo.write("</style>\n");
    
        archivo.write("</head>\n");
        archivo.write("<body>\n");
        archivo.write("<div class=\"encabezado\">\n");
        archivo.write("<h1>"+titulo+"</h1>\n");
        archivo.write("<p>PromptZal</p>\n");
        archivo.write("</div>\n");
        archivo.write("<div class=\"contenedor\">\n");
}
    
    
    
    //*************REPORTE DE TOKENS********************
    public void generarReporteTokens(ArrayList<Token> listaTokens){
        
        try{
        
        FileWriter archivo = new FileWriter("reporteTokens.html");
        
        escribirInicioHTML(archivo, "REPORTE DE TOKENS");
        
        archivo.write("<div class=\"card\">\n");
        archivo.write("<h2>Listado de Tokens Reconocidos</h2>\n");
        archivo.write("<table>\n");
        archivo.write("<tr>\n");
        archivo.write("<th>Numero</th>\n");
        archivo.write("<th>Lexema</th>\n");
        archivo.write("<th>Tipo</th>\n");
        archivo.write("<th>Fila</th>\n");
        archivo.write("<th>Columna</th>\n");
        archivo.write("</tr>\n");
        
        
        for(Token token : listaTokens){
            
            String color = colorHTMLToken(token.getTipo());
            
            archivo.write("<tr>\n");
            archivo.write("<td>"+token.getNumero()+"</td>\n");
            archivo.write("<td>"+token.getLexema()+"</td>\n");
            archivo.write("<td style=\"background-color:"+color+"; color:white; font-weight:bold;\">"+token.getTipo()+"</td>\n");
            archivo.write("<td>"+token.getFila()+"</td>\n");
            archivo.write("<td>"+token.getColumna()+"</td>\n");
            archivo.write("</tr>\n");
        }
        
        archivo.write("</table>\n");
        archivo.write("</div>\n");
        
        escribirFinHTML(archivo);
        archivo.close();
        
        System.out.println("Reporte de Tokens generado con exito");
        
    }catch(IOException e){
        
        System.out.println("Error al generar el reporte de Tokens");
        System.out.println(e.getMessage());
    }
    
    }
    
    //*******************REPORTE DE ERRORES LEXICOS*************************
    public void generarReporteErrores(ArrayList<errorLexico> listaErrores){
        
        try{
        
        FileWriter archivo = new FileWriter("reporteDeErrores.html");
        
        escribirInicioHTML(archivo, "REPORTE DE ERRORES");
        
        archivo.write("<div class=\"card\">\n");
        archivo.write("<h2>Listado de Errores Lexicos</h2>\n");
        
        
        if(listaErrores.isEmpty()){
            
            archivo.write("<div class=\"mensaje\">NO SE ENCONTRARON ERRORES LEXICOS</div>\n");
            
        }else{
            
            archivo.write("<table>\n");
            archivo.write("<tr>\n");
            archivo.write("<th>Numero</th>\n");
            archivo.write("<th>Lexema</th>\n");
            archivo.write("<th>Tipo de Error</th>\n");
            archivo.write("<th>Fila</th>\n");
            archivo.write("<th>Columna</th>\n");
            archivo.write("</tr>\n");
            
            int numeroError = 1;
            
            
            for(errorLexico error : listaErrores){
                
                archivo.write("<tr>\n");
                archivo.write("<td>"+numeroError+"</td>\n");
                archivo.write("<td>"+error.getLexema()+"</td>\n");
                archivo.write("<td style=\"background-color:#C62828; color:white; font-weight:bold;\">"+error.getTipoError()+"</td>\n");
                archivo.write("<td>"+error.getFila()+"</td>\n");
                archivo.write("<td>"+error.getColumna()+"</td>\n");
                archivo.write("</tr>\n");
                numeroError++;
            }
            
            archivo.write("</table>\n");
        }
        
        
        archivo.write("</div>\n");
        
        escribirFinHTML(archivo);
        archivo.close();
        
        System.out.println("Reporte de Errores generado con exito");
        
    }catch(IOException e){
        
        System.out.println("Error al generar el reporte de Errores");
        System.out.println(e.getMessage());
    }
    }
    
    //*******************REPORTE DE ESTADISTICAS*************************
    public void generarReporteEstadisticas(ArrayList<Token> listaTokens,
            ArrayList<errorLexico> listaErrores, String texto){
        
        try{
        
            FileWriter archivo = new FileWriter("reporteEstadisticas.html");
        
            int totalTokens = listaTokens.size();
            int totalErrores = listaErrores.size();
            int totalLineas = contarLineas(texto);
        
        
            escribirInicioHTML(archivo, "REPORTE DE ESTADISTICAS");
        
        
            archivo.write("<div class=\"card\">\n");
            archivo.write("<h2>Resumen General</h2>\n");
            archivo.write("<div style=\"display:flex; gap:20px; flex-wrap:wrap; margin-top:20px;\">\n");
        
            escribirTarjetaResumen(archivo, "Total de Tokens", String.valueOf(totalTokens), "#007A5E");
            escribirTarjetaResumen(archivo, "Total de Lineas", String.valueOf(totalLineas), "#3FAF8F");
            escribirTarjetaResumen(archivo, "Total de Errores", String.valueOf(totalErrores), "#C62828");
        
            archivo.write("</div>\n");
            archivo.write("</div>\n");
        
        
            archivo.write("<div class=\"card\">\n");
            archivo.write("<h2>Frecuencia de Tokens</h2>\n");
            archivo.write("<table>\n");
            archivo.write("<tr>\n");
            archivo.write("<th>TIPO DE TOKEN</th>\n");
            archivo.write("<th>FRECUENCIA</th>\n");
            archivo.write("</tr>\n");

        
            int maxFrecuencia = 1;

            for(tipoToken tipo : tipoToken.values()){

                int contador = contarFrecuencia(listaTokens, tipo);

                if(contador > maxFrecuencia){

                    maxFrecuencia = contador;
                }
            }


            for(tipoToken tipo : tipoToken.values()){

                int contador = contarFrecuencia(listaTokens, tipo);
                String color = colorHTMLToken(tipo);

                archivo.write("<tr>\n");
                archivo.write("<td style=\"background-color:"+color+"; color:white; font-weight:bold;\">"+tipo+"</td>\n");
                archivo.write("<td>"+contador+"</td>\n");
                archivo.write("</tr>\n");
            }

            archivo.write("</table>\n");
            archivo.write("</div>\n");


            //*******************GRAFICA DE BARRAS********************
            archivo.write("<div class=\"card\">\n");
            archivo.write("<h2>Tendencias - Grafica de Barras</h2>\n");

            for(tipoToken tipo : tipoToken.values()){

                int contador = contarFrecuencia(listaTokens, tipo);
                int ancho = (contador * 500) / maxFrecuencia;
                String color = colorHTMLToken(tipo);


                archivo.write("<div style=\"margin-bottom:18px;\">\n");
                archivo.write("<div style=\"font-weight:bold; margin-bottom:5px;\">"+tipo+" ("+contador+")</div>\n");
                archivo.write("<div style=\"background:#DDD; border-radius:10px; overflow:hidden;\">\n");
                archivo.write("<div style=\"width:"+ancho+"px; background:"+color+"; color:white; padding:10px; font-weight:bold; border-radius:10px;\">"+contador+"</div>\n");
                archivo.write("</div>\n");
                archivo.write("</div>\n");
            }

            archivo.write("</div>\n");


            escribirFinHTML(archivo);
            archivo.close();

            System.out.println("Reporte de Estadisticas generado con exito");

        }catch(IOException e){

            System.out.println("Error al generar el reporte de Estadisticas");
            System.out.println(e.getMessage());
        }
    }
    
    //*******************ESCRIBIR TARJETA RESUMEN*************************
    private void escribirTarjetaResumen(FileWriter archivo, String titulo,
            String valor, String color) throws IOException{

        archivo.write("<div style=\"flex:1; min-width:220px; background:"+color+"; color:white; padding:20px; border-radius:14px; text-align:center; box-shadow: 0 4px 10px rgba(0,0,0,0.15);\">\n");
        archivo.write("<div style=\"font-size:18px; font-weight:bold;\">"+titulo+"</div>\n");
        archivo.write("<div style=\"font-size:32px; margin-top:10px; font-weight:bold;\">"+valor+"</div>\n");
        archivo.write("</div>\n");
    }
    
    
    //*******************CONTAR FRECUENCIA*************************
    private int contarFrecuencia(ArrayList<Token> listaTokens, tipoToken tipo){

        int contador = 0;

        for(Token token : listaTokens){

            if(token.getTipo() == tipo){

                contador++;
            }
        }

        return contador;
    }
    

    
    private int contarLineas(String texto){
        
        if(texto.isEmpty()){
            return 0;
        }
        
        int totalLineas = 1;
        for(int i = 0; i < texto.length(); i++){
            
            if(texto.charAt(i) == '\n' && i < texto.length()-1){
                
                totalLineas++;
            }
        }
        return totalLineas;
    }
    
    //*******************COLOR HTML TOKEN*************************
    private String colorHTMLToken(tipoToken tipo){
    
        switch(tipo){
        
            case DIRECTIVA:
                return "#D4AF37";
            
            case PALABRA_RESERVADA:
                return "#8D5A3B";
            
            case COMANDO_IA:
                return "#1F6F50";
            
            case CONECTOR:
                return "#F4B04F";
            
            case IDENTIFICADOR:
                return "#4F9D8A";
            
            case CADENA:
                return "#30499B";
            
            case OPERADOR:
                return "#E7D85B";
            
            case FUNCION:
                return "#467599";
            
            case ENTERO:
                return "#B09540";
            
            case DECIMAL:
                return "#2E8B8B";
            
            case DELIMITADOR:
                return "#74B9E2";
            
            default:
                return "#555555";
        }
    }
    
    
    //*******************FINAL HTML*************************
    private void escribirFinHTML(FileWriter archivo) throws IOException{
    
        archivo.write("</div>\n");
        archivo.write("</body>\n");
        archivo.write("</html>\n");
}
}
