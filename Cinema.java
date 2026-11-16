import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private String nombre;
    private List<Pelicula> peliculas;
    private List<Sala> salas;
    private List<Funcion> funciones;
    private List<Cliente> clientes;
    private List<Venta> ventas;
    private List<Empleado> empleados;
    private List<Entrada> entradas;
    private int consecutivoVentas;

    public Cinema(String nombre) {
        this.nombre = nombre;
        this.peliculas = new ArrayList<Pelicula>();
        this.salas = new ArrayList<Sala>();
        this.funciones = new ArrayList<Funcion>();
        this.clientes = new ArrayList<Cliente>();
        this.ventas = new ArrayList<Venta>();
        this.empleados = new ArrayList<Empleado>();
        this.entradas = new ArrayList<Entrada>();
        this.consecutivoVentas = 0;
        // Tipos de entrada disponibles (cada uno con su porcentaje del valor base)
        entradas.add(new EntradaGeneral());
        entradas.add(new EntradaEstudiante());
        entradas.add(new EntradaNino());
        entradas.add(new EntradaAdultoMayor());
    }

    // ------------------------------------------------------------------
    // Búsquedas: devuelven la posición en la lista, o -1 si no existe
    // ------------------------------------------------------------------
    public int buscarIndicePelicula(String codigo) {
        int posicion = -1;
        for (int i = 0; i < peliculas.size(); i++) {
            if (peliculas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                posicion = i;
            }
        }
        return posicion;
    }

    public int buscarIndiceSala(String codigo) {
        int posicion = -1;
        for (int i = 0; i < salas.size(); i++) {
            if (salas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                posicion = i;
            }
        }
        return posicion;
    }

    public int buscarIndiceFuncion(String codigo) {
        int posicion = -1;
        for (int i = 0; i < funciones.size(); i++) {
            if (funciones.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                posicion = i;
            }
        }
        return posicion;
    }

    public int buscarIndiceVenta(String codigo) {
        int posicion = -1;
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                posicion = i;
            }
        }
        return posicion;
    }

    public int buscarIndiceCliente(String identificacion) {
        int posicion = -1;
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getIdentificacion().equals(identificacion)) {
                posicion = i;
            }
        }
        return posicion;
    }

    // Los empleados se buscan por su código de empleado
    public int buscarIndiceEmpleado(String codigoEmpleado) {
        int posicion = -1;
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getCodigoEmpleado().equalsIgnoreCase(codigoEmpleado)) {
                posicion = i;
            }
        }
        return posicion;
    }

    public boolean existeEmpleadoConIdentificacion(String identificacion) {
        boolean existe = false;
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getIdentificacion().equals(identificacion)) {
                existe = true;
            }
        }
        return existe;
    }

    // ------------------------------------------------------------------
    // Obtener un objeto por su código (lanza excepción si no existe)
    // ------------------------------------------------------------------
    public Pelicula obtenerPelicula(String codigo) throws PeliculaNoEncontradaException {
        int posicion = buscarIndicePelicula(codigo);
        if (posicion == -1) {
            throw new PeliculaNoEncontradaException("No existe una película con el código " + codigo);
        }
        return peliculas.get(posicion);
    }

    public Sala obtenerSala(String codigo) throws SalaNoEncontradaException {
        int posicion = buscarIndiceSala(codigo);
        if (posicion == -1) {
            throw new SalaNoEncontradaException("No existe una sala con el código " + codigo);
        }
        return salas.get(posicion);
    }

    public Funcion obtenerFuncion(String codigo) throws FuncionNoEncontradaException {
        int posicion = buscarIndiceFuncion(codigo);
        if (posicion == -1) {
            throw new FuncionNoEncontradaException("No existe una función con el código " + codigo);
        }
        return funciones.get(posicion);
    }

    public Venta obtenerVenta(String codigo) throws VentaNoEncontradaException {
        int posicion = buscarIndiceVenta(codigo);
        if (posicion == -1) {
            throw new VentaNoEncontradaException("No existe una venta con el código " + codigo);
        }
        return ventas.get(posicion);
    }

    // ------------------------------------------------------------------
    // Registros
    // ------------------------------------------------------------------
    public void registrarPelicula(Pelicula pelicula) throws CodigoDuplicadoException {
        if (buscarIndicePelicula(pelicula.getCodigo()) != -1) {
            throw new CodigoDuplicadoException("Ya existe una película con el código " + pelicula.getCodigo());
        }
        peliculas.add(pelicula);
    }

    public void registrarSala(Sala sala) throws CodigoDuplicadoException {
        if (buscarIndiceSala(sala.getCodigo()) != -1) {
            throw new CodigoDuplicadoException("Ya existe una sala con el código " + sala.getCodigo());
        }
        salas.add(sala);
    }

    public Empleado obtenerEmpleado(String codigoEmpleado) throws EmpleadoNoEncontradoException {
        int posicion = buscarIndiceEmpleado(codigoEmpleado);
        if (posicion == -1) {
            throw new EmpleadoNoEncontradoException("No existe un empleado con el código " + codigoEmpleado);
        }
        return empleados.get(posicion);
    }

    // Devuelve false si ya existe un empleado con ese código o esa identificación
    public boolean registrarEmpleado(Empleado empleado) {
        if (buscarIndiceEmpleado(empleado.getCodigoEmpleado()) != -1) {
            return false;
        }
        if (existeEmpleadoConIdentificacion(empleado.getIdentificacion())) {
            return false;
        }
        empleados.add(empleado);
        return true;
    }

    // Elimina al empleado. Sus ventas siguen registradas en el historial del cinema.
    public void eliminarEmpleado(String codigoEmpleado) throws EmpleadoNoEncontradoException {
        int posicion = buscarIndiceEmpleado(codigoEmpleado);
        if (posicion == -1) {
            throw new EmpleadoNoEncontradoException("No existe un empleado con el código " + codigoEmpleado);
        }
        empleados.remove(posicion);
    }

    // Devuelve false si ya existe un cliente con esa identificación
    public boolean registrarCliente(Cliente cliente) {
        if (buscarIndiceCliente(cliente.getIdentificacion()) != -1) {
            return false;
        }
        clientes.add(cliente);
        return true;
    }

    public Funcion programarFuncion(String codigo, String codigoPelicula, String codigoSala,
                                    LocalDate fecha, LocalTime hora, double valorBase)
            throws CodigoDuplicadoException, PeliculaNoEncontradaException,
            SalaNoEncontradaException, SalaNoDisponibleException {

        if (buscarIndiceFuncion(codigo) != -1) {
            throw new CodigoDuplicadoException("Ya existe una función con el código " + codigo);
        }
        Pelicula pelicula = obtenerPelicula(codigoPelicula);
        if (!pelicula.estaDisponible()) {
            throw new PeliculaNoEncontradaException("La película " + pelicula.getNombre()
                    + " no está disponible.");
        }
        Sala sala = obtenerSala(codigoSala);
        if (!sala.estaDisponible()) {
            throw new SalaNoDisponibleException("La sala " + sala.getNombre()
                    + " está fuera de servicio.");
        }
        // La sala no puede tener otra función programada a la misma fecha y hora
        for (int i = 0; i < funciones.size(); i++) {
            Funcion otra = funciones.get(i);
            if (otra.estaProgramada()
                    && otra.getSala().getCodigo().equalsIgnoreCase(codigoSala)
                    && otra.getFecha().equals(fecha)
                    && otra.getHora().equals(hora)) {
                throw new SalaNoDisponibleException("La sala " + sala.getNombre()
                        + " ya tiene una función programada en esa fecha y hora.");
            }
        }
        Funcion funcion = new Funcion(codigo, pelicula, sala, fecha, hora, valorBase);
        funciones.add(funcion);
        return funcion;
    }

    // ------------------------------------------------------------------
    // Ventas
    // ------------------------------------------------------------------
    public double calcularValorEntrada(Funcion funcion, Entrada entrada) {
        return entrada.calcularValor(funcion.getValorBase());
    }

    public Venta venderEntrada(String codigoPelicula, String codigoFuncion, Cliente cliente,
                               Empleado empleado, Entrada entrada, String codigoAsiento)
            throws FuncionNoEncontradaException, SinAsientosDisponiblesException,
            AsientoNoEncontradoException, AsientoOcupadoException {

        Funcion funcion = obtenerFuncion(codigoFuncion);

        if (!funcion.getPelicula().getCodigo().equalsIgnoreCase(codigoPelicula)) {
            throw new FuncionNoEncontradaException("La función " + codigoFuncion
                    + " no corresponde a la película indicada.");
        }
        if (!funcion.getPelicula().estaDisponible()) {
            throw new FuncionNoEncontradaException("La película de la función "
                    + codigoFuncion + " no está disponible.");
        }
        if (!funcion.estaProgramada()) {
            throw new FuncionNoEncontradaException("La función " + codigoFuncion
                    + " no está programada (estado: " + funcion.getEstado() + ").");
        }
        if (!funcion.hayDisponibles()) {
            throw new SinAsientosDisponiblesException("La función " + codigoFuncion
                    + " no tiene asientos disponibles.");
        }
        int posicionAsiento = funcion.buscarIndiceAsiento(codigoAsiento);
        if (posicionAsiento == -1) {
            throw new AsientoNoEncontradoException("El asiento " + codigoAsiento
                    + " no existe en la sala de esta función.");
        }
        Asiento asiento = funcion.getAsientos().get(posicionAsiento);
        if (!asiento.estaDisponible()) {
            throw new AsientoOcupadoException("El asiento " + asiento.getCodigo() + " ya está ocupado.");
        }

        asiento.ocupar();
        consecutivoVentas = consecutivoVentas + 1;
        double valor = calcularValorEntrada(funcion, entrada);
        Venta venta = new Venta("V" + consecutivoVentas, cliente, empleado, funcion, asiento, entrada, valor);
        ventas.add(venta);
        empleado.agregarVenta(venta);
        return venta;
    }

    public Venta cancelarVenta(String codigoVenta) throws VentaNoEncontradaException {
        Venta venta = obtenerVenta(codigoVenta);
        if (!venta.estaActiva()) {
            throw new VentaNoEncontradaException("La venta " + codigoVenta + " ya estaba cancelada.");
        }
        venta.cancelar();
        return venta;
    }

    // Cancela la función y todas sus ventas activas. Devuelve cuántas ventas canceló.
    public int cancelarFuncion(String codigoFuncion) throws FuncionNoEncontradaException {
        Funcion funcion = obtenerFuncion(codigoFuncion);
        int canceladas = 0;
        if (funcion.estaProgramada()) {
            funcion.cancelar();
            for (int i = 0; i < ventas.size(); i++) {
                Venta venta = ventas.get(i);
                if (venta.estaActiva() && venta.getFuncion().getCodigo().equalsIgnoreCase(codigoFuncion)) {
                    venta.cancelar();
                    canceladas = canceladas + 1;
                }
            }
        }
        return canceladas;
    }

    // ------------------------------------------------------------------
    // Consultas
    // ------------------------------------------------------------------
    public List<Pelicula> consultarPeliculasDisponibles() {
        List<Pelicula> lista = new ArrayList<Pelicula>();
        for (int i = 0; i < peliculas.size(); i++) {
            if (peliculas.get(i).estaDisponible()) {
                lista.add(peliculas.get(i));
            }
        }
        return lista;
    }

    public List<Funcion> consultarFuncionesProgramadasPorPelicula(String codigoPelicula) {
        List<Funcion> lista = new ArrayList<Funcion>();
        for (int i = 0; i < funciones.size(); i++) {
            Funcion f = funciones.get(i);
            if (f.estaProgramada() && f.getPelicula().getCodigo().equalsIgnoreCase(codigoPelicula)) {
                lista.add(f);
            }
        }
        return lista;
    }

    public List<Funcion> consultarFuncionesPorFecha(LocalDate fecha) {
        List<Funcion> lista = new ArrayList<Funcion>();
        for (int i = 0; i < funciones.size(); i++) {
            Funcion f = funciones.get(i);
            if (f.estaProgramada() && f.getFecha().equals(fecha)) {
                lista.add(f);
            }
        }
        return lista;
    }

    public List<Venta> consultarVentasActivas() {
        List<Venta> lista = new ArrayList<Venta>();
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).estaActiva()) {
                lista.add(ventas.get(i));
            }
        }
        return lista;
    }

    // ------------------------------------------------------------------
    // Reportes (solo cuentan las ventas ACTIVAS)
    // ------------------------------------------------------------------
    public int contarEntradasVendidas() {
        return consultarVentasActivas().size();
    }

    public double calcularTotalIngresos() {
        double total = 0;
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).estaActiva()) {
                total = total + ventas.get(i).getValorPagado();
            }
        }
        return total;
    }

    public int contarEntradasPorPelicula(String codigoPelicula) {
        int cantidad = 0;
        for (int i = 0; i < ventas.size(); i++) {
            Venta venta = ventas.get(i);
            if (venta.estaActiva()
                    && venta.getFuncion().getPelicula().getCodigo().equalsIgnoreCase(codigoPelicula)) {
                cantidad = cantidad + 1;
            }
        }
        return cantidad;
    }

    // Bandera para saber si ya hay entradas vendidas
    public boolean hayVentas() {
        return contarEntradasVendidas() > 0;
    }

    // Si no hay ventas devuelve una película "vacía" (usar antes hayVentas())
    public Pelicula obtenerPeliculaMasVendida() {
        int mejorPosicion = -1;
        int mejorCantidad = 0;
        for (int i = 0; i < peliculas.size(); i++) {
            int cantidad = contarEntradasPorPelicula(peliculas.get(i).getCodigo());
            if (cantidad > mejorCantidad) {
                mejorCantidad = cantidad;
                mejorPosicion = i;
            }
        }
        if (mejorPosicion == -1) {
            return new Pelicula("SIN-VENTAS", "Sin ventas", "", 0, Clasificacion.TODOS);
        }
        return peliculas.get(mejorPosicion);
    }

    // ------------------------------------------------------------------
    // Getters
    // ------------------------------------------------------------------
    public String getNombre() {
        return nombre;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public List<Funcion> getFunciones() {
        return funciones;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }
}
