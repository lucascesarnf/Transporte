/*
 Problema do transporte CCO
 */
package transporte;

/**
 *
 * @author lucas
 */
public class Transporte {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Criamos um objeto dados
        Dados d = new Dados();
        System.out.println("Custo:");
        d.imprimemat(d.custo);//Vemos o seu custo
        System.out.println("Canto Noroeste:");
        d.cantonoroeste();//Chamamos o canto noroeste para calcular a soluçõa inicial
        d.imprimemat(d.cn);//Vemos esta matriz:
        System.out.println("");
        //Aqui otimizamos a resposta inicial até que ela seja satisfatória:
        do{
        d.otimiza();
        d.custoTotal();
       }while(d.otimiza());
       //Quando si do loop temos a respostá ótima dentro d variável CN(d.cn)
        System.out.println("Resposta ótima:");
        d.imprimemat(d.cn);
        d.custoTotal();
    }

}
