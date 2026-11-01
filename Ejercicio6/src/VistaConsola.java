// ============================================================
// Propósito: clase de la VISTA (MVC). Es la única que lee del teclado y
// escribe en pantalla. No contiene lógica del negocio: solo muestra y pide datos.
// ============================================================
import java.util.List;
import java.util.Scanner;

public class VistaConsola {

    // Scanner para leer lo que escribe el usuario por teclado.
    private Scanner teclado;

    // Constructor: crea el Scanner conectado a la entrada estándar.
    public VistaConsola() {
        this.teclado = new Scanner(System.in);
    }

    // Muestra el menú principal, lee la opción y la devuelve.
    // Si el usuario escribe algo que no es un número, devuelve -1 (opción inválida).
    public int mostrarMenu() {
        System.out.println();
        System.out.println("========== INTEGRACIONES CON APIs EXTERNAS ==========");
        System.out.println("1. Listar integraciones activas");
        System.out.println("2. Buscar integración por identificador");
        System.out.println("3. Ordenar catálogo por latencia");
        System.out.println("4. Ver operaciones disponibles de una integración");
        System.out.println("5. Ejecutar una operación");
        System.out.println("0. Salir");
        System.out.print("Elija una opción: ");
        String texto = teclado.nextLine().trim();
        try {
            return Integer.parseInt(texto);   // convierte el texto a número
        } catch (NumberFormatException e) {
            return -1;                        // no era un número: se marca como inválida
        }
    }

    // Muestra un mensaje y devuelve el texto que escribe el usuario.
    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine().trim();
    }

    // Pide un número decimal y repite la pregunta hasta que el usuario escriba uno válido.
    public double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            // Se cambia la coma por punto para aceptar "10,5" y "10.5".
            String texto = teclado.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(texto);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Escriba un número (ejemplo: 150.75).");
            }
        }
    }

    // Muestra una línea de texto en pantalla.
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    // Muestra varias líneas, una por cada elemento de la lista.
    public void mostrarMensajes(List<String> mensajes) {
        for (String mensaje : mensajes) {
            System.out.println(mensaje);
        }
    }
}