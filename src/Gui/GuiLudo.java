/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Gui;

import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import ludo.Jugador;
import socket.Cliente;
import socket.Conexion;

/**
 *
 * @author Elias Barrientos
 */
public class GuiLudo extends javax.swing.JFrame {
    /**
     * Creates new form GuiLudo
     */
    private Cliente cli;
    private int flag= 0;
    public GuiLudo() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        this.jButtonInicio.setEnabled(false);
        this.jButtonLanzarDados.setEnabled(false);
        this.jLabelDados.setEnabled(false);
        this.jTextFieldDados.setEnabled(false);
        this.jButtonSeleccionarDados.setVisible(false);
        this.FichaRoja.setVisible(false);
        this.FichaRoja1.setVisible(false);
        this.FichaRoja2.setVisible(false);
        this.FichaRoja3.setVisible(false);
        this.FichaAzul.setVisible(false);
        this.FichaAzul1.setVisible(false);
        this.FichaAzul2.setVisible(false);
        this.FichaAzul3.setVisible(false);
        this.FichaAmarilla.setVisible(false);
        this.FichaAmarilla1.setVisible(false);
        this.FichaAmarilla2.setVisible(false);
        this.FichaAmarilla3.setVisible(false);
        this.FichaVerde.setVisible(false);
        this.FichaVerde1.setVisible(false);
        this.FichaVerde2.setVisible(false);
        this.FichaVerde3.setVisible(false);
        this.setVisible(true);
    }
    
    public void setCliente(Cliente cli){
           this.cli=cli; 
           this.flag =1;    
    }
    
    public void mostrarJugadoresEnTabla(ArrayList<Jugador> jugadores) {
        DefaultTableModel modelo = (DefaultTableModel) getTablaJugadores().getModel();
        modelo.setRowCount(0);
        for (Jugador j : jugadores) {
            Object[] row = new Object[10];
            row[0] = j.getNombre();
            row[1] = j.getFicha(0).getPosicion();
            row[2] = j.getFicha(1).getPosicion();
            row[3] = j.getFicha(2).getPosicion();
            row[4] = j.getFicha(3).getPosicion();
            row[5] = j.getFicha(0).getColor();
            modelo.addRow(row);
        }
        getTablaJugadores().setModel(modelo);
        getTablaJugadores().changeSelection(0, 0, false, false);
        centrarDatosTabla(jTableJugadores);
        updateRowHeights(jTableJugadores);
        getTablaJugadores().repaint();
        getTablaJugadores().revalidate();
    }
       
    public final void centrarDatosTabla(JTable t) {
        DefaultTableCellRenderer modeloCentrar = new DefaultTableCellRenderer();
        modeloCentrar.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < t.getColumnCount(); i++) {
            t.getColumnModel().getColumn(i).setCellRenderer(modeloCentrar);
        }
       }
       
    private void updateRowHeights(JTable j) {
        try {
            for (int row = 0; row < j.getRowCount(); row++) {
                int rowHeight = j.getRowHeight();

                for (int column = 0; column < j.getColumnCount(); column++) {
                    Component comp = j.prepareRenderer(j.getCellRenderer(row, column), row, column);
                    rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
                }

                j.setRowHeight(row, (rowHeight + 6));
            }
        } catch (ClassCastException e) {
        }
    }    
       
    public void InhabilitarFichas(int numero){
           if(numero == 1){
             getFichaAmarilla().setEnabled(false);
             getFichaAmarilla1().setEnabled(false);
             getFichaAmarilla2().setEnabled(false);
             getFichaAmarilla3().setEnabled(false);
             getFichaAzul().setEnabled(true);
             getFichaAzul1().setEnabled(true);
             getFichaAzul2().setEnabled(true);
             getFichaAzul3().setEnabled(true);
             getFichaRoja().setEnabled(false);
             getFichaRoja1().setEnabled(false);
             getFichaRoja2().setEnabled(false);
             getFichaRoja3().setEnabled(false);
             getFichaVerde().setEnabled(false);
             getFichaVerde1().setEnabled(false);
             getFichaVerde2().setEnabled(false);
             getFichaVerde3().setEnabled(false);
           }else if(numero == 2){
             getFichaAzul().setEnabled(false);
             getFichaAzul1().setEnabled(false);
             getFichaAzul2().setEnabled(false);
             getFichaAzul3().setEnabled(false);
             getFichaAmarilla().setEnabled(true);
             getFichaAmarilla1().setEnabled(true);
             getFichaAmarilla2().setEnabled(true);
             getFichaAmarilla3().setEnabled(true);
             getFichaRoja().setEnabled(false);
             getFichaRoja1().setEnabled(false);
             getFichaRoja2().setEnabled(false);
             getFichaRoja3().setEnabled(false);
             getFichaVerde().setEnabled(false);
             getFichaVerde1().setEnabled(false);
             getFichaVerde2().setEnabled(false);
             getFichaVerde3().setEnabled(false);
           }else if(numero == 3){
             getFichaAzul().setEnabled(false);
             getFichaAzul1().setEnabled(false);
             getFichaAzul2().setEnabled(false);
             getFichaAzul3().setEnabled(false);
             getFichaRoja().setEnabled(true);
             getFichaRoja1().setEnabled(true);
             getFichaRoja2().setEnabled(true);
             getFichaRoja3().setEnabled(true);
             getFichaAmarilla().setEnabled(false);
             getFichaAmarilla1().setEnabled(false);
             getFichaAmarilla2().setEnabled(false);
             getFichaAmarilla3().setEnabled(false);
             getFichaVerde().setEnabled(false);
             getFichaVerde1().setEnabled(false);
             getFichaVerde2().setEnabled(false);
             getFichaVerde3().setEnabled(false);
           }else if(numero == 4){
             getFichaAzul().setEnabled(false);
             getFichaAzul1().setEnabled(false);
             getFichaAzul2().setEnabled(false);
             getFichaAzul3().setEnabled(false);
             getFichaVerde().setEnabled(true);
             getFichaVerde1().setEnabled(true);
             getFichaVerde2().setEnabled(true);
             getFichaVerde3().setEnabled(true);
             getFichaAmarilla().setEnabled(false);
             getFichaAmarilla1().setEnabled(false);
             getFichaAmarilla2().setEnabled(false);
             getFichaAmarilla3().setEnabled(false);
             getFichaRoja().setEnabled(false);
             getFichaRoja1().setEnabled(false);
             getFichaRoja2().setEnabled(false);
             getFichaRoja3().setEnabled(false);
           }
       }
       
       public void VerFichasAzules(){
            getFichaAzul().setVisible(true);
            getFichaAzul1().setVisible(true);
            getFichaAzul2().setVisible(true);
            getFichaAzul3().setVisible(true);
       }
       
       public void VerFichasAmarillas(){
            getFichaAmarilla().setVisible(true);
            getFichaAmarilla1().setVisible(true);
            getFichaAmarilla2().setVisible(true);
            getFichaAmarilla3().setVisible(true); 
       }
       
       public void VerFichasRojas(){
           getFichaRoja().setVisible(true);
           getFichaRoja1().setVisible(true);
           getFichaRoja2().setVisible(true);
           getFichaRoja3().setVisible(true);
       }
       
       public void VerFichasVerdes(){
          getFichaVerde().setVisible(true);
          getFichaVerde1().setVisible(true);
          getFichaVerde2().setVisible(true);
          getFichaVerde3().setVisible(true);
       }
       
       public JButton SeleccionarDados(){
            return jButtonSeleccionarDados;
       }
       


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButtonRegistrar = new javax.swing.JButton();
        jScrollPaneTablaJugadores = new javax.swing.JScrollPane();
        jTableJugadores = new javax.swing.JTable();
        jButtonInicio = new javax.swing.JButton();
        jButtonLanzarDados = new javax.swing.JButton();
        jTextFieldJugador = new javax.swing.JTextField();
        jLabelNombre = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        FichaRoja = new javax.swing.JLabel();
        FichaRoja1 = new javax.swing.JLabel();
        FichaRoja2 = new javax.swing.JLabel();
        FichaRoja3 = new javax.swing.JLabel();
        FichaAzul = new javax.swing.JLabel();
        FichaAzul1 = new javax.swing.JLabel();
        FichaAzul2 = new javax.swing.JLabel();
        FichaAzul3 = new javax.swing.JLabel();
        FichaAmarilla1 = new javax.swing.JLabel();
        FichaAmarilla = new javax.swing.JLabel();
        FichaAmarilla2 = new javax.swing.JLabel();
        FichaAmarilla3 = new javax.swing.JLabel();
        FichaVerde1 = new javax.swing.JLabel();
        FichaVerde3 = new javax.swing.JLabel();
        FichaVerde2 = new javax.swing.JLabel();
        FichaVerde = new javax.swing.JLabel();
        jLabel = new javax.swing.JLabel();
        jLabelDados = new javax.swing.JLabel();
        jTextFieldDados = new javax.swing.JTextField();
        jButtonSeleccionarDados = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel4.setBackground(new java.awt.Color(102, 255, 102));
        jPanel4.setLayout(null);

        jButtonRegistrar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonRegistrar.setText("Registrar");
        jButtonRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonRegistrar);
        jButtonRegistrar.setBounds(590, 170, 100, 50);

        jTableJugadores.setFont(new java.awt.Font("Comic Sans MS", 0, 11)); // NOI18N
        jTableJugadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Jugador", "PosF1", "PosF2", "PosF3", "PosF4", "Color"
            }
        ));
        jTableJugadores.setColumnSelectionAllowed(true);
        jTableJugadores.setGridColor(new java.awt.Color(0, 102, 0));
        jTableJugadores.setSelectionBackground(new java.awt.Color(255, 204, 102));
        jScrollPaneTablaJugadores.setViewportView(jTableJugadores);

        jPanel4.add(jScrollPaneTablaJugadores);
        jScrollPaneTablaJugadores.setBounds(470, 10, 348, 124);

        jButtonInicio.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonInicio.setText("Inicio");
        jButtonInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInicioActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonInicio);
        jButtonInicio.setBounds(480, 170, 100, 50);

        jButtonLanzarDados.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonLanzarDados.setText("Lanzar Dados");
        jButtonLanzarDados.setToolTipText("");
        jPanel4.add(jButtonLanzarDados);
        jButtonLanzarDados.setBounds(700, 170, 110, 50);

        jTextFieldJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldJugadorActionPerformed(evt);
            }
        });
        jPanel4.add(jTextFieldJugador);
        jTextFieldJugador.setBounds(530, 140, 110, 27);

        jLabelNombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelNombre.setText("Nombre");
        jPanel4.add(jLabelNombre);
        jLabelNombre.setBounds(480, 142, 50, 14);
        jPanel4.add(jLabel2);
        jLabel2.setBounds(480, 320, 0, 0);

        jLabel3.setToolTipText("");
        jPanel4.add(jLabel3);
        jLabel3.setBounds(375, 350, 0, 40);

        jLabel4.setToolTipText("");
        jPanel4.add(jLabel4);
        jLabel4.setBounds(315, 350, 0, 40);

        jLabel6.setToolTipText("");
        jPanel4.add(jLabel6);
        jLabel6.setBounds(345, 380, 0, 40);

        jLabel7.setToolTipText("");
        jPanel4.add(jLabel7);
        jLabel7.setBounds(345, 320, 0, 40);

        jLabel5.setToolTipText("");
        jPanel4.add(jLabel5);
        jLabel5.setBounds(0, 10, 0, 0);

        FichaRoja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Ficha.png"))); // NOI18N
        FichaRoja.setToolTipText("");
        jPanel4.add(FichaRoja);
        FichaRoja.setBounds(345, 115, 40, 30);

        FichaRoja1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Ficha.png"))); // NOI18N
        FichaRoja1.setToolTipText("");
        jPanel4.add(FichaRoja1);
        FichaRoja1.setBounds(345, 55, 40, 30);

        FichaRoja2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Ficha.png"))); // NOI18N
        FichaRoja2.setToolTipText("");
        jPanel4.add(FichaRoja2);
        FichaRoja2.setBounds(375, 85, 40, 30);

        FichaRoja3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/Ficha.png"))); // NOI18N
        FichaRoja3.setToolTipText("");
        jPanel4.add(FichaRoja3);
        FichaRoja3.setBounds(315, 85, 40, 30);

        FichaAzul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/FichaAzul.png"))); // NOI18N
        jPanel4.add(FichaAzul);
        FichaAzul.setBounds(315, 355, 35, 33);

        FichaAzul1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/FichaAzul.png"))); // NOI18N
        jPanel4.add(FichaAzul1);
        FichaAzul1.setBounds(345, 385, 35, 33);

        FichaAzul2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/FichaAzul.png"))); // NOI18N
        jPanel4.add(FichaAzul2);
        FichaAzul2.setBounds(375, 355, 35, 33);

        FichaAzul3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/FichaAzul.png"))); // NOI18N
        jPanel4.add(FichaAzul3);
        FichaAzul3.setBounds(345, 325, 35, 33);

        FichaAmarilla1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/FichaYellow.png"))); // NOI18N
        FichaAmarilla1.setToolTipText("");
        jPanel4.add(FichaAmarilla1);
        FichaAmarilla1.setBounds(105, 355, 33, 32);

        FichaAmarilla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/FichaYellow.png"))); // NOI18N
        FichaAmarilla.setToolTipText("");
        jPanel4.add(FichaAmarilla);
        FichaAmarilla.setBounds(75, 325, 33, 32);

        FichaAmarilla2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/FichaYellow.png"))); // NOI18N
        FichaAmarilla2.setToolTipText("");
        jPanel4.add(FichaAmarilla2);
        FichaAmarilla2.setBounds(75, 385, 33, 32);

        FichaAmarilla3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/FichaYellow.png"))); // NOI18N
        FichaAmarilla3.setToolTipText("");
        jPanel4.add(FichaAmarilla3);
        FichaAmarilla3.setBounds(45, 355, 33, 32);

        FichaVerde1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/FichaVerde.png"))); // NOI18N
        FichaVerde1.setToolTipText("");
        jPanel4.add(FichaVerde1);
        FichaVerde1.setBounds(105, 85, 32, 30);

        FichaVerde3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/FichaVerde.png"))); // NOI18N
        FichaVerde3.setToolTipText("");
        jPanel4.add(FichaVerde3);
        FichaVerde3.setBounds(45, 85, 32, 30);

        FichaVerde2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/FichaVerde.png"))); // NOI18N
        FichaVerde2.setToolTipText("");
        jPanel4.add(FichaVerde2);
        FichaVerde2.setBounds(75, 115, 32, 30);

        FichaVerde.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/FichaVerde.png"))); // NOI18N
        FichaVerde.setToolTipText("");
        jPanel4.add(FichaVerde);
        FichaVerde.setBounds(75, 55, 32, 30);

        jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/LudoIMG.png"))); // NOI18N
        jPanel4.add(jLabel);
        jLabel.setBounds(0, 0, 460, 470);

        jLabelDados.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelDados.setText("Cantidad de Dados");
        jPanel4.add(jLabelDados);
        jLabelDados.setBounds(650, 142, 110, 14);
        jPanel4.add(jTextFieldDados);
        jTextFieldDados.setBounds(760, 140, 40, 27);

        jButtonSeleccionarDados.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonSeleccionarDados.setText("Seleccionar Numero de Dados");
        jPanel4.add(jButtonSeleccionarDados);
        jButtonSeleccionarDados.setBounds(480, 230, 330, 40);

        getContentPane().add(jPanel4);
        jPanel4.setBounds(0, 10, 840, 470);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        try {
           
            if(flag==1)
            this.Salir();
            else System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(GuiLudo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_exitForm

    private void jTextFieldJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldJugadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldJugadorActionPerformed

    private void jButtonInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInicioActionPerformed
      
        //segunda opcion 

    }//GEN-LAST:event_jButtonInicioActionPerformed

    private void jButtonRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRegistrarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiLudo().setVisible(true);
            }
        });
    }
    
    public JButton Iniciar(){
        return jButtonInicio;
    }
    
    public void Salir() throws IOException{
     cli.salir();
    }
    
    public JButton Registrar(){
      return jButtonRegistrar;
    }
    
    public JLabel getFichaRoja(){
      return this.FichaRoja;
    }
    
    public JLabel getFichaRoja1(){
      return this.FichaRoja1;
    }
    
    public JLabel getFichaRoja2(){
      return this.FichaRoja2;
    }
    
    public JLabel getFichaRoja3(){
      return this.FichaRoja3;
    }
    
    public JLabel getFichaAzul(){
      return this.FichaAzul;
    }
    
    public JLabel getFichaAzul1(){
      return this.FichaAzul1;
    }
     
    public JLabel getFichaAzul2(){
      return this.FichaAzul2;
    }
      
    public JLabel getFichaAzul3(){
      return this.FichaAzul3;
    }
    
    public JLabel getFichaAmarilla(){
      return this.FichaAmarilla;
    }
    
    public JLabel getFichaAmarilla1(){
      return this.FichaAmarilla1;
    }
    
    public JLabel getFichaAmarilla2(){
      return this.FichaAmarilla2;
    }
    
    public JLabel getFichaAmarilla3(){
      return this.FichaAmarilla3;
    }
    
    public JLabel getFichaVerde(){
      return this.FichaVerde;
    }
    
    public JLabel getFichaVerde1(){
      return this.FichaVerde1;
    }
    
    public JLabel getFichaVerde2(){
      return this.FichaVerde2;
    }
    
    public JLabel getFichaVerde3(){
      return this.FichaVerde3;
    }
    
    public JTable getTablaJugadores() {
        return this.jTableJugadores;
    }
    
    public JTextField getJTextFieldJugador(){
        return this.jTextFieldJugador;
    }
    
    public JTextField getJTextFieldDados(){
        return this.jTextFieldDados;
    }
    
    public JLabel getJLabelDados(){
        return this.jLabelDados;
    }
    
    public JLabel getJLabelNombre(){
        return this.jLabelNombre;
    }
    
    public JButton getLanzar(){
        return jButtonLanzarDados;
    }
    
    public JLabel getTablero(){
        return jLabel;
    }
    //private javax.swing.JButton jButtonSalir;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel FichaAmarilla;
    private javax.swing.JLabel FichaAmarilla1;
    private javax.swing.JLabel FichaAmarilla2;
    private javax.swing.JLabel FichaAmarilla3;
    private javax.swing.JLabel FichaAzul;
    private javax.swing.JLabel FichaAzul1;
    private javax.swing.JLabel FichaAzul2;
    private javax.swing.JLabel FichaAzul3;
    private javax.swing.JLabel FichaRoja;
    private javax.swing.JLabel FichaRoja1;
    private javax.swing.JLabel FichaRoja2;
    private javax.swing.JLabel FichaRoja3;
    private javax.swing.JLabel FichaVerde;
    private javax.swing.JLabel FichaVerde1;
    private javax.swing.JLabel FichaVerde2;
    private javax.swing.JLabel FichaVerde3;
    private javax.swing.JButton jButtonInicio;
    private javax.swing.JButton jButtonLanzarDados;
    private javax.swing.JButton jButtonRegistrar;
    private javax.swing.JButton jButtonSeleccionarDados;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelDados;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPaneTablaJugadores;
    private javax.swing.JTable jTableJugadores;
    private javax.swing.JTextField jTextFieldDados;
    private javax.swing.JTextField jTextFieldJugador;
    // End of variables declaration//GEN-END:variables

}
