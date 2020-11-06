/* ANALISIS

Un programa de consola en la cual se podrán realizar distintas operaciones en una casa de apuestas.
Los datos estarán guardados en una base de datos a los que se deberá acceder.

El programa tendrá un inicio de sesión, y, en función si el usuario es un usuario normal o un administrador, tendrá distintas funcionalidades.

El usuario normal podrá:
    - Realizar una apuesta.
    - Ver los partidos que están disponibles para apostar.
    - Comprobar el resultado de una apuesta anterior
    - Hacer un ingreso en su cuenta
    - Retirar dinero de su cuenta
    - Ver los movimientos de la cuenta, incluyendo apuestas realizadas y apuestas ganadas

El administrador podrá:
    - Realizar lo mismo que el usuario normal.
    - Crear un nuevo partido
    - Abrir un partido para que acepte apuestas
    - Cerrar un partido para que no se pueda apostar
    - Consultar las apuestas de un partido, indicando la cantidad de dinero apostado a cada posible resultado.
    - Pagar las apuestas ganadoras de un partido finalizado.

Entrada:
    - Para el menu principal, una opcion que podra ser:
        - 1) Iniciar sesión
        - 2) Crear una cuenta
        - 0) Salir

    - Para Iniciar sesión, un nombre de usuario y una contraseña

    - Para Crear una cuenta, un nombre de usuario y una contraseña

    - Para El menu de sesion de usuario, una opcion que podra ser:
        - 1) Realizar una apuesta.
        - 2) Ver los partidos que están disponibles para apostar.
        - 3) Comprobar el resultado de una apuesta anterior
        - 4) Hacer un ingreso en su cuenta
        - 5) Retirar dinero de su cuenta
        - 6) Ver los movimientos de la cuenta, incluyendo apuestas realizadas y apuestas ganadas

    - Para El menu de sesion de administrador, una opcion que podra ser:
        - 1) Realizar una apuesta.
        - 2) Ver los partidos que están disponibles para apostar.
        - 3) Comprobar el resultado de una apuesta anterior
        - 4) Hacer un ingreso en su cuenta
        - 5) Retirar dinero de su cuenta
        - 6) Ver los movimientos de la cuenta, incluyendo apuestas realizadas y apuestas ganadas
        - 7) Crear un nuevo partido
        - 8) Abrir un partido para que acepte apuestas
        - 9) Cerrar un partido para que no se pueda apostar
        - 10) Consultar las apuestas de un partido, indicando la cantidad de dinero apostado a cada posible resultado.
        - 11) Pagar las apuestas ganadoras de un partido finalizado.

    - Para realizar una apuesta

 */

