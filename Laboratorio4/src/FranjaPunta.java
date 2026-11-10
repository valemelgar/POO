// Franja punta: de las 18:00 a las 21:59, a Q2.10 por kWh.
public class FranjaPunta extends FranjaTarifaria {

    // Constructor: le pasa al padre el nombre y la tarifa de esta franja.
    public FranjaPunta() {
        super("Punta", 2.10);
    }

    // Sobrescribe el método abstracto: horas 18 a 21 inclusive.
    @Override
    public boolean contiene(int horaDelDia) {
        return horaDelDia >= 18 && horaDelDia < 22;
    }
}