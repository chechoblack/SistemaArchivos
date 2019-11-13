/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import static sistemaarchivo.myFileSystem.ventana;

/**
 *
 * @author ser
 */
public class lectorComandos {
    private String textConsola="";

    public lectorComandos() {
    }

    public String getTextConsola() {
        return textConsola;
    }

    public void setTextConsola(String textConsola) {
        this.textConsola = textConsola;
    }
    public void parseoTexto(){
        String[] textoParseado=textConsola.split(" ");
        if("format".equals(textoParseado[0].trim())){
            ventana.escribirMensaje("Creado Disco.....\n");
            ventana.escribirMensaje("Disco Creado\n");
            ventana.ruta="C:\\root>";
            ventana.escribirMensaje(ventana.ruta);
        }else{
            ventana.escribirMensaje("Comando no existente");
            ventana.ruta="C:\\root>";
            ventana.escribirMensaje(ventana.ruta);
        }
    }
}
