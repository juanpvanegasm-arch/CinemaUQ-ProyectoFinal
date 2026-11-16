public class Cliente extends Persona {
    private TipoCliente tipoCliente;

    public Cliente(String identificacion, String nombre, TipoCliente tipoCliente) {
        super(identificacion, nombre);
        this.tipoCliente = tipoCliente;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public String obtenerInformacion() {
        return super.obtenerInformacion() + " | Tipo de cliente: " + tipoCliente;
    }
}
