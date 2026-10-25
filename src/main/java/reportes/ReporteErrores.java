/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import compilador.CompiladorPromptZal;
import java.io.FileWriter;
import java.io.IOException;
import lexico.errorLexico;
import semantico.errorSemantico;
import sintactico.errorSintactico;

/**
 *
 * @author jesua
 */
public class ReporteErrores {
    public void generar(CompiladorPromptZal compilador) {
        try {
            FileWriter archivo = new FileWriter("reporteDeErrores.html");

            archivo.write("<!DOCTYPE html>");
            archivo.write("<html>");
            archivo.write("<head>");
            archivo.write("<meta charset='UTF-8'>");
            archivo.write("<title>Reporte de Errores - PromptZal</title>");
            archivo.write("<style>");
            archivo.write("body{"+ "font-family:Arial;"+ "background:#f4f4f4;"+ "padding:30px;"+ "}");
            archivo.write("h1{" + "text-align:center;" + "color:#222;"+ "}");
            archivo.write( "h2{"+ "color:#006b57;"+ "margin-top:35px;"+ "}");
            archivo.write("table{"+ "width:100%;"+ "border-collapse:collapse;" + "background:white;"+ "margin-bottom:25px;"+ "}");
            archivo.write("th{"+ "background:#006b57;"+ "color:white;"+ "padding:10px;"+ "}" );
            archivo.write( "td{"+ "border:1px solid #ccc;"+ "padding:10px;"+ "text-align:center;"+ "}");
            archivo.write(".sinErrores{"+ "background:white;"+ "padding:15px;"+ "border-left:5px solid #006b57;"+ "}");
            archivo.write("</style>");
            archivo.write("</head>");
            archivo.write("<body>");
            archivo.write("<h1>Reporte de Errores - PromptZal</h1>");


            //****************ERRORES LEXICOS****************
            archivo.write("<h2>Errores Lexicos</h2>");
            if (compilador.getErroresLexicos().isEmpty()) {
                
                archivo.write("<div class='sinErrores'>"+ "No hay errores lexicos."+ "</div>");
            
            } else {
                archivo.write("<table>");
                archivo.write("<tr>");
                archivo.write("<th>Lexema</th>");
                archivo.write("<th>Descripcion</th>");
                archivo.write("<th>Fila</th>");
                archivo.write("<th>Columna</th>");
                archivo.write("</tr>");
                
                for (errorLexico error: compilador.getErroresLexicos()) {
                    
                    archivo.write("<tr>");
                    archivo.write("<td>"+ escapar(error.getLexema())+ "</td>");
                    archivo.write("<td>"+ escapar(error.getTipoError())+ "</td>");
                    archivo.write("<td>"+ error.getFila()+ "</td>");
                    archivo.write("<td>"+ error.getColumna()+ "</td>");
                    archivo.write("</tr>");
                }
                archivo.write("</table>");
            }
            
            //****************ERRORES SINTACTICOS****************
            archivo.write("<h2>Errores Sintacticos</h2>");

            if (compilador.getErroresSintacticos().isEmpty()) {
                
                archivo.write( "<div class='sinErrores'>"+ "No hay errores sintacticos."+ "</div>");
            
            } else {
                archivo.write("<table>");
                archivo.write("<tr>");
                archivo.write("<th>Encontrado</th>");
                archivo.write("<th>Esperado</th>");
                archivo.write("<th>Fila</th>");
                archivo.write("<th>Columna</th>");
                archivo.write("</tr>");
                
                for (errorSintactico error: compilador.getErroresSintacticos()) {
                    archivo.write("<tr>");
                    archivo.write("<td>"+ escapar(error.getEncontrado())+ "</td>");
                    archivo.write("<td>"+ escapar(error.getEsperado())+ "</td>");
                    archivo.write( "<td>" + error.getFila()+ "</td>");
                    archivo.write( "<td>" + error.getColumna()+ "</td>");
                    archivo.write("</tr>");
                }
                
                archivo.write("</table>");
            }
            
            //****************ERRORES SEMANTICOS****************
            archivo.write("<h2>Errores Semanticos</h2>");

            if (compilador.getErroresSemanticos().isEmpty()) {
                
                archivo.write( "<div class='sinErrores'>"+ "No hay errores semanticos."+ "</div>");
            } else {
                
                archivo.write("<table>");
                archivo.write("<tr>");
                archivo.write("<th>Descripcion</th>");
                archivo.write("<th>Fila</th>");
                archivo.write("</tr>");
                
                for (errorSemantico error : compilador.getErroresSemanticos()) {
                    archivo.write("<tr>");
                    archivo.write( "<td>"+ escapar(error.getDescripcion())+ "</td>" );
                    archivo.write("<td>"+ error.getFila()+ "</td>");
                    archivo.write("</tr>");
                }
                
                archivo.write("</table>");
            }
            
            archivo.write("</body>");
            archivo.write("</html>");
            archivo.close();
        
        } catch (IOException e) {
            System.out.println( "Error al generar el reporte de errores");
        }
    }
    
    //****************ESCAPAR HTML****************
    private String escapar(String texto) {
        if (texto == null) {
            return "";
        }

        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
    
}
