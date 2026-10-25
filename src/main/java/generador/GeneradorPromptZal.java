/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package generador;
import ast.Agente;
import ast.Comando;
import ast.Directiva;
import ast.Ejecucion;
import ast.Exportacion;
import ast.Programa;
import ast.Variable;

/**
 *
 * @author jesua
 */
public class GeneradorPromptZal {

    public String generar(Programa programa) {

        StringBuilder salida = new StringBuilder();

        salida.append("============PROMPTZAL GENERADO=============\n\n");


        //****************DIRECTIVAS******************

        for (Directiva directiva : programa.getDirectivas()) {

            String tipo = directiva.getTipo();
            String valor = quitarComillas(directiva.getValor());

            if (tipo.equals("@modelo")) {

                salida.append("Modelo: ")
                        .append(valor)
                        .append("\n");

            } else if (tipo.equals("@rol")) {

                salida.append("Rol: ")
                        .append(valor)
                        .append("\n");

            } else if (tipo.equals("@formato")) {

                salida.append("Formato: ")
                        .append(valor)
                        .append("\n");
            }
        }

        salida.append("\n");

        //****************AGENTES EJECUTADOS******************

        for (Ejecucion ejecucion : programa.getEjecuciones()) {

            Agente agente = buscarAgente(
                    programa,
                    ejecucion.getNombreAgente()
            );

            if (agente != null) {

                salida.append("AGENTE: ")
                        .append(agente.getNombre())
                        .append("\n\n");

                salida.append("Contexto:\n");
                salida.append(
                        quitarComillas(agente.getContexto())
                );
                salida.append("\n\n");


                //****************VARIABLES******************

                if (!agente.getVariables().isEmpty()) {

                    salida.append("Fuentes y variables:\n");

                    for (Variable variable : agente.getVariables()) {

                        salida.append(
                                generarVariable(variable)
                        );

                        salida.append("\n");
                    }

                    salida.append("\n");
                }


                //****************COMANDOS******************

                salida.append("Instrucciones:\n");

                for (Comando comando : agente.getComandos()) {

                    salida.append(
                            generarComando(comando)
                    );

                    salida.append("\n");
                }

                salida.append("\n");
            }
        }


        //****************EXPORTACIONES******************

        salida.append("Resultados a exportar:\n");

        for (Exportacion exportacion : programa.getExportaciones()) {

            salida.append("- ")
                    .append(exportacion.getNombreVariable())
                    .append("\n");
        }

        return salida.toString();
    }


    //****************BUSCAR AGENTE******************

    private Agente buscarAgente(
            Programa programa,
            String nombre) {

        for (Agente agente : programa.getAgentes()) {

            if (agente.getNombre().equals(nombre)) {
                return agente;
            }
        }

        return null;
    }


    //****************GENERAR VARIABLE******************

    private String generarVariable(Variable variable) {

        String valor = variable.getValor();

        if (valor.startsWith("CARGAR(")
                && valor.endsWith(")")) {

            String archivo = valor.substring(
                    7,
                    valor.length() - 1
            );

            archivo = quitarComillas(archivo);

            return variable.getNombre()
                    + " proviene del archivo "
                    + archivo + ".";
        }

        return variable.getNombre()
                + " = "
                + quitarComillas(valor)
                + ".";
    }
    
    //****************GENERAR COMANDO******************
    private String generarComando(Comando comando) {

        String tipo = comando.getTipo();
        String principal = "";

        if (!comando.getArgumentos().isEmpty()) {
            principal = quitarComillas(comando.getArgumentos().get(0));
        }

        String sobre = obtenerConector(comando, "SOBRE");
        String desde = obtenerConector(comando, "DESDE");
        String en = obtenerConector(comando, "EN");
        String como = obtenerConector(comando, "COMO");

        String texto = "";


        if (tipo.equals("PREGUNTAR")) {

            if (sobre != null) {
                texto = "Basandote en " + quitarComillas(sobre) + ", responde: " + principal + ".";
            } else {
                texto = "Responde: " + principal + ".";
            }


        } else if (tipo.equals("GENERAR")) {

            texto = "Genera " + principal;

            if (sobre != null) {
                texto += " basandote en " + quitarComillas(sobre);
            }

            if (desde != null) {
                texto += " a partir de " + quitarComillas(desde);
            }

            texto += ".";


        } else if (tipo.equals("RESUMIR")) {

            texto = "Resume " + principal;

            if (en != null) {
                texto += " en un maximo de " + quitarComillas(en);
            }

            texto += ".";


        } else if (tipo.equals("ANALIZAR")) {

            texto = "Analiza " + principal;

            if (sobre != null) {
                texto += " tomando en cuenta " + quitarComillas(sobre);
            }

            if (desde != null) {
                texto += " desde " + quitarComillas(desde);
            }

            texto += ".";


        } else if (tipo.equals("TRADUCIR")) {

            texto = "Traduce " + principal;

            if (en != null) {
                texto += " a " + quitarComillas(en);
            }

            texto += ".";


        } else if (tipo.equals("CLASIFICAR")) {

            texto = "Clasifica " + principal;

            if (como != null) {
                texto += " como " + quitarComillas(como);
            }

            texto += ".";


        } else if (tipo.equals("EXTRAER")) {

            texto = "Extrae " + principal;

            if (sobre != null) {
                texto += " sobre " + quitarComillas(sobre);
            }

            if (desde != null) {
                texto += " desde " + quitarComillas(desde);
            }

            texto += ".";


        } else {

            texto = tipo + " " + principal + ".";
        }


        // El conector COMO puede indicar formato en otros comandos

        if (como != null && !tipo.equals("CLASIFICAR")) {
            texto += " Entrega el resultado en formato " + quitarComillas(como) + ".";
        }


        // Todos los comandos guardan un resultado

        texto += " Guarda el resultado como " + comando.getDestino() + ".";

        return texto;
    }


    //****************OBTENER CONECTOR******************

    private String obtenerConector(
            Comando comando,
            String conector) {

        for (String argumento : comando.getArgumentos()) {

            if (argumento.startsWith(conector + " ")) {

                return argumento.substring(
                        conector.length() + 1
                );
            }
        }

        return null;
    }


    //****************QUITAR COMILLAS******************

    private String quitarComillas(String texto) {

        if (texto == null) {
            return "";
        }

        if (texto.length() >= 2
                && texto.startsWith("\"")
                && texto.endsWith("\"")) {

            return texto.substring(
                    1,
                    texto.length() - 1
            );
        }

        return texto;
    }
}
