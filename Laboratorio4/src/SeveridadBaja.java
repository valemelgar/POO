// Severidad Baja: prioridad 1, plazo de 24 horas y Q0.50 de crédito por hora de retraso.
public class SeveridadBaja extends Severidad {

    // Constructor: pasa al padre los valores propios de esta severidad.
    public SeveridadBaja() {
        super("Baja", 1, 24, 0.50);
    }
}