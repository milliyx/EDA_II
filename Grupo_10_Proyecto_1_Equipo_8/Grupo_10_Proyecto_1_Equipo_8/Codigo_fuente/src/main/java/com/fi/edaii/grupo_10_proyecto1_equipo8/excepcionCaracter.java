/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fi.edaii.grupo_10_proyecto1_equipo8;

/**
 *Esta clase esta encargada de controlar la excepción de caracteres en el árbol de expresión.
 * @author SamChong
 */
public class excepcionCaracter extends Exception{
     /**
     * Método constructor de la excepción.
     */
    public excepcionCaracter(){
        
    }
    /**
     * Método constructor de la excepción encargada de mostrar un mensaje.
     * @param mensaje Mensaje de error.
     */
    public excepcionCaracter(String mensaje){
        super(mensaje);
    }
}
