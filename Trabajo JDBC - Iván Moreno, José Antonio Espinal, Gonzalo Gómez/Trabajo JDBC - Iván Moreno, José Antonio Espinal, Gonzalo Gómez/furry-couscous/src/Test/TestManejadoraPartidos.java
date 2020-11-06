package Test;

import Entidades.clsPartidos;
import Manejadora.ManejadoraPartidos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class TestManejadoraPartidos
{
    public static void main(String[] args)
    {
        ManejadoraPartidos manejadora = new ManejadoraPartidos();

        /*//CREAR UN PARTIDO
        clsPartidos partido = new clsPartidos
                (2,
                (short)2,
                (short)1,
                "Camp Nou",
                new GregorianCalendar(2017, 5, 3),
                new GregorianCalendar(2017, 5, 4),
                new GregorianCalendar(2017, 5, 1),
                new GregorianCalendar(2017, 5, 4),
                10000,
                10000,
                10000);

        try {
            manejadora.crearPartido(partido);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */

        //OBTENER TODOS LOS PARTIDOS
        try
        {
            ArrayList<clsPartidos> partidos = manejadora.obtenerTodosLosPartidos();

            for(clsPartidos p : partidos)
            {
                System.out.println(p.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
