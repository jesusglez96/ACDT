package Utils;

import java.util.GregorianCalendar;

public class Utils {
    public String GregorianCalendarToDateTime(GregorianCalendar fecha)
    {
        String dateTime;

        int anho = fecha.get(GregorianCalendar.YEAR);
        int mes = fecha.get(GregorianCalendar.MONTH);
        int dia = fecha.get(GregorianCalendar.DAY_OF_MONTH);

        int hora = fecha.get(GregorianCalendar.HOUR_OF_DAY);
        int minutos = fecha.get(GregorianCalendar.MINUTE);
        int segundos = fecha.get(GregorianCalendar.SECOND);

        dateTime = anho + "-" + dia + "-" + (mes+1) + " " + hora + ":" + minutos + ":" + segundos;

        return dateTime;
    }
    public GregorianCalendar dateTimeToGregorianCalendar(String datetime)
    {
        GregorianCalendar fecha;

        String[] campos = datetime.split(" ");

        String[] camposFecha = campos[0].split("-");

        String[] camposHora = campos[1].split(":");

        int anho = Integer.parseInt(camposFecha[0]);
        int mes = Integer.parseInt(camposFecha[1])-1;
        int dia = Integer.parseInt(camposFecha[2]);

        int hora = Integer.parseInt(camposHora[0]);
        int minutos = Integer.parseInt(camposHora[1]);
        int segundos = (int)Double.parseDouble(camposHora[2]);

        fecha = new GregorianCalendar(anho, mes, dia, hora, minutos, segundos);

        return fecha;
    }

    public static void main(String[] args)
    {
        Utils utils = new Utils();
        GregorianCalendar fecha = (utils.dateTimeToGregorianCalendar("2019-11-28 18:50:00"));

        //System.out.println(utils.dateTimeToGregorianCalendar("2019-11-28 18:50:00").getTime().toString());
        System.out.println(utils.GregorianCalendarToDateTime(fecha));
    }
}
