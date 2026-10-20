package lexico;

import java.util.ArrayList;

%%

%public
%class Lexer
%unicode
%column
%type Token
%state COMENTARIO_BLOQUE

%{

    private ArrayList<errorLexico> listaErrores = new ArrayList<>();

    private int filaInicioComentario;
    private int colmunaInicioComentario;

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
    ESPACIO = [\t\r\n]+

    DIRECTIVA = "@modelo"|"@rol"|"@formato"
    COMANDO = "PREGUNTAR"|"GENERAR"|"RESUMIR"|"ANALIZAR"|"TRADUCIR"|"CLASIFICAR"|"EXTRAER"
    CONECTOR = "SOBRE"|"DESDE"|"EN"|"COMO"

    DIRECTIVA_INVALIDA = "@"({LETRA}|{DIGITO}|_)*
    DECIMAL_INVALIDO = {DIGITO}+"."
    CADENA_SIN_CERRAR = \"[^\r\n\"]*
    %%

    //***********ESPACIOS*********
    <YINITIAL>{ESPACIO}{ //SE IGNORAN ESPACIOS, TABULACIONES Y SALTOS DE LINEA
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