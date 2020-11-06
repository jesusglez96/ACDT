package Test;

import Entidades.clsUsuarios;
import Manejadora.ManejadoraUsuarios;

import java.sql.SQLException;

public class TestManejadoraUsuarios
{
    public static void main(String[] args)
    {
        ManejadoraUsuarios manejadora = new ManejadoraUsuarios();

       /* //INGRESAR DINERO
        try {
            manejadora.IngresarDinero(100.0, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

       //OBTENER USUARIO POR NICK
        try {
            clsUsuarios usuario = manejadora.obtenerUsuario("test");

            System.out.println(usuario.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
