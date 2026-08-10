public class ArmamentoEspecial {

    private String nombre;
    private String efecto;
    private int valor;
    private int probabilidad;

    public ArmamentoEspecial() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEfecto() {
        return efecto;
    }

    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(int probabilidad) {
        this.probabilidad = probabilidad;
    }

    public boolean activar() {

        int numero = (int) (Math.random() * 101);

        if (numero <= probabilidad) {
            return true;
        } else {
            return false;
        }
    }

    public void aplicarEfecto(Pal usuario, Pal rival) {

        if (efecto.equalsIgnoreCase("ataque")) {

            usuario.setBonoAtaque(
                usuario.getBonoAtaque() + valor
            );

        } else if (efecto.equalsIgnoreCase("defensa")) {

            usuario.setBonoDefensa(
                usuario.getBonoDefensa() + valor
            );

        } else if (efecto.equalsIgnoreCase("daño")) {

            rival.setBonoAtaque(
                rival.getBonoAtaque() - valor
            );
        }
    }
}