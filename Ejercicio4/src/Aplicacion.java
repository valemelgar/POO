import java.util.Scanner;

public class Aplicacion {
    private GestorAlquileres gestor;
    private Scanner entrada;
    private boolean finEntrada;

    public Aplicacion() {
        gestor = new GestorAlquileres();
        entrada = new Scanner(System.in);
        finEntrada = false;
        cargarDatosIniciales();
    }

    public static void main(String[] args) {
        Aplicacion aplicacion = new Aplicacion();
        aplicacion.ejecutar();
    }

    public void ejecutar() {
        boolean salir = false;
        while (!salir && !finEntrada) {
            mostrarMenu();
            int opcion = leerEnteroPositivo("Opcion: ");
            if (finEntrada) { break; }
            switch (opcion) {
                case 1: procesarRegistro(); break;
                case 2: System.out.println(gestor.consultarInventario()); break;
                case 3: procesarCotizacion(); break;
                case 4: procesarAlquiler(); break;
                case 5: procesarDevolucion(); break;
                case 6: System.out.println(gestor.generarReporte()); break;
                case 7: salir = true; break;
                default: System.out.println("Seleccione una opcion del 1 al 7.");
            }
        }
        System.out.println("Programa finalizado.");
        entrada.close();
    }

    private void mostrarMenu() {
        System.out.println("\nDULCE ESTACION\n1. Registrar maquina\n2. Consultar inventario"
            + "\n3. Cotizar\n4. Alquilar\n5. Registrar devolucion\n6. Reporte general\n7. Salir");
    }

    private void cargarDatosIniciales() {
        gestor.registrarPalomitas(new MaquinaPalomitas("P01", "Dulce", "P100", 100, 100, true));
        gestor.registrarPalomitas(new MaquinaPalomitas("P02", "Dulce", "P200", 100, 80, false));
        gestor.registrarAlgodon(new MaquinaAlgodon("A01", "Dulce", "A100", 120, 1000));
        gestor.registrarAlgodon(new MaquinaAlgodon("A02", "Dulce", "A200", 120, 1200));
        gestor.registrarChocolate(new FuenteChocolate("C01", "Dulce", "C100", 150, 2.5));
        gestor.registrarChocolate(new FuenteChocolate("C02", "Dulce", "C200", 100, 3));
    }

    private String leerLinea(String mensaje) {
        if (finEntrada) { return ""; }
        System.out.print(mensaje);
        if (!entrada.hasNextLine()) {
            finEntrada = true;
            return "";
        }
        return entrada.nextLine();
    }

    private String leerTexto(String mensaje) {
        while (!finEntrada) {
            String texto = leerLinea(mensaje);
            if (finEntrada || texto.matches(".*\\S.*")) { return texto; }
            System.out.println("El texto no puede estar vacio.");
        }
        return "";
    }

    private int leerEnteroPositivo(String mensaje) {
        while (!finEntrada) {
            String texto = leerLinea(mensaje);
            if (finEntrada) { return -1; }
            Scanner lector = new Scanner(texto);

            if (texto.matches("[+]?[0-9]+") && lector.hasNextInt()) {
                int valor = lector.nextInt();
                lector.close();
                if (valor > 0) { return valor; }
            } else {
                lector.close();
            }
            System.out.println("Ingrese un entero positivo valido (maximo 2147483647).");
        }
        return -1;
    }

    private double leerDecimalPositivo(String mensaje) {
        while (!finEntrada) {
            String texto = leerLinea(mensaje);
            if (finEntrada) { return -1; }

            if (texto.matches("[+]?[0-9]+([.][0-9]+)?")) {
                double valor = Double.parseDouble(texto);
                if (valor <= Double.MAX_VALUE && valor > 0) { return valor; }
            }
            System.out.println("Ingrese un numero positivo finito; use punto para decimales.");
        }
        return -1;
    }

    private boolean leerConfirmacion(String mensaje) {
        while (!finEntrada) {
            String respuesta = leerLinea(mensaje + " (s/n): ");
            if (respuesta.equalsIgnoreCase("s")) { return true; }
            if (respuesta.equalsIgnoreCase("n") || finEntrada) { return false; }
            System.out.println("Responda s o n.");
        }
        return false;
    }

