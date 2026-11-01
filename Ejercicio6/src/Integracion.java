// ============================================================
// Propósito: clase abstracta que representa cualquier conexión con una API
// externa. Guarda los datos comunes (id, nombre, endpoint, latencia, estado)
// y define el orden natural de todas las integraciones mediante Comparable,
// de modo que clases distintas (tarjeta, logística, FEL...) se puedan
// comparar y ordenar en una sola lista.
// ============================================================
public abstract class Integracion implements Comparable<Integracion> {

    // Identificador único de la integración (ejemplo: "TAR-001"). Es un String.
    private String id;
    // Nombre comercial del proveedor (ejemplo: "Visa Pagos GT").
    private String nombre;
    // Dirección simulada de la API (no se hace ninguna conexión real).
    private String endpoint;
    // Tiempo de latencia promedio en milisegundos; es el criterio principal del orden.
    private int latenciaMs;
    // Estado actual de la conexión con la API.
    private EstadoConexion estadoConexion;

    // Constructor vacío: crea una integración con valores por defecto.
    public Integracion() {
        // Reutiliza el otro constructor para no repetir las asignaciones.
        this("SIN-ID", "Sin nombre", "sin-endpoint", 0, EstadoConexion.DESCONECTADA);
    }

    // Constructor con todos los datos. Es protected porque la clase es abstracta:
    // solo las clases hijas deben llamarlo (con super).
    protected Integracion(String id, String nombre, String endpoint,
                          int latenciaMs, EstadoConexion estadoConexion) {
        this.id = id;
        this.nombre = nombre;
        this.endpoint = endpoint;
        this.latenciaMs = latenciaMs;
        this.estadoConexion = estadoConexion;
    }

    // Orden natural de las integraciones (implementación de Comparable<Integracion>).
    // Primero compara la latencia (de menor a mayor). Si empatan, desempata por id ascendente.
    // Devuelve negativo si "this" va antes, positivo si va después y 0 si son iguales.
    @Override
    public int compareTo(Integracion otra) {
        // Integer.compare evita errores de desbordamiento que tendría una resta directa.
        int resultado = Integer.compare(this.latenciaMs, otra.latenciaMs);
        if (resultado != 0) {
            return resultado;   // las latencias son distintas: ya se sabe quién va primero
        }
        // Latencias iguales: se desempata alfabéticamente por id.
        return this.id.compareTo(otra.id);
    }

    // Cada clase hija indica su tipo en texto (ejemplo: "Pasarela de tarjeta").
    public abstract String obtenerTipo();

    // Cada clase hija arma el texto con sus parámetros de configuración propios.
    public abstract String obtenerConfiguracion();

    // Una integración se considera activa mientras no esté desconectada.
    public boolean isActiva() {
        return estadoConexion != EstadoConexion.DESCONECTADA;
    }

    // Getters: permiten leer los atributos privados desde otras clases.
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEndpoint() { return endpoint; }
    public int getLatenciaMs() { return latenciaMs; }
    public EstadoConexion getEstadoConexion() { return estadoConexion; }

    // Setter del estado: la conexión puede cambiar mientras el programa corre.
    public void setEstadoConexion(EstadoConexion estadoConexion) {
        this.estadoConexion = estadoConexion;
    }

    // Texto de una línea con los datos principales; se usa al listar el catálogo.
    @Override
    public String toString() {
        return id + " | " + nombre + " | " + obtenerTipo() + " | "
                + latenciaMs + " ms | " + estadoConexion;
    }
}