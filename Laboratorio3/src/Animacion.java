import java.util.Locale;

// Categoría "Animación": además de las horas estimadas, se registra la cantidad de fotogramas.
public class Animacion extends Trabajo {

    // Constantes de la categoría.
    private static final double COSTO_POR_HORA_FACTURABLE = 15.0; // Q15 por hora facturable o fracción
    private static final double FOTOGRAMAS_POR_HORA = 50.0;       // 0.02 h por fotograma equivale a 50 fotogramas por hora
    private static final int ENTREGA_DIURNA = 24;                 // horas de entrega en horario diurno
    private static final int ENTREGA_NOCTURNA = 36;               // horas de entrega en horario nocturno

    // Atributo propio.
    private int fotogramas;   // cantidad de fotogramas (entero positivo)

    // Constructor: Trabajo guarda lo común; aquí se valida y guarda lo propio.
    public Animacion(String codigo, String estudio, String proyecto, double horasEstimadas, Horario horario,
                     int fotogramas) {
        super(codigo, estudio, proyecto, horasEstimadas, horario);
        // Regla propia: los fotogramas deben ser un entero positivo.
        if (fotogramas <= 0) {
            throw new ReglaNegocioException("La cantidad de fotogramas debe ser un entero positivo.");
        }
        this.fotogramas = fotogramas;
    }

    // Devuelve la cantidad de fotogramas.
    public int getFotogramas() {
        return fotogramas;
    }

    // Calcula las horas por fotogramas. Equivale a fotogramas × 0.02, pero se divide entre 50.0 porque
    // la división es exacta cuando el resultado es entero (la multiplicación por 0.02 puede dar 3.0000000000000004).
    public double calcularHorasPorFotogramas() {
        return fotogramas / FOTOGRAMAS_POR_HORA;
    }

    // Horas facturables = el mayor valor entre las horas estimadas y las horas por fotogramas,
    // redondeado hacia arriba porque se cobra "por cada hora facturable o fracción".
    public int calcularHorasFacturables() {
        // Math.max escoge el mayor de los dos valores.
        double mayor = Math.max(getHorasEstimadas(), calcularHorasPorFotogramas());
        // Math.ceil redondea hacia arriba; (int) convierte el resultado a entero.
        return (int) Math.ceil(mayor);
    }

    // Costo = tarifa base según horario + Q15 por cada hora facturable.
    @Override
    public double calcularCosto() {
        return tarifaBase() + COSTO_POR_HORA_FACTURABLE * calcularHorasFacturables();
    }

    // Tiempo de entrega: 24 horas de día y 36 de noche.
    @Override
    public int getTiempoEntrega() {
        return getHorario().elegir(ENTREGA_DIURNA, ENTREGA_NOCTURNA);
    }

    // Nombre de la categoría.
    @Override
    public String getCategoria() {
        return "Animación";
    }

    // Datos comunes (de Trabajo) más los fotogramas.
    @Override
    public String toString() {
        return super.toString() + String.format(Locale.US, " | Fotogramas: %d", fotogramas);
    }
}