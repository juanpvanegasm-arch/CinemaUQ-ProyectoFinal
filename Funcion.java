import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Funcion {
    private String codigo;
    private Pelicula pelicula;
    private Sala sala;
    private LocalDate fecha;
    private LocalTime hora;
    private double valorBase;
    private EstadoFuncion estado;
    // Cada función tiene su propia copia de los asientos de la sala,
    // porque la ocupación depende de la función y no de la sala.
    private List<Asiento> asientos;

    public Funcion(String codigo, Pelicula pelicula, Sala sala,
                   LocalDate fecha, LocalTime hora, double valorBase) {
        this.codigo = codigo;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fecha = fecha;
        this.hora = hora;
        this.valorBase = valorBase;
        this.estado = EstadoFuncion.PROGRAMADA;
        this.asientos = new ArrayList<Asiento>();
        List<Asiento> asientosSala = sala.getAsientos();
        for (int i = 0; i < asientosSala.size(); i++) {
            Asiento modelo = asientosSala.get(i);
            asientos.add(new Asiento(modelo.getFila(), modelo.getNumero()));
        }
    }

    public int contarTotal() {
        return asientos.size();
    }

    public int contarDisponibles() {
        int cantidad = 0;
        for (int i = 0; i < asientos.size(); i++) {
            if (asientos.get(i).estaDisponible()) {
                cantidad = cantidad + 1;
            }
        }
        return cantidad;
    }

    public int contarOcupados() {
        return contarTotal() - contarDisponibles();
    }

    public boolean hayDisponibles() {
        return contarDisponibles() > 0;
    }

    // Devuelve la posición del asiento en la lista, o -1 si no existe
    public int buscarIndiceAsiento(String codigoAsiento) {
        int posicion = -1;
        for (int i = 0; i < asientos.size(); i++) {
            if (asientos.get(i).getCodigo().equalsIgnoreCase(codigoAsiento)) {
                posicion = i;
            }
        }
        return posicion;
    }

    public boolean estaProgramada() {
        return estado == EstadoFuncion.PROGRAMADA;
    }

    public void finalizar() {
        if (estado == EstadoFuncion.PROGRAMADA) {
            estado = EstadoFuncion.FINALIZADA;
        }
    }

    public void cancelar() {
        if (estado == EstadoFuncion.PROGRAMADA) {
            estado = EstadoFuncion.CANCELADA;
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public double getValorBase() {
        return valorBase;
    }

    public EstadoFuncion getEstado() {
        return estado;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public String obtenerInformacion() {
        return "[" + codigo + "] " + pelicula.getNombre()
                + " | Sala: " + sala.getNombre()
                + " | Fecha: " + fecha
                + " | Hora: " + hora
                + " | Valor: $" + Math.round(valorBase)
                + " | Asientos disponibles: " + contarDisponibles()
                + " | Estado: " + estado;
    }
}
