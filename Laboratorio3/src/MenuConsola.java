import java.util.List;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

// Interfaz de consola: muestra los menús, lee y valida lo que escribe el usuario y le pide el trabajo
// real a GranjaRenderGT. Aquí NO hay reglas de negocio: si una operación es inválida, el dominio lanza
// una ReglaNegocioException y este menú solo muestra el mensaje y vuelve a mostrar las opciones.
public class MenuConsola {

    // Atributos privados.
    private GranjaRenderGT granja;   // sistema con el que se trabaja
    private Scanner entrada;         // lector de lo que escribe el usuario

    // Constructor: recibe la granja y el Scanner ya creados y los guarda.
    public MenuConsola(GranjaRenderGT granja, Scanner entrada) {
        this.granja = granja;
        this.entrada = entrada;
    }

    // Ciclo principal: muestra el menú y ejecuta la opción elegida hasta que el usuario decide salir.
    public void ejecutar() {
        boolean salir = false;
        try {
            // El menú se repite mientras el usuario no elija salir.
            while (!salir) {
                mostrarMenu();
                int opcion = leerEntero("Seleccione una opción: ");
                try {
                    // Cada número del menú llama al método que realiza esa operación.
                    switch (opcion) {
                        case 1:
                            cotizarTrabajo();
                            break;
                        case 2:
                            registrarTrabajo();
                            break;
                        case 3:
                            registrarNodo();
                            break;
                        case 4:
                            gestionarLotes();
                            break;
                        case 5:
                            consultar();
                            break;
                        case 6:
                            mostrarReportes();
                            break;
                        case 0:
                            salir = true;
                            System.out.println("Hasta luego.");
                            break;
                        default:
                            System.out.println("Opción no válida. Elija un número del menú.");
                    }
                } catch (ReglaNegocioException e) {
                    // Cualquier operación rechazada por el dominio llega aquí: se informa y el programa continúa.
                    System.out.println("ERROR: " + e.getMessage());
                }
            }
        } catch (FinDeEntradaException e) {
            // Ocurre si ya no hay más datos de entrada (por ejemplo, si se cierra la entrada estándar).
            System.out.println("\nNo hay más datos de entrada. Programa finalizado.");
        }
    }

    // Imprime las opciones del menú principal.
    private void mostrarMenu() {
        System.out.println("\n===== RenderGT - Granja de renderizado =====");
        System.out.println("1. Cotizar un trabajo (no registra nada)");
        System.out.println("2. Registrar un trabajo");
        System.out.println("3. Registrar un nodo");
        System.out.println("4. Gestionar lotes (crear, asignar, retirar, iniciar, registrar resultados)");
        System.out.println("5. Consultar trabajos, nodos y lotes");
        System.out.println("6. Reportes");
        System.out.println("0. Salir");
    }

    // Cotiza un trabajo: pide los datos, muestra costo y tiempo de entrega, y NO guarda nada.
    private void cotizarTrabajo() {
        // El trabajo se crea solo para calcular; al no entregarlo a la granja, no se registra ni se cobra.
        Trabajo trabajo = crearTrabajo();
        System.out.println("\n--- Cotización (no se registra ni se cobra nada) ---");
        System.out.println("Categoría: " + trabajo.getCategoria());
        System.out.println("Costo: " + formatearMonto(trabajo.calcularCosto()));
        System.out.println("Tiempo estimado de entrega: " + trabajo.getTiempoEntrega() + " horas");
    }

    // Registra un trabajo: pide los datos, lo entrega a la granja (que lo guarda en cola y cobra el costo completo).
    private void registrarTrabajo() {
        Trabajo trabajo = crearTrabajo();
        // Si el código está repetido, la granja lanza la excepción y no se cobra nada.
        granja.registrarTrabajo(trabajo);
        System.out.println("Trabajo '" + trabajo.getCodigo() + "' registrado y en cola. Se cobró "
                + formatearMonto(trabajo.calcularCosto()) + ". Entrega estimada: "
                + trabajo.getTiempoEntrega() + " horas.");
    }

