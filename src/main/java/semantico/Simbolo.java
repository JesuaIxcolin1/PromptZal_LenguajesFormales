/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semantico;

/**
 *
 * @author jesua
 */
public class Simbolo {

    private String nombre;
    private String tipo;
    private String ambito;
    private int fila;

    public Simbolo(String nombre, String tipo, String ambito, int fila) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.ambito = ambito;
        this.fila = fila;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getAmbito() {
        return ambito;
    }

    public int getFila() {
        return fila;
    }
}