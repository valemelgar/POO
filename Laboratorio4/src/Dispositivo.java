import java.util.Locale;

// Clase abstracta base de TODOS los dispositivos. Guarda lo común (código, oficina, potencia,
// estado y consumo del día) y deja abstracto lo que cambia por tipo (consumo y nombre del tipo).
// Implementa Comparable<Dispositivo> para ordenar por código en los reportes.
public abstract class Dispositivo implements Comparable<Dispositivo> {

    // Código único del dispositivo.
    private String codigo;

    // Oficina donde está instalado.
    private Oficina oficina;

    // Potencia nominal en watts (puede tener decimales).
    private double potenciaNominal;

    // Estado: true = encendido, false = apagado.
    private boolean encendido;

    // kWh consumidos en el día en curso.
    private double consumoDia;

    // Constructor protegido: lo llaman las subclases con super(...). Valida los datos comunes;
    // si algo es inválido lanza excepción y el objeto nunca se crea (no queda estado a medias).
    protected Dispositivo(String codigo, Oficina oficina, double potenciaNominal, boolean encendido) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new OperacionInvalidaException("El código del dispositivo no puede estar vacío.");
        }
        if (oficina == null) {
            throw new OperacionInvalidaException("El dispositivo debe pertenecer a una oficina.");
        }
        if (Double.isNaN(potenciaNominal) || Double.isInfinite(potenciaNominal) || potenciaNominal <= 0) {
            throw new OperacionInvalidaException("La potencia nominal debe ser un número positivo.");
        }
        this.codigo = codigo.trim();
        this.oficina = oficina;
        this.potenciaNominal = potenciaNominal;
        this.encendido = encendido;   // casi todos inician apagados; el sensor de humo, encendido
        this.consumoDia = 0;          // al registrarse no hay consumo
    }

    // ABSTRACTO: kWh que consume el dispositivo en UNA hora estando encendido. Cada tipo
    // tiene su fórmula, por eso cada subclase la implementa (aquí se evita if/instanceof).
    public abstract double calcularConsumoHora();

    // ABSTRACTO: nombre del tipo de dispositivo ("Luminaria", "Cámara de seguridad", ...).
    public abstract String getTipo();

    // Enciende el dispositivo. Si ya estaba encendido rechaza la operación.
    public void encender() {
        if (encendido) {
            throw new OperacionInvalidaException("El dispositivo " + codigo + " ya está encendido.");
        }
        encendido = true;
    }

    // Apaga el dispositivo. Si ya estaba apagado rechaza la operación.
    // El sensor de humo lo sobrescribe para rechazar siempre.
    public void apagar() {
        if (!encendido) {
            throw new OperacionInvalidaException("El dispositivo " + codigo + " ya está apagado.");
        }
        encendido = false;
    }

    // Calcula el consumo de UNA hora de simulación según el estado actual y lo acumula en el día.
    // Devuelve los kWh de esa hora (0 si está apagado).
    public double consumirHora() {
        if (!encendido) {
            return 0; // apagado no consume
        }
        double kwh = calcularConsumoHora(); // polimorfismo: la fórmula depende del tipo real
        consumoDia += kwh;
        return kwh;
    }

    // Deja el consumo del día en cero (se llama al cerrar el día).
    public void reiniciarConsumoDia() {
        consumoDia = 0;
    }

    // COMPORTAMIENTO POR DEFECTO: un dispositivo normal no es regulable. Los regulables
    // sobrescriben este método. Así el sistema puede llamarlo sin verificar el tipo.
    public void ajustarNivel(int nivel) {
        throw new OperacionInvalidaException("El dispositivo " + codigo + " (" + getTipo() + ") no es regulable.");
    }

    // COMPORTAMIENTO POR DEFECTO: un dispositivo normal NO mide. Los medibles lo sobrescriben.
    public Alerta registrarLectura(double valor, int horaSimulacion, SecuenciaAlertas secuencia) {
        throw new OperacionInvalidaException("El dispositivo " + codigo + " (" + getTipo() + ") no mide lecturas.");
    }

    // COMPORTAMIENTO POR DEFECTO: un dispositivo normal NO es cámara. La cámara lo sobrescribe.
    public Alerta registrarMovimiento(int horaSimulacion, SecuenciaAlertas secuencia) {
        throw new OperacionInvalidaException("El dispositivo " + codigo + " (" + getTipo() + ") no es una cámara de seguridad.");
    }

    // Orden natural por código: lo usan los reportes de dispositivos ordenados.
    @Override
    public int compareTo(Dispositivo otro) {
        return this.codigo.compareTo(otro.codigo);
    }

    // Getters de solo lectura (el estado se cambia únicamente con los métodos de arriba).
    public String getCodigo() { return codigo; }
    public Oficina getOficina() { return oficina; }
    public double getPotenciaNominal() { return potenciaNominal; }
    public boolean isEncendido() { return encendido; }
    public double getConsumoDia() { return consumoDia; }

    // Método de apoyo para las subclases: rechaza la operación si el dispositivo está apagado.
    // Es protegido porque solo las subclases lo necesitan.
    protected void verificarEncendido() {
        if (!encendido) {
            throw new OperacionInvalidaException("El dispositivo " + codigo + " está apagado.");
        }
    }

    // Texto con los datos comunes para consultas y reportes.
    @Override
    public String toString() {
        return String.format(Locale.US, "[%s] %s | Oficina: %s | %.2f W | %s | Consumo del día: %.3f kWh",
                codigo, getTipo(), oficina.getCodigo(), potenciaNominal,
                encendido ? "Encendido" : "Apagado", consumoDia);
    }
}