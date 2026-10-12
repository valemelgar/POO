import java.util.Locale;

// Clase abstracta que representa un trabajo de renderizado. Guarda los datos comunes a todas las
// categorías, los valida y controla su propio estado y número de intentos.
// Las decisiones que dependen de la categoría (costo, tiempo, etc.) las toman las subclases.
public abstract class Trabajo {

    // Constantes: valores fijos del negocio que comparten todas las categorías.
    private static final double CARGO_REINTENTO = 25.0;          // Q25 por cada intento adicional al primero
    private static final double TARIFA_BASE_DIURNA = 60.0;       // tarifa base diurna (animación y simulación)
    private static final double TARIFA_BASE_NOCTURNA = 40.0;     // tarifa base nocturna (animación y simulación)
    private static final int INTENTOS_MAXIMOS_PREDETERMINADOS = 3; // política de intentos por defecto

    // Atributos privados: solo se pueden leer con los getters y modificar a través de los métodos de la clase.
    private String codigo;            // identifica al trabajo (único en el sistema)
    private String estudio;           // nombre del estudio que envía el trabajo
    private String proyecto;          // nombre del proyecto
    private double horasEstimadas;    // horas de cómputo estimadas (se usan para la capacidad del nodo)
    private Horario horario;          // diurno o nocturno
    private EstadoTrabajo estado;     // etapa actual del trabajo (objeto de estado)
    private int intentos;             // cantidad de ejecuciones iniciadas hasta ahora

