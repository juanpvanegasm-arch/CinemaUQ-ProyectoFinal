import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AsientoTest {

    private Asiento asiento;

    @BeforeEach
    public void preparar() {
        asiento = new Asiento("A", 1);
    }

    @Test
    public void elCodigoSeFormaConFilaYNumero() {
        assertEquals("A1", asiento.getCodigo());
        assertEquals("A", asiento.getFila());
        assertEquals(1, asiento.getNumero());
    }

    @Test
    public void asientoNuevoEstaDisponible() {
        assertTrue(asiento.estaDisponible());
        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstado());
    }

    @Test
    public void ocuparCambiaElEstadoAOcupado() {
        asiento.ocupar();
        assertFalse(asiento.estaDisponible());
        assertEquals(EstadoAsiento.OCUPADO, asiento.getEstado());
    }

    @Test
    public void liberarDejaElAsientoDisponible() {
        asiento.ocupar();
        asiento.liberar();
        assertTrue(asiento.estaDisponible());
    }

    @Test
    public void informacionMuestraCodigoYEstado() {
        assertEquals("A1 Disponible", asiento.obtenerInformacion());
        asiento.ocupar();
        assertEquals("A1 Ocupado", asiento.obtenerInformacion());
    }
}
