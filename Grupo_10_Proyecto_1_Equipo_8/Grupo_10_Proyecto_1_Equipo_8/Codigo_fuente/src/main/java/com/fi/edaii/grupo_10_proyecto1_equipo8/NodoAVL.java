
package com.fi.edaii.grupo_10_proyecto1_equipo8;


/**
 * Implementación de un nodo para árboles AVL.
 * 
 * @author Carlos Emiliano Mendoza Hernández
 */

public class NodoAVL {
    // Atributos
    /**
     * Contiene la clave del nodo.
     */
    private int valor;

    /**
     * Referencia al hijo izquierdo del nodo.
     */
    private NodoAVL izq;

    /**
     * Referencia al hijo derecho del nodo.
     */
    private NodoAVL der;

    /**
     * Contiene la altura del subárbol que se forma tomando a este nodo como raíz.
     */
    private int h;

    // Constructor
    /**
     * Inicializa un nodo con el valor especificado. Su altura inicial es 1 porque
     * cada nuevo nodo se debe insertar como una hoja.
     * 
     * @param valor Valor o clave del nodo.
     */
    public NodoAVL(int valor) {
        this.valor = valor;
        h = 1;
    } // Cierre del constructor

    // Setters y getters
    /**
     * Obtiene el atributo de altura de este nodo.
     * 
     * @return Altura del nodo.
     */
    public int getH() {
        return h;
    } // Cierre del método

    /**
     * Modifica el atributo de altura de este nodo.
     * 
     * @param h Nueva altura del nodo.
     */
    public void setH(int h) {
        this.h = h;
    } // Cierre del método

    /**
     * Obtiene el atributo de valor o clave de este nodo.
     * 
     * @return Valor del nodo.
     */
    public int getValor() {
        return valor;
    } // Cierre del método

    /**
     * Modifica el atributo de valor o clave de este nodo.
     * 
     * @param valor Nuevo valor del nodo.
     */
    public void setValor(int valor) {
        this.valor = valor;
    } // Cierre del método

    /**
     * Obtiene el atributo de hijo izquierdo (referencia) de este nodo.
     * 
     * @return Referencia al hijo izquierdo de este nodo.
     */
    public NodoAVL getIzq() {
        return izq;
    } // Cierre del método

    /**
     * Modifica el atributo de hijo izquierdo (referencia) de este nodo.
     * 
     * @param izq Nueva referencia al hijo izquierdo de este nodo.
     */
    public void setIzq(NodoAVL izq) {
        this.izq = izq;
    } // Cierre del método

    /**
     * Obtiene el atributo de hijo derecho (referencia) de este nodo.
     * 
     * @return Referencia al hijo derecho de este nodo.
     */
    public NodoAVL getDer() {
        return der;
    } // Cierre del método

    /**
     * Modifica el atributo de hijo derecho (referencia) de este nodo.
     * 
     * @param der Nueva referencia al hijo derecho de este nodo.
     */
    public void setDer(NodoAVL der) {
        this.der = der;
    } // Cierre del método
}
