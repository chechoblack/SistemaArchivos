/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author ser
 */
public class archivo {
    private String contenido="Hola mundo";
    private String nombre;
    private int Tamaño=0;
    private usuarios crador;
    private directorios directorioPadre;
    public archivo(String nombre, usuarios crador,directorios directorio) {
        this.nombre = nombre;
        this.crador = crador;
        this.directorioPadre=directorio;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTamaño() {
        return Tamaño;
    }

    public void setTamaño(int Tamaño) {
        this.Tamaño = Tamaño;
    }

    public usuarios getCrador() {
        return crador;
    }

    public void setCrador(usuarios crador) {
        this.crador = crador;
    }

    public directorios getDirectorioPadre() {
        return directorioPadre;
    }

    public void setDirectorioPadre(directorios directorioPadre) {
        this.directorioPadre = directorioPadre;
    }
    
}
