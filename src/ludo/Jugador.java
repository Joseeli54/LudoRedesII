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
public class Jugador {
    Carcel carcel;
    ArrayList<Ficha> fichas;
    String nombre;
    
    public Jugador(ArrayList<Ficha> fichas, String nombre, Carcel carcel) {
        this.fichas = fichas;
        this.nombre = nombre;
        this.carcel = carcel;
    }

    public Ficha getFicha(int numero) {
        return fichas.get(numero);
    }

    public void setFicha(int numero, Ficha ficha) {
          this.fichas.set(numero, ficha);
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }  

    public Carcel getCarcel() {
        return carcel;
    }

    public void setCarcel(Carcel carcel) {
        this.carcel = carcel;
    }
    
}
