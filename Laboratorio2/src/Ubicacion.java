public class Ubicacion {
    private String codigo;
    private String nombre;
    private String direccionDescripcion;
    private int nivelRiesgo;
    private String estado;

    public Ubicacion(
            String codigo,
            String nombre,
            String direccionDescripcion,
            int nivelRiesgo,
            String estado
    ) throws Exception {
        validarTexto(codigo, "El código no puede estar vacío.");
        validarTexto(nombre, "El nombre no puede estar vacío.");
        validarTexto(
                direccionDescripcion,
                "La dirección o descripción no puede estar vacía."
        );
        validarRiesgo(nivelRiesgo);
        validarTexto(estado, "El estado no puede estar vacío.");

        this.codigo = codigo;
        this.nombre = nombre;
        this.direccionDescripcion = direccionDescripcion;
        this.nivelRiesgo = nivelRiesgo;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccionDescripcion() {
        return direccionDescripcion;
    }

    public int getNivelRiesgo() {
        return nivelRiesgo;
    }

    public String getEstado() {
        return estado;
    }

    public void setNivelRiesgo(int nivelRiesgo) throws Exception {
        validarRiesgo(nivelRiesgo);
        this.nivelRiesgo = nivelRiesgo;
    }

    public void setEstado(String estado) throws Exception {
        validarTexto(estado, "El estado no puede estar vacío.");
        this.estado = estado;
    }

    private void validarRiesgo(int nivelRiesgo) throws Exception {
        if (nivelRiesgo < 1 || nivelRiesgo > 10) {
            throw new Exception(
                    "El nivel de riesgo debe estar entre 1 y 10."
            );
        }
    }

    private void validarTexto(
            String texto,
            String mensaje
    ) throws Exception {
        if (texto == null || texto.trim().isEmpty()) {
            throw new Exception(mensaje);
        }
    }

    public String toString() {
        return "Código: " + codigo
                + "\nNombre: " + nombre
                + "\nDirección o descripción: "
                + direccionDescripcion
                + "\nNivel de riesgo: " + nivelRiesgo
                + "\nEstado: " + estado;
    }
}