package gt.edu.uvg.model;

public class Repartidor extends Trabajador {
    private TipoTransporte tipoTransporte;

    public Repartidor(int idUsuario, String nombre, String apellido, String correo, String telefono, String contraseña, TipoTransporte tipoTransporte) {
        super(idUsuario, nombre, apellido, correo, telefono, contraseña);
        this.tipoTransporte = tipoTransporte;
    }

    public TipoTransporte getTipoTransporte() { return tipoTransporte; }
    public void setTipoTransporte(TipoTransporte tipoTransporte) { this.tipoTransporte = tipoTransporte; }
}
