// Resultado "Fallido": la ejecución del trabajo falló (por ejemplo, archivo dañado o faltan recursos).
public class ResultadoFallido extends ResultadoEjecucion {

    // Constructor: solo llama al de la clase padre.
    public ResultadoFallido() {
        super();
    }

    // Nombre que se muestra para este resultado.
    @Override
    public String getNombre() {
        return "Fallido";
    }

    // Le indica al trabajo que falló; el trabajo decide si vuelve a cola o se cancela según sus intentos.
    @Override
    public void aplicarA(Trabajo trabajo) {
        trabajo.marcarFallido();
    }
}