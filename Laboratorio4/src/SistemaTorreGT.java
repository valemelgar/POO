import java.util.ArrayList;
import java.util.List;

// Clase del MODELO que coordina todo: guarda las oficinas, la bandeja, la hora de simulación y
// ejecuta las operaciones. NO imprime ni lee nada (eso es de la vista y del controlador).
public class SistemaTorreGT {

    // Horas simuladas transcurridas desde el inicio (empieza en 0).
    private int horaSimulacion;

    // Oficinas registradas.
    private ArrayList<Oficina> oficinas;

    // Alertas pendientes.
    private BandejaAlertas bandeja;

    // Alertas ya atendidas (historial).
    private ArrayList<Alerta> alertasAtendidas;

    // Catálogo de franjas tarifarias.
    private TarifaHoraria tarifa;

    // Contador de códigos de alerta.
    private SecuenciaAlertas secuencia;

    // Constructor: hora 0, listas vacías y las tres franjas de la tarifa.
    public SistemaTorreGT() {
        this.horaSimulacion = 0;
        this.oficinas = new ArrayList<Oficina>();
        this.bandeja = new BandejaAlertas();
        this.alertasAtendidas = new ArrayList<Alerta>();
        this.secuencia = new SecuenciaAlertas();
        this.tarifa = new TarifaHoraria();
        this.tarifa.agregarFranja(new FranjaValle());
        this.tarifa.agregarFranja(new FranjaNormal());
        this.tarifa.agregarFranja(new FranjaPunta());
    }

    public int getHoraSimulacion() {
        return horaSimulacion;
    }

    // Hora del día: residuo de dividir la hora de simulación entre 24 (0 a 23).
    public int getHoraDelDia() {
        return horaSimulacion % 24;
    }

    // Registra una oficina nueva. Valida el código ANTES de crearla.
    public void registrarOficina(String codigo, String inquilino) {
        validarCodigoNuevo(codigo);
        oficinas.add(new Oficina(codigo, inquilino, tarifa));
    }

    // Registra un dispositivo ya creado en su oficina.
    public void registrarDispositivo(Dispositivo dispositivo) {
        validarCodigoNuevo(dispositivo.getCodigo());
        if (!oficinas.contains(dispositivo.getOficina())) {
            throw new OperacionInvalidaException("La oficina del dispositivo no está registrada.");
        }
        dispositivo.getOficina().agregarDispositivo(dispositivo);
    }

    // Busca una oficina por código; si no existe rechaza la operación.
    public Oficina buscarOficina(String codigo) {
        String buscado = (codigo == null) ? "" : codigo.trim();
        for (Oficina oficina : oficinas) {
            if (oficina.getCodigo().equals(buscado)) {
                return oficina;
            }
        }
        throw new OperacionInvalidaException("No existe la oficina '" + buscado + "'.");
    }

    // Busca un dispositivo por código en todas las oficinas.
    public Dispositivo buscarDispositivo(String codigo) {
        String buscado = (codigo == null) ? "" : codigo.trim();
        for (Oficina oficina : oficinas) {
            for (Dispositivo dispositivo : oficina.getDispositivosOrdenados()) {
                if (dispositivo.getCodigo().equals(buscado)) {
                    return dispositivo;
                }
            }
        }
        throw new OperacionInvalidaException("No existe el dispositivo '" + buscado + "'.");
    }

    // Los tres comandos delegan en el dispositivo; cada uno valida sus propias reglas
    // (encendido, rango, sensor de humo...). Aquí no hay if por tipo.
    public void encender(String codigoDispositivo) {
        buscarDispositivo(codigoDispositivo).encender();
    }

    public void apagar(String codigoDispositivo) {
        buscarDispositivo(codigoDispositivo).apagar();
    }

    public void ajustarNivel(String codigoDispositivo, int nivel) {
        buscarDispositivo(codigoDispositivo).ajustarNivel(nivel);
    }

    // Registra una lectura; si genera alerta, la agrega a la bandeja y la devuelve (si no, null).
    public Alerta registrarLectura(String codigoDispositivo, double valor) {
        Dispositivo dispositivo = buscarDispositivo(codigoDispositivo);
        Alerta alerta = dispositivo.registrarLectura(valor, horaSimulacion, secuencia);
        if (alerta != null) {
            bandeja.agregar(alerta);
        }
        return alerta;
    }

