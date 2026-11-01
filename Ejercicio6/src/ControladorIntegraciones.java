// ============================================================
// Propósito: clase del CONTROLADOR (MVC). Conecta la vista con el modelo:
// recibe la opción del usuario, pide los datos a la vista, llama al modelo
// y manda los resultados a la vista para mostrarlos.
// ============================================================
import java.util.Arrays;
import java.util.List;

public class ControladorIntegraciones {

    // Modelo: guarda la lista polimórfica de integraciones.
    private CatalogoIntegraciones catalogo;
    // Modelo: ejecuta las operaciones sobre una integración.
    private GestorOperaciones gestor;
    // Vista: interacción con el usuario por consola.
    private VistaConsola vista;

    // Constructor vacío: crea sus propios objetos por defecto.
    public ControladorIntegraciones() {
        this(new CatalogoIntegraciones(), new GestorOperaciones(), new VistaConsola());
    }

    // Constructor completo: recibe los objetos ya creados (por ejemplo, desde Principal).
    public ControladorIntegraciones(CatalogoIntegraciones catalogo, GestorOperaciones gestor, VistaConsola vista) {
        this.catalogo = catalogo;
        this.gestor = gestor;
        this.vista = vista;
    }

    // Ciclo principal del programa: muestra el menú hasta que el usuario elija 0.
    public void iniciar() {
        vista.mostrarMensaje("Bienvenido al sistema de integraciones del checkout.");
        int opcion = -1;
        while (opcion != 0) {
            opcion = vista.mostrarMenu();   // la vista pide la opción
            ejecutarOpcion(opcion);         // el controlador decide qué hacer
        }
        vista.mostrarMensaje("Hasta luego.");
    }

    // Según la opción elegida, llama al método que corresponde.
    public void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                listarIntegraciones();
                break;
            case 2:
                buscarIntegracion();
                break;
            case 3:
                ordenarCatalogo();
                break;
            case 4:
                mostrarOperaciones();
                break;
            case 5:
                ejecutarOperacion();
                break;
            case 0:
                break;   // salir: el ciclo de iniciar() termina solo
            default:
                vista.mostrarMensaje("Opción no válida. Intente de nuevo.");
        }
    }

    // Muestra las integraciones activas con su estado de conexión.
    public void listarIntegraciones() {
        List<Integracion> activas = catalogo.listarActivas();
        if (activas.isEmpty()) {
            vista.mostrarMensaje("No hay integraciones activas.");
            return;
        }
        vista.mostrarMensaje("--- Integraciones activas (id | nombre | tipo | latencia | estado) ---");
        // El toString de cada elemento incluye el estado de conexión.
        for (Integracion integracion : activas) {
            vista.mostrarMensaje(integracion.toString());
        }
    }

    // Busca una integración por id y muestra sus parámetros de configuración.
    public void buscarIntegracion() {
        Integracion integracion = seleccionarIntegracion();
        if (integracion == null) {
            return;   // seleccionarIntegracion ya avisó que no la encontró
        }
        vista.mostrarMensaje(integracion.toString());
        vista.mostrarMensaje("Configuración -> " + integracion.obtenerConfiguracion());
    }

    // Ordena el catálogo por latencia (menor a mayor) y lo muestra completo.
    public void ordenarCatalogo() {
        List<Integracion> ordenadas = catalogo.ordenarPorLatencia();
        vista.mostrarMensaje("--- Catálogo ordenado por latencia (menor a mayor) ---");
        for (Integracion integracion : ordenadas) {
            vista.mostrarMensaje(integracion.toString());
        }
    }

    // Muestra qué operaciones ofrece la integración elegida (incluye las de auditoría si aplican).
    public void mostrarOperaciones() {
        Integracion integracion = seleccionarIntegracion();
        if (integracion == null) {
            return;
        }
        vista.mostrarMensaje("Operaciones disponibles en " + integracion.getNombre() + ":");
        vista.mostrarMensajes(gestor.obtenerOperacionesDisponibles(integracion));
    }

    // Permite ejecutar una operación sobre la integración elegida.
    // Sirve para probar el caso del reintegro a un proveedor logístico.
    public void ejecutarOperacion() {
        Integracion integracion = seleccionarIntegracion();
        if (integracion == null) {
            return;
        }
        vista.mostrarMensajes(Arrays.asList(
                "1. consultarEstado", "2. cotizar", "3. cobrar", "4. reintegrar",
                "5. generarDocumento", "6. registrarEvento", "7. consultarHistorialEventos"));
        // La vista solo tiene leerDecimal para números, así que se convierte a entero.
        int operacion = (int) vista.leerDecimal("Elija la operación: ");
        String resultado;
        switch (operacion) {
            case 1:
                resultado = gestor.consultarEstado(integracion, vista.leerTexto("Referencia: "));
                break;
            case 2:
                resultado = gestor.cotizar(integracion,
                        vista.leerDecimal("Valor base (monto, o peso en kg si es logística): "));
                break;
            case 3:
                resultado = gestor.cobrar(integracion, vista.leerDecimal("Monto a cobrar: Q"));
                break;
            case 4:
                resultado = gestor.reintegrar(integracion, vista.leerDecimal("Monto a reintegrar: Q"));
                break;
            case 5:
                resultado = gestor.generarDocumento(integracion, vista.leerTexto("Referencia del documento: "));
                break;
            case 6:
                resultado = gestor.registrarEvento(integracion, vista.leerTexto("Evento a registrar: "));
                break;
            case 7:
                vista.mostrarMensajes(gestor.consultarHistorial(integracion));
                return;   // el historial ya se mostró, no queda nada más por imprimir
            default:
                resultado = "Operación no válida.";
        }
        vista.mostrarMensaje(resultado);
    }

    // Pide un id a la vista y busca la integración en el catálogo.
    // Si no existe, avisa al usuario y devuelve null. Método privado: solo lo usa esta clase.
    private Integracion seleccionarIntegracion() {
        String id = vista.leerTexto("Identificador de la integración (ejemplo: TAR-001): ");
        Integracion integracion = catalogo.buscarPorId(id);
        if (integracion == null) {
            vista.mostrarMensaje("No existe ninguna integración con el id '" + id + "'.");
        }
        return integracion;
    }
}