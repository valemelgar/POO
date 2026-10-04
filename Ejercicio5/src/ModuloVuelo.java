/**
 * Módulo de vuelo: cámara o sensor en órbita que recolecta datos científicos.
 * Hereda los atributos comunes de Modulo y sobrescribe procesarCiclo.
 */
public class ModuloVuelo extends Modulo {

    private final String tipoInstrumento;  // "camara" o "sensor"
    private final double datosPorCiclo;    // MB que recolecta en cada operación (constante)
    private final double consumoEnergia;   // Energía necesaria para operar un ciclo (constante)
    private int ciclosAcumulados;          // Ciclos en los que realmente recolectó datos

    // Constructor: inicializa los atributos comunes con super(...) y los propios, validando todo
    public ModuloVuelo(String id, String nombre, int salud, boolean activo, double costoConstruccion,
                       String tipoInstrumento, double datosPorCiclo, double consumoEnergia,
                       int ciclosAcumulados) {
        super(id, nombre, salud, activo, costoConstruccion); // Inicializa y valida lo común
        // El instrumento solo puede ser "camara" o "sensor" (sin importar mayúsculas)
        if (tipoInstrumento == null
                || !(tipoInstrumento.equalsIgnoreCase("camara") || tipoInstrumento.equalsIgnoreCase("sensor"))) {
            throw new IllegalArgumentException("El instrumento debe ser \"camara\" o \"sensor\".");
        }
        // Las cantidades deben ser positivas y los ciclos no negativos
        if (datosPorCiclo <= 0 || consumoEnergia <= 0 || ciclosAcumulados < 0) {
            throw new IllegalArgumentException("Valores inválidos en el módulo de vuelo.");
        }
        this.tipoInstrumento = tipoInstrumento.toLowerCase(); // Se guarda normalizado en minúsculas
        this.datosPorCiclo = datosPorCiclo;                   // Guarda los MB por ciclo
        this.consumoEnergia = consumoEnergia;                 // Guarda el consumo por ciclo
        this.ciclosAcumulados = ciclosAcumulados;             // Guarda los ciclos ya operados
    }

    /**
     * Sobrescribe procesarCiclo: si está activo y hay energía suficiente, consume la energía,
     * agrega los datos a la órbita e incrementa los ciclos acumulados.
     * Si no puede operar no modifica recursos ni acumulados y devuelve la causa.
     */
    @Override
    public String procesarCiclo(Mision mision) {
        // Si el módulo está inactivo no puede operar
        if (!isActivo()) {
            return getNombre() + " (Vuelo): no operó porque está inactivo.";
        }
        // consumirEnergia descuenta solo si alcanza; si devuelve false no hay energía suficiente
        if (!mision.consumirEnergia(consumoEnergia)) {
            return getNombre() + " (Vuelo): no operó porque no hay energía suficiente (requiere "
                    + consumoEnergia + ").";
        }
        mision.agregarDatosEnOrbita(datosPorCiclo); // Los datos recolectados quedan en órbita
        ciclosAcumulados++;                         // Cuenta este ciclo como operado
        return getNombre() + " (Vuelo): consumió " + consumoEnergia + " unidades y recolectó "
                + datosPorCiclo + " MB.";
    }

    // Devuelve el tipo de instrumento
    public String getTipoInstrumento() {
        return tipoInstrumento;
    }

    // Devuelve los MB que recolecta por ciclo
    public double getDatosPorCiclo() {
        return datosPorCiclo;
    }

    // Devuelve la energía que necesita por ciclo
    public double getConsumoEnergia() {
        return consumoEnergia;
    }

    // Devuelve los ciclos en los que operó
    public int getCiclosAcumulados() {
        return ciclosAcumulados;
    }

    // Calcula el histórico recolectado: datos por ciclo × ciclos en que operó
    public double getTotalRecolectado() {
        return datosPorCiclo * ciclosAcumulados;
    }

    // Texto con lo común (super.toString()), lo propio del módulo de vuelo y el histórico
    @Override
    public String toString() {
        return super.toString() + String.format(
                " | Tipo: Vuelo | Instrumento: %s | MB/ciclo: %.1f | Consumo: %.1f | Ciclos: %d | Total recolectado: %.1f MB",
                tipoInstrumento, datosPorCiclo, consumoEnergia, ciclosAcumulados, getTotalRecolectado());
    }
}