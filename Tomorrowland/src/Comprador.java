public class Comprador {
    private String nombre;
    private String email;
    private int cantidadBoletos;
    private int presupuestoMaximo;

    public Comprador (String nombre, String email, int cantidadBoletos, int presupuestoMaximo){
        this.nombre = nombre;
        this.email = email;
        this.cantidadBoletos = cantidadBoletos;
        this.presupuestoMaximo = presupuestoMaximo;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public int getCantidadBoletos(){
        return cantidadBoletos;
    }

    public void setCantidadBoletos(int cantidadBoletos){
        this.cantidadBoletos = cantidadBoletos;
    }

    public int getPresupuesto(){
        return presupuestoMaximo;
    }

    public void setPresupuesto(int presupuestoMaximo){
        this.presupuestoMaximo = presupuestoMaximo;

    }
}