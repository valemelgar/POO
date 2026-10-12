import java.util.Locale;

// Categoría "Simulación física": simula fluidos, humo o telas. Solo se acepta en horario nocturno
// y solo puede ejecutarse en nodos con GPU.
public class SimulacionFisica extends Trabajo {

    // Constantes de la categoría.
    private static final double COSTO_POR_HORA = 22.0;    // Q22 por cada hora estimada
    private static final double CARGO_GPU = 65.0;         // cargo fijo por uso de GPU
    private static final int TIEMPO_ENTREGA = 18;         // siempre 18 horas
    private static final int INTENTOS_MAXIMOS = 1;        // un solo intento
    private static final int MALLA_MINIMA = 1;            // mínimo de millones de celdas
    private static final int MALLA_MAXIMA = 50;           // máximo de millones de celdas

    // Atributo propio.
    private int resolucionMalla;   // resolución de la malla, en millones de celdas

    // Constructor: Trabajo guarda lo común; aquí se validan y guardan las reglas propias.
    public SimulacionFisica(String codigo, String estudio, String proyecto, double horasEstimadas,
                            Horario horario, int resolucionMalla) {
        super(codigo, estudio, proyecto, horasEstimadas, horario);
        // Regla propia: solo se aceptan simulaciones en horario nocturno (de día las GPU se reservan).
        if (!horario.esNocturno()) {
            throw new ReglaNegocioException("Las simulaciones físicas solo se aceptan en horario nocturno.");
        }
        // Regla propia: la malla debe estar entre 1 y 50 millones de celdas.
        if (resolucionMalla < MALLA_MINIMA || resolucionMalla > MALLA_MAXIMA) {
            throw new ReglaNegocioException("La resolución de la malla debe estar entre " + MALLA_MINIMA
                    + " y " + MALLA_MAXIMA + " millones de celdas.");
        }
        this.resolucionMalla = resolucionMalla;
    }

    // Devuelve la resolución de la malla.
    public int getResolucionMalla() {
        return resolucionMalla;
    }

    // Costo = tarifa base según horario + Q22 por hora estimada + Q65 fijos por GPU.
    @Override
    public double calcularCosto() {
        return tarifaBase() + COSTO_POR_HORA * getHorasEstimadas() + CARGO_GPU;
    }

    // Tiempo de entrega: siempre 18 horas.
    @Override
    public int getTiempoEntrega() {
        return TIEMPO_ENTREGA;
    }

    // Política de intentos propia: un solo intento (la caché generada se descarta).
    @Override
    public int getIntentosMaximos() {
        return INTENTOS_MAXIMOS;
    }

    // Restricción de nodo propia: solo puede ejecutarse en nodos con GPU.
    @Override
    public boolean esCompatibleCon(Nodo nodo) {
        return nodo.tieneGpu();
    }

    // Nombre de la categoría.
    @Override
    public String getCategoria() {
        return "Simulación física";
    }

    // Datos comunes más la resolución de la malla.
    @Override
    public String toString() {
        return super.toString() + String.format(Locale.US, " | Malla: %d millones de celdas", resolucionMalla);
    }
}