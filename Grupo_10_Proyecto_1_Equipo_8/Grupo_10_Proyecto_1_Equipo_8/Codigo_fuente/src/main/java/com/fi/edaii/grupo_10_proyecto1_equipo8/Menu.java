
package com.fi.edaii.grupo_10_proyecto1_equipo8;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase ecargada de manejar los menús del programa.
 * @author SamChong 
 */
public class Menu {
    /**
     * Este método controla el menú principal del proyecto.
     */
    public static void menuPrincipal(){
        Scanner s = new Scanner(System.in);
            Menu.menu();
           
            int opP = s.nextInt();
            
            
                                     
            while(opP != 4){
                switch(opP){
                    case 1:
                       Menu.menuArbolAVL();
                        break;
                    case 2:
                        System.out.println();
                        Menu.menuArbolHeap();
                        break;
                    case 3:
                        Menu.menuAE();
                        System.out.print("Ingrese la opcion deseada: ");
                        int opAE = s.nextInt();
                        System.out.println();
                        Menu.menuArbolExpresion(opAE);
                        break;
                    default:
                        System.out.println("Opcion invalida, volver a ingresar");
                        
                }
                Menu.menu();
                
                opP = s.nextInt();
            }
    }
    
    /**
     * El método controla las acciones a realizar con un árbol AVL.
     */
  static void menuArbolAVL(){
      Scanner s = new Scanner(System.in);
      ArbolAVL arbol = new ArbolAVL();
      int opc;
       Menu.menuAVL();
      System.out.print("Ingrese la opcion deseada: ");
      opc = s.nextInt();
      
      while(opc != 7){
          switch(opc){
              case 1:
                   System.out.print("Ingresa el valor para la raíz: ");
                        int raiz = s.nextInt();
                        arbol = new ArbolAVL(raiz);
                        System.out.println("Se ha creado un arbol con raiz " + raiz);
                        break;
              case 2:
                   System.out.print("Insertar clave: ");
                        int insercion = s.nextInt();
                        arbol.insertar(insercion);
                        System.out.println("Se ha insertado la clave " + insercion);
                        break;
              case 3:
                    System.out.print("Buscar clave: ");
                        int busqueda = s.nextInt();
                        arbol.buscar(busqueda);
                  break;
              case 4:
                   System.out.print("Eliminar clave: ");
                        int eliminacion = s.nextInt();
                        arbol.eliminar(eliminacion);
                        System.out.println(eliminacion + " ha sido eliminado del arbol");
                        break;
              case 5:
                   System.out.println("\nRecorrido BFS: ");
                        arbol.breadthFrist();
                        break;
              case 6:
                   arbol.vaciar();
                    
                    System.out.println("El árbol se ha vaciado.");
                    break;
              default:
                  System.out.println("Opcion invalida, volver a ingresar: ");
                 
          }
           Menu.menuAVL();
           System.out.print("Ingrese la opcion deseada: ");
           opc = s.nextInt();
      }
  }
  /**
   * El método controla las acciones a realizar en un Heap.
   */
    static void menuArbolHeap(){
        
        List<Integer> claves = new LinkedList<>();
        int size = claves.size();
        Scanner entrada = new Scanner(System.in);
        int valor, opcion;

        Heap h = new Heap();

            
            h.Print_menu();
            opcion = entrada.nextInt();
            
            while(opcion != 4){
                switch (opcion) {
                case 1:
                    System.out.println("Escriba un valor: ");
                    valor = entrada.nextInt();
                    System.out.println("\n");
                    h.Insert(claves, valor);
                    System.out.println(valor + " ha sido insertado");
                   
                    break;
                case 2:
                    System.out.println("Escriba el valor a eliminar: ");
                    valor = entrada.nextInt();
                    System.out.println("\n");
                    h.Delete(claves, valor);
                    System.out.println(valor + " ha sido eliminado");
                    System.out.println("El estado actual del Heap es: ");
                    System.out.println();
                    h.printHeap(claves, size);
                    break;
                case 3:
                    System.out.println();
                    System.out.println("La forma del Heap es: ");
                    System.out.println();
                    h.printHeap(claves, size);
                    break;
                default:
                    System.out.println("opcion invalida, volver a ingresar");
                    

                    }
                h.Print_menu();
                System.out.print("Ingrese la opcion deseada: ");
                opcion = entrada.nextInt();
            }
            
        

    }
    
    /**
     * El método controla las acciones a realizar en un árbol de expresión
     * @param opc Opción que se desea realizar.
     */
     static void menuArbolExpresion(int opc){
        Scanner scan = new Scanner(System.in);

        ArbolExpresion arbol = new ArbolExpresion();
        while(opc != 4){
            switch(opc){
                case 1:
                    System.out.println("Antes de ingresar la expresion, considerar lo siguiente: ");
                    System.out.println("""
                            \n-Introducir una expresion aritmetica contenida en parentesis. Ejemplo:
                                       ((2+3)/(4+2*3)*(2-3))
                                       
                             -Unicamente se puede ingresar una expresion
                                       
                             -Si se ingresa un caracter erroneo, se tendra que volver a ingresar a este menu y reingresarla.
                                       """);
                            
                   System.out.print("Ingrese la expresion a evaluar: ");
                  
                    try{      
                        String expresion = scan.nextLine();
                        ArbolExpresion arbol2 = new ArbolExpresion(expresion);
                        arbol = arbol2;
                    }catch(excepcionCaracter e){
                         System.out.println("Ha ingresado un caracter invalido, ingrese una expresion aritmetica correcta");
                        System.out.println(e);
                        return;
                    }
                    System.out.println();
                    System.out.println("Se ha creado un arbol con la expresion");
                    break;
                case 2:
                    System.out.println("El recorrido BFS del arbol es: ");
                    arbol.BFS();
                    System.out.println("Su recorrido postOrden es: ");
                    ArbolExpresion.postOrden(arbol.root);
                    break;
                case 3:
                   
                    System.out.println("El resultado de la expresion es: ");
                      String posfija = ArbolExpresion.posOrdenString(arbol.root, "");
                       System.out.println();
                       
                       ArbolExpresion.resolverExpresion(posfija);
                       break;
                       
                default:
                    System.out.println("Opcion invalida");
            }
            Menu.menuAE();
            System.out.print("ingrese la opcion deseada: ");
            opc = scan.nextInt();
        }
    }
    
     /**
      * Método encargado de mostrar el menú prinicpal.
      */
    static void menu(){
        System.out.println();
        System.out.println("1) Arbol AVL");
        System.out.println("2) Heap");
        System.out.println("3) Arbol de expresion aritmetica");
        System.out.println("4) Terminar programa");
        System.out.print("Bienvenido :), ingrese la opcion deseada: ");
    }
    /**
     * Método encargado de mostrar el sub menú del Árbol de expresión.
     */
    static void menuAE(){
        System.out.println();
        System.out.println("***ARBOL DE EXPRESION ARITMETICA***");
        System.out.println("1) Ingresar expresion");
        System.out.println("2) Mostrar arbol");
        System.out.println("3) Resolver expresion");
        System.out.println("4) Regresar");
    }
    
  
    /**
     * Método encargado de mostrar el sub menú del Árbol AVL.
     */
    static void menuAVL(){
     System.out.println("""
                    \n***** Arbol AVL *****
                    Selecciona una opción:
                    1. Crear arbol AVL
                    2. Agregar clave
                    3. Buscar un valor
                    4. Eliminar clave
                    5. Mostrar árbol (BFS)
                    6. Vaciar árbol AVL
                    7. Salir""");
    }
}
