package Entidades;

import java.util.GregorianCalendar;


public abstract class clsApuestas extends clsPartidos {
    private int _idApuestas;
    private GregorianCalendar _fecha;
    private double _cuota;
    private double _cantidadapostada;
    private int _idPartido;
    private int _idUsuario;
    private short _idTipo;

    //Constructor por defecto y por parametros
    public clsApuestas() {
    }

    public clsApuestas(int idApuestas, GregorianCalendar fecha, double cuota, double cantidadapostada, int idPartido, int idUsuario, short idTipo) {
        this._idApuestas = idApuestas;
        this._fecha = fecha;
        this._cuota = cuota;
        this._cantidadapostada = cantidadapostada;
        this._idPartido = idPartido;
        this._idUsuario = idUsuario;
        this._idTipo = idTipo;
    }

    public clsApuestas(GregorianCalendar fecha, double cuota, double cantidadapostada, int idPartido, int idUsuario, short idTipo) {
        this._idApuestas = 0;
        this._fecha = fecha;
        this._cuota = cuota;
        this._cantidadapostada = cantidadapostada;
        this._idPartido = idPartido;
        this._idUsuario = idUsuario;
        this._idTipo = idTipo;
    }

    //Getters y setters
    public int getId() {
        return _idApuestas;
    }

    public void setId(int idApuestas) {
        this._idApuestas = idApuestas;
    }

    public GregorianCalendar getFecha() {
        return _fecha;
    }

    public void setFecha(GregorianCalendar fecha) {
        this._fecha = fecha;
    }

    public double getCuota() {
        return _cuota;
    }

    public void setCuota(double cuota) {
        this._cuota = cuota;
    }

    public double getCantidadapostada() {
        return _cantidadapostada;
    }

    public void setCantidadapostada(double cantidadapostada) {
        this._cantidadapostada = cantidadapostada;
    }

    public int getIdPartido() {
        return _idPartido;
    }

    public void setIdPartido(int idPartido) {
        this._idPartido = idPartido;
    }

    public int getIdUsuario() {
        return _idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this._idUsuario = idUsuario;
    }

    public short getIdTipo() {
        return _idTipo;
    }

    public void setIdTipo(short idTipo) {
        this._idTipo = idTipo;
    }

    @Override
    public String toString() {
        return _fecha.getTime().toString() + " - " + _cantidadapostada + " €";
    }
}