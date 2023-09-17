/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fi.edaii.grupo_10_proyecto1_equipo8;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Esta clase define los métodos para la creación de un árbol de expresión.
 * @author SamChong
 */
public class ArbolExpresion {
    /**
     * Representa a la raíz del árbol.
     */
     NodoExpresion root;
    
    /**
     * Método constructor por defecto
     */
    public ArbolExpresion(){
       
    }
    
    /**
     * Método constructor que convierte una expresion aritmética en árbol binario.
     * 
     * @param expresion Expresion aritmética introducida.
     * @throws excepcionCaracter Caso en que se introduzca una expresión erronea.
     */
    public ArbolExpresion(String expresion) throws excepcionCaracter{
        root = crearArbol(expresion);
    }
    
    
    /**
     * Método encargado de marcar un nodo como visitado.
     * @param n Nodo que será visitado.
     */
     static void visitar(NodoExpresion n){
        System.out.println(n.valor + " ");
    }
    /**
     * Método encargado de realizar el recorrido BFS al árbol. Recorrido por capas. No recibe ni retorna nada.
     */
      public void BFS(){
         NodoExpresion r = root;
	Queue<NodoExpresion> queue = new LinkedList();
	if(r!=null){
            queue.add(r);
            while(!queue.isEmpty()){
                r = (NodoExpresion)queue.poll();
		visitar(r);
		if(r.izq!=null)
                    queue.add(r.izq);
		if(r.der!=null)
		queue.add(r.der);
            }
	}
    }
      /**
       * El método se encarga de dar prioridad a los operadores que contenga la expresion introducida.
       * @param operador Dato de tipo caracter, equivale al operador.
       * @return Retorna la prioridad que tendrá el operador a comparación de los otros.
       */
        private static int Prioridad(char operador){
        
        int prioridad;
        
        switch(operador){
            case '*':
                prioridad = 2;
                break;
            case '/':
                prioridad = 2;
                break;
            case '-':
                prioridad = 1;
                break;
            case '+':
                prioridad = 1;
                break;
            default: 
                prioridad = 0;
        }
        return prioridad;
    }
        /**
         * Comprueba si se trata de algún operador, conteniendo a los paréntesis de la operación.
         * @param caracter Caracter de la expresión a comprobar.
         * @return  Retorna true si se trata de algún operador; de lo contrario false.
         */
      private static boolean Operador(char caracter){
        
        boolean operador = false;
        
        if(caracter == '(' || caracter == ')' || caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/'){
            operador = true;
        }
        return operador;
    }
      /**
       * Dada una expresión aritmética se encargará de construir un árbol binario con cada caracter, respetando la jerarquía de prioridad por cada operador.
       * @param expresion   Expresion Aritmética.
       * @return    Retorna al árbol creado a partir de la raíz.
       * @throws excepcionCaracter Caso en que algún caracter no se encuentre dentro del rango definido.
       */
    
     static NodoExpresion crearArbol(String expresion)throws excepcionCaracter{
         
         Stack<NodoExpresion> pilaOperadores = new Stack<>();
         Stack<NodoExpresion> pilaExpresiones = new Stack<>();
         
         NodoExpresion elemento;
         NodoExpresion operando1, operando2;
         NodoExpresion operador;
         String caracteres = "0123456789()+*-/";
         char caracter;
         
     
         for(int i = 0; i < expresion.length(); i++){
             caracter = expresion.charAt(i);
             elemento = new NodoExpresion(caracter);
            if(caracteres.contains(String.valueOf(caracter))){
                 if(!Operador(caracter)){
                 pilaExpresiones.push(elemento);
             } else if(Operador(caracter)){
                 
                 if(caracter == '('){
                     pilaOperadores.push(elemento);
                     
                 }else if(caracter == ')'){
                      while(!pilaOperadores.isEmpty() && !pilaOperadores.peek().valor.equals('(')){
                             operando2 = pilaExpresiones.pop();
                             operando1 = pilaExpresiones.pop();
                             operador = pilaOperadores.pop();
                             operador.setIzq(operando1);
                             operador.setDer(operando2);
                             pilaExpresiones.push(operador);
                         }
                         pilaOperadores.pop();
                 }else{
                      while(!pilaOperadores.isEmpty() && Prioridad(caracter) <= Prioridad(pilaOperadores.peek().valor.toString().charAt(0))){
                             operando2 = pilaExpresiones.pop();
                             operando1 = pilaExpresiones.pop();
                             operador = pilaOperadores.pop();
                              operador.setIzq(operando1);
                             operador.setDer(operando2);
                            
                             pilaExpresiones.push(operador);
                         }
                         pilaOperadores.push(elemento);
                     }
                 }
             }else{
                 throw new excepcionCaracter("Caracter invalido");
             }
             
                 
             
         }
        
         operador = pilaExpresiones.pop();
         return operador;
         
     }
    
     /**
      * Este método se encarga de aplicarla resolución de la expresión introducida a partir de la notación postfija de la misma.
      * @param posfija Notación postfija.
      */
     public static void resolverExpresion(String posfija){
         System.out.println(ArbolExpresion.resolver(posfija));
     }
  
     /**
      * El método se encarga del proceso de resolución de la expresión aritmética.
      * @param cadena   Notación postfija de la expresión.
      * @return     Retorna el resultado de tipo float.
      */
     static float resolver(String cadena){
        
        Stack<Float> pila = new Stack<>();
        float resultado;
        for(int i = 0; i < cadena.length(); i++){
            char caracter = cadena.charAt(i);
            if(Prioridad(caracter) != 0){
                float a = pila.pop();
                float b = pila.pop();
                float operacion = operar(caracter, b, a);
                pila.push(operacion);
            }else{
                pila.push((float) (caracter-48)); //resta valor en ascii
            }
        }
        resultado = pila.pop();
        return resultado;
    }
    
    
    /**
     * El método se encarga de comprobar qué operador se va a aplicar.
     * @param operador  Caracter que corresponde al operador que se va a aplicar.
     * @param operando1 Primer valor que se va a operar.
     * @param operando2 Segundo valor que se va a operar.
     * @return  Retornará 0 en caso que no se aplique algún operador.
     */
    private static float operar(char operador, float operando1, float operando2){
        
        switch(operador){
            case '+':
                return operando1+operando2;
            case '-':
                return operando1-operando2;
            case '*':
                return operando1*operando2;
            case'/':
                return operando1/operando2;
              default:
                    return 0;
                
        }
    }
    
    /**
     * Realiza el recorrido postOrden del árbol.
     * @param raiz  Raíz del árbol. Por ella comenzará el recorrido.
     */
    public static void postOrden(NodoExpresion raiz){
        if(raiz.izq != null){
            postOrden(raiz.izq);
        }
        if(raiz.der != null){
            postOrden(raiz.der);
        }
        visitar(raiz);
    }
    

   /**
    * El método se encarga de retornar en forma de cadena el recorrido postOrden del árbol, para posteriormente ser usado para la resolución de la expresión.
    * @param raiz   Raíz del árbol en la que comenzará el recorrido.
    * @param c  Cadenaque se irá sumando para obtener el recorrido en forma de cadena.
    * @return   Retorna en forma de String al recorrido postOrden.
    */
    public static String posOrdenString(NodoExpresion raiz,String c){
        
        String preorden = "";
        if(raiz != null){
            preorden = c + posOrdenString(raiz.izq, c) + posOrdenString(raiz.der, c) + raiz.valor.toString();
        }
       return preorden;
    }
    
}
