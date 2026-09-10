package gt.edu.uvg.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Suscripcion {
    private int idSuscripcion;
    private int idTrabajador;
    private Plan plan;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoSuscripcion estado;
    private int viajesUsados;
    private BigDecimal montoPagado;

    public Suscripcion(int idSuscripcion, int idTrabajador, Plan plan, LocalDate fechaInicio, LocalDate fechaFin) {
        this.idSuscripcion = idSuscripcion;
        this.idTrabajador = idTrabajador;
        this.plan = plan;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = EstadoSuscripcion.ACTIVA;
        this.viajesUsados = 0;
        this.montoPagado = plan != null ? plan.getPrecio() : BigDecimal.ZERO;
    }

    public boolean estaVigente() {
        LocalDate hoy = LocalDate.now();
        return estado == EstadoSuscripcion.ACTIVA && (hoy.isEqual(fechaInicio) || hoy.isAfter(fechaInicio)) && (hoy.isEqual(fechaFin) || hoy.isBefore(fechaFin));
    }

    public int getIdSuscripcion() { return idSuscripcion; }
    public void setIdSuscripcion(int idSuscripcion) { this.idSuscripcion = idSuscripcion; }
    public int getIdTrabajador() { return idTrabajador; }
    public Plan getPlan() { return plan; }
    public void setPlan(Plan plan) { this.plan = plan; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public EstadoSuscripcion getEstado() { return estado; }
    public void setEstado(EstadoSuscripcion estado) { this.estado = estado; }
    public int getViajesUsados() { return viajesUsados; }
    public void setViajesUsados(int viajesUsados) { this.viajesUsados = viajesUsados; }
    public BigDecimal getMontoPagado() { return montoPagado; }
}
