package Entidades;

public abstract class clsTotalApostado
{
    private double cantidad;
    private int IDPartido;

    public clsTotalApostado(double cantidad, int IDPartido) {
        this.cantidad = cantidad;
        this.IDPartido = IDPartido;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getIDPartido() {
        return IDPartido;
    }

    public void setIDPartido(int IDPartido) {
        this.IDPartido = IDPartido;
    }
}
