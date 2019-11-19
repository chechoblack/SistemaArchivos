/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Clases.lectorComandos;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ser
 */
public class Console extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public int posicionCursor=0;
    public boolean banderaSU= false;
    public boolean banderaUser= false;
    public boolean banderaPassword= false;
    public boolean banderaPoweroff= false;
    public int contUser=0,contPas=0;
    private lectorComandos lectura=new lectorComandos();
    public String usuario="";
    public String nombreUsuario="";
    public String ruta="Comando>";
    public String respaldo="";
    public Console() {
        initComponents();
        escribirMensaje("AA [Versión 0.1](c) 2019 AA Corporation. Todos los derechos reservados.\n");
        escribirMensaje(ruta);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtAConsole = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtAConsole.setBackground(new java.awt.Color(0, 0, 0));
        txtAConsole.setColumns(20);
        txtAConsole.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtAConsole.setForeground(new java.awt.Color(255, 255, 255));
        txtAConsole.setRows(5);
        txtAConsole.setCaretColor(new java.awt.Color(255, 255, 255));
        txtAConsole.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtAConsole.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAConsoleKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(txtAConsole);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAConsoleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAConsoleKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(!banderaSU && !banderaUser && !banderaPassword && !banderaPoweroff){
                if(!getComando().trim().equals("")){
                    lectura.setTextConsola(getComando());
                    try {
                        lectura.parseoTexto();
                    } catch (IOException ex) {
                        Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    escribirMensaje(ruta);
                }
            }
            else if(banderaSU){
                lectura.funcionSuAux(getComando());
            }
            else if(banderaUser){
                lectura.funcionUserAux(contUser,getComando());
            }
            else if(banderaPassword){
                lectura.funcionPasswordAux(contPas,getComando());
            }
            else if(banderaPoweroff){
                lectura.funcionPoweroffAux(getComando());
            }
        }
        else if(evt.getKeyCode()==8){
            if(this.posicionCursor>txtAConsole.getText().length()){
                txtAConsole.setText(" ");
                txtAConsole.setText(respaldo);
            }
        }
    }//GEN-LAST:event_txtAConsoleKeyReleased
    public void escribirMensaje(String mensaje){
        txtAConsole.setText(txtAConsole.getText()+mensaje);
        respaldo=txtAConsole.getText();
        this.posicionCursor=txtAConsole.getText().length();
        txtAConsole.setCaretPosition(posicionCursor);
    }
    public String getComando(){
        String comando=txtAConsole.getText().substring(posicionCursor, txtAConsole.getText().length());
        return comando;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Console.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Console.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Console.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Console.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Console().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtAConsole;
    // End of variables declaration//GEN-END:variables
}
