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
    int custo[][];//matris de custos
    int cn[][];//recebe o canto noroeste
    int otima[][];//Matriz que calcula a otimidade da solução utilizando u e v
    int origem;//Numero de ofertas
    int destino;//Numero de procuras
    int[] oferta;
    int[] procura;
    int verificacao = 0;//Variável de controle

    public Dados() {
        oferta = new int[]{15, 20, 25};//{15, 20, 25};//{6, 8, 10};
        procura = new int[]{8, 10, 12, 15, 15};//{8, 10, 12, 15, 15};//{4, 7, 6, 7};
        origem = oferta.length;
        destino = procura.length;
        custo = new int[][]{{2, 3, 4, 5, 0}, {3, 2, 5, 2, 0}, {4, 1, 2, 3, 0}};//{{1, 2, 3, 4}, {4, 3, 2, 4}, {0, 2, 2, 1}};//{{2, 3, 4, 5, 0}, {3, 2, 5, 2, 0}, {4, 1, 2, 3, 0}};
        v = new int[destino];
        u = new int[origem];
        cn = new int[origem][destino];
        otima = new int[origem][destino];
        verificacao = 0;
       // this.variaveisArtificiais();//Resposável por tratar que a oferta seja igual a procura e visse versa.
    }
    /*
    public void variaveisArtificiais() {
        int demanda = 0;
        int procura = 0;
        for (int i = 0; i < oferta.length; i++) {
            demanda += oferta[i];
        }
        for (int i = 0; i < this.procura.length; i++) {
            procura += this.procura[i];
        }
        if (demanda > procura) {
            int aux = this.procura[this.procura.length] = demanda - procura;
            destino++;

            for (int i = 0; i < origem; i++) {
                for (int j = 0; j < destino; j++) {
                    if (j == destino - 1) {
                        custo[i][j] = 0;
                    }
                }
            }
        } else {
            if (demanda < procura) {
                this.oferta[this.oferta.length] = demanda - procura;
                origem++;

                for (int i = 0; i < origem; i++) {
                    for (int j = 0; j < destino; j++) {
                        if (j == origem - 1) {
                            custo[i][j] = 0;
                        }
                    }
                }
            }
        }

    }
*/
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
                    if (i > 0) {
                        if (controle == 1) {
                            u[i] = custo[i][j] - v[j];
                            controle = 0;
                        }
                    }
                    v[j] = custo[i][j] - u[i];
                }
            }
        }
        /*
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
        }*/
    }

    public boolean otimiza() {        
        this.uv();
        this.imprimeuv();
        this.zeraOtima();
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
        if(menor<0){
        System.out.println("min:" + menor + "[" + posx + "," + posy + "]\n");
        Arvore a = new Arvore(cn, origem, destino, posx, posy, 0);
        a.montaArvore();
        a.buscaLargura();
        System.out.println("Sai->"+a.sai.valor);
        cn = a.novocantonoroeste(cn);
        //System.out.println("\n Matriz Ótima:");
        //this.imprimemat(otima);
        System.out.println("\n Nova matriz:");
        this.imprimemat(cn);
        System.out.println("");
        return true;
        }else
        return false;
    }

    public void imprimemat(int[][] a) {
        for (int i = 0; i < origem; i++) {
            for (int j = 0; j < destino; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
    public void zeraOtima(){
        for (int i = 0; i < origem; i++) {
            for (int j = 0; j < destino; j++) {
                 otima[i][j]=0;       
            }
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
