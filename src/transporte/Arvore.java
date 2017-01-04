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
public class Arvore {

    ArrayList<Vertice> vert = new ArrayList();
    ArrayList<Aresta> arestas = new ArrayList();
    ArrayList<Vertice> caminho = new ArrayList();
    Vertice centro;
    Vertice sai;
    int[][] mat;
    int m;
    int n;
    int paix;
    int paiy;
    int paiv;

    public Arvore(int[][] mat, int m, int n, int x, int y, int v) {
        this.mat = mat;
        this.m = m;
        this.n = n;
        this.paix = x;
        this.paiy = y;
        this.paiv = v;
        vert.add(new Vertice(paix, paiy, 1, paiv));
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] != 0) {
                    vert.add(new Vertice(i, j, 1, mat[i][j]));
                }
            }
        }

    }

    public void montaArvore() {
        for (int i = 0; i < vert.size(); i++) {
            for (int j = 0; j < vert.size(); j++) {
                if (vert.get(i).posx == vert.get(j).posx) {
                    if (i != j) {
                        if (i == 0) {
                            vert.get(i).aresta.add(vert.get(j));
                            vert.get(j).aresta.add(vert.get(i));
                        } else {
                            if (vert.get(i).posx != paix) {
                                if (!vert.get(i).aresta.contains(vert.get(j))) {
                                    vert.get(i).aresta.add(vert.get(j));
                                }
                                if (!vert.get(j).aresta.contains(vert.get(i))) {
                                    vert.get(j).aresta.add(vert.get(i));
                                }
                            }
                        }
                    }
                }
                if (vert.get(i).posy == vert.get(j).posy) {
                    if (i != j) {
                        if (i == 0) {
                            vert.get(i).aresta.add(vert.get(j));
                            vert.get(j).aresta.add(vert.get(i));
                        } else {
                            if (vert.get(i).posy != paiy) {
                                if (!vert.get(i).aresta.contains(vert.get(j))) {
                                    vert.get(i).aresta.add(vert.get(j));
                                }
                                if (!vert.get(j).aresta.contains(vert.get(i))) {
                                    vert.get(j).aresta.add(vert.get(i));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void buscaLargura() {
        ArrayList<Vertice> fila = new ArrayList();
        vert.get(0).cor = 1;
        fila.add(vert.get(0));
        this.caminho.add(vert.get(0));
        while (fila.size() > 0) {
            if (fila.get(0).aresta.size() == 1) {
                fila.get(0).ordemT = 0;
                if (caminho.contains(fila.get(0))) {
                    caminho.remove(fila.get(0));
                }
            } else {
                for (int i = 0; i < fila.get(0).aresta.size(); i++) {
                    if (fila.get(0).aresta.get(i).cor == 0) {
                        Aresta a = new Aresta(fila.get(0), fila.get(0).aresta.get(i));
                        if (!arestas.contains(a)) {
                            arestas.add(a);
                        }
                        fila.get(0).aresta.get(i).ordemT = fila.get(0).ordemT + 1;
                        fila.get(0).aresta.get(i).cor = 1;
                        caminho.add(fila.get(0).aresta.get(i));
                        fila.add(fila.get(0).aresta.get(i));
                    } else {
                        if (fila.contains(fila.get(0).aresta.get(i))) {
                            centro = fila.get(0).aresta.get(i);
                            fila.get(0).aresta.get(i).cor = 2;
                            Aresta a = new Aresta(fila.get(0), fila.get(0).aresta.get(i));
                            a.marcada = 1;
                            arestas.add(a);
                            this.procuraCaminho();
                            System.out.println("");
                            fila.clear();
                            break;
                        }
                    }
                }
            }
            if (fila.size() > 0) {
                fila.remove(0);
            }
        }
    }

    public void imprimeordemT() {
        for (int i = 0; i < vert.size(); i++) {
            System.out.print("[" + vert.get(i).valor + "(" + vert.get(i).ordemT + ")]");
        }
    }

    public void procuraCaminho() {
        /*System.out.print("Ordem Topologica:");
        this.imprimeordemT();
        System.out.println("");
        System.out.print("Aresta Para Busca:");
        for (int i = 0; i < arestas.size(); i++) {
            System.out.print("[" + arestas.get(i).a.valor + "-" + arestas.get(i).b.valor + "]");
        }*/
        System.out.println("");
        caminho.clear();
        int cont = 0;
        for (int i = 0; i < vert.size(); i++) {
            cont = 0;
            for (int j = 0; j < arestas.size(); j++) {
                if (arestas.get(j).a == vert.get(i) || arestas.get(j).b == vert.get(i)) {
                    cont++;
                }
            }
            if (cont > 1) {
                if (!caminho.contains(vert.get(i))) {
                    caminho.add(vert.get(i));
                }
            }
        }
        int verifica = 0;
        if ((caminho.size()) % 2 == 1) {
            
            for (int i = 0; i < caminho.size(); i++) {
                verifica = 0;
                if (caminho.get(i).ordemT == (centro.ordemT - 1)) {
                    for (int j = 0; j < caminho.get(i).aresta.size(); j++) {
                        if (caminho.get(i).aresta.get(j) == centro) {                       
                            verifica = 1;
                        }
                    }
                    if (verifica == 0) {    
                             
                       // System.out.println("["+caminho.get(i).valor+"-"+caminho.get(i).ordemT+"]");
                        caminho.remove(caminho.get(i));
                        break;
                    }
                }
            }
        }
        System.out.println("Caminho:");
        this.imprimefila(caminho);
        this.varqsai();
    }

    public void imprimefila(ArrayList<Vertice> fila) {
        for (int i = 0; i < fila.size(); i++) {
            System.out.print("|" + fila.get(i).valor);
        }
        System.out.println("");
    }

    public void calculasinal() {
        for (int i = 0; i < vert.size(); i++) {
            if (vert.get(i).ordemT % 2 == 0) {
                vert.get(i).sinal = 1;
            } else {
                vert.get(i).sinal = -1;
            }
        }
    }

    public void varqsai() {
        this.calculasinal();
        for (int i = 0; i < caminho.size(); i++) {
            if (caminho.get(i).sinal < 0) {
                if (sai == null || caminho.get(i).valor < sai.valor) {
                    sai = caminho.get(i);
                }
            }
        }
    }

    public int[][] novocantonoroeste(int[][] cn) {
        int[][] cn2 = cn;
        for (int i = 0; i < caminho.size(); i++) {
            if (caminho.get(i).sinal < 0) {
                cn2[caminho.get(i).posx][caminho.get(i).posy] -= sai.valor;
            } else {
                cn2[caminho.get(i).posx][caminho.get(i).posy] += sai.valor;
            }
        }
        return cn2;
    }

}
