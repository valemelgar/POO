import java.util.ArrayList;
import java.util.List;

// Lote: grupo de trabajos que se ejecutan juntos en un nodo. Se arma (en preparación), se inicia
// (en ejecución) y se cierra cuando todos sus trabajos tienen resultado registrado.
// El lote NO conoce las categorías: pregunta a cada trabajo si es compatible y deja que su estado
// decida qué operaciones están permitidas.
public class Lote {

    // Tolerancia para comparar sumas de horas decimales sin que los errores de redondeo rechacen un lote justo.
    private static final double TOLERANCIA = 1e-9;

    // Atributos privados.
    private String codigo;                           // identificador generado por el sistema
    private Nodo nodo;                               // nodo donde se ejecutará el lote
    private EstadoLote estado;                       // etapa actual del lote (objeto de estado)
    private List<Trabajo> trabajos;                  // trabajos asignados al lote
    // Los resultados se guardan en dos listas paralelas: la posición i de "resultados" es el resultado
    // del trabajo que está en la posición i de "trabajosConResultado". Son de ESTE lote: así un trabajo
    // que se reintenta en otro lote no se confunde con su ejecución anterior.
    private List<Trabajo> trabajosConResultado;      // trabajos cuyo resultado ya se registró en este lote
    private List<ResultadoEjecucion> resultados;     // resultado de cada uno de esos trabajos

    // Constructor: guarda el código y el nodo y deja el lote listo para armarse.
    public Lote(String codigo, Nodo nodo) {
        this.codigo = codigo;
        this.nodo = nodo;
        // Todo lote nuevo empieza en preparación.
        this.estado = new LoteEnPreparacion();
        // Listas vacías: ArrayList mantiene el orden en que se agregan los elementos.
        this.trabajos = new ArrayList<>();
        this.trabajosConResultado = new ArrayList<>();
        this.resultados = new ArrayList<>();
    }

    // Devuelve el código del lote.
    public String getCodigo() {
        return codigo;
    }

    // Devuelve el nodo del lote.
    public Nodo getNodo() {
        return nodo;
    }

    // Devuelve el estado actual del lote.
    public EstadoLote getEstado() {
        return estado;
    }

    // Devuelve una COPIA de la lista de trabajos: quien la reciba puede recorrerla, pero si le agrega
    // o quita elementos solo modifica la copia, no los trabajos reales del lote.
    public List<Trabajo> getTrabajos() {
        return new ArrayList<>(trabajos);
    }

    // Suma las horas ESTIMADAS (no las facturables) de los trabajos asignados.
    public double calcularHorasAsignadas() {
        double suma = 0.0;
        // Recorre cada trabajo del lote y acumula sus horas estimadas.
        for (Trabajo trabajo : trabajos) {
            suma += trabajo.getHorasEstimadas();
        }
        return suma;
    }

    // Capacidad que todavía queda libre: capacidad del nodo menos las horas ya asignadas.
    public double calcularCapacidadDisponible() {
        return nodo.getCapacidadMaxima() - calcularHorasAsignadas();
    }

    // Asigna un trabajo al lote. Primero revisa TODAS las condiciones y solo después modifica algo,
    // de modo que una asignación rechazada no deja rastro.
    public void asignarTrabajo(Trabajo trabajo) {
        if (trabajo == null) {
            throw new ReglaNegocioException("Debe indicar un trabajo para asignar.");
        }
        // El estado del lote decide si se pueden asignar trabajos (solo en preparación).
        estado.validarModificacion();
        // Condición 3 del enunciado: el trabajo debe estar en cola (si está asignado a otro lote,
        // en ejecución, completado o cancelado, no puede asignarse).
        if (!trabajo.puedeAsignarse()) {
            throw new ReglaNegocioException("El trabajo '" + trabajo.getCodigo()
                    + "' no puede asignarse porque está '" + trabajo.getEstado() + "'.");
        }
        // Condición 2: compatibilidad con el nodo. La decide la categoría del trabajo (polimorfismo).
        if (!trabajo.esCompatibleCon(nodo)) {
            throw new ReglaNegocioException("El trabajo '" + trabajo.getCodigo() + "' ("
                    + trabajo.getCategoria() + ") no es compatible con el nodo '" + nodo.getCodigo() + "'.");
        }
        // Condición 1: la suma de horas estimadas no puede superar la capacidad del nodo.
        double horasConElNuevo = calcularHorasAsignadas() + trabajo.getHorasEstimadas();
        if (horasConElNuevo > nodo.getCapacidadMaxima() + TOLERANCIA) {
            // %s inserta texto y %.2f inserta un decimal con dos cifras.
            throw new ReglaNegocioException(String.format(
                    "El trabajo '%s' excede la capacidad del nodo '%s': quedan %.2f horas disponibles y el trabajo requiere %.2f.",
                    trabajo.getCodigo(), nodo.getCodigo(), calcularCapacidadDisponible(),
                    trabajo.getHorasEstimadas()));
        }
        // Todas las condiciones se cumplen: el trabajo pasa a "Asignado" y se agrega a la lista del lote.
        trabajo.marcarAsignado();
        trabajos.add(trabajo);
    }

