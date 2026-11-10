import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Factura diaria de una oficina: kWh y monto por franja, créditos aplicados y total a pagar.
// Guarda copias de los consumos, así no cambia cuando la oficina reinicia sus acumulados.
public class Factura {

    // Oficina facturada.
    private Oficina oficina;

    // Número de día cerrado (1, 2, 3, ...).
    private int dia;

    // Copias de los consumos por franja del día.
    private ArrayList<ConsumoFranja> consumos;

    // Total de créditos acreditados durante el día.
    private double creditos;

    // Constructor: guarda los datos; la lista se copia para no compartirla con la oficina.
    public Factura(Oficina oficina, int dia, List<ConsumoFranja> consumos, double creditos) {
        this.oficina = oficina;
        this.dia = dia;
        this.consumos = new ArrayList<ConsumoFranja>(consumos);
        this.creditos = creditos;
    }

    // Suma de los montos de todas las franjas (sin créditos y sin redondear).
    public double calcularSubtotal() {
        double subtotal = 0;
        for (ConsumoFranja consumo : consumos) {
            subtotal += consumo.calcularMonto();
        }
        return subtotal;
    }

    // Total a pagar: subtotal menos créditos, nunca menor que cero (el exceso no se traslada),
    // redondeado a dos decimales.
    public double calcularTotal() {
        double total = calcularSubtotal() - creditos;
        if (total < 0) {
            total = 0;
        }
        return Math.round(total * 100.0) / 100.0;
    }

    public Oficina getOficina() { return oficina; }
    public int getDia() { return dia; }
    public double getCreditos() { return creditos; }

    // Devuelve una copia de la lista de consumos (no expone la lista interna).
    public List<ConsumoFranja> getConsumos() {
        return new ArrayList<ConsumoFranja>(consumos);
    }

    // Texto de la factura con todos los montos a dos decimales.
    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder();
        texto.append("Factura del día ").append(dia).append(" - Oficina ").append(oficina.getCodigo())
             .append(" (").append(oficina.getInquilino()).append(")\n");
        for (ConsumoFranja consumo : consumos) {
            texto.append("   ").append(consumo).append("\n");
        }
        texto.append(String.format(Locale.US, "   Subtotal: Q%.2f\n", calcularSubtotal()));
        texto.append(String.format(Locale.US, "   Créditos aplicados: Q%.2f\n", creditos));
        texto.append(String.format(Locale.US, "   TOTAL A PAGAR: Q%.2f", calcularTotal()));
        return texto.toString();
    }
}