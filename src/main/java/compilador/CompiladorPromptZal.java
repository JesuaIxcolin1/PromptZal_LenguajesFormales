/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador;
import java.io.StringReader;
import java.util.ArrayList;
import ast.Programa;
import generador.GeneradorPromptZal;
import lexico.Lexer;
import lexico.Token;
import lexico.errorLexico;
import lexico.tipoToken;
import semantico.AnalizadorSemantico;
import semantico.TablaSimbolos;
import semantico.errorSemantico;
import sintactico.Parser;
import sintactico.errorSintactico;

/**
 *
 * @author jesua
 */
public class CompiladorPromptZal {

    private ArrayList<Token> tokens;

    private ArrayList<errorLexico> erroresLexicos;
    private ArrayList<errorSintactico> erroresSintacticos;
    private ArrayList<errorSemantico> erroresSemanticos;

    private Programa programaAST;
    private TablaSimbolos tablaSimbolos;

    private String promptGenerado;


    public CompiladorPromptZal() {

        tokens = new ArrayList<>();

        erroresLexicos = new ArrayList<>();
        erroresSintacticos = new ArrayList<>();
        erroresSemanticos = new ArrayList<>();

        promptGenerado = "";
    }


    //****************COMPILAR******************

    public void compilar(String codigo) throws Exception {

        // Limpiar resultados anteriores

        tokens.clear();

        erroresLexicos.clear();
        erroresSintacticos.clear();
        erroresSemanticos.clear();

        programaAST = null;
        tablaSimbolos = null;
        promptGenerado = "";


        //****************ANALISIS LEXICO******************

        Lexer lexer = new Lexer(
                new StringReader(codigo)
        );

        Token token;

        do {

            token = lexer.yylex();

            tokens.add(token);

        } while (token.getTipo() != tipoToken.EOF);


        erroresLexicos.addAll(
                lexer.getListaErrores()
        );


        //****************ANALISIS SINTACTICO******************

        Parser parser = new Parser(tokens);

        parser.analizar();

        erroresSintacticos.addAll(
                parser.getErrores()
        );


        programaAST = parser.getProgramaAST();


        // Si existen errores sintacticos,
        // no se realiza el analisis semantico.

        if (!erroresLexicos.isEmpty() || !erroresSintacticos.isEmpty()) {
            return;
        }

        //****************TABLA DE SIMBOLOS******************

        tablaSimbolos = new TablaSimbolos();

        tablaSimbolos.construir(
                programaAST
        );


        //****************ANALISIS SEMANTICO******************

        AnalizadorSemantico analizadorSemantico
                = new AnalizadorSemantico(programaAST);

        analizadorSemantico.analizar();

        erroresSemanticos.addAll(
                analizadorSemantico.getErrores()
        );


        // Si existen errores semanticos,
        // no se genera el PromptZal.

        if (!erroresSemanticos.isEmpty()) {
            return;
        }


        //****************GENERACION DEL PROMPTZAL******************

        GeneradorPromptZal generador
                = new GeneradorPromptZal();

        promptGenerado
                = generador.generar(programaAST);
    }


    //****************RESULTADO******************

    public boolean compilacionExitosa() {

        return erroresLexicos.isEmpty()
                && erroresSintacticos.isEmpty()
                && erroresSemanticos.isEmpty();
    }


    //****************GETTERS******************

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public ArrayList<errorLexico> getErroresLexicos() {
        return erroresLexicos;
    }

    public ArrayList<errorSintactico> getErroresSintacticos() {
        return erroresSintacticos;
    }

    public ArrayList<errorSemantico> getErroresSemanticos() {
        return erroresSemanticos;
    }

    public Programa getProgramaAST() {
        return programaAST;
    }

    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
    }

    public String getPromptGenerado() {
        return promptGenerado;
    }
}
