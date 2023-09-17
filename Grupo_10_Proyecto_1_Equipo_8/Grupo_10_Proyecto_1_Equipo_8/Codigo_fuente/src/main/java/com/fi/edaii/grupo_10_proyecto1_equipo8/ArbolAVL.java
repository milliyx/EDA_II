
package com.fi.edaii.grupo_10_proyecto1_equipo8;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
/**
 * Implementación de árbol AVL.
 * 
 * @author Carlos Emiliano Mendoza Hernández
 */
public class ArbolAVL {
    // Atributo
    /**
     * Referencia al nodo raíz del árbol AVL.
     */
    private NodoAVL root;

    // Constrcutores
    /**
     * Inicializa un nuevo árbol AVL vacío.
     */
    public ArbolAVL() {
        root = null;
    } // Cierre del constructor

    /**
     * Inicializa un nuevo árbol AVL únicamente con un nodo (la raíz).
     * 
     * @param val Valor de la raíz del árbol.
     */
    public ArbolAVL(int val) {
        root = new NodoAVL(val);
    } // Cierre del constructor

    // Métodos
    /**
     * Vacía la referencia de la raíz del árbol. Solo se recomienda su uso para
     * crear un nuevo árbol desde cero.
     */
    public void vaciar() {
        root = null;
    } // Cierre del método

    /**
     * Realiza el recorrido BFS del árbol. Imprime cada nodo conforme se visita en
     * el recorrido. Se recomienda este método para la impresión del árbol.
     */
    public void breadthFrist() {
        NodoAVL r = root;
        Queue<NodoAVL> queue = new LinkedList<>();
        if (r != null) {
            queue.add(r);
            while (!queue.isEmpty()) {
                r = (NodoAVL) queue.poll();
                visit(r);
                if (r.getIzq() != null)
                    queue.add(r.getIzq());
                if (r.getDer() != null)
                    queue.add(r.getDer());
            }
        }
    } // Cierre del método

    /**
     * Imprime el valor del nodo visitado. Se utiliza para marcar nodos como
     * visitados en el recorrido BFS.
     * 
     * @param n
     */
    private void visit(NodoAVL n) {
        System.out.println(n.getValor() + " ");
    } // Cierre del método

    /**
     * Realiza la búsqueda de la clave especificada. Imprime un mensaje indicando su
     * la clave buscada está o no está en el árbol.
     * 
     * @param clave Valor que se desea buscar en el árbol
     */
    public void buscar(int clave) {
        if (buscarUtil(root, clave)) {
            System.out.println("El elemento " + clave + " si esta en el arbol AVL.");
        } else {
            System.out.println("El elemento " + clave + " no esta en el arbol AVL");
        }
    } // Cierre del método

    /**
     * Método recursivo para hacer la búsqueda en el árbol. El recorrido de búsqueda
     * debe iniciar desde la raíz.
     * 
     * @param actual Nodo actual donde se realiza la búsqueda.
     * @param clave  Valor que se busca en el árbol.
     * @return true si la clave está en el árbol, false en caso contrario.
     */
    private boolean buscarUtil(NodoAVL actual, int clave) {
        if (actual == null) {
            return false;
        } else if (clave == actual.getValor()) {
            return true;
        } else if (clave < actual.getValor()) {
            return buscarUtil(actual.getIzq(), clave);
        } else {
            return buscarUtil(actual.getDer(), clave);
        }
    } // Cierre del método

    /**
     * Realiza la inserción de un nodo con el valor especificado. Después de la
     * inserción se rectifican las condiciones de equilibrio para mantener el árbol
     * balanceado.
     * 
     * @param valor Valor del nodo que se desea insertar.
     */
    public void insertar(int valor) {
        root = insertarUtil(root, valor);
    } // Cierre del método

