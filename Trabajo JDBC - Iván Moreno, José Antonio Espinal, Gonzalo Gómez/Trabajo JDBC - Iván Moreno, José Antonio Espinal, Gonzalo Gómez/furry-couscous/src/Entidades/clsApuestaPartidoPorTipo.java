package Entidades;

import java.util.GregorianCalendar;

public class clsApuestaPartidoPorTipo extends clsApuestas {
    private int _idPartido;
    //private int _idApuesta;
    private double _totalApostadoTipo1;
    private double _totalApostadoTipo2;
    private double _totalApostadoTipo3;

    public int get_idPartido() {
        return _idPartido;
    }

    public void set_idPartido(int idPartido) {
        this._idPartido = idPartido;
    }

    /* public int get_idApuesta() {
        return _idApuesta;
    }

    public void set_idApuesta(int idApuesta) {
        this._idApuesta = idApuesta;
    } */

    public double get_totalApostadoTipo1() {
        return _totalApostadoTipo1;
    }

    public void set_totalApostadoTipo1(double totalApostadoTipo1) {
        this._totalApostadoTipo1 = totalApostadoTipo1;
    }

    public double get_totalApostadoTipo2() {
        return _totalApostadoTipo2;
    }

    public void set_totalApostadoTipo2(double totalApostadoTipo2) {
        this._totalApostadoTipo2 = totalApostadoTipo2;
    }

    public double get_totalApostadoTipo3() {
        return _totalApostadoTipo3;
    }

    public void set_totalApostadoTipo3(double totalApostadoTipo3) {
        this._totalApostadoTipo3 = totalApostadoTipo3;
    }

    public clsApuestaPartidoPorTipo(){

    }

    public clsApuestaPartidoPorTipo(int idApuestas, GregorianCalendar fecha, double cuota, double cantidadapostada, int idPartido, int idUsuario, short idTipo, int _idPartido, /* int _idApuesta, */ double _totalApostadoTipo1, double _totalApostadoTipo2, double _totalApostadoTipo3) {
        super(idApuestas, fecha, cuota, cantidadapostada, idPartido, idUsuario, idTipo);

        this._idPartido = _idPartido;
        //this._idApuesta = _idApuesta;
        this._totalApostadoTipo1 = _totalApostadoTipo1;
        this._totalApostadoTipo2 = _totalApostadoTipo2;
        this._totalApostadoTipo3 = _totalApostadoTipo3;
    }
}
