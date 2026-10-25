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
            archivo.write("body{"+ "font-family:Arial;"+ "background:#f4f4f4;"+ "padding:30px;"+ "color:#222;"+ "}");
            archivo.write(".contenedor{"+ "max-width:1200px;"+ "margin:auto;"+ "background:white;"+ "padding:25px;"+ "border-radius:10px;"+ "box-shadow:0 3px 12px rgba(0,0,0,0.10);"+ "}");
            archivo.write("h1{"+ "text-align:center;"+ "color:#222;"+ "margin-bottom:30px;"+ "}");
            archivo.write(".arbol{"+ "background:#fafafa;"+ "padding:25px;"+ "border-radius:8px;"+ "}");
            archivo.write("ul{"+ "line-height:2;"+ "}");
            archivo.write("li{"+ "margin:4px 0;"+ "}");
            
            archivo.write(".badge{"+ "display:inline-block;"+ "padding:5px 10px;"+ "border-radius:12px;"+ "font-weight:bold;"+ "font-size:12px;"+ "color:white;"+ "}");
            
            archivo.write(".programa{background:#006b57;}");
            archivo.write(".directiva{background:#8e44ad;}");
            archivo.write(".agente{background:#1f78ff;}");
            archivo.write(".contexto{background:#2980b9;}");
            archivo.write(".variable{background:#27ae60;}");
            archivo.write(".comando{background:#e67e22;}");
            archivo.write(".ejecucion{background:#2c3e50;}");
            archivo.write(".exportacion{background:#16a085;}");
            archivo.write(".argumento{background:#7f8c8d;}");
            
            archivo.write(".valor{"+ "font-family:monospace;"+ "font-weight:bold;"+ "margin-left:5px;"+ "}");
            archivo.write("</style>");
            
            archivo.write("</head>");
            archivo.write("<body>");
            archivo.write("<div class='contenedor'>");
            
            archivo.write("<h1>AST - PromptZal</h1>");
            
            archivo.write("<div class='arbol'>");
            archivo.write("<ul>");
            
            //****************PROGRAMA****************
            archivo.write("<li><span class='badge programa'>PROGRAMA</span></li>");
            archivo.write("<ul>");

            //****************DIRECTIVAS****************
            archivo.write("<li><span class='badge directiva'>DIRECTIVAS</span></li>");
            archivo.write("<ul>");
    
            for (Directiva directiva : programa.getDirectivas()) {
                archivo.write("<li><span class='badge directiva'>"+ escapar(directiva.getTipo())+ "</span>");
                archivo.write("<span class='valor'>= "+ escapar(directiva.getValor())+ "</span></li>");
            }
            
            archivo.write("</ul>");
            

            //****************AGENTES****************
            archivo.write("<li><span class='badge agente'>AGENTES</span></li>");
            archivo.write("<ul>");

            for (Agente agente : programa.getAgentes()) {
                
                archivo.write("<li><span class='badge agente'>AGENTE</span>");
                archivo.write("<span class='valor'>"+ escapar(agente.getNombre())+ "</span></li>");
                
                archivo.write("<ul>");

                //****************CONTEXTO****************
                archivo.write("<li><span class='badge contexto'>CONTEXTO</span>");
                archivo.write("<span class='valor'>"+ escapar(agente.getContexto())+ "</span></li>");


                //****************VARIABLES****************
                archivo.write("<li><span class='badge variable'>VARIABLES</span></li>");
                archivo.write("<ul>");
                
                for (Variable variable : agente.getVariables()) {
                    archivo.write("<li><span class='badge variable'>VARIABLE</span>");
                    archivo.write("<span class='valor'>"+ escapar(variable.getNombre())+ " = "+ escapar(variable.getValor())+ "</span></li>");
                }
                
                archivo.write("</ul>");


                //****************COMANDOS****************
                archivo.write("<li><span class='badge comando'>COMANDOS</span></li>");
                archivo.write("<ul>");
                
                for (Comando comando : agente.getComandos()) {
                    
                    archivo.write("<li><span class='badge comando'>"+ escapar(comando.getTipo())+ "</span>");
                    archivo.write("<span class='valor'>-> "+ escapar(comando.getDestino())+ "</span></li>");
                    
                    archivo.write("<ul>");
                    
                    for (String argumento : comando.getArgumentos()) {
                        archivo.write("<li><span class='badge argumento'>ARGUMENTO</span>");
                        archivo.write("<span class='valor'>"+ escapar(argumento)+ "</span></li>");
                    }
                    
                    archivo.write("</ul>");
                }
                
                archivo.write("</ul>");
                archivo.write("</ul>");
            }
            
            archivo.write("</ul>");


            //****************EJECUCIONES****************
            archivo.write("<li><span class='badge ejecucion'>EJECUCIONES</span></li>");
            archivo.write("<ul>");
            
            for (Ejecucion ejecucion : programa.getEjecuciones()) {
                archivo.write("<li><span class='badge ejecucion'>EJECUTAR</span>");
                archivo.write("<span class='valor'>"+ escapar(ejecucion.getNombreAgente())+ "</span></li>");
            }

            archivo.write("</ul>");


            //****************EXPORTACIONES****************
            archivo.write("<li><span class='badge exportacion'>EXPORTACIONES</span></li>");
            archivo.write("<ul>");

            for (Exportacion exportacion : programa.getExportaciones()) {
                archivo.write("<li><span class='badge exportacion'>EXPORTAR</span>");
                archivo.write("<span class='valor'>"+ escapar(exportacion.getNombreVariable())+ "</span></li>");
            }

            archivo.write("</ul>");
            
            archivo.write("</ul>");
            archivo.write("</ul>");
            
            archivo.write("</div>");
            archivo.write("</div>");
            
            archivo.write("</body>");
            archivo.write("</html>");
            
            archivo.close();
        
        } catch (IOException e) {
            System.out.println("Error al generar el reporte AST");
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
