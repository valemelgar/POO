import java.util.List;     // Para mostrar listas de mensajes
import java.util.Scanner;  // Para leer del teclado

/**
 * Vista: se encarga únicamente de la entrada y salida por consola.
 * No contiene lógica del simulador.
 */
public class VistaConsola {

    private final Scanner teclado; // Lector de entradas del usuario

    // Constructor: inicializa el lector con la entrada estándar (teclado)
    public VistaConsola() {
        this.teclado = new Scanner(System.in);
    }

    // Muestra el menú, lee la opción y valida que sea una opción existente (1 a 6)
    public int mostrarMenu() {
        int opcion = -1; // Valor inválido inicial para entrar al ciclo
        // Repite hasta que el usuario escriba una opción válida
        while (opcion < 1 || opcion > 6) {
            System.out.println("\n===== SIMULADOR QUETZAL-2 =====");           // Título del menú
            System.out.println("1. Listar todos los módulos");                  // Opción a)
            System.out.println("2. Buscar módulo por ID o nombre");             // Opción b)
            System.out.println("3. Catálogo ordenado por costo");               // Opción c)
            System.out.println("4. Avanzar un ciclo");                          // Opción d)
            System.out.println("5. Resumen de comunicaciones");                 // Opción e)
            System.out.println("6. Salir");                                     // Terminar el programa
            System.out.print("Seleccione una opción: ");                        // Solicita la opción
            String entrada = teclado.nextLine();                                // Lee la línea completa
            try {
                opcion = Integer.parseInt(entrada.trim()); // Convierte el texto a número
            } catch (NumberFormatException e) {
                opcion = -1;                               // Si no es número, se vuelve a pedir
            }
            // Si quedó fuera de rango se avisa
            if (opcion < 1 || opcion > 6) {
                System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
        return opcion; // Devuelve la opción válida al controlador
    }

    // Muestra una indicación y devuelve el texto que escriba el usuario
    public String leerTexto(String mensaje) {
        System.out.print(mensaje); // Muestra la indicación
        return teclado.nextLine(); // Devuelve lo escrito
    }

    // Muestra un mensaje en consola
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    // Muestra los mensajes de una lista, en orden
    public void mostrarMensajes(List<String> mensajes) {
        // Imprime cada mensaje en su propia línea
        for (String m : mensajes) {
            System.out.println(m);
        }
    }
}