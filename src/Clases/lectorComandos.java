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
    private ArrayList<directorios> listaDirectorios= new ArrayList<>();
    private ArrayList<archivo> listaArchivos= new ArrayList<>();
    private funcionalidadPrincipal disco;
    private directorios directorioActual;
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
        else if("pwd".equals(textoParseado.trim()) && cadenaEntrada().size()==1){
            funcionPwd();
        }
        else if("mkdir".equals(textoParseado.trim())&& cadenaEntrada().size()==2){
            funcionMkdir();
        }
        else if("rm".equals(textoParseado.trim())){
        
        }
        else if("mv".equals(textoParseado.trim()) && cadenaEntrada().size()==3 && cadenaEntrada().get(1).toString().trim().equals("filename")){
            funcionMv();
        }
        else if("ls".equals(textoParseado.trim())){
            funcionLs();
        }
        else if("cd".equals(textoParseado.trim())){
            funcionCd();
        }
        else if("whereis".equals(textoParseado.trim())){
        
        }
        else if("ln".equals(textoParseado.trim())){
        
        }
        else if("touch".equals(textoParseado.trim())&& cadenaEntrada().size()==2){
            funcionTouch();
        }
        else if("cat".equals(textoParseado.trim())){
            funcionCat();
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
        ventana.escribirMensaje("Creadon Disco....\n");
        nombreDisco="miDiscoDuro";
        directorios nuevoDirectorio = new directorios("Home", listaDirectorios.size(),null,null);
        listaDirectorios.add(nuevoDirectorio);
        directorioActual=nuevoDirectorio;
        String tamaño = cadenaEntrada().get(1).toString();
        disco=new funcionalidadPrincipal((1024*1000)*Integer.parseInt(tamaño));
        disco.CreacionDisco();
        disco.EscribirInicioBloque();
        ventana.escribirMensaje("Disco creado con exito\n");
        ClaveRootDefault=cadenaEntrada().get(2).toString();
        usuarios root=new usuarios("Root","root",ClaveRootDefault);
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
     * funcion principal para el comando mkdir
     */
    private void funcionMkdir(){
        String nombre=cadenaEntrada().get(1).toString();//obtiene el nombre
        directorios nuevoDirectorio = new directorios(nombre, listaDirectorios.size(),directorioActual,verificarUsuarioPassword(ventana.usuario));//crea el directorio
        directorioActual.setDirectorioHijo(nuevoDirectorio);//lo agrega al directorio actual
        listaDirectorios.add(nuevoDirectorio);//lo agrega al repositorio de directorios
        ventana.escribirMensaje("Directorio Creado\n");
        ventana.ruta=pathSistema()+">";
        ventana.escribirMensaje( ventana.ruta);
    }
    private void funcionRm(){
        
    }
    /**
     * funcion principal del comando mv 
     */
    private void funcionMv(){
        String nuevoNombre=cadenaEntrada().get(2).toString();
        if(validarDirectorio(nuevoNombre)!=null){
            validarDirectorio(nuevoNombre);
            directorioActual.getDirectorioPadre().removeDirectorio(directorioActual.getNombre());
            directorioActual.setDirectorioPadre(validarDirectorio(nuevoNombre));
            if(listaUbicaion.size()>1){
                listaUbicaion.remove(listaUbicaion.size()-2);
            }
            //directorioActual=validarDirectorio(listaUbicaion.get(listaUbicaion.size()-1));
            ventana.ruta=pathSistema()+">";
            ventana.escribirMensaje( ventana.ruta);
        }
        else{
            directorioActual.setNombre(nuevoNombre);
            ventana.ruta=pathSistema()+">";
            ventana.escribirMensaje( ventana.ruta);
        }
        
    }
    /**
     * funcion principal del comando ls
     * falta la version recursiva y los archivos
     * 
     */
    private void funcionLs(){
        if(cadenaEntrada().size()==1){
            for(directorios directorio: listaDirectorios){
                if(directorio.getDirectorioPadre()!=null){
                    if(directorio.getDirectorioPadre().getNombre().trim().equals(directorioActual.getNombre().trim())){
                        ventana.escribirMensaje("\t|___"+directorio.getNombre()+"\n");
                    }
                }
            }
            for(archivo archivo: listaArchivos){
                if(archivo.getCrador()!=null){
                    System.out.println("que pasa");
                    if(archivo.getDirectorioPadre().getNombre().trim().equals(directorioActual.getNombre().trim())){
                        ventana.escribirMensaje("\t|___-"+archivo.getNombre()+"\n");
                    }
                }
            }
            ventana.ruta=pathSistema()+">";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * funcion principal para el comando cd
     */
    private void funcionCd(){
        ArrayList<directorios> hijos = directoriosHijos();
        String nombreDirectorio=cadenaEntrada().get(1).toString();
        if(!nombreDirectorio.trim().equals("..")){
            System.out.println("entra");
            hijos.forEach((directorio) -> {
                if(directorio.getNombre().trim().equals(nombreDirectorio.trim())){
                    listaUbicaion.add(nombreDirectorio);
                    directorioActual=directorio;
                    ventana.ruta=pathSistema()+">";
                    ventana.escribirMensaje( ventana.ruta);
                }
                else{
                    ventana.ruta=pathSistema()+">";
                    ventana.escribirMensaje( ventana.ruta);
                }
            });
            if(hijos.isEmpty()){
                ventana.ruta=pathSistema()+">";
                ventana.escribirMensaje( ventana.ruta);
            }
        }
        else{
            if(listaUbicaion.size()>0){
               listaUbicaion.remove(listaUbicaion.size()-1); 
            }
            if(listaUbicaion.isEmpty()){
                directorioActual=listaDirectorios.get(0);
                ventana.ruta=pathSistema()+">";
                ventana.escribirMensaje( ventana.ruta);
            }
            else{
                directorioActual=validarDirectorio(listaUbicaion.get(listaUbicaion.size()-1));
                ventana.ruta=pathSistema()+">";
                ventana.escribirMensaje( ventana.ruta);
            }
        }
    }
    /**
     * funcion principal de touch
     */
    private void funcionTouch(){
        System.out.println(verificarUsuarioPassword(ventana.usuario));
        if(!ventana.usuario.equals("")){
            archivo nuevo=new archivo(cadenaEntrada().get(1).toString(), verificarUsuarioPassword(ventana.usuario),directorioActual);
            listaArchivos.add(nuevo);
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
     * funcion principal del comando cat
     */
    private void funcionCat(){
        ventana.escribirMensaje(validarArchivo(cadenaEntrada().get(1).toString().trim()).getContenido()+"\n");
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
        for (String textoParseado1 : textoParseado) {
            if (!textoParseado1.equals("")) {
                textoCOmpletoP.add(textoParseado1);
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
        String path=null;
        if(directorioActual!=null){
            path=nombreDisco+"\\"+ventana.usuario+"\\"+listaDirectorios.get(0).getNombre();
        }
        else{
            path="Comando";
        }
        for(String var :listaUbicaion){
            System.out.println(var);
            path+="\\"+var;
        }
        return path;
    }
    /**
     * validad si lo que ingresa es un directorio
     * @param nombre
     * @return 
     */
    private directorios validarDirectorio(String nombre){
        for(directorios directorio : listaDirectorios){
            if(directorio.getNombre().equals(nombre.trim())){
                return directorio;
            }
        }
        return null;
    }
    /**
     *  devuelve los directorios hijos del directorio actual
     */
    private ArrayList<directorios> directoriosHijos(){
        ArrayList<directorios> hijos=new ArrayList<>();
        for(directorios directorio : listaDirectorios){
            if(directorio.getDirectorioPadre()!=null){
                if(directorio.getDirectorioPadre().getNombre().trim().equals(directorioActual.getNombre().trim())){
                    hijos.add(directorio);
                }
            }
        }
        return hijos;
    }
    /**
     * devuele el archivo 
     * @param nombre
     * @return 
     */
    private archivo validarArchivo(String nombre){
        for(archivo usu : listaArchivos){
            if(usu.getNombre().equals(nombre.trim())){
                return usu;
            }
        }
        return null;
    }
}
