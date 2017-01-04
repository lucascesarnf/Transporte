/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transporte;

/**
 *
 * @author lucas
 */
public class Aresta {

    Vertice a;
    Vertice b;
    int marcada = 0;

    public Aresta(Vertice a, Vertice b) {
        this.a = a;
        this.b = b;
    }
}
