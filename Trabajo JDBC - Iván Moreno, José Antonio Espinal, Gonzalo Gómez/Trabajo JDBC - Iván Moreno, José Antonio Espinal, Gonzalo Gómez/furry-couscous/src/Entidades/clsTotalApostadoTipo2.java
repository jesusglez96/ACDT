package Entidades;

public class clsTotalApostadoTipo2 extends clsTotalApostado
{
    private char localOVisitante;
    private short goles;

    public clsTotalApostadoTipo2(double cantidad, int IDPartido, char localOVisitante, short goles) {
        super(cantidad, IDPartido);
        this.localOVisitante = localOVisitante;
        this.goles = goles;
    }

    public char getLocalOVisitante() {
        return localOVisitante;
    }

    public void setLocalOVisitante(char localOVisitante) {
        this.localOVisitante = localOVisitante;
    }

    public short getGoles() {
        return goles;
    }

    public void setGoles(short goles) {
        this.goles = goles;
    }

    @Override
    public String toString()
    {
        String localOVisitante = "Local";
        if(getLocalOVisitante() == 'V') localOVisitante = "Visitante";

        return "TIPO 2: " + localOVisitante + " - " + getGoles() + " goles | " + getCantidad() + " €";
    }
}
