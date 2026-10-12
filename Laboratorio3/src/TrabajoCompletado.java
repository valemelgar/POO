// Estado "Completado": la ejecución terminó correctamente. Es un estado final.
// No sobrescribe ninguna transición, así que cualquier operación sobre el trabajo será rechazada.
public class TrabajoCompletado extends EstadoTrabajo {

    // Constructor: solo llama al de la clase padre.
    public TrabajoCompletado() {
        super();
    }

    // Nombre que se muestra para este estado.
    @Override
    public String getNombre() {
        return "Completado";
    }
}