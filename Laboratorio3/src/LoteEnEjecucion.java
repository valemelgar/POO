// Estado "En ejecución": el lote ya inició y se están registrando los resultados de sus trabajos.
public class LoteEnEjecucion extends EstadoLote {

    // Constructor: solo llama al de la clase padre.
    public LoteEnEjecucion() {
        super();
    }

    // Nombre que se muestra para este estado.
    @Override
    public String getNombre() {
        return "En ejecución";
    }

    // Transición permitida: al registrar un resultado, el lote se cierra solo si ya no falta ninguno.
    @Override
    public EstadoLote registrarResultado(boolean esElUltimo) {
        // Si era el último resultado pendiente, el lote pasa a "Cerrado" (y el nodo queda libre).
        if (esElUltimo) {
            return new LoteCerrado();
        }
        // Si todavía faltan resultados, el lote sigue en el mismo estado.
        return this;
    }
}