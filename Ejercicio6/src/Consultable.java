// ============================================================
// Propósito: contrato (interfaz) para las integraciones que permiten
// consultar el estado de algo: un pago, un paquete, un documento, etc.
// ============================================================
public interface Consultable {

    // Devuelve un texto que describe el estado actual de la referencia recibida
    // (puede ser el id de una transacción, un número de guía ...).
    // Cada clase que implemente la interfaz decide cómo armar ese texto.
    String consultarEstado(String referencia);
}