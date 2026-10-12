// Horario nocturno. Es una subclase de Horario sin atributos: su comportamiento es siempre el mismo.
public class Nocturno extends Horario {

    // Constructor: solo llama al constructor de la clase padre (no hay nada más que inicializar).
    public Nocturno() {
        super();
    }

    // Sobrescribe getNombre: el nombre que se muestra para este horario.
    @Override
    public String getNombre() {
        return "Nocturno";
    }

    // Sobrescribe esNocturno: este horario sí es nocturno.
    @Override
    public boolean esNocturno() {
        return true;
    }

    // Sobrescribe elegir (decimales): en horario nocturno se escoge el segundo valor.
    @Override
    public double elegir(double valorDiurno, double valorNocturno) {
        return valorNocturno;
    }

    // Sobrescribe elegir (enteros): en horario nocturno se escoge el segundo valor.
    @Override
    public int elegir(int valorDiurno, int valorNocturno) {
        return valorNocturno;
    }
}