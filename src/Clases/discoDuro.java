/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Clases.grupo;
import Clases.usuarios;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ser
 */
public class discoDuro {

    int tamañoDisco;
    int tamañoActual;
    String Acceso = "miDiscoDuro.fs";
    int Variable= 256;//tamaño bloque
    int CantidadBloques;
    
    //datos actuales
    private ArrayList<Clases.usuarios>ListaUsuarios=new ArrayList();
    private ArrayList<Clases.grupo>ListaGrupos=new ArrayList();
    int IDactualUsuarios;
    int IDactualGrupos;
    //
    
    public discoDuro(int tamañoDisco) {
        this.tamañoDisco=tamañoDisco;
    }

    public ArrayList<usuarios> getListaUsuarios() {
        return ListaUsuarios;
    }

    public void setListaUsuarios(ArrayList<usuarios> ListaUsuarios) {
        this.ListaUsuarios = ListaUsuarios;
    }

    public ArrayList<grupo> getListaGrupos() {
        return ListaGrupos;
    }

    public void setListaGrupos(ArrayList<grupo> ListaGrupos) {
        this.ListaGrupos = ListaGrupos;
    }

    public int getTamañoDisco() {
        return tamañoDisco;
    }

    public void setTamañoDisco(int tamañoDisco) {
        this.tamañoDisco = tamañoDisco;
    }

    public int getTamañoActual() {
        return tamañoActual;
    }

    public void setTamañoActual(int tamañoActual) {
        this.tamañoActual = tamañoActual;
    }
    
    public void CreacionDisco() throws FileNotFoundException, IOException{
      if(!new File(Acceso).exists()){
       System.out.println("No existia");
       RandomAccessFile f = new RandomAccessFile(Acceso, "rw");
       f.setLength(tamañoDisco);
       f.close();
      }
      System.out.println("Existe");
    }
    public void Lectura() throws FileNotFoundException, IOException{
     RandomAccessFile raf = new RandomAccessFile(Acceso, "rw");
     raf.seek(0);
     System.out.println(raf.readLine());
     raf.close();
    }
    
    public void LecturaEspecifica(int bloqueEspecifico) throws FileNotFoundException, IOException{
     RandomAccessFile raf = new RandomAccessFile(Acceso, "rw");
     for(int x=0;x<256;x++){
         raf.seek((Variable*bloqueEspecifico)+x);
         System.out.println(Character.toString((char) raf.read()));
     }
     raf.close();
    }
    
    public void Escritura() throws FileNotFoundException, IOException{
    RandomAccessFile raf = new RandomAccessFile(Acceso, "rw");
    String b1="15";
    raf.seek(1);
    raf.writeBytes(b1);
    raf.close();
    }
    
    public void EscribirBloque() throws FileNotFoundException, IOException{
    RandomAccessFile raf = new RandomAccessFile(Acceso, "rw");
    String abrir="[";
    String Cerrar="]";
    raf.seek(Variable*2);
    raf.writeBytes(abrir);
    raf.close();
    }
    
    public void EscribirInicioBloque() throws FileNotFoundException, IOException{
    RandomAccessFile raf = new RandomAccessFile(Acceso, "rw");
    String abrir="[";
    CantidadBloques=tamañoDisco/256;
    for(int x=0;x<CantidadBloques;x++){
      raf.seek(Variable*x);
      raf.writeBytes(abrir);
      raf.seek((Variable*x)+1);
      raf.writeBytes(String.valueOf(x));
    }
    raf.close();
    }
    
    //se seleccionar un bloque de los existentes y se eliminar su contenido
    public void EliminarBloqueEspecifico(int bloqueEspecifico) throws FileNotFoundException, IOException{
     RandomAccessFile raf = new RandomAccessFile(Acceso, "rw");
     for(int x=2;x<256;x++){
         raf.seek((Variable*bloqueEspecifico)+x);
         raf.writeBytes("0");
     }
     raf.close();
    }
    
    public void CrearUsuarioRootyHome() throws FileNotFoundException, IOException{
     RandomAccessFile raf = new RandomAccessFile(Acceso, "rw");
     raf.seek(2);
     raf.close();
    }
    
    //formatear los datos del disco duro
    public void FormatearDiscoDuro() throws FileNotFoundException, IOException{
     RandomAccessFile raf = new RandomAccessFile(Acceso, "rw");
     for(int x=0;x<tamañoDisco;x++){
       raf.seek(x);
       raf.writeBytes("0");
     }
     raf.close();
    }
    
