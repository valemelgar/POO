// Categoría "Imagen fija": renderiza una sola imagen. Costo fijo según el horario.
public class ImagenFija extends Trabajo {

    // Constantes de la categoría.
    private static final double LIMITE_HORAS = 2.0;        // máximo de horas estimadas permitido
    private static final double COSTO_DIURNO = 45.0;       // costo si el horario es diurno
    private static final double COSTO_NOCTURNO = 30.0;     // costo si el horario es nocturno
    private static final int ENTREGA_DIURNA = 4;           // horas de entrega en horario diurno
    private static final int ENTREGA_NOCTURNA = 10;        // horas de entrega en horario nocturno

    // Constructor: primero deja que Trabajo valide y guarde los datos comunes (super), luego valida lo propio.
    public ImagenFija(String codigo, String estudio, String proyecto, double horasEstimadas, Horario horario) {
        super(codigo, estudio, proyecto, horasEstimadas, horario);
        // Regla propia de la categoría: una imagen fija no puede pasar de 2 horas estimadas.
        if (horasEstimadas > LIMITE_HORAS) {
            throw new ReglaNegocioException("Una imagen fija no puede superar " + LIMITE_HORAS
                    + " horas estimadas (se ingresaron " + horasEstimadas + ").");
        }
    }

    // Costo: valor fijo según el horario, sin importar las horas.
    @Override
    public double calcularCosto() {
        return getHorario().elegir(COSTO_DIURNO, COSTO_NOCTURNO);
    }

    // Tiempo de entrega: 4 horas de día y 10 de noche.
    @Override
    public int getTiempoEntrega() {
        return getHorario().elegir(ENTREGA_DIURNA, ENTREGA_NOCTURNA);
    }

    // Nombre de la categoría.
    @Override
    public String getCategoria() {
        return "Imagen fija";
    }

    // No tiene datos propios, así que muestra lo mismo que la clase padre.
    @Override
    public String toString() {
        return super.toString();
    }
}