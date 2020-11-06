package Entidades;


import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

public class clsMovimientos extends clsUsuarios {
    private int _idMovimientos;
    private double _incremento;
    private int _IDusuario;
    private GregorianCalendar _fecha;

    //Constructor por defecto y por parametros
    public clsMovimientos(){

    }

    public clsMovimientos(int idMovimientos, double incremento, int IDusuario, GregorianCalendar fecha){
        this._idMovimientos = idMovimientos;
        this._incremento = incremento;
        this._IDusuario = IDusuario;
        this._fecha = fecha;
    }

    @Override
    public int getId() {
        return _idMovimientos;
    }

    @Override
    public void setId(int id) {
        this._idMovimientos = id;
    }

    public double getIncremento() {
        return _incremento;
    }

    public void setIncremento(double incremento) {
        this._incremento = incremento;
    }

    public int getIDusuario() {
        return _IDusuario;
    }

    public void setIDusuario(int IDusuario) {
        this._IDusuario = IDusuario;
    }

    public GregorianCalendar getFecha() {
        return _fecha;
    }

    public void setFecha(GregorianCalendar fecha) {
        this._fecha = fecha;
    }

    @Override
    public String toString() {
        return _fecha.getTime().toString() + " - " + _incremento + " €";
    }
}