    // Registra un nodo de cómputo.
    private void registrarNodo() {
        String codigo = leerTexto("Código del nodo: ");
        double capacidad = leerDecimal("Capacidad máxima de horas por lote: ");
        int gpu = leerEntero("¿Tiene GPU? (1 = sí, 2 = no): ");
        // Solo se aceptan las dos respuestas posibles.
        if (gpu != 1 && gpu != 2) {
            throw new ReglaNegocioException("Para la GPU debe elegir 1 (sí) o 2 (no).");
        }
        // gpu == 1 produce true cuando el nodo tiene GPU.
        Nodo nodo = new Nodo(codigo, capacidad, gpu == 1);
        granja.registrarNodo(nodo);
        System.out.println("Nodo registrado: " + nodo);
    }

    // Submenú de lotes: crear, asignar, retirar, iniciar y registrar resultados.
    private void gestionarLotes() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Gestión de lotes ---");
            System.out.println("1. Crear lote");
            System.out.println("2. Asignar trabajo a un lote");
            System.out.println("3. Retirar trabajo de un lote");
            System.out.println("4. Iniciar lote");
            System.out.println("5. Registrar resultado de un trabajo");
            System.out.println("0. Volver al menú principal");
            int opcion = leerEntero("Seleccione una opción: ");
            try {
                switch (opcion) {
                    case 1: {
                        String codigoNodo = leerTexto("Código del nodo: ");
                        Lote lote = granja.crearLote(codigoNodo);
                        System.out.println("Lote creado: " + lote);
                        break;
                    }
                    case 2: {
                        String codigoLote = leerTexto("Código del lote: ");
                        String codigoTrabajo = leerTexto("Código del trabajo: ");
                        granja.asignarTrabajo(codigoLote, codigoTrabajo);
                        System.out.println("Trabajo asignado al lote.");
                        break;
                    }
                    case 3: {
                        String codigoLote = leerTexto("Código del lote: ");
                        String codigoTrabajo = leerTexto("Código del trabajo: ");
                        granja.retirarTrabajo(codigoLote, codigoTrabajo);
                        System.out.println("Trabajo retirado; vuelve a quedar en cola.");
                        break;
                    }
                    case 4: {
                        String codigoLote = leerTexto("Código del lote: ");
                        // Se guarda lo cobrado por reintentos antes de iniciar para poder mostrar cuánto se cargó ahora.
                        double antes = granja.getIngresos().getIngresoPorReintentos();
                        granja.iniciarLote(codigoLote);
                        double cargos = granja.getIngresos().getIngresoPorReintentos() - antes;
                        System.out.println("Lote iniciado. Cargos por reintento registrados: " + formatearMonto(cargos));
                        break;
                    }
                    case 5: {
                        String codigoLote = leerTexto("Código del lote: ");
                        String codigoTrabajo = leerTexto("Código del trabajo: ");
                        ResultadoEjecucion resultado = leerResultado();
                        granja.registrarResultado(codigoLote, codigoTrabajo, resultado);
                        System.out.println("Resultado registrado: " + resultado.getNombre() + ".");
                        break;
                    }
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Elija un número del menú.");
                }
            } catch (ReglaNegocioException e) {
                // El error se muestra y el usuario sigue dentro del submenú de lotes.
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    // Submenú de consultas: lista trabajos, nodos o lotes sin modificar nada.
    private void consultar() {
        System.out.println("\n--- Consultas ---");
        System.out.println("1. Trabajos");
        System.out.println("2. Nodos");
        System.out.println("3. Lotes");
        int opcion = leerEntero("Seleccione una opción: ");
        switch (opcion) {
            case 1: {
                List<Trabajo> trabajos = granja.listarTrabajos();
                if (trabajos.isEmpty()) {
                    System.out.println("No hay trabajos registrados.");
                }
                for (Trabajo trabajo : trabajos) {
                    System.out.println(trabajo);
                    System.out.println("     Costo: " + formatearMonto(trabajo.calcularCosto())
                            + " | Entrega estimada: " + trabajo.getTiempoEntrega() + " horas");
                }
                break;
            }
            case 2: {
                List<Nodo> nodos = granja.listarNodos();
                if (nodos.isEmpty()) {
                    System.out.println("No hay nodos registrados.");
                }
                for (Nodo nodo : nodos) {
                    System.out.println(nodo);
                }
                break;
            }
            case 3: {
                List<Lote> lotes = granja.listarLotes();
                if (lotes.isEmpty()) {
                    System.out.println("No hay lotes creados.");
                }
                for (Lote lote : lotes) {
                    System.out.println(lote);
                    // Debajo de cada lote se listan los trabajos que contiene.
                    for (Trabajo trabajo : lote.getTrabajos()) {
                        System.out.println("     - " + trabajo.getCodigo() + " (" + trabajo.getCategoria()
                                + ", " + trabajo.getEstado() + ")");
                    }
                }
                break;
            }
            default:
                System.out.println("Opción no válida.");
        }
    }

    // Muestra los tres reportes que pide la gerencia.
    private void mostrarReportes() {
        System.out.println("\n===== REPORTES =====");

        // Reporte 1: cuántos trabajos hay de cada categoría y en cada estado.
        System.out.println("\n1) Trabajos por categoría y estado");
        List<GranjaRenderGT.ConteoCategoriaEstado> conteo = granja.contarPorCategoriaYEstado();
        if (conteo.isEmpty()) {
            System.out.println("   No hay trabajos registrados.");
        }
        String ultimaCategoria = null;
        for (GranjaRenderGT.ConteoCategoriaEstado fila : conteo) {
            if (!fila.getCategoria().equals(ultimaCategoria)) {
                System.out.println("   " + fila.getCategoria() + ":");
                ultimaCategoria = fila.getCategoria();
            }
            System.out.println("      - " + fila.getEstado() + ": " + fila.getCantidad());
        }

        // Reporte 2: ingreso total, separado por registro y por reintentos, e ingreso por categoría.
        // getIngresos() devuelve una copia, así que consultar no puede alterar la contabilidad.
        System.out.println("\n2) Ingresos");
        RegistroIngresos ingresos = granja.getIngresos();
        System.out.println("   Ingreso total:          " + formatearMonto(ingresos.getIngresoTotal()));
        System.out.println("   Por registro:           " + formatearMonto(ingresos.getIngresoPorRegistro()));
        System.out.println("   Por reintentos:         " + formatearMonto(ingresos.getIngresoPorReintentos()));
        System.out.println("   Ingreso por categoría:");
        if (ingresos.getIngresoPorCategoria().isEmpty()) {
            System.out.println("      (sin ingresos todavía)");
        }
        ingresos.getIngresoPorCategoria().forEach((categoria, monto) ->
                System.out.println("      - " + categoria + ": " + formatearMonto(monto)));

        // Reporte 3: lotes que no están cerrados, con su nodo, horas asignadas y capacidad disponible.
        System.out.println("\n3) Lotes no cerrados");
        List<Lote> abiertos = granja.listarLotesNoCerrados();
        if (abiertos.isEmpty()) {
            System.out.println("   No hay lotes sin cerrar.");
        }
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formatoHoras = new DecimalFormat("0.00", simbolos);
        formatoHoras.setRoundingMode(java.math.RoundingMode.HALF_UP);
        for (Lote lote : abiertos) {
            System.out.println("   " + lote.getCodigo() + " | Nodo: " + lote.getNodo().getCodigo()
                    + " | Estado: " + lote.getEstado()
                    + " | Horas asignadas: " + formatoHoras.format(lote.calcularHorasAsignadas())
                    + " | Capacidad disponible: " + formatoHoras.format(lote.calcularCapacidadDisponible())
                    + " de " + formatoHoras.format(lote.getNodo().getCapacidadMaxima()));
        }
    }

    // Pide la categoría y los datos del trabajo y construye el objeto correspondiente.
    // El switch es sobre la OPCIÓN que escribió el usuario (para saber qué datos pedir y qué clase crear);
    // no se revisa el tipo de ningún objeto existente.
    private Trabajo crearTrabajo() {
        System.out.println("\nCategorías: 1. Imagen fija  2. Animación  3. Animación con postproducción  4. Simulación física");
        int categoria = leerEntero("Seleccione la categoría: ");
        // Se rechaza una categoría inexistente antes de pedir el resto de los datos.
        if (categoria < 1 || categoria > 4) {
            throw new ReglaNegocioException("La categoría seleccionada no existe.");
        }
        // Datos comunes a todas las categorías.
        String codigo = leerTexto("Código del trabajo: ");
        String estudio = leerTexto("Nombre del estudio: ");
        String proyecto = leerTexto("Nombre del proyecto: ");
        double horas = leerDecimal("Horas de cómputo estimadas: ");
        Horario horario = leerHorario();
        // Datos propios de cada categoría; el constructor de cada clase valida sus reglas y lanza excepción si fallan.
        switch (categoria) {
            case 1:
                return new ImagenFija(codigo, estudio, proyecto, horas, horario);
            case 2: {
                int fotogramas = leerEntero("Cantidad de fotogramas: ");
                return new Animacion(codigo, estudio, proyecto, horas, horario, fotogramas);
            }
            case 3: {
                int fotogramas = leerEntero("Cantidad de fotogramas: ");
                double recursos = leerDecimal("Valor declarado de los recursos licenciados (Q): ");
                return new AnimacionPostproduccion(codigo, estudio, proyecto, horas, horario, fotogramas, recursos);
            }
            default: {
                int malla = leerEntero("Resolución de la malla (millones de celdas): ");
                return new SimulacionFisica(codigo, estudio, proyecto, horas, horario, malla);
            }
        }
    }

    // Muestra un mensaje y lee una línea de texto (sin espacios al inicio ni al final).
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        // Si se termina la entrada, ejecutar() muestra el mensaje de finalización.
        if (!entrada.hasNextLine()) {
            throw new FinDeEntradaException();
        }
        return entrada.nextLine().trim();
    }

