/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semantico;
import java.util.ArrayList;
import ast.Programa;
import ast.Agente;
import ast.Variable;
import ast.Comando;


/**
 *
 * @author jesua
 */
public class TablaSimbolos {
    
    private ArrayList<Simbolo> simbolos;
    
    public TablaSimbolos() {
        simbolos = new ArrayList<>();
    }
    
    public void construir(Programa programa) {

        for (Agente agente : programa.getAgentes()) {

            // Se registra el agente
            simbolos.add(new Simbolo( agente.getNombre(),"AGENTE","GLOBAL",agente.getFila()));

            // Se registran las variables del agente
            for (Variable variable : agente.getVariables()) {

                simbolos.add(new Simbolo(variable.getNombre(),"VARIABLE",agente.getNombre(),variable.getFila()));
            }

            // Los resultados de los comandos tambien son variables
            for (Comando comando : agente.getComandos()) {

                simbolos.add(new Simbolo(comando.getDestino(),"RESULTADO_COMANDO",agente.getNombre(),comando.getFila()));
            }
        }
    }

    public ArrayList<Simbolo> getSimbolos() {
        return simbolos;
    }
}