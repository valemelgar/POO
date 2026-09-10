package gt.edu.uvg.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Viaje {
    private int idViaje;
    private String origen;
    private String destino;
    private LocalDateTime fechaHoraSolicitud;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private double distancia;
    private EstadoViaje estado;
    private BigDecimal precioEstimado;
    private BigDecimal precioFinal;
    private Pasajero pasajero;
    private Conductor conductor;
    private Tarifa tarifa;

    public Viaje(int idViaje, String origen, String destino, double distancia, Pasajero pasajero) {
        this.idViaje = idViaje;
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
        this.pasajero = pasajero;
        this.fechaHoraSolicitud = LocalDateTime.now();
        this.estado = EstadoViaje.SOLICITADO;
        this.precioEstimado = BigDecimal.ZERO;
    }

    public int getIdViaje() { return idViaje; }
    public void setIdViaje(int idViaje) { this.idViaje = idViaje; }
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public LocalDateTime getFechaHoraSolicitud() { return fechaHoraSolicitud; }
    public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) { this.fechaHoraInicio = fechaHoraInicio; }
    public LocalDateTime getFechaHoraFin() { return fechaHoraFin; }
    public void setFechaHoraFin(LocalDateTime fechaHoraFin) { this.fechaHoraFin = fechaHoraFin; }
    public double getDistancia() { return distancia; }
    public EstadoViaje getEstado() { return estado; }
    public void setEstado(EstadoViaje estado) { this.estado = estado; }
    public BigDecimal getPrecioEstimado() { return precioEstimado; }
    public void setPrecioEstimado(BigDecimal precioEstimado) { this.precioEstimado = precioEstimado; }
    public BigDecimal getPrecioFinal() { return precioFinal; }
    public void setPrecioFinal(BigDecimal precioFinal) { this.precioFinal = precioFinal; }
    public Pasajero getPasajero() { return pasajero; }
    public Conductor getConductor() { return conductor; }
    public void setConductor(Conductor conductor) { this.conductor = conductor; }
    public Tarifa getTarifa() { return tarifa; }
    public void setTarifa(Tarifa tarifa) { this.tarifa = tarifa; }
}
