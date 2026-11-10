import java.util.Locale;

// Guarda cuántos kWh se han consumido en una franja tarifaria y calcula su monto.
// Se usa en cada oficina (acumulado del día) y en cada factura (copia del acumulado).
public class ConsumoFranja {

    // Franja a la que pertenece este acumulado.
    private FranjaTarifaria franja;

    // kWh acumulados en esa franja.
    private double kwh;

    // Constructor: inicia en cero kWh para la franja indicada.
    public ConsumoFranja(FranjaTarifaria franja) {
        if (franja == null) {
            throw new OperacionInvalidaException("La franja no puede ser nula.");
        }
        this.franja = franja;
        this.kwh = 0;
    }

    // Suma kWh al acumulado. Rechaza valores negativos para no alterar el consumo.
    public void agregarKwh(double kwh) {
        if (Double.isNaN(kwh) || Double.isInfinite(kwh) || kwh < 0) {
            throw new OperacionInvalidaException("Los kWh a acumular no pueden ser negativos.");
        }
        this.kwh += kwh;
    }

    // Monto en quetzales: la franja calcula el precio según su propia tarifa.
    public double calcularMonto() {
        return franja.calcularMonto(kwh);
    }

    // Crea una copia independiente (misma franja y mismos kWh). La factura guarda copias
    // para que al reiniciar el día de la oficina la factura no cambie.
    public ConsumoFranja copiar() {
        ConsumoFranja copia = new ConsumoFranja(franja);
        copia.kwh = this.kwh; // se puede acceder al campo privado porque es la misma clase
        return copia;
    }

    // Vuelve el acumulado a cero (se usa al cerrar el día).
    public void reiniciar() {
        this.kwh = 0;
    }

    // Devuelve la franja.
    public FranjaTarifaria getFranja() {
        return franja;
    }

    // Devuelve los kWh acumulados.
    public double getKwh() {
        return kwh;
    }

    // Texto: franja, kWh y monto con dos decimales.
    @Override
    public String toString() {
        return String.format(Locale.US, "%s: %.3f kWh -> Q%.2f", franja.getNombre(), kwh, calcularMonto());
    }
}