    /**
     * Método recursivo para hacer la inserción en el árbol. El recorrido de
     * inserción debe iniciar desde la raíz. Se inserta de manera similar a un árbol
     * binario de búsqueda, agregando la validación de las condiciones de
     * equilibrio.
     * 
     * @param actual Nodo actual donde se busca el lugar del nodo a insertar.
     * @param valor  Valor del nodo a insertar.
     * @return Para el nodo insertado, regresa un nuevo nodo con el valor
     *         especificado. Para los demás nodos del recorrido regresa el mismo
     *         nodo con su altura modificada y rotaciones realizadas (si aplica).
     */
    private NodoAVL insertarUtil(NodoAVL actual, int valor) {
        if (actual == null) {
            return (new NodoAVL(valor));
        }

        if (valor < actual.getValor()) {
            actual.setIzq(insertarUtil(actual.getIzq(), valor));
        } else if (valor > actual.getValor()) {
            actual.setDer(insertarUtil(actual.getDer(), valor));
        } else {
            System.out.println("NO ES POSIBLE AÑADIR EL VALOR PORQUE YA EXISTE");
        }

        actual.setH(1 + mayor(getAltura(actual.getIzq()), getAltura(actual.getDer())));
        int fe = getFE(actual);

        if (fe == -2 && getFE(actual.getDer()) == -1) {
            return rotacionI(actual);
        }

        if (fe == 2 && getFE(actual.getIzq()) == 1) {
            return rotacionD(actual);
        }

        if (fe == 2 && getFE(actual.getIzq()) == -1) {
            actual.setIzq(rotacionI(actual.getIzq()));
            return rotacionD(actual);
        }

        if (fe == -2 && getFE(actual.getDer()) == 1) {
            actual.setDer(rotacionD(actual.getDer()));
            return rotacionI(actual);
        }

        return actual;
    } // Cierre del método

    /**
     * Devuelve el mayor de dos números enteros especificados. Es útil para
     * encontrar la altura mayor de los hijos de un nodo.
     * 
     * @param a Un numero entero.
     * @param b Otro numero entero.
     * @return El mayor de ambos números.
     */
    private int mayor(int a, int b) {
        return a > b ? a : b;
    } // Cierre del método

    /**
     * Obtiene la altura de un nodo especificado. Valida el caso en el que el nodo
     * especificado es nulo.
     * 
     * @param actual Nodo del que se desea conocer su altura.
     * @return Altura del nodo. 0 si el nodo es nulo.
     */
    private int getAltura(NodoAVL actual) {
        if (actual == null) {
            return 0;
        }
        return actual.getH();
    } // Cierre del método

    /**
     * Realiza el cálculo para obtener el factor de equilibrio del nodo
     * especificado. Valida el caso en el que el nodo especificado es nulo.
     * 
     * @param actual Nodo del que se desea obtener el factor de equilibrio.
     * @return Factor de equilibrio del nodo. 0 si el nodo es nulo.
     */
    private int getFE(NodoAVL actual) {
        if (actual == null) {
            return 0;
        }
        return getAltura(actual.getIzq()) - getAltura(actual.getDer());
    } // Cierre del método

    /**
     * Realiza los movimientos de las referencias para obtener una rotación simple a
     * la derecha.
     * 
     * @param actual Nodo al que se desea efectuar la rotación simple a la derecha.
     * @return Nueva referencia del subárbol resultante de hacer la rotación simple
     *         a la derecha.
     */
    private NodoAVL rotacionD(NodoAVL actual) {
        NodoAVL nuevo = actual.getIzq();
        NodoAVL temp = nuevo.getDer();

        nuevo.setDer(actual);
        actual.setIzq(temp);

        actual.setH(mayor(getAltura(actual.getIzq()), getAltura(actual.getDer())) + 1);
        nuevo.setH(mayor(getAltura(nuevo.getIzq()), getAltura(nuevo.getDer())) + 1);

        return nuevo;
    } // Cierre del método

    /**
     * Realiza los movimientos de las referencias para obtener una rotación simple a
     * la izquierda.
     * 
     * @param actual Nodo al que se desea efectuar la rotación simple a la
     *               izquierda.
     * @return Nueva referencia del subárbol resultante de hacer la rotación simple
     *         a la izquierda.
     */
    private NodoAVL rotacionI(NodoAVL actual) {
        NodoAVL nuevo = actual.getDer();
        NodoAVL temp = nuevo.getIzq();

        nuevo.setIzq(actual);
        actual.setDer(temp);

        actual.setH(mayor(getAltura(actual.getIzq()), getAltura(actual.getDer())) + 1);
        nuevo.setH(mayor(getAltura(nuevo.getIzq()), getAltura(nuevo.getDer())) + 1);

        return nuevo;
    } // Cierre del método

    /**
     * Realiza la eliminación del nodo con la clave especificada. Después de la
     * eliminación valida las condiciones de equilibrio para mantener el árbol
     * equilibrado.
     * 
     * @param clave Valor del nodo que se busca eliminar.
     */
    public void eliminar(int clave) {
        root = eliminarUtil(root, clave);
    } // Cierre del método

