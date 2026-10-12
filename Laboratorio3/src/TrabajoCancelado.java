// Estado "Cancelado": el trabajo agotó todos sus intentos. Es un estado final.
// No sobrescribe ninguna transición, así que no puede asignarse nuevamente ni cambiar de estado.
public class TrabajoCancelado extends EstadoTrabajo {

    // Constructor: solo llama al de la clase padre.
    public TrabajoCancelado() {
        super();
    }

    // Nombre que se muestra para este estado.
    @Override
    public String getNombre() {
        return "Cancelado";
    }
}