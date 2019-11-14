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

    public grupo() {
    }

    public ArrayList<usuarios> getListaUsuariosGrupo() {
        return listaUsuariosGrupo;
    }

    public ArrayList<archivo> getListaArchivos() {
        return listaArchivos;
    }
    public void setUsuarioGrup(usuarios usuario){
        this.listaUsuariosGrupo.add(usuario);
    }
    public void setArchivoGrup(archivo archi){
        this.listaArchivos.add(archi);
    }
}