    // Constructor protegido: solo las subclases pueden llamarlo (con super). Valida los datos comunes.
    protected Trabajo(String codigo, String estudio, String proyecto, double horasEstimadas, Horario horario) {
        // Cada validación lanza una excepción si el dato es inválido; así el objeto nunca llega a existir mal formado.
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new ReglaNegocioException("El código del trabajo no puede estar vacío.");
        }
        if (estudio == null || estudio.trim().isEmpty()) {
            throw new ReglaNegocioException("El nombre del estudio no puede estar vacío.");
        }
        if (proyecto == null || proyecto.trim().isEmpty()) {
            throw new ReglaNegocioException("El nombre del proyecto no puede estar vacío.");
        }
        exigirPositivo(horasEstimadas, "horas estimadas");
        if (horario == null) {
            throw new ReglaNegocioException("El horario del trabajo es obligatorio.");
        }
        // Asignación de atributos (trim() quita espacios sobrantes al inicio y al final).
        this.codigo = codigo.trim();
        this.estudio = estudio.trim();
        this.proyecto = proyecto.trim();
        this.horasEstimadas = horasEstimadas;
        this.horario = horario;
        // Todo trabajo nuevo empieza en cola y sin intentos.
        this.estado = new TrabajoEnCola();
        this.intentos = 0;
    }

    // Getters (solo lectura; no hay setters públicos para proteger el estado) 

    // Devuelve el código del trabajo.
    public String getCodigo() {
        return codigo;
    }

    // Devuelve el nombre del estudio.
    public String getEstudio() {
        return estudio;
    }

    // Devuelve el nombre del proyecto.
    public String getProyecto() {
        return proyecto;
    }

    // Devuelve las horas de cómputo estimadas.
    public double getHorasEstimadas() {
        return horasEstimadas;
    }

    // Devuelve el horario de ejecución.
    public Horario getHorario() {
        return horario;
    }

    // Devuelve el estado actual del trabajo.
    public EstadoTrabajo getEstado() {
        return estado;
    }

    // Devuelve la cantidad de intentos realizados.
    public int getIntentos() {
        return intentos;
    }

    // Decisiones que dependen de la categoría (las implementa cada subclase)

    // Calcula el costo del trabajo según las reglas de su categoría.
    public abstract double calcularCosto();

    // Devuelve el tiempo estimado de entrega, en horas, según su categoría.
    public abstract int getTiempoEntrega();

    // Devuelve el nombre de la categoría (se usa en reportes y al mostrar el trabajo).
    public abstract String getCategoria();

    // Comportamiento por defecto (las subclases lo sobrescriben solo si cambia)

    // Cantidad máxima de intentos permitidos. Por defecto 3 (imagen fija y animación).
    public int getIntentosMaximos() {
        return INTENTOS_MAXIMOS_PREDETERMINADOS;
    }

    // Indica si el trabajo puede ejecutarse en el nodo dado. Por defecto cualquier nodo sirve.
    public boolean esCompatibleCon(Nodo nodo) {
        return true;
    }

    // Cargo que se cobra por cada intento adicional al primero.
    protected double getCargoReintento() {
        return CARGO_REINTENTO;
    }

    // Tarifa base según el horario: Q60 diurno / Q40 nocturno. Horario.elegir evita usar un "if".
    protected double tarifaBase() {
        return horario.elegir(TARIFA_BASE_DIURNA, TARIFA_BASE_NOCTURNA);
    }

    // Método auxiliar estático: verifica que un número sea positivo y finito; si no, rechaza con un mensaje claro.
    // Es protegido para que las subclases lo usen al validar sus propios datos numéricos.
    protected static void exigirPositivo(double valor, String campo) {
        // NaN e infinito no son números válidos; valor <= 0 no es positivo.
        if (Double.isNaN(valor) || Double.isInfinite(valor) || valor <= 0) {
            throw new ReglaNegocioException("El valor de '" + campo + "' debe ser un número mayor que cero.");
        }
    }

    // Manejo del estado

    // Indica si el trabajo puede asignarse a un lote. La respuesta la da el objeto de estado actual.
    public boolean puedeAsignarse() {
        return estado.puedeAsignarse();
    }

    // Pasa el trabajo a "Asignado". Si el estado actual no lo permite, asignar() lanza una excepción
    // y la línea de asignación nunca se ejecuta (el estado no cambia).
    public void marcarAsignado() {
        this.estado = this.estado.asignar();
    }

    // Devuelve el trabajo a "En cola" cuando se retira de un lote en preparación.
    public void marcarEnCola() {
        this.estado = this.estado.retirar();
    }

    // Inicia la ejecución: pasa a "En ejecución", suma un intento y devuelve el cargo por reintento
    // (Q25 si ya había tenido un intento antes; 0 si es el primero).
    public double iniciarEjecucion() {
        // Primero se pide el estado siguiente: si la transición es inválida, lanza excepción aquí
        // y todavía no se ha modificado nada (ni el estado ni los intentos).
        EstadoTrabajo siguiente = this.estado.iniciar();
        this.estado = siguiente;
        // Se cuenta el intento.
        this.intentos++;
        // Del segundo intento en adelante se cobra el cargo por reintento.
        if (this.intentos > 1) {
            return getCargoReintento();
        }
        // El primer intento no tiene cargo adicional.
        return 0.0;
    }

    // Registra que la ejecución terminó bien: el trabajo queda "Completado".
    public void marcarCompletado() {
        this.estado = this.estado.completar();
    }

    // Registra que la ejecución falló: según los intentos usados, vuelve a cola o queda "Cancelado".
    public void marcarFallido() {
        this.estado = this.estado.fallar(this.intentos, getIntentosMaximos());
    }

    // Texto con los datos comunes del trabajo (las subclases le agregan sus datos propios).
    @Override
    public String toString() {
        // Locale.US asegura que los decimales se muestren con punto y dos cifras, sin importar el idioma del sistema.
        return String.format(Locale.US,
                "[%s] Código: %s | Estudio: %s | Proyecto: %s | Horas estimadas: %.2f | Horario: %s | Estado: %s | Intentos: %d/%d",
                getCategoria(), codigo, estudio, proyecto, horasEstimadas, horario, estado,
                intentos, getIntentosMaximos());
    }
}