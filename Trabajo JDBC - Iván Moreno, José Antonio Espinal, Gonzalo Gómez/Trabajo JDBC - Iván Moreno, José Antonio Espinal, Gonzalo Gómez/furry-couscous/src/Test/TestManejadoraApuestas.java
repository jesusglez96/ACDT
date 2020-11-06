package Test;

import Entidades.*;
import Manejadora.ManejadoraApuestas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class TestManejadoraApuestas
{
    public static void main(String[] args)
    {
        ManejadoraApuestas manejadora = new ManejadoraApuestas();
/*
        //REALIZAR APUESTA TIPO1
        clsTipo1 tipo1 = new clsTipo1
            (
                new GregorianCalendar(),
                2.05,
                20,
                1,
                1,
                    (short)0,
                    (short)0
            );

        try
        {
            manejadora.RealizarApuestaTipo1(tipo1);
        } catch (SQLException e)
        {
            e.printStackTrace();
        } */


        /*//REALIZAR APUESTA TIPO2
        clsTipo2 tipo2 = new clsTipo2
            (
                new GregorianCalendar(),
                2.05,
                20,
                1,
                1,
                'L',
                (short)0
            );

        try
        {
            manejadora.RealizarApuestaTipo2(tipo2);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }*/


        /*//REALIZAR APUESTA TIPO3
        clsTipo3 tipo3 = new clsTipo3
            (
                new GregorianCalendar(),
                2.05,
                20,
                1,
                1,
                    (short)2
            );

        try
        {
            manejadora.RealizarApuestaTipo3(tipo3);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }*/


       /* //OBTENER APUESTA POR ID
        clsApuestas apuestaPorID;

        try
        {
            apuestaPorID = manejadora.obtenerApuesta(0);
            System.out.println(apuestaPorID.toString());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }*/


        /*//OBTENER APUESTAS DE UN PARTIDO
        ArrayList<clsApuestas> apuestasDeUnPartido;

        try
        {
            apuestasDeUnPartido = manejadora.obtenerApuestasDeUnPartido(new clsPartidos(1));

            for(clsApuestas apuesta : apuestasDeUnPartido)
            {
                System.out.println(apuesta.toString());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        */

       /* //OBTENER APUESTAS DE UN USUARIO
        ArrayList<clsApuestas> apuestasDeUnUsuario;

        try
        {
            apuestasDeUnUsuario = manejadora.obtenerApuestasDeUnUsuario(new clsUsuarios(1));

            for(clsApuestas apuesta : apuestasDeUnUsuario)
            {
                System.out.println(apuesta.toString());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        */
       /*
        double total = 0;
        try {
            for(clsTotalApostado totalApostado : manejadora.obtenerTotalApostadoDeUnPartidoTipo1(1))
            {
                total += totalApostado.getCantidad();
            }

            System.out.println(total);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

       /*
       clsApuestas apuesta = new clsTipo1(new GregorianCalendar(), 0, 25, 1, 1, (short)0, (short)0);

        try {
            System.out.println(manejadora.calcularCuota(apuesta));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */

       /* try {
            System.out.println(manejadora.esApuestaGanadora(manejadora.obtenerApuesta(6)));
            System.out.println(manejadora.esApuestaGanadora(manejadora.obtenerApuesta(19)));
            System.out.println(manejadora.esApuestaGanadora(manejadora.obtenerApuesta(21)));
            System.out.println(manejadora.esApuestaGanadora(manejadora.obtenerApuesta(26)));
            System.out.println(manejadora.esApuestaGanadora(manejadora.obtenerApuesta(27)));

            System.out.println();

            System.out.println(manejadora.esApuestaGanadora(manejadora.obtenerApuesta(12)));
            System.out.println(manejadora.esApuestaGanadora(manejadora.obtenerApuesta(13)));
            System.out.println(manejadora.esApuestaGanadora(manejadora.obtenerApuesta(14)));
            System.out.println(manejadora.esApuestaGanadora(manejadora.obtenerApuesta(16)));
            System.out.println(manejadora.esApuestaGanadora(manejadora.obtenerApuesta(17)));
            System.out.println(manejadora.esApuestaGanadora(manejadora.obtenerApuesta(20)));

            System.out.println();

            System.out.println(manejadora.esApuestaGanadora(manejadora.obtenerApuesta(18)));
            System.out.println(manejadora.esApuestaGanadora(manejadora.obtenerApuesta(22)));
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

       //Apostar un partido que no existe
       /* clsTipo1 tipo1 = new clsTipo1
        (
                new GregorianCalendar(),
                2.05,
                20,
                10,
                1,
                (short)0,
                (short)0
        );

        try
        {
            manejadora.RealizarApuestaTipo1(tipo1);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
*/
        //Apostar un partido que ya no está disponible
        /*clsTipo1 tipo1 = new clsTipo1
                (
                        new GregorianCalendar(),
                        2.05,
                        20,
                        2,
                        2,
                        (short)0,
                        (short)0
                );

        try
        {
            manejadora.RealizarApuestaTipo1(tipo1);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }*/

        //Hacer dos apuestas del mismo tipo al mismo partido
        /*clsTipo1 tipo1 = new clsTipo1
                (
                        new GregorianCalendar(),
                        2.05,
                        20,
                        1,
                        2,
                        (short)0,
                        (short)0
                );

        try
        {
            manejadora.RealizarApuestaTipo1(tipo1);
            manejadora.RealizarApuestaTipo1(tipo1);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }*/


        //Hacer una apuesta que sobrepase el máximo
        /*clsTipo1 tipo1 = new clsTipo1
                (
                        new GregorianCalendar(),
                        2.05,
                        22340,
                        1,
                        2,
                        (short)0,
                        (short)0
                );

        try
        {
            manejadora.RealizarApuestaTipo1(tipo1);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }*/
    }
}
