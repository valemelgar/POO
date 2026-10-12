// Estado "Asignado": el trabajo está en un lote que todavía está en preparación.
public class TrabajoAsignado extends EstadoTrabajo {

    // Constructor: solo llama al de la clase padre.
    public TrabajoAsignado() {
        super();
    }

    // Nombre que se muestra para este estado.
    @Override
    public String getNombre() {
        return "Asignado";
    }

    // Transición permitida: si se retira del lote vuelve a quedar "En cola".
    @Override
    public EstadoTrabajo retirar() {
        return new TrabajoEnCola();
    }

    // Transición permitida: cuando el lote inicia, el trabajo pasa a "En ejecución".
    @Override
    public EstadoTrabajo iniciar() {
        return new TrabajoEnEjecucion();
    }
}