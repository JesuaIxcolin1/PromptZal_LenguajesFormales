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

            FileWriter archivo= new FileWriter("reporteDeErrores.html");
            archivo.write("<!DOCTYPE html>");
            archivo.write("<html>");
            archivo.write("<head>");
            archivo.write("<meta charset='UTF-8'>");
            archivo.write("<title>Reporte de Errores - PromptZal</title>");
            archivo.write("<style>");

            //****************ESTILO GENERAL****************

            archivo.write("body{"+ "font-family:Arial;"+ "background:#f4f4f4;"+ "padding:30px;"+ "color:#222;"+ "}");
            archivo.write(".contenedor{"+ "max-width:1200px;"+ "margin:auto;"+ "background:white;"+ "padding:25px;"
                    + "border-radius:10px;"+ "box-shadow:0 3px 12px rgba(0,0,0,0.10);"+ "}");
            archivo.write("h1{"+ "text-align:center;"+ "color:#222;"+ "margin-bottom:30px;"+ "}");
            archivo.write("h2{"+ "margin-top:35px;"+ "margin-bottom:15px;"+ "}");

            //****************TABLA****************
            archivo.write("table{"+ "width:100%;"+ "border-collapse:collapse;"+ "background:white;"+ "margin-bottom:25px;"+ "}");
            archivo.write("th{"+ "color:white;"+ "padding:12px;"+ "}");
            archivo.write("td{"+ "border-bottom:1px solid #ddd;"+ "padding:10px;"+ "text-align:center;"+ "}");
            archivo.write("tr:nth-child(even){"+ "background:#f8f9f9;"+ "}");

            //****************BADGES****************
            archivo.write(".badge{"+ "display:inline-block;"+ "padding:6px 12px;"+ "border-radius:14px;"+ "font-weight:bold;"
                    + "font-size:12px;"+ "color:white;"+ "}");

            //****************COLORES DE ERRORES****************
            archivo.write(".lexico{"+ "background:#e74c3c;"+ "}");
            archivo.write(".sintactico{"+ "background:#f39c12;"+ "}");
            archivo.write(".semantico{"+ "background:#8e44ad;"+ "}");

            //****************TITULOS****************
            archivo.write(".tituloLexico{"+ "color:#c0392b;"+ "}");
            archivo.write(".tituloSintactico{"+ "color:#d68910;"+ "}");
            archivo.write(".tituloSemantico{"+ "color:#7d3c98;"+ "}");

            //****************ENCABEZADOS DE TABLAS****************
            archivo.write(".tablaLexico th{"+ "background:#e74c3c;"+ "}");
            archivo.write(".tablaSintactico th{"+ "background:#f39c12;"+ "}");
            archivo.write(".tablaSemantico th{"+ "background:#8e44ad;"+ "}");

            //****************SIN ERRORES****************
            archivo.write(".sinErrores{"+ "background:#f0fff4;"+ "padding:15px;"+ "border-left:5px solid #27ae60;"
                    + "border-radius:5px;"+ "color:#1e8449;"+ "font-weight:bold;"+ "margin-bottom:20px;"+ "}");

            //****************RESUMEN****************
            archivo.write(".resumen{"+ "display:flex;"+ "gap:15px;"+ "margin-bottom:30px;"+ "flex-wrap:wrap;"+ "}");
            archivo.write(".tarjeta{"+ "flex:1;"+ "min-width:180px;"+ "padding:18px;"+ "border-radius:8px;"+ "color:white;"+ "text-align:center;"+ "}");
            archivo.write(".tarjetaNumero{"+ "font-size:28px;"+ "font-weight:bold;"+ "margin-top:5px;"+ "}");
            archivo.write("</style>");
            archivo.write("</head>");

            //****************CUERPO****************
            archivo.write("<body>");
            archivo.write("<div class='contenedor'>");
            archivo.write("<h1>Reporte de Errores - PromptZal</h1>");

            //****************RESUMEN DE ERRORES****************
            archivo.write("<div class='resumen'>");
            archivo.write("<div class='tarjeta lexico'>"+ "Errores Lexicos"
                    + "<div class='tarjetaNumero'>"+ compilador.getErroresLexicos().size()+ "</div>"+ "</div>");
            archivo.write("<div class='tarjeta sintactico'>"+ "Errores Sintacticos"+ "<div class='tarjetaNumero'>"
                    + compilador.getErroresSintacticos().size()+ "</div>"+ "</div>");
            archivo.write("<div class='tarjeta semantico'>"+ "Errores Semanticos"+ "<div class='tarjetaNumero'>"
                    + compilador.getErroresSemanticos().size()+ "</div>"+ "</div>");
            archivo.write("</div>");

            //****************ERRORES LEXICOS****************
            archivo.write("<h2 class='tituloLexico'>"+ "Errores Lexicos"+ "</h2>");

            if (compilador.getErroresLexicos().isEmpty()) {
                archivo.write("<div class='sinErrores'>"+ "No hay errores lexicos."+ "</div>");
            
            } else {
                archivo.write("<table class='tablaLexico'>");
                archivo.write("<tr>");
                archivo.write("<th>Tipo</th>");
                archivo.write("<th>Lexema</th>");
                archivo.write("<th>Descripcion</th>");
                archivo.write("<th>Fila</th>");
                archivo.write("<th>Columna</th>");
                archivo.write("</tr>");

                for (errorLexico error: compilador.getErroresLexicos()) {
                    archivo.write("<tr>");
                    archivo.write("<td>"+ "<span class='badge lexico'>"+ "LEXICO"+ "</span>"+ "</td>");
                    archivo.write("<td>"+ escapar(error.getLexema())+ "</td>");
                    archivo.write("<td>"+ escapar(error.getTipoError())+ "</td>");
                    archivo.write("<td>"+ error.getFila()+ "</td>");
                    archivo.write("<td>"+ error.getColumna()+ "</td>");
                    archivo.write("</tr>");
                }

                archivo.write("</table>");
            }


            //****************ERRORES SINTACTICOS****************

            archivo.write(
                    "<h2 class='tituloSintactico'>"
                    + "Errores Sintacticos"
                    + "</h2>"
            );


            if (compilador.getErroresSintacticos().isEmpty()) {

                archivo.write("<div class='sinErrores'>"+ "No hay errores sintacticos."+ "</div>");
            } else {

                archivo.write("<table class='tablaSintactico'>");
                archivo.write("<tr>");
                archivo.write("<th>Tipo</th>");
                archivo.write("<th>Encontrado</th>");
                archivo.write("<th>Esperado</th>");
                archivo.write("<th>Fila</th>");
                archivo.write("<th>Columna</th>");
                archivo.write("</tr>");

                for (errorSintactico error: compilador.getErroresSintacticos()) {
                    archivo.write("<tr>");
                    archivo.write("<td>"+ "<span class='badge sintactico'>"+ "SINTACTICO"+ "</span>"+ "</td>");
                    archivo.write("<td>"+ escapar(error.getEncontrado())+ "</td>");
                    archivo.write("<td>"+ escapar(error.getEsperado())+ "</td>");
                    archivo.write("<td>"+ error.getFila()+ "</td>");
                    archivo.write("<td>"+ error.getColumna()+ "</td>");
                    archivo.write("</tr>");
                }
                archivo.write("</table>");
            }


            //****************ERRORES SEMANTICOS****************

            archivo.write("<h2 class='tituloSemantico'>"+ "Errores Semanticos"+ "</h2>");

            if (compilador.getErroresSemanticos().isEmpty()) {
                archivo.write("<div class='sinErrores'>"+ "No hay errores semanticos."+ "</div>");

            } else {
                archivo.write("<table class='tablaSemantico'>");
                archivo.write("<tr>");
                archivo.write("<th>Tipo</th>");
                archivo.write("<th>Descripcion</th>");
                archivo.write("<th>Fila</th>");
                archivo.write("</tr>");

                for (errorSemantico error: compilador.getErroresSemanticos()) {
                    archivo.write("<tr>");
                    archivo.write("<td>"+ "<span class='badge semantico'>" + "SEMANTICO"+ "</span>"+ "</td>");
                    archivo.write("<td>"+ escapar(error.getDescripcion())+ "</td>");
                    archivo.write( "<td>" + error.getFila()+ "</td>");
                    archivo.write("</tr>");
                }

                archivo.write("</table>");
            }


            archivo.write("</div>");
            archivo.write("</body>");
            archivo.write("</html>");
            archivo.close();

        } catch (IOException e) {

            System.out.println("Error al generar el reporte de errores");
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
}
