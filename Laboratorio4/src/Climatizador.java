import java.util.Locale;

// Climatizador: regulable (nivel 20 a 100, inicia en 50) y medible (temperatura en °C).
// Hereda de DispositivoRegulable y además implementa Medible.
public class Climatizador extends DispositivoRegulable implements Medible {

    // Última temperatura registrada en °C.
    private double ultimaTemperatura;

    // Constructor: nivel inicial 50. El inverter reutiliza este mismo constructor.
    public Climatizador(String codigo, Oficina oficina, double potenciaNominal) {
        super(codigo, oficina, potenciaNominal, 50);
        this.ultimaTemperatura = 0;
    }

    // Consumo por hora: potencia × (0.30 + 0.70 × nivel / 100), convertido a kWh.
    @Override
    public double calcularConsumoHora() {
        double watts = getPotenciaNominal() * (0.30 + 0.70 * getNivel() / 100.0);
        return watts / 1000.0;
    }

    // Nivel mínimo del climatizador normal.
    @Override
    public int getNivelMinimo() {
        return 20;
    }

    // Unidad de la magnitud que mide.
    @Override
    public String getUnidad() {
        return "°C";
    }

    // Registra una temperatura. Orden de validación: primero estado, luego rango; solo si todo
    // es válido se guarda la lectura y se decide si hay alerta.
    @Override
    public Alerta registrarLectura(double valor, int horaSimulacion, SecuenciaAlertas secuencia) {
        verificarEncendido(); // solo se registran lecturas de dispositivos encendidos
        if (Double.isNaN(valor) || valor < -10 || valor > 60) {
            throw new OperacionInvalidaException("La temperatura debe estar entre -10 y 60 °C.");
        }
        ultimaTemperatura = valor; // lectura válida: se guarda

        // Umbrales: >=40 Crítica, >=32 Alta, >=28 Baja, menos de 28 no genera alerta.
        Severidad severidad;
        if (valor >= 40) {
            severidad = new SeveridadCritica();
        } else if (valor >= 32) {
            severidad = new SeveridadAlta();
        } else if (valor >= 28) {
            severidad = new SeveridadBaja();
        } else {
            return null; // no alcanzó ningún umbral
        }
        String mensaje = String.format(Locale.US, "Temperatura de %.1f °C en el climatizador %s", valor, getCodigo());
        // El código se pide a la secuencia SOLO ahora, que sí se crea la alerta.
        return new Alerta(secuencia.siguiente(), this, horaSimulacion, severidad, mensaje);
    }

    // Devuelve la última temperatura registrada.
    public double getUltimaTemperatura() {
        return ultimaTemperatura;
    }

    @Override
    public String getTipo() {
        return "Climatizador";
    }
}