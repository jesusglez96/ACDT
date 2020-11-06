package Entidades;

public class clsTotalApostadoTipo3 extends clsTotalApostado
{
    private short equipoGanador;

    public clsTotalApostadoTipo3(double cantidad, int IDPartido, short equipoGanador) {
        super(cantidad, IDPartido);
        this.equipoGanador = equipoGanador;
    }

    public short getEquipoGanador() {
        return equipoGanador;
    }

    public void setEquipoGanador(short equipoGanador) {
        this.equipoGanador = equipoGanador;
    }

    @Override
    public String toString()
    {
        String resultado = "Gana local";
        if (getEquipoGanador() == 1) resultado = "Empate";
        else if (getEquipoGanador() == 2) resultado = "Gana visitante";

        return "TIPO 3: " + resultado + " | " + getCantidad() + " €";
    }
}
