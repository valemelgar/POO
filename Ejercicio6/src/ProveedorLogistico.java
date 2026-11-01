// ============================================================
// Propósito: integración con una empresa de envíos. Rastrea paquetes
// (Consultable), cotiza el envío (Cotizable) y genera la guía de envío
// (Documentable). No implementa Transaccional: no cobra ni reintegra
// ============================================================
public class ProveedorLogistico extends Integracion
        implements Consultable, Cotizable, Documentable {

    // Zona donde el proveedor hace entregas.
    private String cobertura;
    // Precio por kilogramo transportado.
    private double tarifaPorKg;

    // Constructor vacío con valores por defecto.
    public ProveedorLogistico() {
        super();
        this.cobertura = "SIN-COBERTURA";
        this.tarifaPorKg = 0;
    }

    // Constructor completo.
    public ProveedorLogistico(String id, String nombre, String endpoint, int latenciaMs,
                              EstadoConexion estadoConexion, String cobertura, double tarifaPorKg) {
        super(id, nombre, endpoint, latenciaMs, estadoConexion);
        this.cobertura = cobertura;
        this.tarifaPorKg = tarifaPorKg;
    }

    // Aquí "consultar estado" significa rastrear un paquete por su número de guía.
    @Override
    public String consultarEstado(String referencia) {
        return "Paquete " + referencia + " de " + getNombre() + ": EN TRÁNSITO (simulado).";
    }

    // Para logística el valor base es el peso en kg: el costo es peso por tarifa.
    @Override
    public double cotizar(double valorBase) {
        return valorBase * tarifaPorKg;
    }

    // Genera una guía de envío sencilla en formato XML (simulada).
    @Override
    public String generarDocumento(String referencia) {
        return "<guiaEnvio>\n"
             + "  <numero>" + referencia + "</numero>\n"
             + "  <proveedor>" + getNombre() + "</proveedor>\n"
             + "  <cobertura>" + cobertura + "</cobertura>\n"
             + "</guiaEnvio>";
    }

    @Override
    public String obtenerTipo() {
        return "Proveedor logístico";
    }

    @Override
    public String obtenerConfiguracion() {
        return "Endpoint: " + getEndpoint() + " | Cobertura: " + cobertura
                + " | Tarifa: Q" + String.format("%.2f", tarifaPorKg) + " por kg";
    }

    public String getCobertura() { 
        return cobertura; 
        }
    public double getTarifaPorKg() {
         return tarifaPorKg;
          }
}