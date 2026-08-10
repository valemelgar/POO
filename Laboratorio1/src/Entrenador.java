public class Entrenador {

    private String nombre;

    private Pal pal1;
    private Pal pal2;
    private Pal pal3;

    private int rondasGanadas;

    public Entrenador() {
        rondasGanadas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pal getPal1() {
        return pal1;
    }

    public void setPal1(Pal pal1) {
        this.pal1 = pal1;
    }

    public Pal getPal2() {
        return pal2;
    }

    public void setPal2(Pal pal2) {
        this.pal2 = pal2;
    }

    public Pal getPal3() {
        return pal3;
    }

    public void setPal3(Pal pal3) {
        this.pal3 = pal3;
    }

    public int getRondasGanadas() {
        return rondasGanadas;
    }

    public void setRondasGanadas(int rondasGanadas) {
        this.rondasGanadas = rondasGanadas;
    }

    public Pal seleccionarPal(int opcion) {

        if (opcion == 1) {
            return pal1;

        } else if (opcion == 2) {
            return pal2;

        } else if (opcion == 3) {
            return pal3;
        }

        return null;
    }

    public void sumarVictoria() {
        rondasGanadas++;
    }
}