/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package promptZal;

/**
 *
 * @author jesua
 */
public enum estadoAFD {
    
    // ESTADO INICIAL
    INICIAL,
    
    // DIRECTIVAS
    ARROBA,
    DIRECTIVA,
    
    //CADENAS
    CADENA,
    CADENA_ACEPTADA,
    
    //NUMEROS
    ENTERO,
    DECIMAL,
    PUNTO_DECIMAL,
    
    // IDENTIFICADORES Y PALABRAS RESERVADAS
    IDENTIFICADOR,
    
    // COMENTARIOS
    SLASH,
    COMENTARIO_LINEA,
    COMENTARIO_BLOQUE,
    ASTERISCO_COMENTARIO,
    COMENTARIO_BLOQUE_CERRADO,
    
    // CONECTOR
    CONECTOR,
    GUION,
    
    //OPERADORES
    OPERADOR,
    
    //DELIMITADORES
    DELIMITADOR,
    
    //ERRORES
    ERROR
}
