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
public class grupo {
    private ArrayList<usuarios> listaUsuariosGrupo = new ArrayList<>();
    private ArrayList<archivo> listaArchivos = new ArrayList<>();
    private ArrayList<directorios> listaDirectorios = new ArrayList<>();
    private String nombre;
    private int permiso=7;
    private int id;
    public grupo(String nombre) {
        this.nombre=nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public ArrayList<usuarios> getListaUsuariosGrupo() {
        return listaUsuariosGrupo;
    }

    public ArrayList<archivo> getListaArchivos() {
        return listaArchivos;
    }

    public ArrayList<directorios> getListaDirectorios() {
        return listaDirectorios;
    }
    
    public void setUsuarioGrup(usuarios usuario){
        this.listaUsuariosGrupo.add(usuario);
    }
    
    public void setArchivoGrup(archivo archi){
        this.listaArchivos.add(archi);
    }
    
    public void setDirectorioGrup(directorios directorio){
        this.listaDirectorios.add(directorio);
    }

    public int getPermiso() {
        return permiso;
    }

    public void setPermiso(int permiso) {
        this.permiso = permiso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
