import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntradaTest {

    @Test
    public void entradaGeneralPagaElValorBaseCompleto() {
        Entrada entrada = new EntradaGeneral();
        assertEquals(15000, entrada.calcularValor(15000), 0.001);
    }

    @Test
    public void entradaEstudiantePaga80Porciento() {
        Entrada entrada = new EntradaEstudiante();
        assertEquals(12000, entrada.calcularValor(15000), 0.001);
    }

    @Test
    public void entradaNinoPaga67Porciento() {
        Entrada entrada = new EntradaNino();
        assertEquals(10050, entrada.calcularValor(15000), 0.001);
    }

    @Test
    public void entradaAdultoMayorPaga73Porciento() {
        Entrada entrada = new EntradaAdultoMayor();
        assertEquals(10950, entrada.calcularValor(15000), 0.001);
    }

    @Test
    public void cambiarElPorcentajeCambiaElValor() {
        Entrada entrada = new EntradaEstudiante();
        entrada.setPorcentaje(50);
        assertEquals(50, entrada.getPorcentaje(), 0.001);
        assertEquals(7500, entrada.calcularValor(15000), 0.001);
    }

    @Test
    public void cadaTipoTieneSuNombre() {
        Entrada[] tipos = {new EntradaGeneral(), new EntradaEstudiante(),
                new EntradaNino(), new EntradaAdultoMayor()};
        assertEquals("General", tipos[0].getNombreTipo());
        assertEquals("Estudiante", tipos[1].getNombreTipo());
        assertEquals("Niño", tipos[2].getNombreTipo());
        assertEquals("Adulto mayor", tipos[3].getNombreTipo());
    }
}
