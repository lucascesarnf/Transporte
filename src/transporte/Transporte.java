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
        d.imprimemat(d.custo);
        System.out.println("");
        d.cantonoroeste();
        d.imprimemat(d.cn);
        System.out.println("");
        d.uv();
        d.imprimeuv();
        d.otimiza();
    }

}
