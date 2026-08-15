import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        Usuario usuario = new Usuario();

        // Datos del usuario
        System.out.print("Nombre: ");
        usuario.setNombre(teclado.nextLine());

        System.out.print("Nombre de usuario: ");
        usuario.setNombreUsuario(teclado.nextLine());

        System.out.print("Edad: ");
        usuario.setEdad(teclado.nextInt());

        // Preguntar cuantas peliculas quiere registrar
        System.out.print("¿Cuantas peliculas quiere registrar? ");
        int cantidad = teclado.nextInt();

        // Validar que sea entre 1 y 10
        while (cantidad < 1) {
            System.out.print("Ingrese una cantidad entre 1 y 10: ");
            cantidad = teclado.nextInt();
        }

        while (cantidad > 10) {
            System.out.print("Ingrese una cantidad entre 1 y 10: ");
            cantidad = teclado.nextInt();
        }

        // Registrar las peliculas
        for (int i = 0; i < cantidad; i++) {

            System.out.print("Calificacion de la pelicula "
                    + (i + 1) + ": ");

            int calificacion = teclado.nextInt();

            while (usuario.registrarCalificacion(calificacion) == false) {
                System.out.print(
                    "Calificacion invalida. Ingrese un valor de 1 a 10: "
                );

                calificacion = teclado.nextInt();
            }
        }

        int opcion = 0;

        while (opcion != 9) {

            System.out.println("\n--- MENU ---");
            System.out.println("1. Nuevo usuario");
            System.out.println("2. Registrar calificacion");
            System.out.println("3. Consultar calificaciones");
            System.out.println("4. Consultar una pelicula");
            System.out.println("5. Modificar calificacion");
            System.out.println("6. Mostrar promedio");
            System.out.println("7. Mostrar mejor y peor");
            System.out.println("8. Espacios disponibles");
            System.out.println("9. Salir");

            System.out.print("Opcion: ");
            opcion = teclado.nextInt();

            switch (opcion) {

                case 1:

                    usuario = new Usuario();
                    teclado.nextLine();

                    System.out.print("Nombre: ");
                    usuario.setNombre(teclado.nextLine());

                    System.out.print("Nombre de usuario: ");
                    usuario.setNombreUsuario(teclado.nextLine());

                    System.out.print("Edad: ");
                    usuario.setEdad(teclado.nextInt());

                    break;

                case 2:

                    System.out.print("Calificacion: ");
                    int calificacion = teclado.nextInt();

                    if (usuario.registrarCalificacion(calificacion)) {
                        System.out.println("Calificacion registrada.");
                    } else {
                        System.out.println("No se pudo registrar.");
                    }

                    break;

                case 3:

                    usuario.mostrarCalificaciones();
                    break;

                case 4:

                    System.out.print("Numero de pelicula: ");
                    int numero = teclado.nextInt();

                    int resultado = usuario.consultarCalificacion(numero);

                    if (resultado == -1) {
                        System.out.println("Pelicula invalida.");
                    } else {
                        System.out.println("Calificacion: " + resultado);
                    }

                    break;

                case 5:

                    System.out.print("Numero de pelicula: ");
                    int pelicula = teclado.nextInt();

                    System.out.print("Nueva calificacion: ");
                    int nueva = teclado.nextInt();

                    if (usuario.modificarCalificacion(pelicula, nueva)) {
                        System.out.println("Calificacion modificada.");
                    } else {
                        System.out.println("No se pudo modificar.");
                    }

                    break;

                case 6:

                    System.out.println(
                        "Promedio: " + usuario.calcularPromedio()
                    );

                    break;

                case 7:

                    System.out.println(
                        "Mejor: " + usuario.mejorCalificacion()
                    );

                    System.out.println(
                        "Peor: " + usuario.peorCalificacion()
                    );

                    break;

                case 8:

                    System.out.println(
                        "Peliculas calificadas: "
                        + usuario.getCantidadCalificaciones()
                    );

                    System.out.println(
                        "Espacios disponibles: "
                        + usuario.espaciosDisponibles()
                    );

                    break;

                case 9:

                    System.out.println("Adios.");
                    break;

                default:

                    System.out.println("Opcion invalida.");
            }
        }

        teclado.close();
    }
}