    // Registra un movimiento; mismo flujo que la lectura.
    public Alerta registrarMovimiento(String codigoDispositivo) {
        Dispositivo dispositivo = buscarDispositivo(codigoDispositivo);
        Alerta alerta = dispositivo.registrarMovimiento(horaSimulacion, secuencia);
        if (alerta != null) {
            bandeja.agregar(alerta);
        }
        return alerta;
    }

    // Avanza UNA hora: consume con el estado actual en la franja de la hora que termina, suma la
    // hora y, si se pasa de la hora 23 a la 0, cierra el día. Devuelve las facturas generadas.
    public List<Factura> avanzarHora() {
        FranjaTarifaria franja = tarifa.obtenerFranja(getHoraDelDia());
        for (Oficina oficina : oficinas) {
            oficina.consumirHora(franja);
        }
        horaSimulacion++;
        ArrayList<Factura> generadas = new ArrayList<Factura>();
        if (getHoraDelDia() == 0) {
            int dia = horaSimulacion / 24; // día que acaba de cerrarse (1, 2, 3...)
            for (Oficina oficina : oficinas) {
                generadas.add(oficina.cerrarDia(dia));
            }
        }
        return generadas;
    }

    // Atiende la alerta más urgente y acredita el retraso a la oficina del dispositivo.
    public Alerta atenderAlertaMasUrgente() {
        Alerta alerta = bandeja.extraerMasUrgente(); // rechaza si la bandeja está vacía
        alerta.atender(horaSimulacion);
        double credito = alerta.calcularCredito();
        if (credito > 0) {
            alerta.getDispositivo().getOficina().acreditar(credito);
        }
        alertasAtendidas.add(alerta);
        return alerta;
    }

    // Copia de la lista de oficinas.
    public List<Oficina> getOficinas() {
        return new ArrayList<Oficina>(oficinas);
    }

    public BandejaAlertas getBandeja() {
        return bandeja;
    }

    // Dispositivos de una oficina ordenados por código.
    public List<Dispositivo> getDispositivosDeOficina(String codigoOficina) {
        return buscarOficina(codigoOficina).getDispositivosOrdenados();
    }

    // Alertas atendidas con retraso mayor que cero.
    public List<Alerta> getAlertasAtendidasConRetraso() {
        ArrayList<Alerta> conRetraso = new ArrayList<Alerta>();
        for (Alerta alerta : alertasAtendidas) {
            if (alerta.getHorasRetraso() > 0) {
                conRetraso.add(alerta);
            }
        }
        return conRetraso;
    }

    // Todas las facturas generadas, agrupadas por oficina.
    public List<Factura> getFacturas() {
        ArrayList<Factura> todas = new ArrayList<Factura>();
        for (Oficina oficina : oficinas) {
            todas.addAll(oficina.getFacturas());
        }
        return todas;
    }

    // Suma, por franja, los kWh y montos de todas las facturas generadas.
    public List<ConsumoFranja> calcularTotalFacturadoPorFranja() {
        ArrayList<ConsumoFranja> totales = new ArrayList<ConsumoFranja>();
        for (FranjaTarifaria franja : tarifa.getFranjas()) {
            ConsumoFranja total = new ConsumoFranja(franja);
            for (Factura factura : getFacturas()) {
                for (ConsumoFranja consumo : factura.getConsumos()) {
                    if (consumo.getFranja() == franja) {
                        total.agregarKwh(consumo.getKwh());
                    }
                }
            }
            totales.add(total);
        }
        return totales;
    }

    // Rechaza códigos vacíos o repetidos (entre oficinas y dispositivos).
    private void validarCodigoNuevo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new OperacionInvalidaException("El código no puede estar vacío.");
        }
        String nuevo = codigo.trim();
        for (Oficina oficina : oficinas) {
            if (oficina.getCodigo().equals(nuevo)) {
                throw new OperacionInvalidaException("El código '" + nuevo + "' ya existe.");
            }
            for (Dispositivo dispositivo : oficina.getDispositivosOrdenados()) {
                if (dispositivo.getCodigo().equals(nuevo)) {
                    throw new OperacionInvalidaException("El código '" + nuevo + "' ya existe.");
                }
            }
        }
    }
}