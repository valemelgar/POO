import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

// VISTA: lo único que lee del teclado y escribe en pantalla. Maneja entradas con formato
// incorrecto sin terminar el programa.
public class VistaConsola {

    // Lector del teclado.
    private Scanner teclado;

    public VistaConsola() {
        this.teclado = new Scanner(System.in);
    }

    // Muestra el menú y devuelve la opción elegida. Si se cierra la entrada devuelve 0 (salir).
    public int mostrarMenu() {
        System.out.println();
        System.out.println("========== TORREGT - MENÚ ==========");
        System.out.println(" 1. Registrar oficina");
        System.out.println(" 2. Registrar dispositivo");
        System.out.println(" 3. Encender dispositivo");
        System.out.println(" 4. Apagar dispositivo");
        System.out.println(" 5. Ajustar nivel de un dispositivo");
        System.out.println(" 6. Registrar lectura");
        System.out.println(" 7. Registrar movimiento detectado");
        System.out.println(" 8. Avanzar una hora de simulación");
        System.out.println(" 9. Atender la alerta más urgente");
        System.out.println("10. Consultar oficinas");
        System.out.println("11. Consultar dispositivos");
        System.out.println("12. Consultar alertas pendientes");
        System.out.println("13. Reporte: dispositivos de una oficina");
        System.out.println("14. Reporte: bandeja y alertas atendidas con retraso");
        System.out.println("15. Reporte: facturas, totales por franja y créditos");
        System.out.println(" 0. Salir");
        try {
            return leerEntero("Elija una opción: ");
        } catch (OperacionInvalidaException e) {
            return 0; // entrada cerrada: se sale ordenadamente
        }
    }

    // Muestra el mensaje y lee una línea de texto.
    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        try {
            return teclado.nextLine().trim();
        } catch (NoSuchElementException e) {
            throw new OperacionInvalidaException("No hay más datos de entrada.");
        }
    }

    // Lee un entero; si el formato es incorrecto avisa y vuelve a pedirlo.
    public int leerEntero(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                mostrarError("Entrada inválida: escriba un número entero.");
            }
        }
    }

    // Lee un decimal (acepta coma o punto); rechaza NaN e infinito.
    public double leerDecimal(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje).replace(',', '.');
            try {
                double valor = Double.parseDouble(texto);
                if (Double.isNaN(valor) || Double.isInfinite(valor)) {
                    throw new NumberFormatException();
                }
                return valor;
            } catch (NumberFormatException e) {
                mostrarError("Entrada inválida: escriba un número decimal.");
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje) {
        System.out.println("ERROR: " + mensaje);
    }

    public void mostrarMensajes(List<String> mensajes) {
        for (String mensaje : mensajes) {
            System.out.println(mensaje);
        }
    }
}