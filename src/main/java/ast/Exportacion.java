/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ast;

/**
 *
 * @author jesua
 */
public class Exportacion {
    
    private String nombreVariable;
    private int fila;

    public Exportacion(String nombreVariable, int fila) {
        this.nombreVariable = nombreVariable;
        this.fila = fila;
    }

    public String getNombreVariable() {
        return nombreVariable;
    }

    public int getFila() {
        return fila;
    }
    
}
