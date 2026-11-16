import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FuncionTest {

    private Sala sala;
    private Funcion funcion;

    @BeforeEach
    public void preparar() {
        Pelicula pelicula = new Pelicula("P01", "Interestelar", "Ciencia ficción", 169, Clasificacion.MAYORES_12);
        sala = new Sala("S1", "Sala 1", 20);
        funcion = new Funcion("F1", pelicula, sala, LocalDate.of(2026, 12, 1), LocalTime.of(18, 0), 15000);
    }

    @Test
    public void funcionNuevaEstaProgramada() {
        assertTrue(funcion.estaProgramada());
        assertEquals(EstadoFuncion.PROGRAMADA, funcion.getEstado());
    }

    @Test
    public void copiaLosAsientosDeLaSala() {
        assertEquals(20, funcion.contarTotal());
        assertEquals(20, funcion.contarDisponibles());
        assertEquals(0, funcion.contarOcupados());
    }

    @Test
    public void ocuparAsientosActualizaLosContadores() {
        funcion.getAsientos().get(0).ocupar();
        funcion.getAsientos().get(1).ocupar();
        assertEquals(18, funcion.contarDisponibles());
        assertEquals(2, funcion.contarOcupados());
        assertTrue(funcion.hayDisponibles());
    }

    @Test
    public void ocuparEnLaFuncionNoAfectaLosAsientosDeLaSala() {
        funcion.getAsientos().get(0).ocupar();
        assertTrue(sala.getAsientos().get(0).estaDisponible());
    }

    @Test
    public void buscarIndiceDeAsientoExistenteEInexistente() {
        assertEquals(0, funcion.buscarIndiceAsiento("A1"));
        assertEquals(0, funcion.buscarIndiceAsiento("a1"));
        assertEquals(10, funcion.buscarIndiceAsiento("B1"));
        assertEquals(-1, funcion.buscarIndiceAsiento("Z99"));
    }

    @Test
    public void sinAsientosLibresNoHayDisponibles() {
        for (int i = 0; i < funcion.getAsientos().size(); i++) {
            funcion.getAsientos().get(i).ocupar();
        }
        assertFalse(funcion.hayDisponibles());
    }

    @Test
    public void cancelarYFinalizarSoloAplicanSiEstaProgramada() {
        funcion.cancelar();
        assertEquals(EstadoFuncion.CANCELADA, funcion.getEstado());
        funcion.finalizar();
        assertEquals(EstadoFuncion.CANCELADA, funcion.getEstado());
    }
}
