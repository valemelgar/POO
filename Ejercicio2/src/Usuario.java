public class Usuario {

    private String nombre;
    private String nombreUsuario;
    private int edad;
    private int cantidadCalificaciones;
    private int[] calificaciones;

    public Usuario() {
        cantidadCalificaciones = 0;
        calificaciones = new int[10];
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getCantidadCalificaciones() {
        return cantidadCalificaciones;
    }

    public int[] getCalificaciones() {
        return calificaciones;
    }

    public boolean registrarCalificacion(int calificacion) {

        if (calificacion < 1) {
            return false;
        }

        if (calificacion > 10) {
            return false;
        }

        if (cantidadCalificaciones == 10) {
            return false;
        }

        calificaciones[cantidadCalificaciones] = calificacion;
        cantidadCalificaciones++;

        return true;
    }

    public int consultarCalificacion(int numeroPelicula) {

        if (numeroPelicula < 1) {
            return -1;
        }

        if (numeroPelicula > cantidadCalificaciones) {
            return -1;
        }

        return calificaciones[numeroPelicula - 1];
    }

    public boolean modificarCalificacion(int numeroPelicula, int nuevaCalificacion) {

        if (numeroPelicula < 1) {
            return false;
        }

        if (numeroPelicula > cantidadCalificaciones) {
            return false;
        }

        if (nuevaCalificacion < 1) {
            return false;
        }

        if (nuevaCalificacion > 10) {
            return false;
        }

        calificaciones[numeroPelicula - 1] = nuevaCalificacion;

        return true;
    }

    public void mostrarCalificaciones() {

        for (int i = 0; i < cantidadCalificaciones; i++) {
            System.out.println("Pelicula " + (i + 1)
                    + ": " + calificaciones[i]);
        }
    }

    public double calcularPromedio() {

        if (cantidadCalificaciones == 0) {
            return 0;
        }

        int suma = 0;

        for (int i = 0; i < cantidadCalificaciones; i++) {
            suma = suma + calificaciones[i];
        }

        return (double) suma / cantidadCalificaciones;
    }

    public int mejorCalificacion() {

        if (cantidadCalificaciones == 0) {
            return 0;
        }

        int mejor = calificaciones[0];

        for (int i = 0; i < cantidadCalificaciones; i++) {

            if (calificaciones[i] > mejor) {
                mejor = calificaciones[i];
            }
        }

        return mejor;
    }

    public int peorCalificacion() {

        if (cantidadCalificaciones == 0) {
            return 0;
        }

        int peor = calificaciones[0];

        for (int i = 0; i < cantidadCalificaciones; i++) {

            if (calificaciones[i] < peor) {
                peor = calificaciones[i];
            }
        }

        return peor;
    }

    public int espaciosDisponibles() {
        return 10 - cantidadCalificaciones;
    }
}