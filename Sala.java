import java.util.ArrayList;
import java.util.List;

public class Sala {
    private String codigo;
    private String nombre;
    private int capacidad;
    private EstadoSala estado;
    private List<Asiento> asientos;

    public Sala(String codigo, String nombre, int capacidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = EstadoSala.DISPONIBLE;
        this.asientos = new ArrayList<Asiento>();
        generarAsientos();
    }

    // Crea los asientos de la sala: filas A, B, C... con 10 asientos por fila
    private void generarAsientos() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        asientos = new ArrayList<Asiento>();
        for (int i = 0; i < capacidad; i++) {
            int posicionFila = i / 10;
            String fila = letras.substring(posicionFila, posicionFila + 1);
            int numero = (i % 10) + 1;
            asientos.add(new Asiento(fila, numero));
        }
    }

    public boolean estaDisponible() {
        return estado == EstadoSala.DISPONIBLE;
    }

    public void deshabilitar() {
        estado = EstadoSala.FUERA_DE_SERVICIO;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    // Al cambiar la capacidad se vuelven a crear los asientos de la sala
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
        generarAsientos();
    }

    public EstadoSala getEstado() {
        return estado;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public String obtenerInformacion() {
        return "[" + codigo + "] " + nombre + " | Capacidad: " + capacidad
                + " asientos | Estado: " + estado;
    }
}
