// Interfaz que representa la CAPACIDAD de medir una magnitud y generar alertas a partir de
// una lectura. La implementan el climatizador (temperatura) y el sensor de humo (ppm),
// que NO comparten una clase padre propia, por eso se usa una interfaz.
public interface Medible {

    // Devuelve la unidad de la magnitud que mide ("°C", "ppm", ...).
    String getUnidad();

    // Registra una lectura. Devuelve la Alerta generada o null si la lectura no alcanza
    // ningún umbral. Recibe la hora de simulación y la secuencia para numerar la alerta.
    Alerta registrarLectura(double valor, int horaSimulacion, SecuenciaAlertas secuencia);
}