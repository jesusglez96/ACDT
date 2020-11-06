package Entidades;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class clsPartidos {
    private int _idPartidos;
    private short _golesVisitante;
    private short _golesLocal;
    private String _campo;
    private GregorianCalendar _fechaInicio;          //No se yo si un tipo del paquete SQL (Timestamp) debería ir en la clase de persistencia (que poco o nada tiene que ver con SQL)
    private GregorianCalendar _fechaFin;
    private GregorianCalendar _inicioAbierto;
    private GregorianCalendar _finAbierto;
    private double _beneficioMax1;           //Este tipo de dato no dará problemas más adelante?
    private double _beneficioMax2;
    private double _beneficioMax3;

    public clsPartidos() {
    }

    public clsPartidos(int idPartidos, short golesVisitante, short golesLocal, String campo, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, GregorianCalendar inicioAbierto, GregorianCalendar finAbierto, double beneficioMax1, double beneficioMax2, double beneficioMax3) {
        this._idPartidos = idPartidos;
        this._golesVisitante = golesVisitante;
        this._golesLocal = golesLocal;
        this._campo = campo;
        this._fechaInicio = fechaInicio;
        this._fechaFin = fechaFin;
        this._inicioAbierto = inicioAbierto;
        this._finAbierto = finAbierto;
        this._beneficioMax1 = beneficioMax1;
        this._beneficioMax2 = beneficioMax2;
        this._beneficioMax3 = beneficioMax3;
    }

    public clsPartidos(short golesVisitante, short golesLocal, String campo, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, GregorianCalendar inicioAbierto, GregorianCalendar finAbierto, double beneficioMax1, double beneficioMax2, double beneficioMax3) {
        this._idPartidos = 0;
        this._golesVisitante = golesVisitante;
        this._golesLocal = golesLocal;
        this._campo = campo;
        this._fechaInicio = fechaInicio;
        this._fechaFin = fechaFin;
        this._inicioAbierto = inicioAbierto;
        this._finAbierto = finAbierto;
        this._beneficioMax1 = beneficioMax1;
        this._beneficioMax2 = beneficioMax2;
        this._beneficioMax3 = beneficioMax3;
    }

    public clsPartidos(int idPartidos)
    {
        this._idPartidos = idPartidos;
    }

    public int getIdPartidos() {
        return _idPartidos;
    }

    public void setIdPartidos(int idPartidos) {
        this._idPartidos = idPartidos;
    }

    public short getGolesVisitante() {
        return _golesVisitante;
    }

    public void setGolesVisitante(short golesVisitante) {
        this._golesVisitante = golesVisitante;
    }

    public short getGolesLocal() {
        return _golesLocal;
    }

    public void setGolesLocal(short golesLocal) {
        this._golesLocal = golesLocal;
    }

    public String getCampo() {
        return _campo;
    }

    public void setCampo(String campo) {
        this._campo = campo;
    }

    public GregorianCalendar getFechaInicio() {
        return _fechaInicio;
    }

    public void setFechaInicio(GregorianCalendar fechaInicio) {
        this._fechaInicio = fechaInicio;
    }

    public GregorianCalendar getFechaFin() {
        return _fechaFin;
    }

    public void setFechaFin(GregorianCalendar fechaFin) {
        this._fechaFin = fechaFin;
    }

    public GregorianCalendar getInicioAbierto() {
        return _inicioAbierto;
    }

    public void setInicioAbierto(GregorianCalendar inicioAbierto) {
        this._inicioAbierto = inicioAbierto;
    }

    public GregorianCalendar getFinAbierto() {
        return _finAbierto;
    }

    public void setFinAbierto(GregorianCalendar finAbierto) {
        this._finAbierto = finAbierto;
    }

    public double getBeneficioMax1() {
        return _beneficioMax1;
    }

    public void setBeneficioMax1(double beneficioMax1) {
        this._beneficioMax1 = beneficioMax1;
    }

    public double getBeneficioMax2() {
        return _beneficioMax2;
    }

    public void setBeneficioMax2(double beneficioMax2) {
        this._beneficioMax2 = beneficioMax2;
    }

    public double getBeneficioMax3() {
        return _beneficioMax3;
    }

    public void setBeneficioMax3(double beneficioMax3) {
        this._beneficioMax3 = beneficioMax3;
    }

    @Override
    public String toString()
    {
        String ret;

        if(_fechaFin.getTimeInMillis() > new GregorianCalendar().getTimeInMillis())
            ret = _fechaInicio.getTime().toString() + " - " + _fechaFin.getTime().toString() + " | Partido sin terminar" ;
        else
            ret = _fechaInicio.getTime().toString() + " - " + _fechaFin.getTime().toString() + " | Partido terminado (" + _golesLocal + " - " + _golesVisitante + ")";

        return ret;
    }
}
