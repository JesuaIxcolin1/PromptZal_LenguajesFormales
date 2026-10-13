/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexico;

/**
 *
 * @author jesua
 */
public class Token {
    private tipoToken tipo;
    private String lexema;
    private int fila;
    private int columna;
    
    public Token(tipoToken tipo, String lexema, int fila, int columna){
        
        this.tipo = tipo;
        this.lexema = lexema;
        this.fila = fila;
        this.columna = columna;
    
    }
    
    public tipoToken getTipo(){
        
        return tipo;
    }
    
    public String getLexema(){
        
        return lexema;
    }
    
    public int getFila(){
        
        return fila;
    }
    
    public int getColumna(){
        
        return columna;
    }
    
}
