import java.util.Map;
import java.util.TreeMap;

// Lleva la contabilidad de la granja: lo cobrado por registro de trabajos, lo cobrado por reintentos
// y el total acumulado por categoría. Solo se modifica con los dos métodos "registrar...", que rechazan
// montos inválidos; las cotizaciones, los retiros y las operaciones rechazadas nunca llegan aquí.
public class RegistroIngresos {

    // Atributos privados.
    private double ingresosPorRegistro;                      // suma de lo cobrado al registrar trabajos
    private double ingresosPorReintentos;                    // suma de los cargos de Q25 por reintento
    private Map<String, Double> ingresosPorCategoria;        // acumulado por nombre de categoría (ordenado alfabéticamente)

    // Constructor público: empieza con todo en cero.
    public RegistroIngresos() {
        this.ingresosPorRegistro = 0.0;
        this.ingresosPorReintentos = 0.0;
        // TreeMap mantiene las categorías ordenadas por nombre.
        this.ingresosPorCategoria = new TreeMap<>();
    }

    // Constructor privado: crea una COPIA de otro registro. Lo usa copia() para que quien consulte
    // los ingresos no pueda modificar el registro original.
    private RegistroIngresos(RegistroIngresos origen) {
        this.ingresosPorRegistro = origen.ingresosPorRegistro;
        this.ingresosPorReintentos = origen.ingresosPorReintentos;
        // Se copia el mapa (new TreeMap<>(otroMapa)) para que no compartan la misma colección.
        this.ingresosPorCategoria = new TreeMap<>(origen.ingresosPorCategoria);
    }

    // Registra el cobro completo de un trabajo recién registrado. Suma al ingreso por registro y a su categoría.
    public void registrarPorRegistro(String categoria, double monto) {
        // Se valida antes de modificar nada, así un dato inválido no altera los ingresos.
        validar(categoria, monto);
        this.ingresosPorRegistro += monto;
        sumarACategoria(categoria, monto);
    }

    // Registra un cargo por reintento. Suma al ingreso por reintentos y a su categoría.
    public void registrarPorReintento(String categoria, double monto) {
        validar(categoria, monto);
        this.ingresosPorReintentos += monto;
        sumarACategoria(categoria, monto);
    }

    // Devuelve lo cobrado por registro.
    public double getIngresoPorRegistro() {
        return ingresosPorRegistro;
    }

    // Devuelve lo cobrado por reintentos.
    public double getIngresoPorReintentos() {
        return ingresosPorReintentos;
    }

    // Devuelve el ingreso total (registro + reintentos).
    public double getIngresoTotal() {
        return ingresosPorRegistro + ingresosPorReintentos;
    }

    // Devuelve una copia del acumulado por categoría (para que nadie modifique el original).
    public Map<String, Double> getIngresoPorCategoria() {
        return new TreeMap<>(ingresosPorCategoria);
    }

    // Devuelve una copia independiente de todo el registro, usando el constructor privado.
    public RegistroIngresos copia() {
        return new RegistroIngresos(this);
    }

    // Método auxiliar privado: rechaza categorías vacías y montos que no sean positivos y finitos.
    private void validar(String categoria, double monto) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new ReglaNegocioException("La categoría del ingreso no puede estar vacía.");
        }
        if (Double.isNaN(monto) || Double.isInfinite(monto) || monto <= 0) {
            throw new ReglaNegocioException("El monto de un ingreso debe ser mayor que cero.");
        }
    }

    // Método auxiliar privado: suma un monto al acumulado de una categoría.
    private void sumarACategoria(String categoria, double monto) {
        // Si la categoría todavía no tiene acumulado, get devuelve null y se parte de cero.
        Double actual = ingresosPorCategoria.get(categoria);
        double base = (actual == null) ? 0.0 : actual;
        ingresosPorCategoria.put(categoria, base + monto);
    }
}