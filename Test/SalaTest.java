import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SalaTest {

    @Test
    public void salaNuevaQuedaDisponible() {
        Sala sala = new Sala("S1", "Sala 1", 20);
        assertTrue(sala.estaDisponible());
        assertEquals(EstadoSala.DISPONIBLE, sala.getEstado());
    }

    @Test
    public void generaTantosAsientosComoLaCapacidad() {
        Sala sala = new Sala("S1", "Sala 1", 25);
        assertEquals(25, sala.getAsientos().size());
    }

    @Test
    public void losAsientosSeNombranPorFilaYNumero() {
        Sala sala = new Sala("S1", "Sala 1", 25);
        assertEquals("A1", sala.getAsientos().get(0).getCodigo());
        assertEquals("A10", sala.getAsientos().get(9).getCodigo());
        assertEquals("B1", sala.getAsientos().get(10).getCodigo());
        assertEquals("C5", sala.getAsientos().get(24).getCodigo());
    }

    @Test
    public void deshabilitarLaDejaFueraDeServicio() {
        Sala sala = new Sala("S1", "Sala 1", 20);
        sala.deshabilitar();
        assertFalse(sala.estaDisponible());
        assertEquals(EstadoSala.FUERA_DE_SERVICIO, sala.getEstado());
    }

    @Test
    public void cambiarLaCapacidadRegeneraLosAsientos() {
        Sala sala = new Sala("S1", "Sala 1", 10);
        sala.setCapacidad(15);
        assertEquals(15, sala.getCapacidad());
        assertEquals(15, sala.getAsientos().size());
    }
}
