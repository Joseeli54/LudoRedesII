/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ludo;

/**
 *
 * @author Elias Barrientos
 */
public class Ficha {
    public ColorFicha color;
    public int posicion; 

    public Ficha(ColorFicha color, int posicion) {
        this.color = color;
        this.posicion = posicion;
    }

    public ColorFicha getColor() {
        return color;
    }

    public void setColor(ColorFicha color) {
        this.color = color;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    
}
