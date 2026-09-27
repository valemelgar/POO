public class FuenteChocolate extends Maquina {
    private double capacidadKg;

    public FuenteChocolate(String codigoInventario, String marca, String modelo,
            double tarifaDiaria, double capacidadKg) {
        super(codigoInventario, marca, modelo, tarifaDiaria);
        this.capacidadKg = capacidadKg;
    }

    public double getCapacidadKg() { return capacidadKg; }

    public boolean datosValidosChocolate() {
        return datosComunesValidos() && capacidadKg <= Double.MAX_VALUE && capacidadKg > 0;
    }

    public double calcularCostoChocolate(int dias) {
        if (!datosValidosChocolate()) { return -1; }
        double base = calcularCostoBase(dias);
        if (base < 0) { return -1; }
        double total = base + 20.0 * capacidadKg * dias;
        return total <= Double.MAX_VALUE && total > 0 ? total : -1;
    }

    public String describirChocolate() {
        return describirDatosComunes() + " | Chocolate | " + "Capacidad maxima: " + capacidadKg + " kg";
    }
}
