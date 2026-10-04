import java.util.ArrayList;   // Lista que guarda los módulos en orden

/**
 * Modelo principal: administra la lista polimórfica de módulos y los recursos compartidos
 * (energía disponible, datos en órbita y datos descargados).
 */
public class Mision {

    private final ArrayList<Modulo> modulos;  // Única lista polimórfica con los tres tipos de módulos
    private double energiaDisponible;         // Energía disponible para que los módulos operen
    private double datosEnOrbita;             // MB pendientes de descarga
    private double totalDatosDescargados;     // Histórico de MB descargados por toda la misión
    private int cicloActual;                  // Cantidad de ciclos completados

    // Constructor: inicializa los recursos (no negativos) y crea la lista vacía
    public Mision(double energiaDisponible, double datosEnOrbita, double totalDatosDescargados, int cicloActual) {
        // Ningún recurso puede iniciar en negativo
        if (energiaDisponible < 0 || datosEnOrbita < 0 || totalDatosDescargados < 0 || cicloActual < 0) {
            throw new IllegalArgumentException("Los recursos de la misión no pueden ser negativos.");
        }
        this.modulos = new ArrayList<>();                         // Crea la lista vacía
        this.energiaDisponible = energiaDisponible;               // Guarda la energía inicial
        this.datosEnOrbita = datosEnOrbita;                       // Guarda los datos iniciales en órbita
        this.totalDatosDescargados = totalDatosDescargados;       // Guarda el histórico inicial
        this.cicloActual = cicloActual;                           // Guarda los ciclos ya completados
    }

    // Agrega un módulo no nulo a la lista, comprobando que su ID no esté repetido
    public void agregarModulo(Modulo modulo) {
        // No se aceptan módulos nulos
        if (modulo == null) {
            throw new IllegalArgumentException("El módulo no puede ser nulo.");
        }
        // El ID debe ser único en toda la misión
        if (buscarPorId(modulo.getId()) != null) {
            throw new IllegalArgumentException("Ya existe un módulo con el ID " + modulo.getId() + ".");
        }
        modulos.add(modulo); // Se agrega al final para conservar el orden de participación
    }

    // Devuelve una COPIA de la lista para proteger su estructura y su orden
    public ArrayList<Modulo> getModulos() {
        return new ArrayList<>(modulos);
    }

    // Busca un módulo por ID (ignora mayúsculas); devuelve null si no existe
    public Modulo buscarPorId(String id) {
        // Recorre todos los módulos
        for (Modulo m : modulos) {
            // Compara el ID sin distinguir mayúsculas/minúsculas
            if (m.getId().equalsIgnoreCase(id.trim())) {
                return m; // Lo encontró
            }
        }
        return null; // No existe
    }

    // Devuelve todos los módulos cuyo nombre coincida (sin distinguir mayúsculas/minúsculas)
    public ArrayList<Modulo> buscarPorNombre(String nombre) {
        ArrayList<Modulo> encontrados = new ArrayList<>(); // Aquí se guardan las coincidencias
        // Recorre todos los módulos
        for (Modulo m : modulos) {
            // Si el nombre coincide, se agrega al resultado
            if (m.getNombre().equalsIgnoreCase(nombre.trim())) {
                encontrados.add(m);
            }
        }
        return encontrados; // Puede estar vacía si no hay coincidencias
    }

    /**
     * Devuelve una copia ordenada por costo ascendente, sin alterar el orden de ejecución.
     * Usa ordenamiento por inserción hecho a mano (sin Comparator).
     */
    public ArrayList<Modulo> obtenerCatalogoPorCosto() {
        ArrayList<Modulo> copia = new ArrayList<>(modulos); // Copia para no tocar la lista original
        // Cada posición i toma el módulo y lo coloca en su lugar dentro de la parte ya ordenada
        for (int i = 1; i < copia.size(); i++) {
            Modulo actual = copia.get(i);  // Módulo que se va a colocar en su posición correcta
            int j = i - 1;                 // Última posición de la parte ya ordenada
            // Desplaza a la derecha los módulos que cuestan más que el actual
            while (j >= 0 && copia.get(j).getCostoConstruccion() > actual.getCostoConstruccion()) {
                copia.set(j + 1, copia.get(j)); // Mueve el módulo más caro una posición a la derecha
                j--;                            // Retrocede para seguir comparando
            }
            copia.set(j + 1, actual); // Coloca el módulo en el hueco que quedó
        }
        return copia; // Devuelve la copia ordenada de menor a mayor costo
    }

