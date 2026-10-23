/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ast;

/**
 *
 * @author jesua
 */
public class Directiva {
    
    private String tipo;
    private String valor;
    private int fila;
    
    public Directiva(String tipo, String valor, int fila){
        this.tipo = tipo;
        this.valor = valor;
        this.fila = fila;
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public String getValor(){
        return valor;
    }
    
    public int getFila(){
        return fila;
    }
    
}