/* PSEUDOCODIGO GENERALIZADO

Inicio
    Mostrar menu principal y validar opcion elegida
    Mientras(opcion elegida no sea salir)
        Segun(opcion elegida)
            caso 1:
                Iniciar sesion
            caso 2:
                Crear una cuenta
        FinSegun
        Mostrar menu principal y validar opcion elegida
    FinMientras
Fin

- Modulo Crear una cuenta
Inicio

    Leer y validar nombre de usuario nuevo
    Leer y validar nueva contraseña
    Crear cuenta

Fin

- Modulo Iniciar Sesion
Inicio
    Leer usuario y contraseña y validar si es correcto
    Si(es sesion de un usuario normal)
        Mostrar Menu de sesion para usuario normal y validar opcion elegida
    Sino
        Mostrar Menu de sesion para usuario administrador y validar opcion elegida
    FinSi
    Mientras(opcion elegida no sea cerrar sesion)
        Segun(opcion elegida)
            caso 1: Realizar una apuesta.
            caso 2: Ver los partidos que están disponibles para apostar.
            caso 3: Comprobar el resultado de una apuesta anterior
            caso 4: Hacer un ingreso en su cuenta
            caso 5: Retirar dinero de su cuenta
            caso 6: Ver los movimientos de la cuenta, incluyendo apuestas realizadas y apuestas ganadas
            caso 7: Crear un nuevo partido
            caso 8: Abrir un partido para que acepte apuestas
            caso 9: Cerrar un partido para que no se pueda apostar
            caso 10: Consultar las apuestas de un partido, indicando la cantidad de dinero apostado a cada posible resultado.
            caso 11: Pagar las apuestas ganadoras.
        FinSegun
        Si(es sesion de un usuario normal)
            Mostrar Menu de sesion para usuario normal y validar opcion elegida
        Sino
            Mostrar Menu de sesion para usuario administrador y validar opcion elegida
        FinSi
    FinMientras
Fin

- Modulo realizar una apuesta (opcion 1)
Inicio
    Mostrar partidos disponibles para apostar y validar partido elegido
    Si (opcion elegida no es volver atras)
        Leer y validar tipo de apuesta a realizar
        Segun(tipo de apuesta elegida)
            caso 1:
                Leer y validar goles para el equipo local
                Leer y validar goles para el equipo visitante
                Leer y validar cantidad a apostar
                Realizar apuesta tipo 1
            caso 2:
                Leer y validar si desea apostar al equipo local o al equipo visitante
                Leer y validar numero de goles
                Leer y validar cantidad a apostar
                Realizar apuesta tipo 2
            caso 3:
                Leer y validar si apuesta que gana local, visitante o empate
                Leer y validar cantidad a apostar
                Realizar apuesta tipo 3
        FinSegun
    FinSi
Fin

- Modulo Comprobar el resultado de una apuesta anterior (opcion 3)

Inicio
    Mostrar apuestas realizadas por el usuario y validar opcion de apuesta elegida
    Mientras(opcion no sea salir)
        Mostrar el resultado de la apuesta elegida
        Mostrar apuestas realizadas por el usuario y validar opcion de apuesta elegida
    FinMientras
Fin

- Modulo Hacer un ingreso en su cuenta (opcion 4)

Inicio
    Leer y validar cantidad a ingresar
    Ingresar dinero
Fin

- Modulo Retirar dinero de su cuenta (opcion 5)

Inicio
    Leer y validar cantidad a retirar
    Retirar dinero
Fin

- Modulo Crear un nuevo partido

Inicio
    Leer y validar goles del equipo local
    Leer y validar goles del equipo visitante
    Leer campo
    Leer y validar fecha de inicio
    Leer y validar fecha de fin
    Leer y validar fecha de inicio de partido abierto a apuestas
    Leer y validar fecha de cierre de apuestas para el partido
    Leer y validar beneficio maximo para las apuestas de tipo 1
    Leer y validar beneficio maximo para las apuestas de tipo 2
    Leer y validar beneficio maximo para las apuestas de tipo 3
    Insertar partido
Fin

- Modulo Consultar las apuestas de un partido, indicando la cantidad de dinero apostado a cada posible resultado.

Inicio
    Mostrar partidos y validar opcion de partido elegido
    Mostrar apuestas tipo 1 agrupadas por resultados
    Mostrar apuestas tipo 2 agrupadas por resultados
    Mostrar apuestas tipo 3 agrupadas por resultados
Fin

- Modulo Abrir un partido para que acepte apuestas

Inicio
    Mostrar partidos y validar opcion de partido elegido
    Cambiar fecha de inicio de apuestas abiertas a la fecha de hoy.
    Leer y validar fecha de fin de apuestas abiertas
    Cambiar fecha de fin de apuestas abiertas.
Fin

- Modulo Cerrar un partido para que no se pueda apostar

Inicio
    Mostrar partidos y validar opcion de partido elegido
    Cerrar partido para que no se pueda apostar
Fin

- Modulo Pagar las apuestas ganadoras de un partido finalizado.

Inicio
    Mostrar partidos y validar opcion de partido elegido
    Pagar las apuestas ganadoras.
Fin

 */

