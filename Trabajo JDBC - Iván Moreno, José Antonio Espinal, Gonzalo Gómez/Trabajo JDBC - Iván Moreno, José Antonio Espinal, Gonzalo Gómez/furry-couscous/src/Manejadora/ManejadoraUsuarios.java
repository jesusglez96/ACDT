package Manejadora;

import Entidades.clsMovimientos;
import Entidades.clsUsuarios;
import Utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/*

- Deberías meter en una clase conexion la parte de abrir la conexion en cada método, y usar esa clase en lugar de repetir siempre el mismo codigo.
*/

public class ManejadoraUsuarios {

    static final String sourceUrl="jdbc:sqlserver://localhost";
    static final String usuario="pepito";
    static final String password="contraseña";

    //TODO Procedures IngresarDinero y RetirarDinero no lo tiene la bbdd (los tenemos que hacer nosotros)

    public void IngresarDinero(Double cantidad,int idUsuario) throws SQLException {
        clsConexion conexion=new clsConexion(sourceUrl,usuario,password);
        conexion.abrirConexion();
        CallableStatement cStmt = conexion.getConnexionBaseDatos().prepareCall("{call IngresarDinero(?,?)}");
        cStmt.setDouble(1,cantidad);
        cStmt.setInt(2,idUsuario);
        cStmt.execute();
        conexion.cerrarConexion();
    }

    public void RetirarDinero(Double cantidad, int idUsuario) throws SQLException {
        clsConexion conexion=new clsConexion(sourceUrl,usuario,password);
        conexion.abrirConexion();
        CallableStatement cStmt = conexion.getConnexionBaseDatos().prepareCall("{call RetirarDinero(?,?)}");
        cStmt.setDouble(1,cantidad);
        cStmt.setInt(2,idUsuario);
        cStmt.execute();
        conexion.cerrarConexion();
    }

    // Easy. haces un select a la tabla usuarios donde la ID sea igual a la del parámetro y devuelves lo que te da.
    public clsUsuarios obtenerUsuario(String nickUsu) throws SQLException
    {
        int idUsu;
        String nick, pass, email;
        double saldo;
        char tipo;

        clsUsuarios usuarioOb = null;
        clsConexion conexion=new clsConexion(sourceUrl,usuario,password);
        try{
            conexion.abrirConexion();
            CallableStatement cStmt = conexion.getConnexionBaseDatos().prepareCall("SELECT * FROM Usuarios WHERE nick = ?");
            cStmt.setString(1,nickUsu);
            cStmt.execute();
            ResultSet resultado = cStmt.executeQuery();
            while (resultado.next()) {
                idUsu = resultado.getInt("id");
                nick = resultado.getString("nick");
                pass = resultado.getString("pass");
                email = resultado.getString("email");
                saldo = resultado.getDouble("saldo");
                tipo = resultado.getString("tipo").charAt(0);

                usuarioOb = new clsUsuarios(idUsu, nick, pass, email, saldo, tipo);

            }
        }catch (SQLException e){
            throw e;
        }finally {
            conexion.cerrarConexion();
        }

        return usuarioOb;
    }


	// Haces una consulta a la tabla movimientos y en el where pones la ID del usuario del parámetro de entrada.
    public ArrayList<clsMovimientos> obtenerMovimientosDeUnUsuario(clsUsuarios usuario) throws SQLException
    {
        ArrayList<clsMovimientos> movimientos = new ArrayList<clsMovimientos>();
        clsConexion conexion = new clsConexion(sourceUrl, ManejadoraUsuarios.usuario, password);
        ResultSet resultado;
        Utils utils = new Utils();

        clsMovimientos movimiento;
        int id, idUsuario;
        double incremento;
        GregorianCalendar fecha;

        try
        {
            conexion.abrirConexion();

            PreparedStatement statement = conexion.getConnexionBaseDatos().prepareStatement("SELECT id, incremento, IDusuario, fecha FROM Movimientos WHERE IDusuario = ?");

            statement.setInt(1, usuario.getId());

            resultado = statement.executeQuery();

            while(resultado.next())
            {
                id = resultado.getInt("id");
                incremento = resultado.getDouble("incremento");
                idUsuario = resultado.getInt("IDusuario");
                fecha = utils.dateTimeToGregorianCalendar(resultado.getString("fecha"));

                movimiento = new clsMovimientos(id, incremento, idUsuario, fecha);

                movimientos.add(movimiento);
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

        return movimientos;
    }

    //TODO crearUsuario
    public void crearUsuario(clsUsuarios usuario) throws SQLException
    {
        String consulta = "INSERT INTO Usuarios (nick, pass, email, saldo, tipo) VALUES (?, ?, ?, ?, ?)";
        clsConexion conexion = new clsConexion(sourceUrl, ManejadoraUsuarios.usuario, password);

        try
        {
            conexion.abrirConexion();
            PreparedStatement statement = conexion.getConnexionBaseDatos().prepareStatement(consulta);

            statement.setString(1, usuario.getNick());
            statement.setString(2, usuario.getPass());
            statement.setString(3, usuario.getEmail());
            statement.setDouble(4, usuario.getSaldo());
            statement.setString(5, String.valueOf(usuario.getTipo()));

            statement.execute();
        }
        catch(SQLException e)
        {
            throw e;
        }
    }
}