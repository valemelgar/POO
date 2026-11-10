 import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// CONTROLADOR: une la vista con el modelo. Pide datos a la vista, llama al sistema y le pide a la
// vista que muestre el resultado. Atrapa las OperacionInvalidaException para que el programa no termine.
public class ControladorSimulador {

    // Modelo con la lógica del negocio.
    private SistemaTorreGT sistema;

    // Vista de consola.
    private VistaConsola vista;

    public ControladorSimulador(SistemaTorreGT sistema, VistaConsola vista) {
        this.sistema = sistema;
        this.vista = vista;
    }

    // Carga los datos iniciales y repite el menú hasta que el usuario elija salir (0).
    public void iniciar() {
        cargarDatosIniciales();
        vista.mostrarMensaje("Bienvenido a TorreGT. Simulación iniciada en la hora 0.");
        int opcion = -1;
        while (opcion != 0) {
            vista.mostrarMensaje("\nHora de simulación: " + sistema.getHoraSimulacion()
                    + " (hora del día: " + sistema.getHoraDelDia() + ":00)");
            opcion = vista.mostrarMenu();
            if (opcion != 0) {
                ejecutarOpcion(opcion);
            }
        }
        vista.mostrarMensaje("Programa finalizado.");
    }

    // Ejecuta la opción elegida. Cualquier operación inválida se atrapa aquí y se muestra su mensaje.
    public void ejecutarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1: registrarOficina(); break;
                case 2: registrarDispositivo(); break;
                case 3: encenderDispositivo(); break;
                case 4: apagarDispositivo(); break;
                case 5: ajustarNivel(); break;
                case 6: registrarLectura(); break;
                case 7: registrarMovimiento(); break;
                case 8: avanzarHora(); break;
                case 9: atenderAlerta(); break;
                case 10: consultarOficinas(); break;
                case 11: consultarDispositivos(); break;
                case 12: consultarAlertas(); break;
                case 13: reporteDispositivosOficina(); break;
                case 14: reporteBandeja(); break;
                case 15: reporteFacturas(); break;
                default: vista.mostrarError("Opción no válida. Elija un número del menú.");
            }
        } catch (OperacionInvalidaException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    // Datos de partida: 3 oficinas y 2 dispositivos de cada tipo, sin consumos, alertas ni facturas.
    public void cargarDatosIniciales() {
        sistema.registrarOficina("O-101", "Bufete Aguilar");
        sistema.registrarOficina("O-102", "Tech Solutions GT");
        sistema.registrarOficina("O-103", "Clínica Dental Sonrisa");
        Oficina o1 = sistema.buscarOficina("O-101");
        Oficina o2 = sistema.buscarOficina("O-102");
        Oficina o3 = sistema.buscarOficina("O-103");

        sistema.registrarDispositivo(new Luminaria("L-001", o1, 60));
        sistema.registrarDispositivo(new Luminaria("L-002", o2, 40.5));
        sistema.registrarDispositivo(new Climatizador("C-001", o1, 1500));
        sistema.registrarDispositivo(new Climatizador("C-002", o3, 1200));
        sistema.registrarDispositivo(new ClimatizadorInverter("CI-001", o2, 1500));
        sistema.registrarDispositivo(new ClimatizadorInverter("CI-002", o3, 1800));
        sistema.registrarDispositivo(new SensorHumo("S-001", o1, 3));
        sistema.registrarDispositivo(new SensorHumo("S-002", o3, 3));
        sistema.registrarDispositivo(new CamaraSeguridad("CAM-001", o2, 12));
        sistema.registrarDispositivo(new CamaraSeguridad("CAM-002", o3, 15));

        // El inverter CI-001 queda con nivel 15 (menor que 20): se enciende, se ajusta y se apaga
        // (el nivel se conserva apagado). No hay consumo porque no avanzó ninguna hora.
        sistema.encender("CI-001");
        sistema.ajustarNivel("CI-001", 15);
        sistema.apagar("CI-001");
    }

    public void registrarOficina() {
        String codigo = vista.leerTexto("Código de la oficina: ");
        String inquilino = vista.leerTexto("Nombre del inquilino: ");
        sistema.registrarOficina(codigo, inquilino);
        vista.mostrarMensaje("Oficina registrada correctamente.");
    }

    public void registrarDispositivo() {
        List<String> tipos = new ArrayList<String>();
        tipos.add("1. Luminaria");
        tipos.add("2. Climatizador");
        tipos.add("3. Climatizador inverter");
        tipos.add("4. Sensor de humo");
        tipos.add("5. Cámara de seguridad");
        vista.mostrarMensajes(tipos);
        int tipo = vista.leerEntero("Tipo de dispositivo: ");
        if (tipo < 1 || tipo > 5) { // se valida antes de pedir más datos
            throw new OperacionInvalidaException("Tipo de dispositivo no válido.");
        }
        String codigo = vista.leerTexto("Código del dispositivo: ");
        String codigoOficina = vista.leerTexto("Código de la oficina: ");
        double potencia = vista.leerDecimal("Potencia nominal en watts: ");
        Oficina oficina = sistema.buscarOficina(codigoOficina);
        Dispositivo dispositivo;
        // Este switch solo decide QUÉ objeto crear; el resto del programa lo trata polimórficamente.
        switch (tipo) {
            case 1: dispositivo = new Luminaria(codigo, oficina, potencia); break;
            case 2: dispositivo = new Climatizador(codigo, oficina, potencia); break;
            case 3: dispositivo = new ClimatizadorInverter(codigo, oficina, potencia); break;
            case 4: dispositivo = new SensorHumo(codigo, oficina, potencia); break;
            default: dispositivo = new CamaraSeguridad(codigo, oficina, potencia); break;
        }
        sistema.registrarDispositivo(dispositivo);
        vista.mostrarMensaje("Dispositivo registrado: " + dispositivo);
    }

    public void encenderDispositivo() {
        String codigo = vista.leerTexto("Código del dispositivo: ");
        sistema.encender(codigo);
        vista.mostrarMensaje("Dispositivo encendido.");
    }

    public void apagarDispositivo() {
        String codigo = vista.leerTexto("Código del dispositivo: ");
        sistema.apagar(codigo);
        vista.mostrarMensaje("Dispositivo apagado.");
    }

    public void ajustarNivel() {
        String codigo = vista.leerTexto("Código del dispositivo: ");
        int nivel = vista.leerEntero("Nuevo nivel (%): ");
        sistema.ajustarNivel(codigo, nivel);
        vista.mostrarMensaje("Nivel ajustado a " + nivel + "%.");
    }

    public void registrarLectura() {
        String codigo = vista.leerTexto("Código del dispositivo: ");
        double valor = vista.leerDecimal("Valor de la lectura (°C o ppm): ");
        Alerta alerta = sistema.registrarLectura(codigo, valor);
        if (alerta == null) {
            vista.mostrarMensaje("Lectura registrada. No alcanzó ningún umbral.");
        } else {
            vista.mostrarMensaje("Lectura registrada. Se generó: " + alerta);
        }
    }

    public void registrarMovimiento() {
        String codigo = vista.leerTexto("Código de la cámara: ");
        Alerta alerta = sistema.registrarMovimiento(codigo);
        if (alerta == null) {
            vista.mostrarMensaje("Movimiento registrado dentro del horario laboral. Sin alerta.");
        } else {
            vista.mostrarMensaje("Movimiento registrado. Se generó: " + alerta);
        }
    }

    public void avanzarHora() {
        List<Factura> facturas = sistema.avanzarHora();
        vista.mostrarMensaje("Se avanzó una hora. Hora de simulación: " + sistema.getHoraSimulacion());
        if (!facturas.isEmpty()) {
            vista.mostrarMensaje("*** CIERRE AUTOMÁTICO DEL DÍA: facturas generadas ***");
            for (Factura factura : facturas) {
                vista.mostrarMensaje(factura.toString());
            }
        }
    }

    public void atenderAlerta() {
        Alerta alerta = sistema.atenderAlertaMasUrgente();
        vista.mostrarMensaje("Alerta atendida: " + alerta);
        if (alerta.getHorasRetraso() > 0) {
            vista.mostrarMensaje(String.format(Locale.US, "Se acreditó Q%.2f a la oficina %s por %d hora(s) de retraso.",
                    alerta.calcularCredito(), alerta.getDispositivo().getOficina().getCodigo(), alerta.getHorasRetraso()));
        } else {
            vista.mostrarMensaje("Se atendió dentro del plazo: sin crédito.");
        }
    }

    public void consultarOficinas() {
        List<Oficina> oficinas = sistema.getOficinas();
        if (oficinas.isEmpty()) {
            vista.mostrarMensaje("No hay oficinas registradas.");
        }
        for (Oficina oficina : oficinas) {
            vista.mostrarMensaje(oficina.toString());
        }
    }

    public void consultarDispositivos() {
        for (Oficina oficina : sistema.getOficinas()) {
            vista.mostrarMensaje("--- Oficina " + oficina.getCodigo() + " ---");
            for (Dispositivo dispositivo : oficina.getDispositivosOrdenados()) {
                vista.mostrarMensaje(dispositivo.toString());
            }
        }
    }

    public void consultarAlertas() {
        if (sistema.getBandeja().estaVacia()) {
            vista.mostrarMensaje("No hay alertas pendientes.");
            return;
        }
        vista.mostrarMensaje("Alertas pendientes (de la más urgente a la menos urgente):");
        for (Alerta alerta : sistema.getBandeja()) { // for-each gracias a Iterable
            vista.mostrarMensaje(alerta.toString());
        }
    }

    public void reporteDispositivosOficina() {
        String codigo = vista.leerTexto("Código de la oficina: ");
        List<Dispositivo> lista = sistema.getDispositivosDeOficina(codigo);
        vista.mostrarMensaje("Dispositivos de la oficina " + codigo + " (ordenados por código):");
        for (Dispositivo dispositivo : lista) {
            vista.mostrarMensaje(dispositivo.toString());
        }
    }

    public void reporteBandeja() {
        consultarAlertas();
        List<Alerta> conRetraso = sistema.getAlertasAtendidasConRetraso();
        vista.mostrarMensaje("Alertas atendidas con retraso:");
        if (conRetraso.isEmpty()) {
            vista.mostrarMensaje("   (ninguna)");
        }
        for (Alerta alerta : conRetraso) {
            vista.mostrarMensaje("   " + alerta);
        }
    }

    public void reporteFacturas() {
        List<Factura> facturas = sistema.getFacturas();
        if (facturas.isEmpty()) {
            vista.mostrarMensaje("Todavía no se ha generado ninguna factura.");
            return;
        }
        double totalCreditos = 0;
        for (Factura factura : facturas) {
            vista.mostrarMensaje(factura.toString());
            totalCreditos += factura.getCreditos();
        }
        vista.mostrarMensaje("--- Totales facturados por franja ---");
        for (ConsumoFranja total : sistema.calcularTotalFacturadoPorFranja()) {
            vista.mostrarMensaje("   " + total);
        }
        vista.mostrarMensaje(String.format(Locale.US, "Créditos aplicados en total: Q%.2f", totalCreditos));
    }
}