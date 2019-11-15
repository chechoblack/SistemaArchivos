/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;

/**
 *
 * @author ser
 */
public class directorios {
    private String nombre;
    private int id;
    private ArrayList<directorios> listaDirectorios = new ArrayList();
    private ArrayList<archivo> listaArchivos=new ArrayList<>();
    private directorios directorioPadre;

    public directorios(String nombre, int id, directorios directorioPadre) {
        this.nombre = nombre;
        this.id = id;
        this.directorioPadre = directorioPadre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public directorios getDirectorioPadre() {
        return directorioPadre;
    }

    public void setDirectorioPadre(directorios directorioPadre) {
        this.directorioPadre = directorioPadre;
    }
    public void setDirectorioHijo(directorios directorio){
        this.listaDirectorios.add(directorio);
    }
    public void setArchivo(archivo archivo){
        this.listaArchivos.add(archivo);
    }
    public void removeDirectorio(String nombre){
        for(int i=0;i<listaDirectorios.size();i++){
            if(listaDirectorios.get(i).getNombre().trim().equals(nombre.trim())){
                listaDirectorios.remove(i);
            }
        }
    }
}
