// Severidad Crítica: prioridad 3, plazo de 1 hora y Q5.00 de crédito por hora de retraso.
public class SeveridadCritica extends Severidad {

    // Constructor: pasa al padre los valores propios de esta severidad.
    public SeveridadCritica() {
        super("Crítica", 3, 1, 5.00);
    }
}