package Utils;

import Entidades.clsPartidos;
import Entidades.clsUsuarios;
import Manejadora.ManejadoraUsuarios;
import Manejadora.ManejadoraApuestas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Validacion
{

    /* INTERFAZ
     * Comentario: Muestra enumerados la lista de objetos determinada y valida la elección de una de ellos.
     * 				También muestra una opción de volver atrás.
     * Prototipo: public <T> T mostrarObjetosYValidarObjetoElegido(ArrayList<T> lista)
     * Entrada: Un ArrayList<T> con la lista de objetos que se desean mostrar enumerados y validar la elección de uno de ellos.
     * Precondiciones: No hay
     * Salida: El Objeto elegido de la lista
     * Postcondiciones: Asociado al nombre devuelve un objeto del mismo tipo que la lista (T), que será el objeto elegido.
     * 					- Si el usuario selecciona la opción 0 (volver atrás), el objeto devuelto será null.
     */
    /**
     * Muestra enumarados la lista de objetos determinada y valida la elección de uno de ellos.<br>
     * También muestra una opción para volver atrás.
     *
     * @param <T> El tipo del objeto de la lista.
     * @param lista La lista de objetos que se desea mostrar de formar enumerada y validar la elección de uno de ellos.
     * @return Un objeto del mismo tipo que la lista, que será el objeto elegido por el usuario.<br>
     * Si el usuario selecciona la opción 0 (volver atrás), el objeto devuelvo será null.
     */
    public <T> T mostrarObjetosYValidarObjetoElegido(ArrayList<T> lista)
    {
        T objeto = null;
        Scanner teclado = new Scanner(System.in);
        int opcion;

        System.out.println("0) Volver atras");

       /* for(int i = 0 ; i < lista.size() ; i++)
        {
            System.out.println((i+1) + ") " + lista.get(i).toString());
        } */

        lista.forEach(obj -> System.out.println((lista.indexOf(obj)+1) + ") " + obj.toString()));

        do
        {
            System.out.print("Elige una opcion: ");
            opcion = teclado.nextInt();
        }while(opcion < 0 || opcion > (lista.size()));

        if(opcion > 0)
            objeto = lista.get(opcion-1);

        return objeto;
    }

    // RECUERDA: en las validaciones pones un mensajito antes de pedir algo con el scanner, y luego simplementa validas que lo que ha introducido es correcto de la forma que sea.
    // No hagas más nada, ni consultas a la base de datos, ni ninguna accion que vaya antes o despúes de la propia validacion.

    // Muestra el menu con las dos opciones del menu principal y valida que lo que meta el usuario sea alguna de las dos opciones o salir (a la opcion de salir ponle el numero que quieras
    // yo le suelo poner el numero 0). Y devuelves ese numero.
    public int mostrarMenuPrincipalYValidarOpcionElegida() {
        int numeroMenu;
        Scanner teclado = new Scanner(System.in);

        System.out.println();
        System.out.println("MENU PRINCIPAL");
        System.out.println("0) Salir");
        System.out.println("1) Iniciar sesión");
        System.out.println("2) Crear una cuenta");
        System.out.print("Elige una opcion: ");
        numeroMenu = teclado.nextInt();

        if(numeroMenu>2 || numeroMenu<0)
        {
            do {
                System.out.print("Número fuera de rango, introduce uno válido: ");
                numeroMenu = teclado.nextInt();
            }while(numeroMenu>2 || numeroMenu<0);
        }
        return numeroMenu;
    }

    // Aquí tendrias que usar método obtenerUsuario de la clase ManejadoraUsuarios de las manejadoras.
    // básicamente pides al usuario que ingrese usuario y contraseña, luego le pides al metodo ese que te del el objeto usuario
    // y compruebas que coincide la contraseña del objeto que te da el metodo con la que el usuario ha metido.
    // Y si está bien la contraseña, pues devuelves el clsUsuario.
    public clsUsuarios LeerUsuarioYContraseñaYValidar() {
        String nickT, passT;
        //String nickBD = "";
        Scanner teclado = new Scanner(System.in);
        clsUsuarios oUsuarioX = new clsUsuarios();
        ManejadoraUsuarios oUsuario = new ManejadoraUsuarios();

        do
        {
            System.out.print("Introduce usuario: ");
            nickT = teclado.next();

            try
            {
                oUsuarioX = oUsuario.obtenerUsuario(nickT);
            } catch (SQLException e)
            {
                System.out.println("Hubo un error de conexión, inténtelo de nuevo más tarde.");
            }

        }while(oUsuarioX == null);

        do
        {
            System.out.print("Introduce contraseña: ");
            passT = teclado.next();

        }while(!oUsuarioX.getPass().equals(passT));


        return oUsuarioX;
    }
    /*
    * obtenerUsuario devuelve un objeto de la clase usuario, instancia un objeto de la clase usuario y llama al metodo para que te devuelva uno
    * */

    // Aquí igual que el menú principal, muestras las opciones para el menu del usuario normal (viene en el pseudocodigo del main)
    // Y también una opcion para salir, y validas que el numero que inserta el usuario es alguna de las opciones o salir, y devuelves ese numero cuando sea valido.
    public int mostrarMenuUsuarioNormalYValidarOpcionElegida() {
        int numeroMenu;
        Scanner teclado = new Scanner(System.in);
        System.out.println("MENU USUARIO");
        System.out.println("0.Cerrar sesión");
        System.out.println("1.Realizar apuesta");
        System.out.println("2.Ver partidos disponibles");
        System.out.println("3.Comprobar apuestas anteriores");
        System.out.println("4.Hacer ingreso en cuenta");
        System.out.println("5.Retirar dinero en cuenta");
        System.out.println("6.Ver movimientos de la cuenta");
        System.out.print("Elige una opcion: ");
        numeroMenu = teclado.nextInt();

        if(numeroMenu>6 || numeroMenu<0)
        {
            do{
                System.out.print("Numero fuera de rango, introduce uno valido: ");
                numeroMenu = teclado.nextInt();
            }while(numeroMenu>6 || numeroMenu<0);
        }
        return numeroMenu;
    }

    // Igual que el método de arriba pero con el menu para administrador.
    public int mostrarMenuUsuarioAdminYValidarOpcionElegida() {
        int numeroMenuAdmin;
        Scanner teclado = new Scanner(System.in);

        System.out.println("MENU ADMINISTRADOR");
        System.out.println("0.Cerrar sesión");
        System.out.println("1.Realizar apuesta");
        System.out.println("2.Ver partidos disponibles");
        System.out.println("3.Comprobar apuestas anteriores");
        System.out.println("4.Hacer ingreso en cuenta");
        System.out.println("5.Retirar dinero en cuenta");
        System.out.println("6.Ver movimientos de la cuenta");
        System.out.println("7.Crear un partido");
        System.out.println("8.Abrir un partido que acepte apuestas");
        System.out.println("9.Cerrar un partido para que no se pueda apostar");
        System.out.println("10.Consultar las apuestas de un partido");
        System.out.println("11.Pagar las apuestas ganadoras");
        System.out.println("Elige una opcion: ");
        numeroMenuAdmin = teclado.nextInt();

        if(numeroMenuAdmin>11 || numeroMenuAdmin<0){
            do{
                System.out.print("Numero fuera de rango, introduce uno valido: ");
                numeroMenuAdmin = teclado.nextInt();
            }while(numeroMenuAdmin>11 || numeroMenuAdmin<0);
        }
        return numeroMenuAdmin;
    }

    // Este es easy, comprueba que lo que mete el usuario es un 1, un 2 o un 3.
    public int leerYValidarTipoDeApuestaARealizar() {
        int tipoApuesta;
        Scanner teclado = new Scanner(System.in);

        System.out.println("1) Apuesta tipo 1 - Debes acertar el resultado del partido");
        System.out.println("2) Apuesta tipo 2 - Debes acertar el número de goles del equipo que elijas");
        System.out.println("3) Apuesta tipo 3 - Tipo quiniela, debes acertar si ganará local, visitante, o habrá un empate");

        do
        {
            System.out.print("Introduce el tipo de apuesta: ");
            tipoApuesta = teclado.nextInt();
        }while(tipoApuesta < 1 || tipoApuesta > 3);

        return tipoApuesta;
    }

    // También easy, es válido cuando es un número mayor o igual que 0. (Recuerda mandar un mensaje preguntando al usuario en plan: "Introduce numero de goles: "
    public int leerYValidarNumeroDeGoles() {
        int numeroGoles;
        Scanner teclado = new Scanner(System.in);
        //System.out.print("Introduce el numero de goles: ");
        numeroGoles = teclado.nextInt();

        if(numeroGoles < 0){
            do{
                System.out.print("Numero no valido, introduce los goles correctamente: ");
                numeroGoles = teclado.nextInt();
            }while(numeroGoles < 0);
        }

        return numeroGoles;
    }

    // ESTE NO LO HAGAS PORQUE FALTAN COSAS.
    // En este hay que preguntar a la base de datos si la cantidad a apostar en el partido superaría el limite para ese tipo
    // Es decir, se cogerían todas las apuestas de ese tipo para ese partido (junto con la que se quiere apostar), se suma las posibles ganancias,
    // Y si supera el límite, pues no se puede apostar dicha cantidad.
    public double leerYValidarCantidadAApostar(clsUsuarios usuario, clsPartidos partido, int tipoApuesta) throws SQLException {
        double cantidadAApostar, cantidadTotalTipo1, cantidadTotalTipo2, cantidadTotalTipo3;
        ManejadoraUsuarios manejadoraUsuarios = new ManejadoraUsuarios();
        clsUsuarios usu = manejadoraUsuarios.obtenerUsuario(usuario.getNick());
        //int tipoApuesta;
        ManejadoraApuestas oApuesta = new ManejadoraApuestas();
        Scanner teclado = new Scanner(System.in);

            System.out.print("Introduce la cantidad a apostar: ");
            cantidadAApostar = teclado.nextDouble();

            

            //tipoApuesta = leerYValidarTipoDeApuestaARealizar();

            switch (tipoApuesta){
                case 1:
                    cantidadTotalTipo1 = oApuesta.obtenerSumaCantidadApuestasTipo1(partido.getIdPartidos());

                    while(cantidadTotalTipo1+cantidadAApostar>partido.getBeneficioMax1() || usu.getSaldo() < cantidadAApostar)
                    {
                        System.out.print("La cantidad supera el maximo permitido en este tipo de apuesta, es erronea o no tiene suficiente saldo, pruebe a ingresar una cantidad menor");
                        cantidadAApostar = teclado.nextDouble();
                    }

                    break;
                case 2:
                    cantidadTotalTipo2 = oApuesta.obtenerSumaCantidadApuestasTipo2(partido.getIdPartidos());

                    while(cantidadTotalTipo2+cantidadAApostar>partido.getBeneficioMax2() || usu.getSaldo() < cantidadAApostar)
                    {
                        System.out.print("La cantidad supera el maximo permitido en este tipo de apuesta, es erronea o no tiene suficiente saldo, pruebe a ingresar una cantidad menor");
                        cantidadAApostar = teclado.nextDouble();
                    }


                    break;
                case 3:
                    cantidadTotalTipo3 = oApuesta.obtenerSumaCantidadApuestasTipo3(partido.getIdPartidos());

                    while(cantidadTotalTipo3+cantidadAApostar>partido.getBeneficioMax3() || usu.getSaldo() < cantidadAApostar)
                    {
                        System.out.print("La cantidad supera el maximo permitido en este tipo de apuesta, es erronea o no tiene suficiente saldo, pruebe a ingresar una cantidad menor");
                        cantidadAApostar = teclado.nextDouble();
                    }

                    break;
            }


        return cantidadAApostar;
    }


    // Aquí es fácil, aquí preguntas que si desea local(L) o visitante (V) y validas que lo que ha introducido es una de las dos letras.
    public char leerYValidarLocalOVisitante(){
        char localOvisitante;
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduce L para local o V para visitante: ");
        localOvisitante = teclado.next().charAt(0);

        if(localOvisitante != 'L' && localOvisitante != 'V'){
            do{
                System.out.print("Formato no valido, vuelva a introducir el caracter (L/V): ");
                localOvisitante = teclado.next().charAt(0);
            }while(localOvisitante != 'L' && localOvisitante != 'V');
        }

        return localOvisitante;
    }


    public short leerYValidar1X2(){
        char quiniela;
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduzca un resultado en formato quiniela (1X2): ");
        quiniela = teclado.next().charAt(0);
        short equipoGanador = 0;

        if(quiniela != '1' && quiniela != 'X' && quiniela != '2'){
            do{
                System.out.print("Formato no valido, vuelva a introducir el caracter (1/X/2): ");
                quiniela = teclado.next().charAt(0);
            }while(quiniela != '1' && quiniela != 'X' && quiniela != '2');
    }

        switch(quiniela)
        {
            case '1':
                equipoGanador = 1;
                break;
            case 'X':
                equipoGanador = 2;
                break;
            case '2':
                equipoGanador = 3;
                break;
        }

        return equipoGanador;
    }

    // Aquí solo tienes que pedir la cantidad y comprobar que es positiva.
    public double leerYValidarCantidadAIngresar(){
        double cantidad;
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduce la cantidad a ingresar: ");
        cantidad = teclado.nextDouble();

        if(cantidad<=0){
            do{
                System.out.print("La cantidad es demasiado baja o incorrecta, vuelve a introducir la cantidad: ");
                cantidad = teclado.nextDouble();
            }while(cantidad<=0);
        }

        return cantidad;
    }

    // Aquí pides la cantidad y compruebas que sea menor o igual al saldo que tiene el usuario.
    public double leerYValidarCantidadARetirar(clsUsuarios usuario){
        double cantidad;
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduce la cantidad a retirar; ");
        cantidad = teclado.nextDouble();

        if(cantidad>usuario.getSaldo()){
            do{
                System.out.print("La cantidad a retirar es erronea, introducela de nuevo: ");
                cantidad = teclado.nextDouble();
            }while(cantidad>usuario.getSaldo());
        }

        return cantidad;
    }

    // Aquí pides la fecha y validas que la fecha sea válida (si te atascas hay una forma muy easy, dejalo pa lo último si te atascas y te digo la forma easy)
    public GregorianCalendar leerYValidarFechaInicio(){
        int año, mes, dia, hora, minutos;
        GregorianCalendar fecha;
        Scanner teclado = new Scanner(System.in);

        do
        {
            //System.out.println("Fecha de inicio del partido");

            System.out.print("Año: ");
            año = teclado.nextInt();

            System.out.print("Mes: ");
            mes = teclado.nextInt() - 1;

            System.out.print("Dia: ");
            dia = teclado.nextInt();

            System.out.print("Hora: ");
            hora = teclado.nextInt();

            System.out.print("Minutos: ");
            minutos = teclado.nextInt();

            fecha = new GregorianCalendar(año, mes, dia, hora, minutos);

        }while(año != fecha.get(GregorianCalendar.YEAR) || mes != fecha.get(GregorianCalendar.MONTH) || dia != fecha.get(GregorianCalendar.DAY_OF_MONTH));

        return fecha;
    }

    // Aquí pides la fecha y validas que sea válida la fecha y que sea posterior a la fecha de inicio dada.
    public GregorianCalendar leerYValidarFechaFin(GregorianCalendar fechaInicio){
        int año, mes, dia, hora, minutos;
        Scanner teclado = new Scanner(System.in);
        GregorianCalendar fecha;

        do {
            //System.out.println("Fecha de fin del partido");

            System.out.print("Año: ");
            año = teclado.nextInt();

            System.out.print("Mes: ");
            mes = teclado.nextInt() - 1;

            System.out.print("Dia: ");
            dia = teclado.nextInt();

            System.out.print("Hora: ");
            hora = teclado.nextInt();

            System.out.print("Minutos: ");
            minutos = teclado.nextInt();

            fecha = new GregorianCalendar(año, mes, dia, hora, minutos);

        //}while(año != fechaInicio.get(GregorianCalendar.YEAR) || mes+1 != fechaInicio.get(GregorianCalendar.MONTH) || dia != fechaInicio.get(GregorianCalendar.DAY_OF_MONTH) || fecha.getTimeInMillis() < fechaInicio.getTimeInMillis());
        }while(fecha.getTimeInMillis() < fechaInicio.getTimeInMillis());

        return fecha;
    }

    // En este pides el número y validas que sea positivo y ya esta.
    public double leerYValidarBeneficioMaximo(){
        double beneficio;
        Scanner teclado = new Scanner(System.in);
        //System.out.println("Introduce el beneficio a validar");
        beneficio = teclado.nextDouble();
        if(beneficio<1.0){
            do{
                System.out.print("Beneficio incorrecto, asegurese de escribirlo correctamente: ");
                beneficio = teclado.nextDouble();
            }while(beneficio<1.0);
        }

        return beneficio;
    }

    public String leerYValidarNombreUsuarioNuevo()
    {
        String nombreUsuario = "";
        Scanner teclado = new Scanner(System.in);
        ManejadoraUsuarios manejadoraUsuarios = new ManejadoraUsuarios();

        try {
            do
            {
                System.out.print("Introduce nombre de usuario nuevo: ");
                nombreUsuario = teclado.next();

            } while (manejadoraUsuarios.obtenerUsuario(nombreUsuario) != null);
        }
        catch(SQLException e)
        {
            System.out.println("Hubo un error de conexión, inténtelo de nuevo más tarde.");
        }

        return nombreUsuario;
    }

    public String leerYValidarNuevaContraseña()
    {
        String nuevaContraseña = "";
        Scanner teclado = new Scanner(System.in);

        do
        {
            System.out.println("Introduce contraseña nueva: ");
            nuevaContraseña = teclado.next();
        }while(nuevaContraseña.equals(""));

        return nuevaContraseña;
    }

    //ESTO DE AQUI ABAJO NO LO HAGAS: se pueden hacer easy con el primer método de esta clase.

    // Mostrar partidos disponibles para apostar y validar partido elegido : clsPartidos
    // Mostrar apuestas realizadas por el usuario y validar opcion de apuesta elegida : clsApuestas
    // Mostrar el resultado de una apuesta : boolean
    //Mostrar todos los partidos y validar opcion de partido elegido


}
