package aed;

import java.lang.reflect.Array;
import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    // Agregar atributos privados del Conjunto
    private Nodo raiz;
    private int cardinal;

    
    private class Nodo {  
        // Agregar atributos privados del Nodo
        private Nodo ramaIzq;
        private Nodo ramaDer;
        private Nodo padre;
        T valor;

        // Crear Constructor del nodo
        public Nodo(){
            valor = null;
            ramaIzq = null;
            ramaDer = null; 
            padre = null;
        }

        public Nodo(Nodo nodo){
            valor = nodo.valor;
            ramaIzq = nodo.ramaIzq;
            ramaDer = nodo.ramaDer;
            padre = nodo.padre;
        }
        
        public String printear(){
            String res = "";
            if(this != null && this.ramaIzq == null && this.ramaDer == null){
                res = this.valor + "";
            }
            if(this != null && this.ramaIzq != null && this.ramaDer == null){
                res = this.ramaIzq.printear() + "," + this.valor;
            }
            if(this != null && this.ramaDer != null && this.ramaIzq == null){
                res = this.valor + "," + this.ramaDer.printear();
            }
            if(this != null && this.ramaDer != null && this.ramaIzq != null){
                res = this.ramaIzq.printear() + "," + this.valor + "," + this.ramaDer.printear();
            }
            return res;
        }

        public Vector<T> listar(){
            Vector<T> elementos = new Vector<T>(cardinal);
            int indice = 0;
            if(this != null && this.ramaIzq == null && this.ramaDer == null){
                elementos.set(indice, valor);
                indice ++;
            }
            if(this != null && this.ramaIzq != null && this.ramaDer == null){
                ramaIzq.listar();
                elementos.set(indice, valor);
                indice++;
            }
            if(this != null && this.ramaDer != null && this.ramaIzq == null){
                elementos.set(indice, valor);
                ramaDer.listar();
                indice ++;
            }
            if(this != null && this.ramaDer != null && this.ramaIzq != null){
                ramaIzq.listar();
                elementos.set(indice, valor);
                ramaDer.listar();
                indice++;
            }
            return elementos;
        }
    }

    public ABB() {
        raiz = new Nodo();
        cardinal = 0;
    }

    public int cardinal() {
        return this.cardinal;
    }

    public T minimo(){
        return minimoRecursivo(raiz);
    }

    public T minimoRecursivo(Nodo nodo){
        if (nodo.ramaIzq == null){
            return nodo.valor;
        }
        return minimoRecursivo(nodo.ramaIzq);
    }

    public T maximo(){
        T valorActual = raiz.valor;
        ABB arbolito = new ABB();
        T res;
        if(raiz.ramaDer == null){return valorActual;}else{
            arbolito.raiz=raiz.ramaDer;
            res = (T) arbolito.maximo();
        }
        return res;
    }


    public void insertar(T elem){
        Nodo nodoActual = raiz;
        Nodo nodoPadre = null;
        if(raiz.valor == null){
            this.raiz.valor = elem;
            cardinal = cardinal + 1;
            
        }else{if(this.pertenece(elem)){}else{
                this.cardinal = cardinal + 1;
                //vemos para que lado tiene que ir el elem
                while(nodoActual != null){
                if(elem.compareTo(nodoActual.valor)== 0){break;}
                if(elem.compareTo(nodoActual.valor) > 0){
                    //va para la derecha
                    nodoPadre = nodoActual;
                    nodoActual = nodoActual.ramaDer;
                }else{   
                if(elem.compareTo(nodoActual.valor) < 0){
                    //va para la izquierda
                    nodoPadre = nodoActual;
                    nodoActual = nodoActual.ramaIzq;
                }}
                }

                nodoActual = new Nodo();
                nodoActual.valor = elem;
                nodoActual.padre = nodoPadre;
                if(nodoActual.padre.valor.compareTo(nodoActual.valor)> 0){
                    nodoActual.padre.ramaIzq = nodoActual;
                }
                if(nodoActual.padre.valor.compareTo(nodoActual.valor)< 0){
                    nodoActual.padre.ramaDer = nodoActual;
                }}}}

    public boolean pertenece(T elem){
        if (raiz ==null){return false;}
        if(raiz.valor==null){return false;}
        Nodo nodoActual = raiz;
        while (nodoActual != null){
        if(elem.compareTo(nodoActual.valor)== 0){
            return true;
        }else{
            if(elem.compareTo(nodoActual.valor) > 0){
                nodoActual = nodoActual.ramaDer;
            }else{
                nodoActual = nodoActual.ramaIzq;
            }
        } 
        }   
        return false;
    }


    private Nodo buscarElemento(T elem){
       Nodo nodoActual = raiz;
        while(nodoActual.valor.compareTo(elem) != 0){
            if(elem.compareTo(nodoActual.valor) > 0){
              nodoActual = nodoActual.ramaDer;
            }
            if(elem.compareTo(nodoActual.valor) < 0){
                nodoActual = nodoActual.ramaIzq;
            }
        }
        return nodoActual;
    }

    public void eliminar(T elem){
        
        Nodo nodoElemento = this.buscarElemento(elem);
        //No tiene hijos
        if(nodoElemento.ramaIzq == null && nodoElemento.ramaDer == null){
            if(this.raiz.valor.compareTo(elem)==0){
                raiz = null;

            }else{
                if(elem.compareTo(nodoElemento.padre.valor)< 0){
                    nodoElemento.padre.ramaIzq = null;
                    nodoElemento.padre = null;
                }else{
                    nodoElemento.padre.ramaDer = null;
                    nodoElemento.padre = null;
                }

        }}
        //Tiene un hijo
                if(nodoElemento.ramaIzq == null ^ nodoElemento.ramaDer == null){
                    if(nodoElemento.valor.compareTo(raiz.valor)== 0){
                        if(nodoElemento.ramaIzq != null){
                            raiz = nodoElemento.ramaIzq;
                            raiz.padre = null;
                        }else{
                            raiz = nodoElemento.ramaDer;
                            raiz.padre = null;
                        }
                    }else{
                //El hijo es de la rama izquierda
                if(nodoElemento.ramaIzq != null){
                    //el padre lo tiene en la rama izquierda
                    if(nodoElemento.padre.ramaIzq == nodoElemento){
                        //asigno el puntero del "abuelo" al "nieto"
                        nodoElemento.padre.ramaIzq = nodoElemento.ramaIzq;
                        //asigno el puntero del "nieto" al "abuelo"
                        nodoElemento.ramaIzq.padre = nodoElemento.padre;
                    }
                    //el padre lo tiene en la rama derecha
                    if(nodoElemento.padre.ramaDer == nodoElemento){
                        //asigno el puntero del "abuelo" al "nieto"
                        nodoElemento.padre.ramaDer = nodoElemento.ramaIzq;
                        //asigno el puntero del "nieto" al "abuelo"
                        nodoElemento.ramaIzq.padre = nodoElemento.padre;
                    }
                }
                //El hijo es de la rama derecha
                if(nodoElemento.ramaDer != null){
                    //el padre lo tiene en la rama izquierda
                    if(nodoElemento.padre.ramaIzq == nodoElemento){
                        //asigno el puntero del "abuelo" al "nieto"
                        nodoElemento.padre.ramaIzq = nodoElemento.ramaDer;
                        //asigno el puntero del "nieto" al "abuelo"
                        nodoElemento.ramaDer.padre = nodoElemento.padre;
                    }
                    //el padre lo tiene en la rama derecha
                    if(nodoElemento.padre.ramaDer == nodoElemento){
                        //asigno el puntero del "abuelo" al "nieto"
                        nodoElemento.padre.ramaDer = nodoElemento.ramaDer;
                        //asigno el puntero del "nieto" al "abuelo"
                        nodoElemento.ramaDer.padre = nodoElemento.padre;
                    }
                }
            }
        }
        //Tiene dos hijos
        if(nodoElemento.ramaIzq != null && nodoElemento.ramaDer != null){
            Nodo predecesor = nodoElemento.ramaIzq;
            while (predecesor.ramaDer != null){
                predecesor = predecesor.ramaDer;
            }
            T valorPred = predecesor.valor;
            this.eliminar(predecesor.valor);
            nodoElemento.valor = valorPred;
            cardinal +=1;   
        }
        cardinal -=1;  
    }

    public String toString(){
        return "{" + this.raiz.printear() + "}";
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;

        public boolean haySiguiente() {            
            throw new UnsupportedOperationException("No implementada aun");
        }
    
        public T siguiente() {
            // Vector<T> vector = new Vector<T>(); 
            // for (int i = 0; i < cardinal; i++) {
            //     if (vector.get(i).compareTo(_actual.valor) == 0){
            //         return vector.get(i+1);
            //     }else{}                
            // }
            throw new UnsupportedOperationException("No implementada aun");
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}