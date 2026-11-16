import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmpleadoTest {

    private Empleado empleado;
    private Venta venta;

    @BeforeEach
    public void preparar() {
        empleado = new Empleado("1010", "Carlos Ruiz", "E01");
        Pelicula pelicula = new Pelicula("P01", "Interestelar", "Ciencia ficción", 169, Clasificacion.MAYORES_12);
        Sala sala = new Sala("S1", "Sala 1", 10);
        Funcion funcion = new Funcion("F1", pelicula, sala, LocalDate.of(2026, 12, 1), LocalTime.of(18, 0), 15000);
        Cliente cliente = new Cliente("100", "Ana", TipoCliente.GENERAL);
        venta = new Venta("V1", cliente, empleado, funcion, funcion.getAsientos().get(0), new EntradaGeneral(), 15000);
    }

    @Test
    public void guardaSuCodigoYLosDatosDePersona() {
        assertEquals("E01", empleado.getCodigoEmpleado());
        assertEquals("1010", empleado.getIdentificacion());
        assertEquals("Carlos Ruiz", empleado.getNombre());
    }

    @Test
    public void empleadoNuevoNoTieneVentas() {
        assertEquals(0, empleado.consultarVentas().size());
    }

    @Test
    public void agregarVentaLaGuardaEnSuLista() {
        empleado.agregarVenta(venta);
        assertEquals(1, empleado.consultarVentas().size());
        assertEquals("V1", empleado.consultarVentas().get(0).getCodigo());
    }

    @Test
    public void informacionMuestraCodigoYCantidadDeVentas() {
        empleado.agregarVenta(venta);
        String info = empleado.obtenerInformacion();
        assertTrue(info.contains("E01"));
        assertTrue(info.contains("Ventas registradas: 1"));
    }

    @Test
    public void unEmpleadoEsUnaPersonaYSeModifica() {
        Persona persona = empleado;
        persona.setNombre("Carlos Andrés Ruiz");
        assertEquals("Carlos Andrés Ruiz", empleado.getNombre());
    }
}
