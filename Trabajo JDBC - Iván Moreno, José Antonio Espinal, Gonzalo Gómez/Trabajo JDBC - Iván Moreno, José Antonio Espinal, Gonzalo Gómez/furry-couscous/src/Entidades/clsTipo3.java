package Entidades;

import java.util.GregorianCalendar;

public class clsTipo3 extends clsApuestas{
    private short _equipoGanador;

    public clsTipo3(){

    }

    public clsTipo3(int idApuestas, GregorianCalendar fecha, double cuota, double cantidadapostada, int idPartido, int idUsuario, short equipoGanador){
        super(idApuestas, fecha, cuota, cantidadapostada, (short)idPartido, (short)idUsuario, (short)3);
        this._equipoGanador = equipoGanador;
    }

    public clsTipo3(GregorianCalendar fecha, double cuota, double cantidadapostada, int idPartido, int idUsuario, short equipoGanador){
        super(fecha, cuota, cantidadapostada, (short)idPartido, (short)idUsuario, (short)3);
        this._equipoGanador = equipoGanador;
    }

    public short getEquipoGanador() {
        return _equipoGanador;
    }

    public void setEquipoGanador(short equipoGanador) {
        this._equipoGanador = equipoGanador;
    }
}