    //carga a java(memoria) los datos de usuarios, grupos y numeros de id actuales 
    public void CargarDatosPrimerBloque() throws IOException{
     RandomAccessFile raf = new RandomAccessFile(Acceso, "rw");
      
      int BanderaUsuarios=0;
      int BanderaGrupos=0;
      int BanderaID=0;
      
      String DatosUsuarios="[";
      String DatosGrupos="[";
      String DatosID="[";
      
      for(int x=0;x<256;x++){
          
         raf.seek(x);
         
         if(!Character.toString((char) raf.read()).equals("#") && BanderaUsuarios==0 &&
                 BanderaGrupos==0 && BanderaID==0){
           BanderaUsuarios=1;
         }
         if(Character.toString((char) raf.read()).equals("#") && BanderaUsuarios==1 && 
                 BanderaGrupos==0 && BanderaID==0){
           BanderaUsuarios=0;
           BanderaGrupos=1;
         }
         if(Character.toString((char) raf.read()).equals("#") && BanderaUsuarios==0 &&
                 BanderaGrupos==1 && BanderaID==0){
          BanderaGrupos=0;
          BanderaID=1;
         }
         if(BanderaID==1 && Character.toString((char) raf.read()).equals("#")){
          BanderaID=0;
          x=x+256;
         }
         if(BanderaUsuarios==1){
           DatosUsuarios=DatosUsuarios+Character.toString((char) raf.read());
         }
         if(BanderaGrupos==1){
           DatosGrupos=DatosGrupos+Character.toString((char) raf.read());
         }
         if(BanderaID==1){
           DatosID=DatosID+Character.toString((char) raf.read());
         }

     }
     raf.close();
     
     usuarios Temporal= new usuarios("","","");
     //for para obtener todos los usuarios
     DatosUsuarios=DatosUsuarios.replace(DatosUsuarios.substring(DatosUsuarios.length()-1), "");
     DatosUsuarios=DatosUsuarios.replace(DatosUsuarios.substring(DatosUsuarios.length()-1), "");
      String[] output = DatosUsuarios.split("\\]");
      for(int x=0;x<output.length;x++){
        //System.out.println("Este es dato grande "+output[x]);
        String[] output2=output[x].split("\\,");
        String Usuario = output2[0].replace("[",""); 
        Temporal.setNombreUsuario(Usuario);
        //System.out.println("Esto es "+output2[1]);
        Temporal.setPassword(output2[1]);
        Temporal.setNombreCompleto(output2[2]);
        String Contraseña= output2[3].replace("]","");
        Contraseña= Contraseña.replace("#","");
        Temporal.setId(Integer.parseInt(Contraseña));
        ListaUsuarios.add(Temporal);
      }
     
     grupo Grupos= new grupo("");
     System.out.println(DatosGrupos);
     DatosGrupos=DatosGrupos.replace(DatosGrupos.substring(DatosGrupos.length()-1), "");
     //for para obtener todos los grupos
     String[] output3 = DatosGrupos.split("\\]");
     for(int x=0;x<output3.length;x++){
       System.out.println(output3[x]);
       String[] output4=output3[x].split("\\,");
       String Nombre = output4[0].replace("[","");
       Grupos.setNombre(Nombre);
        System.out.println(Integer.parseInt(output4[1]));
       Grupos.setId(Integer.parseInt(output4[1]));
       String Lista = output4[2].replace("#","");
       List<String> myList = new ArrayList<String>(Arrays.asList(Lista.split(",")));
       for(int a=0;a<myList.size();a++){
            for(int i=0;i<ListaUsuarios.size();i++){
              if(String.valueOf(ListaUsuarios.get(i).getId()).equals(myList.get(a))){
                Grupos.getListaUsuariosGrupo().add(ListaUsuarios.get(i));
              }
           }
       }
       //Grupos.setListaUsuarios((ArrayList<String>) myList);
       ListaGrupos.add(Grupos);
     }
     
     DatosID=DatosID.replace("#", "");
     System.out.println(DatosID);
     String[] output5 = DatosID.split("\\,");
     String Usuarios = output5[0].replace("[", "");
     IDactualUsuarios=Integer.parseInt(Usuarios);
     String SGrupos = output5[1].replace("]", "");
     SGrupos = SGrupos.replace("#", "");
     IDactualGrupos=Integer.parseInt(SGrupos);
     
    }
    //USERADD
    public void AgregarUsuario(usuarios usuario) throws FileNotFoundException, IOException{
      IDactualUsuarios=IDactualUsuarios+1;
      usuario.setId(IDactualUsuarios);
      ListaUsuarios.add(usuario);
      AgregarDatosEnMemoriaADisco();
    }
    //GROUPADD
    public void AgregarGrupo(grupo grupo) throws IOException{
      IDactualGrupos=IDactualGrupos+1;
      grupo.setId(IDactualGrupos);
      ListaGrupos.add(grupo);
      AgregarDatosEnMemoriaADisco();
    }
    //PASSWD
    public void CambiarPasswordUsuario(String NombreUsuario,String ContraseñaNueva) throws IOException{
     for(int x=0;x<ListaUsuarios.size();x++){
        if(ListaUsuarios.get(x).getNombreUsuario().equals(NombreUsuario)){
          ListaUsuarios.get(x).setPassword(ContraseñaNueva);
        }
      }
     AgregarDatosEnMemoriaADisco();
    }
    
