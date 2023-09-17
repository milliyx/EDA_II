
package com.fi.edaii.grupo_10_proyecto1_equipo8;

/**
 *
 * @author SamChong
 */
public class NodoExpresion {
     /**
     * Representa el contenido que contendrá el nodo.
     */
    Object valor;
    /**
     * Representa al hijo izquierdo del nodo.
     */
    NodoExpresion izq;
    /**
     * Representa al hijo derecho del nodo.
     */
    NodoExpresion der;
    
    /**
     * Método constructor por defecto, asigna un valor nulo a los hijos izquierdo y derecho.
     */
    public NodoExpresion(){
        izq=der=null;
    }
    /**
     * Método constructor que asigna el contenido al nodo, asigna valor nulo a los hijos izquierdo y derecho.
     * @param dato Contenido del nodo.
     */
   
    public NodoExpresion(Object dato){
        valor = dato;
        izq = null;
        der = null;
    }
    
   /**
    * Método encargado de asignar el hijo izquierdo a un nodo.
    * @param izq Nodo a asignar como hijo izquierdo.
    */
    public void setIzq(NodoExpresion izq){
        this.izq = izq;
    }
    /**
     * Método encargado de asignar el hijo derecho a un nodo.
     * @param der Nodo a asignar como hijo derecho.
     */
    public void setDer(NodoExpresion der){
        this.der = der;
    }
}
