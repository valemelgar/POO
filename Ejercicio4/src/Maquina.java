public class Maquina {
    private String codigoInventario;
    private String marca;
    private String modelo;
    private double tarifaDiaria;
    private boolean disponible;

    protected Maquina(String codigoInventario, String marca, String modelo, double tarifaDiaria) {
        this.codigoInventario = codigoInventario == null ? "" : codigoInventario;
        this.marca = marca == null ? "" : marca;
        this.modelo = modelo == null ? "" : modelo;
        this.tarifaDiaria = tarifaDiaria;
        disponible = true;
    }

    public String getCodigoInventario() { return codigoInventario; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public double getTarifaDiaria() { return tarifaDiaria; }
    public boolean estaDisponible() { return disponible; }

    public boolean datosComunesValidos() {
        return codigoInventario.matches("\\S+") && marca.matches(".*\\S.*")
            && modelo.matches(".*\\S.*")
            && tarifaDiaria <= Double.MAX_VALUE && tarifaDiaria > 0;
    }

    protected double calcularCostoBase(int dias) {
        if (dias <= 0) { return -1; }
        double total = tarifaDiaria * dias;
        return total <= Double.MAX_VALUE && total > 0 ? total : -1;
    }

    public String describirDatosComunes() {
        return String.format(
            "Codigo: %s | Marca: %s | Modelo: %s | Tarifa diaria: Q%.2f | Estado: %s",
            codigoInventario, marca, modelo, tarifaDiaria,
            disponible ? "Disponible" : "Alquilada");
    }

    boolean alquilar() {
        if (!disponible) { return false; }
        disponible = false;
        return true;
    }

    boolean devolver() {
        if (disponible) { return false; }
        disponible = true;
        return true;
    }
}
