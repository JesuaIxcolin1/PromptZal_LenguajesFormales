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
public class Agente {

    private String nombre;
    private String contexto;
    private int fila;

    private ArrayList<Variable> variables;
    private ArrayList<Comando> comandos;

    public Agente(String nombre, int fila) {
        this.nombre = nombre;
        this.fila = fila;

        this.variables = new ArrayList<>();
        this.comandos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getContexto() {
        return contexto;
    }

    public int getFila() {
        return fila;
    }

    public void setContexto(String contexto) {
        this.contexto = contexto;
    }

    public void agregarVariable(Variable variable) {
        variables.add(variable);
    }

    public void agregarComando(Comando comando) {
        comandos.add(comando);
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public ArrayList<Comando> getComandos() {
        return comandos;
    }
}