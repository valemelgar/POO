import java.util.ArrayList;
import java.util.List;

// Fachada del sistema: guarda en memoria los trabajos, nodos y lotes, verifica que los códigos sean únicos
// y coordina las operaciones. El menú solo habla con esta clase.
public class GranjaRenderGT {

    // Las listas mantienen el orden en que se registraron los elementos.
    private List<Trabajo> trabajos;
    private List<Nodo> nodos;
    private List<Lote> lotes;
    private RegistroIngresos ingresos;
    private int secuenciaLotes;

    public GranjaRenderGT() {
        this.trabajos = new ArrayList<>();
        this.nodos = new ArrayList<>();
        this.lotes = new ArrayList<>();
        this.ingresos = new RegistroIngresos();
        this.secuenciaLotes = 0;
    }

    public void registrarTrabajo(Trabajo trabajo) {
        cargarTrabajoInicial(trabajo);
        ingresos.registrarPorRegistro(trabajo.getCategoria(), trabajo.calcularCosto());
    }

    public void cargarTrabajoInicial(Trabajo trabajo) {
        if (trabajo == null) {
            throw new ReglaNegocioException("Debe indicar un trabajo.");
        }
        for (Trabajo registrado : trabajos) {
            if (registrado.getCodigo().equals(trabajo.getCodigo())) {
                throw new ReglaNegocioException("Ya existe un trabajo con el código '" + trabajo.getCodigo() + "'.");
            }
        }
        trabajos.add(trabajo);
    }

    public void registrarNodo(Nodo nodo) {
        if (nodo == null) {
            throw new ReglaNegocioException("Debe indicar un nodo.");
        }
        for (Nodo registrado : nodos) {
            if (registrado.getCodigo().equals(nodo.getCodigo())) {
                throw new ReglaNegocioException("Ya existe un nodo con el código '" + nodo.getCodigo() + "'.");
            }
        }
        nodos.add(nodo);
    }

    public Lote crearLote(String codigoNodo) {
        Nodo nodo = buscarNodo(codigoNodo);
        if (nodoTieneLoteAbierto(nodo)) {
            throw new ReglaNegocioException("El nodo '" + nodo.getCodigo() + "' ya tiene un lote sin cerrar.");
        }
        secuenciaLotes++;
        String codigoLote = String.format("LOTE-%03d", secuenciaLotes);
        Lote lote = new Lote(codigoLote, nodo);
        lotes.add(lote);
        return lote;
    }

    public void asignarTrabajo(String codigoLote, String codigoTrabajo) {
        Lote lote = buscarLote(codigoLote);
        Trabajo trabajo = buscarTrabajo(codigoTrabajo);
        lote.asignarTrabajo(trabajo);
    }

    public void retirarTrabajo(String codigoLote, String codigoTrabajo) {
        Lote lote = buscarLote(codigoLote);
        Trabajo trabajo = buscarTrabajo(codigoTrabajo);
        lote.retirarTrabajo(trabajo);
    }

    public void iniciarLote(String codigoLote) {
        Lote lote = buscarLote(codigoLote);
        lote.iniciar(ingresos);
    }

    public void registrarResultado(String codigoLote, String codigoTrabajo, ResultadoEjecucion resultado) {
        Lote lote = buscarLote(codigoLote);
        Trabajo trabajo = buscarTrabajo(codigoTrabajo);
        lote.registrarResultado(trabajo, resultado);
    }

    public List<Trabajo> listarTrabajos() {
        return new ArrayList<>(trabajos);
    }

    public List<Nodo> listarNodos() {
        return new ArrayList<>(nodos);
    }

    public List<Lote> listarLotes() {
        return new ArrayList<>(lotes);
    }

    public List<Lote> listarLotesNoCerrados() {
        List<Lote> abiertos = new ArrayList<>();
        for (Lote lote : lotes) {
            if (!lote.estaCerrado()) {
                abiertos.add(lote);
            }
        }
        return abiertos;
    }

    // Una fila por cada combinación de categoría y estado, ordenada alfabéticamente.
    public List<ConteoCategoriaEstado> contarPorCategoriaYEstado() {
        List<ConteoCategoriaEstado> conteo = new ArrayList<>();
        for (Trabajo trabajo : trabajos) {
            String categoria = trabajo.getCategoria();
            String estado = trabajo.getEstado().getNombre();
            ConteoCategoriaEstado encontrado = null;
            for (ConteoCategoriaEstado fila : conteo) {
                if (fila.categoria.equals(categoria) && fila.estado.equals(estado)) {
                    encontrado = fila;
                    break;
                }
            }
            if (encontrado == null) {
                conteo.add(new ConteoCategoriaEstado(categoria, estado));
            } else {
                encontrado.cantidad++;
            }
        }
        conteo.sort((a, b) -> {
            int ordenCategoria = a.categoria.compareTo(b.categoria);
            return ordenCategoria != 0 ? ordenCategoria : a.estado.compareTo(b.estado);
        });
        return conteo;
    }

    public RegistroIngresos getIngresos() {
        return ingresos.copia();
    }

    private Trabajo buscarTrabajo(String codigo) {
        String buscado = limpiar(codigo);
        for (Trabajo trabajo : trabajos) {
            if (trabajo.getCodigo().equals(buscado)) {
                return trabajo;
            }
        }
        throw new ReglaNegocioException("No existe un trabajo con el código '" + codigo + "'.");
    }

    private Nodo buscarNodo(String codigo) {
        String buscado = limpiar(codigo);
        for (Nodo nodo : nodos) {
            if (nodo.getCodigo().equals(buscado)) {
                return nodo;
            }
        }
        throw new ReglaNegocioException("No existe un nodo con el código '" + codigo + "'.");
    }

    private Lote buscarLote(String codigo) {
        String buscado = limpiar(codigo);
        for (Lote lote : lotes) {
            if (lote.getCodigo().equals(buscado)) {
                return lote;
            }
        }
        throw new ReglaNegocioException("No existe un lote con el código '" + codigo + "'.");
    }

    private boolean nodoTieneLoteAbierto(Nodo nodo) {
        for (Lote lote : lotes) {
            if (lote.getNodo() == nodo && !lote.estaCerrado()) {
                return true;
            }
        }
        return false;
    }

    private String limpiar(String codigo) {
        return (codigo == null) ? "" : codigo.trim();
    }

    // Datos de una fila del reporte. Quien reciba la lista puede leerlos, pero no alterar el conteo.
    public static class ConteoCategoriaEstado {
        private final String categoria;
        private final String estado;
        private int cantidad;

        private ConteoCategoriaEstado(String categoria, String estado) {
            this.categoria = categoria;
            this.estado = estado;
            this.cantidad = 1;
        }

        public String getCategoria() {
            return categoria;
        }

        public String getEstado() {
            return estado;
        }

        public int getCantidad() {
            return cantidad;
        }
    }
}
