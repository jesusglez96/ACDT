package Entidades;

public class clsTotalApostadoTipo1 extends clsTotalApostado
{
    private short golesLocal;
    private short golesVisitante;

    public clsTotalApostadoTipo1(double cantidad, int IDPartido, short golesLocal, short golesVisitante)
    {
        super(cantidad, IDPartido);
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
    }

    public short getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(short golesLocal) {
        this.golesLocal = golesLocal;
    }

    public short getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(short golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    @Override
    public String toString() {
        return "TIPO 1: " + getGolesLocal() + " - " + getGolesVisitante() + " | Total apostado: " + getCantidad() + " €";
    }
}
