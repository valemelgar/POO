import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SistemaVentas sistema = new SistemaVentas();
        Comprador comprador = null;

        int opcion = 0;

        while (opcion != 6) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Nuevo comprador");
            System.out.println("2. Solicitud de boletos");
            System.out.println("3. Disponibilidad total");
            System.out.println("4. Disponibilidad individual");
            System.out.println("5. Reporte de caja");
            System.out.println("6. Salir");
            System.out.print("Opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 1) {
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();

                System.out.print("Email: ");
                String email = sc.nextLine();

                System.out.print("Cantidad de boletos: ");
                int cantidad = sc.nextInt();

                System.out.print("Presupuesto maximo: ");
                int presupuesto = sc.nextInt();
                sc.nextLine();

                comprador = new Comprador(nombre, email, cantidad, presupuesto);
                System.out.println("Comprador registrado.");

            } else if (opcion == 2) {
                if (comprador == null) {
                    System.out.println("Primero registre un comprador.");
                } else {
                    System.out.println(sistema.procesarSolicitud(comprador));
                }

            } else if (opcion == 3) {
                System.out.println(sistema.consultarDisponibilidad());

            } else if (opcion == 4) {
                System.out.print("Ingrese localidad (1, 5 o 10): ");
                int num = sc.nextInt();
                sc.nextLine();

                if (num == 1) {
                    System.out.println("Localidad 1 disponible.");
                } else if (num == 5) {
                    System.out.println("Localidad 5 disponible.");
                } else if (num == 10) {
                    System.out.println("Localidad 10 disponible.");
                } else {
                    System.out.println("Localidad no valida.");
                }

            } else if (opcion == 5) {
                System.out.println(sistema.reporteCaja());

            } else if (opcion == 6) {
                System.out.println("Saliendo...");
            }
        }

        sc.close();
    }
}