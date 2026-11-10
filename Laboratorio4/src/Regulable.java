// Interfaz que representa la CAPACIDAD de regular la intensidad en porcentaje.
// La implementan los dispositivos regulables (luminaria, climatizador y su inverter).
// No es una clase porque la sensor de humo y la cámara NO la tienen.
public interface Regulable {

    // Devuelve el nivel de intensidad actual (en porcentaje).
    int getNivel();

    // Devuelve el nivel mínimo permitido para este tipo de dispositivo.
    int getNivelMinimo();

    // Devuelve el nivel máximo permitido para este tipo de dispositivo.
    int getNivelMaximo();

    // Cambia el nivel; debe rechazar niveles fuera de rango o dispositivos apagados.
    void ajustarNivel(int nivel);
}