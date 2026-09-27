import java.util.ArrayList;

public class GestorAlquileres {
    private ArrayList<MaquinaPalomitas> palomitas;
    private ArrayList<MaquinaAlgodon> algodon;
    private ArrayList<FuenteChocolate> chocolate;
    private double ingresosAcumulados;

    public GestorAlquileres() {
        palomitas = new ArrayList<MaquinaPalomitas>();
        algodon = new ArrayList<MaquinaAlgodon>();
        chocolate = new ArrayList<FuenteChocolate>();
        ingresosAcumulados = 0;
    }

    private boolean mismoCodigo(String guardado, String buscado) {
        return buscado != null && guardado.equalsIgnoreCase(buscado);
    }

    private MaquinaPalomitas buscarPalomitas(String codigo) {
        for (MaquinaPalomitas maquina : palomitas) {
            if (mismoCodigo(maquina.getCodigoInventario(), codigo)) { return maquina; }
        }
        return null;
    }

    public boolean registrarPalomitas(MaquinaPalomitas maquina) {
        if (maquina == null || !maquina.datosValidosPalomitas()
                || !maquina.estaDisponible() || existeCodigo(maquina.getCodigoInventario())) {
            return false;
        }
        palomitas.add(maquina);
        return true;
    }

    private MaquinaAlgodon buscarAlgodon(String codigo) {
        for (MaquinaAlgodon maquina : algodon) {
            if (mismoCodigo(maquina.getCodigoInventario(), codigo)) { return maquina; }
        }
        return null;
    }

    public boolean registrarAlgodon(MaquinaAlgodon maquina) {
        if (maquina == null || !maquina.datosValidosAlgodon()
                || !maquina.estaDisponible() || existeCodigo(maquina.getCodigoInventario())) {
            return false;
        }
        algodon.add(maquina);
        return true;
    }

    private FuenteChocolate buscarChocolate(String codigo) {
        for (FuenteChocolate maquina : chocolate) {
            if (mismoCodigo(maquina.getCodigoInventario(), codigo)) { return maquina; }
        }
        return null;
    }

    public boolean registrarChocolate(FuenteChocolate maquina) {
        if (maquina == null || !maquina.datosValidosChocolate()
                || !maquina.estaDisponible() || existeCodigo(maquina.getCodigoInventario())) {
            return false;
        }
        chocolate.add(maquina);
        return true;
    }

    public boolean existeCodigo(String codigo) {
        return buscarPalomitas(codigo) != null || buscarAlgodon(codigo) != null
            || buscarChocolate(codigo) != null;
    }

    public boolean estaDisponible(String codigo) {
        MaquinaPalomitas palomitas = buscarPalomitas(codigo);
        if (palomitas != null) { return palomitas.estaDisponible(); }
        MaquinaAlgodon algodon = buscarAlgodon(codigo);
        if (algodon != null) { return algodon.estaDisponible(); }
        FuenteChocolate chocolate = buscarChocolate(codigo);
        if (chocolate != null) { return chocolate.estaDisponible(); }
        return false;
    }

    public String consultarMaquina(String codigo) {
        MaquinaPalomitas palomitas = buscarPalomitas(codigo);
        if (palomitas != null) { return palomitas.describirPalomitas(); }
        MaquinaAlgodon algodon = buscarAlgodon(codigo);
        if (algodon != null) { return algodon.describirAlgodon(); }
        FuenteChocolate chocolate = buscarChocolate(codigo);
        if (chocolate != null) { return chocolate.describirChocolate(); }
        return "No existe una maquina con ese codigo.";
    }

    public String consultarInventario() {
        String resultado = "INVENTARIO\n";
        for (MaquinaPalomitas m : palomitas) { resultado += m.describirPalomitas() + "\n"; }
        for (MaquinaAlgodon m : algodon) { resultado += m.describirAlgodon() + "\n"; }
        for (FuenteChocolate m : chocolate) { resultado += m.describirChocolate() + "\n"; }
        return resultado;
    }

    public double cotizar(String codigo, int dias) {
        if (dias <= 0) { return -1; }
        MaquinaPalomitas palomitas = buscarPalomitas(codigo);
        if (palomitas != null) { return palomitas.calcularCostoPalomitas(dias); }
        MaquinaAlgodon algodon = buscarAlgodon(codigo);
        if (algodon != null) { return algodon.calcularCostoAlgodon(dias); }
        FuenteChocolate chocolate = buscarChocolate(codigo);
        if (chocolate != null) { return chocolate.calcularCostoChocolate(dias); }
        return -1;
    }

    public boolean confirmarAlquiler(String codigo, int dias) {
        double total = cotizar(codigo, dias);
        if (total <= 0 || !estaDisponible(codigo)
                || ingresosAcumulados + total > Double.MAX_VALUE) { return false; }
        boolean realizado = false;
        MaquinaPalomitas palomitas = buscarPalomitas(codigo);
        if (palomitas != null) { realizado = palomitas.alquilar(); }
        MaquinaAlgodon algodon = buscarAlgodon(codigo);
        if (algodon != null) { realizado = algodon.alquilar(); }
        FuenteChocolate chocolate = buscarChocolate(codigo);
        if (chocolate != null) { realizado = chocolate.alquilar(); }
        if (realizado) { ingresosAcumulados += total; }
        return realizado;
    }

    public boolean registrarDevolucion(String codigo) {
        MaquinaPalomitas palomitas = buscarPalomitas(codigo);
        if (palomitas != null) { return palomitas.devolver(); }
        MaquinaAlgodon algodon = buscarAlgodon(codigo);
        if (algodon != null) { return algodon.devolver(); }
        FuenteChocolate chocolate = buscarChocolate(codigo);
        if (chocolate != null) { return chocolate.devolver(); }
        return false;
    }

    public double getIngresosAcumulados() { return ingresosAcumulados; }

    private String filaReporte(String categoria, int total, int disponibles) {
        return categoria + ": total=" + total + ", disponibles=" + disponibles
            + ", alquiladas=" + (total - disponibles) + "\n";
    }

    public String generarReporte() {
        int disponiblesPalomitas = 0;
        int disponiblesAlgodon = 0;
        int disponiblesChocolate = 0;
        for (MaquinaPalomitas m : palomitas) {
            if (m.estaDisponible()) { disponiblesPalomitas++; }
        }
        for (MaquinaAlgodon m : algodon) {
            if (m.estaDisponible()) { disponiblesAlgodon++; }
        }
        for (FuenteChocolate m : chocolate) {
            if (m.estaDisponible()) { disponiblesChocolate++; }
        }
        int total = palomitas.size() + algodon.size() + chocolate.size();
        int disponibles = disponiblesPalomitas + disponiblesAlgodon + disponiblesChocolate;
        return "REPORTE GENERAL\n"
            + filaReporte("Palomitas", palomitas.size(), disponiblesPalomitas)
            + filaReporte("Algodon", algodon.size(), disponiblesAlgodon)
            + filaReporte("Chocolate", chocolate.size(), disponiblesChocolate)
            + filaReporte("General", total, disponibles)
            + String.format("Ingresos acumulados: Q%.2f", ingresosAcumulados);
    }
}
