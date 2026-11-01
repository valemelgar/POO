// ============================================================
// Propósito: contrato para las integraciones que saben calcular una
// cotización (por ejemplo, el costo de un envío o la comisión de un cobro).
// Está separada de Consultable porque no todas las integraciones cotizan
// (el certificador FEL por ejemplo, no cotiza nada).
// ============================================================
public interface Cotizable {

    // Calcula y devuelve el costo total de la operación a partir de un valor base.
    double cotizar(double valorBase);
}