// Clase con el método main: punto de entrada. Solo arma las piezas y arranca el controlador.
public class Principal {

    public static void main(String[] args) {
        VistaConsola vista = new VistaConsola();
        SistemaTorreGT sistema = new SistemaTorreGT();
        ControladorSimulador controlador = new ControladorSimulador(sistema, vista);
        controlador.iniciar();
    }
}