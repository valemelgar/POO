public class MaquinaAlgodon extends Maquina {
    private int potenciaVatios;

    public MaquinaAlgodon(String codigoInventario, String marca, String modelo,
            double tarifaDiaria, int potenciaVatios) {
        super(codigoInventario, marca, modelo, tarifaDiaria);
        this.potenciaVatios = potenciaVatios;
    }

    public int getPotenciaVatios() { return potenciaVatios; }

    public boolean datosValidosAlgodon() {
        return datosComunesValidos() && potenciaVatios > 0;
    }

    public double calcularCostoAlgodon(int dias) {
        if (!datosValidosAlgodon()) { return -1; }
        double base = calcularCostoBase(dias);
        if (base < 0) { return -1; }
        double total = base + (potenciaVatios > 1000 ? 60.0 : 0);
        return total <= Double.MAX_VALUE && total > 0 ? total : -1;
    }

    public String describirAlgodon() {
        return describirDatosComunes() + " | Algodon | " + "Potencia: " + potenciaVatios + " W";
    }
}
