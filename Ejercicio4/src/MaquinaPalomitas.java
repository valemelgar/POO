    public class MaquinaPalomitas extends Maquina {
        private int porcionesPorHora;
        private boolean tieneCarrito;

        public MaquinaPalomitas(String codigoInventario, String marca, String modelo,
                double tarifaDiaria, int porcionesPorHora, boolean tieneCarrito) {
            super(codigoInventario, marca, modelo, tarifaDiaria);
            this.porcionesPorHora = porcionesPorHora;
            this.tieneCarrito = tieneCarrito;
        }

        public int getPorcionesPorHora() { return porcionesPorHora; }
        public boolean tieneCarrito() { return tieneCarrito; }

        public boolean datosValidosPalomitas() {
            return datosComunesValidos() && porcionesPorHora > 0;
        }

        public double calcularCostoPalomitas(int dias) {
            if (!datosValidosPalomitas()) { return -1; }
            double base = calcularCostoBase(dias);
            if (base < 0) { return -1; }
            double total = base + (tieneCarrito ? 40.0 * dias : 0);
            return total <= Double.MAX_VALUE && total > 0 ? total : -1;
        }

        public String describirPalomitas() {
            return describirDatosComunes() + " | Palomitas | " + "Porciones/hora: " + porcionesPorHora + " | Carrito: " + (tieneCarrito ? "Si" : "No");
        }
    }
