/**
 * Módulo de energía: panel solar que genera energía para la misión.
 * Hereda de Modulo y sobrescribe procesarCiclo.
 */
public class ModuloEnergia extends Modulo {

    private final double energiaPorCiclo;  // Energía que genera en cada operación (constante)
    private int ciclosAcumulados;          // Ciclos en los que realmente generó energía

    // Constructor: llama a super(...) para lo común, guarda lo propio y valida los valores
    public ModuloEnergia(String id, String nombre, int salud, boolean activo, double costoConstruccion,
                         double energiaPorCiclo, int ciclosAcumulados) {
        super(id, nombre, salud, activo, costoConstruccion); // Inicializa y valida lo común
        // La energía debe ser positiva y los ciclos no negativos
        if (energiaPorCiclo <= 0 || ciclosAcumulados < 0) {
            throw new IllegalArgumentException("Valores inválidos en el módulo de energía.");
        }
        this.energiaPorCiclo = energiaPorCiclo;     // Guarda la producción por ciclo
        this.ciclosAcumulados = ciclosAcumulados;   // Guarda los ciclos ya operados
    }

    /**
     * Sobrescribe procesarCiclo: si está activo agrega su energía a la misión e incrementa
     * los ciclos acumulados. Si está inactivo no produce nada y lo informa.
     */
    @Override
    public String procesarCiclo(Mision mision) {
        // Un módulo inactivo no produce energía
        if (!isActivo()) {
            return getNombre() + " (Energía): no generó energía porque está inactivo.";
        }
        mision.agregarEnergia(energiaPorCiclo); // La energía queda disponible para los siguientes módulos
        ciclosAcumulados++;                     // Cuenta este ciclo como operado
        return getNombre() + " (Energía): generó " + energiaPorCiclo + " unidades de energía.";
    }

    // Devuelve la producción de energía por ciclo
    public double getEnergiaPorCiclo() {
        return energiaPorCiclo;
    }

    // Devuelve los ciclos en los que generó energía
    public int getCiclosAcumulados() {
        return ciclosAcumulados;
    }

    // Calcula el histórico generado: energía por ciclo × ciclos en que operó
    public double getTotalGenerado() {
        return energiaPorCiclo * ciclosAcumulados;
    }

    // Texto con lo común (super.toString()), lo propio del módulo de energía y el histórico
    @Override
    public String toString() {
        return super.toString() + String.format(
                " | Tipo: Energía | Energía/ciclo: %.1f | Ciclos: %d | Total generado: %.1f",
                energiaPorCiclo, ciclosAcumulados, getTotalGenerado());
    }
}