import java.util.Locale;

// Sensor de humo: siempre encendido, no se regula, mide ppm (0 a 1000) y genera alertas.
public class SensorHumo extends Dispositivo implements Medible {

    // Última concentración de humo registrada en ppm.
    private double ultimaConcentracion;

    // Constructor: pasa true al padre porque el sensor SIEMPRE está encendido.
    public SensorHumo(String codigo, Oficina oficina, double potenciaNominal) {
        super(codigo, oficina, potenciaNominal, true);
        this.ultimaConcentracion = 0;
    }

    // Consume su potencia nominal cada hora (watts ÷ 1000 = kWh).
    @Override
    public double calcularConsumoHora() {
        return getPotenciaNominal() / 1000.0;
    }

    // Sobrescribe apagar(): el sensor nunca se apaga, así que siempre se rechaza.
    @Override
    public void apagar() {
        throw new OperacionInvalidaException("El sensor de humo " + getCodigo() + " no se puede apagar.");
    }

    @Override
    public String getUnidad() {
        return "ppm";
    }

    // Registra una concentración. Rango válido 0 a 1000. Umbrales: >=300 Crítica, >=150 Alta.
    @Override
    public Alerta registrarLectura(double valor, int horaSimulacion, SecuenciaAlertas secuencia) {
        verificarEncendido();
        if (Double.isNaN(valor) || valor < 0 || valor > 1000) {
            throw new OperacionInvalidaException("La concentración de humo debe estar entre 0 y 1000 ppm.");
        }
        ultimaConcentracion = valor;

        Severidad severidad;
        if (valor >= 300) {
            severidad = new SeveridadCritica();
        } else if (valor >= 150) {
            severidad = new SeveridadAlta();
        } else {
            return null; // menos de 150 ppm no genera alerta
        }
        String mensaje = String.format(Locale.US, "Humo de %.1f ppm detectado por el sensor %s", valor, getCodigo());
        return new Alerta(secuencia.siguiente(), this, horaSimulacion, severidad, mensaje);
    }

    public double getUltimaConcentracion() {
        return ultimaConcentracion;
    }

    @Override
    public String getTipo() {
        return "Sensor de humo";
    }
}