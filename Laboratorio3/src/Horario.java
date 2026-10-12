// Clase abstracta que representa el horario de ejecución de un trabajo (diurno o nocturno).
// Reemplaza a una enumeración: cada horario es una subclase que sabe elegir el valor que le corresponde,
// así el resto del programa no necesita "if" para preguntar si es de día o de noche.
public abstract class Horario {

    // Constructor: la clase no tiene atributos, así que no hay nada que inicializar.
    public Horario() {
    }

    // Devuelve el nombre del horario para mostrarlo en pantalla. Cada subclase lo implementa.
    public abstract String getNombre();

    // Indica si el horario es nocturno. Lo usa la simulación física para validar que solo se acepte de noche.
    public abstract boolean esNocturno();

    // Elige entre dos valores decimales (por ejemplo, dos tarifas): el primero si es diurno, el segundo si es nocturno.
    public abstract double elegir(double valorDiurno, double valorNocturno);

    // Igual que el anterior, pero para enteros (por ejemplo, los tiempos de entrega en horas).
    public abstract int elegir(int valorDiurno, int valorNocturno);

    // toString sobrescrito: al imprimir un Horario se muestra su nombre.
    @Override
    public String toString() {
        // Devuelve el nombre que define cada subclase.
        return getNombre();
    }
}