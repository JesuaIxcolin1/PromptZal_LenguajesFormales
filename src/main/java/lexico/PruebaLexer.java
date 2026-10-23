/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexico;

import java.io.StringReader;
import java.util.ArrayList;
import sintactico.Parser;
import sintactico.errorSintactico;
import ast.Programa;
import ast.Directiva;
import ast.Agente;
import ast.Variable;
import ast.Comando;

/**
 *
 * @author jesua
 */

public class PruebaLexer {

    public static void main(String[] args) throws Exception {

String codigo =
        "@modelo \"claude-sonnet-4-6\"\n"
        + "@rol \"analista de datos\"\n"
        + "@formato \"markdown\"\n"
        + "\n"
        + "// Comentario de prueba\n"
        + "\n"
        + "AGENTE analista {\n"
        + "    contexto = \"Eres un analista de datos experto\"\n"
        + "    variable ventas = CARGAR(\"ventas.csv\")\n"
        + "\n"
        + "    PREGUNTAR \"Cuales son las tendencias principales?\" SOBRE ventas -> tendencias\n"
        + "    RESUMIR tendencias EN 100 palabras -> resumen\n"
        + "}\n"
        + "\n"
        + "EJECUTAR analista\n"
        + "EXPORTAR resumen\n";

        Lexer lexer = new Lexer(new StringReader(codigo));
        ArrayList<Token> listaTokens = new ArrayList<>();

        Token token;

        do {

            token = lexer.yylex();
            listaTokens.add(token);

            System.out.println(
                    token.getTipo()
                    + " | "
                    + token.getLexema()
                    + " | Fila: "
                    + token.getFila()
                    + " | Columna: "
                    + token.getColumna()
            );

        } while (token.getTipo() != tipoToken.EOF);

        System.out.println("\nERRORES LEXICOS:");

        if (lexer.getListaErrores().isEmpty()) {

            System.out.println("No hay errores lexicos.");

        } else {

            for (errorLexico error : lexer.getListaErrores()) {

                System.out.println(
                        error.getLexema()
                        + " | "
                        + error.getTipoError()
                        + " | Fila: "
                        + error.getFila()
                        + " | Columna: "
                        + error.getColumna()
                );
            }
        }
        
        //****************PRUEBA DEL PARSER******************
        Parser parser = new Parser(listaTokens);
        parser.analizar();
        System.out.println("\nERRORES SINTACTICOS:");

        if (parser.getErrores().isEmpty()) {

            System.out.println("No hay errores sintacticos.");

        } else {

            for (errorSintactico error : parser.getErrores()) {

                System.out.println("Encontrado: "+ error.getEncontrado()+ " | Esperado: "+ error.getEsperado()+ " | Fila: "
                + error.getFila() + " | Columna: "+ error.getColumna());
            }
        }

        
        //****************PRUEBA DEL AST******************
        
    if (parser.getErrores().isEmpty()) {
        
        Programa programaAST = parser.getProgramaAST();

            System.out.println("\nAST - DIRECTIVAS:");

            for (Directiva directiva : programaAST.getDirectivas()) {

                System.out.println(
                        directiva.getTipo()
                        + " = "
                        + directiva.getValor()
                        + " | Fila: "
                        + directiva.getFila()
                );
            }

            System.out.println("\nAST - AGENTES:");

            for (Agente agente : programaAST.getAgentes()) {

                System.out.println(
                        "Agente: "
                        + agente.getNombre()
                        + " | Fila: "
                        + agente.getFila()
                );

                System.out.println(
                        "Contexto: "
                        + agente.getContexto()
                );
                
                System.out.println("");
                System.out.println("Variables:");

                for (Variable variable : agente.getVariables()) {

                    System.out.println(
                            variable.getNombre()
                            + " = "
                            + variable.getValor()
                            + " | Fila: "
                            + variable.getFila()
                    );
                }
                
                System.out.println("");
                System.out.println("Comandos:");

                for (Comando comando : agente.getComandos()) {

                    System.out.println(
                            comando.getTipo()
                            + " -> "
                            + comando.getDestino()
                            + " | Fila: "
                            + comando.getFila()
                    );

                    for (String argumento : comando.getArgumentos()) {

                        System.out.println(
                                "   Argumento: "
                                + argumento
                        );
                    }
                }
                
                System.out.println("");
                System.out.println("\nAST - EJECUCIONES:");
                for (String ejecucion : programaAST.getEjecuciones()) {

                    System.out.println(
                            "Ejecutar: " + ejecucion );
                }
                
                System.out.println("");
                System.out.println("\nAST - EXPORTACIONES:");

                for (String exportacion : programaAST.getExportaciones()) {

                    System.out.println("Exportar: "+ exportacion);
                }
            }
        }
        
    } 
}