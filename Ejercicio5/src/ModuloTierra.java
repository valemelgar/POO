/**
 * Módulo de tierra: antena del campus que descarga los datos que están en órbita.
 * Hereda de Modulo y sobrescribe procesarCiclo.
 */
public class ModuloTierra extends Modulo {

    private final String estacion;            // Estación terrestre a la que pertenece
    private final double capacidadDescarga;   // Máximo de MB que puede descargar por ciclo
    private final double energiaPorDescarga;  // Energía que cuesta una descarga completa
    private double totalDescargado;           // Histórico de MB descargados por este módulo

    // Constructor: llama a super(...) para lo común, guarda lo propio y valida los valores
    public ModuloTierra(String id, String nombre, int salud, boolean activo, double costoConstruccion,
                        String estacion, double capacidadDescarga, double energiaPorDescarga,
                        double totalDescargado) {
        super(id, nombre, salud, activo, costoConstruccion); // Inicializa y valida lo común
        // La estación no puede estar vacía
        if (estacion == null || estacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La estación no puede estar vacía.");
        }
        // Capacidad y energía deben ser positivas; el histórico no puede ser negativo
        if (capacidadDescarga <= 0 || energiaPorDescarga <= 0 || totalDescargado < 0) {
            throw new IllegalArgumentException("Valores inválidos en el módulo de tierra.");
        }
        this.estacion = estacion.trim();                  // Guarda la estación
        this.capacidadDescarga = capacidadDescarga;       // Guarda la capacidad por ciclo
        this.energiaPorDescarga = energiaPorDescarga;     // Guarda el costo de energía
        this.totalDescargado = totalDescargado;           // Guarda el histórico inicial
    }

    /**
     * Sobrescribe procesarCiclo: verifica que esté activo, que haya datos en órbita y que
     * exista energía. Consume la energía completa, descarga hasta su capacidad (sin superar
     * lo disponible) y aumenta su histórico. Si algo falla, no consume nada y devuelve la causa.
     */
    @Override
    public String procesarCiclo(Mision mision) {
        // Si el módulo está inactivo no puede descargar
        if (!isActivo()) {
            return getNombre() + " (Tierra): no descargó porque está inactivo.";
        }
        // Si no hay datos pendientes en órbita no hay nada que descargar
        if (mision.getDatosEnOrbita() <= 0) {
            return getNombre() + " (Tierra): no descargó porque no hay datos pendientes en órbita.";
        }
        // consumirEnergia solo descuenta si alcanza; false significa energía insuficiente
        if (!mision.consumirEnergia(energiaPorDescarga)) {
            return getNombre() + " (Tierra): no descargó porque no hay energía suficiente (requiere "
                    + energiaPorDescarga + ").";
        }
        double descargado = mision.descargarDatos(capacidadDescarga); // La misión transfiere min(capacidad, disponibles)
        totalDescargado += descargado;                                // Suma al histórico de este módulo
        return getNombre() + " (Tierra): consumió " + energiaPorDescarga + " unidades y descargó "
                + descargado + " MB.";
    }

    // Devuelve la estación terrestre
    public String getEstacion() {
        return estacion;
    }

    // Devuelve la capacidad máxima de descarga por ciclo
    public double getCapacidadDescarga() {
        return capacidadDescarga;
    }

    // Devuelve la energía que consume una descarga
    public double getEnergiaPorDescarga() {
        return energiaPorDescarga;
    }

    // Devuelve el histórico descargado por este módulo
    public double getTotalDescargado() {
        return totalDescargado;
    }

    // Texto con lo común (super.toString()), lo propio del módulo de tierra y el histórico
    @Override
    public String toString() {
        return super.toString() + String.format(
                " | Tipo: Tierra | Estación: %s | Capacidad: %.1f MB | Energía/descarga: %.1f | Total descargado: %.1f MB",
                estacion, capacidadDescarga, energiaPorDescarga, totalDescargado);
    }
}