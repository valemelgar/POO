public class Combate {

    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private int rondaActual;

    public Combate() {
        rondaActual = 1;
    }

    public Entrenador getEntrenador1() {
        return entrenador1;
    }

    public void setEntrenador1(Entrenador entrenador1) {
        this.entrenador1 = entrenador1;
    }

    public Entrenador getEntrenador2() {
        return entrenador2;
    }

    public void setEntrenador2(Entrenador entrenador2) {
        this.entrenador2 = entrenador2;
    }

    public int getRondaActual() {
        return rondaActual;
    }

    public void setRondaActual(int rondaActual) {
        this.rondaActual = rondaActual;
    }

    //**
	// Devuelve:
	// 1 si gana entrenador 1
	// 2 si gana entrenador 2
	// // 0 si hay empate  */
	
    public int jugarRonda(Pal pal1, Pal pal2) {

        int ataque1 = pal1.calcularAtaque(pal2);
        int ataque2 = pal2.calcularAtaque(pal1);

        if (ataque1 > ataque2) {

            entrenador1.sumarVictoria();
            return 1;

        } else if (ataque2 > ataque1) {

            entrenador2.sumarVictoria();
            return 2;

        } else {

            return 0;
        }
    }

    public int calcularVentajaTipo(
            Pal atacante, Pal defensor) {

        String tipoAtacante = atacante.getTipo();
        String tipoDefensor = defensor.getTipo();

        if (tipoAtacante.equalsIgnoreCase("Fuego")
                && tipoDefensor.equalsIgnoreCase("Planta")) {

            return 20;

        } else if (tipoAtacante.equalsIgnoreCase("Planta")
                && tipoDefensor.equalsIgnoreCase("Agua")) {

            return 20;

        } else if (tipoAtacante.equalsIgnoreCase("Agua")
                && tipoDefensor.equalsIgnoreCase("Fuego")) {

            return 20;

        } else if (tipoAtacante.equalsIgnoreCase("Electrico")
                && tipoDefensor.equalsIgnoreCase("Agua")) {

            return 20;

        } else if (tipoAtacante.equalsIgnoreCase("Planta")
                && tipoDefensor.equalsIgnoreCase("Fuego")) {

            return -10;

        } else if (tipoAtacante.equalsIgnoreCase("Agua")
                && tipoDefensor.equalsIgnoreCase("Planta")) {

            return -10;

        } else if (tipoAtacante.equalsIgnoreCase("Fuego")
                && tipoDefensor.equalsIgnoreCase("Agua")) {

            return -10;

        } else if (tipoAtacante.equalsIgnoreCase("Agua")
                && tipoDefensor.equalsIgnoreCase("Electrico")) {

            return -10;
        }

        return 0;
    }

    public int mostrarResultadoFinal() {

        if (entrenador1.getRondasGanadas()
                > entrenador2.getRondasGanadas()) {

            return 1;

        } else if (entrenador2.getRondasGanadas()
                > entrenador1.getRondasGanadas()) {

            return 2;

        } else {

            return 0;
        }
    }
}