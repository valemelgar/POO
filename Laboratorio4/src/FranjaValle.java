// Franja valle: de las 22:00 a las 5:59, a Q0.85 por kWh.
public class FranjaValle extends FranjaTarifaria {

    // Constructor: le pasa al padre el nombre y la tarifa de esta franja.
    public FranjaValle() {
        super("Valle", 0.85);
    }

    // Sobrescribe el método abstracto: el valle cruza la medianoche, por eso usa "o".
    @Override
    public boolean contiene(int horaDelDia) {
        return horaDelDia >= 22 || horaDelDia < 6;
    }
}