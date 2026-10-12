// Horario diurno. Es una subclase de Horario sin atributos: su comportamiento es siempre el mismo.
public class Diurno extends Horario {

    // Constructor: solo llama al constructor de la clase padre (no hay nada más que inicializar).
    public Diurno() {
        super();
    }

    // Sobrescribe getNombre: el nombre que se muestra para este horario.
    @Override
    public String getNombre() {
        return "Diurno";
    }

    // Sobrescribe esNocturno: un horario diurno no es nocturno.
    @Override
    public boolean esNocturno() {
        return false;
    }

    // Sobrescribe elegir (decimales): en horario diurno se escoge el primer valor.
    @Override
    public double elegir(double valorDiurno, double valorNocturno) {
        return valorDiurno;
    }

    // Sobrescribe elegir (enteros): en horario diurno se escoge el primer valor.
    @Override
    public int elegir(int valorDiurno, int valorNocturno) {
        return valorDiurno;
    }
}