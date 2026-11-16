import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PeliculaTest {

    private Pelicula pelicula;

    @BeforeEach
    public void preparar() {
        pelicula = new Pelicula("P01", "Interestelar", "Ciencia ficción", 169, Clasificacion.MAYORES_12);
    }

    @Test
    public void peliculaNuevaQuedaDisponible() {
        assertTrue(pelicula.estaDisponible());
        assertEquals(EstadoPelicula.DISPONIBLE, pelicula.getEstado());
    }

    @Test
    public void deshabilitarCambiaElEstado() {
        pelicula.deshabilitar();
        assertFalse(pelicula.estaDisponible());
        assertEquals(EstadoPelicula.NO_DISPONIBLE, pelicula.getEstado());
    }

    @Test
    public void modificarNombreYGenero() {
        pelicula.setNombre("Coco");
        pelicula.setGenero("Animación");
        assertEquals("Coco", pelicula.getNombre());
        assertEquals("Animación", pelicula.getGenero());
    }

    @Test
    public void modificarDuracionYClasificacion() {
        pelicula.setDuracionMinutos(105);
        pelicula.setClasificacion(Clasificacion.TODOS);
        assertEquals(105, pelicula.getDuracionMinutos());
        assertEquals(Clasificacion.TODOS, pelicula.getClasificacion());
    }

    @Test
    public void informacionContieneLosDatosPrincipales() {
        String info = pelicula.obtenerInformacion();
        assertTrue(info.contains("P01"));
        assertTrue(info.contains("Interestelar"));
        assertTrue(info.contains("169"));
    }
}
