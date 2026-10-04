/**
 * Clase abstracta que representa las características comunes de todos los módulos
 * Es abstracta porque nunca se crea un "módulo genérico", siempre es de vuelo, tierra o energía
 */
public abstract class Modulo {

    private final String id;                 // Identificador único del módulo (no cambia)
    private final String nombre;             // Nombre del módulo (no cambia)
    private int salud;                       // Salud del módulo, entre 0 y 100
    private boolean activo;                  // Indica si el módulo está habilitado para operar
    private final double costoConstruccion;  // Costo de construcción (no cambia)

    /**
     * Constructor protegido: solo las clases hijas pueden invocarlo mediante super(...).
     * Inicializa los atributos comunes y valida sus valores
     */
    protected Modulo(String id, String nombre, int salud, boolean activo, double costoConstruccion) {
        // Valida que el id no sea nulo ni esté vacío
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede estar vacío."); // Rechaza el dato inválido
        }
        // Valida que el nombre no sea nulo ni esté vacío
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío."); // Rechaza el dato inválido
        }
        // Valida que la salud esté en el rango permitido
        if (salud < 0 || salud > 100) {
            throw new IllegalArgumentException("La salud debe estar entre 0 y 100."); // Rechaza el dato inválido
        }
        // Valida que el costo no sea negativo
        if (costoConstruccion < 0) {
            throw new IllegalArgumentException("El costo de construcción no puede ser negativo."); // Rechaza el dato inválido
        }
        this.id = id.trim();                         // Guarda el id sin espacios sobrantes
        this.nombre = nombre.trim();                 // Guarda el nombre sin espacios sobrantes
        this.salud = salud;                          // Guarda la salud inicial
        this.activo = activo;                        // Guarda el estado inicial
        this.costoConstruccion = costoConstruccion;  // Guarda el costo de construcción
    }

    /**
     * Método abstracto: cada tipo de módulo define cómo participa en un ciclo.
     * Se usa polimorfismo, la misión llama a este método sin saber qué tipo de módulo es.
     * Devuelve un texto con el resultado de la operación o la causa por la que no operó.
     */
    public abstract String procesarCiclo(Mision mision);

    // Devuelve el ID del módulo
    public String getId() {
        return id;
    }

    // Devuelve el nombre del módulo
    public String getNombre() {
        return nombre;
    }

    // Devuelve la salud del módulo
    public int getSalud() {
        return salud;
    }

    // Indica si el módulo está activo
    public boolean isActivo() {
        return activo;
    }

    // Devuelve el costo de construcción
    public double getCostoConstruccion() {
        return costoConstruccion;
    }

    // Actualiza la salud validando que esté entre 0 y 100
    public void setSalud(int salud) {
        // Verifica el rango permitido
        if (salud < 0 || salud > 100) {
            throw new IllegalArgumentException("La salud debe estar entre 0 y 100."); // Rechaza el valor inválido
        }
        this.salud = salud; // Guarda el nuevo valor
    }

    // Actualiza el estado activo/inactivo del módulo
    public void setActivo(boolean activo) {
        this.activo = activo; // Guarda el nuevo estado
    }

    // Devuelve los atributos comunes como texto (las hijas lo extienden con super.toString())
    @Override
    public String toString() {
        return String.format("ID: %s | Nombre: %s | Salud: %d | Estado: %s | Costo: %.2f",
                id, nombre, salud, activo ? "Activo" : "Inactivo", costoConstruccion);
    }

    // Dos módulos son iguales si tienen el mismo ID (el ID es único)
    @Override
    public boolean equals(Object otro) {
        if (this == otro) return true;                         // Mismo objeto en memoria
        if (otro == null || getClass() != otro.getClass()) {   // Null o de otra clase: no son iguales
            return false;
        }
        Modulo m = (Modulo) otro;                              // Convierte para comparar el ID
        return id.equals(m.id);                                // Iguales si el ID coincide
    }
}