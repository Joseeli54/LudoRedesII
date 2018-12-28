/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ludo;

import Gui.GuiLudo;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static ludo.ColorFicha.Amarillo;
import static ludo.ColorFicha.Azul;
import static ludo.ColorFicha.Rojo;
import static ludo.ColorFicha.Verde;

/**
 *
 * @author Elias Barrientos
 */
public class Tablero extends Applet{
    public GuiLudo guiludo;
    private Dado dado;
    private final ArrayList<Jugador> jugadores;
    private int Resultado;

    public Tablero() {
         dado = new Dado();
         dado.setCantidad(0);
         this.guiludo = new GuiLudo();
         jugadores = new ArrayList<>();
        
        this.guiludo.Registrar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
               try{
                   String nombre = getgui().getJTextFieldJugador().getText();
                   registrarJugador(nombre);
                   getgui().mostrarJugadoresEnTabla(getJugadores());
                   habilitarJuego();
                   getgui().getJTextFieldJugador().setText("");
                   getgui().getJTextFieldJugador().requestFocus();
               }
               catch(Exception e){
               }
            }
        });
        
        this.guiludo.Iniciar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                     getgui().getJLabelDados().setEnabled(true);
                     getgui().getJTextFieldDados().setEnabled(true); 
                     getgui().SeleccionarDados().setVisible(true);
                     getgui().getJTextFieldJugador().setEnabled(false);
                     getgui().getJLabelNombre().setEnabled(false);
                     getgui().Iniciar().setEnabled(false);
                     getgui().Registrar().setEnabled(false);
            } 
        });
        
        this.guiludo.SeleccionarDados().addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent ae) {
                 String CantidadDados = getgui().getJTextFieldDados().getText();                     
               if(CantidadDados != null){
                if(CantidadDados.equals("2")){
                       dado.setCantidad(2);
                       JOptionPane.showMessageDialog(getgui(),"Se pediran 2 dados al Servidor");
                       getgui().getJLabelDados().setEnabled(false);
                       getgui().getJTextFieldDados().setEnabled(false);
                       getgui().SeleccionarDados().setVisible(false);
                       getgui().getLanzar().setEnabled(true);
                       getgui().getJTextFieldDados().setText("");
                 }
                else if(CantidadDados.equals("1")){
                       dado.setCantidad(1);
                       JOptionPane.showMessageDialog(getgui(),"Se pedira 1 dado al Servidor");
                       getgui().getJLabelDados().setEnabled(false);
                       getgui().getJTextFieldDados().setEnabled(false);
                       getgui().SeleccionarDados().setVisible(false);
                       getgui().getLanzar().setEnabled(true);
                       getgui().getJTextFieldDados().setText("");
                }
                } else{
                      JOptionPane.showMessageDialog(getgui(),"Escriba una cantidad");
               }
             }
        });
        
        this.guiludo.getLanzar().addActionListener(new ActionListener(){

             @Override
             public void actionPerformed(ActionEvent ae) {
                 RealizarJugada();
             }
        });
        
        this.guiludo.getFichaAmarilla().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaAmarilla1().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaAmarilla2().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaAmarilla3().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaAzul().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaAzul1().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaAzul2().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaAzul3().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaRoja().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaRoja1().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaRoja2().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaRoja3().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaVerde().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaVerde1().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaVerde2().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaVerde3().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {}
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
       /*this.guiludo.getFichaAmarilla().addMouseMotionListener(new MouseMotionListener(){

            @Override
            public void mouseDragged(MouseEvent me) {
             if(getgui().getFichaAmarilla().isEnabled() == true){
               
               int x = getgui().getFichaAmarilla().getX();
               int y = getgui().getFichaAmarilla().getY();
               int Postx = me.getX();
               int Posty = me.getY();
               int MitadFichaHorizontal = getgui().getFichaAmarilla().getWidth()/2;
               int MitadFichaVertical = getgui().getFichaAmarilla().getHeight()/2;
               int MovHorizontal = (MitadFichaHorizontal-Postx)*(-1);
               int MovVertical = (MitadFichaVertical-Posty)*(-1);
               
               if(Postx >= MitadFichaHorizontal &&
                  Posty >= MitadFichaVertical)
               getgui().getFichaAmarilla().setLocation(x+2, y+2);
               else if(Postx >= MitadFichaHorizontal &&
                  Posty <= MitadFichaVertical)
               getgui().getFichaAmarilla().setLocation(x+2, y-2);
               else if(Postx <= MitadFichaHorizontal &&
                  Posty <= MitadFichaVertical)
               getgui().getFichaAmarilla().setLocation(x-2, y-2);
               else if(Postx <= MitadFichaHorizontal &&
                  Posty >= MitadFichaVertical)
               getgui().getFichaAmarilla().setLocation(x-2, y+2);
             } else{
              JOptionPane.showMessageDialog(getgui(),"Esta ficha esta Inhabilitada temporalmente");
             }          
            }

            @Override
            public void mouseMoved(MouseEvent me){}
        });*/
        
    }
    
    public void habilitarJugador(){
               if(dado.getCantidad() !=0){  
               getgui().getLanzar().setEnabled(true);
               if(jugadores.get(0).getFicha(0).getColor() == Azul){
                  getgui().InhabilitarFichas(1);
               } 
               else 
                   if(jugadores.get(0).getFicha(0).getColor() == Amarillo){
                    getgui().InhabilitarFichas(2);
                   }
               else 
                   if(jugadores.get(0).getFicha(0).getColor() == Rojo){
                    getgui().InhabilitarFichas(3);
                   }
               else 
                   if(jugadores.get(0).getFicha(0).getColor() == Verde){
                    getgui().InhabilitarFichas(4);
                   }
               }
    }
    
     public void Mover_Ficha(int posicion, JLabel ficha){
         if(posicion == 0){ficha.setLocation(390, 250);}
         if(posicion == 1){ficha.setLocation(360, 250);}
         if(posicion == 2){ficha.setLocation(330, 250);}
         if(posicion == 3){ficha.setLocation(300, 250);}
         if(posicion == 4){ficha.setLocation(270, 250);}
         if(posicion == 5){ficha.setLocation(240, 280);}
         if(posicion == 6){ficha.setLocation(240, 310);}
         if(posicion == 7){ficha.setLocation(240, 340);}
         if(posicion == 8){ficha.setLocation(240, 370);}
         if(posicion == 9){ficha.setLocation(240, 400);}
         if(posicion == 10){ficha.setLocation(240, 430);}
         if(posicion == 11){ficha.setLocation(210, 430);}
         if(posicion == 12){ficha.setLocation(180, 430);}
         if(posicion == 13){ficha.setLocation(180, 400);}
         if(posicion == 14){ficha.setLocation(180, 370);}
         if(posicion == 15){ficha.setLocation(180, 340);}
         if(posicion == 16){ficha.setLocation(180, 310);}
         if(posicion == 17){ficha.setLocation(180, 280);}
         if(posicion == 18){ficha.setLocation(150, 250);}
         if(posicion == 19){ficha.setLocation(120, 250);}
         if(posicion == 20){ficha.setLocation(90, 250);}
         if(posicion == 21){ficha.setLocation(60, 250);}
         if(posicion == 22){ficha.setLocation(30, 250);}
         if(posicion == 23){ficha.setLocation(0, 250);}
         if(posicion == 24){ficha.setLocation(0, 220);}
         if(posicion == 25){ficha.setLocation(0, 190);}
         if(posicion == 26){ficha.setLocation(30, 190);}
         if(posicion == 27){ficha.setLocation(60, 190);}
         if(posicion == 28){ficha.setLocation(90, 190);}
         if(posicion == 29){ficha.setLocation(120, 190);}
         if(posicion == 30){ficha.setLocation(150, 190);}
         if(posicion == 31){ficha.setLocation(180, 160);}
         if(posicion == 32){ficha.setLocation(180, 130);}
         if(posicion == 33){ficha.setLocation(180, 100);}
         if(posicion == 34){ficha.setLocation(180, 70);}
         if(posicion == 35){ficha.setLocation(180, 40);}
         if(posicion == 36){ficha.setLocation(180, 10);}
         if(posicion == 37){ficha.setLocation(210, 10);}
         if(posicion == 38){ficha.setLocation(240, 10);}
         if(posicion == 39){ficha.setLocation(240, 40);}
         if(posicion == 40){ficha.setLocation(240, 70);}
         if(posicion == 41){ficha.setLocation(240, 100);}
         if(posicion == 42){ficha.setLocation(240, 130);}
         if(posicion == 43){ficha.setLocation(240, 160);}
         if(posicion == 44){ficha.setLocation(270, 190);}
         if(posicion == 45){ficha.setLocation(300, 190);}
         if(posicion == 46){ficha.setLocation(330, 190);}
         if(posicion == 47){ficha.setLocation(360, 190);}
         if(posicion == 48){ficha.setLocation(390, 190);}
         if(posicion == 49){ficha.setLocation(420, 190);}
         if(posicion == 50){ficha.setLocation(420, 220);}
         if(posicion == 51){ficha.setLocation(420, 250);}
     }
    
     public void habilitarJuego() {
        if (getJugadores().size() >=1) {
            getgui().Iniciar().setEnabled(true);
        }
        if (getJugadores().size() > 4) {
            getgui().Registrar().setEnabled(false);
        }
    }
    
    public int LanzamientoDado(){
        return dado.getNumero();
    }
    
    public void siguienteJugador() {
        getJugadores().add(getJugadores().get(0));
        getJugadores().remove(0);
    }
    
    public void RealizarJugada(){
       Resultado = LanzamientoDado();
       JOptionPane.showMessageDialog(getgui(),"Valor resultante: "+Resultado);
       siguienteJugador();
       getgui().mostrarJugadoresEnTabla(getJugadores());
    }
    
    public ArrayList<Ficha> crearFichas(ColorFicha color){
    ArrayList<Ficha> fichas = new ArrayList<>();;
        for(int i = 0; i <= 3; i++){
           Ficha ficha = new Ficha(color, -1);
           fichas.add(ficha); 
        }
     return fichas; 
    }
    
    public void registrarJugador(String nombre) {
       
        if(getJugadores().size() == 0){
            Jugador jugador = new Jugador(crearFichas(Azul), nombre, new Carcel(4));
            getJugadores().add(jugador);
            getgui().VerFichasAzules();
        }
            else if (getJugadores().size() == 1){
                Jugador jugador = new Jugador(crearFichas(Amarillo), nombre, new Carcel(4));
                getJugadores().add(jugador);
                getgui().VerFichasAmarillas();
            }
                else if (getJugadores().size() == 2){
                    Jugador jugador = new Jugador(crearFichas(Rojo), nombre, new Carcel(4));
                    getJugadores().add(jugador);
                    getgui().VerFichasRojas();
                }
                    else if (getJugadores().size() == 3){
                        Jugador jugador = new Jugador(crearFichas(Verde), nombre, new Carcel(4));
                        getJugadores().add(jugador);
                        getgui().VerFichasVerdes();
                    }
                    else{
                       JOptionPane.showMessageDialog(getgui(),"No se puede agregar a mas de 4 jugadores");
                    }
    }
    
    public GuiLudo getgui() {
        return guiludo;
    }
    
     public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
}
