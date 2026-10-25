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

            FileWriter archivo = new FileWriter("reporteTokens.html");

            archivo.write("<!DOCTYPE html>");
            archivo.write("<html>");
            archivo.write("<head>");
            archivo.write("<meta charset='UTF-8'>");
            archivo.write("<title>Reporte de Tokens - PromptZal</title>");
            archivo.write("<style>");

            //****************ESTILO GENERAL****************

            archivo.write("body{"+ "font-family:Arial;"+ "background:#f4f4f4;"+ "padding:30px;"+ "color:#222;"+ "}");
            archivo.write("h1{"+ "text-align:center;"+ "color:#222;"+ "margin-bottom:30px;"+ "}");
            archivo.write(".contenedor{"+ "max-width:1200px;"+ "margin:auto;" + "background:white;"+ "padding:25px;"
                    + "border-radius:10px;"+ "box-shadow:0 3px 12px rgba(0,0,0,0.10);"+ "}");

            //****************TABLA****************
            archivo.write("table{"+ "width:100%;"+ "border-collapse:collapse;"+ "background:white;"+ "}");
            archivo.write("th{"+ "background:#006b57;"+ "color:white;"+ "padding:12px;"+ "}");
            archivo.write("td{"+ "border-bottom:1px solid #ddd;"+ "padding:10px;"+ "text-align:center;"+ "}");
            archivo.write("tr:nth-child(even){"+ "background:#f8f9f9;"+ "}");
            archivo.write("tr:hover{"+ "background:#eef7f4;"+ "}");

            //****************BADGES****************
            archivo.write(".badge{"+ "display:inline-block;"+ "padding:6px 12px;"+ "border-radius:14px;"
                    + "font-weight:bold;"+ "font-size:12px;"+ "color:white;"+ "}");

            //****************COLORES DE TOKENS****************
            archivo.write(".directiva{background:#8e44ad;}");
            archivo.write(".cadena{background:#27ae60;}");
            archivo.write(".agente{background:#1f78ff;}");
            archivo.write(".id{background:#5d6d7e;}");
            archivo.write(".llave{background:#7f8c8d;}");
            archivo.write(".par{background:#95a5a6;}");
            archivo.write(".contexto{background:#2980b9;}");
            archivo.write(".variable{background:#0047ab;}");
            archivo.write(".igual{background:#c0392b;}");
            archivo.write(".mas{background:#e74c3c;}");
            archivo.write(".cargar{background:#d35400;}");
            archivo.write(".comando{background:#e67e22;}");
            archivo.write(".conector{background:#16a085;}");
            archivo.write(".flecha{background:#e74c3c;}");
            archivo.write(".numero{background:#3498db;}");
            archivo.write(".ejecutar{background:#2c3e50;}");
            archivo.write(".exportar{background:#0b7285;}");
            archivo.write(".coma{background:#7f8c8d;}");
            archivo.write(".otro{background:#34495e;}");

            //****************LEXEMA****************
            archivo.write(".lexema{"+ "font-family:monospace;"+ "font-weight:bold;"+ "}");
            archivo.write("</style>");
            archivo.write("</head>");

            //****************CUERPO****************
            archivo.write("<body>");
            archivo.write("<div class='contenedor'>");
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

            //****************RECORRER TOKENS****************
            for (Token token : compilador.getTokens()) {

                if (token.getTipo() != tipoToken.EOF) {
                    String claseToken= obtenerClaseToken(token.getTipo().toString());
                    archivo.write("<tr>");

                    // Numero
                    archivo.write("<td>"+ numero+ "</td>");

                    // Lexema con color segun token
                    archivo.write("<td>"+ "<span class='badge "+ claseToken+ " lexema'>"+ escapar(token.getLexema())+ "</span>"+ "</td>");

                    // Tipo con el mismo color
                    archivo.write("<td>"+ "<span class='badge "+ claseToken + "'>"+ token.getTipo()+ "</span>"+ "</td>");

                    // Fila
                    archivo.write("<td>"+ token.getFila()+ "</td>");

                    // Columna
                    archivo.write("<td>"+ token.getColumna() + "</td>");
                    archivo.write("</tr>");

                    numero++;
                }
            }
            
            archivo.write("</table>");
            archivo.write("</div>");
            archivo.write("</body>");
            archivo.write("</html>");
            archivo.close();

        } catch (IOException e) {

            System.out.println(
                    "Error al generar el reporte de tokens"
            );
        }
    }


    //****************ESCAPAR HTML****************

    private String escapar(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }


    //****************COLOR SEGUN TIPO DE TOKEN****************

    private String obtenerClaseToken(String tipo) {

        switch (tipo) {

            case "DIRECTIVA":
                return "directiva";

            case "CADENA":
                return "cadena";

            case "AGENTE":
                return "agente";

            case "ID":
                return "id";

            case "LLAVE_A":
            case "LLAVE_B":
                return "llave";

            case "PAR_A":
            case "PAR_B":
                return "par";

            case "CONTEXTO":
                return "contexto";

            case "VARIABLE":
                return "variable";

            case "IGUAL":
                return "igual";

            case "MAS":
                return "mas";

            case "CARGAR":
                return "cargar";

            case "COMANDO_IA":
                return "comando";

            case "CONECTOR":
                return "conector";

            case "FLECHA":
                return "flecha";

            case "NUMERO":
                return "numero";

            case "EJECUTAR":
                return "ejecutar";

            case "EXPORTAR":
                return "exportar";

            case "COMA":
                return "coma";

            default:
                return "otro";
        }
    }
}
