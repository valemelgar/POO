import java.util.ArrayList;
import java.util.List;

// Catálogo de franjas tarifarias. Su única responsabilidad es decir QUÉ franja
// corresponde a una hora del día. Si algún día hay otra franja, solo se agrega aquí.
public class TarifaHoraria {

    // Lista de franjas registradas. Privada para que nadie la modifique desde fuera.
    private ArrayList<FranjaTarifaria> franjas;

    // Constructor: empieza con la lista vacía.
    public TarifaHoraria() {
        this.franjas = new ArrayList<FranjaTarifaria>();
    }

    // Agrega una franja al catálogo.
    public void agregarFranja(FranjaTarifaria franja) {
        if (franja == null) {
            throw new OperacionInvalidaException("La franja no puede ser nula.");
        }
        franjas.add(franja);
    }

    // Busca la franja que contiene la hora del día (0 a 23). Polimorfismo: se llama contiene()
    // sin saber si la franja es valle, normal o punta.
    public FranjaTarifaria obtenerFranja(int horaDelDia) {
        for (FranjaTarifaria franja : franjas) {
            if (franja.contiene(horaDelDia)) {
                return franja; // la primera que la contiene es la que se usa
            }
        }
        // Si ninguna la contiene, la configuración de franjas está incompleta.
        throw new OperacionInvalidaException("No hay franja tarifaria para la hora " + horaDelDia + ".");
    }

    // Devuelve una COPIA de la lista para no exponer la estructura interna.
    public List<FranjaTarifaria> getFranjas() {
        return new ArrayList<FranjaTarifaria>(franjas);
    }
}