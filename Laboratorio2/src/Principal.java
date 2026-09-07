import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
    private static final Scanner scanner =
            new Scanner(System.in);

    public static void main(String[] args) {
        Caso casoActual = null;
        int opcion = 0;

        try {
            do {
                mostrarMenu();

                opcion = leerEntero(
                        "Seleccione una opción: "
                );

                try {
                    if (opcion == 1) {
                        casoActual = solicitarNuevoCaso();

                        System.out.println(
                                "El caso fue creado correctamente."
                        );
                    } else if (opcion == 13) {
                        System.out.println(
                                "Saliendo del sistema..."
                        );
                    } else if (casoActual == null) {
                        System.out.println(
                                "Primero debe crear un caso."
                        );
                    } else {
                        ejecutarOpcion(
                                opcion,
                                casoActual
                        );
                    }
                } catch (Exception e) {
                    System.out.println(
                            "Error: " + e.getMessage()
                    );
                }

            } while (opcion != 13);

        } finally {
            scanner.close();

            System.out.println(
                    "Programa finalizado."
            );
        }
    }

    private static void mostrarMenu() {
        System.out.println(
                "\n===== AGENCIA DE DETECTIVES ====="
        );

        System.out.println("1. Nuevo caso");
        System.out.println("2. Registrar ubicación");
        System.out.println("3. Consultar ubicaciones");
        System.out.println("4. Consultar una ubicación");
        System.out.println("5. Modificar ubicación");
        System.out.println("6. Descartar ubicación");
        System.out.println("7. Registrar pista");
        System.out.println("8. Consultar pistas");
        System.out.println("9. Buscar pista");
        System.out.println("10. Modificar pista");
        System.out.println("11. Eliminar pista");

        System.out.println(
                "12. Mostrar reporte de investigación"
        );

        System.out.println("13. Salir");
    }

    private static Caso solicitarNuevoCaso()
            throws Exception {
        System.out.println("\n--- Nuevo caso ---");

        String nombre = leerTexto(
                "Nombre del caso: "
        );

        String codigo = leerTexto(
                "Código de identificación: "
        );

        String detective = leerTexto(
                "Detective responsable: "
        );

        return new Caso(
                nombre,
                codigo,
                detective
        );
    }

    private static void ejecutarOpcion(
            int opcion,
            Caso caso
    ) throws Exception {
        switch (opcion) {
            case 2:
                registrarUbicacion(caso);
                break;

            case 3:
                caso.mostrarUbicaciones();
                break;

            case 4:
                consultarUbicacion(caso);
                break;

            case 5:
                modificarUbicacion(caso);
                break;

            case 6:
                descartarUbicacion(caso);
                break;

            case 7:
                registrarPista(caso);
                break;

            case 8:
                caso.mostrarPistas();
                break;

            case 9:
                buscarPista(caso);
                break;

            case 10:
                modificarPista(caso);
                break;

            case 11:
                eliminarPista(caso);
                break;

            case 12:
                mostrarReporte(caso);
                break;

            default:
                System.out.println(
                        "La opción seleccionada no es válida."
                );
        }
    }

    private static void registrarUbicacion(
            Caso caso
    ) throws Exception {
        System.out.println(
                "\n--- Registrar ubicación ---"
        );

        int posicion = leerEntero(
                "Posición del arreglo (1-5): "
        ) - 1;

        String codigo = leerTexto(
                "Código: "
        );

        String nombre = leerTexto(
                "Nombre: "
        );

        String descripcion = leerTexto(
                "Dirección o descripción: "
        );

        int riesgo = leerEntero(
                "Nivel de riesgo (1-10): "
        );

        String estado = leerTexto(
                "Estado: "
        );

        Ubicacion ubicacion = new Ubicacion(
                codigo,
                nombre,
                descripcion,
                riesgo,
                estado
        );

        caso.registrarUbicacion(
                posicion,
                ubicacion
        );

        System.out.println(
                "Ubicación registrada correctamente."
        );
    }

    private static void consultarUbicacion(
            Caso caso
    ) throws Exception {
        int posicion = leerEntero(
                "Posición que desea consultar (1-5): "
        ) - 1;

        System.out.println(
                "\n" + caso.obtenerUbicacion(posicion)
        );
    }

    private static void modificarUbicacion(
            Caso caso
    ) throws Exception {
        int posicion = leerEntero(
                "Posición que desea modificar (1-5): "
        ) - 1;

        int riesgo = leerEntero(
                "Nuevo nivel de riesgo (1-10): "
        );

        String estado = leerTexto(
                "Nuevo estado: "
        );

        caso.modificarUbicacion(
                posicion,
                riesgo,
                estado
        );

        System.out.println(
                "Ubicación modificada correctamente."
        );
    }

    private static void descartarUbicacion(
            Caso caso
    ) throws Exception {
        int posicion = leerEntero(
                "Posición que desea descartar (1-5): "
        ) - 1;

        caso.descartarUbicacion(posicion);

        System.out.println(
                "Ubicación descartada. "
                        + "La posición está disponible nuevamente."
        );
    }

    private static void registrarPista(
            Caso caso
    ) throws Exception {
        System.out.println(
                "\n--- Registrar pista ---"
        );

        String codigo = leerTexto(
                "Código: "
        );

        String descripcion = leerTexto(
                "Descripción: "
        );

        String tipo = leerTexto(
                "Tipo de evidencia: "
        );

        int importancia = leerEntero(
                "Nivel de importancia (1-10): "
        );

        int confiabilidad = leerEntero(
                "Nivel de confiabilidad (0-100): "
        );

        Pista pista = new Pista(
                codigo,
                descripcion,
                tipo,
                importancia,
                confiabilidad
        );

        caso.registrarPista(pista);

        System.out.println(
                "Pista registrada correctamente."
        );
    }

    private static void buscarPista(
            Caso caso
    ) throws Exception {
        String codigo = leerTexto(
                "Código de la pista: "
        );

        Pista pista =
                caso.buscarPista(codigo);

        if (pista == null) {
            System.out.println(
                    "No se encontró una pista con ese código."
            );
        } else {
            System.out.println(
                    "\n" + pista
            );
        }
    }

    private static void modificarPista(
            Caso caso
    ) throws Exception {
        String codigo = leerTexto(
                "Código de la pista que desea modificar: "
        );

        String descripcion = leerTexto(
                "Nueva descripción: "
        );

        String tipo = leerTexto(
                "Nuevo tipo de evidencia: "
        );

        int importancia = leerEntero(
                "Nuevo nivel de importancia (1-10): "
        );

        int confiabilidad = leerEntero(
                "Nuevo nivel de confiabilidad (0-100): "
        );

        boolean modificada =
                caso.modificarPista(
                        codigo,
                        descripcion,
                        tipo,
                        importancia,
                        confiabilidad
                );

        if (modificada) {
            System.out.println(
                    "Pista modificada correctamente."
            );
        } else {
            System.out.println(
                    "No se encontró una pista con ese código."
            );
        }
    }

    private static void eliminarPista(
            Caso caso
    ) throws Exception {
        String codigo = leerTexto(
                "Código de la pista que desea eliminar: "
        );

        if (caso.eliminarPista(codigo)) {
            System.out.println(
                    "Pista eliminada correctamente."
            );
        } else {
            System.out.println(
                    "No se encontró una pista con ese código."
            );
        }
    }

    private static void mostrarReporte(
            Caso caso
    ) {
        System.out.println(
                "\n===== REPORTE DE INVESTIGACIÓN ====="
        );

        System.out.println(
                "Caso: " + caso.getNombre()
        );

        System.out.println(
                "Código: " + caso.getCodigo()
        );

        System.out.println(
                "Detective responsable: "
                        + caso.getDetectiveResponsable()
        );

        System.out.println(
                "Ubicaciones registradas: "
                        + caso.contarUbicaciones()
        );

        System.out.println(
                "Espacios disponibles: "
                        + caso.contarEspaciosDisponibles()
        );

        Ubicacion ubicacionRiesgosa =
                caso.obtenerUbicacionMayorRiesgo();

        if (ubicacionRiesgosa == null) {
            System.out.println(
                    "Ubicación con mayor riesgo: "
                            + "no hay ubicaciones registradas."
            );
        } else {
            System.out.println(
                    "Ubicación con mayor riesgo:\n"
                            + ubicacionRiesgosa
            );
        }

        System.out.println(
                "Cantidad de pistas: "
                        + caso.contarPistas()
        );

        Pista mayorImportancia =
                caso.obtenerPistaMayorImportancia();

        Pista mayorConfiabilidad =
                caso.obtenerPistaMayorConfiabilidad();

        if (mayorImportancia == null) {
            System.out.println(
                    "No hay pistas registradas "
                            + "para realizar los cálculos."
            );
        } else {
            System.out.println(
                    "Pista con mayor importancia:\n"
                            + mayorImportancia
            );

            System.out.println(
                    "Pista con mayor confiabilidad:\n"
                            + mayorConfiabilidad
            );

            System.out.printf(
                    "Promedio de importancia: %.2f%n",
                    caso.calcularPromedioImportancia()
            );
        }
    }

    private static int leerEntero(
            String mensaje
    ) {
        while (true) {
            try {
                System.out.print(mensaje);

                int numero = scanner.nextInt();
                scanner.nextLine();

                return numero;

            } catch (InputMismatchException e) {
                System.out.println(
                        "Entrada incorrecta. "
                                + "Debe ingresar un número entero."
                );

                scanner.nextLine();
            }
        }
    }

    private static String leerTexto(
            String mensaje
    ) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
}