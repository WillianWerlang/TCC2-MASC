/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aaaaa.utilidades;

import aaaaa.testerest.Pontos;
import java.util.Comparator;

/**
 *
 * @author marcelotelles
 */
public class ComparaDadosPorLongitude implements Comparator<Pontos>{
   
   @Override
   public int compare(Pontos c1, Pontos c2) {  
         if(c1 == null || c2 == null)  
             System.out.println("Nome em branco");  
           
         String  nome1 = c1.getLon();
         String nome2 = c2.getLon();  

         return nome1.compareTo(nome2);  
     }


}
