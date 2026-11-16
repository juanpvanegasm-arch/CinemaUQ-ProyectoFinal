import java.time.LocalDate;
import java.time.LocalTime;

public class Venta {
    private String codigo;
    private Cliente cliente;
    private Funcion funcion;
    private Asiento asiento;
    private Empleado empleado;
    private Entrada entrada;
    private LocalDate fechaVenta;
    private LocalTime horaVenta;
    private double valorPagado;
    private EstadoVenta estado;

    public Venta(String codigo, Cliente cliente, Empleado empleado, Funcion funcion,
                 Asiento asiento, Entrada entrada, double valorPagado) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.funcion = funcion;
        this.asiento = asiento;
        this.empleado = empleado;
        this.entrada = entrada;
        this.valorPagado = valorPagado;
        this.fechaVenta = LocalDate.now();
        this.horaVenta = LocalTime.now().withNano(0);
        this.estado = EstadoVenta.ACTIVA;
    }

    public boolean estaActiva() {
        return estado == EstadoVenta.ACTIVA;
    }

    // Cancela la venta y libera el asiento de la función
    public void cancelar() {
        if (estado == EstadoVenta.ACTIVA) {
            estado = EstadoVenta.CANCELADA;
            asiento.liberar();
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public LocalTime getHoraVenta() {
        return horaVenta;
    }

    public double getValorPagado() {
        return valorPagado;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    public String obtenerResumen() {
        return "[" + codigo + "] " + cliente.getNombre() + " | Función: " + funcion.getCodigo()
                + " | Asiento: " + asiento.getCodigo()
                + " | Valor: $" + Math.round(valorPagado)
                + " | Estado: " + estado;
    }

    public String obtenerInformacionEntrada() {
        return "========== ENTRADA " + codigo + " ==========\n"
                + "Película: " + funcion.getPelicula().getNombre() + "\n"
                + "Función: " + funcion.getCodigo() + " | Sala: " + funcion.getSala().getNombre() + "\n"
                + "Fecha de la función: " + funcion.getFecha() + " | Hora: " + funcion.getHora() + "\n"
                + "Asiento: " + asiento.getCodigo() + "\n"
                + "Cliente: " + cliente.getNombre() + " (Id: " + cliente.getIdentificacion() + ")\n"
                + "Tipo de entrada: " + entrada.getNombreTipo() + "\n"
                + "Atendido por: " + empleado.getNombre() + " (" + empleado.getCodigoEmpleado() + ")\n"
                + "Valor pagado: $" + Math.round(valorPagado) + "\n"
                + "Venta realizada el " + fechaVenta + " a las " + horaVenta + "\n"
                + "Estado: " + estado + "\n"
                + "=====================================";
    }
}
