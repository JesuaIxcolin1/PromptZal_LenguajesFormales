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
public class Variable {

    private String nombre;
    private String valor;
    private int fila;

    private ArrayList<String> identificadoresUsados;

    public Variable(String nombre, String valor, int fila) {

        this.nombre = nombre;
        this.valor = valor;
        this.fila = fila;

        this.identificadoresUsados = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getValor() {
        return valor;
    }

    public int getFila() {
        return fila;
    }

    public void agregarIdentificadorUsado(String identificador) {
        identificadoresUsados.add(identificador);
    }

    public ArrayList<String> getIdentificadoresUsados() {
        return identificadoresUsados;
    }
}