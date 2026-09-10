package gt.edu.uvg.model;

import gt.edu.uvg.util.PasswordUtil;

public abstract class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String contraseña;
    private String idioma;
    private boolean estadoCuenta;

    public Usuario(int idUsuario, String nombre, String apellido, String correo, String telefono, String contraseña) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.idioma = "ES";
        this.estadoCuenta = true;
    }

    public boolean iniciarSesion(String correoIngresado, String claveIngresada) {
        return this.correo.equalsIgnoreCase(correoIngresado) && PasswordUtil.verificarPassword(claveIngresada, this.contraseña);
    }

    public void cerrarSesion() {}

    public void editarPerfil(String nombre, String apellido, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public void cambiarIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    public String getIdioma() { return idioma; }
    public boolean isEstadoCuenta() { return estadoCuenta; }
    public void setEstadoCuenta(boolean estadoCuenta) { this.estadoCuenta = estadoCuenta; }
}
