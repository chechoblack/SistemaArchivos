/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template contenido, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ser
 */
public class archivo {
     private final static double fB = 1024.0;
    private String contenido="Hola mundo";
    private String nombre;
    private float Tamaño=1024;
    private usuarios creador;
    private directorios directorioPadre;
    private String fechaAr;
    private int estado=0;
    private Calendar fecha = new GregorianCalendar();
    private int permiso=7;
    private int permisoGrupo=7; 
    private grupo grupo;
    public archivo(String nombre, usuarios creador,directorios directorio) {
        this.nombre = nombre;
        this.creador = creador;
        this.directorioPadre=directorio;
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        this.fechaAr=dia+"/"+mes+"/"+año;
    }

    public String getContenido() {
        System.out.println(contenido);
        return contenido;
    }

    public void setContenido(String contenido) {
        System.out.println(contenido);
        this.contenido = contenido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getTamaño() throws UnsupportedEncodingException {
        final byte[] utf16Bytes= contenido.getBytes("UTF-16");
        Tamaño=1024+(float)utf16Bytes.length/1000;
        return Tamaño;
    }

    public void setTamaño(int Tamaño) {
        this.Tamaño = Tamaño;
    }

    public usuarios getCreador() {
        return creador;
    }

    public void setCreador(usuarios creador) {
        this.creador = creador;
    }

    public grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(grupo grupo) {
        this.grupo = grupo;
    }
    
    public directorios getDirectorioPadre() {
        return directorioPadre;
    }

    public void setDirectorioPadre(directorios directorioPadre) {
        this.directorioPadre = directorioPadre;
    }

    public String getFechaAr() {
        return fechaAr;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getPermiso() {
        return permiso;
    }

    public void setPermiso(int permiso) {
        this.permiso = permiso;
    }

    public int getPermisoGrupo() {
        return permisoGrupo;
    }

    public void setPermisoGrupo(int permisoGrupo) {
        this.permisoGrupo = permisoGrupo;
    }
    
    @Override
    public String toString() {
         try {
             return "File{\n" + " Nombre= " + nombre +"\n Grupo= "+grupo.getNombre().trim()+ "\n Tama\u00f1o= " + getTamaño() + "\n Dueño= " + creador.getNombreCompleto().trim() + "\n Ubicacion= " + directorioPadre.getNombre() + "\n Fecha= " + fechaAr + "\n Estado 1 abierto/ 0 cerrado= " + estado+"\n" + " \n Permiso: "+permiso+ '}'+"\n";
         } catch (UnsupportedEncodingException ex) {
             Logger.getLogger(archivo.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }
}
