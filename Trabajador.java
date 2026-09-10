package gt.edu.uvg.model;

import java.math.BigDecimal;

public abstract class Trabajador extends Usuario {
    private boolean disponible;
    private double calificacionPromedio;
    private BigDecimal gananciasSemana;
    private int viajesRealizados;
    private EstadoConexion estadoConexion;
    private TipoServicio tipoServicio;

    public Trabajador(int idUsuario, String nombre, String apellido, String correo, String telefono, String contraseña) {
        super(idUsuario, nombre, apellido, correo, telefono, contraseña);
        this.disponible = false;
        this.calificacionPromedio = 5.0;
        this.gananciasSemana = BigDecimal.ZERO;
        this.viajesRealizados = 0;
        this.estadoConexion = EstadoConexion.DESCONECTADO;
        this.tipoServicio = TipoServicio.AMBOS;
    }

    public void conectarse() {
        this.estadoConexion = EstadoConexion.CONECTADO;
        this.disponible = true;
    }

    public void desconectarse() {
        this.estadoConexion = EstadoConexion.DESCONECTADO;
        this.disponible = false;
    }

    public void agregarGanancia(BigDecimal monto) {
        if (monto != null && monto.compareTo(BigDecimal.ZERO) > 0) {
            this.gananciasSemana = this.gananciasSemana.add(monto);
            this.viajesRealizados++;
        }
    }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public double getCalificacionPromedio() { return calificacionPromedio; }
    public void setCalificacionPromedio(double calificacionPromedio) { this.calificacionPromedio = calificacionPromedio; }
    public BigDecimal getGananciasSemana() { return gananciasSemana; }
    public void setGananciasSemana(BigDecimal gananciasSemana) { this.gananciasSemana = gananciasSemana; }
    public int getViajesRealizados() { return viajesRealizados; }
    public void setViajesRealizados(int viajesRealizados) { this.viajesRealizados = viajesRealizados; }
    public EstadoConexion getEstadoConexion() { return estadoConexion; }
    public void setEstadoConexion(EstadoConexion estadoConexion) { this.estadoConexion = estadoConexion; }
    public TipoServicio getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(TipoServicio tipoServicio) { this.tipoServicio = tipoServicio; }
}
