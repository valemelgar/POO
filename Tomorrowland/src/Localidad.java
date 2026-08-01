public class Localidad{
    private String nombreLocalidad;
    private int precio;
    private int capacidadMax;
    private int boletosVendidos;

    public Localidad(String nombreLocalidad, int precio){
        this.nombreLocalidad = nombreLocalidad;
        this.precio = precio;
        this.capacidadMax = 20;
        this.boletosVendidos = 0;
    }

    public boolean hayEspacio(){
        return boletosVendidos < capacidadMax;
    }

    public int getBoletosDisponibles(){
        return capacidadMax - boletosVendidos;
    }

    public void venderBoletos(int cantidad){
        this.boletosVendidos = this.boletosVendidos + cantidad;
    }

    public int getRecaudacion(){
        return boletosVendidos * precio;
    }

    public int getPrecio(){
        return precio;
    }

    public String getNombreLocalidad(){
        return nombreLocalidad;
    } 
}