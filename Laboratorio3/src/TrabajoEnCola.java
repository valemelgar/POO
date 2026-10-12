// Estado "En cola": el trabajo está registrado y esperando a ser asignado a un lote.
public class TrabajoEnCola extends EstadoTrabajo {

    // Constructor: solo llama al de la clase padre.
    public TrabajoEnCola() {
        super();
    }

    // Nombre que se muestra para este estado.
    @Override
    public String getNombre() {
        return "En cola";
    }

    // Un trabajo en cola SÍ puede asignarse a un lote.
    @Override
    public boolean puedeAsignarse() {
        return true;
    }

    // Transición permitida: al asignarlo a un lote pasa al estado "Asignado".
    @Override
    public EstadoTrabajo asignar() {
        return new TrabajoAsignado();
    }
}