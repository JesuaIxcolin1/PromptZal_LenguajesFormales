/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexico;

import java.io.StringReader;
import java.util.ArrayList;
import sintactico.Parser;
import sintactico.errorSintactico;

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
                + "AGENTE analista\n" // ERROR 1: falta {
                + "    contexto =\n" // ERROR 2: falta CADENA
                + "    variable ventas = CARGAR(\"ventas.csv\")\n"
                + "\n"
                + "    PREGUNTAR \"Cuales son las tendencias principales?\" SOBRE ventas -> tendencias\n"
                + "    RESUMIR tendencias EN 100 palabras -> resumen\n"
                + "\n" // ERROR 3: falta }
                + "EJECUTAR\n" // ERROR 4: falta ID del agente
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
    }  
}
