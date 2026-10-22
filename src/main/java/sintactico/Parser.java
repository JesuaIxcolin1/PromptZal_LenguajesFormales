/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sintactico;

import java.util.ArrayList;
import lexico.Token;
import lexico.tipoToken;

/**
 *
 * @author jesua
 */
public class Parser {
    
    private ArrayList<Token> tokens;
    private ArrayList<errorSintactico> errores;
    private int posicion;
    
    public Parser(ArrayList<Token> tokens) {

        this.tokens = tokens;
        this.errores = new ArrayList<>();
        this.posicion = 0;
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
            
            consumir(tipoToken.DIRECTIVA);
            consumir(tipoToken.CADENA);
            
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
    
    private void agente(){
        consumir(tipoToken.AGENTE);
        consumir(tipoToken.ID);
        consumir(tipoToken.LLAVE_A);
        
        contexto();
        variables();
        comandos();
        
        consumir(tipoToken.LLAVE_B);
    }
    
    //************CONTEXTO***************
    private void contexto(){
        consumir(tipoToken.CONTEXTO);
        consumir(tipoToken.IGUAL);
        consumir(tipoToken.CADENA);
    }
    
    //*********VARIABLES****************
    private void variables(){
        if(es(tipoToken.VARIABLE)){
            consumir(tipoToken.VARIABLE);
            consumir(tipoToken.ID);
            consumir(tipoToken.IGUAL);
            
            expresion();
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
        consumir(tipoToken.COMANDO_IA);
        expresion();
        conectores();
        
        consumir(tipoToken.FLECHA);
        consumir(tipoToken.ID);
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
    consumir(tipoToken.EJECUTAR);
    consumir(tipoToken.ID);

    masEjecuciones();
    }
    
    private void masEjecuciones() {

        if (es(tipoToken.EJECUTAR)) {
            consumir(tipoToken.EJECUTAR);
            consumir(tipoToken.ID);
            masEjecuciones();
        }
    }
    
    //****************EXPORTAR******************
    private void exportar() {
        consumir(tipoToken.EXPORTAR);
        consumir(tipoToken.ID);
        masIds();
    }
    
    private void masIds() {
        if (es(tipoToken.COMA)) {
                
            consumir(tipoToken.COMA);
            consumir(tipoToken.ID);
            masIds();
    }
}
}
