/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fi.edaii.grupo_10_proyecto1_equipo8;
import java.util.List;
import java.util.LinkedList;
/**
 *Esta clase define los métodos necesarios para la creación de un MAX Heap a partir de los 
 * elementos o claves ingresados por el usuario. 
 * @author Michelle Barrios
 * @version 28/09/2022
 */
public class Heap {
     Nodo root;
    List<Nodo> nodos = new LinkedList<>();

    /**
     * Constructor de la clase Heap donde se inicializa el valor de la raíz con null
     */
    public Heap() {
        root = null;
    }

    /**
     * Constructor de la clase Heap donde define a la raíz como un nuevo nodo el
     * cual tendrá como
     * paramatro un valor igresado por el usuario (Modificable)
     */
    public Heap(int val) {
        root = new Nodo(val);
    }

    /**
     * Constructor que define a la raíz del heap
     * 
     * @param root es la variable a la que se le asigna a la raíz del heap
     */
    public Heap(Nodo root) {
        this.root = root;
    }

    /**
     * Método que define el valor del nodo que el usuario estará ingresando
     * 
     * @param value es la variable a la que se le asignará un nuevo valor cada que
     *              el usuario desee ingresar un nuevo elemento
     */
    public void setRoot(int value) {
        root = new Nodo(value);
    }

    /**
     * Método que retorna el valor de la raíz del heap
     * 
     * @return root.valor nos devuelve el valor de la raíz.
     */
    public int getRoot() {
        return root.valor;
    }

    /**
     * Método que define a los nodos derecho e izquierdo, así como la raíz del heap,
     * de acuerdo a
     * las características de un MAX Heap, donde en una nueva lista asignamos a los
     * hijos izquierdos y derechos
     * de acuerdo con las operaciones necesarias para definir tanto hijo izquierdo
     * como derecho
     * en su incersión de claves.
     * 
     * @param elements
     */
    public void defNodo(List<Integer> elements) {
        Nodo aux, left, right;
        int a, b;

        for (int i = 0; i < elements.size(); i++) {
            aux = new Nodo(elements.get(i));
            nodos.add(aux);
        }

        for (int i = 0; i < elements.size(); i++) {
            aux = nodos.get(i);
            a = (2 * i) + 1;
            if (a < nodos.size()) {
                left = nodos.get(a);
            } else {
                left = null;
            }
            b = (2 * i) + 2;
            if (b < nodos.size()) {
                right = nodos.get(b);
            } else {
                right = null;
            }
            aux.setIzq(left);
            aux.setDer(right);
            root = nodos.get(0);

        }
    }

    /**
     * Método que realiza la incersión de los elementos de acuerdo a las reglas de
     * ordenamiento
     * de un MAX Heap, donde se inserta el nuevo elemento en la primera posicion
     * disponible lo más
     * abajo y a la izquierda posible, inmediantamente se verifica si el valor
     * ingresado es mayor que
     * la raíz, en tal caso realiza el intercambio de los elementos, todo esto
     * proceso repitiéndose de
     * manera recursiva
     * 
     * @param val define a los valores ingresados por el usuario para la
     *            construcción del heap
     */
    public void Insert(List<Integer> elements, int val) {
        int size = elements.size();
        if (size == 0) {
            elements.add(val);
        } else {
            elements.add(val);
            for (int i = size / 2 - 1; i >= 0; i--) {
                Heapify(elements, i);
            }
        }
    }

    /**
     * El metodo Delete realiza la eliminación de una clave, ya sea raíz, hijo
     * derecho o hijo izquierdo
     * de acuerdo a las reglas de eliminación de un heap, donde se reemplaza la raíz
     * con el elemento
     * que ocupa la ultima posición del heap lo más abajo y a la derecha posible,
     * verificando si el valor de
     * la nueva raíz es menor que el valor más grande entre sus hijos, en tal caso,
     * se realiza unintercambio, repitiendo este proceso recursiva.
     * 
     * @param elements Define a los elementos del Heap
     * @param val Define al elemento que se eliminará
     */
    public void Delete(List<Integer> elements, int val) {
        int size = elements.size();
        int i;
        for (i = 0; i < size; i++) {
            if (val == elements.get(i))
                break;
        }

        int temp = elements.get(i);
        elements.set(i, elements.get(size - 1));
        elements.set(size - 1, temp);

        elements.remove(size - 1);
        buildHeap(elements);
    }

    /**
     * El método recorre la lista en la que los elementos se almacenan conforme se
     * van ingresando
     * mientras que los va ordenando de forma en que la raíz de cada heap sea mayor
     * a sus hijos,
     * esto siguiendo la lógica de un MAX Heap, en donde para cada clave que
     * tomemos, el valor del
     * padre es mayor.
     * 
     * @param elements define los elementos de la lista.
     */
    public void buildHeap(List<Integer> elements) {
        for (int i = elements.size() / 2 - 1; i >= 0; i--) {
            Heapify(elements, i);
        }

    }

    /**
     * El método Heapify
     * 
     * @param elements
     * @param i
     */
    public static void Heapify(List<Integer> elements, int i) {
        int size = elements.size();
        int left = (2 * i) + 1;
        int right = (2 * i) + 2;
        int largest = i;

        if (left < size && elements.get(left) > elements.get(largest))
            largest = left;
        if (right < size && elements.get(right) > elements.get(largest))
            largest = right;

        if (largest != i) {
            int temp = elements.get(largest);
            elements.set(largest, elements.get(i));
            elements.set(i, temp);
            Heapify(elements, largest);
        }
    }

    /**
     * Método que se encarga de mostrarnos las diversas instrucciones en las que
     * el usuario podrá navegar dentro del submenú para la creacion de su MAXHEAP.
     */
    public void Print_menu() {
        System.out.println();
        System.out.println("*** H E A P ***");
        System.out.println("1) agregar elemento");
        System.out.println("2) borrar elemento");
        System.out.println("3) mostrar heap");
        System.out.println("4) salir");
        System.out.print("Ingrese la opcion deseada: ");
    }

    /**
     * @param elements define a los elementos que se guardaron en la lista
     *                 previamente
     * @param size     define al tamaño de la lista
     * 
     *                 Método que se encarga de imprimir los elementos guardados en
     *                 la lista en donde ya se
     *                 realizó previamente el ordenamiento de los elementos de
     *                 acuerdo al algoritmo.
     */
    public void printHeap(List<Integer> elements, int size) {
        for (Integer i : elements) {
            System.out.print(i + "\n");
        }
        System.out.println();
    }
}
