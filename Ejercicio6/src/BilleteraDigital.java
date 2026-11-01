// ============================================================
// Propósito: integración con una billetera digital.
// Consulta, cobra/reintegra y audita, pero no cotiza (no implementa
// Cotizable), porque una billetera no ofrece cotizaciones.
// ============================================================
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BilleteraDigital extends Integracion
        implements Consultable, Transaccional, Auditable {

    // Empresa operadora de la billetera.
    private String operador;
    // Monto máximo permitido por transacción.
    private double limiteTransaccion;
    // Historial de eventos para la auditoría.
    private ArrayList<String> historialEventos = new ArrayList<>();

    // Constructor vacío con valores por defecto.
    public BilleteraDigital() {
        super();
        this.operador = "SIN-OPERADOR";
        this.limiteTransaccion = 0;
    }

    // Constructor completo.
    public BilleteraDigital(String id, String nombre, String endpoint, int latenciaMs,
                            EstadoConexion estadoConexion, String operador, double limiteTransaccion) {
        super(id, nombre, endpoint, latenciaMs, estadoConexion);
        this.operador = operador;
        this.limiteTransaccion = limiteTransaccion;
    }

    // Simula la consulta del estado de un movimiento de la billetera.
    @Override
    public String consultarEstado(String referencia) {
        return "Movimiento " + referencia + " en " + operador + ": COMPLETADO (simulado).";
    }

    // Simula un cobro; se rechaza si el monto supera el límite de la billetera.
    @Override
    public String cobrar(double monto) {
        if (monto <= 0) {
            return "Cobro rechazado: el monto debe ser mayor que cero.";
        }
        if (monto > limiteTransaccion) {
            registrarEvento("COBRO RECHAZADO Q" + String.format("%.2f", monto));
            return "Cobro rechazado: Q" + String.format("%.2f", monto)
                    + " supera el límite de Q" + String.format("%.2f", limiteTransaccion) + ".";
        }
        registrarEvento("COBRO Q" + String.format("%.2f", monto));
        return "Cobro de Q" + String.format("%.2f", monto) + " aprobado en la billetera " + operador + ".";
    }

    // Simula la devolución del dinero a la billetera del cliente.
    @Override
    public String reintegrar(double monto) {
        if (monto <= 0) {
            return "Reintegro rechazado: el monto debe ser mayor que cero.";
        }
        registrarEvento("REINTEGRO Q" + String.format("%.2f", monto));
        return "Reintegro de Q" + String.format("%.2f", monto) + " devuelto a la billetera " + operador + ".";
    }

    @Override
    public void registrarEvento(String evento) {
        historialEventos.add(LocalTime.now().withNano(0) + " - " + evento);
    }

    @Override
    public List<String> consultarHistorialEventos() {
        return new ArrayList<>(historialEventos);
    }

    @Override
    public String obtenerTipo() {
        return "Billetera digital";
    }

    @Override
    public String obtenerConfiguracion() {
        return "Endpoint: " + getEndpoint() + " | Operador: " + operador
                + " | Límite por transacción: Q" + String.format("%.2f", limiteTransaccion);
    }

    public String getOperador() { return operador; }
    public double getLimiteTransaccion() { return limiteTransaccion; }
}