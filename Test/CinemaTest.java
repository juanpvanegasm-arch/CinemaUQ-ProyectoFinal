import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CinemaTest {

    private Cinema cinema;
    private Cliente cliente;
    private Empleado empleado;
    private Entrada general;
    private Entrada estudiante;
    private LocalDate fecha;
    private LocalTime hora;

    @BeforeEach
    public void preparar() throws Exception {
        cinema = new Cinema("CINEMAUQ");
        cinema.registrarPelicula(new Pelicula("P01", "Interestelar", "Ciencia ficción", 169, Clasificacion.MAYORES_12));
        cinema.registrarPelicula(new Pelicula("P02", "Coco", "Animación", 105, Clasificacion.TODOS));
        cinema.registrarSala(new Sala("S1", "Sala 1", 10));
        cliente = new Cliente("100", "Ana", TipoCliente.ESTUDIANTE);
        cinema.registrarCliente(cliente);
        empleado = new Empleado("1010", "Carlos", "E01");
        cinema.registrarEmpleado(empleado);
        general = cinema.getEntradas().get(0);
        estudiante = cinema.getEntradas().get(1);
        fecha = LocalDate.of(2026, 12, 1);
        hora = LocalTime.of(18, 0);
        cinema.programarFuncion("F1", "P01", "S1", fecha, hora, 15000);
    }

    // ---------------- Registros y códigos duplicados ----------------

    @Test
    public void noPermiteDosPeliculasConElMismoCodigo() {
        try {
            cinema.registrarPelicula(new Pelicula("P01", "Otra", "Drama", 90, Clasificacion.TODOS));
            fail("Debió lanzar CodigoDuplicadoException");
        } catch (CodigoDuplicadoException e) {
            assertTrue(e.getMessage().contains("P01"));
        }
        assertEquals(2, cinema.getPeliculas().size());
    }

    @Test
    public void noPermiteDosSalasConElMismoCodigo() {
        try {
            cinema.registrarSala(new Sala("S1", "Otra sala", 20));
            fail("Debió lanzar CodigoDuplicadoException");
        } catch (CodigoDuplicadoException e) {
            assertTrue(e.getMessage().contains("S1"));
        }
        assertEquals(1, cinema.getSalas().size());
    }

    @Test
    public void noRegistraClientesNiEmpleadosRepetidos() {
        assertFalse(cinema.registrarCliente(new Cliente("100", "Otro", TipoCliente.GENERAL)));
        assertTrue(cinema.registrarCliente(new Cliente("200", "Luis", TipoCliente.GENERAL)));
        assertFalse(cinema.registrarEmpleado(new Empleado("9999", "Otro", "E01")));
        assertFalse(cinema.registrarEmpleado(new Empleado("1010", "Otro", "E99")));
        assertTrue(cinema.registrarEmpleado(new Empleado("2020", "Laura", "E02")));
    }

    @Test
    public void lasBusquedasDevuelvenMenosUnoSiNoExiste() {
        assertEquals(0, cinema.buscarIndicePelicula("P01"));
        assertEquals(-1, cinema.buscarIndicePelicula("P99"));
        assertEquals(-1, cinema.buscarIndiceSala("S99"));
        assertEquals(-1, cinema.buscarIndiceFuncion("F99"));
        assertEquals(-1, cinema.buscarIndiceVenta("V99"));
        assertEquals(-1, cinema.buscarIndiceCliente("999"));
        assertEquals(-1, cinema.buscarIndiceEmpleado("E99"));
    }

    @Test
    public void eliminarEmpleadoLoQuitaDeLaLista() throws Exception {
        cinema.eliminarEmpleado("E01");
        assertEquals(0, cinema.getEmpleados().size());
        try {
            cinema.eliminarEmpleado("E01");
            fail("Debió lanzar EmpleadoNoEncontradoException");
        } catch (EmpleadoNoEncontradoException e) {
            assertTrue(e.getMessage().contains("E01"));
        }
    }

    // ---------------- Programar funciones ----------------

    @Test
    public void noProgramaFuncionEnSalaFueraDeServicio() throws Exception {
        cinema.registrarSala(new Sala("S2", "Sala 2", 10));
        cinema.obtenerSala("S2").deshabilitar();
        try {
            cinema.programarFuncion("F2", "P01", "S2", fecha, hora, 15000);
            fail("Debió lanzar SalaNoDisponibleException");
        } catch (SalaNoDisponibleException e) {
            assertTrue(e.getMessage().contains("fuera de servicio"));
        }
    }

    @Test
    public void noProgramaFuncionConPeliculaOSalaInexistente() throws Exception {
        try {
            cinema.programarFuncion("F2", "P99", "S1", fecha, hora, 15000);
            fail("Debió lanzar PeliculaNoEncontradaException");
        } catch (PeliculaNoEncontradaException e) {
            assertTrue(e.getMessage().contains("P99"));
        }
        try {
            cinema.programarFuncion("F2", "P01", "S99", fecha, hora, 15000);
            fail("Debió lanzar SalaNoEncontradaException");
        } catch (SalaNoEncontradaException e) {
            assertTrue(e.getMessage().contains("S99"));
        }
    }

    @Test
    public void noProgramaFuncionConPeliculaDeshabilitadaNiCodigoRepetido() throws Exception {
        cinema.obtenerPelicula("P02").deshabilitar();
        try {
            cinema.programarFuncion("F2", "P02", "S1", fecha, LocalTime.of(21, 0), 15000);
            fail("Debió lanzar PeliculaNoEncontradaException");
        } catch (PeliculaNoEncontradaException e) {
            assertTrue(e.getMessage().contains("no está disponible"));
        }
        try {
            cinema.programarFuncion("F1", "P01", "S1", fecha, LocalTime.of(21, 0), 15000);
            fail("Debió lanzar CodigoDuplicadoException");
        } catch (CodigoDuplicadoException e) {
            assertTrue(e.getMessage().contains("F1"));
        }
    }

    @Test
    public void noProgramaDosFuncionesEnLaMismaSalaFechaYHora() throws Exception {
        try {
            cinema.programarFuncion("F2", "P02", "S1", fecha, hora, 12000);
            fail("Debió lanzar SalaNoDisponibleException");
        } catch (SalaNoDisponibleException e) {
            assertTrue(e.getMessage().contains("ya tiene una función"));
        }
        Funcion otra = cinema.programarFuncion("F2", "P02", "S1", fecha, LocalTime.of(21, 0), 12000);
        assertEquals("F2", otra.getCodigo());
        assertEquals(2, cinema.getFunciones().size());
    }

    // ---------------- Venta de entradas ----------------

    @Test
    public void ventaExitosaOcupaElAsientoYCalculaElValor() throws Exception {
        Venta venta = cinema.venderEntrada("P01", "F1", cliente, empleado, estudiante, "A1");
        assertEquals("V1", venta.getCodigo());
        assertEquals(EstadoVenta.ACTIVA, venta.getEstado());
        assertEquals(12000, venta.getValorPagado(), 0.001);
        assertFalse(cinema.obtenerFuncion("F1").getAsientos().get(0).estaDisponible());
        assertEquals(9, cinema.obtenerFuncion("F1").contarDisponibles());
    }

    @Test
    public void laVentaQuedaEnLaListaDelEmpleadoYLosCodigosSonConsecutivos() throws Exception {
        cinema.venderEntrada("P01", "F1", cliente, empleado, general, "A1");
        Venta segunda = cinema.venderEntrada("P01", "F1", cliente, empleado, general, "A2");
        assertEquals("V2", segunda.getCodigo());
        assertEquals(2, empleado.consultarVentas().size());
        assertEquals(2, cinema.getVentas().size());
    }

    @Test
    public void noVendeUnAsientoOcupado() throws Exception {
        cinema.venderEntrada("P01", "F1", cliente, empleado, general, "A1");
        try {
            cinema.venderEntrada("P01", "F1", cliente, empleado, general, "A1");
            fail("Debió lanzar AsientoOcupadoException");
        } catch (AsientoOcupadoException e) {
            assertTrue(e.getMessage().contains("A1"));
        }
        assertEquals(1, cinema.getVentas().size());
    }

    @Test
    public void noVendeParaFuncionInexistenteONoProgramada() throws Exception {
        try {
            cinema.venderEntrada("P01", "F99", cliente, empleado, general, "A1");
            fail("Debió lanzar FuncionNoEncontradaException");
        } catch (FuncionNoEncontradaException e) {
            assertTrue(e.getMessage().contains("F99"));
        }
        cinema.obtenerFuncion("F1").finalizar();
        try {
            cinema.venderEntrada("P01", "F1", cliente, empleado, general, "A1");
            fail("Debió lanzar FuncionNoEncontradaException");
        } catch (FuncionNoEncontradaException e) {
            assertTrue(e.getMessage().contains("no está programada"));
        }
    }

    @Test
    public void noVendeSiLaFuncionNoCorrespondeALaPelicula() throws Exception {
        try {
            cinema.venderEntrada("P02", "F1", cliente, empleado, general, "A1");
            fail("Debió lanzar FuncionNoEncontradaException");
        } catch (FuncionNoEncontradaException e) {
            assertTrue(e.getMessage().contains("no corresponde"));
        }
    }

    @Test
    public void noVendeUnAsientoQueNoExiste() throws Exception {
        try {
            cinema.venderEntrada("P01", "F1", cliente, empleado, general, "Z99");
            fail("Debió lanzar AsientoNoEncontradoException");
        } catch (AsientoNoEncontradoException e) {
            assertTrue(e.getMessage().contains("Z99"));
        }
    }

    @Test
    public void noVendeCuandoNoHayAsientosDisponibles() throws Exception {
        cinema.registrarSala(new Sala("S2", "Sala 2", 1));
        cinema.programarFuncion("F2", "P01", "S2", fecha, hora, 15000);
        cinema.venderEntrada("P01", "F2", cliente, empleado, general, "A1");
        try {
            cinema.venderEntrada("P01", "F2", cliente, empleado, general, "A1");
            fail("Debió lanzar SinAsientosDisponiblesException");
        } catch (SinAsientosDisponiblesException e) {
            assertTrue(e.getMessage().contains("F2"));
        }
    }

    // ---------------- Cancelaciones ----------------

    @Test
    public void cancelarVentaLiberaElAsiento() throws Exception {
        cinema.venderEntrada("P01", "F1", cliente, empleado, general, "A1");
        Venta cancelada = cinema.cancelarVenta("V1");
        assertEquals(EstadoVenta.CANCELADA, cancelada.getEstado());
        assertTrue(cinema.obtenerFuncion("F1").getAsientos().get(0).estaDisponible());
        assertEquals(10, cinema.obtenerFuncion("F1").contarDisponibles());
    }

    @Test
    public void noCancelaUnaVentaInexistenteNiYaCancelada() throws Exception {
        try {
            cinema.cancelarVenta("V99");
            fail("Debió lanzar VentaNoEncontradaException");
        } catch (VentaNoEncontradaException e) {
            assertTrue(e.getMessage().contains("V99"));
        }
        cinema.venderEntrada("P01", "F1", cliente, empleado, general, "A1");
        cinema.cancelarVenta("V1");
        try {
            cinema.cancelarVenta("V1");
            fail("Debió lanzar VentaNoEncontradaException");
        } catch (VentaNoEncontradaException e) {
            assertTrue(e.getMessage().contains("ya estaba cancelada"));
        }
    }

    @Test
    public void cancelarUnaFuncionCancelaSusVentasActivas() throws Exception {
        cinema.venderEntrada("P01", "F1", cliente, empleado, general, "A1");
        cinema.venderEntrada("P01", "F1", cliente, empleado, general, "A2");
        int canceladas = cinema.cancelarFuncion("F1");
        assertEquals(2, canceladas);
        assertEquals(EstadoFuncion.CANCELADA, cinema.obtenerFuncion("F1").getEstado());
        assertEquals(0, cinema.contarEntradasVendidas());
        assertEquals(10, cinema.obtenerFuncion("F1").contarDisponibles());
    }

    // ---------------- Consultas y reportes ----------------

    @Test
    public void calculaElValorDeLaEntradaSegunElTipo() throws Exception {
        Funcion funcion = cinema.obtenerFuncion("F1");
        assertEquals(15000, cinema.calcularValorEntrada(funcion, general), 0.001);
        assertEquals(12000, cinema.calcularValorEntrada(funcion, estudiante), 0.001);
    }

    @Test
    public void totalDeEntradasEIngresosIgnoranLasVentasCanceladas() throws Exception {
        assertFalse(cinema.hayVentas());
        cinema.venderEntrada("P01", "F1", cliente, empleado, estudiante, "A1");
        cinema.venderEntrada("P01", "F1", cliente, empleado, general, "A2");
        assertTrue(cinema.hayVentas());
        assertEquals(2, cinema.contarEntradasVendidas());
        assertEquals(27000, cinema.calcularTotalIngresos(), 0.001);
        cinema.cancelarVenta("V1");
        assertEquals(1, cinema.contarEntradasVendidas());
        assertEquals(15000, cinema.calcularTotalIngresos(), 0.001);
    }

    @Test
    public void identificaLaPeliculaConMasEntradasVendidas() throws Exception {
        cinema.programarFuncion("F2", "P02", "S1", fecha, LocalTime.of(21, 0), 12000);
        cinema.venderEntrada("P01", "F1", cliente, empleado, general, "A1");
        cinema.venderEntrada("P01", "F1", cliente, empleado, general, "A2");
        cinema.venderEntrada("P02", "F2", cliente, empleado, general, "A1");
        assertEquals(2, cinema.contarEntradasPorPelicula("P01"));
        assertEquals(1, cinema.contarEntradasPorPelicula("P02"));
        assertEquals("P01", cinema.obtenerPeliculaMasVendida().getCodigo());
    }

    @Test
    public void sinVentasLaPeliculaMasVendidaEsUnaVacia() {
        assertEquals("SIN-VENTAS", cinema.obtenerPeliculaMasVendida().getCodigo());
    }

    @Test
    public void consultaFuncionesProgramadasPorFecha() throws Exception {
        assertEquals(1, cinema.consultarFuncionesPorFecha(fecha).size());
        assertEquals(0, cinema.consultarFuncionesPorFecha(LocalDate.of(2026, 12, 2)).size());
        cinema.cancelarFuncion("F1");
        assertEquals(0, cinema.consultarFuncionesPorFecha(fecha).size());
    }

    @Test
    public void consultaPeliculasDisponiblesYFuncionesPorPelicula() throws Exception {
        assertEquals(2, cinema.consultarPeliculasDisponibles().size());
        cinema.obtenerPelicula("P02").deshabilitar();
        assertEquals(1, cinema.consultarPeliculasDisponibles().size());
        assertEquals(1, cinema.consultarFuncionesProgramadasPorPelicula("P01").size());
        assertEquals(0, cinema.consultarFuncionesProgramadasPorPelicula("P02").size());
    }

    @Test
    public void tieneLosCuatroTiposDeEntradaIniciales() {
        assertEquals(4, cinema.getEntradas().size());
        assertEquals("General", cinema.getEntradas().get(0).getNombreTipo());
        assertEquals("Adulto mayor", cinema.getEntradas().get(3).getNombreTipo());
    }
}
