// ============================================================
// Propósito: integración con una pasarela de transferencias bancarias.
// Igual que la de tarjeta, consulta, cotiza, cobra/reintegra y audita,
// pero su comisión es una cantidad fija y no un porcentaje.
// ============================================================
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PasarelaTransferencia extends Integracion
        implements Consultable, Cotizable, Transaccional, Auditable {

    // Banco que procesa las transferencias.
    private String banco;
    // Comisión fija en quetzales que se suma a cada transferencia.
    private double comisionFija;
    // Historial de eventos para la auditoría.
    private ArrayList<String> historialEventos = new ArrayList<>();

    // Constructor vacío con valores por defecto.
    public PasarelaTransferencia() {
        super();
        this.banco = "SIN-BANCO";
        this.comisionFija = 0;
    }

    // Constructor completo: datos comunes al padre y datos propios a esta clase.
    public PasarelaTransferencia(String id, String nombre, String endpoint, int latenciaMs,
                                 EstadoConexion estadoConexion, String banco, double comisionFija) {
        super(id, nombre, endpoint, latenciaMs, estadoConexion);
        this.banco = banco;
        this.comisionFija = comisionFija;
    }

    // Simula la consulta del estado de una transferencia.
    @Override
    public String consultarEstado(String referencia) {
        return "Transferencia " + referencia + " en " + banco + ": ACREDITADA (simulado).";
    }

    // El total es el monto más la comisión fija del banco.
    @Override
    public double cotizar(double valorBase) {
        return valorBase + comisionFija;
    }

    // Simula el cobro por transferencia y lo registra en el historial.
    @Override
    public String cobrar(double monto) {
        if (monto <= 0) {
            return "Cobro rechazado: el monto debe ser mayor que cero.";
        }
        registrarEvento("COBRO Q" + String.format("%.2f", monto));
        return "Transferencia de Q" + String.format("%.2f", monto) + " recibida en " + banco + ".";
    }

    // Simula la devolución de una transferencia y la registra en el historial.
    @Override
    public String reintegrar(double monto) {
        if (monto <= 0) {
            return "Reintegro rechazado: el monto debe ser mayor que cero.";
        }
        registrarEvento("REINTEGRO Q" + String.format("%.2f", monto));
        return "Reintegro de Q" + String.format("%.2f", monto) + " transferido de vuelta desde " + banco + ".";
    }

    // Guarda el evento con la hora actual.
    @Override
    public void registrarEvento(String evento) {
        historialEventos.add(LocalTime.now().withNano(0) + " - " + evento);
    }

    // Devuelve una copia del historial.
    @Override
    public List<String> consultarHistorialEventos() {
        return new ArrayList<>(historialEventos);
    }

    @Override
    public String obtenerTipo() {
        return "Pasarela de transferencia";
    }

    @Override
    public String obtenerConfiguracion() {
        return "Endpoint: " + getEndpoint() + " | Banco: " + banco
                + " | Comisión fija: Q" + String.format("%.2f", comisionFija);
    }

    public String getBanco() { return banco; }
    public double getComisionFija() { return comisionFija; }
}