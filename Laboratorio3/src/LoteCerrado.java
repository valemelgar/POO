// Estado "Cerrado": todos los trabajos del lote ya tienen resultado y el nodo quedó libre.
// Es un estado final: no sobrescribe transiciones, así que cualquier operación se rechaza.
public class LoteCerrado extends EstadoLote {

    // Constructor: solo llama al de la clase padre.
    public LoteCerrado() {
        super();
    }

    // Nombre que se muestra para este estado.
    @Override
    public String getNombre() {
        return "Cerrado";
    }

    // Sobrescribe estaCerrado: en este estado el lote sí está cerrado.
    @Override
    public boolean estaCerrado() {
        return true;
    }
}