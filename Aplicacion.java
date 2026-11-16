import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Aplicacion {

    private static Scanner sc = new Scanner(System.in);
    private static Cinema cinema = new Cinema("CINEMAUQ");

    public static void main(String[] args) {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println();
            System.out.println("==============================");
            System.out.println("           " + cinema.getNombre());
            System.out.println("==============================");
            System.out.println(" 1. Venta de entrada");
            System.out.println(" 2. Cancelar venta");
            System.out.println(" 3. Consultar películas");
            System.out.println(" 4. Consultar funciones");
            System.out.println(" 5. Consultar disponibilidad");
            System.out.println(" 6. Administrar películas");
            System.out.println(" 7. Administrar funciones");
            System.out.println(" 8. Administrar salas");
            System.out.println(" 9. Administrar tarifas");
            System.out.println("10. Administrar empleados");
            System.out.println("11. Generar reportes");
            System.out.println("12. Cargar datos de ejemplo");
            System.out.println(" 0. Salir");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    venderEntrada();
                    break;
                case 2:
                    cancelarVenta();
                    break;
                case 3:
                    consultarPeliculasDisponibles();
                    break;
                case 4:
                    consultarFunciones();
                    break;
                case 5:
                    consultarDisponibilidad();
                    break;
                case 6:
                    menuPeliculas();
                    break;
                case 7:
                    menuFunciones();
                    break;
                case 8:
                    menuSalas();
                    break;
                case 9:
                    menuTarifas();
                    break;
                case 10:
                    menuEmpleados();
                    break;
                case 11:
                    menuReportes();
                    break;
                case 12:
                    cargarDatosEjemplo();
                    break;
                case 0:
                    System.out.println("¡Gracias por usar CINEMAUQ! Hasta pronto.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }

    // ------------------------------------------------------------------
    // Métodos para leer datos del teclado
    // ------------------------------------------------------------------
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.println("Debe ingresar un número entero.");
            System.out.print(mensaje);
        }
        int numero = sc.nextInt();
        sc.nextLine();
        return numero;
    }

    private static int leerEnteroPositivo(String mensaje) {
        int numero = leerEntero(mensaje);
        while (numero <= 0) {
            System.out.println("El número debe ser mayor que cero.");
            numero = leerEntero(mensaje);
        }
        return numero;
    }

    private static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextDouble()) {
            sc.next();
            System.out.println("Debe ingresar un número.");
            System.out.print(mensaje);
        }
        double numero = sc.nextDouble();
        sc.nextLine();
        return numero;
    }

    private static double leerDoublePositivo(String mensaje) {
        double numero = leerDouble(mensaje);
        while (numero <= 0) {
            System.out.println("El valor debe ser mayor que cero.");
            numero = leerDouble(mensaje);
        }
        return numero;
    }

    private static int leerCapacidad() {
        int capacidad = leerEntero("Capacidad (1 a 260 asientos): ");
        while (capacidad < 1 || capacidad > 260) {
            System.out.println("La capacidad debe estar entre 1 y 260.");
            capacidad = leerEntero("Capacidad (1 a 260 asientos): ");
        }
        return capacidad;
    }

    private static LocalDate leerFecha(String mensaje) {
        System.out.println(mensaje);
        int anio = leerEntero("  Año (ej. 2026): ");
        while (anio < 2000 || anio > 2100) {
            System.out.println("Año no válido.");
            anio = leerEntero("  Año (ej. 2026): ");
        }
        int mes = leerEntero("  Mes (1-12): ");
        while (mes < 1 || mes > 12) {
            System.out.println("Mes no válido.");
            mes = leerEntero("  Mes (1-12): ");
        }
        int diasDelMes = LocalDate.of(anio, mes, 1).lengthOfMonth();
        int dia = leerEntero("  Día (1-" + diasDelMes + "): ");
        while (dia < 1 || dia > diasDelMes) {
            System.out.println("Día no válido.");
            dia = leerEntero("  Día (1-" + diasDelMes + "): ");
        }
        return LocalDate.of(anio, mes, dia);
    }

    private static LocalTime leerHora(String mensaje) {
        System.out.println(mensaje);
        int horas = leerEntero("  Hora (0-23): ");
        while (horas < 0 || horas > 23) {
            System.out.println("Hora no válida.");
            horas = leerEntero("  Hora (0-23): ");
        }
        int minutos = leerEntero("  Minutos (0-59): ");
        while (minutos < 0 || minutos > 59) {
            System.out.println("Minutos no válidos.");
            minutos = leerEntero("  Minutos (0-59): ");
        }
        return LocalTime.of(horas, minutos);
    }

    private static TipoCliente leerTipoCliente(String mensaje) {
        System.out.println(mensaje);
        System.out.println("  1. General  2. Estudiante  3. Niño  4. Adulto mayor");
        int opcion = leerEntero("  Opción: ");
        while (opcion < 1 || opcion > 4) {
            System.out.println("Opción no válida.");
            opcion = leerEntero("  Opción: ");
        }
        TipoCliente tipo = TipoCliente.GENERAL;
        switch (opcion) {
            case 1:
                tipo = TipoCliente.GENERAL;
                break;
            case 2:
                tipo = TipoCliente.ESTUDIANTE;
                break;
            case 3:
                tipo = TipoCliente.NINO;
                break;
            case 4:
                tipo = TipoCliente.ADULTO_MAYOR;
                break;
            default:
                break;
        }
        return tipo;
    }

    private static Entrada leerTipoEntrada(String mensaje) {
        System.out.println(mensaje);
        for (int i = 0; i < cinema.getEntradas().size(); i++) {
            System.out.println("  " + (i + 1) + ". " + cinema.getEntradas().get(i).getNombreTipo());
        }
        int opcion = leerEntero("  Opción: ");
        while (opcion < 1 || opcion > cinema.getEntradas().size()) {
            System.out.println("Opción no válida.");
            opcion = leerEntero("  Opción: ");
        }
        return cinema.getEntradas().get(opcion - 1);
    }

    private static Clasificacion leerClasificacion() {
        System.out.println("Clasificación:");
        System.out.println("  1. Todos  2. Mayores de 7  3. Mayores de 12  4. Mayores de 15  5. Mayores de 18");
        int opcion = leerEntero("  Opción: ");
        while (opcion < 1 || opcion > 5) {
            System.out.println("Opción no válida.");
            opcion = leerEntero("  Opción: ");
        }
        Clasificacion clasificacion = Clasificacion.TODOS;
        switch (opcion) {
            case 1:
                clasificacion = Clasificacion.TODOS;
                break;
            case 2:
                clasificacion = Clasificacion.MAYORES_7;
                break;
            case 3:
                clasificacion = Clasificacion.MAYORES_12;
                break;
            case 4:
                clasificacion = Clasificacion.MAYORES_15;
                break;
            case 5:
                clasificacion = Clasificacion.MAYORES_18;
                break;
            default:
                break;
        }
        return clasificacion;
    }

    private static boolean leerSiNo(String mensaje) {
        String texto = leerTexto(mensaje + " (s/n): ");
        while (!texto.equalsIgnoreCase("s") && !texto.equalsIgnoreCase("n")) {
            System.out.println("Responda con 's' o 'n'.");
            texto = leerTexto(mensaje + " (s/n): ");
        }
        return texto.equalsIgnoreCase("s");
    }

    private static String dinero(double valor) {
        return "$" + Math.round(valor);
    }

    // ------------------------------------------------------------------
    // 1. Venta de entrada
    // ------------------------------------------------------------------
    private static void venderEntrada() {
        System.out.println("\n--- VENTA DE ENTRADA ---");
        List<Pelicula> disponibles = cinema.consultarPeliculasDisponibles();
        if (disponibles.size() == 0) {
            System.out.println("No hay películas disponibles.");
            return;
        }
        for (int i = 0; i < disponibles.size(); i++) {
            System.out.println(disponibles.get(i).obtenerInformacion());
        }
        String codigoPelicula = leerTexto("Código de la película: ");

        try {
            Pelicula pelicula = cinema.obtenerPelicula(codigoPelicula);
            List<Funcion> funciones = cinema.consultarFuncionesProgramadasPorPelicula(codigoPelicula);
            if (funciones.size() == 0) {
                System.out.println("La película " + pelicula.getNombre() + " no tiene funciones programadas.");
                return;
            }
            System.out.println("Funciones programadas de " + pelicula.getNombre() + ":");
            for (int i = 0; i < funciones.size(); i++) {
                System.out.println(funciones.get(i).obtenerInformacion());
            }
            String codigoFuncion = leerTexto("Código de la función: ");
            Funcion funcion = cinema.obtenerFuncion(codigoFuncion);

            String identificacion = leerTexto("Identificación del cliente: ");
            Cliente cliente;
            int posicionCliente = cinema.buscarIndiceCliente(identificacion);
            if (posicionCliente == -1) {
                String nombreCliente = leerTexto("Cliente nuevo. Nombre completo: ");
                TipoCliente tipoCliente = leerTipoCliente("Tipo de cliente:");
                cliente = new Cliente(identificacion, nombreCliente, tipoCliente);
                cinema.registrarCliente(cliente);
            } else {
                cliente = cinema.getClientes().get(posicionCliente);
                System.out.println("Cliente encontrado: " + cliente.obtenerInformacion());
            }

            String codigoEmpleado = leerTexto("Código del empleado que atiende: ");
            Empleado empleado = cinema.obtenerEmpleado(codigoEmpleado);
            Entrada entrada = leerTipoEntrada("Tipo de entrada:");
            mostrarDisponibilidad(funcion);
            String codigoAsiento = leerTexto("Código del asiento (ej. A1): ");

            Venta venta = cinema.venderEntrada(codigoPelicula, codigoFuncion, cliente,
                    empleado, entrada, codigoAsiento);
            System.out.println("¡Venta realizada con éxito!");
            System.out.println(venta.obtenerInformacionEntrada());
        } catch (AsientoOcupadoException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Consulte la disponibilidad (opción 5) y elija otro asiento.");
        } catch (Exception e) {
            System.out.println("No se pudo realizar la venta: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // 2. Cancelar venta
    // ------------------------------------------------------------------
    private static void cancelarVenta() {
        System.out.println("\n--- CANCELAR VENTA ---");
        List<Venta> activas = cinema.consultarVentasActivas();
        if (activas.size() == 0) {
            System.out.println("No hay ventas activas.");
            return;
        }
        System.out.println("Ventas activas:");
        for (int i = 0; i < activas.size(); i++) {
            System.out.println(activas.get(i).obtenerResumen());
        }
        String codigoVenta = leerTexto("Código de la venta a cancelar: ");

        try {
            Venta venta = cinema.cancelarVenta(codigoVenta);
            System.out.println("Venta cancelada. El asiento " + venta.getAsiento().getCodigo()
                    + " de la función " + venta.getFuncion().getCodigo() + " quedó disponible.");
            System.out.println(venta.obtenerResumen());
        } catch (Exception e) {
            System.out.println("No se pudo cancelar la venta: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // 3. Consultar películas
    // ------------------------------------------------------------------
    private static void consultarPeliculasDisponibles() {
        System.out.println("\n--- PELÍCULAS DISPONIBLES ---");
        List<Pelicula> lista = cinema.consultarPeliculasDisponibles();
        if (lista.size() == 0) {
            System.out.println("No hay películas disponibles.");
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i).obtenerInformacion());
        }
    }

    // ------------------------------------------------------------------
    // 4. Consultar funciones
    // ------------------------------------------------------------------
    private static void consultarFunciones() {
        System.out.println("\n--- FUNCIONES ---");
        List<Funcion> lista = cinema.getFunciones();
        if (lista.size() == 0) {
            System.out.println("No hay funciones registradas.");
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i).obtenerInformacion());
        }
    }

    // ------------------------------------------------------------------
    // 5. Consultar disponibilidad de una función
    // ------------------------------------------------------------------
    private static void consultarDisponibilidad() {
        System.out.println("\n--- DISPONIBILIDAD DE UNA FUNCIÓN ---");
        consultarFunciones();
        String codigoFuncion = leerTexto("Código de la función: ");
        try {
            Funcion funcion = cinema.obtenerFuncion(codigoFuncion);
            mostrarDisponibilidad(funcion);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mostrarDisponibilidad(Funcion funcion) {
        System.out.println("-----------------------------------------------");
        System.out.println("Función: " + funcion.getPelicula().getNombre());
        System.out.println("Sala: " + funcion.getSala().getNombre());
        System.out.println("Fecha: " + funcion.getFecha() + " | Hora: " + funcion.getHora());
        System.out.println("Total de asientos: " + funcion.contarTotal()
                + " | Disponibles: " + funcion.contarDisponibles()
                + " | Ocupados: " + funcion.contarOcupados());
        System.out.println("-----------------------------------------------");
        for (int i = 0; i < funcion.getAsientos().size(); i++) {
            System.out.println(funcion.getAsientos().get(i).obtenerInformacion());
        }
        System.out.println("-----------------------------------------------");
    }

    // ------------------------------------------------------------------
    // 6. Administrar películas
    // ------------------------------------------------------------------
    private static void menuPeliculas() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- ADMINISTRAR PELÍCULAS ---");
            System.out.println("1. Registrar película");
            System.out.println("2. Consultar todas las películas");
            System.out.println("3. Modificar información de una película");
            System.out.println("4. Deshabilitar una película");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarPelicula();
                    break;
                case 2:
                    consultarTodasLasPeliculas();
                    break;
                case 3:
                    modificarPelicula();
                    break;
                case 4:
                    deshabilitarPelicula();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private static void registrarPelicula() {
        System.out.println("\n--- REGISTRAR PELÍCULA ---");
        String codigo = leerTexto("Código: ");
        String nombre = leerTexto("Nombre: ");
        String genero = leerTexto("Género: ");
        int duracion = leerEnteroPositivo("Duración (minutos): ");
        Clasificacion clasificacion = leerClasificacion();

        Pelicula pelicula = new Pelicula(codigo, nombre, genero, duracion, clasificacion);
        try {
            cinema.registrarPelicula(pelicula);
            System.out.println("Película registrada correctamente.");
        } catch (Exception e) {
            System.out.println("No se pudo registrar la película: " + e.getMessage());
        }
    }

    private static void consultarTodasLasPeliculas() {
        System.out.println("\n--- TODAS LAS PELÍCULAS ---");
        List<Pelicula> lista = cinema.getPeliculas();
        if (lista.size() == 0) {
            System.out.println("No hay películas registradas.");
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i).obtenerInformacion());
        }
    }

    private static void modificarPelicula() {
        System.out.println("\n--- MODIFICAR PELÍCULA ---");
        String codigo = leerTexto("Código de la película: ");
        try {
            Pelicula pelicula = cinema.obtenerPelicula(codigo);
            System.out.println(pelicula.obtenerInformacion());
            System.out.println("¿Qué desea modificar?");
            System.out.println("1. Nombre");
            System.out.println("2. Género");
            System.out.println("3. Duración");
            System.out.println("4. Clasificación");
            int opcion = leerEntero("Opción: ");

            switch (opcion) {
                case 1:
                    pelicula.setNombre(leerTexto("Nuevo nombre: "));
                    System.out.println("Nombre actualizado.");
                    break;
                case 2:
                    pelicula.setGenero(leerTexto("Nuevo género: "));
                    System.out.println("Género actualizado.");
                    break;
                case 3:
                    pelicula.setDuracionMinutos(leerEnteroPositivo("Nueva duración (minutos): "));
                    System.out.println("Duración actualizada.");
                    break;
                case 4:
                    pelicula.setClasificacion(leerClasificacion());
                    System.out.println("Clasificación actualizada.");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deshabilitarPelicula() {
        System.out.println("\n--- DESHABILITAR PELÍCULA ---");
        String codigo = leerTexto("Código de la película: ");
        try {
            Pelicula pelicula = cinema.obtenerPelicula(codigo);
            pelicula.deshabilitar();
            System.out.println("La película " + pelicula.getNombre() + " quedó NO_DISPONIBLE.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // 7. Administrar funciones
    // ------------------------------------------------------------------
    private static void menuFunciones() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- ADMINISTRAR FUNCIONES ---");
            System.out.println("1. Programar función");
            System.out.println("2. Consultar funciones");
            System.out.println("3. Cancelar función");
            System.out.println("4. Marcar función como finalizada");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    programarFuncion();
                    break;
                case 2:
                    consultarFunciones();
                    break;
                case 3:
                    cancelarFuncion();
                    break;
                case 4:
                    finalizarFuncion();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private static void programarFuncion() {
        System.out.println("\n--- PROGRAMAR FUNCIÓN ---");
        System.out.println("Películas registradas:");
        for (int i = 0; i < cinema.getPeliculas().size(); i++) {
            System.out.println(cinema.getPeliculas().get(i).obtenerInformacion());
        }
        System.out.println("Salas registradas:");
        for (int i = 0; i < cinema.getSalas().size(); i++) {
            System.out.println(cinema.getSalas().get(i).obtenerInformacion());
        }

        String codigo = leerTexto("Código de la función: ");
        String codigoPelicula = leerTexto("Código de la película: ");
        String codigoSala = leerTexto("Código de la sala: ");
        LocalDate fecha = leerFecha("Fecha de la función:");
        LocalTime hora = leerHora("Hora de la función:");
        double valorBase = leerDoublePositivo("Valor base de la entrada: ");

        try {
            Funcion funcion = cinema.programarFuncion(codigo, codigoPelicula, codigoSala,
                    fecha, hora, valorBase);
            System.out.println("Función programada correctamente:");
            System.out.println(funcion.obtenerInformacion());
        } catch (Exception e) {
            System.out.println("No se pudo programar la función: " + e.getMessage());
        }
    }

    private static void cancelarFuncion() {
        System.out.println("\n--- CANCELAR FUNCIÓN ---");
        String codigo = leerTexto("Código de la función: ");
        try {
            Funcion funcion = cinema.obtenerFuncion(codigo);
            if (!funcion.estaProgramada()) {
                System.out.println("Solo se pueden cancelar funciones PROGRAMADAS (estado actual: "
                        + funcion.getEstado() + ").");
                return;
            }
            int ventasCanceladas = cinema.cancelarFuncion(codigo);
            System.out.println("Función cancelada. Ventas canceladas: " + ventasCanceladas);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void finalizarFuncion() {
        System.out.println("\n--- FINALIZAR FUNCIÓN ---");
        String codigo = leerTexto("Código de la función: ");
        try {
            Funcion funcion = cinema.obtenerFuncion(codigo);
            if (funcion.estaProgramada()) {
                funcion.finalizar();
                System.out.println("La función quedó FINALIZADA.");
            } else {
                System.out.println("Solo se pueden finalizar funciones PROGRAMADAS (estado actual: "
                        + funcion.getEstado() + ").");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // 8. Administrar salas
    // ------------------------------------------------------------------
    private static void menuSalas() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- ADMINISTRAR SALAS ---");
            System.out.println("1. Registrar sala");
            System.out.println("2. Consultar salas");
            System.out.println("3. Modificar información de una sala");
            System.out.println("4. Deshabilitar una sala (fuera de servicio)");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarSala();
                    break;
                case 2:
                    consultarSalas();
                    break;
                case 3:
                    modificarSala();
                    break;
                case 4:
                    deshabilitarSala();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private static void registrarSala() {
        System.out.println("\n--- REGISTRAR SALA ---");
        String codigo = leerTexto("Código: ");
        String nombre = leerTexto("Nombre o número: ");
        int capacidad = leerCapacidad();

        Sala sala = new Sala(codigo, nombre, capacidad);
        try {
            cinema.registrarSala(sala);
            System.out.println("Sala registrada con " + sala.getAsientos().size() + " asientos.");
        } catch (Exception e) {
            System.out.println("No se pudo registrar la sala: " + e.getMessage());
        }
    }

    private static void consultarSalas() {
        System.out.println("\n--- SALAS ---");
        List<Sala> lista = cinema.getSalas();
        if (lista.size() == 0) {
            System.out.println("No hay salas registradas.");
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i).obtenerInformacion());
        }
    }

    private static void modificarSala() {
        System.out.println("\n--- MODIFICAR SALA ---");
        String codigo = leerTexto("Código de la sala: ");
        try {
            Sala sala = cinema.obtenerSala(codigo);
            System.out.println(sala.obtenerInformacion());
            System.out.println("¿Qué desea modificar?");
            System.out.println("1. Nombre o número");
            System.out.println("2. Capacidad (aplica a las funciones que se programen después)");
            int opcion = leerEntero("Opción: ");

            switch (opcion) {
                case 1:
                    sala.setNombre(leerTexto("Nuevo nombre o número: "));
                    System.out.println("Nombre actualizado.");
                    break;
                case 2:
                    sala.setCapacidad(leerCapacidad());
                    System.out.println("Capacidad actualizada.");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deshabilitarSala() {
        System.out.println("\n--- DESHABILITAR SALA ---");
        String codigo = leerTexto("Código de la sala: ");
        try {
            Sala sala = cinema.obtenerSala(codigo);
            sala.deshabilitar();
            System.out.println("La sala " + sala.getNombre() + " quedó FUERA_DE_SERVICIO.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // 9. Administrar tarifas
    // ------------------------------------------------------------------
    private static void menuTarifas() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- ADMINISTRAR TARIFAS ---");
            System.out.println("1. Consultar tarifas");
            System.out.println("2. Modificar una tarifa");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    consultarTarifas();
                    break;
                case 2:
                    modificarTarifa();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private static void consultarTarifas() {
        System.out.println("\n--- TARIFAS ---");
        for (int i = 0; i < cinema.getEntradas().size(); i++) {
            System.out.println(cinema.getEntradas().get(i).obtenerInformacion());
        }
    }

    private static void modificarTarifa() {
        System.out.println("\n--- MODIFICAR TARIFA ---");
        consultarTarifas();
        Entrada entrada = leerTipoEntrada("Tipo de entrada a modificar:");
        double porcentaje = leerDoublePositivo("Nuevo porcentaje del valor base (ej. 80): ");
        while (porcentaje > 100) {
            System.out.println("El porcentaje no puede superar 100.");
            porcentaje = leerDoublePositivo("Nuevo porcentaje del valor base (ej. 80): ");
        }
        entrada.setPorcentaje(porcentaje);
        System.out.println("Tarifa actualizada: " + entrada.obtenerInformacion());
    }

    // ------------------------------------------------------------------
    // 10. Administrar empleados
    // ------------------------------------------------------------------
    private static void menuEmpleados() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- ADMINISTRAR EMPLEADOS ---");
            System.out.println("1. Registrar empleado");
            System.out.println("2. Consultar empleados");
            System.out.println("3. Modificar empleado");
            System.out.println("4. Eliminar empleado");
            System.out.println("5. Ver la lista de ventas de un empleado");
            System.out.println("6. Ver la lista de ventas de todos los empleados");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarEmpleado();
                    break;
                case 2:
                    consultarEmpleados();
                    break;
                case 3:
                    modificarEmpleado();
                    break;
                case 4:
                    eliminarEmpleado();
                    break;
                case 5:
                    verVentasDeUnEmpleado();
                    break;
                case 6:
                    verVentasDeTodosLosEmpleados();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private static void registrarEmpleado() {
        System.out.println("\n--- REGISTRAR EMPLEADO ---");
        String codigoEmpleado = leerTexto("Código de empleado: ");
        String identificacion = leerTexto("Identificación: ");
        String nombre = leerTexto("Nombre completo: ");

        if (cinema.registrarEmpleado(new Empleado(identificacion, nombre, codigoEmpleado))) {
            System.out.println("Empleado registrado correctamente.");
        } else {
            System.out.println("Ya existe un empleado con ese código o esa identificación. No se registró.");
        }
    }

    private static void consultarEmpleados() {
        System.out.println("\n--- EMPLEADOS ---");
        List<Empleado> lista = cinema.getEmpleados();
        if (lista.size() == 0) {
            System.out.println("No hay empleados registrados.");
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i).obtenerInformacion());
        }
    }

    private static void modificarEmpleado() {
        System.out.println("\n--- MODIFICAR EMPLEADO ---");
        String codigoEmpleado = leerTexto("Código del empleado: ");
        try {
            Empleado empleado = cinema.obtenerEmpleado(codigoEmpleado);
            System.out.println(empleado.obtenerInformacion());
            System.out.println("¿Qué desea modificar? (el código de empleado no se puede cambiar)");
            System.out.println("1. Nombre");
            System.out.println("2. Identificación");
            int opcion = leerEntero("Opción: ");

            switch (opcion) {
                case 1:
                    empleado.setNombre(leerTexto("Nuevo nombre: "));
                    System.out.println("Nombre actualizado.");
                    break;
                case 2:
                    String nuevaIdentificacion = leerTexto("Nueva identificación: ");
                    if (cinema.existeEmpleadoConIdentificacion(nuevaIdentificacion)) {
                        System.out.println("Ya existe un empleado con esa identificación. No se modificó.");
                    } else {
                        empleado.setIdentificacion(nuevaIdentificacion);
                        System.out.println("Identificación actualizada.");
                    }
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarEmpleado() {
        System.out.println("\n--- ELIMINAR EMPLEADO ---");
        String codigoEmpleado = leerTexto("Código del empleado: ");
        try {
            Empleado empleado = cinema.obtenerEmpleado(codigoEmpleado);
            System.out.println(empleado.obtenerInformacion());
            boolean confirmar = leerSiNo("¿Seguro que desea eliminarlo?");
            if (confirmar) {
                cinema.eliminarEmpleado(codigoEmpleado);
                System.out.println("Empleado eliminado. Sus ventas siguen en el historial del cinema.");
            } else {
                System.out.println("Operación cancelada.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void verVentasDeUnEmpleado() {
        System.out.println("\n--- VENTAS DE UN EMPLEADO ---");
        String codigoEmpleado = leerTexto("Código del empleado: ");
        try {
            Empleado empleado = cinema.obtenerEmpleado(codigoEmpleado);
            mostrarVentasDeEmpleado(empleado);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void verVentasDeTodosLosEmpleados() {
        System.out.println("\n--- VENTAS DE TODOS LOS EMPLEADOS ---");
        List<Empleado> lista = cinema.getEmpleados();
        if (lista.size() == 0) {
            System.out.println("No hay empleados registrados.");
        }
        for (int i = 0; i < lista.size(); i++) {
            mostrarVentasDeEmpleado(lista.get(i));
        }
    }

    private static void mostrarVentasDeEmpleado(Empleado empleado) {
        System.out.println("Empleado: " + empleado.getNombre() + " (" + empleado.getCodigoEmpleado() + ")");
        List<Venta> ventas = empleado.consultarVentas();
        if (ventas.size() == 0) {
            System.out.println("  Este empleado aún no tiene ventas.");
        }
        double totalActivas = 0;
        for (int i = 0; i < ventas.size(); i++) {
            System.out.println("  " + ventas.get(i).obtenerResumen());
            if (ventas.get(i).estaActiva()) {
                totalActivas = totalActivas + ventas.get(i).getValorPagado();
            }
        }
        System.out.println("  Total vendido (ventas activas): " + dinero(totalActivas));
    }

    // ------------------------------------------------------------------
    // 11. Reportes
    // ------------------------------------------------------------------
    private static void menuReportes() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GENERAR REPORTES ---");
            System.out.println("1. Total de entradas vendidas");
            System.out.println("2. Total de ingresos");
            System.out.println("3. Película con mayor cantidad de entradas vendidas");
            System.out.println("4. Ocupación de una función");
            System.out.println("5. Funciones programadas en una fecha");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    System.out.println("Total de entradas vendidas: " + cinema.contarEntradasVendidas());
                    break;
                case 2:
                    System.out.println("Total de ingresos: " + dinero(cinema.calcularTotalIngresos()));
                    break;
                case 3:
                    reportePeliculaMasVendida();
                    break;
                case 4:
                    reporteOcupacionFuncion();
                    break;
                case 5:
                    reporteFuncionesPorFecha();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    private static void reportePeliculaMasVendida() {
        if (cinema.hayVentas()) {
            Pelicula pelicula = cinema.obtenerPeliculaMasVendida();
            System.out.println("Película con más entradas vendidas: " + pelicula.getNombre()
                    + " (" + cinema.contarEntradasPorPelicula(pelicula.getCodigo()) + " entradas)");
        } else {
            System.out.println("Aún no hay entradas vendidas.");
        }
    }

    private static void reporteOcupacionFuncion() {
        consultarFunciones();
        String codigo = leerTexto("Código de la función: ");
        try {
            Funcion funcion = cinema.obtenerFuncion(codigo);
            System.out.println("Función " + funcion.getCodigo() + " - " + funcion.getPelicula().getNombre());
            System.out.println("Asientos disponibles: " + funcion.contarDisponibles());
            System.out.println("Asientos ocupados: " + funcion.contarOcupados());
        } catch (Exception e) {
            // el getMessage devuelve la cadena de texto con el mensaje que explica qué ocurrió
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void reporteFuncionesPorFecha() {
        LocalDate fecha = leerFecha("Fecha a consultar:");
        List<Funcion> lista = cinema.consultarFuncionesPorFecha(fecha);
        System.out.println("Funciones programadas para el " + fecha + ":");
        if (lista.size() == 0) {
            System.out.println("No hay funciones programadas en esa fecha.");
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i).obtenerInformacion());
        }
    }

    // ------------------------------------------------------------------
    // 12. Datos de ejemplo
    // ------------------------------------------------------------------
    private static void cargarDatosEjemplo() {
        try {
            cinema.registrarPelicula(new Pelicula("P01", "Interestelar", "Ciencia ficción", 169, Clasificacion.MAYORES_12));
            cinema.registrarPelicula(new Pelicula("P02", "El Último Algoritmo", "Suspenso", 110, Clasificacion.MAYORES_15));
            cinema.registrarPelicula(new Pelicula("P03", "Coco", "Animación", 105, Clasificacion.TODOS));
            Pelicula antigua = new Pelicula("P04", "Película retirada", "Drama", 95, Clasificacion.MAYORES_7);
            antigua.deshabilitar();
            cinema.registrarPelicula(antigua);

            cinema.registrarEmpleado(new Empleado("1010", "Carlos Ruiz", "E01"));
            cinema.registrarEmpleado(new Empleado("1020", "Laura Mora", "E02"));

            cinema.registrarSala(new Sala("S1", "Sala 1", 20));
            cinema.registrarSala(new Sala("S2", "Sala 2", 30));
            Sala fuera = new Sala("S3", "Sala 3", 20);
            fuera.deshabilitar();
            cinema.registrarSala(fuera);

            LocalDate hoy = LocalDate.now();
            LocalDate manana = hoy.plusDays(1);
            cinema.programarFuncion("F1", "P01", "S1", hoy, LocalTime.of(14, 0), 15000);
            cinema.programarFuncion("F2", "P01", "S2", hoy, LocalTime.of(18, 0), 15000);
            cinema.programarFuncion("F3", "P02", "S2", manana, LocalTime.of(20, 30), 18000);
            cinema.programarFuncion("F4", "P03", "S1", manana, LocalTime.of(16, 0), 12000);

            System.out.println("Datos de ejemplo cargados:");
            System.out.println("- Películas: P01, P02, P03 (disponibles) y P04 (no disponible)");
            System.out.println("- Empleados: códigos E01 (Carlos Ruiz) y E02 (Laura Mora)");
            System.out.println("- Salas: S1 (20 asientos), S2 (30 asientos) y S3 (fuera de servicio)");
            System.out.println("- Funciones: F1 y F2 (hoy), F3 y F4 (mañana)");
        } catch (Exception e) {
            System.out.println("No se pudieron cargar los datos de ejemplo: " + e.getMessage());
        }
    }
}
