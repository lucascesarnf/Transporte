/*
   Classe que recebe e trata os dados para encontrar a matríz resultante ótima
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
    int otima[][];//Matriz que contem os dados a fim de verificação da ótimidade do problema
    int origem;//Numero de ofertas
    int destino;//Numero de procuras
    int[] oferta;
    int[] procura;
    int custoT = 0;
    int verificacao = 0;//Variável de controle

    public Dados() {
        oferta = new int[]{15, 20, 25};//{15, 20, 25};//{6, 8, 10};
        procura = new int[]{8, 10, 12, 15};//{8, 10, 12, 15, 15};//{4, 7, 6, 7};
        origem = oferta.length;
        destino = procura.length;
        custo = new int[][]{{2, 3, 4, 5}, {3, 2, 5, 2}, {4, 1, 2, 3}};//{{1, 2, 3, 4}, {4, 3, 2, 4}, {0, 2, 2, 1}};//{{2, 3, 4, 5, 0}, {3, 2, 5, 2, 0}, {4, 1, 2, 3, 0}};
        v = new int[destino];
        u = new int[origem];
        cn = new int[origem][destino];
        otima = new int[origem][destino];
        verificacao = 0;
        this.variaveisArtificiais();//Resposável por tratar que a oferta seja igual a procura e visse versa.
    }

    public void variaveisArtificiais() {//Método que insere variáveis artificiais caso necessário
        int demanda = 0;//valor total da demanda
        int procura = 0;//valor total da procura
        for (int i = 0; i < oferta.length; i++) {//Calculo do valor total da demanda
            demanda += oferta[i];
        }
        for (int i = 0; i < this.procura.length; i++) {//Calculo do valor total da procura
            procura += this.procura[i];
        }
        if (demanda > procura) {
            /*Caso a demanda seja maior que aprocura inserimos uma procura artificial de custo 0
            e com o valor necessário para igualar a demanda!
            */
            System.out.println("A demanda é maior que a procura!");
            int[] aux;
            aux = new int[this.procura.length + 1];
            for (int i = 0; i < aux.length; i++) {
                if (i >= this.procura.length) {
                    aux[i] = demanda - procura;//Insere a nova procura que sera a diferença entre as duas
                    // System.out.print("|"+aux[i]);
                } else {
                    aux[i] = this.procura[i];
                    // System.out.print("|"+aux[i]);
                }
            }

            this.procura = aux;
            destino += 1;
            int[][] aux2;
            aux2 = new int[origem][destino];
            for (int i = 0; i < origem; i++) {
                for (int j = 0; j < destino; j++) {
                    if (j == destino - 1) {
                        aux2[i][j] = 0;// insere os custos da nova procura como 0
                    } else {
                        aux2[i][j] = custo[i][j];
                    }
                }
            }
            //Seta os novos tamanhos das matrizes e vetores
            custo = aux2;
            v = new int[destino];
            u = new int[origem];
            cn = new int[origem][destino];
            otima = new int[origem][destino];
        } else {

            if (demanda < procura) {
                /*Caso a procura seja maior que a demanda inserimos uma demanda artificial de custo 0
                 e com o valor necessário para igualar a procura!
               */
                System.out.println("A procura é maior que a demanda!");
                int[] aux;
                aux = new int[this.oferta.length + 1];
                for (int i = 0; i < aux.length; i++) {
                    if (i >= this.oferta.length) {
                        aux[i] = procura - demanda;// inserimos o novo valor na demanda
                        // System.out.print("|"+aux[i]);
                    } else {
                        aux[i] = this.oferta[i];
                        // System.out.print("|"+aux[i]);
                    }
                }
                this.oferta = aux;
                origem += 1;
                int[][] aux2;
                aux2 = new int[origem][destino];
                for (int i = 0; i < origem; i++) {
                    for (int j = 0; j < destino; j++) {
                        if (i == origem - 1) {
                            aux2[i][j] = 0;//Inserimos as novas demandas com valor 0
                        } else {
                            aux2[i][j] = custo[i][j];
                        }
                    }
                }
                custo = aux2;
                //Setamos os novos tamanhos das matrizes e vetores
                v = new int[destino];
                u = new int[origem];
                cn = new int[origem][destino];
                otima = new int[origem][destino];
            }
        }

    }

    public void cantonoroeste() {//Aplica o canto noroeste
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

    public void uv() {// Encontra U e V
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
    }
     /*Faz o processo de otimização do resultado encontrado utilizando U e V. 
      Retorna true caso a matriz resultante não seja ótima e retorna falso caso seja ótima
      ( A logica da pergunta é = Preciso chamar a função ótima novamente? se sim retorna true, se não false"
     */    
    public boolean otimiza() {
        this.uv();
        this.imprimeuv();
        this.zeraOtima();//Função que limpa o buffer da matriz ótima
        for (int i = 0; i < origem; i++) {//Aqui recalculamos a matriz ótima
            for (int j = 0; j < destino; j++) {
                if (otima[i][j] == 0) {
                    otima[i][j] = custo[i][j] - u[i] - v[j];
                }
            }
        }
        int menor = 0;//este será o menor valor
        int posx = 1;//esta será a posição x do menor valor da matriz
        int posy = 1;// esta será a posição y do menor valor da matriz
        for (int i = 0; i < origem; i++) {
            //Aqui procuramos o menor valor da matris e guardamos as cordenadas
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
        //Caso o menor valor da matriz seja negativo
        if (menor < 0) {
            System.out.println("min:" + menor + "[" + posx + "," + posy + "]\n");
            //Chamamos a classe arvore para retonar a matriz do canto noroeste recalculada de acordo com a nova rota
            Arvore a = new Arvore(cn, origem, destino, posx, posy, 0);
            a.montaArvore();//Monta a arvore com os valores da matriz canto noroeste cn
            a.buscaLargura();//Faz a Busca em largura, encontra a rota e coloca os sinais. 
            System.out.println("Sai->" + a.sai.valor);//Apos a busca ela encontra o menor valor negativo(variável que sai)
            cn = a.novocantonoroeste(cn);//Aqui a Classe arvore retorna para aclasse dados a nova matriz calculada de acordo com a rota do menor valor
            //System.out.println("\n Matriz de teste de ótimidade:");
            //this.imprimemat(otima);//Aqui apenas imprimimos a matriz com os valores ótimos
            System.out.println("\n Nova matriz:");
            this.imprimemat(cn);//imprime a nova matriz que foi calculada pela classe Arvore
            System.out.println("");
            return true;//Retorna true pois háviam valores negatimos na matriz otima
        } else {
            return false;//Chegamos aqui caso não seja mais necessário otimizar ou seja chegamos a um resultado satirfatório
        }
    }
    public void custoTotal() {
        this.custoT = 0;
        for (int i = 0; i < origem; i++) {
            for (int j = 0; j < destino; j++) {
            if(cn[i][j]>0){
              custoT+=(cn[i][j]*custo[i][j]);  
              }
            }

        }
      System.out.println("\nCusto total:"+this.custoT);
    }
//Aqui são apenas métodos de impressão e auxilio
    public void imprimemat(int[][] a) {
        for (int i = 0; i < origem; i++) {
            for (int j = 0; j < destino; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public void zeraOtima() {
        for (int i = 0; i < origem; i++) {
            for (int j = 0; j < destino; j++) {
                otima[i][j] = 0;
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
