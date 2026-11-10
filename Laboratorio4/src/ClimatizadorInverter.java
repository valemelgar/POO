// Climatizador inverter: ES un climatizador (misma lectura y alertas). Solo cambia el nivel
// mínimo (10) y el consumo (80 % del de un climatizador con los mismos datos).
public class ClimatizadorInverter extends Climatizador {

    // Constructor: reutiliza el del climatizador (nivel inicial 50).
    public ClimatizadorInverter(String codigo, Oficina oficina, double potenciaNominal) {
        super(codigo, oficina, potenciaNominal);
    }

    // Consumo: el 80 % de la fórmula del padre. super.calcularConsumoHora() evita copiar la fórmula.
    @Override
    public double calcularConsumoHora() {
        return super.calcularConsumoHora() * 0.80;
    }

    // El inverter admite niveles desde 10.
    @Override
    public int getNivelMinimo() {
        return 10;
    }

    @Override
    public String getTipo() {
        return "Climatizador inverter";
    }
}