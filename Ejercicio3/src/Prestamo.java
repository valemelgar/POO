public class Prestamo {
    private String codigo;
    private String carne;
    private String nombreEstudiante;
    private String tituloLibro;
    private int diasAutorizados;

    public Prestamo(String codigo, String carne,
                    String nombreEstudiante,
                    String tituloLibro,
                    int diasAutorizados) {
        this.codigo = codigo;
        this.carne = carne;
        this.nombreEstudiante = nombreEstudiante;
        this.tituloLibro = tituloLibro;
        this.diasAutorizados = diasAutorizados;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCarne() {
        return carne;
    }

    public void setCarne(String carne) {
        this.carne = carne;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    public int getDiasAutorizados() {
        return diasAutorizados;
    }

    public void setDiasAutorizados(int diasAutorizados) {
        this.diasAutorizados = diasAutorizados;
    }

    public String toString() {
        return "Código: " + codigo
                + "\nCarné: " + carne
                + "\nEstudiante: " + nombreEstudiante
                + "\nLibro: " + tituloLibro
                + "\nDías autorizados: " + diasAutorizados;
    }
}