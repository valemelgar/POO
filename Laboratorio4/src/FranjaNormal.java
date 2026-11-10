// Franja normal: de las 6:00 a las 17:59, a Q1.40 por kWh.
public class FranjaNormal extends FranjaTarifaria {

    // Constructor: le pasa al padre el nombre y la tarifa de esta franja.
    public FranjaNormal() {
        super("Normal", 1.40);
    }

    // Sobrescribe el método abstracto: horas 6 a 17 inclusive.
    @Override
    public boolean contiene(int horaDelDia) {
        return horaDelDia >= 6 && horaDelDia < 18;
    }
}