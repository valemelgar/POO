import java.util.Scanner;

// Clase principal: contiene el método main, que es el punto de entrada del programa.
public class Main {

    // Constructor privado: esta clase solo sirve para arrancar el programa, no se instancia.
    private Main() {
    }

    // Método main: crea la granja, carga los datos iniciales y le entrega el control al menú.
    public static void main(String[] args) {
        // Crea el sistema vacío.
        GranjaRenderGT granja = new GranjaRenderGT();
        // Carga trabajos y nodos de ejemplo (sin registrar ingresos).
        cargarDatosIniciales(granja);
        // Scanner para leer lo que escribe el usuario por consola.
        Scanner entrada = new Scanner(System.in);
        // Crea el menú y lo ejecuta hasta que el usuario decida salir.
        MenuConsola menu = new MenuConsola(granja, entrada);
        menu.ejecutar();
        // Libera el recurso de lectura al terminar.
        entrada.close();
    }

    // Carga los datos con los que inicia el programa: 2 trabajos de cada categoría (en cola) y 3 nodos.
    // Se usa cargarTrabajoInicial para que el programa arranque SIN ingresos registrados.
    private static void cargarDatosIniciales(GranjaRenderGT granja) {
        // Horarios que se reutilizan al crear los trabajos de ejemplo.
        Horario diurno = new Diurno();
        Horario nocturno = new Nocturno();

        // Imágenes fijas: una diurna (Q45) y una nocturna con exactamente 2 horas, el límite permitido (Q30).
        granja.cargarTrabajoInicial(new ImagenFija("IMG-001", "Estudio Quetzal", "Portada de libro", 1.5, diurno));
        granja.cargarTrabajoInicial(new ImagenFija("IMG-002", "Pixel Maya", "Afiche de festival", 2.0, nocturno));

        // Animaciones:
        // ANI-001: 9.5 horas estimadas y 610 fotogramas -> horas por fotogramas 12.2 (superan las estimadas),
        // se facturan 13 horas: Q60 + Q195 = Q255 (el ejemplo del enunciado).
        granja.cargarTrabajoInicial(new Animacion("ANI-001", "Volcán Films", "Corto El Cadejo", 9.5, diurno, 610));
        // ANI-002: 20 horas estimadas y 300 fotogramas -> las horas estimadas superan a las de fotogramas (6),
        // se facturan 20 horas en horario nocturno: Q40 + Q300 = Q340.
        granja.cargarTrabajoInicial(new Animacion("ANI-002", "Atitlán Studio", "Spot publicitario", 20.0, nocturno, 300));

        // Animaciones con postproducción:
        // POS-001: mismos datos que ANI-001 y recursos por Q1,800 -> Q255 + Q80 + Q90 = Q425.
        granja.cargarTrabajoInicial(new AnimacionPostproduccion("POS-001", "Volcán Films", "Corto El Cadejo (final)",
                9.5, diurno, 610, 1800.0));
        // POS-002: horario nocturno, 4 horas estimadas y 100 fotogramas (2 horas) -> 4 horas facturables,
        // Q40 + Q60 + Q80 + 5 % de Q2,500 (Q125) = Q305.
        granja.cargarTrabajoInicial(new AnimacionPostproduccion("POS-002", "Pixel Maya", "Videoclip",
                4.0, nocturno, 100, 2500.0));

        // Simulaciones físicas (solo nocturnas):
        // SIM-001: 6 horas, malla de 20 millones -> Q40 + Q132 + Q65 = Q237.
        granja.cargarTrabajoInicial(new SimulacionFisica("SIM-001", "Atitlán Studio", "Simulación de humo", 6.0,
                nocturno, 20));
        // SIM-002: 10 horas, malla de 50 millones (el máximo) -> Q40 + Q220 + Q65 = Q325.
        granja.cargarTrabajoInicial(new SimulacionFisica("SIM-002", "Estudio Quetzal", "Simulación de tela", 10.0,
                nocturno, 50));

        // Nodos: dos sin GPU (de distinta capacidad) y uno con GPU.
        granja.registrarNodo(new Nodo("NODO-01", 24.0, false));
        granja.registrarNodo(new Nodo("NODO-02", 40.0, true));
        granja.registrarNodo(new Nodo("NODO-03", 10.0, false));
    }
}