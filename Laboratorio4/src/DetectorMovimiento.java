// Interfaz que representa la CAPACIDAD de registrar movimientos detectados.
// Hoy solo la implementa la cámara de seguridad, pero otro dispositivo podría implementarla.
public interface DetectorMovimiento {

    // Registra un movimiento detectado. Devuelve la Alerta generada o null si no corresponde alerta.
    Alerta registrarMovimiento(int horaSimulacion, SecuenciaAlertas secuencia);
}