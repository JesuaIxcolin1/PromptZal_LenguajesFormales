/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package promptZal;

import java.util.ArrayList;

/**
 *
 * @author jesua
 */
public class analizadorLexico {

    private String entrada;
    private int posicion;
    private int fila;
    private int columna;
    private int numeroToken;
    private estadoAFD estado;
    private ArrayList<Token> listaTokens;
    private ArrayList<errorLexico> listaErrores;

    public analizadorLexico(String entrada) {
        this.entrada = entrada;
        this.posicion = 0;
        this.fila = 1;
        this.columna = 1;
        this.numeroToken = 1;
        
        this.estado = estadoAFD.INICIAL;

        this.listaTokens = new ArrayList<>();
        this.listaErrores = new ArrayList<>();
    }

    public void analizador() {

        while (posicion < entrada.length()) {

            char actual = entrada.charAt(posicion);

            
            //*********************ESPACIOS Y TABULACIONES************************
            //Se ignoran pero se actualiza la posicion
            if (actual == ' ' || actual == '\t' || actual == '\r') {

                posicion++;
                columna++;
                
                
            //*********************SALTOS DE LINEA*****************************
            // Aumenta la fila y la columna vuelve a 1
            } else if (actual == '\n') {

                posicion++;
                fila++;
                columna = 1;
                
                
                
            //*******************COMENTARIOS********************
            }else if(actual == '/'){
                
                int filaInicio = fila;
                int columnaInicio = columna;
                
                // Se encontro el primer /
                estado = estadoAFD.SLASH;
                
                posicion++;
                columna++;
                
                
                //*******************COMENTARIO DE LINEA********************
                if(posicion < entrada.length() && entrada.charAt(posicion) == '/'){
                    
                    estado = estadoAFD.COMENTARIO_LINEA;
                    
                    //Avanza el segundo /
                    posicion++;
                    columna++;
                    
                    //Ignora todo hasta encontrar salto de linea
                    while(posicion < entrada.length() && entrada.charAt(posicion) != '\n'){
                        
                        posicion++;
                        columna++;
                    }
                    
                    
                    
                //*********************COMENTARIO DE BLOQUE*********************
                }else if(posicion < entrada.length() && entrada.charAt(posicion) == '*'){
                    
                    estado = estadoAFD.COMENTARIO_BLOQUE;
                    
                    StringBuilder lexema = new StringBuilder();
                    lexema.append('/');
                    lexema.append('*');
                    
                    //Avanza el *
                    posicion++;
                    columna++;
                    
                    boolean comentarioCerrado = false;
                    
                    while(posicion < entrada.length()){
                        
                        char caracter = entrada.charAt(posicion);
                        
                        
                        //Se encontro *
                        if(caracter == '*'){
                            
                            estado = estadoAFD.ASTERISCO_COMENTARIO;
                            
                            lexema.append(caracter);
                            
                            posicion++;
                            columna++;
                            
                            
                            //Verifica si despues del * viene /
                            if(posicion < entrada.length() && entrada.charAt(posicion) == '/'){
                                
                                lexema.append('/');
                                
                                posicion++;
                                columna++;
                                
                                estado = estadoAFD.COMENTARIO_BLOQUE_CERRADO;
                                
                                comentarioCerrado = true;
                                break;
                                
                            }else{
                                
                                estado = estadoAFD.COMENTARIO_BLOQUE;
                            }
                            
                            
                        //Salto de linea dentro del comentario
                        }else if(caracter == '\n'){
                            
                            lexema.append(caracter);
                            
                            posicion++;
                            fila++;
                            columna = 1;
                            
                            
                        //Cualquier otro caracter
                        }else{
                            
                            lexema.append(caracter);
                            
                            posicion++;
                            columna++;
                        }
                    }
                    
                    
                    //Si el comentario no encontro */
                    if(!comentarioCerrado){
                        
                        estado = estadoAFD.ERROR;
                        
                        errorLexico error = new errorLexico(
                                lexema.toString(),
                                "Comentario de Bloque sin cerrar",
                                filaInicio,
                                columnaInicio
                        );
                        
                        listaErrores.add(error);
                    }
                    
                    
                //Si aparece / sin formar comentario
                }else{
                    
                    estado = estadoAFD.ERROR;
                    
                    errorLexico error = new errorLexico(
                            "/",
                            "Caracter no reconocido",
                            filaInicio,
                            columnaInicio
                    );
                    
                    listaErrores.add(error);
                }
                
                
                estado = estadoAFD.INICIAL;
                
                
                
            //*************************DIRECTIVAS*************************
            }else if (actual == '@') {
                
                int filaInicio = fila;
                int columnaInicio = columna;
                
                StringBuilder lexema = new StringBuilder();
                
                
                //Estado despues de encontrar @
                estado = estadoAFD.ARROBA;
                
                lexema.append(actual);
                
                posicion++;
                columna++;
                
                
                //Se leen las letras que forman la directiva
                while (posicion < entrada.length()
                        && Character.isLetter(entrada.charAt(posicion))) {
                    
                    estado = estadoAFD.DIRECTIVA;

                    lexema.append(entrada.charAt(posicion));

                    posicion++;
                    columna++;
                }

                
                String palabra = lexema.toString();
                
                //Verifica si la directiva pertenece al lenguaje
                tipoToken tipo = identificarDirectiva(palabra);

                
                if (tipo != null) {

                    Token token = new Token(
                            numeroToken,
                            palabra,
                            tipo,
                            filaInicio,
                            columnaInicio
                    );

                    listaTokens.add(token);
                    numeroToken++;

                } else {
                    
                    estado = estadoAFD.ERROR;
                    
                    errorLexico error = new errorLexico(
                            palabra,
                            "Directiva no reconocida",
                            filaInicio,
                            columnaInicio
                    );

                    listaErrores.add(error);
                }
                
                
                estado = estadoAFD.INICIAL;
                
                
                
            //**************CADENAS*****************
            } else if(actual == '"' ){
                
                estado = estadoAFD.CADENA;
                
                int filaInicio = fila;
                int columnaInicio = columna;
                
                StringBuilder lexema = new StringBuilder();
                
                lexema.append(actual);
                
                posicion++;
                columna++;
                
                boolean cerrada = false;
                
                
                //Lee la cadena hasta encontrar la comilla de cierre
                while (posicion < entrada.length()){
                    
                    char caracter = entrada.charAt(posicion);
                   
                    
                    if (caracter == '"'){
                        
                        lexema.append(caracter);
                       
                        posicion++;
                        columna++;
                        
                        estado = estadoAFD.CADENA_ACEPTADA;
                        
                        cerrada = true;
                        break;
                    }
                    
                    
                    //Si hay salto de linea, no es una cadena valida
                    if(caracter == '\n'){
                        
                        break;
                    }
                    
                    
                    lexema.append(caracter);
                    
                    posicion++;
                    columna++;
                }
                
                
                if (cerrada){
                    
                    Token token = new Token(
                            numeroToken,
                            lexema.toString(),
                            tipoToken.CADENA,
                            filaInicio,
                            columnaInicio
                    );
                    
                    listaTokens.add(token);
                    numeroToken++;
                    
                    
                }else{
                    
                    estado = estadoAFD.ERROR;
                    
                    errorLexico error = new errorLexico(
                            lexema.toString(),
                            "Cadena sin cerrar",
                            filaInicio,
                            columnaInicio
                    );
                    
                    listaErrores.add(error);
                }
                
                
                estado = estadoAFD.INICIAL;
            
            
            
            //****************NUMEROS******************
            }else if (Character.isDigit(actual)) {
                
                //Todo numero empieza como entero
                estado = estadoAFD.ENTERO;
                
                int filaInicio = fila;
                int columnaInicio = columna;

                StringBuilder lexema = new StringBuilder();
                
                
                while (posicion < entrada.length()) {
                    
                    char caracter = entrada.charAt(posicion);
       
                    
                    //****************ESTADO ENTERO******************
                    if (estado == estadoAFD.ENTERO) {
             
                        if (Character.isDigit(caracter)) {
                            
                            lexema.append(caracter);
                 
                            posicion++;
                            columna++;
             
                            
                        } else if (caracter == '.') {

                            estado = estadoAFD.PUNTO_DECIMAL;
               
                            lexema.append(caracter);
               
                            posicion++;
                            columna++;
             
                        } else {
                            
                            break;
                        }
              
                        
                    //****************ESTADO PUNTO DECIMAL******************
                    } else if (estado == estadoAFD.PUNTO_DECIMAL) {
             
                        if (Character.isDigit(caracter)) {

                            estado = estadoAFD.DECIMAL;
                            
                            lexema.append(caracter);
             
                            posicion++;
                            columna++;
             
                        } else {

                            break;
                        }

                        
                    //****************ESTADO DECIMAL******************
                    } else if (estado == estadoAFD.DECIMAL) {
            
                        if (Character.isDigit(caracter)) {
                            
                            lexema.append(caracter);
                            
                            posicion++;
                            columna++;
             
                        } else {
                            
                            break;
                        }   
                    }
                }
                
                
                String numero = lexema.toString();

                
                //Si termina en ENTERO
                if (estado == estadoAFD.ENTERO) {
                    
                    Token token = new Token(
                            numeroToken,
                            numero,
                            tipoToken.ENTERO,
                            filaInicio,
                            columnaInicio
                    );

                    listaTokens.add(token);
                    numeroToken++;

                    
                //Si termina en DECIMAL
                } else if (estado == estadoAFD.DECIMAL) {

                    Token token = new Token(
                            numeroToken,
                            numero,
                            tipoToken.DECIMAL,
                            filaInicio,
                            columnaInicio
                    );

                    listaTokens.add(token);
                    numeroToken++;

                    
                //Si termina en PUNTO_DECIMAL hay error
                } else if(estado == estadoAFD.PUNTO_DECIMAL) {
                
                    estado = estadoAFD.ERROR;
                    
                    errorLexico error = new errorLexico(
                            numero,
                            "Numero decimal invalido",
                            filaInicio,
                            columnaInicio
                    );
                    
                    listaErrores.add(error);
                }
                
                
                estado = estadoAFD.INICIAL;
            
            
            
            // ***********IDENTIFICADORES Y PALABRAS RESERVADAS**************
            }else if (Character.isLetter(actual) || actual == '_') {
                
                estado = estadoAFD.IDENTIFICADOR;

                //Guarda la fila y columna donde comienza el token
                int filaInicio = fila;
                int columnaInicio = columna;

                StringBuilder lexema = new StringBuilder();

                
                while (posicion < entrada.length()
                        && (Character.isLetterOrDigit(entrada.charAt(posicion))
                        || entrada.charAt(posicion) == '_')) {
                    
                    lexema.append(entrada.charAt(posicion));

                    posicion++;
                    columna++;
                }

                
                String palabra = lexema.toString();
                
                //Reconoce el tipo de Token
                tipoToken tipo = identificarPalabra(palabra);

                
                Token token = new Token(
                        numeroToken,
                        palabra,
                        tipo,
                        filaInicio,
                        columnaInicio
                );

                listaTokens.add(token);
                numeroToken++;
                
                
                estado = estadoAFD.INICIAL;
                
                
                
            // **************CONECTOR -> ************************
            }else if (actual == '-' ){
                
                estado = estadoAFD.GUION;
                
                int filaInicio = fila;
                int columnaInicio = columna;
                
                posicion++;
                columna++;
                
                
                if(posicion < entrada.length() && entrada.charAt(posicion) == '>'){
                    
                    estado = estadoAFD.CONECTOR;
                    
                    Token token = new Token(
                            numeroToken,
                            "->",
                            tipoToken.CONECTOR,
                            filaInicio,
                            columnaInicio
                    );
                    
                    listaTokens.add(token);
                    numeroToken++;
                    
                    posicion++;
                    columna++;
                    
                    
                }else{
                    
                    estado = estadoAFD.ERROR;
                    
                    errorLexico error = new errorLexico(
                            "-",
                            "Conector Incompleto",
                            filaInicio,
                            columnaInicio
                    );
                    
                    listaErrores.add(error);
                }
                
                
                estado = estadoAFD.INICIAL;
               
                
                
            // ******************OPERADORES***********************
            }else if(actual == '=' || actual == '+'){
                
                estado = estadoAFD.OPERADOR;
                
                int columnaInicio = columna;
                
                Token token = new Token(
                        numeroToken,
                        String.valueOf(actual),
                        tipoToken.OPERADOR,
                        fila,
                        columnaInicio
                );
                
                listaTokens.add(token);
                numeroToken++;
                
                posicion++;
                columna++;
                
                
                estado = estadoAFD.INICIAL;
        
                    
                    
            //*********************DELIMITADORES************************
            }else if(actual == '{' || actual == '}' || actual == '('
                    || actual == ')' || actual == ',' || actual == ';'){
                
                estado = estadoAFD.DELIMITADOR;
                
                int columnaInicio = columna;
                
                Token token = new Token(
                        numeroToken,
                        String.valueOf(actual),
                        tipoToken.DELIMITADOR,
                        fila,
                        columnaInicio
                );
            
                listaTokens.add(token);
                numeroToken++;
                
                posicion++;
                columna++;
                
                
                estado = estadoAFD.INICIAL;
            

            
            //*************CARACTER NO RECONOCIDO*****************
            }else {
                
                estado = estadoAFD.ERROR;

                errorLexico error = new errorLexico(
                        String.valueOf(actual),
                        "Caracter no reconocido",
                        fila,
                        columna
                );

                listaErrores.add(error);

                
                //Se descarta solamente el caracter incorrecto
                //y el analizador continua
                posicion++;
                columna++;
                
                
                estado = estadoAFD.INICIAL;
            }
        }
    }
    
    
    
