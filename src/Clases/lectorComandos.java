/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import java.util.ArrayList;
import static sistemaarchivo.myFileSystem.ventana;

/**
 *
 * @author ser
 */
public class lectorComandos {
    private String textConsola="";
    private boolean banderaPassword=false;
    private boolean banderaIngresoPassword=false;
    private ArrayList usuarioTemp = new ArrayList();
    private ArrayList<usuarios> listaUsuarios = new ArrayList<>();
    public lectorComandos() {
    }

    public String getTextConsola() {
        return textConsola;
    }

    public void setTextConsola(String textConsola) {
        this.textConsola = textConsola;
    }
    public void parseoTexto(){
        String textoParseado=(String) cadenaEntrada().get(0);
        if("format".equals(textoParseado.trim()) && cadenaEntrada().size()==3){
            funcionFormat();
        }
        else if("useradd".equals(textoParseado.trim()) && cadenaEntrada().size()==2){
            funcionUser();
        }
        else if("groupadd".equals(textoParseado.trim())){
        
        }
        else if("passwd".equals(textoParseado.trim())){
        
        }
        else if("su".equals(textoParseado.trim())){
            funcionSu();
        }
        else if("whoami".equals(textoParseado.trim())){
        
        }
        else if("pwd".equals(textoParseado.trim())){
        
        }
        else if("mkdir".equals(textoParseado.trim())){
        
        }
        else if("rm".equals(textoParseado.trim())){
        
        }
        else if("mv".equals(textoParseado.trim())){
        
        }
        else if("ls".equals(textoParseado.trim())){
        
        }
        else if("cd".equals(textoParseado.trim())){
        
        }
        else if("ln".equals(textoParseado.trim())){
        
        }
        else if("touch".equals(textoParseado.trim())){
        
        }
        else if("cat".equals(textoParseado.trim())){
        
        }
        else if("chown".equals(textoParseado.trim())){
        
        }
        else if("chgrp".equals(textoParseado.trim())){
        
        }
        else if("chmod".equals(textoParseado.trim())){
        
        }
        else if("openFile".equals(textoParseado.trim())){
        
        }
        else if("closeFile".equals(textoParseado.trim())){
        
        }
        else if("viewFilesOpen".equals(textoParseado.trim())){
        
        }
        else if("viewFCB".equals(textoParseado.trim())){
        
        }
        else if("infoFS".equals(textoParseado.trim())){
        
        }
        else if("usermod".equals(textoParseado.trim())){
        
        }
        else if("poweroff".equals(textoParseado.trim())){
        
        }
        else{
            ventana.escribirMensaje("Comando no existente\n");
            ventana.ruta="C:\\"+ventana.usuario+">";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    private void funcionSu(){
        /**/
        if(!banderaPassword){
            ArrayList textoParseado=cadenaEntrada();
            /*aqui valida que usuario es el que esta entrando*/
            if(!banderaIngresoPassword && textoParseado.size()==1 && textoParseado.get(0).equals("su")){
                ventana.escribirMensaje(" Password root: ");
                banderaIngresoPassword=true;
                ventana.banderaSU=true;
            }
            else{
                System.out.println("segundo");
                ventana.escribirMensaje(" Password root: ");
                ventana.banderaSU=true;
            }
        }
    }
    public void funcionSuAux(String contra){
        if(banderaIngresoPassword){
            /*aqui se valia el usuario root*/
            if(contra.trim().equals("anderson123")){
                banderaPassword=true;
                ventana.banderaSU=false;
                ventana.usuario="root";
                ventana.ruta="C:\\"+ventana.usuario+">";
                ventana.escribirMensaje( ventana.ruta);
            }
            else{
                ventana.escribirMensaje(" Error, Contraseña incorrecta\n");
                ventana.escribirMensaje(" Password root: ");
            }
        }
        else{
            /*
            aqui se valida los usuarios que no sean root
            */
            System.out.println(cadenaEntrada().get(0));
            System.out.println(cadenaEntrada().get(1));
            System.out.println("Contraseña "+contra);
            ventana.banderaSU=false;
        }
    }
    private void funcionFormat(){
        /*
        valida format
        */
        if(ventana.usuario.equals("root")){
            ventana.escribirMensaje("Creado Disco.....\n");
            ventana.escribirMensaje("Disco Creado\n");
            ventana.ruta="C:\\root>";
            ventana.escribirMensaje(ventana.ruta);
        }else{
            ventana.escribirMensaje("Creado Disco.....\n");
            ventana.escribirMensaje("Disco Creado\n");
            ventana.ruta="C:\\"+ventana.usuario+">";
            ventana.escribirMensaje(ventana.ruta);
        }
    }
    private void funcionUser(){
        if(ventana.usuario.equals("root")){
            usuarioTemp.clear();
            ventana.escribirMensaje(" Nombre completo: ");
            ventana.banderaUser=true;
            ventana.contUser=1;
            usuarioTemp.add(cadenaEntrada().get(1));
        }
        else{
            ventana.escribirMensaje("Debe ingresar como usuario root para crear un usuario\n");
            ventana.ruta="C:\\"+ventana.usuario+">";
            ventana.escribirMensaje(ventana.ruta);
        }
    }
    public void funcionUserAux(int cont,String comando){
        System.out.println("tamaño lista: "+usuarioTemp.size());
        if(cont==1){
            //aqui se agrega el nombre
            ventana.escribirMensaje(" Contraseña: ");
            ventana.contUser=2;
        }
        else if(cont==2){
            usuarioTemp.add(comando);//se obtiene la contraseña del usuario que se agrega
            ventana.escribirMensaje(" Confirmación de Contraseña: ");
            ventana.contUser=3;
        }
        else{
            if(usuarioTemp.get(1).equals(comando)){
                ventana.banderaUser=false;
                usuarios nuevoUsuario = new usuarios(usuarioTemp.get(0).toString(), usuarioTemp.get(1).toString(), usuarioTemp.get(2).toString());
                listaUsuarios.add(nuevoUsuario);
                ventana.ruta="C:\\"+ventana.usuario+">";
                ventana.escribirMensaje(ventana.ruta);
            }else{
                ventana.escribirMensaje(" Contraseña incorrecta ");
                ventana.escribirMensaje(" Confirmación de Contraseña: ");
                ventana.contUser=3;
            }
        }
    }
    private ArrayList cadenaEntrada(){
        String[] textoParseado=textConsola.trim().split(" ");
        ArrayList<String> textoCOmpletoP = new ArrayList();
        for(int i=0;i<textoParseado.length;i++){
            if(!textoParseado[i].equals("")){
                textoCOmpletoP.add(textoParseado[i]);
            }
        }
        return textoCOmpletoP;
    }
}
