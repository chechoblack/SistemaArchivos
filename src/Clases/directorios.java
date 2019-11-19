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
    private usuarios usurioPadre;
    private grupo grupo;
    public directorios(String nombre, int id, directorios directorioPadre,usuarios usurio) {
        this.nombre = nombre;
        this.id = id;
        this.directorioPadre = directorioPadre;
        this.usurioPadre=usurio;
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

    public usuarios getUsurioPadre() {
        return usurioPadre;
    }

    public void setUsurioPadre(usuarios usurioPadre) {
        this.usurioPadre = usurioPadre;
    }

    public grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(grupo grupo) {
        this.grupo = grupo;
    }
    
    public ArrayList<directorios> getListaDirectorios() {
        return listaDirectorios;
    }

    public ArrayList<archivo> getListaArchivos() {
        return listaArchivos;
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
    public void removeArchivo(String nombre){
        for(int i=0;i<listaArchivos.size();i++){
            if(listaArchivos.get(i).getNombre().trim().equals(nombre.trim())){
                System.out.println(listaArchivos.get(i).getNombre());
                listaArchivos.remove(i);
            }
        }       
    }
}
