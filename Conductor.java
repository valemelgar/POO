package gt.edu.uvg.model;

import java.time.LocalDate;

public class Conductor extends Trabajador {
    private String numeroLicencia;
    private LocalDate vencimientoLicencia;
    private Vehiculo vehiculo;

    public Conductor(int idUsuario, String nombre, String apellido, String correo, String telefono, String contraseña, String numeroLicencia, LocalDate vencimientoLicencia) {
        super(idUsuario, nombre, apellido, correo, telefono, contraseña);
        this.numeroLicencia = numeroLicencia;
        this.vencimientoLicencia = vencimientoLicencia;
    }

    public String getNumeroLicencia() { return numeroLicencia; }
    public LocalDate getVencimientoLicencia() { return vencimientoLicencia; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }
}
