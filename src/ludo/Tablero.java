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
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static ludo.ColorFicha.Amarillo;
import static ludo.ColorFicha.Azul;
import static ludo.ColorFicha.Rojo;
import static ludo.ColorFicha.Verde;
import socket.Cliente;

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
                try {
                    getgui().getJLabelDados().setEnabled(true);
                    getgui().getJTextFieldDados().setEnabled(true);
                    getgui().SeleccionarDados().setVisible(true);
                    getgui().getJTextFieldJugador().setEnabled(false);
                    getgui().getJLabelNombre().setEnabled(false);
                    getgui().Iniciar().setEnabled(false);
                    getgui().Registrar().setEnabled(false);
                    //inicio cliente
                    Cliente cli = new Cliente(); //Se crea el cliente
                    System.out.println("Iniciando cliente\n");
                    cli.empezarFlujo();
                    cli.startClient(jugadores.get(0).getNombre()); //Se inicia el cliente
                    //cli.enviarmensaje(jugadores.get(0).getNombre());
                     guiludo.setCliente(cli);
                      
                } catch (IOException ex) {
                    Logger.getLogger(Tablero.class.getName()).log(Level.SEVERE, null, ex);
                }
        
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
                       getgui().InhabilitarFichas(1);
                 }
                else if(CantidadDados.equals("1")){
                       dado.setCantidad(1);
                       JOptionPane.showMessageDialog(getgui(),"Se pedira 1 dado al Servidor");
                       getgui().getJLabelDados().setEnabled(false);
                       getgui().getJTextFieldDados().setEnabled(false);
                       getgui().SeleccionarDados().setVisible(false);
                       getgui().getLanzar().setEnabled(true);
                       getgui().getJTextFieldDados().setText("");
                       getgui().InhabilitarFichas(1);
                }
                } else{
                      JOptionPane.showMessageDialog(getgui(),"Escriba una cantidad");
               }
             }
        });
        
        this.guiludo.getLanzar().addActionListener(new ActionListener(){

             @Override
             public void actionPerformed(ActionEvent ae) {
                 getgui().getLanzar().setEnabled(false);
                 RealizarJugada();
             }
        });
        
        this.guiludo.getFichaAzul().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {
                if(jugadores.get(0).getFicha(0).getColor() == Azul){
                    if(getgui().getFichaAzul().isEnabled() == true){
                        if(jugadores.get(0).getFicha(0).getPosicion() == -1){
                            if(Resultado >= 6){
                                int FichaMontada = 0;
                                
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 0){
                                        FichaMontada = 1;
                                    }
                                }
                                
                                if(FichaMontada == 0){
                                    Mover_Ficha(0,getgui().getFichaAzul());
                                    jugadores.get(0).getFicha(0).setPosicion(0);
                                    jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                    siguienteJugador();
                                    getgui().mostrarJugadoresEnTabla(getJugadores());
                                    getgui().InhabilitarFichas(2);
                                }else{
                                    JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        }else
                            if(jugadores.get(0).getFicha(0).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(0).getPosicion();
                               
                               LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 50 && jugadores.get(0).getFicha(0).getPosicion() <= limite.LimiteFichaColorAzul){ 
                                 Pasos = (limite.LimiteFichaColorAzul + 1) + (Resultado-
                                         (limite.LimiteFichaColorAzul-jugadores.get(0).getFicha(0).getPosicion()));
                               }
                               
                               int PuedoEstacionarme = mismos_pasos(0, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                               Mover_Ficha(Pasos,getgui().getFichaAzul());
                               jugadores.get(0).getFicha(0).setPosicion(Pasos);
                               siguienteJugador();
                               getgui().mostrarJugadoresEnTabla(getJugadores());
                               getgui().InhabilitarFichas(2);
                               } else{
                                JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                                siguienteJugador();
                                getgui().mostrarJugadoresEnTabla(getJugadores());
                                getgui().InhabilitarFichas(2);
                               }
                            }
                    }
                }
            }
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
            public void mouseClicked(MouseEvent me) {
                 if(jugadores.get(0).getFicha(1).getColor() == Azul){
                    if(getgui().getFichaAzul1().isEnabled() == true){
                        if(jugadores.get(0).getFicha(1).getPosicion() == -1){
                            if(Resultado >= 6){
                                
                                int FichaMontada = 0;
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 0){
                                        FichaMontada = 1;
                                    }
                                }
                                 
                                if(FichaMontada == 0){
                                    Mover_Ficha(0,getgui().getFichaAzul1());
                                    jugadores.get(0).getFicha(1).setPosicion(0);
                                    jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                    siguienteJugador();
                                    getgui().mostrarJugadoresEnTabla(getJugadores());
                                    getgui().InhabilitarFichas(2);
                                } else{
                                JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        }else
                            if(jugadores.get(0).getFicha(1).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(1).getPosicion();
                               
                                LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 50 && jugadores.get(0).getFicha(1).getPosicion() <= limite.LimiteFichaColorAzul){ 
                                 Pasos = (limite.LimiteFichaColorAzul + 1) + (Resultado-
                                         (limite.LimiteFichaColorAzul-jugadores.get(0).getFicha(1).getPosicion()));
                               }
                               
                               int PuedoEstacionarme = mismos_pasos(1, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                               Mover_Ficha(Pasos,getgui().getFichaAzul1());
                               jugadores.get(0).getFicha(1).setPosicion(Pasos);
                               siguienteJugador();
                               getgui().mostrarJugadoresEnTabla(getJugadores());
                               getgui().InhabilitarFichas(2);
                               } else{
                                JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                                siguienteJugador();
                                getgui().mostrarJugadoresEnTabla(getJugadores());
                                getgui().InhabilitarFichas(2);
                                }
                            }
                    }
                }
            }
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
            public void mouseClicked(MouseEvent me) {
                 if(jugadores.get(0).getFicha(2).getColor() == Azul){
                    if(getgui().getFichaAzul2().isEnabled() == true){
                        if(jugadores.get(0).getFicha(2).getPosicion() == -1){
                            if(Resultado >= 6){
                                int FichaMontada = 0;
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 0){
                                        FichaMontada = 1;
                                    }
                                }
                                
                                if(FichaMontada == 0){
                                Mover_Ficha(0,getgui().getFichaAzul2());
                                jugadores.get(0).getFicha(2).setPosicion(0);
                                jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                siguienteJugador();
                                getgui().mostrarJugadoresEnTabla(getJugadores());
                                getgui().InhabilitarFichas(2);
                                } else{
                                JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        }else
                            if(jugadores.get(0).getFicha(2).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(2).getPosicion();
                               
                               LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 50 && jugadores.get(0).getFicha(2).getPosicion() <= limite.LimiteFichaColorAzul){ 
                                 Pasos = (limite.LimiteFichaColorAzul + 1) + (Resultado-
                                         (limite.LimiteFichaColorAzul-jugadores.get(0).getFicha(2).getPosicion()));
                               }
                               
                               int PuedoEstacionarme = mismos_pasos(2, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                               Mover_Ficha(Pasos,getgui().getFichaAzul2());
                               jugadores.get(0).getFicha(2).setPosicion(Pasos);
                               siguienteJugador();
                               getgui().mostrarJugadoresEnTabla(getJugadores());
                               getgui().InhabilitarFichas(2);
                               } else{
                               JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                               siguienteJugador();
                               getgui().mostrarJugadoresEnTabla(getJugadores());
                               getgui().InhabilitarFichas(2);
                                }
                            }
                    }
                }
            }
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
            public void mouseClicked(MouseEvent me) {
                 if(jugadores.get(0).getFicha(3).getColor() == Azul){
                    if(getgui().getFichaAzul3().isEnabled() == true){
                        if(jugadores.get(0).getFicha(3).getPosicion() == -1){
                            if(Resultado >= 6){
                                int FichaMontada = 0;
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 0){
                                        FichaMontada = 1;
                                    }
                                }
                                
                                if(FichaMontada == 0){
                                Mover_Ficha(0,getgui().getFichaAzul3());
                                jugadores.get(0).getFicha(3).setPosicion(0);
                                jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                siguienteJugador();
                                getgui().mostrarJugadoresEnTabla(getJugadores());
                                getgui().InhabilitarFichas(2);
                                } else{
                                JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        }else
                            if(jugadores.get(0).getFicha(3).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(3).getPosicion();
                               
                               LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 50 && jugadores.get(0).getFicha(3).getPosicion() <= limite.LimiteFichaColorAzul){ 
                                 Pasos = (limite.LimiteFichaColorAzul + 1) + (Resultado-
                                         (limite.LimiteFichaColorAzul-jugadores.get(0).getFicha(3).getPosicion()));
                               }
                               
                               int PuedoEstacionarme = mismos_pasos(3, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                               Mover_Ficha(Pasos,getgui().getFichaAzul3());
                               jugadores.get(0).getFicha(3).setPosicion(Pasos);
                               siguienteJugador();
                               getgui().mostrarJugadoresEnTabla(getJugadores());
                               getgui().InhabilitarFichas(2);
                               }else{
                                JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                                siguienteJugador();
                                getgui().mostrarJugadoresEnTabla(getJugadores());
                                getgui().InhabilitarFichas(2);
                                }
                            }
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent me) {}
            @Override
            public void mouseReleased(MouseEvent me) {}
            @Override
            public void mouseEntered(MouseEvent me) {}
            @Override
            public void mouseExited(MouseEvent me) {}
        });
        
        this.guiludo.getFichaAmarilla().addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {
                if(jugadores.get(0).getFicha(0).getColor() == Amarillo){
                    if(getgui().getFichaAmarilla().isEnabled() == true){
                        if(jugadores.get(0).getFicha(0).getPosicion() == -1){
                            if(Resultado >= 6){
                                
                                int FichaMontada = 0;
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 13){
                                        FichaMontada = 1;
                                    }
                                }
                                
                                if(FichaMontada == 0){
                                    Mover_Ficha(13,getgui().getFichaAmarilla());
                                    jugadores.get(0).getFicha(0).setPosicion(13);
                                    jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                    siguienteJugador();

                                    if(jugadores.size() >= 3){
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(3);
                                    }
                                    else{
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(1);
                                    }
                                } else{
                                JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        }else
                            if(jugadores.get(0).getFicha(0).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(0).getPosicion();
                               
                               LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 11 && jugadores.get(0).getFicha(0).getPosicion() <= limite.LimiteFichaColorAmarillo){ 
                                 Pasos = (57) + (Resultado-
                                         (limite.LimiteFichaColorAmarillo-jugadores.get(0).getFicha(0).getPosicion()));
                               }
                               else
                               if(Pasos > 51){ 
                                 Pasos = Resultado - (51 - jugadores.get(0).getFicha(0).getPosicion()) - 1;
                               }
                               
                               int PuedoEstacionarme = mismos_pasos(0, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                               Mover_Ficha(Pasos,getgui().getFichaAmarilla());
                               jugadores.get(0).getFicha(0).setPosicion(Pasos);
                               siguienteJugador();
                               if(jugadores.size() >= 3){
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(3);
                                    }
                                    else{
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(1);
                                    }
                               }else{
                                JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                                siguienteJugador();
                               if(jugadores.size() >= 3){
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(3);
                                    }
                                    else{
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(1);
                                    }
                                }
                            }
                    }
                }
            }
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
            public void mouseClicked(MouseEvent me) {
                if(jugadores.get(0).getFicha(1).getColor() == Amarillo){
                    if(getgui().getFichaAmarilla1().isEnabled() == true){
                        if(jugadores.get(0).getFicha(1).getPosicion() == -1){
                            if(Resultado >= 6){
                                
                                int FichaMontada = 0;
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 13){
                                        FichaMontada = 1;
                                    }
                                }
                                
                                if(FichaMontada == 0){
                                Mover_Ficha(13,getgui().getFichaAmarilla1());
                                jugadores.get(0).getFicha(1).setPosicion(13);
                                jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                siguienteJugador();
                                
                                if(jugadores.size() >= 3){
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(3);
                                }
                                else{
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(1);
                                }
                                } else{
                                JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        }else
                            if(jugadores.get(0).getFicha(1).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(1).getPosicion();
                              
                               LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 11 && jugadores.get(0).getFicha(1).getPosicion() <= limite.LimiteFichaColorAmarillo){ 
                                 Pasos = (57) + (Resultado-
                                         (limite.LimiteFichaColorAmarillo-jugadores.get(0).getFicha(1).getPosicion()));
                               }
                               else
                               if(Pasos > 51){ 
                                 Pasos = Resultado - (51 - jugadores.get(0).getFicha(1).getPosicion()) - 1;
                               }
                               
                                int PuedoEstacionarme = mismos_pasos(1, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                               Mover_Ficha(Pasos,getgui().getFichaAmarilla1());
                               jugadores.get(0).getFicha(1).setPosicion(Pasos);
                               siguienteJugador();
                               if(jugadores.size() >= 3){
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(3);
                                    }
                                    else{
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(1);
                                    }
                                }else{
                                JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                                siguienteJugador();
                               if(jugadores.size() >= 3){
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(3);
                                    }
                                    else{
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(1);
                                    }
                                }
                            }
                    }
                }
            }
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
            public void mouseClicked(MouseEvent me) {
                if(jugadores.get(0).getFicha(2).getColor() == Amarillo){
                    if(getgui().getFichaAmarilla2().isEnabled() == true){
                        if(jugadores.get(0).getFicha(2).getPosicion() == -1){
                            if(Resultado >= 6){
                                int FichaMontada = 0;
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 13){
                                        FichaMontada = 1;
                                    }
                                }
                                
                                if(FichaMontada == 0){
                                Mover_Ficha(13,getgui().getFichaAmarilla2());
                                jugadores.get(0).getFicha(2).setPosicion(13);
                                jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                siguienteJugador();
                                
                                if(jugadores.size() >= 3){
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(3);
                                }
                                else{
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(1);
                                }
                                } else{
                                JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        }else
                            if(jugadores.get(0).getFicha(2).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(2).getPosicion();
                               
                               LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 11 && jugadores.get(0).getFicha(2).getPosicion() <= limite.LimiteFichaColorAmarillo){ 
                                 Pasos = (57) + (Resultado-
                                         (limite.LimiteFichaColorAmarillo-jugadores.get(0).getFicha(2).getPosicion()));
                               }
                               else
                               if(Pasos > 51){ 
                                 Pasos = Resultado - (51 - jugadores.get(0).getFicha(2).getPosicion()) - 1;
                               }
                               
                               int PuedoEstacionarme = mismos_pasos(2, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                               Mover_Ficha(Pasos,getgui().getFichaAmarilla2());
                               jugadores.get(0).getFicha(2).setPosicion(Pasos);
                               siguienteJugador();
                               if(jugadores.size() >= 3){
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(3);
                                    }
                                    else{
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(1);
                                    }
                               }else{
                                JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                                siguienteJugador();
                               if(jugadores.size() >= 3){
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(3);
                                    }
                                    else{
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(1);
                                    }
                                }
                            }
                    }
                }
            }
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
            public void mouseClicked(MouseEvent me) {
                if(jugadores.get(0).getFicha(3).getColor() == Amarillo){
                    if(getgui().getFichaAmarilla3().isEnabled() == true){
                        if(jugadores.get(0).getFicha(3).getPosicion() == -1){
                            if(Resultado >= 6){
                                int FichaMontada = 0;
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 13){
                                        FichaMontada = 1;
                                    }
                                }
                                
                                if(FichaMontada == 0){
                                Mover_Ficha(13,getgui().getFichaAmarilla3());
                                jugadores.get(0).getFicha(3).setPosicion(13);
                                jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                siguienteJugador();
                                
                                if(jugadores.size() >= 3){
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(3);
                                }
                                else{
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(1);
                                }
                                } else{
                                JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        }else
                            if(jugadores.get(0).getFicha(3).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(3).getPosicion();
                               
                               LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 11 && jugadores.get(0).getFicha(3).getPosicion() <= limite.LimiteFichaColorAmarillo){ 
                                 Pasos = (57) + (Resultado-
                                         (limite.LimiteFichaColorAmarillo-jugadores.get(0).getFicha(3).getPosicion()));
                               }
                               else
                               if(Pasos > 51){ 
                                 Pasos = Resultado - (51 - jugadores.get(0).getFicha(3).getPosicion()) - 1;
                               }
                               
                               int PuedoEstacionarme = mismos_pasos(3, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                               Mover_Ficha(Pasos,getgui().getFichaAmarilla3());
                               jugadores.get(0).getFicha(3).setPosicion(Pasos);
                               siguienteJugador();
                               if(jugadores.size() >= 3){
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(3);
                                    }
                                    else{
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(1);
                                    }
                               
                               }else{
                                JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                                siguienteJugador();
                               if(jugadores.size() >= 3){
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(3);
                                    }
                                    else{
                                      getgui().mostrarJugadoresEnTabla(getJugadores());
                                      getgui().InhabilitarFichas(1);
                                    }
                                }
                            }
                    }
                }
            }
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
            public void mouseClicked(MouseEvent me) {
                if(jugadores.get(0).getFicha(0).getColor() == Rojo){
                    if(getgui().getFichaRoja().isEnabled() == true){
                        if(jugadores.get(0).getFicha(0).getPosicion() == -1){
                            if(Resultado >= 6){
                                int FichaMontada = 0;
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 39){
                                        FichaMontada = 1;
                                    }
                                }
                                
                                if(FichaMontada == 0){
                                Mover_Ficha(39,getgui().getFichaRoja());
                                jugadores.get(0).getFicha(0).setPosicion(39);
                                jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                siguienteJugador();
                                
                                if(jugadores.size() == 4){
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(4);
                                }
                                else{
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(1);
                                }
                                } else{
                                JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        }else
                            if(jugadores.get(0).getFicha(0).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(0).getPosicion();
                               
                               LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 37 && jugadores.get(0).getFicha(0).getPosicion() <= limite.LimiteFichaColorRojo){ 
                                 Pasos = (69) + (Resultado-
                                         (limite.LimiteFichaColorRojo-jugadores.get(0).getFicha(0).getPosicion()));
                               }
                               else
                               if(Pasos > 51){ 
                                 Pasos = Resultado - (51 - jugadores.get(0).getFicha(0).getPosicion()) - 1;
                               }
                               
                               int PuedoEstacionarme = mismos_pasos(0, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                               Mover_Ficha(Pasos,getgui().getFichaRoja());
                               jugadores.get(0).getFicha(0).setPosicion(Pasos);
                               siguienteJugador();
                               if(jugadores.size() == 4){
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(4);
                                }
                                else{
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(1);
                                }
                               }else{
                                JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                                siguienteJugador();
                               if(jugadores.size() == 4){
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(4);
                                }
                                else{
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(1);
                                }
                                }
                               
                            }
                    }
                }
            }
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
            public void mouseClicked(MouseEvent me) {
                if(jugadores.get(0).getFicha(1).getColor() == Rojo){
                    if(getgui().getFichaRoja1().isEnabled() == true){
                        if(jugadores.get(0).getFicha(1).getPosicion() == -1){
                            if(Resultado >= 6){
                                int FichaMontada = 0;
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 39){
                                        FichaMontada = 1;
                                    }
                                }
                                
                                if(FichaMontada == 0){
                                Mover_Ficha(39,getgui().getFichaRoja1());
                                jugadores.get(0).getFicha(1).setPosicion(39);
                                jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                siguienteJugador();
                                
                                if(jugadores.size() == 4){
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(4);
                                }
                                else{
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(1);
                                }
                                } else{
                                JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        }else
                            if(jugadores.get(0).getFicha(1).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(1).getPosicion();
                               
                               LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 37 && jugadores.get(0).getFicha(1).getPosicion() <= limite.LimiteFichaColorRojo){ 
                                 Pasos = (69) + (Resultado-
                                         (limite.LimiteFichaColorRojo-jugadores.get(0).getFicha(1).getPosicion()));
                               }
                               else
                               if(Pasos > 51){ 
                                 Pasos = Resultado - (51 - jugadores.get(0).getFicha(1).getPosicion()) - 1;
                               }
                               
                               int PuedoEstacionarme = mismos_pasos(1, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                               Mover_Ficha(Pasos,getgui().getFichaRoja1());
                               jugadores.get(0).getFicha(1).setPosicion(Pasos);
                               siguienteJugador();
                               if(jugadores.size() == 4){
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(4);
                                }
                                else{
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(1);
                                }
                               }else{
                                JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                                siguienteJugador();
                               if(jugadores.size() == 4){
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(4);
                                }
                                else{
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(1);
                                }
                                }
                            }
                    }
                }
            }
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
            public void mouseClicked(MouseEvent me) {
                if(jugadores.get(0).getFicha(2).getColor() == Rojo){
                    if(getgui().getFichaRoja2().isEnabled() == true){
                        if(jugadores.get(0).getFicha(2).getPosicion() == -1){
                            if(Resultado >= 6){
                                int FichaMontada = 0;
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 39){
                                        FichaMontada = 1;
                                    }
                                }
                                
                                if(FichaMontada == 0){
                                Mover_Ficha(39,getgui().getFichaRoja2());
                                jugadores.get(0).getFicha(2).setPosicion(39);
                                jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                siguienteJugador();
                                
                                if(jugadores.size() == 4){
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(4);
                                }
                                else{
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(1);
                                }
                                } else{
                                JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        } else
                            if(jugadores.get(0).getFicha(2).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(2).getPosicion();
                               
                               LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 37 && jugadores.get(0).getFicha(2).getPosicion() <= limite.LimiteFichaColorRojo){ 
                                 Pasos = (69) + (Resultado-
                                         (limite.LimiteFichaColorRojo-jugadores.get(0).getFicha(2).getPosicion()));
                               }
                               else
                               if(Pasos > 51){ 
                                 Pasos = Resultado - (51 - jugadores.get(0).getFicha(2).getPosicion()) - 1;
                               }
                               
                               int PuedoEstacionarme = mismos_pasos(2, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                               Mover_Ficha(Pasos,getgui().getFichaRoja2());
                               jugadores.get(0).getFicha(2).setPosicion(Pasos);
                               siguienteJugador();
                               if(jugadores.size() == 4){
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(4);
                                }
                                else{
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(1);
                                }
                               }else{
                                JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                                siguienteJugador();
                               if(jugadores.size() == 4){
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(4);
                                }
                                else{
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(1);
                                }
                                }
                        }
                    }
                }
            }
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
            public void mouseClicked(MouseEvent me) {
                if(jugadores.get(0).getFicha(3).getColor() == Rojo){
                    if(getgui().getFichaRoja3().isEnabled() == true){
                        if(jugadores.get(0).getFicha(3).getPosicion() == -1){
                            if(Resultado >= 6){
                                int FichaMontada = 0;
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 39){
                                        FichaMontada = 1;
                                    }
                                }
                                
                                if(FichaMontada == 0){
                                Mover_Ficha(39,getgui().getFichaRoja3());
                                jugadores.get(0).getFicha(3).setPosicion(39);
                                jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                siguienteJugador();
                                
                                if(jugadores.size() == 4){
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(4);
                                }
                                else{
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(1);
                                }
                                } else{
                                JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        }else
                            if(jugadores.get(0).getFicha(3).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(3).getPosicion();
                               
                               LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 37 && jugadores.get(0).getFicha(3).getPosicion() <= limite.LimiteFichaColorRojo){ 
                                 Pasos = (69) + (Resultado-
                                         (limite.LimiteFichaColorRojo-jugadores.get(0).getFicha(3).getPosicion()));
                               }
                               else
                               if(Pasos > 51){ 
                                 Pasos = Resultado - (51 - jugadores.get(0).getFicha(3).getPosicion()) - 1;
                               }
                               
                               int PuedoEstacionarme = mismos_pasos(3, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                               Mover_Ficha(Pasos,getgui().getFichaRoja3());
                               jugadores.get(0).getFicha(3).setPosicion(Pasos);
                               siguienteJugador();
                               if(jugadores.size() == 4){
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(4);
                                }
                                else{
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(1);
                                }
                               }else{
                                JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                                siguienteJugador();
                               if(jugadores.size() == 4){
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(4);
                                }
                                else{
                                  getgui().mostrarJugadoresEnTabla(getJugadores());
                                  getgui().InhabilitarFichas(1);
                                }
                                }
                            }
                    }
                }
            }
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
            public void mouseClicked(MouseEvent me) {
                if(jugadores.get(0).getFicha(0).getColor() == Verde){
                    if(getgui().getFichaVerde().isEnabled() == true){
                        if(jugadores.get(0).getFicha(0).getPosicion() == -1){
                            if(Resultado >= 6){
                                int FichaMontada = 0;
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 26){
                                        FichaMontada = 1;
                                    }
                                }
                                
                                if(FichaMontada == 0){
                                Mover_Ficha(26,getgui().getFichaVerde());
                                jugadores.get(0).getFicha(0).setPosicion(26);
                                jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                siguienteJugador();
                                getgui().mostrarJugadoresEnTabla(getJugadores());
                                getgui().InhabilitarFichas(1);
                                } else{
                                JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        }else
                            if(jugadores.get(0).getFicha(0).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(0).getPosicion();
                               
                               LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 24 && jugadores.get(0).getFicha(0).getPosicion() <= limite.LimiteFichaColorVerde){ 
                                 Pasos = (63) + (Resultado-
                                         (limite.LimiteFichaColorVerde-jugadores.get(0).getFicha(0).getPosicion()));
                               }
                               else
                               if(Pasos > 51){ 
                                 Pasos = Resultado - (51 - jugadores.get(0).getFicha(0).getPosicion()) - 1;
                               }
                               
                               int PuedoEstacionarme = mismos_pasos(0, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                               Mover_Ficha(Pasos,getgui().getFichaVerde());
                               jugadores.get(0).getFicha(0).setPosicion(Pasos);
                               siguienteJugador();
                               getgui().mostrarJugadoresEnTabla(getJugadores());
                               getgui().InhabilitarFichas(1);
                               }else{
                                JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                                siguienteJugador();
                                getgui().mostrarJugadoresEnTabla(getJugadores());
                                getgui().InhabilitarFichas(1);
                                }
                               
                            }
                    }
                }
            }
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
            public void mouseClicked(MouseEvent me) {
                if(jugadores.get(0).getFicha(1).getColor() == Verde){
                    if(getgui().getFichaVerde1().isEnabled() == true){
                        if(jugadores.get(0).getFicha(1).getPosicion() == -1){
                            if(Resultado >= 6){
                                int FichaMontada = 0;
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 26){
                                        FichaMontada = 1;
                                    }
                                }
                                
                                if(FichaMontada == 0){
                                Mover_Ficha(26,getgui().getFichaVerde1());
                                jugadores.get(0).getFicha(1).setPosicion(26);
                                jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                siguienteJugador();
                                getgui().mostrarJugadoresEnTabla(getJugadores());
                                getgui().InhabilitarFichas(1);
                                } else{
                                JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        }else
                            if(jugadores.get(0).getFicha(1).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(1).getPosicion();
                               
                               LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 24 && jugadores.get(0).getFicha(1).getPosicion() <= limite.LimiteFichaColorVerde){ 
                                 Pasos = (63) + (Resultado-
                                         (limite.LimiteFichaColorVerde-jugadores.get(0).getFicha(1).getPosicion()));
                               }
                               else
                               if(Pasos > 51){ 
                                 Pasos = Resultado - (51 - jugadores.get(0).getFicha(1).getPosicion()) - 1;
                               }
                               
                               int PuedoEstacionarme = mismos_pasos(1, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                               Mover_Ficha(Pasos,getgui().getFichaVerde1());
                               jugadores.get(0).getFicha(1).setPosicion(Pasos);
                               siguienteJugador();
                               getgui().mostrarJugadoresEnTabla(getJugadores());
                               getgui().InhabilitarFichas(1);
                               }else{
                                JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                                siguienteJugador();
                                getgui().mostrarJugadoresEnTabla(getJugadores());
                                getgui().InhabilitarFichas(1);
                                }
                               
                            }
                    }
                }
            }
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
            public void mouseClicked(MouseEvent me) {
                if(jugadores.get(0).getFicha(2).getColor() == Verde){
                    if(getgui().getFichaVerde2().isEnabled() == true){
                        if(jugadores.get(0).getFicha(2).getPosicion() == -1){
                            if(Resultado >= 6){
                                int FichaMontada = 0;
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 26){
                                        FichaMontada = 1;
                                    }
                                }
                                
                                if(FichaMontada == 0){
                                Mover_Ficha(26,getgui().getFichaVerde2());
                                jugadores.get(0).getFicha(2).setPosicion(26);
                                jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                siguienteJugador();
                                getgui().mostrarJugadoresEnTabla(getJugadores());
                                getgui().InhabilitarFichas(1);
                                } else{
                                JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        } else
                            if(jugadores.get(0).getFicha(2).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(2).getPosicion();
                               
                               LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 24 && jugadores.get(0).getFicha(2).getPosicion() <= limite.LimiteFichaColorVerde){ 
                                 Pasos = (63) + (Resultado-
                                         (limite.LimiteFichaColorVerde-jugadores.get(0).getFicha(2).getPosicion()));
                               }
                               else
                               if(Pasos > 51){ 
                                 Pasos = Resultado - (51 - jugadores.get(0).getFicha(2).getPosicion()) - 1;
                               }
                               
                               int PuedoEstacionarme = mismos_pasos(2, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                               Mover_Ficha(Pasos,getgui().getFichaVerde2());
                               jugadores.get(0).getFicha(2).setPosicion(Pasos);
                               siguienteJugador();
                               getgui().mostrarJugadoresEnTabla(getJugadores());
                               getgui().InhabilitarFichas(1);
                               }else{
                                JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                                siguienteJugador();
                                getgui().mostrarJugadoresEnTabla(getJugadores());
                                getgui().InhabilitarFichas(1);
                                }
                               
                            }
                    }
                }
            }
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
            public void mouseClicked(MouseEvent me) {
                if(jugadores.get(0).getFicha(3).getColor() == Verde){
                    if(getgui().getFichaVerde3().isEnabled() == true){
                        if(jugadores.get(0).getFicha(3).getPosicion() == -1){
                            if(Resultado >= 6){
                                int FichaMontada = 0;
                                for(int i = 0; i <= 3; i++){
                                    if(jugadores.get(0).getFicha(i).getPosicion() == 26){
                                        FichaMontada = 1;
                                    }
                                }
                                
                                if(FichaMontada == 0){
                                Mover_Ficha(26,getgui().getFichaVerde3());
                                jugadores.get(0).getFicha(3).setPosicion(26);
                                jugadores.get(0).getCarcel().setCantidadFichas(jugadores.get(0).getCarcel().getCantidadFichas()-1);
                                siguienteJugador();
                                getgui().mostrarJugadoresEnTabla(getJugadores());
                                getgui().InhabilitarFichas(1);
                                }else{
                                JOptionPane.showMessageDialog(getgui(),"Hay una ficha en la posicion 0, por favor mueva esa ficha primero antes de sacar las otras");
                                }
                            }
                        } else
                            if(jugadores.get(0).getFicha(3).getPosicion() != -1 && Resultado != 0){
                               int Pasos = Resultado + jugadores.get(0).getFicha(3).getPosicion();
                               
                               LimiteFichaColores limite = new LimiteFichaColores();
                               
                               if(Pasos > 24 && jugadores.get(0).getFicha(3).getPosicion() <= limite.LimiteFichaColorVerde){ 
                                 Pasos = (63) + (Resultado-
                                         (limite.LimiteFichaColorVerde-jugadores.get(0).getFicha(3).getPosicion()));
                               }
                               else
                               if(Pasos > 51){ 
                                 Pasos = Resultado - (51 - jugadores.get(0).getFicha(3).getPosicion()) - 1;
                               }
                               
                               int PuedoEstacionarme = mismos_pasos(3, Pasos);
                               
                               if(PuedoEstacionarme == 0){
                                Mover_Ficha(Pasos,getgui().getFichaVerde3());
                                jugadores.get(0).getFicha(3).setPosicion(Pasos);
                                siguienteJugador();
                                getgui().mostrarJugadoresEnTabla(getJugadores());
                                getgui().InhabilitarFichas(1);
                               }else{
                                JOptionPane.showMessageDialog(getgui(),"Una ficha no puede estar encima de otra, turno perdido"); 
                                siguienteJugador();
                                getgui().mostrarJugadoresEnTabla(getJugadores());
                                getgui().InhabilitarFichas(1);
                               }
                               
                            }
                    }
                }
            }
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
    
    public int mismos_pasos(int numero, int pasos){
    int VariableLogica = 0;
        for(int i = 1; i<jugadores.size(); i++){
            for(int j = 0; j<=3; j++)
            if(pasos 
                    == jugadores.get(i).getFicha(j).getPosicion())
            VariableLogica = 1;
        }
        
        for(int i = 0; i<=3; i++){
            if(pasos
                    == jugadores.get(0).getFicha(i).getPosicion() && i != numero)
             VariableLogica = 1;   
        }
        
        return VariableLogica;
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
         if(posicion == 52){ficha.setLocation(390, 220);}
         if(posicion == 53){ficha.setLocation(360, 220);}
         if(posicion == 54){ficha.setLocation(330, 220);}
         if(posicion == 55){ficha.setLocation(300, 220);}
         if(posicion == 56){ficha.setLocation(270, 220);}
         if(posicion == 57){ficha.setLocation(240, 220);}
         if(posicion == 58){ficha.setLocation(210, 400);}
         if(posicion == 59){ficha.setLocation(210, 370);}
         if(posicion == 60){ficha.setLocation(210, 340);}
         if(posicion == 61){ficha.setLocation(210, 310);}
         if(posicion == 62){ficha.setLocation(210, 280);}
         if(posicion == 63){ficha.setLocation(210, 250);}
         if(posicion == 64){ficha.setLocation(30, 220);}
         if(posicion == 65){ficha.setLocation(60, 220);}
         if(posicion == 66){ficha.setLocation(90, 220);}
         if(posicion == 67){ficha.setLocation(120, 220);}
         if(posicion == 68){ficha.setLocation(150, 220);}
         if(posicion == 69){ficha.setLocation(180, 220);}
         if(posicion == 70){ficha.setLocation(210, 40);}
         if(posicion == 71){ficha.setLocation(210, 70);}
         if(posicion == 72){ficha.setLocation(210, 100);}
         if(posicion == 73){ficha.setLocation(210, 130);}
         if(posicion == 74){ficha.setLocation(210, 150);}
         if(posicion == 75){ficha.setLocation(210, 180);}
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
        getgui().getLanzar().setEnabled(true);
        Resultado = 0;
    }
    
    public void RealizarJugada(){
       Resultado = LanzamientoDado();
       JOptionPane.showMessageDialog(getgui(),"Valor resultante: "+Resultado);
       
       if(jugadores.get(0).getCarcel().getCantidadFichas() == 4){
           if(Resultado < 6){
               if(jugadores.get(0).getFicha(0).getColor() == Azul){
                   getgui().InhabilitarFichas(2);
               }
               else if(jugadores.get(0).getFicha(0).getColor() == Amarillo){
                   if(jugadores.size() >= 3){
                      getgui().InhabilitarFichas(3);
                   }
                   else{
                      getgui().InhabilitarFichas(1);
                   }
               }
               else if(jugadores.get(0).getFicha(0).getColor() == Rojo){
                   if(jugadores.size() == 4){
                      getgui().InhabilitarFichas(4);
                   }
                   else{
                      getgui().InhabilitarFichas(1);
                   }
               }
               else if(jugadores.get(0).getFicha(0).getColor() == Verde){
                   getgui().InhabilitarFichas(1);
               }
               siguienteJugador();
               getgui().mostrarJugadoresEnTabla(getJugadores());
           }
       }
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
