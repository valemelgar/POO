import java.util.List;
import java.util.Scanner;

public class Principal {
    private Biblioteca biblioteca;
    private Scanner scanner;

    public Principal() {
        biblioteca = new Biblioteca();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Principal programa = new Principal();
        programa.mostrarMenu();
    }

    public void mostrarMenu() {
        int opcion = 0;

        while (opcion != 10) {
            System.out.println("\n--- BIBLIOTECA ---");
            System.out.println("1. Registrar préstamo");
            System.out.println("2. Consultar préstamos");
            System.out.println("3. Buscar préstamo");
            System.out.println("4. Modificar préstamo");
            System.out.println("5. Registrar devolución");
            System.out.println(
                    "6. Consultar préstamos por estudiante"
            );
            System.out.println(
                    "7. Reporte de días autorizados"
            );
            System.out.println(
                    "8. Préstamo con mayor duración"
            );
            System.out.println("9. Cantidad de préstamos");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrarPrestamo();
                        break;

                    case 2:
                        consultarPrestamos();
                        break;

                    case 3:
                        buscarPrestamo();
                        break;

                    case 4:
                        modificarPrestamo();
                        break;

                    case 5:
                        registrarDevolucion();
                        break;

                    case 6:
                        consultarPorEstudiante();
                        break;

                    case 7:
                        mostrarTotalDias();
                        break;

                    case 8:
                        mostrarMayorDuracion();
                        break;

                    case 9:
                        mostrarCantidadPrestamos();
                        break;

                    case 10:
                        System.out.println(
                                "Programa finalizado."
                        );
                        break;

                    default:
                        System.out.println(
                                "Opción incorrecta."
                        );
                }

            } catch (NumberFormatException error) {
                System.out.println(
                        "Error: debe ingresar un número."
                );

            } finally {
                System.out.println("Operación finalizada.");
            }
        }

        scanner.close();
    }

    private void registrarPrestamo() {
        System.out.print("Código: ");
        String codigo = scanner.nextLine();

        System.out.print("Carné: ");
        String carne = scanner.nextLine();

        System.out.print("Nombre del estudiante: ");
        String nombre = scanner.nextLine();

        System.out.print("Título del libro: ");
        String titulo = scanner.nextLine();

        System.out.print("Días autorizados: ");
        int dias = Integer.parseInt(scanner.nextLine());

        if (codigo.equals("")
                || carne.equals("")
                || nombre.equals("")
                || titulo.equals("")) {
            System.out.println(
                    "Los campos no pueden estar vacíos."
            );
            return;
        }

        if (dias <= 0) {
            System.out.println(
                    "Los días deben ser mayores que cero."
            );
            return;
        }

        Prestamo prestamo = new Prestamo(
                codigo,
                carne,
                nombre,
                titulo,
                dias
        );

        boolean agregado =
                biblioteca.agregarPrestamo(prestamo);

        if (agregado == true) {
            System.out.println(
                    "Préstamo registrado correctamente."
            );
        } else {
            System.out.println(
                    "El código ya está registrado."
            );
        }
    }

    private void consultarPrestamos() {
        List<Prestamo> prestamos =
                biblioteca.getPrestamos();

        if (prestamos.size() == 0) {
            System.out.println(
                    "No hay préstamos registrados."
            );
        } else {
            for (Prestamo prestamo : prestamos) {
                System.out.println();
                System.out.println(prestamo);
            }
        }
    }

    private void buscarPrestamo() {
        System.out.print("Código del préstamo: ");
        String codigo = scanner.nextLine();

        Prestamo prestamo =
                biblioteca.buscarPrestamo(codigo);

        if (prestamo == null) {
            System.out.println(
                    "El préstamo no fue encontrado."
            );
        } else {
            System.out.println(prestamo);
        }
    }

    private void modificarPrestamo() {
        System.out.print("Código del préstamo: ");
        String codigo = scanner.nextLine();

        System.out.print("Nuevo título: ");
        String titulo = scanner.nextLine();

        System.out.print("Nuevos días autorizados: ");
        int dias = Integer.parseInt(scanner.nextLine());

        if (titulo.equals("")) {
            System.out.println(
                    "El título no puede estar vacío."
            );
            return;
        }

        if (dias <= 0) {
            System.out.println(
                    "Los días deben ser mayores que cero."
            );
            return;
        }

        boolean modificado =
                biblioteca.modificarPrestamo(
                        codigo,
                        titulo,
                        dias
                );

        if (modificado == true) {
            System.out.println(
                    "Préstamo modificado correctamente."
            );
        } else {
            System.out.println(
                    "El préstamo no fue encontrado."
            );
        }
    }

    private void registrarDevolucion() {
        System.out.print("Código del préstamo: ");
        String codigo = scanner.nextLine();

        boolean eliminado =
                biblioteca.eliminarPrestamo(codigo);

        if (eliminado == true) {
            System.out.println(
                    "Devolución registrada correctamente."
            );
        } else {
            System.out.println(
                    "El préstamo no fue encontrado."
            );
        }
    }

    private void consultarPorEstudiante() {
        System.out.print("Carné del estudiante: ");
        String carne = scanner.nextLine();

        List<Prestamo> resultados =
                biblioteca.buscarPorEstudiante(carne);

        if (resultados.size() == 0) {
            System.out.println(
                    "No se encontraron préstamos."
            );
        } else {
            for (Prestamo prestamo : resultados) {
                System.out.println();
                System.out.println(prestamo);
            }
        }
    }

    private void mostrarTotalDias() {
        int total = biblioteca.calcularTotalDias();

        System.out.println(
                "Total de días autorizados: " + total
        );
    }

    private void mostrarMayorDuracion() {
        Prestamo mayor =
                biblioteca.obtenerPrestamoMayorDuracion();

        if (mayor == null) {
            System.out.println(
                    "No hay préstamos registrados."
            );
        } else {
            System.out.println(
                    "Préstamo con mayor duración:"
            );
            System.out.println(mayor);
        }
    }

    private void mostrarCantidadPrestamos() {
        int cantidad =
                biblioteca.cantidadPrestamos();

        System.out.println(
                "Cantidad de préstamos: " + cantidad
        );
    }
}