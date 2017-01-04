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
public class Transporte {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Dados d = new Dados();
        System.out.println("Custo:");
        d.imprimemat(d.custo);
        System.out.println("Canto Noroeste:");
        d.cantonoroeste();
        d.imprimemat(d.cn);
        System.out.println("");
        do{
        d.otimiza();
       }while(d.otimiza());
       
        System.out.println("Resposta ótima:");
        d.imprimemat(d.cn);
    }

}
