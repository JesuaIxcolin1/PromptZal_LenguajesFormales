/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sintactico;

/**
 *
 * @author jesua
 */
public class errorSintactico {
    
    private String encontrado;
    private String esperado;
    private int fila;
    private int columna;
    
    public errorSintactico(String encontrado, String esperado, int fila, int columna) {
        this.encontrado = encontrado;
        this.esperado = esperado;
        this.fila = fila;
        this.columna = columna;
    }
    
    public String getEncontrado() {
        return encontrado;
    }

    public String getEsperado() {
        return esperado;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
    
}
