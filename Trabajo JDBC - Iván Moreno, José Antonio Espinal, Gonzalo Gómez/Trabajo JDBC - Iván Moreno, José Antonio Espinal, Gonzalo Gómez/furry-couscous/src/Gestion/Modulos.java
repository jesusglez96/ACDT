package Gestion;

import Entidades.*;
import Manejadora.ManejadoraApuestas;
import Manejadora.ManejadoraPartidos;
import Manejadora.ManejadoraUsuarios;
import Utils.Validacion;

import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Modulos
{
    public void realizarApuesta(clsUsuarios usuarioSesion)
    {
        clsPartidos partidoElegido;
        clsApuestas apuesta;
        ManejadoraApuestas manejadoraApuestas = new ManejadoraApuestas();
        ManejadoraPartidos manejadoraPartidos = new ManejadoraPartidos();
        ManejadoraUsuarios manejadoraUsuarios = new ManejadoraUsuarios();
        Validacion validacion = new Validacion();
        int tipoApuestaElegida, golesLocal, golesVisitante, goles;
        double cantidadAApostar = 0, cuota;
        double totalApostado = 0;
        char localOVisitante;
        short _1x2;


        //Mostrar partidos disponibles para apostar y validar partido elegido
        System.out.println("Partidos disponibles para apostar:");
        try {
            partidoElegido = validacion.mostrarObjetosYValidarObjetoElegido(manejadoraPartidos.obtenerPartidosAbiertosAApuestas()); //Aqui no son todos los partidos, solo los disponibles, necesitamos un metodo pa eso.
        } catch (SQLException e) {
            System.out.println("Hubo un error de conexión, inténtelo de nuevo más tarde");
            partidoElegido = null;
        }

        if (partidoElegido != null)
        {

            //Leer y validar tipo de apuesta a realizar
            tipoApuestaElegida = validacion.leerYValidarTipoDeApuestaARealizar();

            switch(tipoApuestaElegida)
            {
                case 1:
                    /*
                    try {
                        for(clsTotalApostado total : manejadoraApuestas.obtenerTotalApostadoDeUnPartidoTipo1(partidoElegido.getIdPartidos()))
                        {
                            totalApostado += total.getCantidad();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } */

                    //Leer y validar goles para el equipo local
                    System.out.print("Introduce el número de goles para el equipo local: ");
                    golesLocal = validacion.leerYValidarNumeroDeGoles();

                    //Leer y validar goles para el equipo visitante
                    System.out.print("Introduce el número de goles para el equipo visitante: ");
                    golesVisitante = validacion.leerYValidarNumeroDeGoles();

                    clsTotalApostadoTipo1 total1;

                    try {
                        for(clsTotalApostado total : manejadoraApuestas.obtenerTotalApostadoDeUnPartidoTipo1(partidoElegido.getIdPartidos()))
                        {
                            total1 = (clsTotalApostadoTipo1) total;

                            if(total1.getGolesVisitante() == golesVisitante && total1.getGolesLocal() == golesLocal)
                                totalApostado += total.getCantidad();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    //Leer y validar cantidad a apostar
                    try
                    {
                        cantidadAApostar = validacion.leerYValidarCantidadAApostar(usuarioSesion, partidoElegido, tipoApuestaElegida);
                    }
                    catch (SQLException e)
                    {
                        System.out.println("Hubo un error de conexión. Inténtelo de nuevo más tarde.");;
                    }

                    //Realizar apuesta tipo 1
                    apuesta = new clsTipo1
                            (new GregorianCalendar(),
                            0,
                            cantidadAApostar,
                            partidoElegido.getIdPartidos(),
                            usuarioSesion.getId(),
                            (short)golesLocal,
                            (short)golesVisitante);

                    try
                    {
                        if(/*apuesta.getCantidadapostada() + */totalApostado >= 40)
                            cuota = apuesta.getCantidadapostada() + manejadoraApuestas.calcularCuota(apuesta);
                        else
                        {
                            cuota = 4;
                        }

                        apuesta.setCuota(cuota);

                        manejadoraApuestas.RealizarApuestaTipo1((clsTipo1)apuesta);
                        usuarioSesion = manejadoraUsuarios.obtenerUsuario(usuarioSesion.getNick());
                    } catch (SQLException e)
                    {
                        System.out.println("Hubo un error de conexión, inténtelo de nuevo más tarde.");
                    }

                    break;
                case 2:
                    /*
                    try {
                        for(clsTotalApostado total : manejadoraApuestas.obtenerTotalApostadoDeUnPartidoTipo2(partidoElegido.getIdPartidos()))
                        {
                            totalApostado += total.getCantidad();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }*/

                    //Leer y validar si desea apostar al equipo local o al equipo visitante
                    localOVisitante = validacion.leerYValidarLocalOVisitante();

                    //Leer y validar numero de goles
                    System.out.println("Introduce el número de goles: ");
                    goles = validacion.leerYValidarNumeroDeGoles();

                    clsTotalApostadoTipo2 total2;

                    try {
                        for(clsTotalApostado total : manejadoraApuestas.obtenerTotalApostadoDeUnPartidoTipo2(partidoElegido.getIdPartidos()))
                        {
                            total2 = (clsTotalApostadoTipo2) total;

                            if(total2.getLocalOVisitante() == localOVisitante && total2.getGoles() == goles)
                                totalApostado += total.getCantidad();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    //Leer y validar cantidad a apostar
                    try
                    {
                        cantidadAApostar = validacion.leerYValidarCantidadAApostar(usuarioSesion, partidoElegido, tipoApuestaElegida);
                    }
                    catch (SQLException e)
                    {
                        System.out.println("Hubo un error de conexión. Inténtelo de nuevo más tarde.");;
                    }

                    //Realizar apuesta tipo 2
                    apuesta = new clsTipo2
                            (new GregorianCalendar(),
                                    0,
                                    cantidadAApostar,
                                    partidoElegido.getIdPartidos(),
                                    usuarioSesion.getId(),
                                    localOVisitante,
                                    (short)goles);

                    try
                    {
                        if(/*apuesta.getCantidadapostada() + */totalApostado >= 40)
                            cuota = apuesta.getCantidadapostada() + manejadoraApuestas.calcularCuota(apuesta);
                        else
                        {
                            cuota = 3;
                        }

                        apuesta.setCuota(cuota);

                        manejadoraApuestas.RealizarApuestaTipo2((clsTipo2)apuesta);
                        usuarioSesion = manejadoraUsuarios.obtenerUsuario(usuarioSesion.getNick());
                    } catch (SQLException e)
                    {
                        System.out.println("Hubo un error de conexión, inténtelo de nuevo más tarde.");;
                    }

                    break;

                case 3:
                    /*
                    try {
                        for(clsTotalApostado total : manejadoraApuestas.obtenerTotalApostadoDeUnPartidoTipo3(partidoElegido.getIdPartidos()))
                        {
                            totalApostado += total.getCantidad();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }*/

                    //Leer y validar si apuesta que gana local, visitante o empate
                    _1x2 = validacion.leerYValidar1X2();

                    int equipoGanador = 0;
                    switch(_1x2)
                    {
                        case '1': equipoGanador = 0;
                        case 'X': equipoGanador = 1;
                        case '2': equipoGanador = 2;
                    }

                    clsTotalApostadoTipo3 total3;

                    try {
                        for(clsTotalApostado total : manejadoraApuestas.obtenerTotalApostadoDeUnPartidoTipo3(partidoElegido.getIdPartidos()))
                        {
                            total3 = (clsTotalApostadoTipo3) total;

                            if(total3.getEquipoGanador() == equipoGanador)
                                totalApostado += total.getCantidad();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    //Leer y validar cantidad a apostar
                    try
                    {
                        cantidadAApostar = validacion.leerYValidarCantidadAApostar(usuarioSesion, partidoElegido, tipoApuestaElegida);
                    }
                    catch (SQLException e)
                    {
                        System.out.println("Hubo un error de conexión. Inténtelo de nuevo más tarde.");;
                    }

                    //Realizar apuesta tipo 3
                    apuesta = new clsTipo3
                            (new GregorianCalendar(),
                                    0,
                                    cantidadAApostar,
                                    partidoElegido.getIdPartidos(),
                                    usuarioSesion.getId(),
                                    _1x2);

                    try
                    {
                        if(/*apuesta.getCantidadapostada() + */totalApostado >= 40)
                            cuota = apuesta.getCantidadapostada() + manejadoraApuestas.calcularCuota(apuesta);
                        else
                        {
                            cuota = 1.5;
                        }

                        apuesta.setCuota(cuota);

                        manejadoraApuestas.RealizarApuestaTipo3((clsTipo3)apuesta);
                        usuarioSesion = manejadoraUsuarios.obtenerUsuario(usuarioSesion.getNick());
                    } catch (SQLException e)
                    {
                        System.out.println("Hubo un error de conexión, inténtelo de nuevo más tarde.");;
                    }

                    break;

            }
        }
    }

    public void comprobarResultadoDeUnaApuestaAnterior(clsUsuarios usuarioSesion)
    {
        Validacion validacion = new Validacion();
        clsApuestas opcionApuestaElegida = null;
        ManejadoraApuestas manejadoraApuestas = new ManejadoraApuestas();
        int resultadoApuesta;

        //Mostrar apuestas realizadas por el usuario y validar opcion de apuesta elegida
        try {
            opcionApuestaElegida = validacion.mostrarObjetosYValidarObjetoElegido(manejadoraApuestas.obtenerApuestasDeUnUsuario(usuarioSesion));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while(opcionApuestaElegida != null)
        {
            try
            {
                //Mostrar el resultado de la apuesta elegida
                resultadoApuesta = manejadoraApuestas.esApuestaGanadora(opcionApuestaElegida);

                if(resultadoApuesta == 1)
                    System.out.println("Esta apuesta resultó ganadora!");
                else if (resultadoApuesta == 2)
                    System.out.println("Esta apuesta no resultó ganadora.");
                else
                    System.out.println("El partido todavía no terminó, consulta la apuesta cuando acabe.");

                //Mostrar apuestas realizadas por el usuario y validar opcion de apuesta elegida
                opcionApuestaElegida = validacion.mostrarObjetosYValidarObjetoElegido(manejadoraApuestas.obtenerApuestasDeUnUsuario(usuarioSesion));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void crearUnPartido()
    {
        Validacion validacion = new Validacion();
        Scanner teclado = new Scanner(System.in);
        ManejadoraPartidos manejadoraPartidos = new ManejadoraPartidos();

        int golesLocal;
        int golesVisitante;
        String campo;
        GregorianCalendar fechaInicio, fechaFin, fechaInicioApuestas, fechaFinApuestas;
        double beneficioMaximoTipo1, beneficioMaximoTipo2, beneficioMaximoTipo3;
        clsPartidos partido;

        //Leer y validar goles del equipo local
        System.out.print("Introduce goles del equipo local: ");
        golesLocal = validacion.leerYValidarNumeroDeGoles();

        //Leer y validar goles del equipo visitante
        System.out.print("Introduce goles del equipo visitante: ");
        golesVisitante = validacion.leerYValidarNumeroDeGoles();

        //Leer campo
        System.out.print("Escriba el nombre del campo: ");
        campo = teclado.next();

        //Leer y validar fecha de inicio
        System.out.println("Fecha de inicio del partido");
        fechaInicio = validacion.leerYValidarFechaInicio();

        //Leer y validar fecha de fin
        System.out.println("Fecha de fin del partido");
        fechaFin = validacion.leerYValidarFechaFin(fechaInicio);

        //Leer y validar fecha de inicio de partido abierto a apuestas
        System.out.println("Fecha de inicio de apuestas");
        fechaInicioApuestas = validacion.leerYValidarFechaInicio();

        //Leer y validar fecha de cierre de apuestas para el partido
        System.out.println("Fecha de fin de apuestas");
        fechaFinApuestas = validacion.leerYValidarFechaFin(fechaInicioApuestas);

        //Leer y validar beneficio maximo para las apuestas de tipo 1
        System.out.print("Introduce beneficio para las apuestas de tipo 1: ");
        beneficioMaximoTipo1 = validacion.leerYValidarBeneficioMaximo();

        //Leer y validar beneficio maximo para las apuestas de tipo 2
        System.out.print("Introduce beneficio para las apuestas de tipo 2: ");
        beneficioMaximoTipo2 = validacion.leerYValidarBeneficioMaximo();

        //Leer y validar beneficio maximo para las apuestas de tipo 3
        System.out.print("Introduce beneficio para las apuestas de tipo 3: ");
        beneficioMaximoTipo3 = validacion.leerYValidarBeneficioMaximo();

        //Insertar partido
        partido = new clsPartidos(
                (short)golesVisitante,
                (short)golesLocal,
                campo,
                fechaInicio,
                fechaFin,
                fechaInicioApuestas,
                fechaFinApuestas,
                beneficioMaximoTipo1,
                beneficioMaximoTipo2,
                beneficioMaximoTipo3);

        try
        {
            manejadoraPartidos.crearPartido(partido);
        } catch (SQLException e)
        {
            System.out.println("No se pudo crear el partido porque hubo un error en la conexión. Inténtelo de nuevo más tarde.");
        }

    }
}
