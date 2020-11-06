package Manejadora;

import Entidades.clsApuestas;
import Entidades.clsPartidos;
import Utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class ManejadoraPartidos
{
    static final String sourceUrl="jdbc:sqlserver://localhost";
    static final String usuario="pepito";
    static final String password="contraseña";

    public void crearPartido(clsPartidos partido) throws SQLException {
        Utils convertidora = new Utils();
        clsConexion conexion=new clsConexion(sourceUrl,usuario,password);

        String consulta = "INSERT INTO Partidos VALUES (?,?,?,?,?,?,?,?,?,?)";

        conexion.abrirConexion();
        //CallableStatement cStmt = conexion.getConnexionBaseDatos().prepareCall("{call CrearPartido(?,?,?,?,?,?,?,?,?,?,?)}");
         PreparedStatement statement = conexion.getConnexionBaseDatos().prepareStatement(consulta);

         statement.setShort(1,partido.getGolesVisitante());
        statement.setShort(2,partido.getGolesLocal());
        statement.setString(3,partido.getCampo());
        statement.setString(4,convertidora.GregorianCalendarToDateTime(partido.getFechaInicio()));
        statement.setString(5,convertidora.GregorianCalendarToDateTime(partido.getFechaFin()));
        statement.setString(6,convertidora.GregorianCalendarToDateTime(partido.getInicioAbierto()));
        statement.setString(7,convertidora.GregorianCalendarToDateTime(partido.getFinAbierto()));
        statement.setDouble(8,partido.getBeneficioMax1());
        statement.setDouble(9,partido.getBeneficioMax2());
        statement.setDouble(10,partido.getBeneficioMax3());
        statement.execute();

        conexion.cerrarConexion();
    }

    //TODO Estos dos métodos de los casos 8 y 9 hay que revisar qué necesitamos exactamente, es decir, como abrimos y cerramos un partido a apuestas (le preguntamos al usuario la fecha?, otra forma no se me ocurre)

    // caso 8: Abrir un partido para que acepte apuestas
    public void abrirPartido(int id, GregorianCalendar fecha) throws SQLException {
        Utils convertidora = new Utils();
        clsConexion conexion=new clsConexion(sourceUrl,usuario,password);
        conexion.abrirConexion();
        //CallableStatement cStmt = conexion.getConnexionBaseDatos().prepareCall("{call AbrirPartido(?,?)}");
        CallableStatement cStmt = conexion.getConnexionBaseDatos().prepareCall("{call AbrirPartido(?,?)}");
        cStmt.setInt(1,id);
        cStmt.setString(2,convertidora.GregorianCalendarToDateTime(fecha));
        cStmt.execute();
        conexion.cerrarConexion();
    }

    // caso 9: Cerrar un partido para que no se pueda apostar
    public void CerrarPartido(int id, GregorianCalendar fecha) throws SQLException {
        Utils convertidora = new Utils();
        clsConexion conexion=new clsConexion(sourceUrl,usuario,password);
        conexion.abrirConexion();
        CallableStatement cStmt = conexion.getConnexionBaseDatos().prepareCall("{call CerrarPartido(?,?)}");
        cStmt.setInt(1,id);
        cStmt.setString(2,convertidora.GregorianCalendarToDateTime(fecha));
        cStmt.execute();
        conexion.cerrarConexion();
    }

    public boolean cambiarFechaApuestasDelPartido(int id, GregorianCalendar nuevaFechaInicioApustas, GregorianCalendar nuevaFechaFinApuestas) throws SQLException
    {
        Utils utils = new Utils();
        int filasAfectadas;
        boolean fechasCambiadas = false;

        String consulta = "UPDATE Partidos " +
                          "SET " +
                          "inicioAbierto = ?, " +
                          "finAbierto = ? " +
                          "WHERE id = ? ";

        clsConexion conexion = new clsConexion(sourceUrl, usuario, password);

        try
        {
            conexion.abrirConexion();

            PreparedStatement statement = conexion.getConnexionBaseDatos().prepareStatement(consulta);

            statement.setString(1, utils.GregorianCalendarToDateTime(nuevaFechaInicioApustas));
            statement.setString(2, utils.GregorianCalendarToDateTime(nuevaFechaFinApuestas));
            statement.setInt(3, id);

            filasAfectadas = statement.executeUpdate();

            if(filasAfectadas == 1)
                fechasCambiadas = true;
        }
        catch (SQLException e)
        {
            throw e;
        }
        finally
        {
            conexion.cerrarConexion();
        }

        return fechasCambiadas;
    }

    //Fasilito, no tienes ni que poner where, pilla todos los partidos de la tabla Partidos xD.
    public ArrayList<clsPartidos> obtenerTodosLosPartidos() throws SQLException {
        clsPartidos partido = null;
        ArrayList<clsPartidos> partidos = new ArrayList<clsPartidos>();
        clsConexion conexion = new clsConexion(sourceUrl,usuario,password);

        try {
            conexion.abrirConexion();
            PreparedStatement statement = conexion.getConnexionBaseDatos().prepareStatement("SELECT * FROM Partidos");
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                Utils convertidora = new Utils();
                int id = resultado.getInt("id");
                short golesVisitante = resultado.getShort("golesVisitante");
                short golesLocal = resultado.getShort("golesLocal");
                String campo = resultado.getString("campo");
                GregorianCalendar FechaInicio = convertidora.dateTimeToGregorianCalendar(resultado.getString("FechaInicio"));
                GregorianCalendar FechaFin = convertidora.dateTimeToGregorianCalendar(resultado.getString("FechaFin"));
                GregorianCalendar inicioAbierto = convertidora.dateTimeToGregorianCalendar(resultado.getString("inicioAbierto"));
                GregorianCalendar finAbierto = convertidora.dateTimeToGregorianCalendar(resultado.getString("finAbierto"));
                Double beneficioMax1 = resultado.getDouble("beneficioMax1");
                Double beneficioMax2 = resultado.getDouble("beneficioMax2");
                Double beneficioMax3 = resultado.getDouble("beneficioMax3");

                partido = new clsPartidos(id, golesVisitante, golesLocal, campo, FechaInicio, FechaFin, inicioAbierto,finAbierto,beneficioMax1,beneficioMax2,beneficioMax3);
                partidos.add(partido);
            }
        }
        catch(SQLException e)
        {
            throw e;
        }
        finally
        {
            conexion.cerrarConexion();
        }

        return partidos;
    }

    public ArrayList<clsPartidos> obtenerPartidosAbiertosAApuestas() throws SQLException
    {
        ArrayList<clsPartidos> partidosAbiertos = new ArrayList<clsPartidos>();

        long fechaActualEnMilisegundos = new GregorianCalendar().getTimeInMillis();

        try
        {
            ArrayList<clsPartidos> todosLosPartidos = obtenerTodosLosPartidos();

            for(clsPartidos partido : todosLosPartidos)
            {
                if((partido.getInicioAbierto().getTimeInMillis() < fechaActualEnMilisegundos && partido.getFinAbierto().getTimeInMillis()> fechaActualEnMilisegundos) &&
                   (partido.getFechaInicio().getTimeInMillis() < fechaActualEnMilisegundos && partido.getFechaFin().getTimeInMillis()> fechaActualEnMilisegundos))
                    partidosAbiertos.add(partido);
            }
        } catch (SQLException e) {
            throw e;
        }

        return partidosAbiertos;
    }

    public clsPartidos obtenerPartidoPorId(int idPartido) throws SQLException
    {
        clsPartidos partido = null;
        clsConexion conexion = new clsConexion(sourceUrl,usuario,password);

        try {
            conexion.abrirConexion();
            PreparedStatement statement = conexion.getConnexionBaseDatos().prepareStatement("SELECT * FROM Partidos WHERE id = ?");
            statement.setInt(1, idPartido);

            ResultSet resultado = statement.executeQuery();

            if (resultado.next())
            {
                Utils convertidora = new Utils();
                int id = resultado.getInt("id");
                short golesVisitante = resultado.getShort("golesVisitante");
                short golesLocal = resultado.getShort("golesLocal");
                String campo = resultado.getString("campo");
                GregorianCalendar FechaInicio = convertidora.dateTimeToGregorianCalendar(resultado.getString("FechaInicio"));
                GregorianCalendar FechaFin = convertidora.dateTimeToGregorianCalendar(resultado.getString("FechaFin"));
                GregorianCalendar inicioAbierto = convertidora.dateTimeToGregorianCalendar(resultado.getString("inicioAbierto"));
                GregorianCalendar finAbierto = convertidora.dateTimeToGregorianCalendar(resultado.getString("finAbierto"));
                Double beneficioMax1 = resultado.getDouble("beneficioMax1");
                Double beneficioMax2 = resultado.getDouble("beneficioMax2");
                Double beneficioMax3 = resultado.getDouble("beneficioMax3");

                partido = new clsPartidos(id, golesVisitante, golesLocal, campo, FechaInicio, FechaFin, inicioAbierto,finAbierto,beneficioMax1,beneficioMax2,beneficioMax3);
            }
        }
        catch(SQLException e)
        {
            throw e;
        }
        finally
        {
            conexion.cerrarConexion();
        }

        return partido;
    }

    public double getCuotaPartido(int idPartido)
    {
        System.out.println("getCuotaPartido en construcción");
        return 1.01;
    }
}
