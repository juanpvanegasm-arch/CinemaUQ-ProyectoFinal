import java.util.ArrayList;
import java.util.List;

public class Empleado extends Persona {
    private String codigoEmpleado;
    private List<Venta> ventas;

    public Empleado(String identificacion, String nombre, String codigoEmpleado) {
        super(identificacion, nombre);
        this.codigoEmpleado = codigoEmpleado;
        this.ventas = new ArrayList<Venta>();
    }

    public void agregarVenta(Venta venta) {
        ventas.add(venta);
    }

    public List<Venta> consultarVentas() {
        return ventas;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public String obtenerInformacion() {
        return super.obtenerInformacion() + " | Código de empleado: " + codigoEmpleado
                + " | Ventas registradas: " + ventas.size();
    }
}
