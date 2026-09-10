package gt.edu.uvg.model;

import java.math.BigDecimal;

public class Plan {
    private int idPlan;
    private String nombre;
    private BigDecimal precio;
    private int viajesIncluidos;
    private int duracionDias;
    private BigDecimal precioViajeExtra;
    private String descripcion;

    public Plan(int idPlan, String nombre, BigDecimal precio, int viajesIncluidos, int duracionDias, BigDecimal precioViajeExtra, String descripcion) {
        this.idPlan = idPlan;
        this.nombre = nombre;
        this.precio = precio;
        this.viajesIncluidos = viajesIncluidos;
        this.duracionDias = duracionDias;
        this.precioViajeExtra = precioViajeExtra;
        this.descripcion = descripcion;
    }

    public int getIdPlan() { return idPlan; }
    public String getNombre() { return nombre; }
    public BigDecimal getPrecio() { return precio; }
    public int getViajesIncluidos() { return viajesIncluidos; }
    public int getDuracionDias() { return duracionDias; }
    public BigDecimal getPrecioViajeExtra() { return precioViajeExtra; }
    public String getDescripcion() { return descripcion; }
}
