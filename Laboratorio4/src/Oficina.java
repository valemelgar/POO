import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

// Oficina de la torre: guarda sus dispositivos, el consumo acumulado del día por franja,
// los créditos del día y las facturas ya generadas.
public class Oficina {

    // Código único de la oficina.
    private String codigo;

    // Nombre del inquilino.
    private String inquilino;

    // Dispositivos instalados en la oficina.
    private ArrayList<Dispositivo> dispositivos;

    // Un acumulado de kWh por cada franja tarifaria (valle, normal, punta).
    private ArrayList<ConsumoFranja> consumosPorFranja;

    // Créditos en quetzales acumulados en el día en curso.
    private double creditosDia;

    // Facturas ya generadas (una por cada día cerrado).
    private ArrayList<Factura> facturas;

    // Constructor: crea un acumulador en cero por cada franja de la tarifa.
    public Oficina(String codigo, String inquilino, TarifaHoraria tarifa) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new OperacionInvalidaException("El código de la oficina no puede estar vacío.");
        }
        if (inquilino == null || inquilino.trim().isEmpty()) {
            throw new OperacionInvalidaException("El nombre del inquilino no puede estar vacío.");
        }
        this.codigo = codigo.trim();
        this.inquilino = inquilino.trim();
        this.dispositivos = new ArrayList<Dispositivo>();
        this.consumosPorFranja = new ArrayList<ConsumoFranja>();
        for (FranjaTarifaria franja : tarifa.getFranjas()) {
            consumosPorFranja.add(new ConsumoFranja(franja));
        }
        this.creditosDia = 0;
        this.facturas = new ArrayList<Factura>();
    }

    // Agrega un dispositivo. Verifica que pertenezca a esta oficina y que no esté repetido.
    public void agregarDispositivo(Dispositivo dispositivo) {
        if (dispositivo.getOficina() != this) {
            throw new OperacionInvalidaException("El dispositivo pertenece a otra oficina.");
        }
        if (dispositivos.contains(dispositivo)) {
            throw new OperacionInvalidaException("El dispositivo ya está registrado en la oficina.");
        }
        dispositivos.add(dispositivo);
    }

    // Devuelve una COPIA de los dispositivos ordenada por código (Dispositivo.compareTo).
    public List<Dispositivo> getDispositivosOrdenados() {
        ArrayList<Dispositivo> copia = new ArrayList<Dispositivo>(dispositivos);
        Collections.sort(copia);
        return copia;
    }

    // Consume UNA hora: cada dispositivo calcula su consumo (polimorfismo) y la suma se acumula
    // en la franja recibida.
    public void consumirHora(FranjaTarifaria franja) {
        double kwhHora = 0;
        for (Dispositivo dispositivo : dispositivos) {
            kwhHora += dispositivo.consumirHora();
        }
        for (ConsumoFranja consumo : consumosPorFranja) {
            if (consumo.getFranja() == franja) {
                consumo.agregarKwh(kwhHora);
            }
        }
    }

    // Suma un crédito al día en curso.
    public void acreditar(double monto) {
        if (Double.isNaN(monto) || monto < 0) {
            throw new OperacionInvalidaException("El crédito no puede ser negativo.");
        }
        creditosDia += monto;
    }

    // Cierra el día: genera la factura con COPIAS de los acumulados y deja todo en cero.
    public Factura cerrarDia(int dia) {
        ArrayList<ConsumoFranja> copias = new ArrayList<ConsumoFranja>();
        for (ConsumoFranja consumo : consumosPorFranja) {
            copias.add(consumo.copiar());
        }
        Factura factura = new Factura(this, dia, copias, creditosDia);
        facturas.add(factura);
        // Reinicio: acumulados, créditos y consumo diario de cada dispositivo.
        for (ConsumoFranja consumo : consumosPorFranja) {
            consumo.reiniciar();
        }
        creditosDia = 0;
        for (Dispositivo dispositivo : dispositivos) {
            dispositivo.reiniciarConsumoDia();
        }
        return factura;
    }

    public String getCodigo() { return codigo; }
    public String getInquilino() { return inquilino; }
    public double getCreditosDia() { return creditosDia; }

    // Copia de las facturas (no expone la lista interna).
    public List<Factura> getFacturas() {
        return new ArrayList<Factura>(facturas);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Oficina %s | Inquilino: %s | Dispositivos: %d | Créditos del día: Q%.2f",
                codigo, inquilino, dispositivos.size(), creditosDia);
    }
}