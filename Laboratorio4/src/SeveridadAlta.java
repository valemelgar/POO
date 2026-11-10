// Severidad Alta: prioridad 2, plazo de 4 horas y Q2.00 de crédito por hora de retraso.
public class SeveridadAlta extends Severidad {

    // Constructor: pasa al padre los valores propios de esta severidad.
    public SeveridadAlta() {
        super("Alta", 2, 4, 2.00);
    }
}