// Luminaria: regulable, nivel de 10 a 100 (inicia en 100). No mide ni genera alertas.
public class Luminaria extends DispositivoRegulable {

    // Constructor: llama al padre con el nivel inicial propio de la luminaria (100).
    public Luminaria(String codigo, Oficina oficina, double potenciaNominal) {
        super(codigo, oficina, potenciaNominal, 100);
    }

    // Consumo por hora: potencia nominal × nivel / 100, convertido de watts a kWh (÷ 1000).
    @Override
    public double calcularConsumoHora() {
        double watts = getPotenciaNominal() * getNivel() / 100.0;
        return watts / 1000.0;
    }

    // Nivel mínimo de la luminaria.
    @Override
    public int getNivelMinimo() {
        return 10;
    }

    @Override
    public String getTipo() {
        return "Luminaria";
    }
}