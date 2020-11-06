package Entidades;

import java.util.GregorianCalendar;

public class clsTipo2 extends clsApuestas {
    private char _LocVis;
    private short _goles;

    public clsTipo2(){

    }
    public clsTipo2(int idApuestas, GregorianCalendar fecha, double cuota, double cantidadapostada, int idPartido, int idUsuario, char LocVis, short goles){
        super(idApuestas, fecha, cuota, cantidadapostada, (short)idPartido, (short)idUsuario, (short)2);
        this._LocVis = LocVis;
        this._goles = goles;
    }

    public clsTipo2(GregorianCalendar fecha, double cuota, double cantidadapostada, int idPartido, int idUsuario, char LocVis, short goles){
        super(fecha, cuota, cantidadapostada, (short)idPartido, (short)idUsuario, (short)2);
        this._LocVis = LocVis;
        this._goles = goles;
    }

    public char getLocVis() {
        return _LocVis;
    }

    public void setLocVis(char locVis) {
        _LocVis = locVis;
    }

    public short getGoles() {
        return _goles;
    }

    public void setGoles(short goles) {
        this._goles = goles;
    }
}