    //*******************IDENTIFICAR DIRECTIVAS*********************
    //Se comprueba si el lexema es una directiva valida
    private tipoToken identificarDirectiva(String lexema) {

        if (lexema.equals("@modelo")
                || lexema.equals("@rol")
                || lexema.equals("@formato")) {

            return tipoToken.DIRECTIVA;
        }

        return null;
    }
    
    
   
    //*******************IDENTIFICAR PALABRAS*********************
    private tipoToken identificarPalabra(String lexema) {
        
        
        // PALABRAS RESERVADAS
        if (lexema.equals("AGENTE")
                || lexema.equals("contexto")
                || lexema.equals("variable")
                || lexema.equals("EJECUTAR")
                || lexema.equals("EXPORTAR")) {

            return tipoToken.PALABRA_RESERVADA;
        }
        
        
        //COMANDOS DE IA
        if (lexema.equals("PREGUNTAR")
                || lexema.equals("GENERAR")
                || lexema.equals("RESUMIR")
                || lexema.equals("ANALIZAR")
                || lexema.equals("TRADUCIR")
                || lexema.equals("CLASIFICAR")
                || lexema.equals("EXTRAER")) {

            return tipoToken.COMANDO_IA;
        }
        
        
        //CONECTORES
        if (lexema.equals("SOBRE")
                || lexema.equals("DESDE")
                || lexema.equals("EN")
                || lexema.equals("COMO")) {

            return tipoToken.CONECTOR;
        }
        
        
        //FUNCION DEL SISTEMA
        if (lexema.equals("CARGAR")) {

            return tipoToken.FUNCION;
        }
        
        
        //En caso de no reconocer los anteriores, es un identificador
        return tipoToken.IDENTIFICADOR;
    }

    
    
    public ArrayList<Token> getlistaTokens() {
        
        return listaTokens;
    }

    
    
    public ArrayList<errorLexico> getlistaErrores() {
        
        return listaErrores;
    }
}
