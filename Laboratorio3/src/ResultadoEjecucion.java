// Clase abstracta que representa el resultado de ejecutar un trabajo dentro de un lote.
// Cada subclase sabe qué hacerle al trabajo, así se evita un "if completado / if fallido".
public abstract class ResultadoEjecucion {

    // Constructor: la clase no tiene atributos, no hay nada que inicializar.
    public ResultadoEjecucion() {
    }

    // Nombre del resultado, para mostrarlo en pantalla. Lo implementa cada subclase.
    public abstract String getNombre();

    // Aplica el resultado al trabajo indicado (cambia su estado). Lo implementa cada subclase.
    public abstract void aplicarA(Trabajo trabajo);
}