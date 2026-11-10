import java.util.Locale;

// Clase abstracta que representa una franja tarifaria (valle, normal, punta).
// Guarda lo que tienen en común (nombre y tarifa) y deja abstracto SOLO lo que cambia:
// qué horas del día abarca cada franja.
public abstract class FranjaTarifaria {

    // Nombre de la franja (por ejemplo "Valle"). Privado: nadie lo cambia desde fuera.
    private String nombre;

    // Precio en quetzales de cada kWh consumido dentro de esta franja.
    private double tarifaPorKwh;

    // Constructor protegido: solo lo pueden llamar las subclases (FranjaValle, etc.).
    protected FranjaTarifaria(String nombre, double tarifaPorKwh) {
        // Valida que el nombre no sea nulo ni vacío.
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new OperacionInvalidaException("El nombre de la franja no puede estar vacío.");
        }
        // Valida que la tarifa sea un número positivo.
        if (Double.isNaN(tarifaPorKwh) || Double.isInfinite(tarifaPorKwh) || tarifaPorKwh <= 0) {
            throw new OperacionInvalidaException("La tarifa de la franja debe ser positiva.");
        }
        this.nombre = nombre.trim();       // guarda el nombre sin espacios sobrantes
        this.tarifaPorKwh = tarifaPorKwh;  // guarda la tarifa
    }

    // Método abstracto: cada franja decide si la hora del día (0 a 23) le pertenece.
    public abstract boolean contiene(int horaDelDia);

    // Calcula el monto en quetzales de una cantidad de kWh según la tarifa de la franja.
    public double calcularMonto(double kwh) {
        return kwh * tarifaPorKwh;
    }

    // Devuelve el nombre de la franja.
    public String getNombre() {
        return nombre;
    }

    // Devuelve la tarifa por kWh.
    public double getTarifaPorKwh() {
        return tarifaPorKwh;
    }

    // Texto para mostrar la franja: nombre y tarifa con dos decimales.
    @Override
    public String toString() {
        return nombre + " (Q" + String.format(Locale.US, "%.2f", tarifaPorKwh) + " por kWh)";
    }
}