    /**
     * Método recursivo para hacer la eliminación en el árbol. El recorrido de
     * eliminación debe iniciar desde la raíz. Se elimina de manera similar a un
     * árbol
     * binario de búsqueda, agregando la validación de las condiciones de
     * equilibrio.
     * 
     * @param actual
     * @param clave
     * @return
     */
    private NodoAVL eliminarUtil(NodoAVL actual, int clave) {
        if (actual == null) {
            return actual;
        }

        if (clave < actual.getValor()) {
            actual.setIzq(eliminarUtil(actual.getIzq(), clave));
        } else if (clave > actual.getValor()) {
            actual.setDer(eliminarUtil(actual.getDer(), clave));
        } else {
            if (actual.getIzq() == null || actual.getDer() == null) {
                NodoAVL temp = null;
                if (temp == actual.getIzq()) {
                    temp = actual.getDer();
                } else {
                    temp = actual.getIzq();
                }

                if (temp == null) {
                    actual = null;
                } else {
                    actual = temp;
                }
            } else {
                NodoAVL temp = getPredecesor(actual.getIzq());
                actual.setValor(temp.getValor());
                actual.setIzq(eliminarUtil(actual.getIzq(), temp.getValor()));
            }
        }

        if (actual == null) {
            return actual;
        }

        actual.setH(mayor(getAltura(actual.getIzq()), getAltura(actual.getDer())) + 1);
        int fe = getFE(actual);

        if (fe == 2 && getFE(actual.getIzq()) == 1) {
            return rotacionD(actual);
        }

        if (fe == -2 && getFE(actual.getDer()) == -1) {
            return rotacionI(actual);
        }

        if (fe == 2 && getFE(actual.getIzq()) == -1) {
            actual.setIzq(rotacionI(actual.getIzq()));
            return rotacionD(actual);
        }

        if (fe == -2 && getFE(actual.getDer()) == 1) {
            actual.setDer(rotacionD(actual.getDer()));
            return rotacionI(actual);
        }

        return actual;
    } // Cierre del método

    /**
     * Busca, a partir de un nodo especificado, al nodo más a la derecha de su
     * herencia (hijos).
     * 
     * @param nodo Nodo donde se empieza a buscar el predecesor.
     * @return Predecesor del nodo especificado.
     */
    private NodoAVL getPredecesor(NodoAVL nodo) {
        NodoAVL actual = nodo;

        while (actual.getDer() != null) {
            actual = actual.getDer();
        }

        return actual;
    } // Cierre del método
    
   /* public static ArbolAVL menuAVL(ArbolAVL previousState, int opcion) {
        Scanner stdin = new Scanner(System.in);
        ArbolAVL arbolAVL = previousState;
        boolean existe;
        if (previousState.root == null) {
            existe = false;
        } else {
            existe = true;
        }
    
        do {
          
            System.out.print("Opcion: ");
            opcion = stdin.nextInt();

            switch (opcion) {
                case 1:
                    if (existe) {
                        System.out.println("Ya existe un árbol. Para crear otro vacía el árbol actual.");
                    } else {
                        System.out.print("Ingresa el valor para la raíz: ");
                        int raiz = stdin.nextInt();
                        arbolAVL = new ArbolAVL(raiz);
                        System.out.println("Se ha creado un nuevo árbol con raíz " + raiz);
                        existe = true;
                    }
                    break;
                
                case 2:
                    if (existe) {
                        System.out.print("Insertar clave: ");
                        int insercion = stdin.nextInt();
                        arbolAVL.insertar(insercion);
                        System.out.println("Se ha ingresado la clave " + insercion);
                    } else {
                        System.out.println("Por favor crea un árbol primero.");
                    }
                    break;
                
                case 3:
                    if (existe) {
                        System.out.print("Buscar clave: ");
                        int busqueda = stdin.nextInt();
                        arbolAVL.buscar(busqueda);
                    } else {
                        System.out.println("Por favor crea un árbol primero.");
                    }
                    
                    break;

                case 4: 
                    if (existe) {
                        System.out.print("Eliminar clave: ");
                        int eliminacion = stdin.nextInt();
                        arbolAVL.eliminar(eliminacion);
                    } else {
                        System.out.println("Por favor crea un árbol primero.");
                    }
                    break;
                
                case 5:
                    if (existe) {
                        System.out.println("\nRecorrido BFS: ");
                        arbolAVL.breadthFrist();
                    } else {
                        System.out.println("Por favor crea un árbol primero.");
                    }
                    break;
                
                case 6:
                    arbolAVL.vaciar();
                    existe = false;
                    System.out.println("El árbol se ha vaciado.");
                    break;
                
                case 7:
                    continue;
            
                default:
                    System.out.println("Opcion invalida");
                    
            }
        } while (opcion != 7);
        // stdin.close();
        return arbolAVL;
    }*/
    
}
