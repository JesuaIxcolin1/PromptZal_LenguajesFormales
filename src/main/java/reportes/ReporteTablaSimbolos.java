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

            FileWriter archivo = new FileWriter("reporteTablaSimbolos.html");

            int agentes = 0;
            int variables = 0;
            int resultados = 0;

            for (Simbolo simbolo : tabla.getSimbolos()) {

                if (simbolo.getTipo().equals("AGENTE")) {
                    agentes++;
                } else if (simbolo.getTipo().equals("VARIABLE")) {
                    variables++;
                } else if (simbolo.getTipo().equals("RESULTADO_COMANDO")) {
                    resultados++;
                }
            }

            archivo.write("<!DOCTYPE html>");
            archivo.write("<html>");
            archivo.write("<head>");
            archivo.write("<meta charset='UTF-8'>");
            archivo.write("<title>Tabla de Simbolos - PromptZal</title>");
            archivo.write("<style>");
            archivo.write("body{"+ "font-family:Arial;"+ "background:#f4f4f4;"+ "padding:30px;"+ "color:#222;"+ "}");
            archivo.write(".contenedor{"+ "max-width:1200px;"+ "margin:auto;"+ "background:white;"+ "padding:25px;"+ "border-radius:10px;"+ "box-shadow:0 3px 12px rgba(0,0,0,0.10);"+ "}");
            archivo.write("h1{"+ "text-align:center;"+ "color:#222;"+ "margin-bottom:30px;"+ "}");
            archivo.write(".resumen{"+ "display:flex;"+ "gap:15px;"+ "margin-bottom:30px;"+ "}");
            archivo.write(".tarjeta{"+ "flex:1;"+ "padding:18px;"+ "border-radius:8px;"+ "color:white;"+ "text-align:center;"+ "}");
            archivo.write(".numero{"+ "font-size:28px;"+ "font-weight:bold;"+ "margin-top:5px;"+ "}");
            archivo.write(".agente{background:#1f78ff;}");
            archivo.write(".variable{background:#27ae60;}");
            archivo.write(".resultado{background:#e67e22;}");
            archivo.write(".global{background:#8e44ad;}");
            archivo.write(".ambito{background:#5d6d7e;}");
            archivo.write(".badge{"+ "display:inline-block;"+ "padding:6px 12px;"+ "border-radius:14px;"+ "font-weight:bold;"+ "font-size:12px;"+ "color:white;"+ "}");
            archivo.write("table{"+ "width:100%;"+ "border-collapse:collapse;"+ "background:white;"+ "}");
            archivo.write("th{"+ "background:#006b57;"+ "color:white;"+ "padding:12px;"+ "}");
            archivo.write("td{"+ "border-bottom:1px solid #ddd;"+ "padding:10px;"+ "text-align:center;"+ "}");
            archivo.write("tr:nth-child(even){"+ "background:#f8f9f9;"+ "}");
            archivo.write("tr:hover{"+ "background:#eef7f4;"+ "}");
            archivo.write("</style>");
            archivo.write("</head>");
            archivo.write("<body>");
            archivo.write("<div class='contenedor'>");
            archivo.write("<h1>Tabla de Simbolos - PromptZal</h1>");

            
            //****************RESUMEN****************
            archivo.write("<div class='resumen'>");
            archivo.write("<div class='tarjeta agente'>Agentes<div class='numero'>"+ agentes +"</div></div>");
            archivo.write("<div class='tarjeta variable'>Variables<div class='numero'>"+ variables +"</div></div>");
            archivo.write("<div class='tarjeta resultado'>Resultados de Comandos<div class='numero'>"+ resultados +"</div></div>");
            archivo.write("</div>");

            
            //****************TABLA****************
            archivo.write("<table>");
            archivo.write("<tr>");
            archivo.write("<th>Nombre</th>");
            archivo.write("<th>Tipo</th>");
            archivo.write("<th>Ambito</th>");
            archivo.write("<th>Fila</th>");
            archivo.write("</tr>");

            for (Simbolo simbolo : tabla.getSimbolos()) {

                String claseTipo = obtenerClaseTipo(simbolo.getTipo());
                String claseAmbito = obtenerClaseAmbito(simbolo.getAmbito());

                archivo.write("<tr>");
                archivo.write("<td>"+ simbolo.getNombre()+ "</td>");
                archivo.write("<td><span class='badge "+ claseTipo +"'>"+ simbolo.getTipo()+ "</span></td>");
                archivo.write("<td><span class='badge "+ claseAmbito +"'>"+ simbolo.getAmbito()+ "</span></td>");
                archivo.write("<td>"+ simbolo.getFila()+ "</td>");
                archivo.write("</tr>");
            }

            archivo.write("</table>");
            archivo.write("</div>");
            archivo.write("</body>");
            archivo.write("</html>");
            archivo.close();

        } catch (IOException e) {

            System.out.println("Error al generar el reporte de tabla de simbolos");
        }
    }

    //****************COLOR SEGUN TIPO DE SIMBOLO****************
    private String obtenerClaseTipo(String tipo) {

        switch (tipo) {

            case "AGENTE":
                return "agente";

            case "VARIABLE":
                return "variable";

            case "RESULTADO_COMANDO":
                return "resultado";

            default:
                return "ambito";
        }
    }

    //****************COLOR SEGUN AMBITO****************
    private String obtenerClaseAmbito(String ambito) {

        if (ambito.equals("GLOBAL")) {
            return "global";
        }

        return "ambito";
    }

}
