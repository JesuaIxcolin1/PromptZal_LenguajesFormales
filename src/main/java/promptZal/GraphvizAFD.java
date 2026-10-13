/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package promptZal;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author jesua
 */
public class GraphvizAFD {
    
    
    //*********************GENERAR ARCHIVO DOT*********************
    public void generarDOT(){
        
        try{
            
            FileWriter archivo = new FileWriter("afd.dot");
        
        
            archivo.write("digraph AFD {\n");
            archivo.write("rankdir=LR;\n\n");
            archivo.write("graph [bgcolor=\"#F7F4EA\", pad=\"0.4\", nodesep=\"0.7\", ranksep=\"1.2\", dpi=180];\n");
            archivo.write("node [fontname=\"Arial\", fontsize=12, style=filled, fillcolor=\"#F1F8F5\", color=\"#007A5E\"];\n");
            archivo.write("edge [fontname=\"Arial\", fontsize=10, color=\"#444444\"];\n\n");
        
        
            //*******************ESTADO INICIAL********************
            archivo.write("inicio [shape=point];\n");
            archivo.write("inicio -> INICIAL;\n\n");
        
        
            //*******************ESTADOS DE ACEPTACION********************
            archivo.write("DIRECTIVA [shape=doublecircle];\n");
            archivo.write("CADENA_ACEPTADA [shape=doublecircle];\n");
            archivo.write("ENTERO [shape=doublecircle];\n");
            archivo.write("DECIMAL [shape=doublecircle];\n");
            archivo.write("IDENTIFICADOR [shape=doublecircle];\n");
            archivo.write("CONECTOR [shape=doublecircle];\n");
            archivo.write("OPERADOR [shape=doublecircle];\n");
            archivo.write("DELIMITADOR [shape=doublecircle];\n");
            
            //Los comentarios son aceptados pero se ignoran
            archivo.write("COMENTARIO_LINEA [shape=doublecircle];\n");
            archivo.write("COMENTARIO_BLOQUE_CERRADO [shape=doublecircle];\n\n");
        
        
            //*******************ESTADO ERROR********************
            archivo.write("ERROR [shape=circle];\n\n");
        
        
            //****************TRANSICIONES DESDE INICIAL****************
            archivo.write("INICIAL -> INICIAL [label=\"espacio, tab, salto de linea\"];\n");
            archivo.write("INICIAL -> ARROBA [label=\"@\"];\n");
            archivo.write("INICIAL -> CADENA [label=\"comilla doble\"];\n");
            archivo.write("INICIAL -> ENTERO [label=\"digito\"];\n");
            archivo.write("INICIAL -> IDENTIFICADOR [label=\"letra, _\"];\n");
            archivo.write("INICIAL -> SLASH [label=\"/\"];\n");
            archivo.write("INICIAL -> GUION [label=\"-\"];\n");
            archivo.write("INICIAL -> OPERADOR [label=\"=, +\"];\n");
            archivo.write("INICIAL -> DELIMITADOR [label=\"{ } ( ) , ;\"];\n");
            archivo.write("INICIAL -> ERROR [label=\"otro\"];\n\n");
        
        
            //****************TRANSICIONES DE DIRECTIVAS****************
            archivo.write("ARROBA -> DIRECTIVA [label=\"letra\"];\n");
            archivo.write("ARROBA -> ERROR [label=\"otro\"];\n");
            archivo.write("DIRECTIVA -> DIRECTIVA [label=\"letra\"];\n\n");
        
        
            //****************TRANSICIONES DE CADENAS****************
            archivo.write("CADENA -> CADENA [label=\"cualquier caracter excepto comilla y salto de linea\"];\n");
            archivo.write("CADENA -> CADENA_ACEPTADA [label=\"comilla doble\"];\n");
            archivo.write("CADENA -> ERROR [label=\"salto de linea\"];\n\n");
        
            //****************TRANSICIONES DE NUMEROS****************
            archivo.write("ENTERO -> ENTERO [label=\"digito\"];\n");
            archivo.write("ENTERO -> PUNTO_DECIMAL [label=\".\"];\n");
            
            archivo.write("PUNTO_DECIMAL -> DECIMAL [label=\"digito\"];\n");
            archivo.write("PUNTO_DECIMAL -> ERROR [label=\"otro\"];\n");
            
            archivo.write("DECIMAL -> DECIMAL [label=\"digito\"];\n\n");
        
        
            //****************TRANSICIONES DE IDENTIFICADORES****************
            archivo.write("IDENTIFICADOR -> IDENTIFICADOR [label=\"letra, digito, _\"];\n\n");
        
        
            //****************TRANSICIONES DE COMENTARIOS****************
            archivo.write("SLASH -> COMENTARIO_LINEA [label=\"/\"];\n");
            archivo.write("SLASH -> COMENTARIO_BLOQUE [label=\"*\"];\n");
            archivo.write("SLASH -> ERROR [label=\"otro\"];\n\n");
        
        
            //Comentario de linea
            archivo.write("COMENTARIO_LINEA -> COMENTARIO_LINEA [label=\"caracter diferente de salto\"];\n\n");
        
        
            //Comentario de bloque
            archivo.write("COMENTARIO_BLOQUE -> COMENTARIO_BLOQUE [label=\"caracter diferente de *\"];\n");
            archivo.write("COMENTARIO_BLOQUE -> ASTERISCO_COMENTARIO [label=\"*\"];\n");
            
            archivo.write("ASTERISCO_COMENTARIO -> COMENTARIO_BLOQUE_CERRADO [label=\"/\"];\n");
            archivo.write("ASTERISCO_COMENTARIO -> ASTERISCO_COMENTARIO [label=\"*\"];\n");
            archivo.write("ASTERISCO_COMENTARIO -> COMENTARIO_BLOQUE [label=\"otro\"];\n\n");
        
        
            //****************TRANSICIONES DEL CONECTOR ->****************
            archivo.write("GUION -> CONECTOR [label=\">\"];\n");
            archivo.write("GUION -> ERROR [label=\"otro\"];\n\n");
        
        
            archivo.write("}\n");
        
        
            archivo.close();
        
        
            System.out.println("Archivo afd.dot generado con exito");
        
        
        }catch(IOException e){
            
            System.out.println("Error al generar el archivo afd.dot");
            System.out.println(e.getMessage());
        }
    }
    
    
    
    //*********************GENERAR IMAGEN DEL AFD*********************
    public void generarImagen(){
        
        try{
            
            ProcessBuilder procesoGraphviz = new ProcessBuilder(
                    "dot",
                    "-Tpng",
                    "afd.dot",
                    "-o",
                    "afd.png"
            );
            
            
            Process proceso = procesoGraphviz.start();
            
            
            //Espera hasta que Graphviz termine de generar la imagen
            int resultado = proceso.waitFor();
            
            
            if(resultado == 0){
                
                System.out.println("Imagen afd.png generada con exito");
                
            }else{
                
                System.out.println("Error al generar la imagen del AFD");
            }
            
            
        }catch(IOException e){
            
            System.out.println("No se pudo ejecutar Graphviz");
            System.out.println("Verifique que Graphviz este instalado");
            System.out.println(e.getMessage());
            
            
        }catch(InterruptedException e){
            
            System.out.println("El proceso de Graphviz fue interrumpido");
            
            Thread.currentThread().interrupt();
        }
    }
    
    
    
    //*********************GENERAR AFD COMPLETO*********************
    public void generarAFD(){
        
        generarDOT();
        generarImagen();
    }
}
