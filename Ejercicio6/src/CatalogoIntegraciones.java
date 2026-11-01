// ============================================================
// Propósito: clase del MODELO que guarda el registro de integraciones.
// Contiene la ÚNICA lista polimórfica del sistema: un ArrayList<Integracion>
// donde conviven tarjetas, transferencias, billeteras, logística y FEL.
// ============================================================
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CatalogoIntegraciones {

    // La única lista polimórfica: el tipo es la clase abstracta Integracion,
    // pero dentro hay objetos de las clases hijas.
    private ArrayList<Integracion> integraciones;

    // Constructor: crea la lista vacía para poder agregar integraciones.
    public CatalogoIntegraciones() {
        this.integraciones = new ArrayList<>();
    }

    // Agrega una integración al catálogo. Recibe el tipo padre, así que acepta
    // cualquier integración presente o futura sin modificar este método.
    public void agregarIntegracion(Integracion integracion) {
        integraciones.add(integracion);
    }

    // Devuelve una copia de la lista para que otras clases no puedan alterar la original.
    public List<Integracion> getIntegraciones() {
        return new ArrayList<>(integraciones);
    }

    // Devuelve solo las integraciones activas (las que no están desconectadas).
    public List<Integracion> listarActivas() {
        List<Integracion> activas = new ArrayList<>();
        // Se recorre la lista completa y se guardan únicamente las activas.
        for (Integracion integracion : integraciones) {
            if (integracion.isActiva()) {
                activas.add(integracion);
            }
        }
        return activas;
    }

    // Busca una integración por su id (sin importar mayúsculas o minúsculas).
    // Devuelve null si no existe ninguna con ese id.
    public Integracion buscarPorId(String id) {
        for (Integracion integracion : integraciones) {
            if (integracion.getId().equalsIgnoreCase(id)) {
                return integracion;
            }
        }
        return null;   // se recorrió todo y no hubo coincidencia
    }

    // Ordena el catálogo por latencia de menor a mayor usando el orden natural.
    // Collections.sort llama al compareTo de Integracion, por eso no hace falta
    // pasarle un criterio aparte. Devuelve la lista ya ordenada.
    public List<Integracion> ordenarPorLatencia() {
        Collections.sort(integraciones);
        return getIntegraciones();
    }

    // Carga inicial: crea 10 integraciones de naturaleza diversa y las agrega.
    // Hay latencias repetidas (180 y 120) para comprobar el desempate por id,
    // una DESCONECTADA y una DEGRADADA para probar el filtro de activas.
    public void cargarIntegracionesIniciales() {
        // Pasarelas de tarjeta
        agregarIntegracion(new PasarelaTarjeta("TAR-001", "Visa", "api.visa.gt",
                180, EstadoConexion.CONECTADA, "VISA", 2.9));
        agregarIntegracion(new PasarelaTarjeta("TAR-002", "Mastercard", "api.mastercard.gt",
                180, EstadoConexion.CONECTADA, "MASTERCARD", 3.2));
        // Pasarela de transferencia
        agregarIntegracion(new PasarelaTransferencia("TRF-001", "BAC", "api.bac.gt",
                250, EstadoConexion.CONECTADA, "BAC", 5.00));
        // Billeteras digitales
        agregarIntegracion(new BilleteraDigital("BIL-001", "Tigo Money", "api.tigo.gt",
                140, EstadoConexion.CONECTADA, "Tigo", 2000));
        agregarIntegracion(new BilleteraDigital("BIL-002", "Billetera BAM", "api.bam.gt",
                210, EstadoConexion.DESCONECTADA, "BAM", 1500));
        // Proveedores logísticos
        agregarIntegracion(new ProveedorLogistico("LOG-001", "Cargo Expreso", "api.cargo.gt",
                400, EstadoConexion.CONECTADA, "Nacional", 8.50));
        agregarIntegracion(new ProveedorLogistico("LOG-002", "Forza Delivery", "api.forza.gt",
                90, EstadoConexion.CONECTADA, "Capital", 12.00));
        // Certificadores FEL
        agregarIntegracion(new CertificadorFEL("FEL-001", "Infile", "api.infile.gt",
                120, EstadoConexion.CONECTADA, "A", 0));
        agregarIntegracion(new CertificadorFEL("FEL-002", "Digifact", "api.digifact.gt",
                120, EstadoConexion.CONECTADA, "B", 0));
        agregarIntegracion(new CertificadorFEL("FEL-003", "Megaprint", "api.megaprint.gt",
                260, EstadoConexion.DEGRADADA, "C", 0));
    }
}