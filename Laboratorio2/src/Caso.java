import java.util.ArrayList;

public class Caso {
    private static final int MAX_UBICACIONES = 5;

    private String nombre;
    private String codigo;
    private String detectiveResponsable;
    private Ubicacion[] ubicaciones;
    private ArrayList<Pista> pistas;

    public Caso(
            String nombre,
            String codigo,
            String detectiveResponsable
    ) throws Exception {
        validarTexto(
                nombre,
                "El nombre del caso no puede estar vacío."
        );
        validarTexto(
                codigo,
                "El código del caso no puede estar vacío."
        );
        validarTexto(
                detectiveResponsable,
                "El nombre del detective no puede estar vacío."
        );

        this.nombre = nombre;
        this.codigo = codigo;
        this.detectiveResponsable = detectiveResponsable;

        ubicaciones = new Ubicacion[MAX_UBICACIONES];
        pistas = new ArrayList<Pista>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDetectiveResponsable() {
        return detectiveResponsable;
    }

    public void registrarUbicacion(
            int posicion,
            Ubicacion ubicacion
    ) throws Exception {
        validarPosicion(posicion);

        if (ubicacion == null) {
            throw new Exception(
                    "La ubicación no puede ser null."
            );
        }

        if (ubicaciones[posicion] != null) {
            throw new Exception(
                    "La posición seleccionada ya está ocupada."
            );
        }

        ubicaciones[posicion] = ubicacion;
    }

    public Ubicacion obtenerUbicacion(
            int posicion
    ) throws Exception {
        validarPosicion(posicion);

        if (ubicaciones[posicion] == null) {
            throw new Exception(
                    "La posición seleccionada está vacía."
            );
        }

        return ubicaciones[posicion];
    }

    public void mostrarUbicaciones() {
        boolean hayUbicaciones = false;

        for (int i = 0; i < ubicaciones.length; i++) {
            if (ubicaciones[i] != null) {
                hayUbicaciones = true;

                System.out.println(
                        "\nPosición " + (i + 1)
                );

                System.out.println(ubicaciones[i]);
            }
        }

        if (!hayUbicaciones) {
            System.out.println(
                    "No hay ubicaciones registradas."
            );
        }
    }

    public void modificarUbicacion(
            int posicion,
            int nuevoRiesgo,
            String nuevoEstado
    ) throws Exception {
        Ubicacion ubicacion =
                obtenerUbicacion(posicion);

        if (nuevoRiesgo < 1 || nuevoRiesgo > 10) {
            throw new Exception(
                    "El nivel de riesgo debe estar entre 1 y 10."
            );
        }

        validarTexto(
                nuevoEstado,
                "El estado no puede estar vacío."
        );

        ubicacion.setNivelRiesgo(nuevoRiesgo);
        ubicacion.setEstado(nuevoEstado);
    }

    public void descartarUbicacion(
            int posicion
    ) throws Exception {
        obtenerUbicacion(posicion);
        ubicaciones[posicion] = null;
    }

    public int contarUbicaciones() {
        int cantidad = 0;

        for (Ubicacion ubicacion : ubicaciones) {
            if (ubicacion != null) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public int contarEspaciosDisponibles() {
        return MAX_UBICACIONES
                - contarUbicaciones();
    }

    public Ubicacion obtenerUbicacionMayorRiesgo() {
        Ubicacion mayor = null;

        for (Ubicacion ubicacion : ubicaciones) {
            if (ubicacion != null) {
                if (mayor == null
                        || ubicacion.getNivelRiesgo()
                        > mayor.getNivelRiesgo()) {
                    mayor = ubicacion;
                }
            }
        }

        return mayor;
    }

    public void registrarPista(
            Pista pista
    ) throws Exception {
        if (pista == null) {
            throw new Exception(
                    "La pista no puede ser null."
            );
        }

        if (buscarPista(pista.getCodigo()) != null) {
            throw new Exception(
                    "Ya existe una pista con ese código."
            );
        }

        pistas.add(pista);
    }

    public Pista buscarPista(
            String codigo
    ) throws Exception {
        validarTexto(
                codigo,
                "El código de la pista no puede estar vacío."
        );

        for (Pista pista : pistas) {
            if (pista.getCodigo().equalsIgnoreCase(codigo)) {
                return pista;
            }
        }

        return null;
    }

    public void mostrarPistas() {
        if (pistas.isEmpty()) {
            System.out.println(
                    "No hay pistas registradas."
            );
            return;
        }

        for (Pista pista : pistas) {
            System.out.println("\n" + pista);
        }
    }

    public boolean modificarPista(
            String codigo,
            String nuevaDescripcion,
            String nuevoTipo,
            int nuevaImportancia,
            int nuevaConfiabilidad
    ) throws Exception {
        Pista pista = buscarPista(codigo);

        if (pista == null) {
            return false;
        }

        validarTexto(
                nuevaDescripcion,
                "La descripción no puede estar vacía."
        );

        validarTexto(
                nuevoTipo,
                "El tipo de evidencia no puede estar vacío."
        );

        if (nuevaImportancia < 1
                || nuevaImportancia > 10) {
            throw new Exception(
                    "El nivel de importancia debe estar entre 1 y 10."
            );
        }

        if (nuevaConfiabilidad < 0
                || nuevaConfiabilidad > 100) {
            throw new Exception(
                    "El nivel de confiabilidad debe estar entre 0 y 100."
            );
        }

        pista.setDescripcion(nuevaDescripcion);
        pista.setTipoEvidencia(nuevoTipo);
        pista.setNivelImportancia(nuevaImportancia);
        pista.setNivelConfiabilidad(nuevaConfiabilidad);

        return true;
    }

    public boolean eliminarPista(
            String codigo
    ) throws Exception {
        Pista pista = buscarPista(codigo);

        if (pista == null) {
            return false;
        }

        pistas.remove(pista);
        return true;
    }

    public int contarPistas() {
        return pistas.size();
    }

    public Pista obtenerPistaMayorImportancia() {
        if (pistas.isEmpty()) {
            return null;
        }

        Pista mayor = pistas.get(0);

        for (Pista pista : pistas) {
            if (pista.getNivelImportancia()
                    > mayor.getNivelImportancia()) {
                mayor = pista;
            }
        }

        return mayor;
    }

    public Pista obtenerPistaMayorConfiabilidad() {
        if (pistas.isEmpty()) {
            return null;
        }

        Pista mayor = pistas.get(0);

        for (Pista pista : pistas) {
            if (pista.getNivelConfiabilidad()
                    > mayor.getNivelConfiabilidad()) {
                mayor = pista;
            }
        }

        return mayor;
    }

    public double calcularPromedioImportancia() {
        if (pistas.isEmpty()) {
            return 0;
        }

        int suma = 0;

        for (Pista pista : pistas) {
            suma += pista.getNivelImportancia();
        }

        return (double) suma / pistas.size();
    }

    private void validarPosicion(
            int posicion
    ) throws Exception {
        if (posicion < 0
                || posicion >= ubicaciones.length) {
            throw new Exception(
                    "La posición debe estar entre 1 y 5."
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
}