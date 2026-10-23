/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ast;

/**
 *
 * @author jesua
 */
public class Ejecucion {

    private String nombreAgente;
    private int fila;

    public Ejecucion(String nombreAgente, int fila) {
        this.nombreAgente = nombreAgente;
        this.fila = fila;
    }

    public String getNombreAgente() {
        return nombreAgente;
    }

    public int getFila() {
        return fila;
    }
}
