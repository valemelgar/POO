// ============================================================
// Propósito: contrato para las integraciones que generan documentos
// legales o de soporte: facturas FEL en XML, guías de envío, etc.
// ============================================================
public interface Documentable {

    // Genera el documento asociado a la referencia indicada y lo devuelve como texto.
    String generarDocumento(String referencia);
}