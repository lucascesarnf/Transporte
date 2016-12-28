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
public class Dados {

    int[] u;
    int[] v;
    int custo[][];
    int cn[][];//recebe o canto noroeste
    int otima[][];
    int origem;
    int destino;
    int[] oferta;
    int[] procura;
    int verificacao = 0;

    public Dados() {
        oferta = new int[]{6, 8, 10};
        procura = new int[]{4, 7, 6, 7};
        origem = oferta.length;
        destino = procura.length;
        custo = new int[][]{{1, 2, 3, 4}, {4, 3, 2, 4}, {0, 2, 2, 1}};
        v = new int[destino];
        u = new int[origem];
        cn = new int[origem][destino];
        otima = new int[origem][destino];
        verificacao = 0;
    }

    public void cantonoroeste() {
        for (int i = 0; i < origem; i++) {
            for (int j = 0; j < destino; j++) {
                if (oferta[i] > 0) {
                    cn[i][j] = Math.min(oferta[i], procura[j]);
                    if (oferta[i] > procura[j]) {
                        oferta[i] = oferta[i] - procura[j];
                        procura[j] = 0;
                    } else {
                        procura[j] = procura[j] - oferta[i];
                        oferta[i] = 0;
                    }
                } else {
                    cn[i][j] = 0;
                }
            }
        }
    }

    public void uv() {
        for (int i = 0; i < origem; i++) {
            u[i] = 0;
        }

        for (int i = 0; i < destino; i++) {
            v[i] = 0;
        }
        int controle = 1;
        for (int i = 0; i < origem; i++) {
            controle = 1;
            for (int j = 0; j < destino; j++) {
                if (cn[i][j] != 0) {
                    if (i > 0 && controle == 1) {
                        u[i] = custo[i][j] - v[j];
                        controle = 0;
                    }
                    v[j] = custo[i][j] - u[i];
                }
            }
        }
    }

    public void otimiza() {
        for (int i = 0; i < origem; i++) {
            for (int j = 0; j < destino; j++) {
                if (otima[i][j] == 0) {
                    otima[i][j] = custo[i][j] - u[i] - v[j];
                }
            }
        }
        int menor = 0;
        int posx = 1;
        int posy = 1;
        for (int i = 0; i < origem; i++) {
            for (int j = 0; j < destino; j++) {
                if (otima[i][j] < 0) {
                    if (otima[i][j] < menor) {
                        menor = otima[i][j];
                        posx = i;
                        posy = j;
                    }
                }
            }
        }
        System.out.println("min:" + menor + "[" + posx + "," + posy + "]\n");
        Arvore a = new Arvore(cn, origem, destino, posx, posy, 0);
        a.montaArvore();
        a.buscaLargura();
        System.out.println("Sai->"+a.sai.valor);

    }

    public void imprimemat(int[][] a) {
        for (int i = 0; i < origem; i++) {
            for (int j = 0; j < destino; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public void imprimeuv() {
        System.out.print("U:");
        for (int i = 0; i < origem; i++) {
            System.out.print(" " + u[i]);
        }
        System.out.println("");
        System.out.print("V:");
        for (int i = 0; i < destino; i++) {
            System.out.print(" " + v[i]);
        }
        System.out.println("");
    }

}
