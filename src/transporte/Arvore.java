/*
 * Classe resposável por encontrar o menor ciclo que contenha o menor valor negativo e rearanjar a matriz
 */
package transporte;

import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public class Arvore {

    ArrayList<Vertice> vert = new ArrayList();//Este é o array de vertices retirados da matriz cn
    ArrayList<Aresta> arestas = new ArrayList();//Array de arestas que são candidatas ao menor caminho
    ArrayList<Vertice> caminho = new ArrayList();//Array que contem os vertices que serão alterados
    Vertice centro;//Variável auxiliar para busca do menor caminho
    Vertice sai;//Vertice que saí, é utilizado para recalcular todos os vertices do caminho
    int[][] mat;//Variável que recebe a matriz da classe dados e é utilizada para criar a arvore
    int m;//tamanho da oferta;
    int n;//tamanho da procura;
    int paix;//posição X do nó principa(Menor valor negativo da matriz)
    int paiy;//posição Y do nó principa
    int paiv;//valor do nó principa

    public Arvore(int[][] mat, int m, int n, int x, int y, int v) {//Método contrutor da nossa arvore
        this.mat = mat;
        this.m = m;
        this.n = n;
        this.paix = x;
        this.paiy = y;
        this.paiv = v;
        vert.add(new Vertice(paix, paiy, 1, paiv));//O nó principal é o primeiro vertice do nosso array
        for (int i = 0; i < m; i++) {//Aqui adicionamos os valores da matriz na nossa arvore como vertices
            for (int j = 0; j < n; j++) {
                if (mat[i][j] != 0) {
                    vert.add(new Vertice(i, j, 1, mat[i][j]));
                }
            }
        }

    }

    public void montaArvore() {//Função resposável por criar as arestas entre os vertices
        //Esta funçõa popula o array aresta de cada vertice ou seja adiciona ao vertice os filhos de cada vertice(Cria arestas)
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
        //Aqui fazemos uma busca em largura á fim se encontrar o nó que no ciclo será correspondente ao nó principal
        ArrayList<Vertice> fila = new ArrayList();//Criamos uma fila
        vert.get(0).cor = 1;//Visitamos o vertice 0(Nó principal)
        fila.add(vert.get(0));//Adicionamos o vertice 0 na fila
        this.caminho.add(vert.get(0));//Adicionamos o vertice 0 no caminhho(Que será dele para ele mesmo)
        while (fila.size() > 0) {//Enquanto a fila não for vazia fazemos a busca
            if (fila.get(0).aresta.size() == 1) {//Se o vertice tem apenas uma aresta já o descartamos pois ele não pode fazer parte de um ciclo tendo apenas uma aresta
                fila.get(0).ordemT = 0;
            } else {//Se ele tem mais de 1 aresta:
                for (int i = 0; i < fila.get(0).aresta.size(); i++) {
                    if (fila.get(0).aresta.get(i).cor == 0) {//Caso o vertice não tenha sido visitado
                        Aresta a = new Aresta(fila.get(0), fila.get(0).aresta.get(i));//Guardamos sua aresta
                        if (!arestas.contains(a)) {
                            arestas.add(a);
                        }
                        fila.get(0).aresta.get(i).ordemT = fila.get(0).ordemT + 1;//Calculamos sua ordem topologica
                        fila.get(0).aresta.get(i).cor = 1;//Visitamos o vertice
                        fila.add(fila.get(0).aresta.get(i));//O aicionamos na fila
                    } else {//Caso ele ja tenha sido visitado
                        if (fila.contains(fila.get(0).aresta.get(i))) {//E caso ele esteja na fila
                            //Significa que encontramos o menor ciclo entre o nó principal e ele msm
                            centro = fila.get(0).aresta.get(i);//Guardamos o centro do ciclo corresposndente ao nó principal
                            fila.get(0).aresta.get(i).cor = 2;//Pintamos ele de uma cor especial
                            Aresta a = new Aresta(fila.get(0), fila.get(0).aresta.get(i));
                            a.marcada = 1;
                            arestas.add(a);//Salvamos sua aresta
                            this.procuraCaminho();//(Segunda parte da busca em largura!)Procuramos os vertices do ciclo nas arestas salvas
                            System.out.println("");
                            fila.clear();
                            break;//Saimos do for e acabamos o método por aqui
                        }
                    }
                }
            }
            if (fila.size() > 0) {
                fila.remove(0);//Visitamos os filhos do vertice e não encontramos um ciclo vamos para o próximo vertice
            }
        }
    }

     //Segunda Parte Da busca:
    public void procuraCaminho() {
        //Este trecho comentado apenas imprime a ordem topologica e os vertices que foram salvos!
        /*System.out.print("Ordem Topologica:");
        this.imprimeordemT();
        System.out.println("");
        System.out.print("Aresta Para Busca:");
        for (int i = 0; i < arestas.size(); i++) {
            System.out.print("[" + arestas.get(i).a.valor + "-" + arestas.get(i).b.valor + "]");
        }*/
        System.out.println("");
        caminho.clear();//Limpamos o buffer
        int cont = 0;//Aqui procuramos os vertices remanescentes nas arestas salvas
        for (int i = 0; i < vert.size(); i++) {
            cont = 0;
            for (int j = 0; j < arestas.size(); j++) {
                if (arestas.get(j).a == vert.get(i) || arestas.get(j).b == vert.get(i)) {
                    cont++;
                }
            }
            if (cont > 1) {
                if (!caminho.contains(vert.get(i))) {
                    caminho.add(vert.get(i));//Se o vertice é remanescente salvamos ele no caminho
                }
            }
        }
        int verifica = 0;//Verifica se não houve a inserção repetida de um vertice remanescente
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
                        caminho.remove(caminho.get(i));//Houve um vertice repetido e estamos ó removendo
                        break;
                    }
                }
            }
        }
        System.out.println("Caminho:");
        this.imprimefila(caminho);//Encontramos o caminho
        this.varqsai();//Procuramos a variável do caminho que irá sair
    }
    
    public void varqsai() {
        this.calculasinal();//Esta função adiciona os sinais aos vertices
        for (int i = 0; i < caminho.size(); i++) {
            if (caminho.get(i).sinal < 0) {
                if (sai == null || caminho.get(i).valor < sai.valor) {
                    sai = caminho.get(i);//Salvamos á variável do caminho que irá sair
                }
            }
        }
    }

    public void calculasinal() {//Calculamos os sinais de acordo com a ordem topológica
        for (int i = 0; i < vert.size(); i++) {
            if (vert.get(i).ordemT % 2 == 0) {
                vert.get(i).sinal = 1;
            } else {
                vert.get(i).sinal = -1;
            }
        }
    }
    //Aqui recalculamos a matriz que chegou de acordo com a nova rota somando ou subtraindo a variável que sai
    public int[][] novocantonoroeste(int[][] cn) { 
        int[][] cn2 = cn;
        for (int i = 0; i < caminho.size(); i++) {
            if (caminho.get(i).sinal < 0) {
                cn2[caminho.get(i).posx][caminho.get(i).posy] -= sai.valor;
            } else {
                cn2[caminho.get(i).posx][caminho.get(i).posy] += sai.valor;
            }
        }
        return cn2;//Retornamos a nova matriz
    }
    //Métodos auxiliares que imprimem Vertices e arestas
    public void imprimeordemT() {
        for (int i = 0; i < vert.size(); i++) {
            System.out.print("[" + vert.get(i).valor + "(" + vert.get(i).ordemT + ")]");
        }
    }

    public void imprimefila(ArrayList<Vertice> fila) {
        for (int i = 0; i < fila.size(); i++) {
            System.out.print("|" + fila.get(i).valor);
        }
        System.out.println("");
    }

}
