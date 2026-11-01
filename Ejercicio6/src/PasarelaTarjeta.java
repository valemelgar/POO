// ============================================================
// Propósito: integración con una pasarela de pago por tarjeta de crédito/débito.
// Hereda los datos comunes de Integracion e implementa cuatro contratos:
// consultar, cotizar, cobrar/reintegrar y auditar. No genera documentos.
// ============================================================
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PasarelaTarjeta extends Integracion
        implements Consultable, Cotizable, Transaccional, Auditable {

    // Red de la tarjeta que procesa la pasarela (VISA, MASTERCARD...).
    private String redTarjeta;
    // Porcentaje de comisión que cobra la pasarela por cada transacción.
    private double comisionPorcentaje;
    // Historial de eventos para la auditoría (cobros, reintegros, etc.).
    private ArrayList<String> historialEventos = new ArrayList<>();

    // Constructor vacío: deja valores por defecto.
    public PasarelaTarjeta() {
        super();                       // inicializa los datos comunes con sus valores por defecto
        this.redTarjeta = "SIN-RED";
        this.comisionPorcentaje = 0;
    }

    // Constructor completo: recibe los datos comunes (los pasa a la clase padre) y los propios.
    public PasarelaTarjeta(String id, String nombre, String endpoint, int latenciaMs,
                           EstadoConexion estadoConexion, String redTarjeta, double comisionPorcentaje) {
        super(id, nombre, endpoint, latenciaMs, estadoConexion);
        this.redTarjeta = redTarjeta;
        this.comisionPorcentaje = comisionPorcentaje;
    }

    // Simula la consulta del estado de una transacción con tarjeta.
    @Override
    public String consultarEstado(String referencia) {
        return "Transacción " + referencia + " en " + getNombre() + ": APROBADA (simulado).";
    }

    // Devuelve el monto más la comisión porcentual de la pasarela.
    @Override
    public double cotizar(double valorBase) {
        return valorBase + (valorBase * comisionPorcentaje / 100.0);
    }

    // Simula un cobro con tarjeta y deja constancia en el historial.
    @Override
    public String cobrar(double monto) {
        if (monto <= 0) {
            return "Cobro rechazado: el monto debe ser mayor que cero.";
        }
        registrarEvento("COBRO Q" + String.format("%.2f", monto));
        return "Cobro de Q" + String.format("%.2f", monto) + " aprobado con tarjeta " + redTarjeta
                + " en " + getNombre() + ".";
    }

    // Simula un reintegro a la tarjeta y deja constancia en el historial.
    @Override
    public String reintegrar(double monto) {
        if (monto <= 0) {
            return "Reintegro rechazado: el monto debe ser mayor que cero.";
        }
        registrarEvento("REINTEGRO Q" + String.format("%.2f", monto));
        return "Reintegro de Q" + String.format("%.2f", monto) + " enviado a la tarjeta " + redTarjeta + ".";
    }

    // Agrega un evento al historial anteponiendo la hora para saber cuándo ocurrió.
    @Override
    public void registrarEvento(String evento) {
        historialEventos.add(LocalTime.now().withNano(0) + " - " + evento);
    }

    // Devuelve una copia del historial para que nadie lo modifique desde afuera.
    @Override
    public List<String> consultarHistorialEventos() {
        return new ArrayList<>(historialEventos);
    }

    // Tipo de integración, usado al mostrar el catálogo.
    @Override
    public String obtenerTipo() {
        return "Pasarela de tarjeta";
    }

    // Parámetros de configuración propios de esta clase.
    @Override
    public String obtenerConfiguracion() {
        return "Endpoint: " + getEndpoint() + " | Red: " + redTarjeta
                + " | Comisión: " + comisionPorcentaje + "%";
    }

    public String getRedTarjeta() { return redTarjeta; }
    public double getComisionPorcentaje() { return comisionPorcentaje; }
}