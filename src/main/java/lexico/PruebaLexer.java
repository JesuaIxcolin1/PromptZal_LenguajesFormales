/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexico;

import java.io.StringReader;

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

        Token token;

        do {

            token = lexer.yylex();

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
    }
}
