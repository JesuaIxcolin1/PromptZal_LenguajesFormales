/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package semantico;

import java.util.ArrayList;
import ast.Programa;
import ast.Agente;
import ast.Ejecucion;
import ast.Variable;
import ast.Comando;
import ast.Exportacion;

/**
 *
 * @author jesua
 */
public class AnalizadorSemantico {

    private Programa programa;
    private ArrayList<errorSemantico> errores;

    public AnalizadorSemantico(Programa programa) {

        this.programa = programa;
        this.errores = new ArrayList<>();
    }

    public void analizar() {

        validarAgentesDuplicados();
        validarEjecuciones();
        validarVariables();
        validarExportaciones();

    }

    public ArrayList<errorSemantico> getErrores() {
        return errores;
    }

    private void validarAgentesDuplicados() {
            for (int i = 0; i < programa.getAgentes().size(); i++) {

        Agente agenteActual = programa.getAgentes().get(i);

        for (int j = i + 1; j < programa.getAgentes().size(); j++) {

            Agente agenteSiguiente = programa.getAgentes().get(j);

            if (agenteActual.getNombre().equals(agenteSiguiente.getNombre())) {

                errores.add(
                        new errorSemantico(
                                "El agente \""
                                + agenteSiguiente.getNombre()
                                + "\" ya fue declarado",
                                agenteSiguiente.getFila()
                        )
                );
            }
        }
    }

    }

    private void validarEjecuciones() {
        for (Ejecucion ejecucion : programa.getEjecuciones()) {
        boolean existe = false;
        
        for (Agente agente : programa.getAgentes()) {

            if (agente.getNombre().equals(ejecucion.getNombreAgente())) {
                existe = true;
                break;
            }
        }

        if (!existe) {

            errores.add( new errorSemantico("El agente \""+ ejecucion.getNombreAgente()+ "\" no existe", ejecucion.getFila()));
            }
        }

    }
    
    private void validarVariables() {
        
        for (Agente agente : programa.getAgentes()) {

        ArrayList<String> declarados = new ArrayList<>();

        // Primero se procesan las variables
        for (Variable variable : agente.getVariables()) {
            for (String identificador: variable.getIdentificadoresUsados()) {
                
                if (!declarados.contains(identificador)) {

                    errores.add(new errorSemantico("La variable \""+ identificador+ "\" no ha sido declarada anteriormente en el agente \""+ agente.getNombre()+ "\"",variable.getFila()));
                
                }
            }
            // Después de validar su expresión, la variable queda declarada.
            declarados.add(variable.getNombre());
        }

        // Después se procesan los comandos
        for (Comando comando : agente.getComandos()) {
            for (String identificador: comando.getIdentificadoresUsados()) {
                if (!declarados.contains(identificador)) {

                    errores.add(new errorSemantico("La variable \"" + identificador+ "\" no ha sido declarada anteriormente en el agente \""
                                    + agente.getNombre()+ "\"",comando.getFila()));
                }
            }

            // El resultado -> ID declara una nueva variable
            declarados.add(comando.getDestino());
            }
        }
    }
    
    private void validarExportaciones() {
        
        for (Exportacion exportacion : programa.getExportaciones()) {
            boolean existeEnAgenteEjecutado = false;
            for (Ejecucion ejecucion : programa.getEjecuciones()) {
                for (Agente agente : programa.getAgentes()) {
                    if (agente.getNombre().equals(ejecucion.getNombreAgente())) {
                    
                    // Buscar en variables declaradas
                    for (Variable variable : agente.getVariables()) {
                        if (variable.getNombre().equals(exportacion.getNombreVariable())) {
                            existeEnAgenteEjecutado = true; 
                            break;
                        }
                    }
                    
                    if (!existeEnAgenteEjecutado) {
                        
                        for (Comando comando : agente.getComandos()) {
                            if (comando.getDestino().equals(exportacion.getNombreVariable())) {
                                
                                existeEnAgenteEjecutado = true;
                                break;
                            }
                        }
                    }
                    }
                    
                    if (existeEnAgenteEjecutado) {
                        break;
                    }
                }
                
                if (existeEnAgenteEjecutado) {
                    break;
                }
            }

            if (!existeEnAgenteEjecutado) {

                errores.add(
                        new errorSemantico("La variable \""+ exportacion.getNombreVariable()+ "\" no existe en ningun agente ejecutado",
                                exportacion.getFila()) );
            }
        }
    }
}