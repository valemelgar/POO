// ============================================================
// Propósito: contrato para las integraciones que mueven dinero:
//  cobran y reintegran. Solo lo implementan las pasarelas de pago.
// Un proveedor logístico no lo implementa
// ============================================================
public interface Transaccional {

    // Ejecuta un cobro por el monto indicado y devuelve un texto con el resultado.
    String cobrar(double monto);

    // Ejecuta un reintegro (devolución) por el monto indicado y devuelve el resultado.
    String reintegrar(double monto);
}