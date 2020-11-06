package Entidades;

import java.util.GregorianCalendar;

public class clsTipo1 extends clsApuestas{
    private short _golesLocal;
    private short _golesVisitante;

    public clsTipo1(){

    }

    public clsTipo1(int idApuestas, GregorianCalendar fecha, double cuota, double cantidadapostada, int idPartido, int idUsuario, short golesLocal, short golesVisitante){
        super(idApuestas, fecha, cuota, cantidadapostada, (short)idPartido, (short)idUsuario, (short)1);
        this._golesLocal = golesLocal;
        this._golesVisitante = golesVisitante;
    }

    public clsTipo1(GregorianCalendar fecha, double cuota, double cantidadapostada, int idPartido, int idUsuario, short golesLocal, short golesVisitante){
        super(fecha, cuota, cantidadapostada, (short)idPartido, (short)idUsuario, (short)1);
        this._golesLocal = golesLocal;
        this._golesVisitante = golesVisitante;
    }


    public short getGolesLocal() {
        return _golesLocal;
    }

    public void setGolesLocal(short golesLocal) {
        this._golesLocal = golesLocal;
    }

    public short getGolesVisitante() {
        return _golesVisitante;
    }

    public void setGolesVisitante(short golesVisitante) {
        this._golesVisitante = golesVisitante;
    }
}
