// ============================================================
// Propósito: clase del modelo que ejecuta las operaciones sobre una integración.
// Es el CLIENTE de los contratos: cada método depende de una sola interfaz.
// Antes de ejecutar, revisa con instanceof si la integración cumple ese contrato.
// Si no lo cumple, devuelve un aviso de "no disponible" y el proceso continúa:
// ============================================================
import java.util.ArrayList;
import java.util.List;

public class GestorOperaciones {

    // Constructor vacío: la clase no guarda datos, solo trabaja con lo que recibe.
    public GestorOperaciones() {
    }

    // Consulta el estado de una referencia. Solo depende de Consultable.
    public String consultarEstado(Integracion integracion, String referencia) {
        if (integracion instanceof Consultable) {
            // Se convierte al contrato para usar únicamente lo que ofrece Consultable.
            Consultable consultable = (Consultable) integracion;
            return consultable.consultarEstado(referencia);
        }
        return mensajeNoDisponible(integracion, "consultarEstado");
    }

    // Pide una cotización. Solo depende de Cotizable.
    public String cotizar(Integracion integracion, double valorBase) {
        if (integracion instanceof Cotizable) {
            Cotizable cotizable = (Cotizable) integracion;
            double total = cotizable.cotizar(valorBase);
            return "Cotización en " + integracion.getNombre() + ": Q" + String.format("%.2f", total)
                    + " (valor base: " + String.format("%.2f", valorBase) + ").";
        }
        return mensajeNoDisponible(integracion, "cotizar");
    }

    // Ejecuta un cobro. Solo depende de Transaccional.
    public String cobrar(Integracion integracion, double monto) {
        if (integracion instanceof Transaccional) {
            Transaccional transaccional = (Transaccional) integracion;
            return transaccional.cobrar(monto);
        }
        return mensajeNoDisponible(integracion, "cobrar");
    }

    // Ejecuta un reintegro. Si la integración es, por ejemplo, un proveedor logístico,
    // no implementa Transaccional y aquí se informa que la operación no está disponible.
    public String reintegrar(Integracion integracion, double monto) {
        if (integracion instanceof Transaccional) {
            Transaccional transaccional = (Transaccional) integracion;
            return transaccional.reintegrar(monto);
        }
        return mensajeNoDisponible(integracion, "reintegrar");
    }

    // Genera un documento (factura FEL, guía de envío...). Solo depende de Documentable.
    public String generarDocumento(Integracion integracion, String referencia) {
        if (integracion instanceof Documentable) {
            Documentable documentable = (Documentable) integracion;
            return documentable.generarDocumento(referencia);
        }
        return mensajeNoDisponible(integracion, "generarDocumento");
    }

    // Registra un evento de auditoría. Solo depende de Auditable.
    public String registrarEvento(Integracion integracion, String evento) {
        if (integracion instanceof Auditable) {
            Auditable auditable = (Auditable) integracion;
            auditable.registrarEvento(evento);
            return "Evento registrado en " + integracion.getNombre() + ".";
        }
        return mensajeNoDisponible(integracion, "registrarEvento");
    }

    // Consulta el historial de auditoría. Si no es auditable, devuelve una lista con el aviso.
    public List<String> consultarHistorial(Integracion integracion) {
        if (integracion instanceof Auditable) {
            Auditable auditable = (Auditable) integracion;
            List<String> historial = auditable.consultarHistorialEventos();
            if (historial.isEmpty()) {
                historial.add("El historial de " + integracion.getNombre() + " está vacío.");
            }
            return historial;
        }
        List<String> aviso = new ArrayList<>();
        aviso.add(mensajeNoDisponible(integracion, "consultarHistorialEventos"));
        return aviso;
    }

    // Arma la lista de operaciones que la integración ofrece, revisando cada contrato.
    // Las de auditoría solo aparecen si la integración es Auditable.
    public List<String> obtenerOperacionesDisponibles(Integracion integracion) {
        List<String> operaciones = new ArrayList<>();
        if (integracion instanceof Consultable) {
            operaciones.add("consultarEstado(referencia)");
        }
        if (integracion instanceof Cotizable) {
            operaciones.add("cotizar(valorBase)");
        }
        if (integracion instanceof Transaccional) {
            operaciones.add("cobrar(monto)");
            operaciones.add("reintegrar(monto)");
        }
        if (integracion instanceof Documentable) {
            operaciones.add("generarDocumento(referencia)");
        }
        if (integracion instanceof Auditable) {
            operaciones.add("registrarEvento(evento)");
            operaciones.add("consultarHistorialEventos()");
        }
        return operaciones;
    }

    // Mensaje estándar cuando la operación no corresponde a la integración.
    // Aclara que la compra sigue, porque el enunciado pide continuar el proceso.
    private String mensajeNoDisponible(Integracion integracion, String operacion) {
        return "La operación '" + operacion + "' no está disponible para "
                + integracion.getNombre() + " (" + integracion.obtenerTipo()
                + "). El proceso de compra continúa.";
    }
}