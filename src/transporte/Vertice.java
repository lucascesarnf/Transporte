/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transporte;

import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public class Vertice {

    int posx;
    int posy;
    int sinal;
    int valor;
    int componente;
    int cor;    
    int ordemT =0;
    
    ArrayList<Vertice> aresta = new ArrayList();

    public Vertice(int posx, int posy, int sinal, int valor) {

        this.posx = posx;
        this.posy = posy;
        this.sinal = sinal;
        this.valor = valor;
        this.componente = valor;
        this.cor = 0;
    }

}
