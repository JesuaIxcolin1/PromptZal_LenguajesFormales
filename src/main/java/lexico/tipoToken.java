/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexico;

/**
 *
 * @author jesua
 */
public enum tipoToken {
    // Directivas
    DIRECTIVA,
            
    //Palabras de Estructura
    AGENTE,
    CONTEXTO,
    VARIABLE,
    EJECUTAR,
    EXPORTAR,
    
    //Comandos
    COMANDO_IA,
    
    //Funcion
    CARGAR,
    
    //Conectores
    CONECTOR,
    
    //Asignacion de Resultado
    FLECHA,
    
    //Operadores
    IGUAL,
    MAS,
    
    //Delimitadores
    LLAVE_A,
    LLAVE_B,
    PAR_A,
    PAR_B,
    COMA,
    
    //Valores
    ID,
    CADENA,
    NUMERO,
    
    //Fin de Archivo
    EOF
            
    
}
