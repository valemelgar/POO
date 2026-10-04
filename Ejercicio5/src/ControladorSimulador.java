import java.util.ArrayList;  // Para construir listas de texto que se envían a la vista
import java.util.List;       // Tipo de las listas

/**
 * Controlador: conecta la vista con el modelo. Pide datos a la vista, consulta a la misión
 * y le devuelve a la vista lo que debe mostrar.
 */
public class ControladorSimulador {

    private final Mision mision;       // Modelo con los módulos y los recursos
    private final VistaConsola vista;  // Vista de consola

    // Constructor: guarda las referencias al modelo y a la vista
    public ControladorSimulador(Mision mision, VistaConsola vista) {
        this.mision = mision;  // Guarda el modelo
        this.vista = vista;    // Guarda la vista
    }

    // Repite el menú y procesa las opciones hasta que el usuario elija salir
    public void iniciar() {
        int opcion = 0; // Guarda la opción elegida
        // Mientras no elija "6. Salir", el programa sigue funcionando
        do {
            opcion = vista.mostrarMenu(); // Pide a la vista que muestre el menú y devuelva la opción
            ejecutarOpcion(opcion);       // Ejecuta la operación elegida
        } while (opcion != 6);            // Se detiene al elegir salir
        vista.mostrarMensaje("Fin del simulador. ¡Hasta pronto!"); // Despedida al terminar
    }

    // Invoca la operación que corresponde a la opción seleccionada
    private void ejecutarOpcion(int opcion) {
        // Decide qué acción realizar según la opción
        switch (opcion) {
            case 1:
                listarModulos();               // Opción 1: listar módulos
                break;                         // Evita que continúe con otros casos
            case 2:
                buscarModulo();                // Opción 2: buscar módulo
                break;                         // Evita que continúe con otros casos
            case 3:
                mostrarCatalogo();             // Opción 3: catálogo por costo
                break;                         // Evita que continúe con otros casos
            case 4:
                avanzarCiclo();                // Opción 4: avanzar ciclo
                break;                         // Evita que continúe con otros casos
            case 5:
                mostrarResumenComunicaciones(); // Opción 5: resumen de comunicaciones
                break;                         // Evita que continúe con otros casos
            default:
                break;                         // La opción 6 (salir) la maneja iniciar()
        }
    }

    // Solicita los módulos al modelo y envía su información a la vista
    private void listarModulos() {
        List<String> lineas = new ArrayList<>();            // Lista de textos a mostrar
        vista.mostrarMensaje("\n--- Módulos de la misión ---"); // Encabezado del listado
        // Recorre los módulos en el orden de la carga inicial
        for (Modulo m : mision.getModulos()) {
            lineas.add(m.toString());                        // toString polimórfico: cada tipo imprime lo suyo
        }
        vista.mostrarMensajes(lineas);                       // La vista imprime todas las líneas
    }

    // Pregunta si se busca por ID o nombre, hace la consulta y muestra las coincidencias
    private void buscarModulo() {
        String criterio = vista.leerTexto("Buscar por (1) ID o (2) Nombre: "); // Pregunta el tipo de búsqueda
        // Si eligió buscar por ID
        if (criterio.trim().equals("1")) {
            String id = vista.leerTexto("Ingrese el ID: ");        // Pide el ID
            Modulo encontrado = mision.buscarPorId(id);            // Consulta al modelo
            // El modelo devuelve null si no existe
            if (encontrado == null) {
                vista.mostrarMensaje("No se encontró ningún módulo con ese ID."); // Informa que no existe
            } else {
                vista.mostrarMensaje(encontrado.toString());       // Muestra toda su información
            }
        } else if (criterio.trim().equals("2")) {                  // Si eligió buscar por nombre
            String nombre = vista.leerTexto("Ingrese el nombre: "); // Pide el nombre
            List<Modulo> encontrados = mision.buscarPorNombre(nombre); // Consulta al modelo
            // Lista vacía significa que no hubo coincidencias
            if (encontrados.isEmpty()) {
                vista.mostrarMensaje("No se encontró ningún módulo con ese nombre."); // Informa que no existe
            } else {
                // Muestra cada coincidencia
                for (Modulo m : encontrados) {
                    vista.mostrarMensaje(m.toString());            // Muestra toda la información del módulo
                }
            }
        } else {
            vista.mostrarMensaje("Opción de búsqueda inválida."); // Entrada distinta de 1 o 2
        }
    }

    // Solicita el catálogo ordenado por costo y lo muestra mediante la vista
    private void mostrarCatalogo() {
        vista.mostrarMensaje("\n--- Catálogo ordenado por costo de construcción ---"); // Encabezado
        // Recorre la copia ordenada que entrega el modelo
        for (Modulo m : mision.obtenerCatalogoPorCosto()) {
            vista.mostrarMensaje(m.toString());                // Muestra cada módulo en orden de costo
        }
    }

    // Pide al modelo avanzar un ciclo y envía los resultados a la vista
    private void avanzarCiclo() {
        vista.mostrarMensaje("\n--- Resultado del ciclo ---");      // Encabezado del resultado
        List<String> resultados = mision.avanzarCiclo();           // El modelo ejecuta el ciclo completo
        vista.mostrarMensajes(resultados);                         // La vista muestra cada módulo y los recursos
    }

    // Consulta los datos de comunicaciones y los presenta a través de la vista
    private void mostrarResumenComunicaciones() {
        vista.mostrarMensaje("\n--- Resumen de comunicaciones ---"); // Encabezado del informe
        // Cantidad total de módulos de tierra y cuántos están activos
        vista.mostrarMensaje("Módulos de tierra: " + mision.contarModulosTierra()
                + " (activos: " + mision.contarModulosTierraActivos() + ")");
        // Capacidad conjunta de descarga por ciclo de los activos
        vista.mostrarMensaje("Capacidad total de descarga por ciclo (activos): "
                + mision.calcularCapacidadDescargaActiva() + " MB");
        // Histórico total descargado
        vista.mostrarMensaje("Total histórico descargado: " + mision.getTotalDatosDescargados() + " MB");
        List<ModuloTierra> lideres = mision.obtenerTierraConMayorDescarga(); // Módulos con el mayor histórico
        // Si no hay módulos de tierra la lista viene vacía
        if (lideres.isEmpty()) {
            vista.mostrarMensaje("No hay módulos de tierra registrados."); // Informa la situación
        } else {
            vista.mostrarMensaje("Módulo(s) con mayor descarga histórica:"); // Título de la lista
            // Recorre todos (puede haber empate)
            for (ModuloTierra t : lideres) {
                vista.mostrarMensaje(" - Nombre: " + t.getNombre() + " | ID: " + t.getId()
                        + " | Estación: " + t.getEstacion()
                        + " | Descargado: " + t.getTotalDescargado() + " MB"); // Datos pedidos + el histórico
            }
        }
    }
}