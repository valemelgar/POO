package gt.edu.uvg.model;

import java.util.ArrayList;
import java.util.List;

public class Pasajero extends Usuario {
    private double calificacionPromedio;
    private int cantidadViajes;
    private final List<Viaje> historialViajes;

    public Pasajero(int idUsuario, String nombre, String apellido, String correo, String telefono, String contraseña) {
        super(idUsuario, nombre, apellido, correo, telefono, contraseña);
        this.calificacionPromedio = 5.0;
        this.cantidadViajes = 0;
        this.historialViajes = new ArrayList<>();
    }

    public Viaje solicitarViaje(String origen, String destino, double distancia) {
        Viaje viaje = new Viaje(0, origen, destino, distancia, this);
        this.historialViajes.add(viaje);
        return viaje;
    }

    public void confirmarViaje() { this.cantidadViajes++; }
    public void cancelarViaje() {}
    public List<Viaje> verHistorial() { return new ArrayList<>(historialViajes); }

    public double getCalificacionPromedio() { return calificacionPromedio; }
    public void setCalificacionPromedio(double calificacionPromedio) { this.calificacionPromedio = calificacionPromedio; }
    public int getCantidadViajes() { return cantidadViajes; }
    public void setCantidadViajes(int cantidadViajes) { this.cantidadViajes = cantidadViajes; }
}
