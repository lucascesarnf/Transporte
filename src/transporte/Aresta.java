/*
 Esta classe é utilizada pela classe arvore para representar uma aresta
 */
package transporte;

/**
 *
 * @author lucas
 */
public class Aresta {

    Vertice a;
    Vertice b;
    int marcada = 0;//Variável utilizada para marcar vertice como visitado

    public Aresta(Vertice a, Vertice b) {//Método construtor
        this.a = a;
        this.b = b;
    }
}
