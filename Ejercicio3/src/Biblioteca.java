import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Prestamo> prestamos;

    public Biblioteca() {
        prestamos = new ArrayList<Prestamo>();
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public boolean agregarPrestamo(Prestamo prestamo) {
        Prestamo encontrado;

        encontrado = buscarPrestamo(prestamo.getCodigo());

        if (encontrado == null) {
            prestamos.add(prestamo);
            return true;
        }

        return false;
    }

    public Prestamo buscarPrestamo(String codigo) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getCodigo().equals(codigo)) {
                return prestamo;
            }
        }

        return null;
    }

    public boolean modificarPrestamo(String codigo,
                                     String nuevoTitulo,
                                     int nuevosDias) {
        Prestamo prestamo = buscarPrestamo(codigo);

        if (prestamo != null) {
            prestamo.setTituloLibro(nuevoTitulo);
            prestamo.setDiasAutorizados(nuevosDias);
            return true;
        }

        return false;
    }

    public boolean eliminarPrestamo(String codigo) {
        Prestamo prestamo = buscarPrestamo(codigo);

        if (prestamo != null) {
            prestamos.remove(prestamo);
            return true;
        }

        return false;
    }

    public List<Prestamo> buscarPorEstudiante(String carne) {
        List<Prestamo> resultados =
                new ArrayList<Prestamo>();

        for (Prestamo prestamo : prestamos) {
            if (prestamo.getCarne().equals(carne)) {
                resultados.add(prestamo);
            }
        }

        return resultados;
    }

    public int calcularTotalDias() {
        int total = 0;

        for (Prestamo prestamo : prestamos) {
            total = total
                    + prestamo.getDiasAutorizados();
        }

        return total;
    }

    public Prestamo obtenerPrestamoMayorDuracion() {
        if (prestamos.size() == 0) {
            return null;
        }

        Prestamo mayor = prestamos.get(0);

        for (Prestamo prestamo : prestamos) {
            if (prestamo.getDiasAutorizados()
                    > mayor.getDiasAutorizados()) {
                mayor = prestamo;
            }
        }

        return mayor;
    }

    public int cantidadPrestamos() {
        return prestamos.size();
    }
}