/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import socket.Conexion;

public class Cliente extends Conexion
{
    public Cliente() throws IOException{super("cliente"); } //Se usa el constructor para cliente de Conexion
     private Scanner entrada=new Scanner(System.in);
      private String st = "",s2="";
     private int menu=0;
     private int maquina;
     private DataInputStream bufferDeEntrada = null;
    public void startClient(String nombre) //Método para iniciar el cliente
    {
        try
        { 
            //Flujo de datos hacia el servidor
                  
                        
                            System.out.println("Mensaje enviado solicitando conexion al servidor \n");
                            //Se escribe en el servidor usando su flujo de datos
                            salidaServidor.writeUTF("conexion "+nombre+"\n");
                        


                           recibirRespuestasServidor();
        }       
                   
                 
        
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
     public void enviarmensaje(String mensaje) throws IOException{
         salidaServidor.writeUTF(mensaje+"\n");
         recibirRespuestasServidor();
         
     }
     
    public void salir() throws IOException{
      salidaServidor.writeUTF("salir"+maquina+"\n");
                   /*salidaServidor.writeUTF("Maquina desconectada exitosamente" + "\n");*/
                  // cs.close();//Fin de la conexión
                   System.out.println("Fin de la conexión");                   
        System.exit(0);
    }
    public void empezarFlujo() throws IOException{
    salidaServidor = new DataOutputStream(cs.getOutputStream());
    bufferDeEntrada = new DataInputStream(cs.getInputStream());
    }
    public void recibirRespuestasServidor() throws IOException{
        //Se muestra por pantalla el mensaje recibido
              st = (String) bufferDeEntrada.readUTF();
               System.out.println("Respuesta del servidor:  "+st);
               if(st.length()>=10)
               if(st.substring(0,9).equals("conectado"))
                   this.maquina=Integer.parseInt(st.substring(9));
               System.out.println("su maquina es: "+maquina);
               st="";
    }
}