/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author ser
 */
public class discoDuro {

    int tamañoDisco;
    String Acceso = "miDiscoDuro.fs";
    int Variable= 256;
    int CantidadBloques;
    
    public discoDuro(int tamañoDisco) {
        this.tamañoDisco=tamañoDisco;
    }

    public int getTamañoDisco() {
        return tamañoDisco;
    }
    
    public void CreacionDisco() throws FileNotFoundException, IOException{
        RandomAccessFile f = new RandomAccessFile(Acceso, "rw");
        f.setLength(tamañoDisco);
        f.close();
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
    
    //al iniciar y utilizar format se asgina el usuario root
    //esto se hace en bloque 0, ya que este es el que se va a 
    //utilizar para guardar los daos de usuarios y grupos
    public void CrearUsuarioRoot() throws FileNotFoundException, IOException{
        RandomAccessFile raf = new RandomAccessFile(Acceso, "rw");
        String Usuario="root";
        String Nombre="Andrey";
        String Contraseña="1234root";
        String IdUsuario="1";
        raf.seek(2);
        raf.writeBytes(","+"["+Usuario+","+Contraseña+","+Nombre+","+IdUsuario+"]");
        raf.close();
    }
    
}
