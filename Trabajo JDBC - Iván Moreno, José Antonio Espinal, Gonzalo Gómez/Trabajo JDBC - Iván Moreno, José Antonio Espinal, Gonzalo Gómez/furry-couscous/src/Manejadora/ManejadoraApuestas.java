package Manejadora;

import Entidades.*;
import Utils.Utils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class ManejadoraApuestas
{
    static final String sourceUrl="jdbc:sqlserver://localhost";
    static final String usuario="pepito";
    static final String password="contraseña";
    //hay que hacer en sql los realizarapuestas

    /**
     * Realiza una apuesta de tipo 1, insertándola en la base de datos y descontándole el saldo al usuario que la realiza.
     * @param tipo1 La apuesta a realizar.
     * @throws SQLException Lanza SQLException si hay algún error con la conexión a la base de datos.
     */
    public void RealizarApuestaTipo1(clsTipo1 tipo1) throws SQLException
    {
        clsConexion conexion=new clsConexion(sourceUrl,usuario,password);
        Utils utils = new Utils();

        try
        {
            conexion.abrirConexion();
            CallableStatement cStmt = conexion.getConnexionBaseDatos().prepareCall("{call RealizarApuestaTipo1(?,?,?,?,?,?,?)}");
            cStmt.setString(1, utils.GregorianCalendarToDateTime(tipo1.getFecha()));
            cStmt.setDouble(2, tipo1.getCuota());
            cStmt.setDouble(3, tipo1.getCantidadapostada());
            cStmt.setInt(4, tipo1.getIdPartido());
            cStmt.setInt(5, tipo1.getIdUsuario());
            cStmt.setInt(6, tipo1.getGolesLocal());
            cStmt.setInt(7, tipo1.getGolesVisitante());
            cStmt.execute();
        }
        catch(SQLException e)
        {
            throw e;
        }
        finally
        {
            conexion.cerrarConexion();
        }
    }

    /**
     * Realiza una apuesta de tipo 2, insertándola en la base de datos y descontándole el saldo al usuario que la realiza.
     * @param tipo2 La apuesta a realizar.
     * @throws SQLException Lanza SQLException si hay algún error con la conexión a la base de datos.
     */
    public void RealizarApuestaTipo2(clsTipo2 tipo2) throws SQLException
    {
        clsConexion conexion=new clsConexion(sourceUrl,usuario,password);
        Utils utils = new Utils();

        try
        {
            conexion.abrirConexion();
            CallableStatement cStmt = conexion.getConnexionBaseDatos().prepareCall("{call RealizarApuestaTipo2(?,?,?,?,?,?,?)}");
            cStmt.setString(1, utils.GregorianCalendarToDateTime(tipo2.getFecha()));
            cStmt.setDouble(2, tipo2.getCuota());
            cStmt.setDouble(3, tipo2.getCantidadapostada());
            cStmt.setInt(4, tipo2.getIdPartido());
            cStmt.setInt(5, tipo2.getIdUsuario());
            cStmt.setString(6, Character.toString(tipo2.getLocVis()));
            cStmt.setShort(7, tipo2.getGoles());
            cStmt.execute();
        }
        catch(SQLException e)
        {
            throw e;
        }
        finally
        {
            conexion.cerrarConexion();
        }
    }

    ///TENGO QUE CAMBIARLO

    /**
     * Realiza una apuesta de tipo 3, insertándola en la base de datos y descontándole el saldo al usuario que la realiza.
     * @param tipo3 La apuesta a realizar.
     * @throws SQLException Lanza SQLException si hay algún error con la conexión a la base de datos.
     * @param tipo3
     * @throws SQLException
     */
    public void RealizarApuestaTipo3(clsTipo3 tipo3) throws SQLException
    {
        clsConexion conexion = new clsConexion(sourceUrl, usuario, password);
        conexion.abrirConexion();
        Utils utils = new Utils();

        try
        {
        CallableStatement cStmt = conexion.getConnexionBaseDatos().prepareCall("{call RealizarApuestaTipo3(?,?,?,?,?,?)}");
        cStmt.setString(1, utils.GregorianCalendarToDateTime(tipo3.getFecha()));
        cStmt.setDouble(2, tipo3.getCuota());
        cStmt.setDouble(3, tipo3.getCantidadapostada());
        cStmt.setInt(4, tipo3.getIdPartido());
        cStmt.setInt(5, tipo3.getIdUsuario());
        cStmt.setShort(6, tipo3.getEquipoGanador());
        cStmt.execute();
        }
        catch(SQLException e)
        {
            throw e;
        }
        finally
        {
            conexion.cerrarConexion();
        }
    }

    //hay que hacer procedure apuesta anterior

    // Yo este método no lo veo, según lo que dijo Antonio lo de consultar el resultado de una apuesta anterior se refería a que el usuario podía seleccionar una de las apuestas
    // que ya había hecho y ver el resultado, para eso solo basta con el método de obtenerApuesta.
    public void resultadoApuestaAnterior(int idApuesta) throws SQLException
    {
        clsConexion conexion = new clsConexion(sourceUrl, usuario, password);

        try
        {
            conexion.abrirConexion();
            CallableStatement cStmt = conexion.getConnexionBaseDatos().prepareCall("{call ApuestaAnterior(?)}");
            cStmt.setInt(1, idApuesta);
            cStmt.execute();
        }
        catch(SQLException e)
        {
            throw e;
        }
        finally
        {
            conexion.cerrarConexion();
        }
    }

    /**
     * Obtiene una apuesta de la base de datos dado su ID.
     * @param id El ID de la apuesta a obtener.
     * @return La apuesta obtenida de la base de datos.
     * @throws SQLException Lanza SQLException si hay algun error relacionado con la base de datos.
     */
    public clsApuestas obtenerApuesta(int id) throws SQLException
    {
        clsApuestas apuesta = null;
        clsConexion conexion = new clsConexion(sourceUrl,usuario,password);

        String consulta =
                "SELECT * FROM Apuestas AS Ap " +
                        "FULL OUTER JOIN Tipos1 AS T1 ON T1.id = Ap.id " +
                        "FULL OUTER JOIN Tipos2 AS T2 ON T2.id = Ap.id " +
                        "FULL OUTER JOIN Tipos3 AS T3 ON T3.id = Ap.id " +
                        "WHERE Ap.ID = ?";

        try {
            conexion.abrirConexion();
            PreparedStatement statement = conexion.getConnexionBaseDatos().prepareStatement(consulta);
            statement.setInt(1, id);
            ResultSet resultado = statement.executeQuery();

            apuesta = resultSetToListApuestas(resultado).get(0);
        }
        catch(SQLException e)
        {
            throw e;
        }
        finally
        {
            conexion.cerrarConexion();
        }

        return apuesta;
    }

    // La idea es que hagas un select a la tabla apuestas donde el ID del partido sea igual al ID del partido que se le pasa por parametro y devuelvas esa lista.
    public ArrayList<clsApuestas> obtenerApuestasDeUnPartido(clsPartidos partidoAp) throws SQLException {
        ArrayList<clsApuestas> apuestas = new ArrayList<clsApuestas>();
        clsConexion conexion = new clsConexion(sourceUrl,usuario,password);

        String consulta =
                "SELECT * FROM Apuestas AS Ap " +
                        "FULL OUTER JOIN Tipos1 AS T1 ON T1.id = Ap.id " +
                        "FULL OUTER JOIN Tipos2 AS T2 ON T2.id = Ap.id " +
                        "FULL OUTER JOIN Tipos3 AS T3 ON T3.id = Ap.id " +
                        "WHERE IDpartido = ?";

        try
        {
            conexion.abrirConexion();
            PreparedStatement statement = conexion.getConnexionBaseDatos().prepareStatement(consulta);
            statement.setInt(1, partidoAp.getIdPartidos());
            ResultSet resultado = statement.executeQuery();

            apuestas = resultSetToListApuestas(resultado);
        }
        catch(SQLException e)
        {
            throw e;
        }
        finally
        {
            conexion.cerrarConexion();
        }

        return apuestas;
    }

    private ArrayList<clsApuestas> resultSetToListApuestas(ResultSet resultSet) throws SQLException {
        clsApuestas apuesta = null;
        ArrayList<clsApuestas> apuestas = new ArrayList<clsApuestas>();
        
        Utils convertidora = new Utils();

        int id;
        GregorianCalendar fecha;
        Double cuota;
        Double cantidadApostada;
        int idPartido;
        int idUsuario;
        short idTipo;

        short golesLocal;
        short golesVisitante;

        char localOVisitante;
        short goles;

        short equipoGanador;

        while (resultSet.next()) {
                convertidora = new Utils();
                id = resultSet.getInt("id");
                fecha = convertidora.dateTimeToGregorianCalendar(resultSet.getString("fecha"));
                cuota = resultSet.getDouble("cuota");
                cantidadApostada = resultSet.getDouble("cantidadApostada");
                idPartido = resultSet.getInt("IDpartido");
                idUsuario = resultSet.getInt("IDusuario");
                idTipo = resultSet.getShort("IDTipo");

                switch(idTipo)
                {
                    case 1:
                        golesLocal = (short)resultSet.getInt("golesLocal");
                        golesVisitante = (short)resultSet.getInt("golesVisitante");
                        apuesta = new clsTipo1(id, fecha, cuota, cantidadApostada, idPartido, idUsuario, golesLocal, golesVisitante);
                        break;
                    case 2:
                        localOVisitante = resultSet.getString("LocVis").charAt(0);
                        goles = (short)resultSet.getInt("goles");
                        apuesta = new clsTipo2(id, fecha, cuota, cantidadApostada, idPartido, idUsuario, localOVisitante, goles);
                        break;
                    case 3:
                        equipoGanador = (short)resultSet.getInt("equipoGanador");
                        apuesta = new clsTipo3(id, fecha, cuota, cantidadApostada, idPartido, idUsuario, equipoGanador);
                        break;
                    default:
                        apuesta = null;
                }

                if(apuesta != null)
                    apuestas.add(apuesta);
            }

        return apuestas;
    }


    public void pagarApuestasGanadoras() throws SQLException
    {
        clsConexion conexion = new clsConexion(sourceUrl, usuario, password);

        try
        {
            conexion.abrirConexion();
            CallableStatement cStmt = conexion.getConnexionBaseDatos().prepareCall("{call MofificacionDiariaResultadosApuesta}");
            cStmt.execute();
        }
        catch(SQLException e)
        {
            throw e;
        }
        finally
        {
            conexion.cerrarConexion();
        }
    }

    // Aquí igual que en el de obtenerApuestasDeUnPartido pero con un usuario, haces una consulta a la tabla apuestas donde el ID del usuario sea igual que el del parametro.
    public ArrayList<clsApuestas> obtenerApuestasDeUnUsuario(clsUsuarios usuarioAp) throws SQLException
    {
        ArrayList<clsApuestas> apuestas = new ArrayList<clsApuestas>();
        clsConexion conexion = new clsConexion(sourceUrl,usuario,password);

        String consulta =
                "SELECT * FROM Apuestas AS Ap " +
                        "FULL OUTER JOIN Tipos1 AS T1 ON T1.id = Ap.id " +
                        "FULL OUTER JOIN Tipos2 AS T2 ON T2.id = Ap.id " +
                        "FULL OUTER JOIN Tipos3 AS T3 ON T3.id = Ap.id " +
                        "WHERE IDUsuario = ?";

        try {
            conexion.abrirConexion();
            PreparedStatement statement = conexion.getConnexionBaseDatos().prepareStatement(consulta);

            statement.setInt(1, usuarioAp.getId());
            ResultSet resultado = statement.executeQuery();

            apuestas = resultSetToListApuestas(resultado);
        }
        catch(SQLException e)
        {
            throw e;
        }
        finally
        {
            conexion.cerrarConexion();
        }

        return apuestas;
    }

    public ArrayList<clsTotalApostado> obtenerTotalApostadoDeUnPartidoTipo1(int idPartido) throws SQLException
    {
        ArrayList<clsTotalApostado> resultadosAgrupados = new ArrayList<clsTotalApostado>();
        clsTotalApostadoTipo1 totalApostadoTipo1;
        clsConexion conexion = new clsConexion(sourceUrl,usuario,password);

        double cantidad;
        short golesLocal, golesVisitante;

        String consulta =
                        "SELECT SUM(A.cantidadApostada) AS cantidadApostada, A.IDpartido, T1.golesLocal, T1.golesVisitante FROM Apuestas AS A " +
                        "INNER JOIN Tipos1 AS T1 ON T1.ID = A.id " +
                        "WHERE A.IDpartido = ? " +
                        "GROUP BY A.IDpartido, T1.golesLocal, T1.golesVisitante";

        try
        {
            conexion.abrirConexion();
            PreparedStatement statement = conexion.getConnexionBaseDatos().prepareStatement(consulta);

            statement.setInt(1, idPartido);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next())
            {
                cantidad = resultado.getDouble("cantidadApostada");
                golesLocal = resultado.getShort("golesLocal");
                golesVisitante = resultado.getShort("golesVisitante");

                totalApostadoTipo1 = new clsTotalApostadoTipo1(cantidad, idPartido, golesLocal, golesVisitante);

                resultadosAgrupados.add(totalApostadoTipo1);
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

        return resultadosAgrupados;
    }

    public double obtenerSumaCantidadApuestasTipo1(int idPartido) throws SQLException {
        double sumaTotal =0;
        ArrayList<clsTotalApostado> lista;

        try {
            lista = obtenerTotalApostadoDeUnPartidoTipo1(idPartido);

            for(clsTotalApostado total : lista)
            {
                sumaTotal += total.getCantidad();
            }
        }
        catch(SQLException e){
            throw e;
        }
        return sumaTotal;
    }
    public double obtenerSumaCantidadApuestasTipo2(int idPartido) throws SQLException {
        double sumaTotal =0;
        ArrayList<clsTotalApostado> lista;

        try {
            lista = obtenerTotalApostadoDeUnPartidoTipo2(idPartido);

            for(clsTotalApostado total : lista)
            {
                sumaTotal += total.getCantidad();
            }
        }
        catch(SQLException e){
            throw e;
        }
        return sumaTotal;
    }
    public double obtenerSumaCantidadApuestasTipo3(int idPartido) throws SQLException {
        double sumaTotal =0;
        ArrayList<clsTotalApostado> lista;

        try {
            lista = obtenerTotalApostadoDeUnPartidoTipo3(idPartido);

            for(clsTotalApostado total : lista)
            {
                sumaTotal += total.getCantidad();
            }
        }
        catch(SQLException e){
            throw e;
        }
        return sumaTotal;
    }

    public ArrayList<clsTotalApostado> obtenerTotalApostadoDeUnPartidoTipo2(int idPartido) throws SQLException
    {
        ArrayList<clsTotalApostado> resultadosAgrupados = new ArrayList<clsTotalApostado>();
        clsTotalApostadoTipo2 totalApostadoTipo2;
        clsConexion conexion = new clsConexion(sourceUrl,usuario,password);

        double cantidad;
        short goles;
        char localOVisitante;

        String consulta =
                        "SELECT SUM(A.cantidadApostada) AS cantidadApostada, A.IDpartido, T2.LocVis, T2.goles FROM Apuestas AS A " +
                        "INNER JOIN Tipos2 AS T2 ON T2.ID = A.id " +
                        "WHERE A.IDpartido = ? " +
                        "GROUP BY A.IDpartido, T2.LocVis, T2.goles";

        try
        {
            conexion.abrirConexion();
            PreparedStatement statement = conexion.getConnexionBaseDatos().prepareStatement(consulta);

            statement.setInt(1, idPartido);

            ResultSet resultado = statement.executeQuery();

            while (resultado.next())
            {
                cantidad = resultado.getDouble("cantidadApostada");
                localOVisitante = resultado.getString("LocVis").charAt(0);
                goles = resultado.getShort("goles");

                totalApostadoTipo2 = new clsTotalApostadoTipo2(cantidad, idPartido, localOVisitante, goles);

                resultadosAgrupados.add(totalApostadoTipo2);
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

        return resultadosAgrupados;
    }

    public ArrayList<clsTotalApostado> obtenerTotalApostadoDeUnPartidoTipo3(int idPartido) throws SQLException
    {
        ArrayList<clsTotalApostado> resultadosAgrupados = new ArrayList<clsTotalApostado>();
        clsTotalApostadoTipo3 totalApostadoTipo3;
        clsConexion conexion = new clsConexion(sourceUrl,usuario,password);

        double cantidad;
        short equipoGanador;

        String consulta =
                        "SELECT SUM(A.cantidadApostada) AS cantidadApostada, A.IDpartido, T3.equipoGanador FROM Apuestas AS A " +
                        "INNER JOIN Tipos3 AS T3 ON T3.ID = A.id " +
                        "WHERE A.IDpartido = ? " +
                        "GROUP BY A.IDpartido, T3.equipoGanador";

        try
        {
            conexion.abrirConexion();
            PreparedStatement statement = conexion.getConnexionBaseDatos().prepareStatement(consulta);

            statement.setInt(1, idPartido);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next())
            {
                cantidad = resultado.getDouble("cantidadApostada");
                equipoGanador = resultado.getShort("equipoGanador");

                totalApostadoTipo3 = new clsTotalApostadoTipo3(cantidad, idPartido, equipoGanador);

                resultadosAgrupados.add(totalApostadoTipo3);
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

        return resultadosAgrupados;
    }

    public ArrayList<clsTotalApostado> obtenerTotalApostadoDeUnPartido(int idPartido) throws SQLException
    {
        ArrayList<clsTotalApostado> totalApostado = new ArrayList<clsTotalApostado>();

        try
        {
            totalApostado.addAll(obtenerTotalApostadoDeUnPartidoTipo1(idPartido));
            totalApostado.addAll(obtenerTotalApostadoDeUnPartidoTipo2(idPartido));
            totalApostado.addAll(obtenerTotalApostadoDeUnPartidoTipo3(idPartido));
        }
        catch (SQLException e)
        {
            throw e;
        }

        return totalApostado;
    }

    public double calcularCuota(clsApuestas apuesta) throws SQLException
    {
        double T = 0;
        double F = 0;
        ArrayList<clsTotalApostado> totalApostadoAlPartido = new ArrayList<clsTotalApostado>();
        clsTotalApostadoTipo1 t1;
        clsTotalApostadoTipo2 t2;
        clsTotalApostadoTipo3 t3;

        if(apuesta instanceof clsTipo1)
        {
            //Tipo 1
            totalApostadoAlPartido = obtenerTotalApostadoDeUnPartidoTipo1(apuesta.getIdPartido());

            for(clsTotalApostado t : totalApostadoAlPartido)
            {
                t1 = (clsTotalApostadoTipo1) t;

                if(t1.getGolesLocal() == apuesta.getGolesLocal() && t1.getGolesVisitante() == apuesta.getGolesVisitante())
                    F += t1.getCantidad();
            }
        }
        else if(apuesta instanceof clsTipo2)
        {
            //Tipo 2
            totalApostadoAlPartido = obtenerTotalApostadoDeUnPartidoTipo2(apuesta.getIdPartido());

            for(clsTotalApostado t : totalApostadoAlPartido)
            {
                t2 = (clsTotalApostadoTipo2) t;

                if(t2.getGoles() == ((clsTipo2) apuesta).getGoles() && t2.getLocalOVisitante() == ((clsTipo2) apuesta).getLocVis())
                    F += t2.getCantidad();
            }
        }
        else if(apuesta instanceof clsTipo3)
        {
            //Tipo 3
            totalApostadoAlPartido = obtenerTotalApostadoDeUnPartidoTipo3(apuesta.getIdPartido());

            for(clsTotalApostado t : totalApostadoAlPartido)
            {
                t3 = (clsTotalApostadoTipo3) t;

                if(t3.getEquipoGanador() == ((clsTipo3) apuesta).getEquipoGanador())
                    F += t3.getCantidad();
            }
        }

        for(clsTotalApostado t : totalApostadoAlPartido)
        {
            T += t.getCantidad();
        }

        return ((T/F)-1)*0.8;
    }

    /*
     0 -> El partido todavia no ha terminado
     1 -> La apuesta resulto ganadora
     2 -> La apuesta no resulto ganadora
     */
    public int esApuestaGanadora(clsApuestas apuesta) throws SQLException {
        int resultado = 2;
        ManejadoraPartidos manejadoraPartidos = new ManejadoraPartidos();
        clsTipo1 tipo1;
        clsTipo2 tipo2;
        clsTipo3 tipo3;

        clsPartidos partido = manejadoraPartidos.obtenerPartidoPorId(apuesta.getIdPartido());

        if(partido.getFechaFin().getTimeInMillis() < new GregorianCalendar().getTimeInMillis())
        {
            switch (apuesta.getIdTipo()) {
                case 1:
                    tipo1 = (clsTipo1) apuesta;

                    if (tipo1.getGolesLocal() == partido.getGolesLocal() && tipo1.getGolesVisitante() == partido.getGolesVisitante())
                        resultado = 1;
                    break;

                case 2:
                    tipo2 = (clsTipo2) apuesta;

                    if (tipo2.getLocVis() == 'L') {
                        if (tipo2.getGoles() == partido.getGolesLocal())
                            resultado = 1;
                    } else if (tipo2.getGoles() == partido.getGolesVisitante())
                        resultado = 1;

                    break;

                case 3:
                    tipo3 = (clsTipo3) apuesta;

                    if (tipo3.getEquipoGanador() == 1 && partido.getGolesLocal() > partido.getGolesVisitante()) //Local
                        resultado = 1;
                    else if (tipo3.getEquipoGanador() == 2 && partido.getGolesVisitante() == partido.getGolesVisitante()) //Empate
                        resultado = 1;
                    else if (partido.getGolesLocal() < partido.getGolesVisitante())
                        resultado = 1;
            }
        }
        else
            resultado = 0;

        return resultado;
    }
}
