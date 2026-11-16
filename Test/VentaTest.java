import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VentaTest {

    private Asiento asiento;
    private Venta venta;

    @BeforeEach
    public void preparar() {
        Pelicula pelicula = new Pelicula("P01", "Interestelar", "Ciencia ficción", 169, Clasificacion.MAYORES_12);
        Sala sala = new Sala("S1", "Sala 1", 10);
        Funcion funcion = new Funcion("F1", pelicula, sala, LocalDate.of(2026, 12, 1), LocalTime.of(18, 0), 15000);
        Cliente cliente = new Cliente("100", "Ana", TipoCliente.ESTUDIANTE);
        Empleado empleado = new Empleado("1010", "Carlos", "E01");
        asiento = funcion.getAsientos().get(0);
        asiento.ocupar();
        venta = new Venta("V1", cliente, empleado, funcion, asiento, new EntradaEstudiante(), 12000);
    }

    @Test
    public void ventaNuevaEstaActiva() {
        assertTrue(venta.estaActiva());
        assertEquals(EstadoVenta.ACTIVA, venta.getEstado());
    }

    @Test
    public void guardaElValorPagadoYElTipoDeEntrada() {
        assertEquals(12000, venta.getValorPagado(), 0.001);
        assertEquals("Estudiante", venta.getEntrada().getNombreTipo());
    }

    @Test
    public void cancelarCambiaElEstadoYLiberaElAsiento() {
        venta.cancelar();
        assertFalse(venta.estaActiva());
        assertEquals(EstadoVenta.CANCELADA, venta.getEstado());
        assertTrue(asiento.estaDisponible());
    }

    @Test
    public void cancelarDosVecesNoLiberaDeNuevoElAsiento() {
        venta.cancelar();
        asiento.ocupar();
        venta.cancelar();
        assertFalse(asiento.estaDisponible());
    }

    @Test
    public void laEntradaGeneradaContieneLosDatosDeLaVenta() {
        String entrada = venta.obtenerInformacionEntrada();
        assertTrue(entrada.contains("V1"));
        assertTrue(entrada.contains("Interestelar"));
        assertTrue(entrada.contains("A1"));
        assertTrue(entrada.contains("Ana"));
        assertTrue(entrada.contains("Carlos"));
        assertTrue(entrada.contains("12000"));
    }

    @Test
    public void elResumenContieneCodigoYEstado() {
        String resumen = venta.obtenerResumen();
        assertTrue(resumen.contains("V1"));
        assertTrue(resumen.contains("ACTIVA"));
    }
}
