// Representa una alerta generada por un dispositivo. Guarda sus datos, sabe atenderse,
// calcula su retraso y su crédito, y sabe compararse con otras alertas (orden de urgencia).
// Implementa Comparable<Alerta>: así la bandeja la ordena sin repetir el criterio.
public class Alerta implements Comparable<Alerta> {

    // Código secuencial de la alerta (1, 2, 3, ...).
    private int codigo;

    // Dispositivo que generó la alerta (de él se obtiene la oficina a la que se acredita).
    private Dispositivo dispositivo;

    // Hora de simulación en que se generó.
    private int horaGeneracion;

    // Severidad (Baja, Alta o Crítica): define prioridad, plazo y crédito.
    private Severidad severidad;

    // Mensaje que describe lo ocurrido.
    private String mensaje;

    // Estado: false = pendiente, true = atendida.
    private boolean atendida;

    // Hora de simulación en que se atendió (solo tiene sentido si atendida es true).
    private int horaAtencion;

    // Horas completas de retraso al atenderla (0 si se atendió dentro del plazo).
    private int horasRetraso;

    // Constructor: toda alerta nace PENDIENTE y sin retraso.
    public Alerta(int codigo, Dispositivo dispositivo, int horaGeneracion, Severidad severidad, String mensaje) {
        // Valida que los datos obligatorios existan para no crear alertas incompletas.
        if (dispositivo == null || severidad == null) {
            throw new OperacionInvalidaException("La alerta necesita un dispositivo y una severidad.");
        }
        if (codigo <= 0 || horaGeneracion < 0) {
            throw new OperacionInvalidaException("El código y la hora de la alerta deben ser válidos.");
        }
        this.codigo = codigo;
        this.dispositivo = dispositivo;
        this.horaGeneracion = horaGeneracion;
        this.severidad = severidad;
        this.mensaje = mensaje;
        this.atendida = false;  // estado inicial: pendiente
        this.horaAtencion = -1; // -1 significa "todavía no atendida"
        this.horasRetraso = 0;
    }

    // Pasa la alerta de pendiente a atendida y calcula el retraso.
    public void atender(int horaActual) {
        if (atendida) {
            throw new OperacionInvalidaException("La alerta " + codigo + " ya fue atendida.");
        }
        if (horaActual < horaGeneracion) {
            throw new OperacionInvalidaException("La hora de atención no puede ser anterior a la de generación.");
        }
        // Horas completas transcurridas entre la generación y la atención.
        int transcurridas = horaActual - horaGeneracion;
        // Retraso = horas transcurridas menos el plazo de la severidad (nunca negativo).
        int retraso = transcurridas - severidad.getPlazoHoras();
        this.horasRetraso = (retraso > 0) ? retraso : 0;
        this.horaAtencion = horaActual;
        this.atendida = true; // transición pendiente -> atendida
    }

    // Crédito en quetzales por el retraso: la severidad sabe cuánto vale cada hora.
    public double calcularCredito() {
        return severidad.calcularCredito(horasRetraso);
    }

    // CRITERIO DE URGENCIA (único lugar donde se decide el orden de la bandeja):
    // 1) mayor prioridad de severidad primero, 2) generada antes primero, 3) código menor primero.
    // Un número negativo significa "esta alerta va antes que la otra".
    @Override
    public int compareTo(Alerta otra) {
        // Se invierte el orden (otra vs this) porque mayor prioridad debe ir primero.
        int porSeveridad = Integer.compare(otra.severidad.getPrioridad(), this.severidad.getPrioridad());
        if (porSeveridad != 0) {
            return porSeveridad;
        }
        // Empate de severidad: la generada primero (hora menor) va antes.
        int porHora = Integer.compare(this.horaGeneracion, otra.horaGeneracion);
        if (porHora != 0) {
            return porHora;
        }
        // Empate también en hora: el código menor va antes.
        return Integer.compare(this.codigo, otra.codigo);
    }

    // Getters: solo lectura, el estado cambia únicamente con atender().
    public int getCodigo() { return codigo; }
    public Dispositivo getDispositivo() { return dispositivo; }
    public int getHoraGeneracion() { return horaGeneracion; }
    public Severidad getSeveridad() { return severidad; }
    public String getMensaje() { return mensaje; }
    public boolean isAtendida() { return atendida; }
    public int getHoraAtencion() { return horaAtencion; }
    public int getHorasRetraso() { return horasRetraso; }

    // Texto para consultas y reportes.
    @Override
    public String toString() {
        String texto = "Alerta #" + codigo + " [" + severidad.getNombre() + "] dispositivo "
                + dispositivo.getCodigo() + " | hora " + horaGeneracion + " | " + mensaje;
        if (atendida) {
            texto += " | ATENDIDA en la hora " + horaAtencion + " (retraso: " + horasRetraso + " h)";
        } else {
            texto += " | PENDIENTE";
        }
        return texto;
    }
}