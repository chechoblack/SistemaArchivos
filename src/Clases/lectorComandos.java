/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import java.io.IOException;
import java.util.ArrayList;
import static sistemaarchivo.myFileSystem.ventana;

/**
 *
 * @author ser
 */
public class lectorComandos {
    private String textConsola="",nombreDisco="";
    private String ClaveRootDefault="";
    private boolean banderaPassword=false;
    private boolean banderaIngresoPassword=false;
    private ArrayList usuarioTemp = new ArrayList();
    private ArrayList ContraTemp=new ArrayList();
    private ArrayList<usuarios> listaUsuarios = new ArrayList<>();
    private ArrayList<grupo> listaGrupos = new ArrayList<>();
    private ArrayList<String> listaUbicaion = new ArrayList<>();
    private funcionalidadPrincipal disco;
    public lectorComandos() {
    }

    public String getTextConsola() {
        return textConsola;
    }

    public void setTextConsola(String textConsola) {
        this.textConsola = textConsola;
    }
    public void parseoTexto() throws IOException{
        String textoParseado=(String) cadenaEntrada().get(0);
        if("format".equals(textoParseado.trim()) && cadenaEntrada().size()==3){
            funcionFormat();
        }
        else if("useradd".equals(textoParseado.trim()) && cadenaEntrada().size()==2){
            funcionUser();
        }
        else if("groupadd".equals(textoParseado.trim())&& cadenaEntrada().size()==2){
            funcionGroupadd();
        }
        else if("passwd".equals(textoParseado.trim()) && cadenaEntrada().size()==2){
            funcionPassword();
        }
        else if("su".equals(textoParseado.trim())){
            funcionSu();
        }
        else if("whoami".equals(textoParseado.trim()) && cadenaEntrada().size()==1){
            funcionWhoami();
        }
        else if("pwd".equals(textoParseado.trim())){
            funcionPwd();
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
            funcionUsermod();
        }
        else if("poweroff".equals(textoParseado.trim())){
        
        }
        else{
            ventana.escribirMensaje("Comando no existente\n");
            ventana.ruta=pathSistema()+">";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * funcion principal de su
     */
    private void funcionSu(){
        /**/
        if(!banderaPassword && !ClaveRootDefault.equals("")){
            ArrayList textoParseado=cadenaEntrada();
            /*aqui valida que usuario es el que esta entrando*/
            if(!banderaIngresoPassword && textoParseado.size()==1 && textoParseado.get(0).equals("su")){
                ventana.escribirMensaje(" Password root: ");
                banderaIngresoPassword=true;
                ventana.banderaSU=true;
            }
            else{
                ventana.escribirMensaje(" Password user: ");
                ventana.banderaSU=true;
            }
        }
        else{
            ventana.escribirMensaje(" Error, no se ha creado ningun root\n");
            ventana.ruta=pathSistema()+">";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * funcion auxiliar de su
     * @param contra 
     */
    public void funcionSuAux(String contra){
        if(banderaIngresoPassword){
            /*aqui se valia el usuario root*/
            if(contra.trim().equals(ClaveRootDefault)){
                ventana.banderaSU=false;
                ventana.usuario="root";
                banderaIngresoPassword=false;
                ventana.ruta=pathSistema()+">";
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
            if(verificarUsuarioPassword((String) cadenaEntrada().get(1))!=null){
                usuarios usuario=verificarUsuarioPassword((String) cadenaEntrada().get(1));
                if(usuario.getPassword().trim().equals(contra.trim())){
                    System.out.println(cadenaEntrada().get(1).toString());
                    ventana.usuario=cadenaEntrada().get(1).toString();
                    ventana.nombreUsuario=usuario.getNombreCompleto();
                    ventana.ruta=pathSistema()+">";
                    ventana.escribirMensaje(ventana.ruta);
                    ventana.banderaSU=false;
                    ventana.banderaPassword=false;
                }
                else{
                    ventana.escribirMensaje(" Error, Contraseña incorrecta\n");
                    ventana.escribirMensaje(" Password root: ");
                }
            }
            else{
                ventana.escribirMensaje(" Error, Usuario no existente\n");
                ventana.ruta=pathSistema()+">";
                ventana.escribirMensaje( ventana.ruta);
            }
        }
    }
    /**
     * funcion principal de format
     */
    private void funcionFormat() throws IOException{
        /*
        valida format
        */
        nombreDisco="miDiscoDuro";
        String tamaño = cadenaEntrada().get(1).toString();
        disco=new funcionalidadPrincipal((1024*1000)*Integer.parseInt(tamaño));
        disco.CreacionDisco();
        disco.EscribirInicioBloque();
        ClaveRootDefault=cadenaEntrada().get(2).toString();
        usuarios root=new usuarios("Root","rot",ClaveRootDefault);
        listaUsuarios.add(root);
        ventana.ruta=pathSistema()+">";
        ventana.escribirMensaje( ventana.ruta);
    }
    /**
     * funcion principal de User
     */
    private void funcionUser(){
        //verifica que sea el usuario root que esta logueado para usar esta funcion
        if(ventana.usuario.equals("root")){
            usuarioTemp.clear();
            ventana.escribirMensaje(" Nombre completo: ");
            ventana.banderaUser=true;
            ventana.contUser=1;
            usuarioTemp.add(cadenaEntrada().get(1));//usuario
        }
        else{
            ventana.escribirMensaje("Debe ingresar como usuario root para crear un usuario\n");
            ventana.ruta=pathSistema()+">";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * funcion auxiliar de User
     * @param cont para verificar que solicitar al ususario
     * @param comando para obetener lo que el usuario ingresa
     */
    public void funcionUserAux(int cont,String comando){
        switch (cont) {
            case 1:
                if(!comando.trim().equals("")){
                    //aqui se obtiene el nombre del usuario
                    usuarioTemp.add(comando);
                    ventana.escribirMensaje(" Contraseña: ");
                    ventana.contUser=2;
                }
                else{
                    //si esta vacio el nombre lo vuelve a solicitar 
                    ventana.escribirMensaje(" Error, debe dijitar un caracter para el nombre\n");
                    ventana.escribirMensaje(" Nombre completo: ");
                    ventana.banderaUser=true;
                    ventana.contUser=1;
                }   break;
            case 2:
                if(!comando.trim().equals("")){
                    usuarioTemp.add(comando);//se obtiene la contraseña del usuario que se agrega
                    ventana.escribirMensaje(" Confirmación de Contraseña: ");
                    ventana.contUser=3;
                }
                else{
                    //si esta vacia la contraseña la vuelve a solicitar
                    ventana.escribirMensaje(" Error, debe dijitar un caracter la contraseña\n");
                    ventana.escribirMensaje(" Contraseña: ");
                    ventana.contUser=2;
                }   break;
            default:
                //verifica si la contraseña y la confirmacion son las mismas
                if(usuarioTemp.get(2).equals(comando)){
                    ventana.banderaUser=false;
                    usuarios nuevoUsuario = new usuarios(usuarioTemp.get(1).toString(), usuarioTemp.get(0).toString(), usuarioTemp.get(2).toString());
                    //verifia si el usuario que se agrego ya se encuentra agregado
                    if(!verificarUsuarioExistente(nuevoUsuario)){
                        listaUsuarios.add(nuevoUsuario);
                        ventana.ruta=pathSistema()+">";
                        ventana.escribirMensaje( ventana.ruta);
                    }else{
                        //le indica al usuario si nickname que desea agregar ya esta siendo utilizado
                        ventana.escribirMensaje("El usuario ya se encuentra registrado\n");
                        ventana.ruta=pathSistema()+">";
                        ventana.escribirMensaje( ventana.ruta);
                    }
                }else{
                    //incica al usuario si la confirmaion es incorrecta
                    ventana.escribirMensaje(" Contraseña incorrecta\n");
                    ventana.escribirMensaje(" Confirmación de Contraseña: ");
                    ventana.contUser=3;
                }   break;
        }
    }
    /**
     * funcion principal de password 
     */
    private void funcionPassword(){
        //verifica que la lista de usuarios contenga al menos un usuario
        if(ventana.usuario.equals("root")){
            if(listaUsuarios.size()>1){
                ContraTemp.clear();
                ventana.escribirMensaje(" Contraseña nueva: ");
                ventana.banderaPassword=true;
                ventana.contPas=1;
            }else{
                ventana.escribirMensaje("Error, no hay usuarios registrados\n");
                ventana.ruta=pathSistema()+">";
                ventana.escribirMensaje( ventana.ruta);
            }
        }
        else{
            ventana.escribirMensaje("Debe ingresar como usuario root para crear un usuario\n");
            ventana.ruta=pathSistema()+">";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * funcion auxiliar del comando password
     * @param cont
     * @param contra 
     */
    public void funcionPasswordAux(int cont,String contra){
        if(cont==1){
            if(!contra.trim().equals("")){
                //aqui se obtiene el nombre del usuario
                ContraTemp.add(contra);
                ventana.escribirMensaje(" Confirmar contraseña nueva: ");
                ventana.contPas=2;
            }
            else{
                //si esta vacio el nombre lo vuelve a solicitar 
                ventana.escribirMensaje(" Error, debe dijitar un caracter para la contraseña\n");
                ventana.escribirMensaje(" Contraseña nueva: ");
                ventana.contPas=1;
            }
        }
        else{
            //verifica que las conraseñas sea iguales
            if(ContraTemp.get(0).toString().trim().equals(contra.trim())){
                //verifica que el usuario exista
                if(verificarUsuarioPassword((String) cadenaEntrada().get(1))!=null){
                    //se obtiene el usuario que se le desea cambiar la clave
                    usuarios usuario=verificarUsuarioPassword((String) cadenaEntrada().get(1));
                    //verifica que la contraseña no venga ""
                    if(!contra.trim().equals("")){
                        usuario.setPassword(contra);
                        ventana.escribirMensaje(" Cambiada con exito\n");
                        ventana.ruta=pathSistema()+">";
                        ventana.escribirMensaje(ventana.ruta);
                        ventana.banderaPassword=false;
                    }
                    else{
                        ventana.escribirMensaje(" Error, Contraseña incorrecta\n");
                        ventana.escribirMensaje(" Password root: ");
                    }
                }
                else{
                    System.out.println("cae");
                    ventana.escribirMensaje(" Error, Usuario no existente\n");
                    ventana.ruta=pathSistema()+">";
                    ventana.escribirMensaje( ventana.ruta);
                    ventana.banderaPassword=false;
                }
            }else{
                ventana.escribirMensaje(" Error, las contraseñas no coinciden\n");
                ventana.ruta=pathSistema()+">";
                ventana.escribirMensaje( ventana.ruta);
            }
        }
    }
    /**
     * funcion princiapl del comando groupadd
     */
    private void funcionGroupadd(){
        if(!ventana.usuario.equals("")){
            grupo nuevoGrupo = new grupo(cadenaEntrada().get(1).toString());
            listaGrupos.add(nuevoGrupo);
            ventana.ruta=pathSistema()+">";
            ventana.escribirMensaje( ventana.ruta);
        }
        else{
            ventana.escribirMensaje(" Error, no se encontro un usuario logeado\n");
            ventana.ruta=pathSistema()+">";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * funcion principal del comando whoami
     */
    private void funcionWhoami(){
        ventana.escribirMensaje(" Username: "+ventana.usuario+"\n");
        ventana.escribirMensaje(" Full name: "+ventana.nombreUsuario+"\n");
        ventana.ruta=pathSistema()+">";
        ventana.escribirMensaje( ventana.ruta);
    }
    /**
     * funcion principal del comando usermod
     */
    private void funcionUsermod(){
        if(!ventana.usuario.equals("")){
            if(verificarUsuarioPassword(cadenaEntrada().get(1).toString())!=null){
                if(verificarGrupo(cadenaEntrada().get(2).toString())!=null){
                    usuarios usuario=verificarUsuarioPassword(cadenaEntrada().get(1).toString());
                    grupo grup=verificarGrupo(cadenaEntrada().get(2).toString());
                    grup.setUsuarioGrup(usuario);
                    ventana.ruta=pathSistema()+">";
                    ventana.escribirMensaje( ventana.ruta);
                }
                else{
                    ventana.escribirMensaje(" Error, no se encontro ningun grupo con ese nombre\n");
                    ventana.ruta=pathSistema()+">";
                    ventana.escribirMensaje( ventana.ruta);
                }
            }else
            {
                ventana.escribirMensaje(" Error, no se encontro ningun usuario con ese nombre\n");
                ventana.ruta=pathSistema()+">";
                ventana.escribirMensaje( ventana.ruta);
            }
        }
        else{
            ventana.escribirMensaje(" Error, no se encontro un usuario logeado\n");
            ventana.ruta=pathSistema()+">";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * funcion principal del comando pwd
     */
    private void funcionPwd(){
        String path="/"+ventana.usuario;;
        System.out.println(listaUbicaion.size());
        for(int i=1;i<listaUbicaion.size();i++){
            path+="/"+listaUbicaion.get(i).toString();
        }
        ventana.escribirMensaje(path+"\n");
        ventana.ruta=pathSistema()+">";
        ventana.escribirMensaje( ventana.ruta);
    } 
    /**
     * arma la cadena de entrada sin espacios y la devuelebe en una lista
     * @return 
     */
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
    /**
     * virifica si el usuario esta en la lista de usuarios 
     * @param usuario
     * @return 
     */
    private boolean verificarUsuarioExistente(usuarios usuario){
        for(usuarios usu : listaUsuarios){
            if(usu.getNombreUsuario().equals(usuario.getNombreUsuario())){
                return true;
            }
        }
        return false;
    }
    /**
     * verifica la contraseña del usuario que desa loguearse
     * @param usuario
     * @return 
     */
    private usuarios verificarUsuarioPassword(String usuario){
        for(usuarios usu : listaUsuarios){
            if(usu.getNombreUsuario().equals(usuario.trim())){
                return usu;
            }
        }
        return null;
    }
    /**
     * verifica que el grupo exista
     * @param nombre
     * @return 
     */
    private grupo verificarGrupo(String nombre){
        for(grupo usu : listaGrupos){
            if(usu.getNombre().equals(nombre.trim())){
                return usu;
            }
        }
        return null;
    }
    /**
     * funcion que genera el path
     */
    private String pathSistema(){
        String path=nombreDisco+"\\"+ventana.usuario;
        for(String var :listaUbicaion){
            path+=var+"\\";
        }
        return path;
    }
}