    private void procesarRegistro() {
        int tipo = leerEnteroPositivo("Categoria: 1 Palomitas, 2 Algodon, 3 Chocolate: ");
        if (finEntrada) { return; }
        if (tipo > 3) { System.out.println("Categoria invalida."); return; }
        String codigo = leerTexto("Codigo: ");
        if (finEntrada) { return; }
        if (!codigo.matches("\\S+")) {
            System.out.println("El codigo no puede contener espacios."); return;
        }
        if (gestor.existeCodigo(codigo)) {
            System.out.println("Ya existe una maquina con ese codigo."); return;
        }
        String marca = leerTexto("Marca: ");
        String modelo = leerTexto("Modelo: ");
        double tarifa = leerDecimalPositivo("Tarifa diaria: Q");
        if (finEntrada) { return; }
        boolean registrado = false;
        if (tipo == 1) {
            int porciones = leerEnteroPositivo("Porciones por hora: ");
            boolean carrito = leerConfirmacion("Tiene carrito");
            if (!finEntrada) {
                registrado = gestor.registrarPalomitas(new MaquinaPalomitas(
                    codigo, marca, modelo, tarifa, porciones, carrito));
            }
        } else if (tipo == 2) {
            int potencia = leerEnteroPositivo("Potencia en vatios: ");
            if (!finEntrada) {
                registrado = gestor.registrarAlgodon(new MaquinaAlgodon(
                    codigo, marca, modelo, tarifa, potencia));
            }
        } else {
            double capacidad = leerDecimalPositivo("Capacidad maxima en kg: ");
            if (!finEntrada) {
                registrado = gestor.registrarChocolate(new FuenteChocolate(
                    codigo, marca, modelo, tarifa, capacidad));
            }
        }
        if (!finEntrada) {
            System.out.println(registrado ? "Maquina registrada y disponible."
                : "Registro rechazado: datos invalidos o codigo repetido.");
        }
    }

    private void procesarCotizacion() {
        String codigo = leerTexto("Codigo: ");
        if (finEntrada) { return; }
        if (!gestor.existeCodigo(codigo)) {
            System.out.println("No existe una maquina con ese codigo."); return;
        }
        int dias = leerEnteroPositivo("Dias: ");
        if (finEntrada) { return; }
        System.out.println(gestor.consultarMaquina(codigo));
        double total = gestor.cotizar(codigo, dias);
        if (total <= 0) { System.out.println("No se puede representar el costo solicitado."); return; }
        System.out.printf("Cotizacion: Q%.2f%n", total);
        System.out.println("La cotizacion no registra un alquiler.");
    }

    private void procesarAlquiler() {
        String codigo = leerTexto("Codigo: ");
        if (finEntrada) { return; }
        if (!gestor.existeCodigo(codigo)) {
            System.out.println("No existe una maquina con ese codigo."); return;
        }
        if (!gestor.estaDisponible(codigo)) {
            System.out.println("La maquina esta alquilada. No puede alquilarse nuevamente."); return;
        }
        int dias = leerEnteroPositivo("Dias: ");
        if (finEntrada) { return; }
        double total = gestor.cotizar(codigo, dias);
        if (total <= 0) { System.out.println("No se puede representar el costo solicitado."); return; }
        System.out.println(gestor.consultarMaquina(codigo));
        System.out.printf("Total a cobrar: Q%.2f%n", total);
        if (!leerConfirmacion("Confirmar alquiler")) {
            if (!finEntrada) { System.out.println("Alquiler cancelado. No se modificaron los datos."); }
            return;
        }
        if (gestor.confirmarAlquiler(codigo, dias)) {
            System.out.printf("Alquiler confirmado. Cobrado: Q%.2f%n", total);
        } else {
            System.out.println("No se pudo confirmar el alquiler. No se modificaron los datos.");
        }
    }

    private void procesarDevolucion() {
        String codigo = leerTexto("Codigo: ");
        if (finEntrada) { return; }
        if (!gestor.existeCodigo(codigo)) {
            System.out.println("No existe una maquina con ese codigo.");
        } else if (gestor.estaDisponible(codigo)) {
            System.out.println("La maquina ya esta disponible; no puede devolverse.");
        } else if (gestor.registrarDevolucion(codigo)) {
            System.out.println("Devolucion registrada. Los ingresos no cambian.");
        }
    }
}
