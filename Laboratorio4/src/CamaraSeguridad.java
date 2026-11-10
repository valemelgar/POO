// Cámara de seguridad: se enciende y apaga, no mide, pero registra movimientos detectados.
// Un movimiento fuera del horario laboral (antes de las 7:00 o desde las 19:00) genera alerta Alta.
public class CamaraSeguridad extends Dispositivo implements DetectorMovimiento {

    // Cantidad de movimientos aceptados (con o sin alerta).
    private int movimientosDetectados;

    // Constructor: inicia apagada (false) como todos los dispositivos excepto el sensor de humo.
    public CamaraSeguridad(String codigo, Oficina oficina, double potenciaNominal) {
        super(codigo, oficina, potenciaNominal, false);
        this.movimientosDetectados = 0;
    }

    // Consume su potencia nominal por cada hora encendida.
    @Override
    public double calcularConsumoHora() {
        return getPotenciaNominal() / 1000.0;
    }

    // Registra un movimiento. Solo se acepta si la cámara está encendida.
    @Override
    public Alerta registrarMovimiento(int horaSimulacion, SecuenciaAlertas secuencia) {
        verificarEncendido();
        movimientosDetectados++; // movimiento válido: se cuenta
        int horaDelDia = horaSimulacion % 24; // hora del día de 0 a 23
        if (horaDelDia < 7 || horaDelDia >= 19) {
            String mensaje = "Movimiento fuera del horario laboral detectado por la cámara " + getCodigo();
            return new Alerta(secuencia.siguiente(), this, horaSimulacion, new SeveridadAlta(), mensaje);
        }
        return null; // dentro del horario laboral no genera nada
    }

    public int getMovimientosDetectados() {
        return movimientosDetectados;
    }

    @Override
    public String getTipo() {
        return "Cámara de seguridad";
    }
}