    /**
     * Avanza un ciclo: invoca procesarCiclo en cada módulo, en orden (polimorfismo), y los
     * recursos se actualizan después de cada uno. Devuelve los resultados y los recursos finales.
     */
    public ArrayList<String> avanzarCiclo() {
        ArrayList<String> resultados = new ArrayList<>(); // Mensajes que se mostrarán al usuario
        // Cada módulo participa en el orden de la carga inicial
        for (Modulo m : modulos) {
            resultados.add(m.procesarCiclo(this)); // Cada tipo de módulo ejecuta su propia versión
        }
        cicloActual++; // El ciclo se completó
        resultados.add("--- Estado de los recursos al finalizar el ciclo " + cicloActual + " ---"); // Encabezado
        resultados.add("Energía disponible: " + energiaDisponible);               // Energía final
        resultados.add("Datos en órbita: " + datosEnOrbita + " MB");              // Datos pendientes
        resultados.add("Datos descargados (histórico): " + totalDatosDescargados + " MB"); // Histórico
        return resultados;
    }

    // Devuelve la energía disponible
    public double getEnergiaDisponible() {
        return energiaDisponible;
    }

    // Devuelve los MB pendientes de descarga
    public double getDatosEnOrbita() {
        return datosEnOrbita;
    }

    // Devuelve el histórico descargado por la misión
    public double getTotalDatosDescargados() {
        return totalDatosDescargados;
    }

    // Devuelve los ciclos completados
    public int getCicloActual() {
        return cicloActual;
    }

    // Descuenta energía solo si alcanza; devuelve true si se consumió y false si no alcanzó
    public boolean consumirEnergia(double cantidad) {
        // Si no hay suficiente no se modifica nada
        if (cantidad > energiaDisponible) {
            return false;
        }
        energiaDisponible -= cantidad; // Descuenta la energía usada
        return true;
    }

    // Suma la energía producida por un módulo de energía
    public void agregarEnergia(double cantidad) {
        energiaDisponible += cantidad;
    }

    // Suma los MB recolectados por un módulo de vuelo
    public void agregarDatosEnOrbita(double cantidad) {
        datosEnOrbita += cantidad;
    }

    // Transfiere el mínimo entre la capacidad y los datos disponibles; devuelve los MB transferidos
    public double descargarDatos(double capacidad) {
        double transferidos = Math.min(capacidad, datosEnOrbita); // No se puede descargar más de lo que hay
        datosEnOrbita -= transferidos;                            // Esos datos ya no están pendientes
        totalDatosDescargados += transferidos;                    // Se suman al histórico de la misión
        return transferidos;
    }

    // Cuenta todos los módulos de tierra de la lista
    public int contarModulosTierra() {
        int contador = 0; // Acumulador
        // Recorre la lista polimórfica
        for (Modulo m : modulos) {
            // instanceof identifica los módulos de tierra
            if (m instanceof ModuloTierra) {
                contador++;
            }
        }
        return contador;
    }

    // Cuenta los módulos de tierra que están activos
    public int contarModulosTierraActivos() {
        int contador = 0; // Acumulador
        // Recorre la lista polimórfica
        for (Modulo m : modulos) {
            // Solo cuenta los de tierra que además estén activos
            if (m instanceof ModuloTierra && m.isActivo()) {
                contador++;
            }
        }
        return contador;
    }

    // Suma las capacidades de descarga por ciclo de los módulos de tierra activos
    public double calcularCapacidadDescargaActiva() {
        double total = 0; // Acumulador
        // Recorre la lista polimórfica
        for (Modulo m : modulos) {
            // Solo considera los de tierra activos
            if (m instanceof ModuloTierra && m.isActivo()) {
                total += ((ModuloTierra) m).getCapacidadDescarga(); // Convierte a ModuloTierra para leer su capacidad
            }
        }
        return total;
    }

    // Devuelve todos los módulos de tierra empatados en el mayor histórico (lista vacía si no hay)
    public ArrayList<ModuloTierra> obtenerTierraConMayorDescarga() {
        ArrayList<ModuloTierra> mayores = new ArrayList<>(); // Aquí se guardan los líderes (puede haber empates)
        double maximo = -1;                                  // Mayor histórico encontrado hasta ahora
        // Recorre la lista polimórfica
        for (Modulo m : modulos) {
            // Solo interesan los módulos de tierra
            if (m instanceof ModuloTierra) {
                ModuloTierra t = (ModuloTierra) m; // Convierte para acceder a su histórico
                // Si supera el máximo actual, reinicia la lista de líderes
                if (Double.compare(t.getTotalDescargado(), maximo) > 0) {
                    maximo = t.getTotalDescargado(); // Nuevo máximo
                    mayores.clear();                 // Descarta los líderes anteriores
                    mayores.add(t);                  // Este es el nuevo líder
                } else if (Double.compare(t.getTotalDescargado(), maximo) == 0) {
                    mayores.add(t);                  // Empata con el máximo: se agrega también
                }
            }
        }
        return mayores;
    }
}