public class Pal {

    private String nombre;
    private String tipo;
    private int ataque;
    private int defensa;
    private ArmamentoEspecial armamentoEspecial;

    private int bonoAtaque;
    private int bonoDefensa;

    public Pal() {
        bonoAtaque = 0;
        bonoDefensa = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public ArmamentoEspecial getArmamentoEspecial() {
        return armamentoEspecial;
    }

    public void setArmamentoEspecial(
            ArmamentoEspecial armamentoEspecial) {

        this.armamentoEspecial = armamentoEspecial;
    }

    public int getBonoAtaque() {
        return bonoAtaque;
    }

    public void setBonoAtaque(int bonoAtaque) {
        this.bonoAtaque = bonoAtaque;
    }

    public int getBonoDefensa() {
        return bonoDefensa;
    }

    public void setBonoDefensa(int bonoDefensa) {
        this.bonoDefensa = bonoDefensa;
    }

    public int calcularAtaque(Pal rival) {

        int ataqueTotal = ataque + bonoAtaque;

        if (tipo.equalsIgnoreCase("Fuego")
                && rival.getTipo().equalsIgnoreCase("Planta")) {

            ataqueTotal = ataqueTotal + 20;

        } else if (tipo.equalsIgnoreCase("Planta")
                && rival.getTipo().equalsIgnoreCase("Agua")) {

            ataqueTotal = ataqueTotal + 20;

        } else if (tipo.equalsIgnoreCase("Agua")
                && rival.getTipo().equalsIgnoreCase("Fuego")) {

            ataqueTotal = ataqueTotal + 20;

        } else if (tipo.equalsIgnoreCase("Electrico")
                && rival.getTipo().equalsIgnoreCase("Agua")) {

            ataqueTotal = ataqueTotal + 20;

        } else if (tipo.equalsIgnoreCase("Planta")
                && rival.getTipo().equalsIgnoreCase("Fuego")) {

            ataqueTotal = ataqueTotal - 10;

        } else if (tipo.equalsIgnoreCase("Agua")
                && rival.getTipo().equalsIgnoreCase("Planta")) {

            ataqueTotal = ataqueTotal - 10;

        } else if (tipo.equalsIgnoreCase("Fuego")
                && rival.getTipo().equalsIgnoreCase("Agua")) {

            ataqueTotal = ataqueTotal - 10;

        } else if (tipo.equalsIgnoreCase("Agua")
                && rival.getTipo().equalsIgnoreCase("Electrico")) {

            ataqueTotal = ataqueTotal - 10;
        }

        return ataqueTotal;
    }

    public void usarArmamento(Pal rival) {

        if (armamentoEspecial != null) {

            boolean seActiva = armamentoEspecial.activar();

            if (seActiva) {
                armamentoEspecial.aplicarEfecto(this, rival);
            }
        }
    }
}