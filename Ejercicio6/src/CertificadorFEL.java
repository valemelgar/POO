// ============================================================
// Propósito: integración con un certificador de Factura Electrónica en
// Línea (FEL). Consulta el estado de una factura, genera el XML de la
// factura (Documentable) y audita sus eventos. No cotiza ni cobra.
// ============================================================
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CertificadorFEL extends Integracion
        implements Consultable, Documentable, Auditable {

    // Serie con la que el certificador emite las facturas.
    private String serie;
    // Contador de documentos que ha emitido esta integración.
    private int documentosEmitidos;
    // Historial de eventos para la auditoría.
    private ArrayList<String> historialEventos = new ArrayList<>();

    // Constructor vacío con valores por defecto.
    public CertificadorFEL() {
        super();
        this.serie = "SIN-SERIE";
        this.documentosEmitidos = 0;
    }

    // Constructor completo.
    public CertificadorFEL(String id, String nombre, String endpoint, int latenciaMs,
                           EstadoConexion estadoConexion, String serie, int documentosEmitidos) {
        super(id, nombre, endpoint, latenciaMs, estadoConexion);
        this.serie = serie;
        this.documentosEmitidos = documentosEmitidos;
    }

    // Simula la consulta del estado de una factura.
    @Override
    public String consultarEstado(String referencia) {
        return "Factura " + referencia + " en " + getNombre() + ": CERTIFICADA (simulado).";
    }

    // Genera el XML de la factura, suma uno al contador y registra el evento.
    @Override
    public String generarDocumento(String referencia) {
        documentosEmitidos++;   // lleva la cuenta de las facturas emitidas
        registrarEvento("DOCUMENTO FEL generado para " + referencia);
        return "<dte>\n"
             + "  <serie>" + serie + "</serie>\n"
             + "  <numero>" + documentosEmitidos + "</numero>\n"
             + "  <referencia>" + referencia + "</referencia>\n"
             + "  <certificador>" + getNombre() + "</certificador>\n"
             + "</dte>";
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
        return "Certificador FEL";
    }

    @Override
    public String obtenerConfiguracion() {
        return "Endpoint: " + getEndpoint() + " | Serie: " + serie
                + " | Documentos emitidos: " + documentosEmitidos;
    }

    public String getSerie() {
         return serie; 
         }
    public int getDocumentosEmitidos() {
         return documentosEmitidos;
          }
}