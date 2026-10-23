/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sintactico;

import java.util.ArrayList;
import lexico.Token;
import lexico.tipoToken;
import ast.Programa;
import ast.Directiva;
import ast.Agente;
import ast.Variable;
import ast.Comando;
import ast.Ejecucion;
import ast.Exportacion;

/**
 *
 * @author jesua
 */
public class Parser {
    
    private ArrayList<Token> tokens;
    private ArrayList<errorSintactico> errores;
    private int posicion;
    private Programa programaAST;
    private Agente agenteActual;

    
    public Parser(ArrayList<Token> tokens) {

        this.tokens = tokens;
        this.errores = new ArrayList<>();
        this.posicion = 0;
        
        this.programaAST = new Programa();
    }
    
    public Programa getProgramaAST(){
        return programaAST;
    }
    
    public ArrayList<errorSintactico> getErrores() {
        return errores;
    
    }
    
    //Ver Token actual (posicion, tipo)
    private Token actual() {

    if (posicion < tokens.size()) {
        return tokens.get(posicion);
        
        }else

    return tokens.get(tokens.size() - 1);
    
    }   
    
    // Avanza de posicion
    private void avanzar() {

    if (posicion < tokens.size() - 1) {
        posicion++;
        }
    }
    
    private boolean esSincronizacion() {
        return es(tipoToken.AGENTE)|| es(tipoToken.CONTEXTO)|| es(tipoToken.VARIABLE)|| es(tipoToken.COMANDO_IA)
            || es(tipoToken.LLAVE_B)|| es(tipoToken.EJECUTAR)|| es(tipoToken.EXPORTAR) || es(tipoToken.EOF);
    }
    
    private void recuperar() {
        while (!es(tipoToken.EOF) && !esSincronizacion()) {
            avanzar();
        }
    }
    
    //Ver si el tipo de token es el esperado
    private boolean es(tipoToken tipo) {
    return actual().getTipo() == tipo;
    }
    
    private void consumir(tipoToken esperado){
        if(es(esperado)){
            avanzar();
        }else{
            Token tokenActual = actual();
            
            errores.add(new errorSintactico(tokenActual.getTipo().toString(),
            esperado.toString(), tokenActual.getFila(), tokenActual.getColumna()));
            recuperar();
        }
    
    }
    
    public void analizar(){
        programa();
    }
    
    private void programa(){
        directivas();
        agentes();
        ejecuciones();
        exportar();
        
        consumir(tipoToken.EOF);
    }
    
    //*********DIRECTIVAS**************
    private void directivas(){
        if(es(tipoToken.DIRECTIVA)){
            
            Token tokenDirectiva = actual();
            consumir(tipoToken.DIRECTIVA);
            Token tokenValor = actual();
            consumir(tipoToken.CADENA);
            
            Directiva directiva = new Directiva(tokenDirectiva.getLexema(), tokenValor.getLexema(),
            tokenDirectiva.getFila());
            
            programaAST.agregarDirectiva(directiva);
            directivas();
        }
    }
    
    //*************AGENTES*******************
    private void agentes(){
        agente();
        masAgentes();
    }
    
    private void masAgentes(){
        if(es(tipoToken.AGENTE)){
            agente();
            masAgentes();
        }
    }
    
    private void agente() {

        consumir(tipoToken.AGENTE);

        Token tokenNombre = actual();

        consumir(tipoToken.ID);

        agenteActual = new Agente(
                tokenNombre.getLexema(),
                tokenNombre.getFila()
        );

        consumir(tipoToken.LLAVE_A);

        contexto();
        variables();
        comandos();

        consumir(tipoToken.LLAVE_B);

        programaAST.agregarAgente(agenteActual);

        agenteActual = null;
    }
    
    //************CONTEXTO***************
    private void contexto(){
    consumir(tipoToken.CONTEXTO);
    consumir(tipoToken.IGUAL);

    Token tokenContexto = actual();

    consumir(tipoToken.CADENA);

    if (agenteActual != null) {
        agenteActual.setContexto(tokenContexto.getLexema());
         }
    }
    
    //*********VARIABLES****************
    private void variables() {
        
        if (es(tipoToken.VARIABLE)) {
        consumir(tipoToken.VARIABLE);
        Token tokenNombre = actual();
        consumir(tipoToken.ID);
        consumir(tipoToken.IGUAL);

        ArrayList<String> identificadoresUsados = new ArrayList<>();

        String valorVariable = obtenerExpresionTexto(identificadoresUsados,false);

        Variable variable = new Variable(tokenNombre.getLexema(),valorVariable,tokenNombre.getFila());

        for (String identificador : identificadoresUsados) {
            variable.agregarIdentificadorUsado(identificador);
        }

        if (agenteActual != null) {
            agenteActual.agregarVariable(variable);
        }

        variables();
        }
    }

    
    //*************EXPRESIONES*****************
    private void expresion(){
        termino();
        masTerminos();
    }
    
    private void masTerminos(){
        if(es(tipoToken.MAS)){
            consumir(tipoToken.MAS);
            
            termino();
            masTerminos();
        }
    }
    
    private void termino(){
        if(es(tipoToken.CADENA)){
            consumir(tipoToken.CADENA);
            
        }else if(es(tipoToken.ID)){
            consumir(tipoToken.ID);
            
        }else if(es(tipoToken.NUMERO)){
            consumir(tipoToken.NUMERO);
            unidad();
            
        }else if(es(tipoToken.CARGAR)){
            cargar();
            
        }else{
            Token tokenActual = actual();
            
            errores.add(new errorSintactico(tokenActual.getTipo().toString(),
            "CADENA, ID, NUMERO o CARGAR", tokenActual.getFila(), tokenActual.getColumna()));
        }
    }
    
