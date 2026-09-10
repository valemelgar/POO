package gt.edu.uvg.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Tarifa {
    private int idTarifa;
    private BigDecimal precioBase;
    private BigDecimal montoConductor;
    private BigDecimal montoPlataforma;
    private BigDecimal recargoDinamico;
    private BigDecimal precioTotal;
    private String motivoVariacion;

    public Tarifa(int idTarifa, BigDecimal precioBase, BigDecimal recargoDinamico, BigDecimal precioTotal, String motivoVariacion) {
        this.idTarifa = idTarifa;
        this.precioBase = precioBase;
        this.recargoDinamico = recargoDinamico;
        this.precioTotal = precioTotal.setScale(2, RoundingMode.HALF_UP);
        this.montoConductor = this.precioTotal.multiply(new BigDecimal("0.85")).setScale(2, RoundingMode.HALF_UP);
        this.montoPlataforma = this.precioTotal.subtract(this.montoConductor);
        this.motivoVariacion = motivoVariacion;
    }

    public int getIdTarifa() { return idTarifa; }
    public void setIdTarifa(int idTarifa) { this.idTarifa = idTarifa; }
    public BigDecimal getPrecioBase() { return precioBase; }
    public BigDecimal getMontoConductor() { return montoConductor; }
    public BigDecimal getMontoPlataforma() { return montoPlataforma; }
    public BigDecimal getRecargoDinamico() { return recargoDinamico; }
    public BigDecimal getPrecioTotal() { return precioTotal; }
    public String getMotivoVariacion() { return motivoVariacion; }
}
