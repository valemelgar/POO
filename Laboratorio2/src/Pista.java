public class Pista {
    private String codigo;
    private String descripcion;
    private String tipoEvidencia;
    private int nivelImportancia;
    private int nivelConfiabilidad;

    public Pista(
            String codigo,
            String descripcion,
            String tipoEvidencia,
            int nivelImportancia,
            int nivelConfiabilidad
    ) throws Exception {
        validarTexto(codigo, "El código no puede estar vacío.");
        validarTexto(
                descripcion,
                "La descripción no puede estar vacía."
        );
        validarTexto(
                tipoEvidencia,
                "El tipo de evidencia no puede estar vacío."
        );
        validarImportancia(nivelImportancia);
        validarConfiabilidad(nivelConfiabilidad);

        this.codigo = codigo;
        this.descripcion = descripcion;
        this.tipoEvidencia = tipoEvidencia;
        this.nivelImportancia = nivelImportancia;
        this.nivelConfiabilidad = nivelConfiabilidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipoEvidencia() {
        return tipoEvidencia;
    }

    public int getNivelImportancia() {
        return nivelImportancia;
    }

    public int getNivelConfiabilidad() {
        return nivelConfiabilidad;
    }

    public void setDescripcion(String descripcion) throws Exception {
        validarTexto(
                descripcion,
                "La descripción no puede estar vacía."
        );
        this.descripcion = descripcion;
    }

    public void setTipoEvidencia(
            String tipoEvidencia
    ) throws Exception {
        validarTexto(
                tipoEvidencia,
                "El tipo de evidencia no puede estar vacío."
        );
        this.tipoEvidencia = tipoEvidencia;
    }

    public void setNivelImportancia(
            int nivelImportancia
    ) throws Exception {
        validarImportancia(nivelImportancia);
        this.nivelImportancia = nivelImportancia;
    }

    public void setNivelConfiabilidad(
            int nivelConfiabilidad
    ) throws Exception {
        validarConfiabilidad(nivelConfiabilidad);
        this.nivelConfiabilidad = nivelConfiabilidad;
    }

    private void validarImportancia(
            int nivelImportancia
    ) throws Exception {
        if (nivelImportancia < 1 || nivelImportancia > 10) {
            throw new Exception(
                    "El nivel de importancia debe estar entre 1 y 10."
            );
        }
    }

    private void validarConfiabilidad(
            int nivelConfiabilidad
    ) throws Exception {
        if (nivelConfiabilidad < 0
                || nivelConfiabilidad > 100) {
            throw new Exception(
                    "El nivel de confiabilidad debe estar entre 0 y 100."
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
                + "\nDescripción: " + descripcion
                + "\nTipo de evidencia: " + tipoEvidencia
                + "\nNivel de importancia: "
                + nivelImportancia
                + "\nNivel de confiabilidad: "
                + nivelConfiabilidad;
    }
}