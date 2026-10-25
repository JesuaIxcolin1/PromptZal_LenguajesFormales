/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import ast.Agente;
import ast.Comando;
import ast.Directiva;
import ast.Ejecucion;
import ast.Exportacion;
import ast.Programa;
import ast.Variable;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author jesua
 */
public class ReporteAST {
    
    public void generar(Programa programa) {
        try {
            FileWriter archivo = new FileWriter("reporteAST.html");
            
            archivo.write("<!DOCTYPE html>");
            archivo.write("<html>");
            archivo.write("<head>");
            archivo.write("<meta charset='UTF-8'>");
            archivo.write("<title>AST - PromptZal</title>");
            archivo.write("<style>");
            archivo.write("body{"+ "font-family:Arial;"+ "background:#f4f4f4;"+ "padding:30px;"+ "}");
            archivo.write("h1{"+ "text-align:center;"+ "color:#222;"+ "}");
            archivo.write(".arbol{"+ "background:white;"+ "padding:25px;"+ "border-radius:8px;"+ "}");
            archivo.write("ul{"+ "line-height:1.8;"+ "}");
            archivo.write(".titulo{"+ "font-weight:bold;"+ "color:#006b57;"+ "}");
            archivo.write("</style>");
            archivo.write("</head>");
            archivo.write("<body>");
            archivo.write("<h1>AST - PromptZal</h1>");
            archivo.write("<div class='arbol'>");
            archivo.write("<ul>");
            archivo.write("<li class='titulo'>Programa</li>");
            archivo.write("<ul>");

    //****************DIRECTIVAS****************
            archivo.write("<li class='titulo'>Directivas</li>");
            archivo.write("<ul>");
    
            for (Directiva directiva: programa.getDirectivas()) {
                archivo.write("<li>"+ escapar(directiva.getTipo()) + " = "+ escapar(directiva.getValor())+ "</li>");
            }
            
            archivo.write("</ul>");
            
    //****************AGENTES****************
            archivo.write("<li class='titulo'>Agentes</li>");
            archivo.write("<ul>");

            for (Agente agente : programa.getAgentes()) {
                archivo.write("<li><b>Agente:</b> "+ escapar(agente.getNombre())+ "</li>");
                archivo.write("<ul>");

                // Contexto
                archivo.write("<li><b>Contexto:</b> " + escapar(agente.getContexto()) + "</li>");

                // Variables
                archivo.write("<li class='titulo'>Variables</li>");
                archivo.write("<ul>");
                
                for (Variable variable: agente.getVariables()) {
                    archivo.write("<li>"+ escapar(variable.getNombre())+ " = "+ escapar(variable.getValor())+ "</li>" );
                }
                
                archivo.write("</ul>");

                // Comandos
                archivo.write("<li class='titulo'>Comandos</li>");
                archivo.write("<ul>");
                
                for (Comando comando : agente.getComandos()) {
                    
                    archivo.write("<li><b>"+ escapar(comando.getTipo())+ "</b> -> "+ escapar(comando.getDestino())+ "</li>");
                    archivo.write("<ul>");
                    
                    for (String argumento : comando.getArgumentos()) {
                        archivo.write("<li>"+ escapar(argumento)+ "</li>");
                    }                  
                    archivo.write("</ul>");
                }
                
                archivo.write("</ul>");
                archivo.write("</ul>");
            }
            
            archivo.write("</ul>");


            //****************EJECUCIONES****************
            archivo.write("<li class='titulo'>Ejecuciones</li>");
            archivo.write("<ul>");
            
            for (Ejecucion ejecucion : programa.getEjecuciones()) {archivo.write("<li>Ejecutar: "+ escapar(ejecucion.getNombreAgente())
                    + "</li>");
            }

            archivo.write("</ul>");


            //****************EXPORTACIONES****************
            archivo.write("<li class='titulo'>Exportaciones</li>");
            archivo.write("<ul>");

            for (Exportacion exportacion : programa.getExportaciones()) {
                archivo.write("<li>Exportar: "+ escapar(exportacion.getNombreVariable()) + "</li>");
            }

            archivo.write("</ul>");
            archivo.write("</ul>");
            archivo.write("</ul>");
            archivo.write("</div>");
            archivo.write("</body>");
            archivo.write("</html>");
            archivo.close();
        
        } catch (IOException e) {
            System.out.println("Error al generar el reporte AST");
        }
    }

    // Evita problemas con caracteres HTML

    private String escapar(String texto) {

        if (texto == null) {
            return "";
        }
        return texto.replace("&", "&amp;") .replace("<", "&lt;").replace(">", "&gt;");
    }
    
}
