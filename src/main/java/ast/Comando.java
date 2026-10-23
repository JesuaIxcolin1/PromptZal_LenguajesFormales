/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ast;

import java.util.ArrayList;

/**
 *
 * @author jesua
 */
public class Comando {

    private String tipo;
    private String destino;
    private int fila;
    private ArrayList<String> identificadoresUsados;
    private ArrayList<String> argumentos;

    public Comando(String tipo, int fila) {
        this.tipo = tipo;
        this.fila = fila;
        this.identificadoresUsados = new ArrayList<>();

        this.argumentos = new ArrayList<>();
    }

    public void agregarArgumento(String argumento) {
        argumentos.add(argumento);
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDestino() {
        return destino;
    }

    public int getFila() {
        return fila;
    }

    public ArrayList<String> getArgumentos() {
        return argumentos;
    }
    
    public void agregarIdentificadorUsado(String identificador) {
    identificadoresUsados.add(identificador);
    }

    public ArrayList<String> getIdentificadoresUsados() {
        return identificadoresUsados;
    }
}