    private void unidad(){
        if(es(tipoToken.ID)){
            consumir(tipoToken.ID);
        }
    }
    
    //************CARGAR*******************
    private void cargar(){
        consumir(tipoToken.CARGAR);
        consumir(tipoToken.PAR_A);
        consumir(tipoToken.CADENA);
        consumir(tipoToken.PAR_B);
     
    }
    
    //****************COMANDOS******************
    private void comandos() {
        comando();
        masComandos();
    }
    
    private void masComandos() {

    if (es(tipoToken.COMANDO_IA)) {

        comando();
        masComandos();
        }
    }
    
    private void comando() {

    Token tokenComando = actual();

    consumir(tipoToken.COMANDO_IA);

    Comando comando = new Comando( tokenComando.getLexema(),tokenComando.getFila());
    ArrayList<String> identificadoresUsados = new ArrayList<>();
    boolean ignorarPrimerId = tokenComando.getLexema().equals("GENERAR");
    String argumentoPrincipal = obtenerExpresionTexto(identificadoresUsados,ignorarPrimerId);
    comando.agregarArgumento(argumentoPrincipal);

    while (es(tipoToken.CONECTOR)) {

        Token tokenConector = actual();

        consumir(tipoToken.CONECTOR);
        String argumento= obtenerExpresionTexto(identificadoresUsados,false);

        comando.agregarArgumento(tokenConector.getLexema()+ " "+ argumento);
        }
    
    consumir(tipoToken.FLECHA);
    Token tokenDestino = actual();
    consumir(tipoToken.ID);

    comando.setDestino(tokenDestino.getLexema());
    for (String identificador : identificadoresUsados) {
        comando.agregarIdentificadorUsado(identificador);
        }
    
    if (agenteActual != null) {
        agenteActual.agregarComando(comando);
         }
    }
    
    
    private void conectores() {
        if (es(tipoToken.CONECTOR)) {
            consumir(tipoToken.CONECTOR);
            
            expresion();
            conectores();
        }
    }
    
    //****************EJECUCIONES******************
    private void ejecuciones() {

        Token tokenEjecutar = actual();

        consumir(tipoToken.EJECUTAR);

        Token tokenAgente = actual();

        consumir(tipoToken.ID);

        Ejecucion ejecucion = new Ejecucion(
                tokenAgente.getLexema(),
                tokenEjecutar.getFila()
        );

        programaAST.agregarEjecucion(ejecucion);

        masEjecuciones();
    }
    
    private void masEjecuciones() {

        if (es(tipoToken.EJECUTAR)) {

            Token tokenEjecutar = actual();

            consumir(tipoToken.EJECUTAR);

            Token tokenAgente = actual();

            consumir(tipoToken.ID);

            Ejecucion ejecucion = new Ejecucion(
                    tokenAgente.getLexema(),
                    tokenEjecutar.getFila()
            );

            programaAST.agregarEjecucion(ejecucion);

            masEjecuciones();
        }
    }
    
    //****************EXPORTAR******************
    private void exportar() {

        consumir(tipoToken.EXPORTAR);

        Token tokenVariable = actual();

        consumir(tipoToken.ID);

        Exportacion exportacion = new Exportacion(
                tokenVariable.getLexema(),
                tokenVariable.getFila()
        );

        programaAST.agregarExportacion(exportacion);

        masIds();
    }

    private void masIds() {

        if (es(tipoToken.COMA)) {

            consumir(tipoToken.COMA);

            Token tokenVariable = actual();

            consumir(tipoToken.ID);

            Exportacion exportacion = new Exportacion(
                    tokenVariable.getLexema(),
                    tokenVariable.getFila()
            );

            programaAST.agregarExportacion(exportacion);

            masIds();
        }
    }
    
    private String obtenerExpresionTexto(
        ArrayList<String> identificadoresUsados,
        boolean ignorarPrimerId) {
        String texto = obtenerTerminoTexto(identificadoresUsados,ignorarPrimerId);

        while (es(tipoToken.MAS)) {

            consumir(tipoToken.MAS);

            texto += " + ";

            texto += obtenerTerminoTexto(identificadoresUsados,false);
        }

        return texto;
    }
    
    private String obtenerTerminoTexto(
        ArrayList<String> identificadoresUsados,boolean ignorarId) {

        String texto = "";

        if (es(tipoToken.CADENA)) {

            texto = actual().getLexema();
            consumir(tipoToken.CADENA);

        } else if (es(tipoToken.ID)) {

            texto = actual().getLexema();

            if (!ignorarId) {identificadoresUsados.add(actual().getLexema());
            }

            consumir(tipoToken.ID);

        } else if (es(tipoToken.NUMERO)) {

            texto = actual().getLexema();

            consumir(tipoToken.NUMERO);

            if (es(tipoToken.ID)) {

                texto += " " + actual().getLexema();

                consumir(tipoToken.ID);
            }

        } else if (es(tipoToken.CARGAR)) {

            texto = actual().getLexema();

            consumir(tipoToken.CARGAR);
            consumir(tipoToken.PAR_A);

            texto += "(" + actual().getLexema();

            consumir(tipoToken.CADENA);
            consumir(tipoToken.PAR_B);

            texto += ")";
        }

        return texto;
    }
}
