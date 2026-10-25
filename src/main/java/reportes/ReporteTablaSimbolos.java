/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import java.io.FileWriter;
import java.io.IOException;
import semantico.Simbolo;
import semantico.TablaSimbolos;

/**
 *
 * @author jesua
 */
public class ReporteTablaSimbolos {
        public void generar(TablaSimbolos tabla) {

        try {

            FileWriter archivo
                    = new FileWriter("reporteTablaSimbolos.html");

            archivo.write("<!DOCTYPE html>");
            archivo.write("<html>");
            archivo.write("<head>");
            archivo.write("<meta charset='UTF-8'>");
            archivo.write("<title>Tabla de Simbolos</title>");

            archivo.write("<style>");
            archivo.write("body{"+ "font-family:Arial;"+ "background:#f4f4f4;" + "padding:30px;"+ "}");
            archivo.write("h1{" + "text-align:center;" + "color:#222;"+ "}");
            archivo.write("table{"+ "width:100%;"+ "border-collapse:collapse;"+ "background:white;"+ "}");
            archivo.write("th{"+ "background:#006b57;"+ "color:white;"+ "padding:10px;"+ "}");
            archivo.write("td{"+ "border:1px solid #ccc;" + "padding:10px;"+ "text-align:center;"+ "}");
            archivo.write("</style>");
            archivo.write("</head>");
            archivo.write("<body>");
            archivo.write("<h1>Tabla de Simbolos - PromptZal</h1>");
            archivo.write("<table>");
            archivo.write("<tr>");
            archivo.write("<th>Nombre</th>");
            archivo.write("<th>Tipo</th>");
            archivo.write("<th>Ambito</th>");
            archivo.write("<th>Fila</th>");
            archivo.write("</tr>");

            for (Simbolo simbolo : tabla.getSimbolos()) {
                
                archivo.write("<tr>");
                archivo.write("<td>"+ simbolo.getNombre()+ "</td>");
                archivo.write("<td>"+ simbolo.getTipo()+ "</td>");
                archivo.write("<td>"+ simbolo.getAmbito()+ "</td>");
                archivo.write("<td>"+ simbolo.getFila() + "</td>");
                archivo.write("</tr>");
            }
            
            archivo.write("</table>");
            archivo.write("</body>");
            archivo.write("</html>");
            archivo.close();
        
        } catch (IOException e) {
            System.out.println( "Error al generar el reporte de tabla de simbolos");
        }
    }
    
}