    // Retira un trabajo del lote (solo mientras está en preparación). El trabajo vuelve a quedar en cola.
    public void retirarTrabajo(Trabajo trabajo) {
        if (trabajo == null) {
            throw new ReglaNegocioException("Debe indicar un trabajo para retirar.");
        }
        // Solo se puede retirar mientras el lote está en preparación.
        estado.validarModificacion();
        // El trabajo debe pertenecer a este lote.
        if (!trabajos.contains(trabajo)) {
            throw new ReglaNegocioException("El trabajo '" + trabajo.getCodigo()
                    + "' no está asignado al lote '" + codigo + "'.");
        }
        // Se devuelve el trabajo a cola y se quita de la lista del lote.
        trabajo.marcarEnCola();
        trabajos.remove(trabajo);
    }

    // Inicia el lote: todos sus trabajos pasan a ejecución y se registra el cargo por reintento
    // (Q25) de los trabajos que ya habían tenido un intento. El cargo se registra AQUÍ, no al asignar.
    public void iniciar(RegistroIngresos ingresos) {
        // Pide el estado siguiente: si el lote no está en preparación, lanza excepción sin modificar nada.
        EstadoLote siguiente = estado.iniciar();
        // No puede iniciar un lote vacío.
        if (trabajos.isEmpty()) {
            throw new ReglaNegocioException("No se puede iniciar el lote '" + codigo + "' porque está vacío.");
        }
        // Se inicia cada trabajo; iniciarEjecucion devuelve el cargo de reintento (0 si es su primer intento).
        for (Trabajo trabajo : trabajos) {
            double cargo = trabajo.iniciarEjecucion();
            // Solo se registra ingreso cuando realmente hubo un cargo.
            if (cargo > 0) {
                ingresos.registrarPorReintento(trabajo.getCategoria(), cargo);
            }
        }
        // Finalmente el lote cambia a "En ejecución".
        estado = siguiente;
    }

    // Registra el resultado de un trabajo del lote. Cuando se registra el último, el lote se cierra
    // y el nodo queda libre.
    public void registrarResultado(Trabajo trabajo, ResultadoEjecucion resultado) {
        if (trabajo == null || resultado == null) {
            throw new ReglaNegocioException("Debe indicar el trabajo y su resultado.");
        }
        // El trabajo debe pertenecer a este lote.
        if (!trabajos.contains(trabajo)) {
            throw new ReglaNegocioException("El trabajo '" + trabajo.getCodigo()
                    + "' no pertenece al lote '" + codigo + "'.");
        }
        // No se puede registrar dos veces el resultado de un trabajo en el mismo lote.
        if (trabajosConResultado.contains(trabajo)) {
            throw new ReglaNegocioException("El trabajo '" + trabajo.getCodigo()
                    + "' ya tiene resultado registrado en el lote '" + codigo + "'.");
        }
        // Este será el último resultado si, con el nuevo, ya hay uno por cada trabajo del lote.
        boolean esElUltimo = (trabajosConResultado.size() + 1 == trabajos.size());
        // Pide el estado siguiente: si el lote no está en ejecución, lanza excepción sin modificar nada.
        EstadoLote siguiente = estado.registrarResultado(esElUltimo);
        // El resultado se aplica al trabajo (completado, o fallido con reintento/cancelación).
        resultado.aplicarA(trabajo);
        // Se guardan el trabajo y su resultado en la misma posición de las dos listas paralelas.
        trabajosConResultado.add(trabajo);
        resultados.add(resultado);
        // Se actualiza el estado del lote (pasa a "Cerrado" si era el último resultado).
        estado = siguiente;
    }

    // Indica si el lote está cerrado. La respuesta la da el objeto de estado.
    public boolean estaCerrado() {
        return estado.estaCerrado();
    }

    // Resumen del lote para mostrarlo en pantalla.
    @Override
    public String toString() {
        // %s inserta texto, %d un entero y %.2f un decimal con dos cifras.
        return String.format(
                "Lote %s | Nodo: %s | Estado: %s | Trabajos: %d | Horas asignadas: %.2f de %.2f | Capacidad disponible: %.2f",
                codigo, nodo.getCodigo(), estado, trabajos.size(), calcularHorasAsignadas(),
                nodo.getCapacidadMaxima(), calcularCapacidadDisponible());
    }
}