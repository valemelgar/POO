// Resultado "Completado": la ejecución del trabajo terminó bien.
public class ResultadoCompletado extends ResultadoEjecucion {

    // Constructor: solo llama al de la clase padre.
    public ResultadoCompletado() {
        super();
    }

    // Nombre que se muestra para este resultado.
    @Override
    public String getNombre() {
        return "Completado";
    }

    // Le indica al trabajo que se completó; el trabajo cambia su estado a "Completado".
    @Override
    public void aplicarA(Trabajo trabajo) {
        trabajo.marcarCompletado();
    }
}