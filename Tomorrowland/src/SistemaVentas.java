import java.util.Random;
public class SistemaVentas {
    private Localidad[] localidades;
    private Random aleatorio;

    public SistemaVentas() {
        this.aleatorio = new Random();
        this.localidades = new Localidad[3];
        
        this.localidades[0] = new Localidad("Localidad 1", 100);
        this.localidades[1] = new Localidad("Localidad 5", 500);
        this.localidades[2] = new Localidad("Localidad 10", 1000);
    }

    public boolean ticketApto(int ticket) {
        int a = aleatorio.nextInt(15000) + 1;
        int b = aleatorio.nextInt(15000) + 1;

        int min = Math.min(a, b);
        int max = Math.max(a, b);

        if (ticket >= min) {
            if (ticket <= max) {
                return true;
            }
        }
        return false;
    }

    public String procesarSolicitud(Comprador comprador) {
        int ticket = aleatorio.nextInt(15000) + 1;

        if (ticketApto(ticket) == false) {
            return "El ticket " + ticket + " no fue apto en el sorteo.";
        }

        int numRandom = aleatorio.nextInt(3);
        Localidad localidadElegida = localidades[numRandom];

        if (localidadElegida.hayEspacio() == false) {
            return "No hay espacio en la " + localidadElegida.getNombreLocalidad();
        }

        int boletosAComprar = comprador.getCantidadBoletos();
        if (boletosAComprar > localidadElegida.getBoletosDisponibles()) {
            boletosAComprar = localidadElegida.getBoletosDisponibles();
        }

        int total = boletosAComprar * localidadElegida.getPrecio();

        if (total > comprador.getPresupuesto()) {
            return "Presupuesto insuficiente para la " + localidadElegida.getNombreLocalidad();
        }

        localidadElegida.venderBoletos(boletosAComprar);
        return "¡Venta exitosa en " + localidadElegida.getNombreLocalidad() + "! Boletos: " + boletosAComprar + ". Total: $" + total;
    }

    public String consultarDisponibilidad() {
        String info = "";
        for (int i = 0; i < 3; i++) {
            info = info + localidades[i].getNombreLocalidad() + " -> Disponibles: " + localidades[i].getBoletosDisponibles() + "\n";
        }
        return info;
    }

    public String reporteCaja() {
        int total = 0;
        for (int i = 0; i < 3; i++) {
            total = total + localidades[i].getRecaudacion();
        }
        return "Total en caja: $" + total;
    }
}