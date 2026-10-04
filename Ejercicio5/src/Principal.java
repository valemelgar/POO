/**
 * Clase de arranque: crea la misión con la carga inicial, la vista y el controlador.
 */
public class Principal {

    // Punto de entrada: arma las piezas del MVC y ejecuta el simulador
    public static void main(String[] args) {
        Mision mision = crearMisionInicial();                              // Modelo con la misión en progreso
        VistaConsola vista = new VistaConsola();                           // Vista de consola
        ControladorSimulador controlador = new ControladorSimulador(mision, vista); // Une modelo y vista
        controlador.iniciar();                                             // Arranca el menú
    }

    /**
     * Crea los 10 módulos de la carga inicial y los agrega en orden a una misión con
     * 168 unidades de energía, 60 MB en órbita, 60 MB descargados y 2 ciclos completados.
     * Los históricos de los módulos de tierra suman 60 MB (20 + 25 + 15), igual que la misión.
     */
    private static Mision crearMisionInicial() {
        Mision mision = new Mision(168, 60, 60, 2); // Recursos iniciales de la misión en progreso

        // Orden de participación: el orden en que se agregan es el orden en que operan
        // Parámetros Energía: id, nombre, salud, activo, costo, energiaPorCiclo, ciclosAcumulados
        mision.agregarModulo(new ModuloEnergia("EN-01", "Panel Solar Alfa", 95, true, 15000, 20, 2));
        // Parámetros Vuelo: id, nombre, salud, activo, costo, instrumento, MB/ciclo, consumo, ciclos
        mision.agregarModulo(new ModuloVuelo("VU-01", "Camara Aurora", 90, true, 25000, "camara", 15, 8, 2));
        // Parámetros Tierra: id, nombre, salud, activo, costo, estacion, capacidad, energia, totalDescargado
        mision.agregarModulo(new ModuloTierra("TI-01", "Antena Campus Central", 88, true, 18000, "UVG Campus Central", 10, 5, 20));
        mision.agregarModulo(new ModuloEnergia("EN-02", "Panel Solar Beta", 85, true, 20000, 25, 2));
        mision.agregarModulo(new ModuloVuelo("VU-02", "Sensor Magnetico", 92, true, 22000, "sensor", 12, 6, 2));
        mision.agregarModulo(new ModuloTierra("TI-02", "Antena Norte", 80, true, 21000, "Estacion Norte", 15, 7, 25));
        mision.agregarModulo(new ModuloVuelo("VU-03", "Sensor Radiacion", 60, false, 19000, "sensor", 10, 5, 1)); // Inactivo
        mision.agregarModulo(new ModuloEnergia("EN-03", "Panel Solar Gamma", 70, false, 30000, 30, 0));          // Inactivo
        mision.agregarModulo(new ModuloTierra("TI-03", "Antena Sur", 75, false, 17000, "Estacion Sur", 20, 9, 15)); // Inactiva
        mision.agregarModulo(new ModuloVuelo("VU-04", "Camara Auroral", 83, true, 27000, "camara", 10, 4, 2));

        return mision; // Devuelve la misión lista para usarse
    }
}