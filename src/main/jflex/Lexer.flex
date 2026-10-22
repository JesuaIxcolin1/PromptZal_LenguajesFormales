package lexico;

import java.util.ArrayList;

%%

%public
%class Lexer
%unicode
%line
%column
%type Token
%state COMENTARIO_BLOQUE

%{

    private ArrayList<errorLexico> listaErrores = new ArrayList<>();

    private int filaInicioComentario;
    private int columnaInicioComentario;

    public ArrayList<errorLexico> getListaErrores(){
        return listaErrores;

    }

    private Token crearToken(tipoToken tipo){
        return new Token(tipo, yytext(), yyline + 1, yycolumn + 1);
        
    }

    private void registrarError(String lexema, String tipoError, int fila, int columna){
        listaErrores.add(new errorLexico(lexema, tipoError, fila, columna));
    }
%}

    LETRA = [a-zA-Z]
    DIGITO = [0-9]
    ID = ({LETRA}|_)({LETRA}|{DIGITO}|_)*
    ENTERO = {DIGITO}+
    DECIMAL = {DIGITO}+ "."{DIGITO}+
    CADENA = \"[^\r\n\"]*\"
    ESPACIO = [ \t\r\n]+

    DIRECTIVA = "@modelo"|"@rol"|"@formato"
    COMANDO = "PREGUNTAR"|"GENERAR"|"RESUMIR"|"ANALIZAR"|"TRADUCIR"|"CLASIFICAR"|"EXTRAER"
    CONECTOR = "SOBRE"|"DESDE"|"EN"|"COMO"

    DIRECTIVA_INVALIDA = "@"({LETRA}|{DIGITO}|_)*
    DECIMAL_INVALIDO = {DIGITO}+"."
    CADENA_SIN_CERRAR = \"[^\r\n\"]*
%%

    //***********ESPACIOS*********
    <YYINITIAL>{ESPACIO} { //SE IGNORAN ESPACIOS, TABULACIONES Y SALTOS DE LINEA
    }

    //********COMENTARIOS*********
       <YYINITIAL>"//"[^\r\n]* {// Comentario de linea ignorado
    }

    <YYINITIAL>"/*" {

    filaInicioComentario = yyline + 1;
    columnaInicioComentario = yycolumn + 1;

    yybegin(COMENTARIO_BLOQUE);
    }

    <COMENTARIO_BLOQUE>"*/" {
        yybegin(YYINITIAL);
    }

    <COMENTARIO_BLOQUE>\r\n {
    }

    <COMENTARIO_BLOQUE>\r {
    }

    <COMENTARIO_BLOQUE>\n {
    }

    <COMENTARIO_BLOQUE>. {
    }

    //**********DIRECTIVAS*************
    <YYINITIAL>{DIRECTIVA} {
        return crearToken(tipoToken.DIRECTIVA);
    }

    //*********PALABRAS RESERVADAS***********
    <YYINITIAL>"AGENTE" {
        return crearToken(tipoToken.AGENTE);
    }

        <YYINITIAL>"contexto" {
        return crearToken(tipoToken.CONTEXTO);
    }
      
        <YYINITIAL>"variable" {
        return crearToken(tipoToken.VARIABLE);
    }

        <YYINITIAL>"EJECUTAR" {
        return crearToken(tipoToken.EJECUTAR);
    }

        <YYINITIAL>"EXPORTAR" {
        return crearToken(tipoToken.EXPORTAR);
    }
    

    //*********COMANDOS*************
    <YYINITIAL>{COMANDO} {
        return crearToken(tipoToken.COMANDO_IA);
    }

    //*********FUNCION CARGAR************
    <YYINITIAL>"CARGAR" {
        return crearToken(tipoToken.CARGAR);
    }

    //*********CONECTORES**********
    <YYINITIAL>{CONECTOR} {
        return crearToken(tipoToken.CONECTOR);
    }

    //************FLECHA*************
    <YYINITIAL>"->" {
        return crearToken(tipoToken.FLECHA);
    }

    //**********OPERADORES*************
    <YYINITIAL>"=" {
        return crearToken(tipoToken.IGUAL);
    }
    
    <YYINITIAL>"+" {
        return crearToken(tipoToken.MAS);
    }

    //**********DELIMITADORES***************
    <YYINITIAL>"{" {
        return crearToken(tipoToken.LLAVE_A);
    }

    <YYINITIAL>"}" {
        return crearToken(tipoToken.LLAVE_B);
    }

    <YYINITIAL>"(" {
        return crearToken(tipoToken.PAR_A);
    }

    <YYINITIAL>")" {
        return crearToken(tipoToken.PAR_B);
    }

    <YYINITIAL>"," {
        return crearToken(tipoToken.COMA);
    }

    //***********CADENAS*****************
    <YYINITIAL>{CADENA} {
        return crearToken(tipoToken.CADENA);
    }

    //***************NUMEROS******************
    <YYINITIAL>{DECIMAL} {
        return crearToken(tipoToken.NUMERO);
    }

    <YYINITIAL>{ENTERO} {
        return crearToken(tipoToken.NUMERO);
    }

    //****************IDENTIFICADORES*************
    <YYINITIAL>{ID} {
        return crearToken(tipoToken.ID);
    }

    //****************ERRORES LEXICOS******************
    <YYINITIAL>{DIRECTIVA_INVALIDA} {
        registrarError(yytext(),"Directiva no reconocida",yyline + 1,yycolumn + 1);
    }

    <YYINITIAL>{DECIMAL_INVALIDO} {
        registrarError(yytext(),"Numero decimal invalido",yyline + 1,yycolumn + 1);
    }
    
    <YYINITIAL>{CADENA_SIN_CERRAR} {
        registrarError(yytext(),"Cadena sin cerrar",yyline + 1,yycolumn + 1);
    }

    <YYINITIAL>. {
        registrarError(yytext(),"Caracter no reconocido",yyline + 1,yycolumn + 1);
    }

    //****************FIN DEL ARCHIVO******************
    <YYINITIAL><<EOF>> {
        return new Token(tipoToken.EOF,"EOF",yyline + 1,yycolumn + 1);
    }

    <COMENTARIO_BLOQUE><<EOF>> {
        registrarError("/*","Comentario de bloque sin cerrar",filaInicioComentario, columnaInicioComentario);
        
        yybegin(YYINITIAL);
        return new Token(tipoToken.EOF,"EOF",yyline + 1,yycolumn + 1);
    }