    // Lee un entero. Si el usuario escribe algo que no es un entero, avisa y vuelve a preguntar.
    private int leerEntero(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                // Si el texto no es un entero válido, parseInt lanza NumberFormatException.
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida: escriba un número entero (sin decimales ni letras).");
            }
        }
    }

    // Lee un decimal. Acepta coma o punto como separador; si el formato es incorrecto, vuelve a preguntar.
    // Los valores no positivos o fuera de rango los rechaza el dominio, no este método.
    private double leerDecimal(String mensaje) {
        while (true) {
            // Se cambia la coma por punto para que "9,5" también funcione.
            String texto = leerTexto(mensaje).replace(',', '.');
            try {
                double valor = Double.parseDouble(texto);
                // "NaN" e "Infinity" se pueden parsear, pero no son números válidos para este programa.
                if (Double.isNaN(valor) || Double.isInfinite(valor)) {
                    System.out.println("Entrada no válida: escriba un número.");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida: escriba un número (por ejemplo 9.5).");
            }
        }
    }

    // Pregunta el horario y crea el objeto Horario correspondiente. Repite hasta recibir una opción válida.
    private Horario leerHorario() {
        while (true) {
            int opcion = leerEntero("Horario (1 = diurno, 2 = nocturno): ");
            if (opcion == 1) {
                return new Diurno();
            }
            if (opcion == 2) {
                return new Nocturno();
            }
            System.out.println("Opción no válida: elija 1 (diurno) o 2 (nocturno).");
        }
    }

    // Pregunta el resultado de la ejecución y crea el objeto ResultadoEjecucion correspondiente.
    private ResultadoEjecucion leerResultado() {
        while (true) {
            int opcion = leerEntero("Resultado (1 = completado, 2 = ejecución fallida): ");
            if (opcion == 1) {
                return new ResultadoCompletado();
            }
            if (opcion == 2) {
                return new ResultadoFallido();
            }
            System.out.println("Opción no válida: elija 1 (completado) o 2 (fallido).");
        }
    }

    // Da formato de dinero a un monto: "Q" + miles con coma + exactamente dos decimales.
    private String formatearMonto(double monto) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        simbolos.setGroupingSeparator(',');
        DecimalFormat formato = new DecimalFormat("#,##0.00", simbolos);
        formato.setRoundingMode(java.math.RoundingMode.HALF_UP);
        return "Q" + formato.format(monto);
    }

    private static class FinDeEntradaException extends RuntimeException {
    }
}
