/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semantico;

/**
 *
 * @author jesua
 */
public class errorSemantico {

    private String descripcion;
    private int fila;

    public errorSemantico(String descripcion, int fila) {
        this.descripcion = descripcion;
        this.fila = fila;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getFila() {
        return fila;
    }
}