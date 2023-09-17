/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fi.edaii.grupo_10_proyecto1_equipo8;

/**
 * Clase donde se define los getters y setters de Nodo, así como los valores de
 * retorno necesarios para la ejecución éxitosa del programa
 * 
 * @author Michelle Barrios
 * @version 28/09/2022
 */
public class Nodo {
    /**
     * Variable que le asiganará un valor al nuevo nodo
     */
    int valor;
    /**
     * Define el valor inicial de la variable izq, haciendo referencia a un Nodo
     * Izquierdo inicializandose en NULL
     */
    Nodo izq = null;
    /**
     * Define el valor inicial de la variable der, haciendo referencia a un Nodo
     * Derechp inicializandose en NULL
     */
    Nodo der = null;

    /**
     * Constructor para la clase Nodo
     */
    public Nodo(Nodo nodo) {
        izq = der = null;
    }

    /**
     * Método que inicializa las instancias derecho o izquierdo
     * 
     * @param data hace referencia al dato que se le será asignado
     */
    public Nodo(int data) {
        this(data, null, null);
    }

    /**
     * @param data define el valor asignado
     * @param lt   define el valor de la variable izquierda
     * @param rt   define el valor de la variable derecha
     */
    public Nodo(int data, Nodo lt, Nodo rt) {
        valor = data;
        izq = lt;
        der = rt;
    }

    /**
     * @param n    define el número de nodo que se está analizando
     * @param data define el valor del nodo
     * @return el numero de nodo
     */
    public Nodo valor(Nodo n, int data) {
        valor = data;
        return n;
    }

    /**
     * Método que define la instancia izq para los nodos izquierdos
     * 
     * @param izq hace referencia al nodo izquierdo
     */
    public void setIzq(Nodo izq) {
        this.izq = izq;
    }

    /**
     * Método que define la instancia izq para los nodos derechos
     * 
     * @param der hace referencia al nodo derecho
     */
    public void setDer(Nodo der) {
        this.der = der;
    }
}