    //Todos los datos que estan en java los meto al disco para actualizar los datos, 
    //datos del primer bloque
    public void AgregarDatosEnMemoriaADisco() throws FileNotFoundException, IOException{
      RandomAccessFile raf = new RandomAccessFile(Acceso, "rw");
      String DatoParaIngresar="";
      for(int x=0;x<ListaUsuarios.size();x++){
       DatoParaIngresar=DatoParaIngresar+"["+ListaUsuarios.get(x).getNombreUsuario()+","+
               ListaUsuarios.get(x).getNombreCompleto()+","+ListaUsuarios.get(x).getPassword()+
               ","+ListaUsuarios.get(x).getId()+"]";
      }
      DatoParaIngresar=DatoParaIngresar+"#";
      for(int x=0;x<ListaGrupos.size();x++){
           DatoParaIngresar=DatoParaIngresar+"["+ListaGrupos.get(x).getNombre()+
                ","+ListaGrupos.get(x).getId()+",";
           for(int i=0;i<ListaGrupos.get(x).getListaUsuariosGrupo().size();i++){
             DatoParaIngresar=DatoParaIngresar+ListaGrupos.get(i).getListaUsuariosGrupo().get(i).getId()+",";
           }
           DatoParaIngresar=DatoParaIngresar.substring(0,DatoParaIngresar.length()-1);
           //DatoParaIngresar=DatoParaIngresar.replace(DatoParaIngresar.substring(DatoParaIngresar.length()-1), "");
           DatoParaIngresar=DatoParaIngresar+"]"+"]";
      }
      DatoParaIngresar=DatoParaIngresar+"#";
      DatoParaIngresar=DatoParaIngresar+"["+String.valueOf(IDactualUsuarios)+","+
              String.valueOf(IDactualGrupos)+"]"+"#";
      
      raf.seek(2);
      raf.writeBytes(DatoParaIngresar);
      raf.close();
    }
    
    public void ImprimirDatosMemoria(){
     for(int x=0;x<ListaUsuarios.size();x++){
         System.out.println(ListaUsuarios.get(x).getNombreCompleto());
          System.out.println(ListaUsuarios.get(x).getId());
        }
        for(int x=0;x<ListaGrupos.size();x++){
         System.out.println(ListaGrupos.get(x).getNombre());
         System.out.println(ListaGrupos.get(x).getId());
        }
      
      System.out.println("-------------------");
      System.out.println(IDactualUsuarios);
      System.out.println(IDactualGrupos);
    }
    
    //al iniciar y utilizar format se asgina el usuario root
    //esto se hace en bloque 0, ya que este es el que se va a 
    //utilizar para guardar los daos de usuarios y grupos
    //se crea un grupo root tambien
    public void CrearUsuarioRootyGrupoRoot() throws FileNotFoundException, IOException{
      RandomAccessFile raf = new RandomAccessFile(Acceso, "rw");
      String Usuario="Root";
      String Nombre="Usuario";
      String Contraseña="1234";
      String IDusuario="1";
      String NombreGrupo="Grupo1";
      String IDgrupo="1";
      ArrayList<String>GrupoRoot=new ArrayList();
      GrupoRoot.add(IDusuario);
      
      raf.seek(2);
      raf.writeBytes("["+Usuario+","+Contraseña+","+Nombre+","+IDusuario+"]"+"#"+
              "["+NombreGrupo+","+IDgrupo+","+GrupoRoot+"]"+"#"+"["+IDusuario+","+IDgrupo+"]"+"#");
      raf.close();
    }
    
}
