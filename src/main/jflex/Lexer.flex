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
