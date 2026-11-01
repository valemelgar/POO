// ============================================================
// Propósito: clase con el método main. Solo arma los
// objetos del sistema (modelo, vista y controlador), hace la carga inicial
// y entrega el control al controlador.
// ============================================================
public class Principal {

    // Punto de entrada del programa.
    public static void main(String[] args) {
        // Modelo: catálogo con la lista polimórfica y gestor de operaciones.
        CatalogoIntegraciones catalogo = new CatalogoIntegraciones();
        GestorOperaciones gestor = new GestorOperaciones();
        // Carga inicial: construye el registro con las 12 integraciones.
        catalogo.cargarIntegracionesIniciales();
        // Vista: interfaz de consola.
        VistaConsola vista = new VistaConsola();
        // Controlador: recibe los tres objetos y coordina todo.
        ControladorIntegraciones controlador = new ControladorIntegraciones(catalogo, gestor, vista);
        // Arranca el menú interactivo.
        controlador.iniciar();
    }
}