/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
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
    private ArrayList<archivo> listaArchivosOpen= new ArrayList<>();
    private discoDuro disco;
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
        else if("su".equals(textoParseado.trim())&& cadenaEntrada().size()<=2){
            funcionSu();
        }
        else if("whoami".equals(textoParseado.trim()) && cadenaEntrada().size()==1){
            funcionWhoami();
        }
        else if("pwd".equals(textoParseado.trim()) && cadenaEntrada().size()==1){
            funcionPwd();
        }
        else if("mkdir".equals(textoParseado.trim())&& cadenaEntrada().size()==2){
            funcionMkdir();//falta terminar cuando son iguales no funciona
        }
        else if("rm".equals(textoParseado.trim()) && cadenaEntrada().size()<=3){
            funcionRm();
        }
        else if("mv".equals(textoParseado.trim()) && cadenaEntrada().size()==3){
            funcionMv();//falta terminar
        }
        else if("ls".equals(textoParseado.trim())&& cadenaEntrada().size()<=2){
            funcionLs();//falta terminar -r
        }
        else if("cd".equals(textoParseado.trim()) && cadenaEntrada().size()==2){
            funcionCd();
        }
        else if("whereis".equals(textoParseado.trim()) && cadenaEntrada().size()==2){
            //pathBusqueda(cadenaEntrada().get(1).toString());//falta terminar
        }
        else if("ln".equals(textoParseado.trim()) && cadenaEntrada().size()==3){
            //falta terminar
        }
        else if("touch".equals(textoParseado.trim())&& cadenaEntrada().size()==2){
            funcionTouch();
        }
        else if("cat".equals(textoParseado.trim()) && cadenaEntrada().size()==2){
            funcionCat();//falta terminar los privilegios
        }
        else if("chown".equals(textoParseado.trim()) && cadenaEntrada().size()<=4){
            funcionChown();
        }
        else if("chgrp".equals(textoParseado.trim()) && cadenaEntrada().size()<=4){
            funcionChgrp();//falta terminar -r
        }
        else if("chmod".equals(textoParseado.trim()) && cadenaEntrada().size()==2){
            //falta terminar
        }
        else if("openFile".equals(textoParseado.trim()) && cadenaEntrada().size()==2){
            funcionOpenFile();
        }
        else if("closeFile".equals(textoParseado.trim()) && cadenaEntrada().size()==2){
            funcionCloseFile();
        }
        else if("viewFilesOpen".equals(textoParseado.trim()) && cadenaEntrada().size()==1){
            funcionViewFilesOpen();
        }
        else if("viewFCB".equals(textoParseado.trim())&& cadenaEntrada().size()==2){
            funcionViewFCB();
        }
        else if("infoFS".equals(textoParseado.trim()) && cadenaEntrada().size()==1){
            funcionInfoFS();
        }
        else if("usermod".equals(textoParseado.trim()) && cadenaEntrada().size()==2){
            funcionUsermod();
        }
        else if("poweroff".equals(textoParseado.trim()) && cadenaEntrada().size()==1){
            funcionPoweroff();
        }
        else{
            ventana.escribirMensaje("Comando no existente\n");
            ventana.ruta=pathSistema()+":";
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
            ventana.ruta=pathSistema()+":";
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
                ventana.ruta=pathSistema()+":";
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
                    ventana.usuario=cadenaEntrada().get(1).toString();
                    ventana.nombreUsuario=usuario.getNombreCompleto();
                    ventana.ruta=pathSistema()+":";
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
                ventana.banderaSU=false;
                ventana.banderaPassword=false;
                ventana.escribirMensaje(" Error, Usuario no existente\n");
                ventana.ruta=pathSistema()+":";
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
        try {
            nombreDisco="miDiscoDuro";
            grupo nuevoGrupo = new grupo(nombreDisco+"Grup");
            directorios nuevoDirectorio = new directorios("Home", listaDirectorios.size(),null,null);
            nuevoDirectorio.setGrupo(nuevoGrupo);
            listaGrupos.add(nuevoGrupo);
            listaDirectorios.add(nuevoDirectorio);
            directorioActual=nuevoDirectorio;
            String tamaño = cadenaEntrada().get(1).toString();
            disco=new discoDuro(1024*Integer.parseInt(tamaño));
            ventana.escribirMensaje("Creadon Disco....\n");
            disco.CreacionDisco();
            disco.EscribirInicioBloque();
            ventana.escribirMensaje("Disco creado con exito\n");
            ClaveRootDefault=cadenaEntrada().get(2).toString();
            usuarios root=new usuarios("Root","root",ClaveRootDefault);
            listaUsuarios.add(root);
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        } catch (Exception e) {
            ventana.escribirMensaje("Error, verificar el dato del tamaño del disco\n");
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        }
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
            grupo nuevoGrupo = new grupo("Grup");
        }
        else{
            ventana.escribirMensaje("Debe ingresar como usuario root para crear un usuario\n");
            ventana.ruta=pathSistema()+":";
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
                        grupo nuevoGrupo = new grupo(usuarioTemp.get(0).toString().trim()+"Grup");
                        nuevoGrupo.setUsuarioGrup(nuevoUsuario);
                        directorios nuevo = new directorios(usuarioTemp.get(0).toString().trim(), listaDirectorios.size(),listaDirectorios.get(0),nuevoUsuario);
                        nuevoGrupo.setDirectorioGrup(nuevo);
                        nuevo.setGrupo(nuevoGrupo);
                        listaDirectorios.add(nuevo);
                        listaGrupos.add(nuevoGrupo);
                        listaUsuarios.add(nuevoUsuario);
                        ventana.ruta=pathSistema()+":";
                        ventana.escribirMensaje( ventana.ruta);
                    }else{
                        //le indica al usuario si nickname que desea agregar ya esta siendo utilizado
                        ventana.escribirMensaje("El usuario ya se encuentra registrado\n");
                        ventana.ruta=pathSistema()+":";
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
                ventana.ruta=pathSistema()+":";
                ventana.escribirMensaje( ventana.ruta);
            }
        }
        else{
            ventana.escribirMensaje("Debe ingresar como usuario root para crear un usuario\n");
            ventana.ruta=pathSistema()+":";
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
                        ventana.ruta=pathSistema()+":";
                        ventana.escribirMensaje(ventana.ruta);
                        ventana.banderaPassword=false;
                    }
                    else{
                        ventana.escribirMensaje(" Error, Contraseña incorrecta\n");
                        ventana.escribirMensaje(" Password root: ");
                    }
                }
                else{
                    ventana.escribirMensaje(" Error, Usuario no existente\n");
                    ventana.ruta=pathSistema()+":";
                    ventana.escribirMensaje( ventana.ruta);
                    ventana.banderaPassword=false;
                }
            }else{
                ventana.escribirMensaje(" Error, las contraseñas no coinciden\n");
                ventana.ruta=pathSistema()+":";
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
            directorios nuevo = new directorios(cadenaEntrada().get(1).toString(),listaDirectorios.size(),listaDirectorios.get(0), validarUsuario(ventana.usuario));
            listaGrupos.add(nuevoGrupo);
            nuevo.setGrupo(nuevoGrupo);
            nuevoGrupo.setDirectorioGrup(nuevo);
            listaDirectorios.add(nuevo);
            ventana.escribirMensaje(" Grupo creado\n");
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        }
        else{
            ventana.escribirMensaje(" Error, no se encontro un usuario logeado\n");
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * funcion principal del comando whoami
     */
    private void funcionWhoami(){
        ventana.escribirMensaje(" Username: "+ventana.usuario+"\n");
        ventana.escribirMensaje(" Full name: "+ventana.nombreUsuario+"\n");
        ventana.ruta=pathSistema()+":";
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
                    ventana.ruta=pathSistema()+":";
                    ventana.escribirMensaje( ventana.ruta);
                }
                else{
                    ventana.escribirMensaje(" Error, no se encontro ningun grupo con ese nombre\n");
                    ventana.ruta=pathSistema()+":";
                    ventana.escribirMensaje( ventana.ruta);
                }
            }else
            {
                ventana.escribirMensaje(" Error, no se encontro ningun usuario con ese nombre\n");
                ventana.ruta=pathSistema()+":";
                ventana.escribirMensaje( ventana.ruta);
            }
        }
        else{
            ventana.escribirMensaje(" Error, no se encontro un usuario logeado\n");
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * funcion principal del comando pwd
     */
    private void funcionPwd(){
        String path="/"+ventana.usuario;;
        for(int i=1;i<listaUbicaion.size();i++){
            path+="/"+listaUbicaion.get(i).toString();
        }
        ventana.escribirMensaje(path+"\n");
        ventana.ruta=pathSistema()+":";
        ventana.escribirMensaje( ventana.ruta);
    }
    /**
     * funcion principal para el comando mkdir
     */
    private void funcionMkdir(){
        String lista=cadenaEntrada().get(1).toString();
        //
        if(!ventana.usuario.equals("")){
            for(String nuevoDirectorio : listaDirectoriosNew(lista)){
                boolean bander=false;
                for(directorios directorio: directoriosHijos()){
                    if(directorio.getNombre().trim().equals(nuevoDirectorio.trim())){
                        bander=true;
                    }
                }
                if(directoriosHijos().isEmpty()){
                    directorios nuevo = new directorios(nuevoDirectorio, listaDirectorios.size(),directorioActual,verificarUsuarioPassword(ventana.usuario));//crea el directorio
                    nuevo.setGrupo(directorioActual.getGrupo());
                    directorioActual.getGrupo().setDirectorioGrup(nuevo);
                    directorioActual.setDirectorioHijo(nuevo);//lo agrega al directorio actual
                    listaDirectorios.add(nuevo);//lo agrega al repositorio de directorios
                    ventana.escribirMensaje("Directorio Creado\n");
                    
                }
                else if(!bander){
                    directorios nuevo = new directorios(nuevoDirectorio, listaDirectorios.size(),directorioActual,verificarUsuarioPassword(ventana.usuario));//crea el directorio
                    nuevo.setGrupo(directorioActual.getGrupo());
                    directorioActual.getGrupo().setDirectorioGrup(nuevo);
                    directorioActual.setDirectorioHijo(nuevo);//lo agrega al directorio actual
                    listaDirectorios.add(nuevo);//lo agrega al repositorio de directorios
                    ventana.escribirMensaje("Directorio Creado\n");
                }
                else{
                    ventana.escribirMensaje("Directorio existente\n");
                }
            }
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        }
        else{
            ventana.escribirMensaje(" Error, no se encontro un usuario logeado\n");
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * 
     */
    private void funcionRm(){
        String nombre=cadenaEntrada().get(1).toString();
        if(validarArchivo(nombre)!=null){
            for(int i=0;i<listaArchivos.size();i++){
                if(listaArchivos.get(i).getDirectorioPadre().getNombre().trim().equals(directorioActual.getNombre().trim()) 
                        && listaArchivos.get(i).getNombre().trim().equals(nombre)){
                    listaArchivos.remove(i);
                }
            }
            ventana.escribirMensaje(" Elimicacion exitosa\n");
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        }
        else if(validarDirectorio(nombre)!=null){
            for(int i=1;i<listaDirectorios.size();i++){
                if(listaDirectorios.get(i).getDirectorioPadre().getNombre().trim().equals(nombre.trim())){
                    listaDirectorios.remove(i);
                }
            }
            for(int i=0;i<listaArchivos.size();i++){
                if(listaArchivos.get(i).getDirectorioPadre().getNombre().trim().equals(nombre.trim())){
                    listaArchivos.remove(i);
                }
            }
            for(int i=1;i<listaDirectorios.size();i++){
                if(listaDirectorios.get(i).getDirectorioPadre().getNombre().trim().equals(directorioActual.getNombre().trim()) 
                        && listaDirectorios.get(i).getNombre().trim().equals(nombre)){
                    listaDirectorios.remove(i);
                }
            }
            ventana.escribirMensaje(" Elimicacion exitosa\n");
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        }
        else{
            ventana.escribirMensaje(" Este elemento no pertenece a este directorio\n");
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * pasar a lo archivos
     * funcion principal del comando mv 
     */
    private void funcionMv(){
        String nuevoNombre=cadenaEntrada().get(1).toString();
        String nombreMover=cadenaEntrada().get(2).toString();
        System.out.println(validarPath(nombreMover).getDirectorioPadre().getNombre());
        System.out.println(validarPath(nombreMover).getNombre());
        if(validarArchivo(nuevoNombre)!=null && validarPath(nombreMover)!=null){
            if(validarArchivo(nuevoNombre).getDirectorioPadre().getNombre().trim().equals(directorioActual.getNombre().trim())){
                validarArchivo(nuevoNombre).getDirectorioPadre().removeArchivo(validarArchivo(nuevoNombre).getNombre());
                validarArchivo(nuevoNombre).setDirectorioPadre(validarPath(nombreMover));
                validarPath(nombreMover).setArchivo( validarArchivo(nuevoNombre));
                ventana.escribirMensaje(" Archivo movido con exito\n");
                ventana.ruta=pathSistema()+":";
                ventana.escribirMensaje( ventana.ruta);
            }
            else{
                ventana.escribirMensaje(" El archivo no existe en este directorio\n");
                ventana.ruta=pathSistema()+":";
                ventana.escribirMensaje( ventana.ruta);
            }
        }
        else{
            ventana.escribirMensaje(" El archivo no existe\n");
            ventana.ruta=pathSistema()+":";
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
            for(directorios directorio: directorioActual.getListaDirectorios()){
                if(directorio.getDirectorioPadre()!=null){
                    if(directorio.getDirectorioPadre().getNombre().trim().equals(directorioActual.getNombre().trim())){
                        ventana.escribirMensaje("\t|___"+directorio.getNombre()+"\n");
                    }
                }
            }
            for(archivo archivo: directorioActual.getListaArchivos()){
                if(archivo.getCreador()!=null){
                    if(archivo.getDirectorioPadre().getNombre().trim().equals(directorioActual.getNombre().trim())){
                        ventana.escribirMensaje("\t|___-"+archivo.getNombre()+"\n");
                    }
                }
            }
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    private void funcionLsAux(ArrayList<directorios> listaDirectorios,ArrayList<archivo>listaArchivos){
        for(directorios directorio: listaDirectorios){
                if(directorio.getDirectorioPadre()!=null){
                    if(directorio.getDirectorioPadre().getNombre().trim().equals(directorioActual.getNombre().trim())){
                        ventana.escribirMensaje("\t|___"+directorio.getNombre()+"\n");
                    }
                }
            }
            for(archivo archivo: listaArchivos){
                if(archivo.getCreador()!=null){
                    if(archivo.getDirectorioPadre().getNombre().trim().equals(directorioActual.getNombre().trim())){
                        ventana.escribirMensaje("\t|___-"+archivo.getNombre()+"\n");
                    }
                }
            }
    }
    /**
     * funcion principal para el comando cd
     */
    private void funcionCd(){
        ArrayList<directorios> hijos = directorioActual.getListaDirectorios();
        String nombreDirectorio=cadenaEntrada().get(1).toString();
        if(!nombreDirectorio.trim().equals("..")){
            boolean bar=false;
            for(directorios directorio : hijos){
                if(directorio.getNombre().trim().equals(nombreDirectorio.trim())){
                    listaUbicaion.add(nombreDirectorio);
                    directorioActual=directorio;
                    ventana.ruta=pathSistema()+":";
                    ventana.escribirMensaje( ventana.ruta);
                    bar=true;
                }
            }
            if(hijos.isEmpty()|| !bar){
                ventana.ruta=pathSistema()+":";
                ventana.escribirMensaje( ventana.ruta);
            }
            
        }
        else{
            if(listaUbicaion.size()>0){
               listaUbicaion.remove(listaUbicaion.size()-1); 
            }
            if(listaUbicaion.isEmpty()){
                directorioActual=listaDirectorios.get(0);
                ventana.ruta=pathSistema()+":";
                ventana.escribirMensaje( ventana.ruta);
            }
            else{
                directorioActual=validarDirectorio(listaUbicaion.get(listaUbicaion.size()-1));
                ventana.ruta=pathSistema()+":";
                ventana.escribirMensaje( ventana.ruta);
            }
        }
    }
    /**
     * funcion principal de touch
     */
    private void funcionTouch(){
        String nuevoArchivo=cadenaEntrada().get(1).toString();
        if(!ventana.usuario.equals("")){
            boolean bander=false;
            for(archivo archi: archivosHijos()){
                if(archi.getNombre().trim().equals(nuevoArchivo.trim())){
                    bander=true;
                }
            }
            if(archivosHijos().isEmpty()){
                archivo nuevo=new archivo(nuevoArchivo, verificarUsuarioPassword(ventana.usuario),directorioActual);
                nuevo.setGrupo(directorioActual.getGrupo());
                directorioActual.getGrupo().setArchivoGrup(nuevo);
                listaArchivos.add(nuevo);
                directorioActual.setArchivo(nuevo);
                ventana.escribirMensaje("Archivo creado\n");
                ventana.ruta=pathSistema()+":";
                ventana.escribirMensaje( ventana.ruta);
            }
            else if(!bander){
                archivo nuevo=new archivo(nuevoArchivo, verificarUsuarioPassword(ventana.usuario),directorioActual);
                nuevo.setGrupo(directorioActual.getGrupo());
                directorioActual.getGrupo().setArchivoGrup(nuevo);
                listaArchivos.add(nuevo);
                directorioActual.setArchivo(nuevo);
                ventana.escribirMensaje("Archivo creado\n");
                ventana.ruta=pathSistema()+":";
                bander=true;
                ventana.escribirMensaje( ventana.ruta);
            }
            else{
                ventana.escribirMensaje("Archivo existente\n");
                ventana.ruta=pathSistema()+":";
                ventana.escribirMensaje( ventana.ruta);
            }
        }
        else{
            ventana.escribirMensaje(" Error, no se encontro un usuario logeado\n");
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * funcion principal del comando cat
     */
    private void funcionCat(){
        ventana.escribirMensaje(validarArchivo(cadenaEntrada().get(1).toString().trim()).getContenido()+"\n");
        ventana.ruta=pathSistema()+":";
        ventana.escribirMensaje( ventana.ruta);
    }
    /**
     * funcion principal del comando chown
     */
    private void funcionChown(){
        if(validarUsuario(cadenaEntrada().get(1).toString())!=null){
            if(validarDirectorio(cadenaEntrada().get(2).toString())!=null){
                validarDirectorio(cadenaEntrada().get(2).toString()).setUsurioPadre(validarUsuario(cadenaEntrada().get(1).toString()));
                ventana.escribirMensaje(" Cambio con exito\n");
                ventana.ruta=pathSistema()+":";
                ventana.escribirMensaje( ventana.ruta);
            }
            else if(validarArchivo(cadenaEntrada().get(2).toString())!=null){
                validarArchivo(cadenaEntrada().get(2).toString()).setCreador(validarUsuario(cadenaEntrada().get(1).toString()));
                ventana.escribirMensaje(" Cambio con exito\n");
                ventana.ruta=pathSistema()+":";
                ventana.escribirMensaje( ventana.ruta);
            }
            else{
                ventana.escribirMensaje(" Error, no se encontro el elemento a cambiar el propietario\n");
                ventana.ruta=pathSistema()+":";
                ventana.escribirMensaje( ventana.ruta);
            }
        }
        else{
            ventana.escribirMensaje(" Error, no se encontro el usuario\n");
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * 
     */
    private void funcionChgrp(){
        if(validarDirectorio(cadenaEntrada().get(2).toString())!=null){
            for(directorios directorio : directoriosHijos()){
                if(directorio.getDirectorioPadre()!=null){
                    if(directorio.getDirectorioPadre().getNombre().trim().equals(directorioActual.getNombre().trim())){
                        directorio.setGrupo(validarGrupo(cadenaEntrada().get(1).toString()));
                        ventana.ruta=pathSistema()+":";
                        ventana.escribirMensaje( ventana.ruta);
                    }
                }
            }
        }
        else if(validarArchivo(cadenaEntrada().get(2).toString())!=null){
            for(archivo directorio : archivosHijos()){
                if(directorio.getDirectorioPadre()!=null){
                    if(directorio.getDirectorioPadre().getNombre().trim().equals(directorioActual.getNombre().trim())){
                        directorio.setGrupo(validarGrupo(cadenaEntrada().get(1).toString()));
                        ventana.ruta=pathSistema()+":";
                        ventana.escribirMensaje( ventana.ruta);
                    }
                }
            }
        }
    }
    /**
     * funcion principal del comando viewFCB
     */
    private void funcionViewFCB(){
        ArrayList<archivo> hijos = archivosHijos();
        String nombreArchivo=cadenaEntrada().get(1).toString();
        boolean bander=false;
        for(archivo archivo : hijos){
            if(archivo.getNombre().trim().equals(nombreArchivo.trim())){
                ventana.escribirMensaje( archivo.toString());
                bander=true;
                ventana.ruta=pathSistema()+":";
                ventana.escribirMensaje( ventana.ruta);
            }
        }
        if(hijos.isEmpty() || !bander){
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * 
     */
    private void funcionInfoFS() throws UnsupportedEncodingException{
        DecimalFormat df = new DecimalFormat("#.00");
        ventana.escribirMensaje(" Nombre del FileSystem: "+nombreDisco+"\n");
        if(disco.getTamañoDisco()>1024000000){
               ventana.escribirMensaje(" Tamaño: "+df.format(disco.getTamañoDisco()/1024000000) + " Gb\n");
        }else if(disco.getTamañoDisco()>1024000){
                ventana.escribirMensaje(" Tamaño: "+df.format(disco.getTamañoDisco()/1024000) + " Mb\n");
        }else if(disco.getTamañoDisco()>1024){
                ventana.escribirMensaje(" Tamaño: "+df.format(disco.getTamañoDisco()/1024) + " Kb\n");
        }else{
                ventana.escribirMensaje(" Tamaño: "+df.format(disco.getTamañoDisco()) + " bytes\n");
        }
        double tamañoutilizado=0;
        double disponible=0;
        for(archivo archi : listaArchivos){
            tamañoutilizado+=archi.getTamaño();
        }
        if(tamañoutilizado>1024000000){
               ventana.escribirMensaje(" Espacio utilizado: "+df.format(tamañoutilizado/1024000000) + " Gb\n");
               disponible=(disco.getTamañoDisco())-(tamañoutilizado);
        }else if(tamañoutilizado>1024000){
                ventana.escribirMensaje(" Espacio utilizado: "+df.format(tamañoutilizado/1024000) + " Mb\n");
                disponible=(disco.getTamañoDisco())-(tamañoutilizado);
        }else if(tamañoutilizado>1024){
                ventana.escribirMensaje(" Espacio utilizado: "+df.format(tamañoutilizado/1024) + " Kb\n");
                disponible=(disco.getTamañoDisco())-(tamañoutilizado);
        }else{
                ventana.escribirMensaje(" Espacio utilizado: "+df.format(tamañoutilizado) + " bytes\n");
                disponible=(disco.getTamañoDisco())-(tamañoutilizado);
        }
        if(disponible>1024000000){
               ventana.escribirMensaje(" Disponible: "+df.format((disco.getTamañoDisco()/1024000)-(tamañoutilizado/1024000))+"Gb\n");
        }else if(disponible>1024000){
                ventana.escribirMensaje(" Disponible: "+df.format((disco.getTamañoDisco()/1024000)-(tamañoutilizado/1024000))+"Mb\n");
        }else if(disponible>1024){
                ventana.escribirMensaje(" Disponible: "+df.format((disco.getTamañoDisco()/1024)-(tamañoutilizado/1024))+"Kb\n");
        }else{
                ventana.escribirMensaje(" Disponible: "+df.format((disco.getTamañoDisco())-(tamañoutilizado))+"bytes\n");
        }
        ventana.ruta=pathSistema()+":";
        ventana.escribirMensaje( ventana.ruta);
    }
    /**
     * 
     */
    private void funcionOpenFile(){
        ArrayList<archivo> hijos = archivosHijos();
        for(archivo archivo : hijos){
            if(archivo.getNombre().trim().equals(cadenaEntrada().get(1).toString().trim())){
                archivo.setEstado(1);
                listaArchivosOpen.add(archivo);
                ventana.escribirMensaje(" Archivo abierto\n");
                ventana.ruta=pathSistema()+":";
                ventana.escribirMensaje( ventana.ruta);
            }
        }
        if(hijos.isEmpty()){
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * 
     */
    private void funcionCloseFile(){
        ArrayList<archivo> hijos = archivosHijos();
        boolean bander=false;
        for(archivo archivo : hijos){
            if(archivo.getNombre().trim().equals(cadenaEntrada().get(1).toString().trim())){
                for(int i=0;i<listaArchivosOpen.size();i++){
                    if(listaArchivosOpen.get(i).getNombre().trim().equals(archivo.getNombre().trim())){
                        archivo.setEstado(0);
                        ventana.escribirMensaje(" Archivo cerrado\n");
                        ventana.ruta=pathSistema()+":";
                        ventana.escribirMensaje( ventana.ruta);
                        listaArchivosOpen.remove(i);
                        bander=true;
                    }
                }
            }
            else{
                ventana.escribirMensaje(" Archivo no existe en este directorio");
                ventana.ruta=pathSistema()+":";
                ventana.escribirMensaje( ventana.ruta);
            }
        }
        if(hijos.isEmpty() || !bander){
            ventana.ruta=pathSistema()+":";
            ventana.escribirMensaje( ventana.ruta);
        }
    }
    /**
     * 
     */
    private void funcionViewFilesOpen(){
        ventana.escribirMensaje(" Cantidad archivos abiertos "+listaArchivosOpen.size()+"\n");
        ventana.ruta=pathSistema()+":";
        ventana.escribirMensaje( ventana.ruta);
    }
    /**
     * 
     */
    private void funcionPoweroff(){
        ventana.escribirMensaje("Desea apagar el sistema(y/n): ");
        ventana.banderaPoweroff=true;
    }
    /**
     * 
     * @param comando 
     */
    public void funcionPoweroffAux(String comando){
        if(comando.trim().equals("y")){
            ventana.dispose();
        }else{
            ventana.banderaPoweroff=false;
            ventana.ruta=pathSistema()+">";
            ventana.escribirMensaje( ventana.ruta);
        }
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
            path=ventana.usuario+"@"+nombreDisco+"\\"+listaDirectorios.get(0).getNombre();
            //path=nombreDisco+"\\"+ventana.usuario+"\\"+listaDirectorios.get(0).getNombre();
        }
        else{
            path="Comando";
        }
        for(String var :listaUbicaion){
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
     *  devuelve los archivos hijos del directorio actual
     */
    private ArrayList<archivo> archivosHijos(){
        ArrayList<archivo> hijos=new ArrayList<>();
        for(archivo archivo : listaArchivos){
            if(archivo.getDirectorioPadre()!=null){
                if(archivo.getDirectorioPadre().getNombre().trim().equals(directorioActual.getNombre().trim())){
                    hijos.add(archivo);
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
    /**
     * 
     * @param nombre
     * @return 
     */
    private usuarios validarUsuario(String nombre){
        for(usuarios usu : listaUsuarios){
            if(usu.getNombreUsuario().equals(nombre.trim())){
                return usu;
            }
        }
        return null;
    }
    /**
     * 
     * @param nombre
     * @return 
     */
    private grupo validarGrupo(String nombre){
        for(grupo usu : listaGrupos){
            if(usu.getNombre().equals(nombre.trim())){
                return usu;
            }
        }
        return null;
    }
    /**
     * 
     * @param path
     * @return 
     */
    private ArrayList<String> parseoPathMov(String path){
        String[] textoParseado=path.trim().split("/");
        ArrayList<String> textoCOmpletoP = new ArrayList();
        for (String textoParseado1 : textoParseado) {
            if (!textoParseado1.equals("")) {
                textoCOmpletoP.add(textoParseado1.trim());
            }
        }
        return textoCOmpletoP;
    }
    /**
     * 
     * @param path
     * @return 
     */
    private directorios validarPath(String path){
        directorios bander=null;
        ArrayList<String> textoCOmpletoP = parseoPathMov(path);
        for(int i=0;i<textoCOmpletoP.size();i++){
            if(validarDirectorio(textoCOmpletoP.get(i).trim())!=null){
                ArrayList<directorios> hijos=validarDirectorio(textoCOmpletoP.get(i).trim()).getListaDirectorios();
                if(i+1<textoCOmpletoP.size()){
                    if(validarHijo(textoCOmpletoP.get(i+1).trim(), hijos)!=null || listaDirectorios.get(0).toString().trim().equals(textoCOmpletoP.get(i).trim())){
                        bander=validarHijo(textoCOmpletoP.get(i+1).trim(), hijos);
                    }
                    else{
                        bander=null;
                        break;
                    }
                }
                else{
                    if(listaDirectorios.get(0).getNombre().trim().equals(textoCOmpletoP.get(i).trim())){
                        bander=listaDirectorios.get(0);
                    }
                    break;
                }
            }else{
                bander=null;
                break;
            }
        }
        return bander;
    }
    /**
     * 
     * @param nombre
     * @param hijos
     * @return 
     */
    private directorios validarHijo(String nombre,ArrayList<directorios> hijos){
        directorios bandera = null;
        for(directorios directorio : hijos){
            if(directorio.getNombre().trim().equals(nombre.trim())){
                bandera = directorio;
            }
        }
        return bandera;
    }
    /**
     * 
     * @param lista
     * @return 
     */
    private ArrayList<String> listaDirectoriosNew(String lista){
        String[] textoParseado=lista.trim().split(",");
        ArrayList<String> textoCOmpletoP = new ArrayList();
        for (String textoParseado1 : textoParseado) {
            if (!textoParseado1.equals("")) {
                textoCOmpletoP.add(textoParseado1.trim());
            }
        }
        return textoCOmpletoP;
    }
   
}