import Entidades.*;
import Gestion.Modulos;
import Manejadora.ManejadoraApuestas;
import Manejadora.ManejadoraPartidos;
import Manejadora.ManejadoraUsuarios;
import Utils.Validacion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ApuestaYGana
{
   public static void main(String[] args)
    {
        //Variables
        Modulos modulos = new Modulos();
        Validacion validacion = new Validacion();
        int opcionMenuPrincipal, opcionSesion;
        boolean usuarioNormal;
        clsUsuarios usuarioSesion, usuarioNuevo;
        clsPartidos partidoElegido;
        ManejadoraPartidos manejadoraPartidos = new ManejadoraPartidos();
        ManejadoraUsuarios manejadoraUsuarios = new ManejadoraUsuarios();
        ManejadoraApuestas manejadoraApuestas = new ManejadoraApuestas();
        double cantidad;
        ArrayList<clsMovimientos> movimientos;
        ArrayList<clsApuestas> apuestas;
        ArrayList<clsPartidos> partidos;
        GregorianCalendar nuevaFechaInicioAbierto, nuevaFechaFinAbierto;
        String nombreUsuario, contraseña;

        //Mostrar menu principal y validar opcion elegida
        opcionMenuPrincipal = validacion.mostrarMenuPrincipalYValidarOpcionElegida();

        while(opcionMenuPrincipal != 0)
        {
            switch(opcionMenuPrincipal)
            {
                case 1:
                    //Iniciar sesion

                    //Leer usuario y contraseña y validar si es correcto
                    usuarioSesion = validacion.LeerUsuarioYContraseñaYValidar();

                    System.out.println("Saldo: " + usuarioSesion.getSaldo() + " €");
                    if(usuarioSesion.getTipo() == 'N')
                    {
                        //Mostrar Menu de sesion para usuario normal y validar opcion elegida
                        opcionSesion = validacion.mostrarMenuUsuarioNormalYValidarOpcionElegida();
                    }
                    else if(usuarioSesion.getTipo() == 'A')
                    {
                        //Mostrar Menu de sesion para usuario administrador y validar opcion elegida
                        opcionSesion = validacion.mostrarMenuUsuarioAdminYValidarOpcionElegida();
                    }
                    else
                    {
                        System.out.println("Este usuario no tiene permisos, inicie sesión con otro usuario.");
                        opcionSesion = 0;
                    }

                    while(opcionSesion != 0)
                    {
                        switch(opcionSesion)
                        {
                            case 1:
                                //Realizar una apuesta.
                                modulos.realizarApuesta(usuarioSesion);
                                break;

                            case 2:
                                //Ver los partidos que están disponibles para apostar.
                                try {
                                    partidos = manejadoraPartidos.obtenerPartidosAbiertosAApuestas();

                                    for(clsPartidos partido : partidos)
                                    {
                                        System.out.println(partido.toString());
                                    }

                                } catch (SQLException e) {
                                    System.out.println("Hubo un error en la conexión, vuelva a intentarlo más tarde.");
                                }

                                break;
                            case 3:
                                //Comprobar el resultado de una apuesta anterior
                                modulos.comprobarResultadoDeUnaApuestaAnterior(usuarioSesion);
                                break;

                            case 4:
                                //Hacer un ingreso en su cuenta

                                //Leer y validar cantidad a ingresar
                                cantidad = validacion.leerYValidarCantidadAIngresar();

                                //Ingresar dinero
                                try {
                                    manejadoraUsuarios.IngresarDinero(cantidad, usuarioSesion.getId());
                                    usuarioSesion = manejadoraUsuarios.obtenerUsuario(usuarioSesion.getNick());
                                } catch (SQLException e) {
                                    System.out.println("Hubo un error en la conexión, vuelva a intentarlo más tarde.");
                                }

                                break;

                            case 5:
                                //Retirar dinero de su cuenta

                                //Leer y validar cantidad a retirar
                                cantidad = validacion.leerYValidarCantidadARetirar(usuarioSesion);

                                //Retirar dinero
                                try {
                                    manejadoraUsuarios.RetirarDinero(cantidad, usuarioSesion.getId());
                                    usuarioSesion = manejadoraUsuarios.obtenerUsuario(usuarioSesion.getNick());
                                } catch (SQLException e) {
                                    System.out.println("Hubo un error en la conexión, vuelva a intentarlo más tarde.");
                                }
                                break;

                            case 6:
                                //Ver los movimientos de la cuenta, incluyendo apuestas realizadas y apuestas ganadas
                                System.out.println("----MOVIMIENTOS----");

                                try {
                                    movimientos = manejadoraUsuarios.obtenerMovimientosDeUnUsuario(usuarioSesion);              //Este metodo solo da movimientos de ingresos y retiradas.

                                    for(clsMovimientos movimiento : movimientos)
                                    {
                                        System.out.println(movimiento.toString());
                                    }

                                } catch (SQLException e) {
                                    System.out.println("Hubo un error en la conexión, vuelva a intentarlo más tarde.");
                                }

                                System.out.println();


                                System.out.println("----APUESTAS----");

                                try
                                {
                                    apuestas = manejadoraApuestas.obtenerApuestasDeUnUsuario(usuarioSesion);

                                    for(clsApuestas apuesta : apuestas)
                                    {
                                        System.out.println(apuesta.toString());
                                    }

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }

                                break;

                            case 7:
                                //Crear un nuevo partido
                                modulos.crearUnPartido();
                                break;

                            case 8:
                                //Abrir un partido para que acepte apuestas

                                //Mostrar partidos y validar opcion de partido elegido
                                try {
                                    partidoElegido = validacion.mostrarObjetosYValidarObjetoElegido(manejadoraPartidos.obtenerTodosLosPartidos());

                                    if(partidoElegido != null)
                                    {
                                        //Cambiar fecha de inicio de apuestas abiertas a la fecha de hoy
                                        nuevaFechaInicioAbierto = new GregorianCalendar();
                                        nuevaFechaInicioAbierto.set(GregorianCalendar.HOUR, nuevaFechaInicioAbierto.get(GregorianCalendar.HOUR)-1);

                                        //Leer y validar fecha de fin de apuestas abiertas
                                        System.out.println("Introduce la fecha en la que se cerrarán las apuestas (Debe ser posterior a la fecha/hora actual");
                                        nuevaFechaFinAbierto = validacion.leerYValidarFechaFin(nuevaFechaInicioAbierto);

                                        //Cambiar fecha de fin de apuestas abiertas
                                        manejadoraPartidos.cambiarFechaApuestasDelPartido(partidoElegido.getIdPartidos(), nuevaFechaInicioAbierto, nuevaFechaFinAbierto);
                                    }

                                } catch (SQLException e) {
                                    System.out.println("Hubo un error de conexión, inténtelo de nuevo más tarde.");
                                }
                                break;

                            case 9:
                                //Cerrar un partido para que no se pueda apostar
                                //Mostrar partidos y validar opcion de partido elegido
                                try {
                                    partidoElegido = validacion.mostrarObjetosYValidarObjetoElegido(manejadoraPartidos.obtenerTodosLosPartidos());

                                    if(partidoElegido != null)
                                    {
                                        //Cerrar partido para que no se pueda apostar
                                        nuevaFechaInicioAbierto = new GregorianCalendar();
                                        nuevaFechaInicioAbierto.set(GregorianCalendar.DAY_OF_MONTH, nuevaFechaInicioAbierto.get(GregorianCalendar.DAY_OF_MONTH) - 2);

                                        nuevaFechaFinAbierto = new GregorianCalendar();
                                        nuevaFechaFinAbierto.set(GregorianCalendar.DAY_OF_MONTH, nuevaFechaFinAbierto.get(GregorianCalendar.DAY_OF_MONTH) - 1);

                                        manejadoraPartidos.cambiarFechaApuestasDelPartido(partidoElegido.getIdPartidos(), nuevaFechaInicioAbierto, nuevaFechaFinAbierto);
                                    }

                                } catch (SQLException e) {
                                    System.out.println("Hubo un error de conexión, inténtelo de nuevo más tarde.");
                                }
                                break;

                            case 10:
                                //consultar las apuestas de un partido, indicando la cantidad de dinero apostado a cada posible resultado.      //TODO Necesitamos un método que nos agrupe por resultado las apuestas y sume la cantida de dinero apostado de cada grupo.
                                try
                                {
                                    partidoElegido = validacion.mostrarObjetosYValidarObjetoElegido(manejadoraPartidos.obtenerTodosLosPartidos());

                                    if(partidoElegido != null)
                                    {
                                        ArrayList<clsTotalApostado> totalApostado = manejadoraApuestas.obtenerTotalApostadoDeUnPartido(partidoElegido.getIdPartidos());

                                        totalApostado.forEach(apostado -> System.out.println(apostado.toString()));
                                    }
                                }
                                catch (SQLException e)
                                {
                                    System.out.println("Hubo un error de conexión. Inténtelo de nuevo más tarde.");
                                }
                                break;

                            case 11:
                                //Pagar las apuestas ganadoras.
                                try {
                                    manejadoraApuestas.pagarApuestasGanadoras();
                                } catch (SQLException e) {
                                    System.out.println("Hubo un error de conexión, inténtelo de nuevo más tarde.");
                                }
                                break;

                        }

                        try {
                            usuarioSesion = manejadoraUsuarios.obtenerUsuario(usuarioSesion.getNick());
                        } catch (SQLException e) {
                            System.out.println("Hubo un error de conexión, inténtelo de nuevo más tarde.");
                        }

                        System.out.println("Saldo: " + usuarioSesion.getSaldo() + " €");
                        if(usuarioSesion.getTipo() == 'N')
                        {
                            //Mostrar Menu de sesion para usuario normal y validar opcion elegida
                            opcionSesion = validacion.mostrarMenuUsuarioNormalYValidarOpcionElegida();
                        }
                        else if(usuarioSesion.getTipo() == 'A')
                        {
                            //Mostrar Menu de sesion para usuario administrador y validar opcion elegida
                            opcionSesion = validacion.mostrarMenuUsuarioAdminYValidarOpcionElegida();
                        }
                        else
                        {
                            System.out.println("Este usuario no tiene permisos, inicie sesión con otro usuario.");
                            opcionSesion = 0;
                        }
                    }

                    break;

                case 2:
                    //Crear una cuenta

                    //Leer y validar nombre de usuario nuevo
                    nombreUsuario = validacion.leerYValidarNombreUsuarioNuevo();

                    //Leer y validar nueva contraseña
                    contraseña = validacion.leerYValidarNuevaContraseña();

                    //Crear cuenta
                    usuarioNuevo = new clsUsuarios(nombreUsuario, contraseña, "null@null.null", 0, 'N');

                    try {
                        manejadoraUsuarios.crearUsuario(usuarioNuevo);
                    } catch (SQLException e) {
                        System.out.println("Hubo un error al crear la cuenta, inténtelo de nuevo más tarde");
                    }

                    break;
            }

            //Mostrar menu principal y validar opcion elegida
            opcionMenuPrincipal = validacion.mostrarMenuPrincipalYValidarOpcionElegida();
        }
    }
}
