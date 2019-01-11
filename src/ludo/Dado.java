/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ludo;

import java.util.ArrayList;

/**
 *
 * @author Elias Barrientos
 */
public class Dado {
    
    private final ArrayList<Integer> numeros;
    public int cantidad;

    public Dado() {
        this.numeros = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            this.numeros.add(i);
        }
    }
    
    public int getNumero() {
        int numero = 0;
        
        if(getCantidad() == 2){
            int num1, num2;
            do { 
                num1 = (int) (Math.random() * this.numeros.size());
            } while (num1 < 1 || num1 > 6);
            do { 
                num2 = (int) (Math.random() * this.numeros.size());
            } while (num2 < 1 || num2 > 6);
            numero = num1+num2;
        }
        else 
            if(getCantidad() == 1){                
                do { 
                numero = (int) (Math.random() * this.numeros.size());
            } while (numero < 1 || numero > 6);
        }
        
       return numero;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }  
    
}
