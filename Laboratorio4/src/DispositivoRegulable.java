// Clase abstracta para los dispositivos que se regulan por nivel (luminaria y climatizadores).
// Guarda el nivel UNA sola vez (evita duplicarlo en cada tipo) y valida los ajustes.
// Implementa la interfaz Regulable.
public abstract class DispositivoRegulable extends Dispositivo implements Regulable {

    // Nivel de intensidad actual en porcentaje. Se conserva mientras el dispositivo está apagado.
    private int nivel;

    // Constructor protegido: los dispositivos regulables inician apagados y con un nivel inicial
    // que define cada tipo (luminaria 100, climatizador 50).
    protected DispositivoRegulable(String codigo, Oficina oficina, double potenciaNominal, int nivelInicial) {
        super(codigo, oficina, potenciaNominal, false); // inicializa lo heredado: apagado
        this.nivel = nivelInicial;
    }

    // ABSTRACTO: cada tipo tiene su propio nivel mínimo (10, 20 o 10).
    @Override
    public abstract int getNivelMinimo();

    // El nivel máximo es 100 para todos los tipos, por eso se implementa aquí una sola vez.
    @Override
    public int getNivelMaximo() {
        return 100;
    }

    // Devuelve el nivel actual.
    @Override
    public int getNivel() {
        return nivel;
    }

    // Cambia el nivel. Primero valida; si algo falla se lanza excepción ANTES de cambiar nada.
    @Override
    public void ajustarNivel(int nivel) {
        verificarEncendido(); // solo se ajusta si está encendido
        if (nivel < getNivelMinimo() || nivel > getNivelMaximo()) {
            throw new OperacionInvalidaException("El nivel debe estar entre " + getNivelMinimo()
                    + " y " + getNivelMaximo() + " para " + getTipo() + ".");
        }
        this.nivel = nivel;
    }
}