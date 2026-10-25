/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import compilador.CompiladorPromptZal;
import java.io.FileWriter;
import java.io.IOException;
import lexico.Token;
import lexico.tipoToken;

/**
 *
 * @author jesua
 */
public class ReporteTokens {
    
    public void generar(CompiladorPromptZal compilador) {
        try {
            
            FileWriter archivo= new FileWriter("reporteTokens.html");
            archivo.write("<!DOCTYPE html>");
            archivo.write("<html>");
            archivo.write("<head>");
            archivo.write("<meta charset='UTF-8'>");
            archivo.write("<title>Reporte de Tokens - PromptZal</title>");
            archivo.write("<style>");
            archivo.write("body{"+ "font-family:Arial;"+ "background:#f4f4f4;"+ "padding:30px;"+ "}");
            archivo.write("h1{"+ "text-align:center;" + "color:#222;"+ "}");
            archivo.write("table{"+ "width:100%;"+ "border-collapse:collapse;"+ "background:white;"+ "}");
            archivo.write( "th{" + "background:#006b57;"+ "color:white;" + "padding:10px;" + "}");
            archivo.write("td{"+ "border:1px solid #ccc;"+ "padding:10px;" + "text-align:center;"+ "}");
            archivo.write("</style>");
            archivo.write("</head>");
            archivo.write("<body>");
            archivo.write("<h1>Reporte de Tokens - PromptZal</h1>");
            archivo.write("<table>");
            archivo.write("<tr>");
            archivo.write("<th>Numero</th>");
            archivo.write("<th>Lexema</th>");
            archivo.write("<th>Tipo</th>");
            archivo.write("<th>Fila</th>");
            archivo.write("<th>Columna</th>");
            archivo.write("</tr>");
            
            int numero = 1;
            for (Token token : compilador.getTokens()) {
                if (token.getTipo() != tipoToken.EOF) {
                    archivo.write("<tr>");
                    archivo.write("<td>"+ numero + "</td>" );
                    archivo.write( "<td>" + escapar(token.getLexema())  + "</td>" );
                    archivo.write("<td>" + token.getTipo()+ "</td>");
                    archivo.write( "<td>" + token.getFila() + "</td>");
                    archivo.write("<td>" + token.getColumna() + "</td>" );
                    archivo.write("</tr>");
                    numero++;
                }
            }

            archivo.write("</table>");
            archivo.write("</body>");
            archivo.write("</html>");
            archivo.close();
        
        } catch (IOException e) {
            
            System.out.println("Error al generar el reporte de tokens");
        }
    }
    
    private String escapar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
    
}
