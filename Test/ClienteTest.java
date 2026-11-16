import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    public void preparar() {
        cliente = new Cliente("100", "Ana Gómez", TipoCliente.ESTUDIANTE);
    }

    @Test
    public void guardaLosDatosHeredadosDePersona() {
        assertEquals("100", cliente.getIdentificacion());
        assertEquals("Ana Gómez", cliente.getNombre());
    }

    @Test
    public void guardaSuTipoDeCliente() {
        assertEquals(TipoCliente.ESTUDIANTE, cliente.getTipoCliente());
    }

    @Test
    public void unClienteEsUnaPersona() {
        Persona persona = cliente;
        assertEquals("Ana Gómez", persona.getNombre());
    }

    @Test
    public void modificarNombreEIdentificacionHeredados() {
        cliente.setNombre("Ana María Gómez");
        cliente.setIdentificacion("200");
        assertEquals("Ana María Gómez", cliente.getNombre());
        assertEquals("200", cliente.getIdentificacion());
    }

    @Test
    public void informacionIncluyeDatosDePersonaYTipo() {
        String info = cliente.obtenerInformacion();
        assertTrue(info.contains("Ana Gómez"));
        assertTrue(info.contains("100"));
        assertTrue(info.contains("ESTUDIANTE"));
    }
}
