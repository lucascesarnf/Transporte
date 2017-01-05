/*
 Esta classe é utilizada pela classe arvore para representar um vértice
 */
package transporte;

import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public class Vertice {

    int posx;//Posição X do vertice na matriz
    int posy;//Posição Y do vertice na matriz
    int sinal;//Sinal do vertice
    int valor;//Valor do vertice referente a matris
    int componente;//Componete conexa do vertice
    int cor;  //Coloração do vertice(Utilizado para busta em largura)  
    int ordemT =0;//Ordem topológica do vertice(Utilizado para encontrar o menor caminho)
    
    ArrayList<Vertice> aresta = new ArrayList();/*Todo vertice tem um array que contem seus filhos
    ou seja acessamos os filhos de cada vertice atravez do próprio objeto vertice*/

    public Vertice(int posx, int posy, int sinal, int valor) {//Método construtor

        this.posx = posx;
        this.posy = posy;
        this.sinal = sinal;
        this.valor = valor;
        this.componente = valor;
        this.cor = 0;
    }

}
