import java.util.Locale;

// Categoría "Animación con postproducción": es una animación que además requiere composición final
// y el uso de recursos licenciados. Hereda de Animacion para reutilizar sus fotogramas y su cálculo.
public class AnimacionPostproduccion extends Animacion {

    // Constantes de la categoría.
    private static final double COSTO_COMPOSICION = 80.0;     // Q80 por la composición final
    private static final double PORCENTAJE_RECARGO = 0.05;    // 5 % del valor de los recursos licenciados
    private static final int HORAS_ADICIONALES_ENTREGA = 12;  // 12 horas más que una animación normal
    private static final int INTENTOS_MAXIMOS = 2;            // política de intentos propia

    // Atributo propio (los fotogramas los hereda de Animacion).
    private double recursosLicenciados;   // valor declarado de modelos o texturas licenciados

    // Constructor: Animacion (y por encima Trabajo) guardan lo heredado; aquí se valida y guarda lo propio.
    public AnimacionPostproduccion(String codigo, String estudio, String proyecto, double horasEstimadas,
                                   Horario horario, int fotogramas, double recursosLicenciados) {
        super(codigo, estudio, proyecto, horasEstimadas, horario, fotogramas);
        // Regla propia: el valor de los recursos licenciados debe ser mayor que cero.
        exigirPositivo(recursosLicenciados, "recursos licenciados");
        this.recursosLicenciados = recursosLicenciados;
    }

    // Devuelve el valor declarado de los recursos licenciados.
    public double getRecursosLicenciados() {
        return recursosLicenciados;
    }

    // Costo = costo de una animación + Q80 de composición + 5 % de los recursos licenciados.
    @Override
    public double calcularCosto() {
        // super.calcularCosto() reutiliza la fórmula de Animacion (no se repite aquí).
        return super.calcularCosto() + COSTO_COMPOSICION + PORCENTAJE_RECARGO * recursosLicenciados;
    }

    // Tiempo de entrega = el de una animación normal con el mismo horario + 12 horas.
    @Override
    public int getTiempoEntrega() {
        return super.getTiempoEntrega() + HORAS_ADICIONALES_ENTREGA;
    }

    // Política de intentos propia: solo 2 intentos.
    @Override
    public int getIntentosMaximos() {
        return INTENTOS_MAXIMOS;
    }

    // Nombre de la categoría (distinto al de Animacion para que los reportes la cuenten aparte).
    @Override
    public String getCategoria() {
        return "Animación con postproducción";
    }

    // Datos de Animacion (que incluyen los de Trabajo) más los recursos licenciados.
    @Override
    public String toString() {
        return super.toString() + String.format(Locale.US, " | Recursos licenciados: Q%.2f", recursosLicenciados);
    }
}