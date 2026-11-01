// ============================================================
// Propósito: contrato para las integraciones que necesitan dejar un
// historial de auditoría. Las dos operaciones van juntas porque
// registrar eventos sin poder consultarlos no tendría sentido.
// ============================================================
import java.util.List;

public interface Auditable {

    // Guarda un evento en el historial de la integración (por ejemplo "COBRO Q100").
    void registrarEvento(String evento);

    // Devuelve la lista de eventos que se han registrado hasta el momento.
    List<String> consultarHistorialEventos();
}